package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.SlytherServerMessageBase;

public class MessageRemoveSnakePointBase extends SlytherServerMessageBase {

	@Override
	final public int[] getMessageIds() {
		return new int[] { 'r' };
	}
}
