package net.gegy1000.slyther.network.message.fromserver;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.server.MessageGotServerVersionBase;
import net.gegy1000.slyther.network.message.toserver.MessageClientRiddleAnswer;
import net.gegy1000.slyther.network.message.toserver.MessageClientSetup;

public class MessageGotServerVersion extends MessageGotServerVersionBase implements MessageFromServer {

	public MessageGotServerVersion() {
	}


	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		byte[] b = buffer.array();
		int[] data = new int[b.length];
		for (int i=0; i<b.length; i++)
			data[i] = b[i] & 0xFF;
//		version = buffer.readASCIIBytes();
		networkManager.send(new MessageClientRiddleAnswer(data));
		networkManager.send(new MessageClientSetup(client.configuration.nickname, 
				client.configuration.customSkin != null ? client.configuration.customSkin : client.configuration.skin));
	}

}
