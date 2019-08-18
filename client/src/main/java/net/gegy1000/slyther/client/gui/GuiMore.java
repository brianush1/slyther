package net.gegy1000.slyther.client.gui;

import java.util.function.Function;

import net.gegy1000.slyther.client.gui.element.ButtonElement;
import net.gegy1000.slyther.client.gui.element.Element;

public class GuiMore extends GuiWithBanner {

	
	private final int X = 0;
	private final int Y = 1;
	private float buttonCoords[][] = new float[ButtonType.values().length][2];
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
		elements.add(createButton(ButtonType.Configure, "Configure", (button) -> {
			closeGui();
			renderHandler.openGui(new GuiConfigure());
			return true;
			
		}));
		elements.add(createButton(ButtonType.ChangeSkin, "Change Skin", (button) -> {
			closeGui();
			renderHandler.openGui(new GuiSelectSkin());
			return true;
			
		}));
		elements.add(createButton(ButtonType.SelectServer, "Select Server", (button) -> {
			closeGui();
			renderHandler.openGui(new GuiSelectServer());
			return true;
			
		}));
		elements.add(createButton(ButtonType.LocalServer, "Local Server", (button) -> {
			setLocalServer();
			gotoMainMenu();
			return true;
			
		}));
		elements.add(createButton(ButtonType.ShowStats, "Show Stats", (button) -> {
			closeGui();
			renderHandler.openGui(new GuiStatistics(guiMainMenu));
			return true;
			
		}));
		elements.add(createButton(ButtonType.About, "About", (button) -> {
			closeGui();
			renderHandler.openGui(new GuiAbout());
			return true;
			
		}));
		elements.add(createButton(ButtonType.Done, "Done", (button) -> {
			gotoMainMenu();
			return true;
			
		}));
//		elements.add(new ButtonElement(this, "Change Skin", renderResolution.getWidth() / 2.0F,
//				renderResolution.getHeight() / 2.0F + changeSkinY, 150.0F, 40.0F, (button) -> {
//					closeGui();
//					renderHandler.openGui(new GuiSelectSkin());
//					return true;
//				}));
//		elements.add(new ButtonElement(this, "Select Server", renderResolution.getWidth() / 2.0F,
//				renderResolution.getHeight() / 2.0F + selectServerY, 150.0F, 40.0F, (button) -> {
//					closeGui();
//					renderHandler.openGui(new GuiSelectServer());
//					return true;
//				}));
//		if (localServerAvailable) {
//		elements.add(new ButtonElement(this, "Local Server", renderResolution.getWidth() / 2.0F,
//				renderResolution.getHeight() / 2.0F + localServerY, 150.0F, 40.0F, (button) -> {
//					setLocalServer();
//					gotoMainMenu();
//					return true;
//				}));
//		}
//		elements.add(new ButtonElement(this, "Show Stats", renderResolution.getWidth() / 2.0F,
//				renderResolution.getHeight() / 2.0F + showStatsY, 150.0F, 40.0F, (button) -> {
//					closeGui();
//					renderHandler.openGui(new GuiStatistics(guiMainMenu));
//					return true;
//				}));
//		elements.add(new ButtonElement(this, "About", renderResolution.getWidth() / 2.0F,
//				renderResolution.getHeight() / 2.0F + aboutY, 150.0F, 40.0F, (button) -> {
//					closeGui();
//					renderHandler.openGui(new GuiAbout());
//					return true;
//				}));
//		elements.add(new ButtonElement(this, "Done", renderResolution.getWidth() / 2.0F,
//				renderResolution.getHeight() / 2.0F + doneY, 100.0F, 40.0F, (button) -> {
//					gotoMainMenu();
//					return true;
//				}));
//
	}

	private Element createButton(ButtonType buttonType, String string, Function<ButtonElement, Boolean> function) {
		float x =										buttonCoords[buttonType.ordinal()][X];
		float y = renderResolution.getHeight() / 2.0F + buttonCoords[buttonType.ordinal()][Y];
		Element ele = new ButtonElement(this, string, x, y, 150F, 40F, function);
		return ele;
	}

	private enum ButtonType {
		Configure,
		ChangeSkin,
		SelectServer,
		LocalServer,
		ShowStats,
		About,
		Done
	};

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
		float left = renderResolution.getWidth() / 3.0F + 75F;
		float right = renderResolution.getWidth() * 2.0F / 3.0F - 75F;
	
		buttonCoords[ButtonType.Configure.ordinal()][X] = left;
		buttonCoords[ButtonType.Configure.ordinal()][Y] = -50F;
		buttonCoords[ButtonType.ChangeSkin.ordinal()][X] = left;
		buttonCoords[ButtonType.ChangeSkin.ordinal()][Y] = 0F;
		buttonCoords[ButtonType.SelectServer.ordinal()][X] = right;
		buttonCoords[ButtonType.SelectServer.ordinal()][Y] = -50F;
		buttonCoords[ButtonType.LocalServer.ordinal()][X] = right;
		buttonCoords[ButtonType.LocalServer.ordinal()][Y] = 0F;
		buttonCoords[ButtonType.ShowStats.ordinal()][X] = left;
		buttonCoords[ButtonType.ShowStats.ordinal()][Y] = 50F;
		buttonCoords[ButtonType.About.ordinal()][X] = left;
		buttonCoords[ButtonType.About.ordinal()][Y] = 100F;
		buttonCoords[ButtonType.Done.ordinal()][X] = renderResolution.getWidth() / 2.0F;
		buttonCoords[ButtonType.Done.ordinal()][Y] = 200F;
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
	}

	@Override
	public void keyPressed(int key, char character) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(float mouseX, float mouseY, int button) {
		// TODO Auto-generated method stub

	}
    private void gotoMainMenu() {
        closeGui();
        renderHandler.openGui(guiMainMenu);
    }

	@Override
	public void resize() {
		super.resize();
		init();
	}

}
