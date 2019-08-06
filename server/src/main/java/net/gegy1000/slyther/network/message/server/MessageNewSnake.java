package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.game.SkinCustom;
import net.gegy1000.slyther.game.entity.Snake;
import net.gegy1000.slyther.game.entity.SnakePoint;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherServerServerMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;

public class MessageNewSnake extends SlytherServerServerMessageBase {
	private boolean removing;
	private boolean dead;
	private Snake<?> snake;

	public MessageNewSnake() {
	}

	public MessageNewSnake(Snake<?> snake, boolean dead) {
		this(snake);
		this.dead = dead;
		removing = true;
	}

	public MessageNewSnake(Snake<?> snake) {
		this.snake = snake;
	}

	@Override
	public void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
		buffer.writeUInt16(snake.id);
		if (removing) {
			buffer.writeUInt8(dead ? 1 : 0);
		} else {
			buffer.writeUInt24( (int) (snake.angle / ((2.0F * Math.PI) / 0xFFFFFF)));
			buffer.writeUInt8(0);
			buffer.writeUInt24( (int) (snake.wantedAngle / ((2.0F * Math.PI) / 0xFFFFFF)));
			buffer.writeUInt16((int) (snake.speed * 1000.0F));
			buffer.writeUInt24((int) (snake.fam * 0xFFFFFF));
			buffer.writeUInt8(snake.skin.ordinal());
			int gameRadius = server.configuration.gameRadius;
			buffer.writeUInt24((int) ((snake.posX + gameRadius) * 5.0F));
			buffer.writeUInt24((int) ((snake.posY + gameRadius) * 5.0F));
			String name = snake.name;
			buffer.writeUInt8(name.length());
			for (int i = 0; i < name.length(); i++) {
				buffer.writeUInt8((byte) name.charAt(i));
			}
			if (snake.skin.isCustom()) {
				SkinCustom sc = (SkinCustom)snake.skin;
				buffer.writeUInt8(sc.getColorsPacked().length);
				//buffer.writeUInt24(0xFFFFFF);
				//buffer.writeUInt24(0);
				//buffer.writeUInt16(420);
				buffer.writeBytes(sc.getColorsPacked());
			} else {
				buffer.writeUInt8(0);
			}
			boolean head = true;
			float prevPosX = 0.0F;
			float prevPosY = 0.0F;
			for (SnakePoint point : snake.points) {
				float posX = point.posX + gameRadius;
				float posY = point.posY + gameRadius;
				if (head) {
					buffer.writeUInt24((int) (posX * 5.0F));
					buffer.writeUInt24((int) (posY * 5.0F));
					head = false;
				} else {
					buffer.writeUInt8((int) ((posX - prevPosX + 127) / 2.0F));
					buffer.writeUInt8((int) ((posY - prevPosY + 127) / 2.0F));
				}
				prevPosX = posX;
				prevPosY = posY;
			}
		}
	}


	@Override
	final public int[] getMessageIds() {
		return new int[] { 's' };
	}
}
