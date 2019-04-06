package net.gegy1000.slyther.client.gui;

import net.gegy1000.slyther.client.gui.element.ButtonElement;

public class GuiMore extends GuiWithBanner {

 //   private float backgroundX;
 //   private float backgroundY;
	private float changeSkinY;
	private float selectServerY;
	private float showStatsY;
	private float doneY;
	private	GuiMainMenu	guiMainMenu;

    public GuiMore(GuiMainMenu menu) {
        this.guiMainMenu = menu;
    }


	@Override
	public void init() {
		super.init();
		calcElementPos();
		elements.clear();
		elements.add(new ButtonElement(this, "Change Skin", renderResolution.getWidth() / 2.0F,
				renderResolution.getHeight() / 2.0F + changeSkinY, 150.0F, 40.0F, (button) -> {
					closeGui();
					renderHandler.openGui(new GuiSelectSkin(guiMainMenu));
					return true;
				}));
		elements.add(new ButtonElement(this, "Select Server", renderResolution.getWidth() / 2.0F,
				renderResolution.getHeight() / 2.0F + selectServerY, 150.0F, 40.0F, (button) -> {
					closeGui();
					renderHandler.openGui(new GuiSelectServer(guiMainMenu));
					return true;
				}));
		elements.add(new ButtonElement(this, "Show Stats", renderResolution.getWidth() / 2.0F,
				renderResolution.getHeight() / 2.0F + showStatsY, 150.0F, 40.0F, (button) -> {
					closeGui();
					renderHandler.openGui(new GuiStatistics(guiMainMenu));
					return true;
				}));
		elements.add(new ButtonElement(this, "Done", renderResolution.getWidth() / 2.0F,
				renderResolution.getHeight() / 2.0F + doneY, 100.0F, 40.0F, (button) -> {
					exit();
					return true;
				}));

	}

	void calcElementPos() {
		changeSkinY		= -50F;
		selectServerY	=   0F;
		showStatsY		=  50F;
		doneY			= 100F;
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
    private void exit() {
        closeGui();
        renderHandler.openGui(guiMainMenu);
    }

}
