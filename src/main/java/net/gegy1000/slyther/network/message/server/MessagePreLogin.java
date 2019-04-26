/**
 * 
 */
package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherServerMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;

/**
 * @author Dick Balaska
 *
 */
public class MessagePreLogin extends SlytherServerMessageBase {

	/* (non-Javadoc)
	 * @see net.gegy1000.slyther.network.message.SlytherServerMessageBase#write(net.gegy1000.slyther.network.MessageByteBuffer, net.gegy1000.slyther.server.SlytherServer, net.gegy1000.slyther.server.ConnectedClient)
	 */
	@Override
	public void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
		// TODO Auto-generated method stub
		client.send(new MessageRiddle());

	}

	/* (non-Javadoc)
	 * @see net.gegy1000.slyther.network.message.SlytherServerMessageBase#read(net.gegy1000.slyther.network.MessageByteBuffer, net.gegy1000.slyther.client.SlytherClient, net.gegy1000.slyther.client.ClientNetworkManager)
	 */
	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.gegy1000.slyther.network.message.SlytherServerMessageBase#getMessageIds()
	 */
	@Override
	public int[] getMessageIds() {
        return new int[] { 'c' };
	}

}
