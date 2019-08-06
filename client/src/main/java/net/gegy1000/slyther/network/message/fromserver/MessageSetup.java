package net.gegy1000.slyther.network.message.fromserver;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.server.MessageSetupBase;

public class MessageSetup extends MessageSetupBase implements MessageFromServer {

	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		int gameRadius = buffer.readUInt24();
		int mscps = buffer.readUInt16();
		int sectorSize = buffer.readUInt16();
		int sectorCountAlongEdge = buffer.readUInt16();
		float spangDV = buffer.readUInt8() / 10.0F;
		float nsp1 = buffer.readUInt16() / 100.0F;
		float nsp2 = buffer.readUInt16() / 100.0F;
		float nsp3 = buffer.readUInt16() / 100.0F;
		float mamu = buffer.readUInt16() / 1000.0F;
		float manu2 = buffer.readUInt16() / 1000.0F;
		float cst = buffer.readUInt16() / 1000.0F;
		int protocolVersion = buffer.readUInt8() + 1;

		client.setup(gameRadius, mscps, sectorSize, sectorCountAlongEdge, spangDV, nsp1, nsp2, nsp3, mamu, manu2, cst, protocolVersion);
	}

}
