package net.gegy1000.slyther.client.gui;

import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.client.gui.element.ButtonElement;
import net.gegy1000.slyther.client.gui.element.TextBoxElement;
import net.gegy1000.slyther.util.TimeUtils;

public class GuiMainMenu extends GuiWithBanner {

    final private float PlayY1			= 0F;
    final private float MoreY1	 		= 50F;
    final private float ReplayGameY1	= 200F;
    final private float QuitY1			= 200F;
    
    private float playY;
    private float moreY;
    private float replayGameY;
    private float quitY;

    private String killAndTimeMessage = null;
    
    public GuiMainMenu() {
    	
    }

	void calcElementPos() {
		playY = PlayY1;
		moreY = MoreY1;
		replayGameY = ReplayGameY1;
		quitY = QuitY1;
	}

	@Override
	public void init() {
		super.init();
		calcElementPos();
		elements.clear();
		elements.add(new TextBoxElement(this, client.configuration.nickname, renderResolution.getWidth() / 2.0F,
				renderResolution.getHeight() / 2.0F - 50.0F, 200.0F, 40.0F, (textbox) -> {
					client.configuration.nickname = textbox.getText();
					return null;
				}));
		elements.add(new ButtonElement(this, "Play", renderResolution.getWidth() / 2.0F, 
				renderResolution.getHeight() / 2.0F + playY, 150.0F, 40.0F, (button) -> {
					closeGui();
					client.connect();
					client.gameStatistic.reset();
					renderHandler.openGui(new GuiGame());
					return true;
				}));
		elements.add(new ButtonElement(this, "More", renderResolution.getWidth() / 2.0F,
				renderResolution.getHeight() / 2.0F + moreY, 150.0F, 40.0F, (button) -> {
					closeGui();
					renderHandler.openGui(new GuiMore(this));
					return true;
				}));
		if (SlytherClient.RECORD_FILE.exists()) {
			elements.add(new ButtonElement(this, "Replay Last Game", renderResolution.getWidth() / 2.0F,
					renderResolution.getHeight() / 2.0F + replayGameY, 150.0F, 40.0F, (button) -> {
						closeGui();
						client.replay();
						renderHandler.openGui(new GuiGame());
						return true;
					}));
		}
		elements.add(new ButtonElement(this, "Exit", renderResolution.getWidth() / 2.0F, 
				renderResolution.getHeight() / 2.0F + quitY, 150.0F, 40.0F, (button) -> {
					System.exit(0);
					return true;
				}));
	}

	@Override
	public void update() {
		
	}

    @Override
    public void keyPressed(int key, char character) {

    }

	@Override
	public void render(float mouseX, float mouseY) {
		renderBackground();
		renderBanner();

		if (client.gameStatistic.isValid()) {
			String fl = "Your final length was " + client.gameStatistic.getLength();
			drawCenteredString(fl, renderResolution.getWidth() / 2.0F, renderResolution.getHeight() / 2.0F - 110.0F, 0.45F, 0xFFFFFF);
			fl = "Your rank: " + client.gameStatistic.getRank() + " of " + client.gameStatistic.getSnakeCount();
			drawCenteredString(fl, renderResolution.getWidth() / 2.0F, renderResolution.getHeight() / 2.0F -  95.0F, 0.38F, 0xFFFFFF);
			if (killAndTimeMessage == null) {
				fl = TimeUtils.toString(client.gameStatistic.getDuration());
				killAndTimeMessage = "Kills: " + client.gameStatistic.getKills() + " Time: " + fl;
			}
			drawCenteredString(killAndTimeMessage, renderResolution.getWidth() / 2.0F, 
					renderResolution.getHeight() / 2.0F -  83.0F, 0.38F, 0xFFFFFF);
		}
	}

    @Override
    public void mouseClicked(float mouseX, float mouseY, int button) {

    }

	@Override
	public void resize() {
		super.resize();
		init();
	}

}
