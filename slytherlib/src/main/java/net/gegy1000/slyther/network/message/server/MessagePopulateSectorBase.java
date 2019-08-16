package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.SlytherServerMessageBase;

public class MessagePopulateSectorBase extends SlytherServerMessageBase {

	public MessagePopulateSectorBase() {
	}


	@Override
	final public int[] getMessageIds() {
		return new int[] { 'F' };
	}
}
