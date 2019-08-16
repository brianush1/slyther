package net.gegy1000.slyther.server.game.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.gegy1000.slyther.game.Color;
import net.gegy1000.slyther.game.entity.Entity;
import net.gegy1000.slyther.game.entity.Food;
import net.gegy1000.slyther.game.entity.Sector;
import net.gegy1000.slyther.network.Connection;
import net.gegy1000.slyther.network.message.server.MessageAddSector;
import net.gegy1000.slyther.network.message.server.MessagePopulateSector;
import net.gegy1000.slyther.network.message.server.MessageRemoveSector;
import net.gegy1000.slyther.server.ConnectedClient;
import net.gegy1000.slyther.server.ServerConfig;
import net.gegy1000.slyther.server.SlytherServer;

public class ServerSector extends Sector<SlytherServer> {
    public List<Food> foods = new ArrayList<>();
    public long lastSpawnTime;
    public boolean populated;

    public ServerSector(SlytherServer game, int posX, int posY) {
        super(game, posX, posY);
    }

    public void populateSector() {
        int sectorSize = game.getSectorSize();
        int sectorX = posX * sectorSize;
        int sectorY = posY * sectorSize;
        Random rng = game.rng;
        ServerConfig configuration = game.configuration;
        for (int i = 0; i < rng.nextInt(configuration.maxSpawnFoodPerSector); i++) {
            int posX = sectorX + rng.nextInt(sectorSize);
            int posY = sectorY + rng.nextInt(sectorSize);
            int size = rng.nextInt(configuration.maxNaturalFoodSize - configuration.minNaturalFoodSize) + configuration.minNaturalFoodSize;
            Color color = Color.values()[rng.nextInt(9)];
            addFood(new ServerFood(game, posX, posY, size, true, color));
        }
        lastSpawnTime = System.currentTimeMillis();
    }

    @Override
    public void update(float delta, float lastDelta, float lastDelta2) {
        if (foods.size() < game.configuration.maxSpawnFoodPerSector) {
            if (System.currentTimeMillis() - lastSpawnTime > game.configuration.respawnFoodDelay) {
                int sectorSize = game.getSectorSize();
                int sectorX = posX * sectorSize;
                int sectorY = posY * sectorSize;
                int posX = sectorX + game.rng.nextInt(sectorSize);
                int posY = sectorY + game.rng.nextInt(sectorSize);
                int size = game.rng.nextInt(game.configuration.maxNaturalFoodSize - game.configuration.minNaturalFoodSize) + game.configuration.minNaturalFoodSize;
                Color color = Color.values()[game.rng.nextInt(Color.values().length)];
                addFood(new ServerFood(game, posX, posY, size, true, color));
            }
        }
    }

    public void addFood(Food food) {
        if (!foods.contains(food)) {
            foods.add(food);
            game.addEntity(food);
        }
    }

    public void removeFood(Food food) {
        foods.remove(food);
        game.removeEntity(food);
    }

    public boolean shouldTrack(ConnectedClient client) {
        int sectorSize = ((SlytherServer) game).configuration.sectorSize;
        float deltaX = (posX * sectorSize) + (sectorSize / 2.0F) - client.snake.posX;
        float deltaY = (posY * sectorSize) + (sectorSize / 2.0F) - client.snake.posY;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY) <= client.viewDistance;
    }

    public void startTracking(ConnectedClient tracker) {
        if (!populated) {
            populateSector();
            populated = true;
        }
        tracker.send(new MessageAddSector(this));
        tracker.send(new MessagePopulateSector(this));
        for (Entity entity : game.getEntities()) {
            int sectorX = (int) (entity.posX / game.getSectorSize());
            int sectorY = (int) (entity.posY / game.getSectorSize());
            if (sectorX == posX && sectorY == posY) {
                if (entity instanceof Food) {
                    tracker.trackingEntities.add(entity);
                } else {
                    tracker.track(entity);
                }
            }
        }
    }

    public void stopTracking(ConnectedClient tracker) {
        tracker.send(new MessageRemoveSector(this));
        for (Entity entity : game.getEntities()) {
            int sectorX = (int) (entity.posX / game.getSectorSize());
            int sectorY = (int) (entity.posY / game.getSectorSize());
            if (sectorX == posX && sectorY == posY) {
                if (entity instanceof Food) {
                    tracker.trackingEntities.remove(entity);
                } else {
                    tracker.untrack(entity);
                }
            }
        }
    }

	@Override
	public void startTracking(Connection tracker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopTracking(Connection tracker) {
		// TODO Auto-generated method stub
		
	}
    
}
