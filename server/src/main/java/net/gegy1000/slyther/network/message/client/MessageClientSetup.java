package net.gegy1000.slyther.network.message.client;

import net.gegy1000.slyther.game.ProfanityHandler;
import net.gegy1000.slyther.game.Skin;
import net.gegy1000.slyther.game.SkinEnum;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherClientMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;
import net.gegy1000.slyther.util.Log;

public class MessageClientSetup extends SlytherClientMessageBase {
//	private String username;
//	private Skin skin;

	public MessageClientSetup() {
	}

//	public MessageClientSetup(String username, Skin skin) {
//		this.username = username;
//		this.skin = skin;
//		if (this.username.length() > 24) {
//			this.username = this.username.substring(0, 24);
//		}
//		if (!ProfanityHandler.isClean(this.username)) {
//			this.username = "";
//		}
//	}

	@Override
	public void read(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
		int protocolVersion = buffer.readUInt8() + 1;
		Skin skin = SkinEnum.values()[buffer.readUInt8() % SkinEnum.values().length];
		String name = buffer.readSizedString();
		if (!ProfanityHandler.isClean(name)) {
			name = "";
		}
		client.setup(name, skin, protocolVersion);
		Log.debug("{} ({}) connected with skin {} protocol {}", client.name, client.id, client.skin, protocolVersion);
	}
}