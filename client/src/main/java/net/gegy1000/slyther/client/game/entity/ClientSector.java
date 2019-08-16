package net.gegy1000.slyther.client.game.entity;

import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.game.entity.Sector;
import net.gegy1000.slyther.network.Connection;

public class ClientSector extends Sector<SlytherClient> {
    public ClientSector(SlytherClient game, int posX, int posY) {
        super(game, posX, posY);
    }

    @Override
    public void update(float delta, float lastDelta, float lastDelta2) {

    }

	@Override
	public void startTracking(Connection tracker) {
	}

	@Override
	public void stopTracking(Connection tracker) {
	}
    
}
