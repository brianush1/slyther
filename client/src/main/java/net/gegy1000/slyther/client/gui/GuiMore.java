package net.gegy1000.slyther.client.gui;

import net.gegy1000.slyther.client.gui.element.ButtonElement;

public class GuiMore extends GuiWithBanner {

	private float changeSkinY;
	private float selectServerY;
	private float localServerY;
	private float showStatsY;
	private float aboutY;
	private float doneY;
	private	GuiMainMenu	guiMainMenu;
    private boolean localServerAvailable = true;

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
					renderHandler.openGui(new GuiSelectSkin());
					return true;
				}));
		elements.add(new ButtonElement(this, "Select Server", renderResolution.getWidth() / 2.0F,
				renderResolution.getHeight() / 2.0F + selectServerY, 150.0F, 40.0F, (button) -> {
					closeGui();
					renderHandler.openGui(new GuiSelectServer());
					return true;
				}));
		if (localServerAvailable) {
		elements.add(new ButtonElement(this, "Local Server", renderResolution.getWidth() / 2.0F,
				renderResolution.getHeight() / 2.0F + localServerY, 150.0F, 40.0F, (button) -> {
					setLocalServer();
					exit();
					return true;
				}));
		}
		elements.add(new ButtonElement(this, "Show Stats", renderResolution.getWidth() / 2.0F,
				renderResolution.getHeight() / 2.0F + showStatsY, 150.0F, 40.0F, (button) -> {
					closeGui();
					renderHandler.openGui(new GuiStatistics(guiMainMenu));
					return true;
				}));
		elements.add(new ButtonElement(this, "About", renderResolution.getWidth() / 2.0F,
				renderResolution.getHeight() / 2.0F + aboutY, 150.0F, 40.0F, (button) -> {
					closeGui();
					renderHandler.openGui(new GuiAbout());
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
		localServerY	=  50F;
		showStatsY		= 100F;
		aboutY			= 150F;
		doneY			= 200F;
		if (!localServerAvailable) {
			showStatsY -= 50F;
			doneY -= 50F;
		}
	}
	void setLocalServer() {
		client.userServerSelection = "127.0.0.1:1444";
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
	public void keyPressed(int key) {
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

	@Override
	public void resize() {
		super.resize();
		init();
	}

}
