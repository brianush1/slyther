/**
 * 
 */
package net.gegy1000.slyther.network;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;

/**
 * @author dick
 *
 */
public interface MessageFromServer {
    public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager);

}
