package net.gegy1000.slyther.network.message.fromserver;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.client.game.entity.ClientPrey;
import net.gegy1000.slyther.game.entity.Snake;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.server.MessagePreyPositionUpdateBase;

public class MessagePreyPositionUpdate extends MessagePreyPositionUpdateBase implements MessageFromServer {

	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		int id = buffer.readUInt16();
		int x = buffer.readUInt16() * 3 + 1;
		int y = buffer.readUInt16() * 3 + 1;
		ClientPrey prey = client.getPrey(id);
		if (prey != null) {
			float moveAmount = (client.errorTime / 8.0F * prey.speed / 4.0F) * client.lagMultiplier;
			float prevX = prey.posX;
			float prevY = prey.posY;
			if (buffer.hasExactlyRemaining(9)) {
				prey.turningDirection = buffer.readUInt8() - 48;
				prey.angle = (float) (buffer.readUInt24() * Math.PI * 2.0F / 0xFFFFFF);
				prey.wantedAngle = (float) (buffer.readUInt24() * Math.PI * 2.0F / 0xFFFFFF);
				prey.speed = buffer.readUInt16() / 1000.0F;
			} else if (buffer.hasExactlyRemaining(5)) {
				prey.angle = (float) (buffer.readUInt24() * Math.PI * 2.0F / 0xFFFFFF);
				prey.speed = buffer.readUInt16() / 1000.0F;
			} else if (buffer.hasExactlyRemaining(6)) {
				prey.turningDirection = buffer.readUInt8() - 48;
				prey.wantedAngle = (float) (buffer.readUInt24() * Math.PI * 2.0F / 0xFFFFFF);
				prey.speed = buffer.readUInt16() / 1000.0F;
			} else if (buffer.hasExactlyRemaining(7)) {
				prey.turningDirection = buffer.readUInt8() - 48;
				prey.angle = (float) (buffer.readUInt24() * Math.PI * 2.0F / 0xFFFFFF);
				prey.wantedAngle = (float) (buffer.readUInt24() * Math.PI * 2.0F / 0xFFFFFF);
			} else if (buffer.hasExactlyRemaining(3)) {
				prey.angle = (float) (buffer.readUInt24() * Math.PI * 2.0F / 0xFFFFFF);
			} else if (buffer.hasExactlyRemaining(4)) {
				prey.turningDirection = buffer.readUInt8() - 48;
				prey.wantedAngle = (float) (buffer.readUInt24() * Math.PI * 2.0F / 0xFFFFFF);
			} else if (buffer.hasExactlyRemaining(2)) {
				prey.speed = buffer.readUInt16() / 1000.0F;
			}
			prey.posX = (float) (x + Math.cos(prey.angle) * moveAmount);
			prey.posY = (float) (y + Math.sin(prey.angle) * moveAmount);
			prey.prevPosX = prey.posX;
			prey.prevPosY = prey.posY;
			prey.renderX = prey.posX;
			prey.renderY = prey.posY;
			float moveX = prey.posX - prevX;
			float moveY = prey.posY - prevY;
			int fpos = prey.fpos;
			for (int i = 0; i < Snake.RFC; i++) {
				prey.fxs[fpos] -= moveX * Snake.RFAS[i];
				prey.fys[fpos] -= moveY * Snake.RFAS[i];
				fpos++;
				if (fpos >= Snake.RFC) {
					fpos = 0;
				}
			}
			prey.fx = prey.fxs[prey.fpos];
			prey.fy = prey.fys[prey.fpos];
			prey.prevFx = prey.fx;
			prey.prevFy = prey.fy;
			prey.ftg = Snake.RFC;
		}
	}

}
