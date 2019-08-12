package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.game.entity.Food;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherServerServerMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;

public class MessageNewFood extends SlytherServerServerMessageBase {
	private Food food;

	public MessageNewFood() {
	}

	public MessageNewFood(Food food) {
		this.food = food;
	}

	@Override
	public void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
		buffer.writeUInt8(food.color.ordinal());
		buffer.writeUInt16((int) food.posX + server.configuration.gameRadius);
		buffer.writeUInt16((int) food.posY + server.configuration.gameRadius);
		buffer.writeUInt8((int) (food.size * 5.0F));
	}


	@Override
	final public int[] getMessageIds() {
		return new int[] { 'b', 'f' };
	}

	@Override
	public int getSendMessageId() {
		return food.isNatural ? 'f' : 'b';
	}
}
