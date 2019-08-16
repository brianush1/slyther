package net.gegy1000.slyther.network.message.fromserver;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.client.game.entity.ClientSnake;
import net.gegy1000.slyther.game.entity.Snake;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.server.MessageUpdateSnakeBase;

//10 per second
public class MessageUpdateSnake extends MessageUpdateSnakeBase implements MessageFromServer {

	public MessageUpdateSnake() {
	}



	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		int id = buffer.readUInt16();
		int turnDirection = -1;
		float angle = -1;
		float wantedAngle = -1;
		float speed = -1;
		if (buffer.hasRemaining(3)) {
			turnDirection = messageId == 'e' ? 1 : 2;
			angle = (float) (buffer.readUInt8() * (2.0F * Math.PI / 256.0F));
			wantedAngle = (float) (buffer.readUInt8() * (2.0F * Math.PI / 256.0F));
			speed = buffer.readUInt8() / 18.0F;
		} else if (buffer.hasRemaining(2)) {
			if (messageId == 'e') {
				angle = (float) (buffer.readUInt8() * (2.0F * Math.PI / 256.0F));
				speed = buffer.readUInt8() / 18.0F;
			} else if (messageId == 'E') {
				turnDirection = 1;
				wantedAngle = (float) (buffer.readUInt8() * (2.0F * Math.PI / 256.0F));
				speed = buffer.readUInt8() / 18.0F;
			} else if (messageId == '4') {
				turnDirection = 2;
				wantedAngle = (float) (buffer.readUInt8() * (2.0F * Math.PI / 256.0F));
				speed = buffer.readUInt8() / 18.0F;
			} else if (messageId == '5') {
				turnDirection = 2;
				angle = (float) (buffer.readUInt8() * (2.0F * Math.PI / 256.0F));
				wantedAngle = (float) (buffer.readUInt8() * (2.0F * Math.PI / 256.0F));
			}
		} else if (buffer.hasRemaining()) {
			if (messageId == 'e') {
				angle = (float) (buffer.readUInt8() * (2.0F * Math.PI / 256.0F));
			} else if (messageId == 'E') {
				turnDirection = 1;
				wantedAngle = (float) (buffer.readUInt8() * (2.0F * Math.PI / 256.0F));
			} else if (messageId == '3') {
				speed = buffer.readUInt8() / 18.0F;
			}
		}
		ClientSnake snake = client.getSnake(id);
		if (snake != null) {
			if (turnDirection != -1) {
				snake.turnDirection = turnDirection;
			}
			if (angle != -1) {
				float foodAngle = (float) ((angle - snake.angle) % Snake.PI_2);
				if (foodAngle < 0) {
					foodAngle += Snake.PI_2;
				}
				if (foodAngle > Math.PI) {
					foodAngle -= Snake.PI_2;
				}
				int index = snake.foodAngleIndex;
				for (int i = 0; i < Snake.AFC; i++) {
					snake.foodAngles[index] = foodAngle * Snake.AFAS[i];
					index++;
					if (index >= Snake.AFC) {
						index = 0;
					}
				}
				snake.foodAnglesToGo = Snake.AFC;
				snake.angle = angle;
			}
			if (wantedAngle != -1) {
				snake.wantedAngle = wantedAngle;
				if (snake != client.player) {
					snake.eyeAngle = wantedAngle;
				}
			}
			if (speed != -1) {
				snake.speed = speed;
				snake.speedTurnMultiplier = speed / client.SPANG_DV;
				if (snake.speedTurnMultiplier > 1.0F) {
					snake.speedTurnMultiplier = 1.0F;
				}
			}
		}
	}
}
