package net.gegy1000.slyther.client.gui;


import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.client.game.entity.ClientSnake;
import net.gegy1000.slyther.client.gui.element.ArrowElement;
import net.gegy1000.slyther.client.gui.element.ButtonElement;
import net.gegy1000.slyther.game.Skin;
import net.gegy1000.slyther.game.SkinEnum;
import net.gegy1000.slyther.game.entity.SnakePoint;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F3;

import java.util.ArrayList;
import java.util.List;


public class GuiSelectSkin extends GuiWithSnakeEditor {
	ClientSnake snake;

    @Override
    public void init() {
		elements.clear();
        snake = createSnake();
        elements.add(new ArrowElement(this, renderResolution.getWidth() / 6.0F, renderResolution.getHeight() / 2.0F, false, (arrow) -> {
            updateSkin(false);
            return true;
        }));
        elements.add(new ArrowElement(this, renderResolution.getWidth() - renderResolution.getWidth() / 6.0F, renderResolution.getHeight() / 2.0F, true, (arrow) -> {
            updateSkin(true);
            return true;
        }));
        elements.add(new ButtonElement(this, "Custom Skin", 
        		renderResolution.getWidth() / 2.0F, 
        		renderResolution.getHeight() - 90.0F, 100.0F, 40.0F, (button) -> {
            renderHandler.openGui(new GuiCustomSkin());
            return true;
        }));
        elements.add(new ButtonElement(this, "Done", 
        		renderResolution.getWidth() / 2.0F, 
        		renderResolution.getHeight() - 40.0F, 100.0F, 40.0F, (button) -> {
            exit();
            return true;
        }));
    }

	@Override
	public void render(float mouseX, float mouseY) {
		renderBackground();
		int snakePointIndex = 0;
		for (SnakePoint point : snake.points) {
			point.posY = (float) (15.0F * Math.cos(snakePointIndex / 4.0F + (client.frameTicks) / 4.0F) * (1.0F - ((float) snakePointIndex / snake.points.size())));
			snakePointIndex++;
		}
		drawCenteredLargeString("Select Skin", renderResolution.getWidth() / 2.0F, 25.0F, 0.5F, 0xFFFFFF);
		drawSnake(snake, renderHandler.centerX, renderHandler.centerY, 1.0F);
		if (client.configuration.showDebug) {
			int yinc = (int)(font.getHeight() / 2.0F + 2);

			int debugY = (int)(font.getHeight() / 2.0F + 2);
			drawString(client.configuration.skin.toString(), 10, debugY, 0.5F, 0xFFFFFF);
			debugY += yinc;
		}
	}

    @Override
    public void update() {
    }

	@Override
	public void keyPressed(int key) {
        if (key == GLFW_KEY_RIGHT || key == GLFW_KEY_LEFT) {
            updateSkin(key == GLFW_KEY_RIGHT);
        } else if (key == GLFW_KEY_ESCAPE || key == GLFW_KEY_BACKSPACE) {
            exit();
        } else if (key == GLFW_KEY_F3) {
			client.configuration.showDebug = !client.configuration.showDebug;
		}

	}

	private void exit() {
		closeGui();
		renderHandler.openGui(new GuiMainMenu());
	}

	@Override
	public void mouseClicked(float mouseX, float mouseY, int button) {

	}

	public void updateSkin(boolean side) {
		int skin = client.configuration.skin.ordinal();
		if (side) {
			skin++;
		} else {
			skin--;
		}
		Skin[] values = SkinEnum.values();
		skin %= values.length;
		if (skin < 0) {
			skin += values.length;
		}
		client.configuration.skin = (SkinEnum) values[skin];
		client.configuration.customSkin = null;
		client.saveConfig();
		snake = createSnake();
	}
	@Override
	public void resize() {
		super.resize();
		init();
	}

}
