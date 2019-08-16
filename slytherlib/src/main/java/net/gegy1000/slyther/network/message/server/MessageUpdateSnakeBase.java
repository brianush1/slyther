package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.game.entity.Snake;
import net.gegy1000.slyther.network.SlytherServerMessageBase;

//10 per second
public class MessageUpdateSnakeBase extends SlytherServerMessageBase {
	protected Snake snake;
	protected boolean turnDirection;
	protected boolean angle;
	protected boolean wantedAngle;
	protected boolean speed;

	public MessageUpdateSnakeBase() {
	}


	@Override
	final public int[] getMessageIds() {
		return new int[] { 'e', 'E', '3', '4', '5' };
	}

	@Override
	final public int getSendMessageId() {
		if (turnDirection) {
			if (snake.turnDirection != 1) {
				return !(angle || wantedAngle) ? '5' : '4';
			}
		}
		return 'e';
	}
}
