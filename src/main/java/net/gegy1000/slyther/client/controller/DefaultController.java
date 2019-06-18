package net.gegy1000.slyther.client.controller;


import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.client.game.entity.ClientSnake;

public class DefaultController implements IController {
    private float targetAngle;

    private int lastMouseX;
    private int lastMouseY;

	@Override
	public void update(SlytherClient client) {
		ClientSnake player = client.player;

		int mouseX = (int)client.mouseX - client.frameBufferWidth / 2;
		int mouseY = client.frameBufferHeight - (int)client.mouseY - client.frameBufferHeight/2;
		if (mouseX != lastMouseX || mouseY != lastMouseY) {
			lastMouseX = mouseX;
			lastMouseY = mouseY;
			int dist = mouseX * mouseX + mouseY * mouseY;
			if (dist > 256) {
				targetAngle = (float) Math.atan2(mouseY, mouseX);
				player.eyeAngle = targetAngle;
			} else {
				targetAngle = player.wantedAngle;
			}
		}
    }

	@Override
	public float getTargetAngle() {
		return targetAngle;
	}

}
