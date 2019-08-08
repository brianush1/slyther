package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.SlytherServerMessageBase;

public class MessageNewPreyBase extends SlytherServerMessageBase {

	public MessageNewPreyBase() {
	}


	@Override
	final public int[] getMessageIds() {
		return new int[] { 'y' };
	}
}
