package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherServerServerMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;

public class MessageSetup extends SlytherServerServerMessageBase {
	@Override
	public void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
		buffer.writeUInt24(server.configuration.gameRadius);
		buffer.writeUInt16(server.configuration.mscps);
		buffer.writeUInt16(server.configuration.sectorSize);
		buffer.writeUInt16(server.configuration.sectorsAlongEdge);
		buffer.writeUInt8((int) (server.configuration.spangDv * 10));
		buffer.writeUInt16((int) (server.configuration.nsp1 * 100));
		buffer.writeUInt16((int) (server.configuration.nsp2 * 100));
		buffer.writeUInt16((int) (server.configuration.nsp3 * 100));
		buffer.writeUInt16((int) (server.configuration.snakeTurnSpeed * 1000));
		buffer.writeUInt16((int) (server.configuration.preyTurnSpeed * 1000));
		buffer.writeUInt16((int) (server.configuration.cst * 1000));
		buffer.writeUInt8(client.protocolVersion);
	}

	@Override
	final public int[] getMessageIds() {
		return new int[] { 'a' };
	}
}
