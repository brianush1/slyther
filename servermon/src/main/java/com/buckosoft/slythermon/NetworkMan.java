/**
 * 
 */
package com.buckosoft.slythermon;

import java.net.URI;
import java.nio.ByteBuffer;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buckosoft.slythermon.message.MessageBase;

import net.gegy1000.slyther.network.MessageByteBuffer;


/**
 * @author dick
 *
 */
public class NetworkMan extends WebSocketClient {
    private static final Logger log = LoggerFactory.getLogger(NetworkMan.class);

	private Main main;
	public NetworkMan(Main theMain, URI serverUri) {
		super(serverUri);
		main = theMain;
		connect();
	}

	public boolean isConnected() {
		return(getReadyState() == ReadyState.OPEN);
	}

	public void sendMessage(MessageBase message) {
		send(message.buffer.array());
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		main.onConnectedToServer();
		
	}

	@Override
	public void onMessage(String message) {
		log.warn("Unhandled message '" + message + "'");
		
	}
	public void onMessage( ByteBuffer bytes ) {
		MessageByteBuffer bb = new MessageByteBuffer(bytes);
		int b = bb.readUInt8();
		log.info("got message " + b);
		MessageBase mb = MessageHandler.INSTANCE.getMessage((byte)b);
		mb.read(bb, main);
	}
	
	@Override
	public void onClose(int code, String reason, boolean remote) {
		main.onDisconnectedFromServer();
	}

	@Override
	public void onError(Exception ex) {
		// TODO Auto-generated method stub
		
	}

}
