package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.SlytherServerMessageBase;

public class MessageAddSectorBase extends SlytherServerMessageBase {

	public MessageAddSectorBase() {
	}


	@Override
	final public int[] getMessageIds() {
		return new int[] { 'W' };
	}
}
