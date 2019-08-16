package net.gegy1000.slyther.network.message.toserver;

import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherClientMessageBase;

public class MessageClientPing extends SlytherClientMessageBase {
	@Override
	public void write(MessageByteBuffer buffer, SlytherClient client) {
		buffer.writeUInt8(251);
	}

//    @Override
//    public void read(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
//        client.send(new MessagePing());
//    }
}