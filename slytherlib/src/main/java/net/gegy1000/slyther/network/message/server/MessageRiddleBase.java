/**
 * 
 */
package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.SlytherServerMessageBase;

/**
 * @author Dick Balaska
 *
 */
public class MessageRiddleBase extends SlytherServerMessageBase {


	/* (non-Javadoc)
	 * @see net.gegy1000.slyther.network.message.SlytherServerMessageBase#getMessageIds()
	 */
	@Override
	final public int[] getMessageIds() {
		return new int[] { '6' };
	}

}
