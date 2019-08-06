/**
 * 
 */
package net.gegy1000.slyther.network.message;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.network.MessageByteBuffer;

/**
 * @author dick
 *
 */
public abstract class MessageFromServerBase implements Message {
	public byte messageId;
	public int serverTimeDelta;

	public abstract void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager);

	public abstract int[] getMessageIds();

	public int getSendMessageId() {
		return getMessageIds()[0];
	}
    
}
