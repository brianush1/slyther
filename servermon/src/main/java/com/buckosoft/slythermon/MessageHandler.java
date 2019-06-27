/**
 * 
 */
package com.buckosoft.slythermon;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buckosoft.slythermon.message.MessageBase;
import com.buckosoft.slythermon.message.MessageInfo;

/**
 * @author dick
 *
 */
public enum MessageHandler {
	INSTANCE;
    private final Logger log = LoggerFactory.getLogger(MessageHandler.class);
	private final Map<Byte, Class<? extends MessageBase>> MESSAGES = new HashMap<>();

	private MessageHandler() {
		registerMessage(MessageInfo.class);
	}

	
	private void registerMessage(Class<? extends MessageBase> message) {
		try {
			MessageBase messageObject = message.getConstructor().newInstance();
			for (int id : messageObject.getMessageIds()) {
				MESSAGES.put((byte) id, message);
			}
		} catch (Exception ex) {
			log.error("Error while registering message " + message.getName(), ex);
		}
	}

	public MessageBase getMessage(byte id) {
		Class<? extends MessageBase> messageType = MESSAGES.get(id);
		if (messageType == null)
			return(null);
		MessageBase message;
		try {
			message = messageType.getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return message;
	}
	
}
