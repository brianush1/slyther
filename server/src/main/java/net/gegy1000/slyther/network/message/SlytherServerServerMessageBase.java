/**
 * 
 */
package net.gegy1000.slyther.network.message;

import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.SlytherServerMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;

/**
 * @author dick
 *
 */
public abstract class SlytherServerServerMessageBase extends SlytherServerMessageBase {

    public abstract void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client);
	
}
