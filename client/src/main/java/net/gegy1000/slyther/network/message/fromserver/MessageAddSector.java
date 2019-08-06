package net.gegy1000.slyther.network.message.fromserver;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.client.game.entity.ClientSector;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.server.MessageAddSectorBase;

public class MessageAddSector extends MessageAddSectorBase implements MessageFromServer {

	public MessageAddSector() {
	}

	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		client.addSector(new ClientSector(client, buffer.readUInt8(), buffer.readUInt8()));
	}

}
