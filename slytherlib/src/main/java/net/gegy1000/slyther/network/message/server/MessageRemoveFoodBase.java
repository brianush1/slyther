package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.SlytherServerMessageBase;

public class MessageRemoveFoodBase extends SlytherServerMessageBase {

	public MessageRemoveFoodBase() {
	}


	@Override
	final public int[] getMessageIds() {
		return new int[] { 'c' };
	}
}
