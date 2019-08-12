package net.gegy1000.slyther.network.message.fromserver;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.client.game.entity.ClientFood;
import net.gegy1000.slyther.game.Color;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.server.MessageNewFoodBase;

public class MessageNewFood extends MessageNewFoodBase implements MessageFromServer {

	public MessageNewFood() {
	}


	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		if (buffer.hasRemaining(5)) {
			Color color = Color.values()[buffer.readUInt8()];
			int x = buffer.readUInt16();
			int y = buffer.readUInt16();
			float size = buffer.readUInt8() / 5.0F;
			client.addEntity(new ClientFood(client, x, y, size, messageId == 'b', color));
		}
	}
}
