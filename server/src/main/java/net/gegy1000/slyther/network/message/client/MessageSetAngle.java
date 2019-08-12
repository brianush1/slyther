package net.gegy1000.slyther.network.message.client;

import net.gegy1000.slyther.game.entity.Snake;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherClientMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;

public class MessageSetAngle extends SlytherClientMessageBase {
//	public float angle;

	public MessageSetAngle() {
	}

//	public MessageSetAngle(float angle) {
//		this.angle = angle;
//	}

	@Override
	public void read(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
		if (client.snake != null)
			client.snake.wantedAngle = (float) ((buffer.readUInt8() / 251.0F) * Snake.PI_2);
	}
}
