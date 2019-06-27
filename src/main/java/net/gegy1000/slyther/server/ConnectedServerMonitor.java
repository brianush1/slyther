/**
 * 
 */
package net.gegy1000.slyther.server;

import org.java_websocket.WebSocket;

import net.gegy1000.slyther.network.message.SlytherMonitorMessageBase;
import net.gegy1000.slyther.util.Log;

/**
 * @author Dick Balaska
 *
 */
public class ConnectedServerMonitor {
	public WebSocket socket;
	private SlytherServer server;
	private int id;

	public ConnectedServerMonitor(SlytherServer server, WebSocket socket, int id) {
		this.server = server;
		this.socket = socket;
		this.id = id;
	}

	public void send(SlytherMonitorMessageBase message) {
		if (socket.isOpen()) {
			try {
				socket.send(message.buffer.bytes());
			} catch (Exception e) {
				Log.error("An error occurred while sending message {} to {} ", message.getClass().getName(), id);
				Log.catching(e);
			}
		} else {
			server.serverMonitorManager.removeMonitor(socket);
		}
	}

}
