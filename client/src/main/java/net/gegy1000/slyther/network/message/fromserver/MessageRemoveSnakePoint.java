package net.gegy1000.slyther.network.message.fromserver;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.game.entity.Snake;
import net.gegy1000.slyther.game.entity.SnakePoint;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.server.MessageRemoveSnakePointBase;

public class MessageRemoveSnakePoint extends MessageRemoveSnakePointBase implements MessageFromServer {
	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		Snake<?> snake = client.getSnake(buffer.readUInt16());
		if (snake != null) {
			if (buffer.hasRemaining(3)) {
				snake.fam = (double) buffer.readUInt24() / 0xFFFFFF;
			}
			for (SnakePoint point : snake.points) {
				if (!point.dying) {
					point.dying = true;
					snake.sct--;
					snake.scale = Math.min(6.0F, (snake.sct - 2.0F) / 106.0F + 1.0F);
					snake.scaleTurnMultiplier = (float) (Math.pow((7.0F - snake.scale) / 6.0F, 2.0F) * 0.87F + 0.13F);
					snake.moveSpeed = client.NSP1 + client.NSP2 * snake.scale;
					snake.accelleratingSpeed = snake.moveSpeed + 0.1F;
					snake.wantedSeperation = snake.scale * 6.0F;
					float max = Snake.NSEP / client.globalScale;
					if (snake.wantedSeperation > max) {
						snake.wantedSeperation = max;
					}
					break;
				}
			}
			snake.updateLength();
		}
	}

}
