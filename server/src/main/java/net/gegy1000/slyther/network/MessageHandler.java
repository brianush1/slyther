package net.gegy1000.slyther.network;

import java.util.HashMap;
import java.util.Map;

import net.gegy1000.slyther.network.message.SlytherClientMessageBase;
import net.gegy1000.slyther.network.message.SlytherMonitorMessageBase;
import net.gegy1000.slyther.network.message.client.MessageAccelerate;
import net.gegy1000.slyther.network.message.client.MessageClientPing;
import net.gegy1000.slyther.network.message.client.MessageClientRiddleAnswer;
import net.gegy1000.slyther.network.message.client.MessageClientSetup;
import net.gegy1000.slyther.network.message.client.MessageSetAngle;
import net.gegy1000.slyther.network.message.client.MessageSetTurn;
import net.gegy1000.slyther.network.message.monitor.MessageInfo;
import net.gegy1000.slyther.util.Log;

public enum MessageHandler {
	INSTANCE;

	private static boolean LOG_NETWORK = false;
	private final Map<Byte, Class<? extends SlytherMonitorMessageBase>> MONITOR_MESSAGES = new HashMap<>();

	MessageHandler() {
		registerMonitor(MessageInfo.class);
	}

	private void registerMonitor(Class<? extends SlytherMonitorMessageBase> message) {
		try {
			SlytherMonitorMessageBase messageObject = message.getConstructor().newInstance();
			for (int id : messageObject.getMessageIds()) {
				MONITOR_MESSAGES.put((byte) id, message);
			}
		} catch (Exception e) {
			Log.error("Error while registering message {}", message.getName());
			Log.catching(e);
		}
	}

	public SlytherClientMessageBase getClientMessage(MessageByteBuffer buffer) {
		if (buffer.limit() == 1) {
			int type = buffer.readUInt8();
			if (LOG_NETWORK)
				Log.debug("Recv1: {}", type);
			if (type == 251) {
				return new MessageClientPing();
			} else if (type == 253 || type == 254) {
				return new MessageAccelerate(type == 253);
			} else {
				buffer.skipBytes(-1);
				return new MessageSetAngle();
			}
		} else if (buffer.limit() > 1) {
			int type = buffer.readUInt8();
			if (LOG_NETWORK)
				Log.debug("Recvm: {}", type);
			if (type == 's') {
				return new MessageClientSetup();
			} else if (type == 252) {
				return new MessageSetTurn();
			} else if (type == 79) {
				return(new MessageClientRiddleAnswer());
			}
		}
		return null;
	}

	public SlytherMonitorMessageBase getMonitorMessage(byte id) {
		Class<? extends SlytherMonitorMessageBase> messageType = MONITOR_MESSAGES.get(id);
		if (messageType == null)
			return(null);
		SlytherMonitorMessageBase message;
		try {
			message = messageType.getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return message;
	}
}
