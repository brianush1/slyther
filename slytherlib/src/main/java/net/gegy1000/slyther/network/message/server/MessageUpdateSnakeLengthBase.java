package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.SlytherServerMessageBase;

public class MessageUpdateSnakeLengthBase extends SlytherServerMessageBase {

	public MessageUpdateSnakeLengthBase() {
	}


	@Override
	final public int[] getMessageIds() {
		return new int[] { 'h' };
	}
}
