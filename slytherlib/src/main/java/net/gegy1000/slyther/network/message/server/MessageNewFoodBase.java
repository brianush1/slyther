package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.game.entity.Food;
import net.gegy1000.slyther.network.SlytherServerMessageBase;

public class MessageNewFoodBase extends SlytherServerMessageBase {
	private Food food;

	public MessageNewFoodBase() {
	}

	public MessageNewFoodBase(Food food) {
		this.food = food;
	}


	@Override
	final public int[] getMessageIds() {
		return new int[] { 'b', 'f' };
	}

	@Override
	final public int getSendMessageId() {
		return food.isNatural ? 'f' : 'b';
	}
}
