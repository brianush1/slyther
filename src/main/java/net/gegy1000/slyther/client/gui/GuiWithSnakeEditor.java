/**
 * 
 */
package net.gegy1000.slyther.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.client.game.entity.ClientSnake;
import net.gegy1000.slyther.game.entity.SnakePoint;

/**
 * @author dick
 *
 */
public abstract class GuiWithSnakeEditor extends GuiWithBanner {
	protected ClientSnake snake;

    protected void createSnake() {
        List<SnakePoint> points = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            SnakePoint point = new SnakePoint(client, i * 10.0F, 0.0F);
            point.deltaX = i == 0 ? 0.0F : 10.0F;
            points.add(point);
        }
        snake = new ClientSnake(client, "", 0, points.get(points.size() - 1).posX, 0.0F, client.configuration.skin, 0.0F, points);
        snake.speed = 4.8F;
        snake.speedTurnMultiplier = snake.speed / client.getSpangDv();
        if (snake.speedTurnMultiplier > 1) {
            snake.speedTurnMultiplier = 1;
        }
        snake.scale = 1.0F;
        snake.scaleTurnMultiplier = 1.0F;
        snake.moveSpeed = client.getNsp1() + client.getNsp2() * snake.scale;
        snake.accelleratingSpeed = snake.moveSpeed + 0.1F;
        snake.wantedSeperation = snake.scale * 6.0F;
        float nsep = SlytherClient.NSEP;
        if (snake.wantedSeperation < nsep) {
            snake.wantedSeperation = nsep;
        }
        snake.partSeparation = snake.wantedSeperation;
        snake.updateLength();
        snake.aliveAmt = 1.0F;
        snake.relativeEyeX = 1.66F;
        update();
    }

    protected void drawSnake() {
    	
    }
}
