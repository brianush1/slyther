/**
 * 
 */
package net.gegy1000.slyther.client.gui;

import net.gegy1000.slyther.client.gui.element.ButtonElement;

/**
 * @author dick
 *
 */
public class GuiConfigure extends GuiWithBanner {

	@Override
	public void init() {
		super.init();
		elements.clear();
		elements.add(new ButtonElement(this, "Continue", renderResolution.getWidth() / 2.0F,
				renderResolution.getHeight() / 2.0F + 200F, 100.0F, 40.0F, (button) -> {
			        closeGui();
			        renderHandler.openGui(new GuiMainMenu());
					return true;
				}));
	}

	@Override
	public void resize() {
		super.resize();
		init();
	}

	@Override
	public void render(float mouseX, float mouseY) {
		renderBackground();
		renderBanner();

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

}
