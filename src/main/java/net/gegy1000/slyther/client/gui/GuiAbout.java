/**
 * 
 */
package net.gegy1000.slyther.client.gui;

import net.gegy1000.slyther.client.gui.element.ButtonElement;

/**
 * @author Dick Balaska
 *
 */
public class GuiAbout extends GuiWithBanner {

    private static int delayStart = 1500;		// # of milliseconds
    private static int fadeIn = 3000;
//	private	GuiMainMenu	guiMainMenu;
	class Banner {
		private String name;
		private String text;
		
		private Banner(String n, String t) {
			name = n;
			text = t;
		}
	}

	private Banner[] bannerText = { 
		new Banner("Slyther", "Copyright \u00A9 2015 Gegy"),
		new Banner("",        "Copyright \u00A9 2019 Dick Balaska"),
		new Banner("", ""),
		new Banner("", ""),
		new Banner("", ""),
		new Banner("LWJGL", "Copyright \u00A9 2012-present Lightweight Java Game Library"),
		new Banner("Slick2D", "Copyright \u00A9 2006 New Dawn Software."),
		new Banner("Java-WebSocket", "Copyright \u00A9 2010-2019 Nathan Rajlich"),
		new Banner("JFreeChart", "Copyright \u00A9 2001-2017 JFree.org")
	}; 
    private long startTime = System.currentTimeMillis();
	
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
		long runTime = System.currentTimeMillis() - startTime;
		float yDist = 15F;
		float yOfs = -110F;
		float y = renderResolution.getHeight() / 2.0F;
		float x = renderResolution.getWidth() * 0.25F;
		float xc = renderResolution.getWidth() / 2.0F;
		String s = "Slyther - a clone of the popular massively multiplayer game slither.io";
		drawString(s, x,  y + yOfs, 0.5F, 0xFFFFFF);
		yOfs += yDist;
		s = "Use mouse or arrow keys to steer, button or up arrow to accelerate";
		drawString(s, x,  y + yOfs, 0.45F, 0xFFFFFF);
		yOfs += yDist;
		s = "F11 - Toggle fullscreen    F12 - change score detail    F3 - show debug";
		drawString(s, x,  y + yOfs, 0.45F, 0xFFFFFF);
		yOfs += yDist * 1.75;
		s = "Protect your face!";
		drawCenteredString(s, xc,  y + yOfs, 0.45F, 0xFFFFFF);
		yOfs += yDist * 2;
		float xofs = 150F;
		
		int alpha;
		if (runTime < delayStart)
			alpha = 1;
		else if (runTime > delayStart + fadeIn)
			alpha = 255;
		else {
			long fadeTime = runTime - delayStart;
			if (fadeTime >= fadeIn)
				alpha = 255;
			else {
				alpha = (int)(fadeTime * 255 / fadeIn);
				if (alpha == 0)
					alpha = 1;
			}
		}
		int i = 0;
		for (Banner b : bannerText) {
			int a = alpha;
			if (i < 2) 
				a = 255;
			drawString(b.name, x,      y + yOfs, 0.45F, 0xFFFFFF | a << 24);
			drawString(b.text, x+xofs, y + yOfs, 0.45F, 0xFFFFFF | a << 24);
			yOfs += yDist;
			i++;
		}
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

	
}
