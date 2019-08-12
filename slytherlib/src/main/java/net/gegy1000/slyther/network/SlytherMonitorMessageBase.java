package net.gegy1000.slyther.network;

import net.gegy1000.slyther.network.message.Message;

/**
 * Message being sent to the monitor
 */
public abstract class SlytherMonitorMessageBase implements Message {
	public byte messageId;
	public int serverTimeDelta;


	public abstract int[] getMessageIds();

	public int getSendMessageId() {
		return getMessageIds()[0];
	}
}
