package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.SlytherServerMessageBase;

public class MessageSetupBase extends SlytherServerMessageBase {

	@Override
	final public int[] getMessageIds() {
		return new int[] { 'a' };
	}
}
