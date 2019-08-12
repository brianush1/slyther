package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.game.entity.Snake;
import net.gegy1000.slyther.game.entity.SnakePoint;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherServerServerMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;

//4 per second
public class MessageSnakeMovement extends SlytherServerServerMessageBase {
	private Snake<?> snake;
	private boolean absolute;
	private boolean updateLength;

	public MessageSnakeMovement() {
	}

	public MessageSnakeMovement(Snake snake, boolean absolute, boolean updateLength) {
		this.snake = snake;
		this.absolute = absolute;
		this.updateLength = updateLength;
	}

	@Override
	public void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
		buffer.writeUInt16(snake.id);
		SnakePoint head = snake.points.get(snake.points.size() - 1);
		int gameRadius = server.configuration.gameRadius;
		if (absolute) {
			buffer.writeUInt16((int) snake.posX + gameRadius);
			buffer.writeUInt16((int) snake.posY + gameRadius);
		} else {
			buffer.writeUInt8(Math.min(255, Math.max(0, (int) ((snake.posX - head.posX) + 128))));
			buffer.writeUInt8(Math.min(255, Math.max(0, (int) ((snake.posY - head.posY) + 128))));
		}
		if (updateLength) {
			buffer.writeUInt24((int) (snake.fam * 0xFFFFFF));
		}
	}


	@Override
	public int[] getMessageIds() {
		return new int[] { 'g', 'n', 'G', 'N' };
	}

	@Override
	public int getSendMessageId() {
		char id = updateLength ? 'n' : 'g';
		return absolute ? id : Character.toUpperCase(id);
	}
}
