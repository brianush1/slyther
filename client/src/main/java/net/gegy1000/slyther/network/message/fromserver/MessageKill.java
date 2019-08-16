package net.gegy1000.slyther.network.message.fromserver;


import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.server.MessageKillBase;
import net.gegy1000.slyther.util.Log;

public class MessageKill extends MessageKillBase implements MessageFromServer {

	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		int me = buffer.readUInt16();
		client.gameStatistic.setKills(buffer.readUInt24());
		Log.debug("Kill: me={} count={}", me, client.gameStatistic.getKills());
	}

}
