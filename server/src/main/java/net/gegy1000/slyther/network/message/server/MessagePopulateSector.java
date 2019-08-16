package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.game.entity.Food;
import net.gegy1000.slyther.game.entity.Sector;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherServerServerMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;
import net.gegy1000.slyther.server.game.entity.ServerSector;

public class MessagePopulateSector extends SlytherServerServerMessageBase {
	private Sector<?> sector;

	public MessagePopulateSector() {
	}

	public MessagePopulateSector(Sector<?> sector) {
		this.sector = sector;
	}

	@Override
	public void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
		int gameRadius = server.configuration.gameRadius;
		ServerSector sector = (ServerSector) this.sector;
		for (Food food : sector.foods) {
			buffer.writeUInt8(food.color.ordinal());
			buffer.writeUInt16((int) food.posX + gameRadius);
			buffer.writeUInt16((int) food.posY + gameRadius);
			buffer.writeUInt8((int) food.size * 5);
		}
	}

	@Override
	final public int[] getMessageIds() {
		return new int[] { 'F' };
	}
}
