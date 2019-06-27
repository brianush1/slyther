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
public class MessageInfo extends MessageBase {
	static final int[] ids = new int[] {'i'};

	public MessageInfo() {
	}
	
	public MessageInfo(Main main) {
		write(main);
	}

	@Override
	public void write(Main main) {
		buffer = new MessageByteBuffer(1);
		buffer.writeUInt8(getSendMessageId());
	}

	@Override
	public void read(MessageByteBuffer buffer, Main main) {
		String s = "" + buffer.readUInt16();
		main.getMainFrame().getConnectedClients().setText(s);
		
	}

	@Override
	public int[] getMessageIds() {
		return ids;
	}

}
