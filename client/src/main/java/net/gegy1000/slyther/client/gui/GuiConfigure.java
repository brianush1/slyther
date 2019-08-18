/**
 * 
 */
package net.gegy1000.slyther.client.gui;

import java.util.function.Function;

import net.gegy1000.slyther.client.gui.element.ButtonElement;
import net.gegy1000.slyther.client.gui.element.CheckBoxElement;
import net.gegy1000.slyther.client.gui.element.Element;

/**
 * @author dick
 *
 */
public class GuiConfigure extends GuiWithBanner {
	//private	CheckBoxElement checkboxRecord;

	private enum ButtonType {
		RecordGames,
		AutoSelectCloseServer
	}
	
	private final int X = 0;
	private final int Y = 1;
	private float buttonCoords[][] = new float[ButtonType.values().length][2];
	
	@Override
	public void init() {
		super.init();
		elements.clear();
		calcElementPos();
		elements.add(createCheckBox(ButtonType.RecordGames, "Record Games", client.configuration.shouldRecord, (checkbox) -> {
			client.configuration.shouldRecord = checkbox.isChecked();
			return true;
			
		}));
		elements.add(createCheckBox(ButtonType.AutoSelectCloseServer, "Auto Select closest server", client.configuration.autoSelectCloseServer, (checkbox) -> {
			client.configuration.autoSelectCloseServer = checkbox.isChecked();
			return true;
			
		}));
		elements.add(new ButtonElement(this, "Done", renderResolution.getWidth() / 2.0F,
				renderResolution.getHeight() / 2.0F + 200F, 100.0F, 40.0F, (button) -> {
			        client.saveConfig();
			        closeGui();
			        renderHandler.openGui(new GuiMainMenu());
					return true;
				}));
	}

	void calcElementPos() {
		float left = renderResolution.getWidth() / 3.0F - 50F;
		//float right = renderResolution.getWidth() * 2.0F / 3.0F - 50F;
	
		buttonCoords[ButtonType.AutoSelectCloseServer.ordinal()][X]	= left;
		buttonCoords[ButtonType.AutoSelectCloseServer.ordinal()][Y]	= -50F;
		buttonCoords[ButtonType.RecordGames.ordinal()][X]			= left;
		buttonCoords[ButtonType.RecordGames.ordinal()][Y]			=   0F;
	}	
	
	private Element createCheckBox(ButtonType buttonType, String string, boolean state, Function<CheckBoxElement, Boolean> function) {
		float x =										buttonCoords[buttonType.ordinal()][X];
		float y = renderResolution.getHeight() / 2.0F + buttonCoords[buttonType.ordinal()][Y];
		Element ele = new CheckBoxElement(this, state, string, x, y, 25F, 25F, function);
		return ele;
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
	}

	@Override
	public void keyPressed(int key, char character) {
	}

	@Override
	public void mouseClicked(float mouseX, float mouseY, int button) {
	}

}
