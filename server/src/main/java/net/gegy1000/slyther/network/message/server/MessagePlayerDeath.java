package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherServerServerMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;

public class MessagePlayerDeath extends SlytherServerServerMessageBase {
	@Override
	public void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
		// TODO
	}


	@Override
	final public int[] getMessageIds() {
		return new int[] { 'v' };
	}
}
