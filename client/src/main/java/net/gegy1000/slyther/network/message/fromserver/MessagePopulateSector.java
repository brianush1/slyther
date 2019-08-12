package net.gegy1000.slyther.network.message.fromserver;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.client.game.entity.ClientFood;
import net.gegy1000.slyther.game.Color;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.server.MessagePopulateSectorBase;

public class MessagePopulateSector extends MessagePopulateSectorBase implements MessageFromServer {

	public MessagePopulateSector() {
	}

	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		while (buffer.hasRemaining()) {
			Color color = Color.values()[buffer.readUInt8() % Color.values().length];
			int x = buffer.readUInt16();
			int y = buffer.readUInt16();
			float size = buffer.readUInt8() / 5.0F;
			client.addEntity(new ClientFood(client, x, y, size, true, color));
		}
	}

}
