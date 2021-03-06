/**
 * 
 */
package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherServerServerMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;

/**
 * @author Dick Balaska
 *
 */
public class MessageRiddle extends SlytherServerServerMessageBase {
	String sriddle = "euLXuGxMJVOpvrgWOHWjDFlMtLDtkdTiCQJgReYrGSpMvxFdlbvIDuKEtjbwHaNjwVhYP"
				   + "PYKBQjGWMdPmYPeBvGHOcWvhbmmUkCuKjvpZzHhpncxJdRGZMhtolXreDkLVhzcl"
				   + "ztMbNkWpFZZHWPFvJgUjgvPzxIhuR";
	byte[] riddle;

	/* (non-Javadoc)
	 * @see net.gegy1000.slyther.network.message.SlytherServerMessageBase#write(net.gegy1000.slyther.network.MessageByteBuffer, net.gegy1000.slyther.server.SlytherServer, net.gegy1000.slyther.server.ConnectedClient)
	 */
	@Override
	public void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
		for (char i : sriddle.toCharArray()) {
			buffer.writeUInt8(i);
		}

	}


	/* (non-Javadoc)
	 * @see net.gegy1000.slyther.network.message.SlytherServerMessageBase#getMessageIds()
	 */
	@Override
	final public int[] getMessageIds() {
		return new int[] { '6' };
	}

}
