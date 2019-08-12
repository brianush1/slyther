/**
 * 
 */
package net.gegy1000.slyther.server;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import net.gegy1000.slyther.network.MessageHandler;
import net.gegy1000.slyther.network.message.SlytherMonitorMessageBase;
import net.gegy1000.slyther.network.message.monitor.MessageInfo;
import net.gegy1000.slyther.util.Log;

/**
 * @author Dick Balaska
 *
 */
public class ServerMonitorManager extends WebSocketServer {
	private SlytherServer server;
	private int port;
	private List<ConnectedServerMonitor> monitors = new LinkedList<ConnectedServerMonitor>();
	
    private int currentClientId;


	public ServerMonitorManager(SlytherServer server, int port) throws UnknownHostException {
		super(new InetSocketAddress(port));
		this.server = server;
		this.port = port;
		start();
	}

    @Override
    public void run() {
        Thread.currentThread().setName(getClass().getSimpleName());
        Log.info("Server Monitor  on port {}", port);
        super.run();
    }


	@Override
	public void onOpen(WebSocket connection, ClientHandshake handshake) {
		Log.debug("onOpen");
		server.scheduleTask(() -> {
			Log.info("Connecting a new monitor.");
			ConnectedServerMonitor monitor = new ConnectedServerMonitor(server, connection, currentClientId);
			currentClientId++;
			synchronized(monitors) {
				monitors.add(monitor);
			}
			return null;
		});
	}

	@Override
	public void onClose(WebSocket connection, int code, String reason, boolean remote) {
		Log.info("Monitor connection closed '{}'", reason);
		removeMonitor(connection);
	}

	@Override
	public void onMessage(WebSocket connection, String message) {
		Log.warn("Got monitor text message {}", message);

	}
	@Override
	public void onMessage(WebSocket connection, ByteBuffer byteBuffer) {
		byte type = byteBuffer.get();
		SlytherMonitorMessageBase smm = MessageHandler.INSTANCE.getMonitorMessage(type);
		if (smm == null) {
			Log.warn("ServerMonitor Unhandled binary message {}", type);
			return;
		}
		smm.write(server);
		send(smm);
	}
   
	@Override
	public void onError(WebSocket connection, Exception ex) {
		Log.catching(ex);
		removeMonitor(connection);

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub

	}

	public void send(SlytherMonitorMessageBase message) {
		synchronized(monitors) {
			for (ConnectedServerMonitor csm : monitors) {
				csm.send(message);
			}
		}
	}
	
	public void removeMonitor(WebSocket connection) {
		synchronized(monitors) {
			ConnectedServerMonitor m = findMonitor(connection);
			if (m != null) {
				monitors.remove(m);
			}
		}
	}
	private ConnectedServerMonitor findMonitor(WebSocket connection) {
		for (ConnectedServerMonitor csm : monitors) {
			if (csm.socket == connection)
				return(csm);
		}
		return(null);
	}

	public void sendInfo() {
		MessageInfo mi = new MessageInfo();
		mi.write(server);
		send(mi);
	}

}
