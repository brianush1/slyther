/**
 * 
 */
package net.gegy1000.slyther.network.message.monitor;

import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherMonitorMessageBase;
import net.gegy1000.slyther.server.SlytherServer;

/**
 * @author Dick Balaska
 *
 */
public class MessageInfo extends SlytherMonitorMessageBase {
	
	public class Info {
		public int connectedClients;
	}

	public void write(SlytherServer server) {
		buffer = new MessageByteBuffer();
		buffer.writeUInt8(getSendMessageId());
		buffer.writeUInt16(server.clients.size());
	}

//	@Override
//	public void write() {
//		// TODO Auto-generated method stub
//
//	}

	@Override
	public void read(MessageByteBuffer buffer, SlytherServer server) {
	}
    @Override
    public int[] getMessageIds() {
        return new int[] { 'i' };
    }

}
