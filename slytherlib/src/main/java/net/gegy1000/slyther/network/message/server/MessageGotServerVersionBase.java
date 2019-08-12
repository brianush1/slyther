package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.SlytherServerMessageBase;

public class MessageGotServerVersionBase extends SlytherServerMessageBase {

	public MessageGotServerVersionBase() {
	}

	@Override
	final public int[] getMessageIds() {
		return new int[] { '6' };
	}
}
