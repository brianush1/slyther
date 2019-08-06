package net.gegy1000.slyther.network.message.fromserver;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.client.game.entity.ClientFood;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.server.MessageRemoveFoodBase;

public class MessageRemoveFood extends MessageRemoveFoodBase implements MessageFromServer {

	public MessageRemoveFood() {
	}

	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		int x = buffer.readUInt16();
		int y = buffer.readUInt16();
		int id = y * client.GAME_RADIUS * 3 + x;
		ClientFood food = client.getFood(id);
		if (food != null) {
			food.eaten = true;
			if (buffer.hasRemaining(2)) {
				food.eater = client.getSnake(buffer.readUInt16());
				food.eatenFr = 0;
			} else {
				client.removeEntity(food);
			}
		}
	}
}
