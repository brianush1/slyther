package net.gegy1000.slyther.network;

import java.util.HashMap;
import java.util.Map;

import net.gegy1000.slyther.network.message.fromserver.MessageAddSector;
import net.gegy1000.slyther.network.message.fromserver.MessageGotServerVersion;
import net.gegy1000.slyther.network.message.fromserver.MessageKill;
import net.gegy1000.slyther.network.message.fromserver.MessageNewFood;
import net.gegy1000.slyther.network.message.fromserver.MessageNewPrey;
import net.gegy1000.slyther.network.message.fromserver.MessageNewSnake;
import net.gegy1000.slyther.network.message.fromserver.MessagePing;
import net.gegy1000.slyther.network.message.fromserver.MessagePlayerDeath;
import net.gegy1000.slyther.network.message.fromserver.MessagePopulateSector;
import net.gegy1000.slyther.network.message.fromserver.MessagePreLogin;
import net.gegy1000.slyther.network.message.fromserver.MessagePreyPositionUpdate;
import net.gegy1000.slyther.network.message.fromserver.MessageRemoveFood;
import net.gegy1000.slyther.network.message.fromserver.MessageRemoveSector;
import net.gegy1000.slyther.network.message.fromserver.MessageRemoveSnakePoint;
import net.gegy1000.slyther.network.message.fromserver.MessageSetup;
import net.gegy1000.slyther.network.message.fromserver.MessageSnakeMovement;
import net.gegy1000.slyther.network.message.fromserver.MessageUpdateLeaderboard;
import net.gegy1000.slyther.network.message.fromserver.MessageUpdateLongestPlayer;
import net.gegy1000.slyther.network.message.fromserver.MessageUpdateMap;
import net.gegy1000.slyther.network.message.fromserver.MessageUpdateSnake;
import net.gegy1000.slyther.network.message.fromserver.MessageUpdateSnakeLength;
import net.gegy1000.slyther.util.Log;

public enum MessageHandler {
	INSTANCE;

//	private static boolean LOG_NETWORK = false;
	private final Map<Byte, Class<? extends SlytherServerMessageBase>> SERVER_MESSAGES = new HashMap<>();

	MessageHandler() {
		registerServer(MessagePreLogin.class);
		registerServer(MessagePing.class);
		registerServer(MessageSetup.class);
		registerServer(MessageNewSnake.class);
		registerServer(MessagePopulateSector.class);
		registerServer(MessageNewFood.class);
		registerServer(MessageNewPrey.class);
		registerServer(MessageUpdateSnake.class);
		registerServer(MessageSnakeMovement.class);
		registerServer(MessagePreyPositionUpdate.class);
		registerServer(MessageUpdateSnakeLength.class);
		registerServer(MessageRemoveFood.class);
		registerServer(MessageRemoveSnakePoint.class);
		registerServer(MessageUpdateLeaderboard.class);
		registerServer(MessageUpdateLongestPlayer.class);
		registerServer(MessageAddSector.class);
		registerServer(MessageRemoveSector.class);
		registerServer(MessagePlayerDeath.class);
		registerServer(MessageUpdateMap.class);
		registerServer(MessageGotServerVersion.class);
		registerServer(MessageKill.class);

	}

	private void registerServer(Class<? extends SlytherServerMessageBase> message) {
		try {
			SlytherServerMessageBase messageObject = message.getConstructor().newInstance();
			for (int id : messageObject.getMessageIds()) {
				SERVER_MESSAGES.put((byte) id, message);
			}
		} catch (Exception e) {
			Log.error("Error while registering message {}", message.getName());
			Log.catching(e);
		}
	}


	public Class<? extends SlytherServerMessageBase> getServerMessage(byte id) {
		return SERVER_MESSAGES.get(id);
	}

}
