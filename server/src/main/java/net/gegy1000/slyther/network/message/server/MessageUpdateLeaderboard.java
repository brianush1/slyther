package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.game.Color;
import net.gegy1000.slyther.game.SkinHandler;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherServerServerMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;
import net.gegy1000.slyther.server.game.entity.ServerLeaderboardEntry;
import net.gegy1000.slyther.server.game.entity.ServerSnake;

public class MessageUpdateLeaderboard extends SlytherServerServerMessageBase {
	@Override
	public void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
		int playerIndex = 0;
		if (client.rank > 0 && client.rank <= server.leaderboard.size()) {
			playerIndex = client.rank;
		}
		buffer.writeUInt8(playerIndex);
		buffer.writeUInt16(client.rank);
		buffer.writeUInt16(server.getSnakes().size());
		for (ServerLeaderboardEntry leaderboardEntry : server.leaderboard) {
			ServerSnake snake = leaderboardEntry.client.snake;
			buffer.writeUInt16(snake.sct);
			buffer.writeUInt24((int) (snake.fam * 0xFFFFFF));
			buffer.writeUInt8(SkinHandler.INSTANCE.getDetails(snake.client.skin).pattern[0].ordinal() % Color.values().length);
			String name = leaderboardEntry.client.name;
			buffer.writeUInt8(name.length());
			for (int i = 0; i < name.length(); i++) {
				buffer.writeUInt8((byte) name.charAt(i));
			}
		}
	}

	@Override
	final public int[] getMessageIds() {
		return new int[] { 'l' };
	}
}
