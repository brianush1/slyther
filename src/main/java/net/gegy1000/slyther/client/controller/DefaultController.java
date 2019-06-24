package net.gegy1000.slyther.client.controller;


import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.client.game.entity.ClientSnake;

public class DefaultController implements IController {

    private int lastMouseX;
    private int lastMouseY;
    private final static int BOXSIZE = 40;

    private int boxX = BOXSIZE;
    private int boxY = 0;
    private float targetAngle = (float) Math.atan2(boxY, boxX);
    
	@Override
	public void update(SlytherClient client) {
		double sensitivity = 4.0;
		ClientSnake player = client.player;

		int mouseX = (int)client.mouseX - client.frameBufferWidth / 2;
		int mouseY = client.frameBufferHeight - (int)client.mouseY - client.frameBufferHeight/2;
		mouseX /= sensitivity;
		mouseY /= sensitivity;
		if (mouseX != lastMouseX || mouseY != lastMouseY) {
			if (mouseX < lastMouseX) {
				boxX--;
				if (boxX < -BOXSIZE)
					boxX = -BOXSIZE;
			} else if (mouseX > lastMouseX) {
				boxX++;
				if (boxX > BOXSIZE)
					boxX = BOXSIZE;
			}
			if (mouseY < lastMouseY) {
				boxY--;
				if (boxY < -BOXSIZE)
					boxY = -BOXSIZE;
			} else if (mouseY > lastMouseY) {
				boxY++;
				if (boxY > BOXSIZE)
					boxY = BOXSIZE;
			}
			
			lastMouseX = mouseX;
			lastMouseY = mouseY;
			int dist = mouseX * mouseX + mouseY * mouseY;
			if (dist > 128) {
				targetAngle = (float) Math.atan2(mouseY, mouseX);
				player.eyeAngle = targetAngle;
			} else {
				targetAngle = player.wantedAngle;
			}
			//targetAngle = (float) Math.atan2(boxY, boxX);
		}
    }

	@Override
	public float getTargetAngle() {
		return targetAngle;
	}

	@Override
	public int getBoxX() {
		return(boxX);
	}

	@Override
	public int getBoxY() {
		return(boxY);
	}

	@Override
	public int getMouseX() {
		return(lastMouseX);
	}

	@Override
	public int getMouseY() {
		return(lastMouseY);
	}

}
