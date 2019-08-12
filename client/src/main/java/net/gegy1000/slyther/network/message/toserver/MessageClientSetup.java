package net.gegy1000.slyther.network.message.toserver;

import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.game.ProfanityHandler;
import net.gegy1000.slyther.game.Skin;
import net.gegy1000.slyther.game.SkinCustom;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherClientMessageBase;

public class MessageClientSetup extends SlytherClientMessageBase {
    private String username;
    private Skin skin;

    public MessageClientSetup() {
    }

    public MessageClientSetup(String username, Skin skin) {
        this.username = username;
        this.skin = skin;
        if (this.username.length() > 24) {
            this.username = this.username.substring(0, 24);
        }
        if (!ProfanityHandler.isClean(this.username)) {
            this.username = "";
        }
    }

	@Override
	public void write(MessageByteBuffer buffer, SlytherClient client) {
		buffer.writeUInt8('s');
		buffer.writeUInt8(11 - 1);
		buffer.writeUInt8(skin.ordinal());
		buffer.writeUInt8(username.length());
		buffer.writeASCIIBytes(username);
		if (skin.isCustom())
			buffer.writeBytes(((SkinCustom)skin).getColorsPacked());
		else
			buffer.writeUInt8(0);
	}

//	@Override
//	public void read(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
//		// apparantly unused?
//		int protocolVersion = buffer.readUInt8() + 1;
//		Skin skin = SkinEnum.values()[buffer.readUInt8() % SkinEnum.values().length];
//		String name = buffer.readASCIIBytes();
//		if (!ProfanityHandler.isClean(name)) {
//			name = "";
//		}
//		client.setup(name, skin, protocolVersion);
//		Log.debug("{} ({}) connected with skin {} protocol {}", client.name, client.id, client.skin, protocolVersion);
//	}
}