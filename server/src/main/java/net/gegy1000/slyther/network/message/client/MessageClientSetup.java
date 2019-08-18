package net.gegy1000.slyther.network.message.client;

import net.gegy1000.slyther.game.ProfanityHandler;
import net.gegy1000.slyther.game.Skin;
import net.gegy1000.slyther.game.SkinCustom;
import net.gegy1000.slyther.game.SkinEnum;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherClientMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;
import net.gegy1000.slyther.util.Log;

public class MessageClientSetup extends SlytherClientMessageBase {

	public MessageClientSetup() {
	}

	@Override
	public void read(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
		int protocolVersion = buffer.readUInt8() + 1;
		Skin skin = SkinEnum.values()[buffer.readUInt8() % SkinEnum.values().length];
		String name = buffer.readSizedString();
		if (!ProfanityHandler.isClean(name)) {
			name = "";
		}
		if (buffer.hasRemaining()) {
			byte[] colors = buffer.readBytes(buffer.remaining());
			skin = new SkinCustom();
			((SkinCustom)skin).setPackedColors(colors);
		}
		client.setup(name, skin, protocolVersion);
		Log.debug("{} ({}) connected with skin {} protocol {}", client.name, client.id, client.skin, protocolVersion);
	}
}