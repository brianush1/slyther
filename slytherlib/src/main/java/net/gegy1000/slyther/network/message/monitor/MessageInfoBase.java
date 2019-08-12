/**
 * 
 */
package net.gegy1000.slyther.network.message.monitor;

import net.gegy1000.slyther.network.SlytherMonitorMessageBase;

/**
 * @author Dick Balaska
 *
 */
public class MessageInfoBase extends SlytherMonitorMessageBase {
	
	public class Info {
		public int connectedClients;
	}

    @Override
    final public int[] getMessageIds() {
        return new int[] { 'i' };
    }

}
