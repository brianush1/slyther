package net.gegy1000.slyther.network.message.fromserver;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.server.MessageUpdateMapBase;

public class MessageUpdateMap extends MessageUpdateMapBase implements MessageFromServer {

	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		for (int x = 0; x < 80; x++) {
			for (int y = 0; y < 80; y++) {
				client.map[x][y] = false;
			}
		}
		int mapSize = 80 * 80;
		for (int i = 0; i < mapSize && buffer.hasRemaining();) {
			int value = buffer.readUInt8();
			if (value > 127) {
				i += value - 128;
			} else {
				for (int bit = 64; bit > 0 && i < mapSize; bit /= 2, i++) {
					if ((value & bit) > 0) {
						client.map[i % 80][i / 80] = true;
					}
				}
			}
		}
	}
}
