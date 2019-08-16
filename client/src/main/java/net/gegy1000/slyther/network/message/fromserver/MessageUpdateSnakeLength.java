package net.gegy1000.slyther.network.message.fromserver;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.game.entity.Snake;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.server.MessageUpdateSnakeLengthBase;

public class MessageUpdateSnakeLength extends MessageUpdateSnakeLengthBase implements MessageFromServer {

	public MessageUpdateSnakeLength() {
	}

	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		int id = buffer.readUInt16();
		Snake<?> snake = client.getSnake(id);
		if (snake != null) {
			snake.fam = (double) buffer.readUInt24() / 0xFFFFFF;
			snake.updateLength();
		}
	}

}
