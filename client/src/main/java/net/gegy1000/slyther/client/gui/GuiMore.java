package net.gegy1000.slyther.client.gui;

import java.util.function.Function;

import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.client.gui.element.ButtonElement;
import net.gegy1000.slyther.client.gui.element.Element;

public class GuiMore extends GuiWithBanner {

	private enum ButtonType {
		Configure,
		ChangeSkin,
		SelectServer,
		LocalServer,
		ReplayLastGame,
		ShowStats,
		About,
		Done
	};

	
	private final int X = 0;
	private final int Y = 1;
	private float buttonCoords[][] = new float[ButtonType.values().length][2];
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

		///////////////////////////////////////////////////////////////////////
		elements.add(createButton(ButtonType.SelectServer, "Select Server", (button) -> {
			closeGui();
			renderHandler.openGui(new GuiSelectServer());
			return true;
			
		}));
		if (localServerAvailable) {
			elements.add(createButton(ButtonType.LocalServer, "Local Server", (button) -> {
				setLocalServer();
				gotoMainMenu();
				return true;
				
			}));
		}
		if (SlytherClient.RECORD_FILE.exists()) {
			elements.add(createButton(ButtonType.ReplayLastGame, "Replay Last Game", (button) -> {
				closeGui();
				client.replay();
				renderHandler.openGui(new GuiGame());
				return true;
				
			}));
		}
		elements.add(createButton(ButtonType.Done, "Done", (button) -> {
			gotoMainMenu();
			return true;
			
		}));
	}

	private Element createButton(ButtonType buttonType, String string, Function<ButtonElement, Boolean> function) {
		float x =										buttonCoords[buttonType.ordinal()][X];
		float y = renderResolution.getHeight() / 2.0F + buttonCoords[buttonType.ordinal()][Y];
		Element ele = new ButtonElement(this, string, x, y, 150F, 40F, function);
		return ele;
	}

	void calcElementPos() {
		float left = renderResolution.getWidth() / 3.0F + 50F;
		float right = renderResolution.getWidth() * 2.0F / 3.0F - 50F;
	
		buttonCoords[ButtonType.Configure.ordinal()][X]		= left;
		buttonCoords[ButtonType.Configure.ordinal()][Y]		= -50F;
		buttonCoords[ButtonType.ChangeSkin.ordinal()][X]	= left;
		buttonCoords[ButtonType.ChangeSkin.ordinal()][Y]	=   0F;
		buttonCoords[ButtonType.ShowStats.ordinal()][X]		= left;
		buttonCoords[ButtonType.ShowStats.ordinal()][Y]		=  50F;
		buttonCoords[ButtonType.About.ordinal()][X]			= left;
		buttonCoords[ButtonType.About.ordinal()][Y]			= 100F;
		buttonCoords[ButtonType.SelectServer.ordinal()][X]	= right;
		buttonCoords[ButtonType.SelectServer.ordinal()][Y]	= -50F;
		buttonCoords[ButtonType.LocalServer.ordinal()][X] 	= right;
		buttonCoords[ButtonType.LocalServer.ordinal()][Y] 	=   0F;
		buttonCoords[ButtonType.ReplayLastGame.ordinal()][X]= right;
		buttonCoords[ButtonType.ReplayLastGame.ordinal()][Y]= 100F;
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
	}

	@Override
	public void mouseClicked(float mouseX, float mouseY, int button) {
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
