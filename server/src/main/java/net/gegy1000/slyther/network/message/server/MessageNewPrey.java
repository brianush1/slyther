package net.gegy1000.slyther.network.message.server;

import net.gegy1000.slyther.game.entity.Prey;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.network.message.SlytherServerServerMessageBase;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;

public class MessageNewPrey extends SlytherServerServerMessageBase {
	@SuppressWarnings("unused")
	private Prey<?> prey;

	public MessageNewPrey(Prey<?> prey) {
		this.prey = prey;
	}

	public MessageNewPrey() {
	}

	@Override
	public void write(MessageByteBuffer buffer, SlytherServer server, ConnectedClient client) {
		//TODO
	}


	@Override
	final public int[] getMessageIds() {
		return new int[] { 'y' };
	}
}
