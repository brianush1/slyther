/**
 * 
 */
package net.gegy1000.slyther.network.message;

import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.server.SlytherServer;

/**
 * @author Dick Balaska
 *
 */
public abstract class SlytherMonitorMessageBase implements Message {
	/** Monitor write messages builds the buffer and then calls write */
	public MessageByteBuffer buffer;

	public abstract void write(SlytherServer server);

	public abstract void read(MessageByteBuffer buffer, SlytherServer server);

	public abstract int[] getMessageIds();

	public int getSendMessageId() {
		return getMessageIds()[0];
	}

}
