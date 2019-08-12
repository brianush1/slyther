package net.gegy1000.slyther.network.message.fromserver;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.game.entity.Sector;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.server.MessageRemoveSectorBase;

public class MessageRemoveSector extends MessageRemoveSectorBase implements MessageFromServer {

	public MessageRemoveSector() {
	}

	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		int x = buffer.readUInt8();
		int y = buffer.readUInt8();
		Sector remove = null;
		for (Sector sector : client.getSectors()) {
			if (sector.posX == x && sector.posY == y) {
				remove = sector;
				break;
			}
		}
		client.removeSector(remove);
	}

}
