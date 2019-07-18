/**
 * 
 */
package net.gegy1000.slyther.client.gui;


import net.gegy1000.slyther.client.gui.element.ButtonElement;
import net.gegy1000.slyther.game.SkinColor;
import net.gegy1000.slyther.game.entity.SnakePoint;
import net.gegy1000.slyther.util.Log;

/**
 * @author dick
 *
 */
public class GuiCustomSkin extends GuiWithSnakeEditor {

	@Override
	public void init() {
		elements.clear();
        createSnake();
		elements.add(new ButtonElement(this, "Done", renderResolution.getWidth() / 2.0F, 
				renderResolution.getHeight() - 40.0F, 
				100.0F, 40.0F,
				(button) -> {
					renderHandler.openGui(new GuiSelectSkin());
					return true;
				}));
		float bsize = 32F;
		float vspacing = 10F;
		float hspacing = 10F;
		float row = renderResolution.getHeight() / 3 + (bsize * 2) + vspacing;
		float firstCol = renderResolution.getWidth() / 2 - (bsize * 4) - (hspacing*2);
		float col = firstCol;
		for (int i=0; i<40; i++) {
			final int fi = i;
			if (i == 36 || i == 38)
				continue;
			if (i == 8 || i == 17 || i == 27) {
				firstCol -= (bsize+hspacing)/2;
				col = firstCol;
				row -= bsize+vspacing;
			}
			elements.add(new ButtonElement(this, col, row, bsize, bsize, getTexture0(i), getTexture1(i),
					(button) -> {
						buttonPressed(fi);
						return(true);
					}));
			col += bsize + hspacing;
		}
	}

	@Override
	public void resize() {
		super.resize();
		init();
	}

	@Override
	public void render(float mouseX, float mouseY) {
    	renderBackground();
        drawCenteredLargeString("Custom Skin", renderResolution.getWidth() / 2.0F, 25.0F, 0.5F, 0xFFFFFF);
        int snakePointIndex = 0;
        for (SnakePoint point : snake.points) {
            point.posY = (float) (15.0F * Math.cos(snakePointIndex / 4.0F + (client.frameTicks) / 4.0F) * (1.0F - ((float) snakePointIndex / snake.points.size())));
            snakePointIndex++;
        }
        drawSnake(renderHandler.centerX, renderHandler.renderResolution.getHeight()*3/4, 1.0F);

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(int key, char character) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(float mouseX, float mouseY, int button) {
		// TODO Auto-generated method stub

	}

	private String getTexture0(int which) {
		String s = "/textures/colors/snake_" + SkinColor.values()[which].name().toLowerCase() + "_" + 0 + ".png";
		return(s);
	}
	
	private String getTexture1(int which) {
		String s = "/textures/colors/snake_" + SkinColor.values()[which].name().toLowerCase() + "_" + 5 + ".png";
		return(s);
	}
	
	private void buttonPressed(int which) {
		Log.info("clicked {}", which);
	}
}
