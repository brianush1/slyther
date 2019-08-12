package net.gegy1000.slyther.client.render;

import org.lwjgl.opengl.GL11;

import net.gegy1000.slyther.client.SlytherClient;

public class RenderResolution {
    private int width;
    private int height;

    private float scale = 1.0F;

    private static final int BASE_WIDTH = 854;
    private static final int BASE_HEIGHT = 480;

    public RenderResolution(SlytherClient client) {
        int displayWidth = client.frameBufferWidth;
        int displayHeight = client.frameBufferHeight;

        while (displayWidth / (scale + 1.0F) >= BASE_WIDTH && displayHeight / (scale + 1.0F) >= BASE_HEIGHT) {
            scale++;
        }

        width = (int) Math.ceil(displayWidth / scale);
        height = (int) Math.ceil(displayHeight / scale);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void applyScale() {
        GL11.glScalef(scale, scale, 1.0F);
    }

    public float getScale() {
        return scale;
    }
}
