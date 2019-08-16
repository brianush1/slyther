package net.gegy1000.slyther.game.entity;

import net.gegy1000.slyther.game.Game;
import net.gegy1000.slyther.network.Connection;

public abstract class Sector<GME extends Game<?, ?>> {
    public GME game;
    public int posX;
    public int posY;

    public Sector(GME game, int posX, int posY) {
        this.game = game;
        this.posX = posX;
        this.posY = posY;
    }

    public abstract void update(float delta, float lastDelta, float lastDelta2);


    @Override
    public boolean equals(Object o) {
        return o instanceof Sector && ((Sector) o).posX == posX && ((Sector) o).posY == posY;
    }
    
	public abstract void startTracking(Connection tracker);

	public abstract void stopTracking(Connection tracker);
    
}
