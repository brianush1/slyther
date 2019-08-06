package net.gegy1000.slyther.server.game.entity;

import net.gegy1000.slyther.game.Color;
import net.gegy1000.slyther.game.entity.Food;
import net.gegy1000.slyther.network.Connection;
import net.gegy1000.slyther.network.message.server.MessageNewFood;
import net.gegy1000.slyther.network.message.server.MessageRemoveFood;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.SlytherServer;

public class ServerFood extends Food<SlytherServer> {
    public ServerFood(SlytherServer game, int posX, int posY, float size, boolean isNatural, Color color) {
        super(game, posX, posY, size, isNatural, color);
    }

    @Override
    public boolean update(float delta, float lastDelta, float lastDelta2) {
        return false;
    }

	@Override
	public void startTracking(Connection tracker) {
		ConnectedClient t = (ConnectedClient) tracker;
        t.send(new MessageNewFood(this));
		
	}

	@Override
	public void stopTracking(Connection tracker) {
		ConnectedClient t = (ConnectedClient) tracker;
        t.send(new MessageRemoveFood(this));
		
	}
}
