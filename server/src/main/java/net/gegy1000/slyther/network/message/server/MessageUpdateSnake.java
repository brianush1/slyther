package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.game.entity.Snake;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherServerServerMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;

//10 per second
public class MessageUpdateSnake extends SlytherServerServerMessageBase {
	private Snake snake;
	private boolean turnDirection;
	private boolean angle;
	private boolean wantedAngle;
	@SuppressWarnings("unused")
	private boolean speed;

	public MessageUpdateSnake() {
	}

	public MessageUpdateSnake(Snake snake, boolean turnDirection, boolean angle, boolean wantedAngle, boolean speed) {
		this.snake = snake;
		this.turnDirection = turnDirection;
		this.angle = angle;
		this.wantedAngle = wantedAngle;
		this.speed = speed;
	}

	@Override
	public void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
		buffer.writeUInt16(snake.id);
		if (turnDirection) {
			buffer.writeUInt8((int) (snake.angle / (2.0F * Math.PI / 256.0F)));
			buffer.writeUInt8((int) (snake.wantedAngle / (2.0F * Math.PI / 256.0F)));
			buffer.writeUInt8((int) (snake.speed * 18.0F));
		} else {
			buffer.writeUInt8((int) (snake.angle / (2.0F * Math.PI / 256.0F)));
			buffer.writeUInt8((int) (snake.speed * 18.0F));
		}
	}


	@Override
	final public int[] getMessageIds() {
		return new int[] { 'e', 'E', '3', '4', '5' };
	}

	@Override
	final public int getSendMessageId() {
		if (turnDirection) {
			if (snake.turnDirection != 1) {
				return !(angle || wantedAngle) ? '5' : '4';
			}
		}
		return 'e';
	}
}
