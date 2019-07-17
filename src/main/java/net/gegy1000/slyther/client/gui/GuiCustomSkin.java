/**
 * 
 */
package net.gegy1000.slyther.client.gui;

import net.gegy1000.slyther.client.gui.element.ButtonElement;

/**
 * @author dick
 *
 */
public class GuiCustomSkin extends GuiWithBanner {

	@Override
	public void init() {
		elements.clear();
        elements.add(new ButtonElement(this, "Done", renderResolution.getWidth() / 2.0F, renderResolution.getHeight() - 40.0F, 100.0F, 40.0F, (button) -> {
            renderHandler.openGui(new GuiSelectSkin());
            return true;
        }));
		
	}

	@Override
	public void render(float mouseX, float mouseY) {
		
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
