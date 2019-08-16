package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.network.SlytherServerMessageBase;

//4 per second
public class MessageSnakeMovementBase extends SlytherServerMessageBase {
	protected boolean absolute;
	protected boolean updateLength;

	public MessageSnakeMovementBase() {
	}


	@Override
	public int[] getMessageIds() {
		return new int[] { 'g', 'n', 'G', 'N' };
	}

	@Override
	final public int getSendMessageId() {
		char id = updateLength ? 'n' : 'g';
		return absolute ? id : Character.toUpperCase(id);
	}
}
