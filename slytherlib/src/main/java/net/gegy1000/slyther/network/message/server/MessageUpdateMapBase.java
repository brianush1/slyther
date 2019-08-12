package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.SlytherServerMessageBase;

public class MessageUpdateMapBase extends SlytherServerMessageBase {

	@Override
	final public int[] getMessageIds() {
		return new int[] { 'u' };
	}
}
