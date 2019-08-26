package net.gegy1000.slyther.client;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import net.gegy1000.slyther.client.recording.GameRecorder;
import net.gegy1000.slyther.client.recording.GameReplayer;
import net.gegy1000.slyther.client.recording.ReplayMan;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.MessageHandler;
import net.gegy1000.slyther.network.NetworkManager;
import net.gegy1000.slyther.network.ServerMan;
import net.gegy1000.slyther.network.SlytherServerMessageBase;
import net.gegy1000.slyther.network.message.SlytherClientMessageBase;
import net.gegy1000.slyther.network.message.toserver.MessageStartLogin;
import net.gegy1000.slyther.util.Log;

public class ClientNetworkManager extends WebSocketClient implements NetworkManager {
	private static boolean LOG_NETWORK = false;
	public static final int SHUTDOWN_CODE = 1;

	public static final byte[] PING_DATA = new byte[] { (byte) 251 };
	private SlytherClient client;
	private String ip;

	public int bytesPerSecond;
	public int packetsPerSecond;

	public boolean isReplaying;
	public GameRecorder recorder;

	public boolean waitingForPingReturn;
	public long lastPacketTime;
	public long lastPingSendTime;

	public long packetTimeOffset;

	public long currentPacketTime;
	public GameReplayer replayer;

	public ClientNetworkManager(URI uri, SlytherClient client, String ip, Map<String, String> headers, boolean shouldRecord, boolean isReplaying) throws IOException {
		super(uri, new Draft_6455(), headers, 0);
		this.ip = ip;
		this.client = client;
		this.isReplaying = isReplaying;
//      if (!isReplaying && shouldRecord && !SlytherClient.RECORD_FILE.delete()) {
//         SlytherClient.RECORD_FILE.createNewFile();
//      }
		connect();
		if (shouldRecord) {
			recorder = new GameRecorder(ReplayMan.INSTANCE.getNewReplayFile());
			recorder.start();
		}
	}

	public static ClientNetworkManager create(SlytherClient client, ServerMan.Server server, boolean shouldRecord) throws Exception {
		return create(client, server.getIp(), shouldRecord);
	}

	public static ClientNetworkManager create(SlytherClient client, String ip, boolean shouldRecord) throws Exception {
		Log.debug("Connecting to server {}", ip);
		Map<String, String> headers = new HashMap<>(ServerMan.INSTANCE.getHeaders());
		headers.put("Host", ip);
		return new ClientNetworkManager(new URI("ws://" + ip + "/slither"), client, ip, headers, shouldRecord, false);
	}

	public static ClientNetworkManager create(SlytherClient client, File replayFile) throws IOException, URISyntaxException {
		GameReplayer replayer = new GameReplayer(replayFile);
		ClientNetworkManager cnm = new ClientNetworkManager(replayer.getURI(), client, "GameReplayer", null, false, true);
		cnm.replayer = replayer;
		return(cnm);
	}

	@Override
	public void run() {
		Thread.currentThread().setName(getClass().getSimpleName());
		super.run();
	}

	@Override
	public void onOpen(ServerHandshake serverHandshake) {
		send(new MessageStartLogin());
		Log.debug("Connected to {}", ip);
		lastPacketTime = System.currentTimeMillis();
	}

	public void ping() {
		if (isOpen() && !isReplaying) {
			if (!waitingForPingReturn) {
				send(PING_DATA);
				waitingForPingReturn = true;
			}
		}
	}

	@Override
	public void onMessage(String message) {}

	@Override
	public void onMessage(ByteBuffer byteBuffer) {
		MessageByteBuffer buffer = new MessageByteBuffer(byteBuffer);
		if (recorder != null) {
			recorder.onMessage(buffer.array());
		}
		if (buffer.limit() >= 2) {
			bytesPerSecond += buffer.limit();
			packetsPerSecond++;
			lastPacketTime = currentPacketTime;
			currentPacketTime = System.currentTimeMillis();
			int serverTimeDelta = buffer.readUInt16();
			byte messageId = (byte) buffer.readUInt8();
			long timeDelta = currentPacketTime - lastPacketTime;
			if (lastPacketTime == 0) {
				timeDelta = 0;
			}
			packetTimeOffset = serverTimeDelta - timeDelta;
			client.errorTime += Math.max(-180, Math.min(180, timeDelta - serverTimeDelta));
			Class<? extends SlytherServerMessageBase> messageType = MessageHandler.INSTANCE.getServerMessage(messageId);
			if (messageType == null) {
				Log.warn("Received unknown message {} ({})!", () -> Log.bytes(buffer.array()), (char) messageId);
			} else {
				try {
					SlytherServerMessageBase message = messageType.getConstructor().newInstance();
					message.messageId = messageId;
					byte[] bd = new byte[1];
					bd[0] = messageId;
					String s = new String(bd);
					if (LOG_NETWORK)
						Log.debug("Recv: {} \"{}\"", bd[0], s);
					message.serverTimeDelta = serverTimeDelta;
					client.scheduleTask(() -> {
						if (isOpen()) {
							MessageFromServer msf = (MessageFromServer)message;
							msf.read(buffer, client, this);
						}
						return null;
					});
				} catch (Exception e) {
					Log.error("Error while receiving message " + messageId + "!" + " (" + (char) messageId + ")");
					Log.catching(e);
				}
			}
		}
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		Log.debug("Connection closed with code {} for reason \"{}\"", code, reason);
		if (code != SHUTDOWN_CODE) {
			client.reset();   
		}
		if (recorder != null) {
			recorder.close(client.gameStatistic);
		}
	}

	@Override
	public void onError(Exception e) {
		Log.catching(e);
	}

	public void send(SlytherClientMessageBase message) {
		if (isOpen() && !isReplaying) {
			try {
				MessageByteBuffer buffer = new MessageByteBuffer();
				message.write(buffer, client);
				final boolean DEBUG = true;
				if (DEBUG) {
					byte[] bb = buffer.bytes();
					byte[] bd = new byte[1];
					bd[0] = bb[0];
					String s = new String(bd);
					if (LOG_NETWORK)
						Log.debug("Sendm {} \"{}\"", bd[0], s);
					send(bb);
				} else
					send(buffer.bytes());
			} catch (Exception e) {
				Log.error("An error occurred while sending message {}", message.getClass().getName());
				Log.catching(e);
			}
		}
	}

	public String getIp() {
		return ip;
	}
}
