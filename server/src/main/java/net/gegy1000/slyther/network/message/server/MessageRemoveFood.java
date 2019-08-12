package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherServerServerMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;
import net.gegy1000.slyther.server.game.entity.ServerFood;

public class MessageRemoveFood extends SlytherServerServerMessageBase {
	private ServerFood food;

	public MessageRemoveFood() {
	}

	public MessageRemoveFood(ServerFood food) {
		this.food = food;
	}

	@Override
	public void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
		int gameRadius = server.configuration.gameRadius;
		buffer.writeUInt16((int) food.posX + gameRadius);
		buffer.writeUInt16((int) food.posY + gameRadius);
		if (food.eater != null) {
			buffer.writeUInt16(food.eater.id);
		}
	}


	@Override
	final public int[] getMessageIds() {
		return new int[] { 'c' };
	}
}
