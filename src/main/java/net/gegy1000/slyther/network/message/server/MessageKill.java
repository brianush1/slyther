package net.gegy1000.slyther.network.message.server;


import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherServerMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;
import net.gegy1000.slyther.util.Log;

public class MessageKill extends SlytherServerMessageBase {

	@Override
	public void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
		/* unused */
	}

	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		int me = buffer.readUInt16();
		client.gameStatistic.setKills(buffer.readUInt24());
		Log.debug("Kill: me={} count={}", me, client.gameStatistic.getKills());
	}

	@Override
	public int[] getMessageIds() {
        return new int[] { 'k' };
	}

}
