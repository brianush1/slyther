package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.SlytherServerMessageBase;

public class MessageNewSnakeBase extends SlytherServerMessageBase {
	public MessageNewSnakeBase() {
	}


	@Override
	final public int[] getMessageIds() {
		return new int[] { 's' };
	}
}
