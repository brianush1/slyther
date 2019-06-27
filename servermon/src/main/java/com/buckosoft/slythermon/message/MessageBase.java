/**
 * 
 */
package com.buckosoft.slythermon.message;

import com.buckosoft.slythermon.Main;

import net.gegy1000.slyther.network.MessageByteBuffer;

/**
 * @author dick
 *
 */
public abstract class MessageBase {
	
	public MessageByteBuffer buffer;
	public abstract void write(Main main);

	public abstract void read(MessageByteBuffer buffer, Main main);

	public abstract int[] getMessageIds();

	public int getSendMessageId() {
		return getMessageIds()[0];
	}


}
