package net.gegy1000.slyther.network.message.fromserver;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.game.ProfanityHandler;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.server.MessageUpdateLongestPlayerBase;
import net.gegy1000.slyther.util.Log;

public class MessageUpdateLongestPlayer extends MessageUpdateLongestPlayerBase implements MessageFromServer {

	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		int length = buffer.readUInt16();
		float fam = (float) buffer.readUInt24() / 0xFFFFFF;
		int score = (int) Math.floor(15.0F * (client.getFPSL(length) + fam / client.getFMLT(length) - 1.0F) - 5.0F);
		String name = "";
		for (int i = 0; i < buffer.readUInt8(); i++) {
			name += (char) buffer.readUInt8();
		}
		if (!ProfanityHandler.isClean(name)) {
			name = "";
		}
		String message = "";
		while (buffer.hasRemaining()) {
			message += (char) buffer.readUInt8();
		}
		if (!ProfanityHandler.isClean(message)) {
			message = "";
		}
		client.longestPlayerName = name;
		client.longestPlayerScore = score;
		client.longestPlayerMessage = message;
		Log.debug("{} ({}) - \"{}\"", client.longestPlayerName, client.longestPlayerScore, client.longestPlayerMessage);
	}

}
