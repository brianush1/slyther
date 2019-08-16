package net.gegy1000.slyther.network.message.fromserver;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.server.MessagePingBase;

public class MessagePing extends MessagePingBase implements MessageFromServer {
	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		networkManager.waitingForPingReturn = false;
		if (client.lagging) {
			client.errorTime *= client.lagMultiplier;
			client.lagging = false;
		}
	}

}