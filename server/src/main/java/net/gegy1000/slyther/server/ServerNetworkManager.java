package net.gegy1000.slyther.server;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageHandler;
import net.gegy1000.slyther.network.NetworkManager;
import net.gegy1000.slyther.network.message.SlytherClientMessageBase;
import net.gegy1000.slyther.network.message.server.MessageRiddle;
import net.gegy1000.slyther.util.Log;

public class ServerNetworkManager extends WebSocketServer implements NetworkManager {
	private static boolean LOG_NETWORK = false;

	private SlytherServer server;
    private int port;

    private int currentClientId;

    public ServerNetworkManager(SlytherServer server, int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
        this.server = server;
        this.port = port;
        start();
    }

	@Override
	public void run() {
		Thread.currentThread().setName(getClass().getSimpleName());
		Log.info("Server starting on port {}", port);
		try {
			super.run();
		} catch (Exception ex) {
			Log.catching(ex);
			System.exit(1);
		}
	}

    @Override
    public void onOpen(WebSocket connection, ClientHandshake handshake) {
    	Log.debug("onOpen");
        server.scheduleTask(() -> {
            Log.info("Initiating a new connection.");
            ConnectedClient client = new ConnectedClient(server, connection, currentClientId);
            if (!server.clients.contains(client)) {					// DIK: How could this client ever be on the list if it was just created?
                currentClientId++;
                server.clients.add(client);
                client.lastPacketTime = System.currentTimeMillis();	// DIK: redundant. Set in the constructor
                client.send(new MessageRiddle());
                server.serverMonitorManager.sendInfo();
            }
            return null;
        });
    }

    @Override
    public void onClose(WebSocket connection, int code, String reason, boolean remote) {
        server.removeClient(connection);
    }

    @Override
    public void onMessage(WebSocket connection, String message) {
    }

    @Override
    public void onMessage(WebSocket connection, ByteBuffer byteBuffer) {
        server.scheduleTask(() -> {
            ConnectedClient client = server.getConnectedClient(connection);
            if (client != null) {
            	if (LOG_NETWORK)
            		Log.info("onMessage {}", byteBuffer.get(0));
                MessageByteBuffer buffer = new MessageByteBuffer(byteBuffer);
                SlytherClientMessageBase message = MessageHandler.INSTANCE.getClientMessage(buffer);
                if (message == null) {
                    Log.warn("Received unknown message {} from {} ({})", () -> Arrays.toString(buffer.array()), client.name, client.id);
                } else {
                    message.read(buffer, server, client);
                }
            }
            return null;
        });
    }

    @Override
    public void onError(WebSocket connection, Exception e) {
        server.removeClient(connection);
    }

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
	}
}
