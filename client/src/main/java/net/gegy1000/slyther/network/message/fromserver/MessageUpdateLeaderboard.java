package net.gegy1000.slyther.network.message.fromserver;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.client.game.entity.ClientLeaderboardEntry;
import net.gegy1000.slyther.game.Color;
import net.gegy1000.slyther.game.ProfanityHandler;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.server.MessageUpdateLeaderboardBase;

public class MessageUpdateLeaderboard extends MessageUpdateLeaderboardBase implements MessageFromServer {

	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		int playerIndex = buffer.readUInt8();
		client.gameStatistic.setRank(buffer.readUInt16());
//      if (client.rank < client.bestRank) {
//          client.bestRank = client.rank;
//      }
		client.gameStatistic.setSnakeCount(buffer.readUInt16());
		client.leaderboard.clear();
		int index = 1;
		while (buffer.hasRemaining()) {
			int length = buffer.readUInt16();
			float fam = (float) buffer.readUInt24() / 0xFFFFFF;
			Color color = Color.values()[buffer.readUInt8() % 9];
			String name = "";
			int nameLength = buffer.readUInt8();
			for (int i = 0; i < nameLength; i++) {
				name += (char) buffer.readUInt8();
			}
			if (index != playerIndex) {
				if (!ProfanityHandler.isClean(name)) {
					name = "";
				}
			}
			int score = (int) Math.floor(15.0F * (client.getFPSL(length) + fam / client.getFMLT(length) - 1.0F) - 5.0F);
			client.leaderboard.add(index - 1, new ClientLeaderboardEntry(name, score, color, index == playerIndex));
			index++;
		}
	}
}
