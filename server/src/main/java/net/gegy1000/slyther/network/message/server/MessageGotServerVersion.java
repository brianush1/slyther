package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherServerServerMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;

public class MessageGotServerVersion extends SlytherServerServerMessageBase {
	private String version;

	public MessageGotServerVersion() {
	}

	public MessageGotServerVersion(String version) {
		this.version = version;
	}

	@Override
	public void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
		buffer.writeASCIIBytes(version);
	}


	@Override
	final public int[] getMessageIds() {
		return new int[] { '6' };
	}
}
