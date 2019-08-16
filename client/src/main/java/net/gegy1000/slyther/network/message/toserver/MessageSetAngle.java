package net.gegy1000.slyther.network.message.toserver;

import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.game.entity.Snake;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherClientMessageBase;

public class MessageSetAngle extends SlytherClientMessageBase {
	public float angle;

	public MessageSetAngle() {
	}

	public MessageSetAngle(float angle) {
		this.angle = angle;
	}

	@Override
	public void write(MessageByteBuffer buffer, SlytherClient client) {
		int sendAngle = (int) Math.floor((251 * angle) / Snake.PI_2);
		buffer.writeUInt8(sendAngle & 0xFF);
		client.lastSendAngleTime = System.currentTimeMillis();
	}

//	@Override
//	public void read(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
//		if (client.snake != null)
//			client.snake.wantedAngle = (float) ((buffer.readUInt8() / 251.0F) * SlytherClient.PI_2);
//	}
}
