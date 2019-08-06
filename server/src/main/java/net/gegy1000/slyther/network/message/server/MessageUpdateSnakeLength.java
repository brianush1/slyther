package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.game.entity.Snake;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherServerServerMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;

public class MessageUpdateSnakeLength extends SlytherServerServerMessageBase {
	private Snake<?> snake;

	public MessageUpdateSnakeLength() {
	}

	public MessageUpdateSnakeLength(Snake<?> snake) {
		this.snake = snake;
	}

	@Override
	public void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
		buffer.writeUInt16(snake.id);
		buffer.writeUInt24((int) (snake.fam * 0xFFFFFF));
	}

	@Override
	public int[] getMessageIds() {
		return new int[] { 'h' };
	}
}
