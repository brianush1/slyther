package net.gegy1000.slyther.network.message.fromserver;

import java.util.ArrayList;
import java.util.List;

import net.gegy1000.slyther.client.ClientNetworkManager;
import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.client.game.entity.ClientSnake;
import net.gegy1000.slyther.game.ProfanityHandler;
import net.gegy1000.slyther.game.Skin;
import net.gegy1000.slyther.game.SkinCustom;
import net.gegy1000.slyther.game.SkinEnum;
import net.gegy1000.slyther.game.entity.Snake;
import net.gegy1000.slyther.game.entity.SnakePoint;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.MessageFromServer;
import net.gegy1000.slyther.network.message.server.MessageNewSnakeBase;
import net.gegy1000.slyther.util.Log;

public class MessageNewSnake extends MessageNewSnakeBase implements MessageFromServer {
	private final static boolean DEBUG = false;

    public MessageNewSnake() {
    }



	@Override
	public void read(MessageByteBuffer buffer, SlytherClient client, ClientNetworkManager networkManager) {
		int id = buffer.readUInt16();
		if (buffer.hasExactlyRemaining(1)) {
			boolean dead = buffer.readUInt8() == 1;
			Snake<?> snake = client.getSnake(id);
			if (snake != null) {
				if (DEBUG)
					Log.debug("Remove snake {} \"{}\" with skin {}, dead {}", snake.id, snake.name, snake.skin, dead);
				if (dead) {
					snake.dead = true;
					snake.deadAmt = 0;
					snake.edir = 0;
				} else {
					client.removeEntity(snake);
				}
			}
			return;
		}
		float angle = (float) (buffer.readUInt24() * (2 * Math.PI / 0xFFFFFF));
		buffer.skipBytes(1);
		float wantedAngle = (float) (buffer.readUInt24() * (2 * Math.PI / 0xFFFFFF));
		float speed = buffer.readUInt16() / 1000.0F;
		double fam = (double) buffer.readUInt24() / 0xFFFFFF;
		int ski = buffer.readUInt8();
		Skin skin = SkinEnum.values()[ski < SkinEnum.values().length ? ski : 0];
		float x = buffer.readUInt24() / 5.0F;
		float y = buffer.readUInt24() / 5.0F;
		String name = "";
		int nameLength = buffer.readUInt8();
		for (int i = 0; i < nameLength; i++) {
			name += (char) buffer.readUInt8();
		}
		int skinLength = buffer.readUInt8();
		if (DEBUG)
			Log.debug("Snake {} \"{}\" skinLength={}", id, name, skinLength);
		if (skinLength > 0) {
			byte[] sarray = buffer.readBytes(skinLength);
			SkinCustom skinCustom = new SkinCustom();
			skinCustom.setPackedColors(sarray);
			skin = skinCustom;
			
		}
		float prevPointX;
		float prevPointY;
		float pointX = 0;
		float pointY = 0;
		List<SnakePoint> points = new ArrayList<>();
		while (buffer.hasRemaining(2)) {
			prevPointX = pointX;
			prevPointY = pointY;
			if (points.isEmpty()) {
				pointX = buffer.readUInt24() / 5.0F;
				pointY = buffer.readUInt24() / 5.0F;
				prevPointX = pointX;
				prevPointY = pointY;
			} else {
				pointX += (buffer.readUInt8() - 127) / 2.0F;
				pointY += (buffer.readUInt8() - 127) / 2.0F;
			}
			SnakePoint point = new SnakePoint(client, pointX, pointY);
			point.deltaX = pointX - prevPointX;
			point.deltaY = pointY - prevPointY;
			points.add(point);
		}
		if (client.player == null) {
			name = client.configuration.nickname;
		} else {
			if (!ProfanityHandler.isClean(name)) {
				name = "";
			}
		}
		ClientSnake snake = new ClientSnake(client, name, id, x, y, skin, angle, points);
		if (client.player == null) {
			client.viewX = pointX;
			client.viewY = pointY;
			client.player = snake;
			snake.accelerating = false;
			snake.wasAccelerating = false;
		}
		snake.eyeAngle = snake.wantedAngle = wantedAngle;
		snake.speed = speed;
		snake.speedTurnMultiplier = speed / client.SPANG_DV;
		if (snake.speedTurnMultiplier > 1.0F) {
			snake.speedTurnMultiplier = 1.0F;
		}
		snake.fam = fam;
		snake.scale = Math.min(6.0F, 1.0F + (snake.sct - 2.0F) / 106.0F);
		snake.scaleTurnMultiplier = (float) (0.13F + 0.87F * Math.pow((7.0F - snake.scale) / 6.0F, 2.0F));
		snake.wantedSeperation = snake.scale * 6.0F;
		float max = Snake.NSEP / client.globalScale;
		if (snake.wantedSeperation < max) {
			snake.wantedSeperation = max;
		}
		snake.partSeparation = snake.wantedSeperation;

		if (DEBUG)
			Log.debug("Added snake \"{}\" with skin {} remaining {}", snake.name, snake.skin, buffer.remaining());
		if (buffer.remaining() != 0) {
			Log.info("****************** remaining: {}", buffer.remaining());
		}
		client.addEntity(snake);

		snake.updateLength();

	}
}
