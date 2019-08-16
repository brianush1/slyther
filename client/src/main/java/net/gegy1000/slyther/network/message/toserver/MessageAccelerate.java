package net.gegy1000.slyther.network.message.toserver;

import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherClientMessageBase;

public class MessageAccelerate extends SlytherClientMessageBase {
	public boolean accelerating;

	public MessageAccelerate() {
	}

	public MessageAccelerate(boolean accelerating) {
		this.accelerating = accelerating;
	}

	@Override
	public void write(MessageByteBuffer buffer, SlytherClient client) {
		buffer.writeUInt8(accelerating ? 253 : 254);
	}

//	@Override
//	public void read(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
//		client.snake.accelerating = accelerating;
//	}
}
