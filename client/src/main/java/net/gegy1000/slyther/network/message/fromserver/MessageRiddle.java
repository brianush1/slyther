/**
 * 
 */
package net.gegy1000.slyther.network.message.fromserver;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.MessageFromServerBase;

/**
 * @author Dick Balaska
 *
 */
public class MessageRiddle extends MessageFromServerBase implements MessageFromServer {

	/* (non-Javadoc)
	 * @see net.gegy1000.slyther.network.message.SlytherServerMessageBase#read(net.gegy1000.slyther.network.MessageByteBuffer, net.gegy1000.slyther.client.SlytherClient, net.gegy1000.slyther.client.ClientNetworkManager)
	 */
	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
	}

	/* (non-Javadoc)
	 * @see net.gegy1000.slyther.network.message.SlytherServerMessageBase#getMessageIds()
	 */
	@Override
	public int[] getMessageIds() {
		return new int[] { '6' };
	}

}
