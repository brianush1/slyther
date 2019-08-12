package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.SlytherServerMessageBase;

public class MessageUpdateLongestPlayerBase extends SlytherServerMessageBase {

	@Override
	final public int[] getMessageIds() {
		return new int[] { 'm' };
	}
}
