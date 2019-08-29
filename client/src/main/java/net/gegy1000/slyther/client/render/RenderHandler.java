package net.gegy1000.slyther.client.render;

import com.buckosoft.glelements.TrueTypeFont;

//import org.lwjgl.input.Keyboard;
//import org.lwjgl.input.Mouse;
//import org.lwjgl.opengl.Display;
//import org.newdawn.slick.opengl.ImageIOImageData;

import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.client.gui.Gui;
import net.gegy1000.slyther.game.Color;
//import net.gegy1000.slyther.util.Log;

public class RenderHandler {
    public SlytherClient client;

    public TextureManager textureManager;
    public RenderResolution renderResolution;

    public TrueTypeFont font;
    public TrueTypeFont largeFont;

    public float centerX;
    public float centerY;

    public float snakeMinX;
    public float snakeMinY;
    public float snakeMaxX;
    public float snakeMaxY;
    public float foodMinX;
    public float foodMinY;
    public float foodMaxX;
    public float foodMaxY;
    public float apx1;
    public float apy1;
    public float apx2;
    public float apy2;

    public Gui activeGui;

    public RenderHandler(SlytherClient client) {
        this.client = client;
        textureManager = new TextureManager();
    }

	public void setup() {
//        try {
//            Display.setDisplayMode(new DisplayMode(854, 480));
//            Display.setTitle("Slyther");
//            Display.setResizable(true);
//            try {
//                ByteBuffer[] icons = new ByteBuffer[] { toBuffer("/textures/icon_16.png"), toBuffer("/textures/icon_32.png"), toBuffer("/textures/icon.png") };
//                Display.setIcon(icons);
//            } catch (Exception e) {
//                Log.catching(e);
//            }
//            Display.create();
//            Keyboard.create();
//            Mouse.create();
//		try {
//			Font awtFont = new Font("Arial Rounded MT Bold", Font.PLAIN, 0);
//			font = new TrueTypeFont(awtFont.deriveFont(28.0F), true);
//			largeFont = new TrueTypeFont(awtFont.deriveFont(55.0F), true);
//		} catch (Exception e) {
//			Log.catching(e);
//		}
		init();
		font = new TrueTypeFont();
		font.load("FreeSans.ttf", 28);
		largeFont = new TrueTypeFont();
		largeFont.load("FreeSans.ttf", 55);
		for (Color color : Color.values()) {
			String name = color.name().toLowerCase();
			for (int i = 0; i < 6; i++) {
				textureManager.getTexture("/textures/colors/snake_" + name + "_" + i + ".png");
			}
			//Log.debug("Load " + color.name() + " textures.");
		}
    }

//    private ByteBuffer toBuffer(String directory) throws IOException {
//        return new ImageIOImageData().imageToByteBuffer(ImageIO.read(RenderHandler.class.getResourceAsStream(directory)), false, false, null);
//    }

    public void init() {
        renderResolution = new RenderResolution(client);
        centerX = renderResolution.getWidth() / 2.0F;
        centerY = renderResolution.getHeight() / 2.0F;
    }

    public void update() {
   		if (activeGui != null)
    		activeGui.updateBase();
//    	while (Keyboard.next()) {
//    		if (Keyboard.getEventKeyState()) {
//    			int key = Keyboard.getEventKey();
//    			char character = Keyboard.getEventCharacter();
//    			if (client.handleKeyboard(key, character))		// check for global key handling (F11)
//    				continue;
//    			for (Gui gui : getGuis()) {
//    				gui.keyPressedBase(key, character);
//    			}
//    		}
//    	}
    }

    public void render() {
        client.frameTicks = (float) (client.ticks + client.frameDelta);
        renderResolution.applyScale();
        float mouseX = (float) (client.mouseX / renderResolution.getScale());
        float mouseY = (float) ((client.frameBufferHeight - client.mouseY) / renderResolution.getScale());
        if (activeGui != null) {
	        activeGui.renderBase(mouseX, mouseY);
	        if (client.errorMessage != null) {
	        	activeGui.drawCenteredLargeString(client.errorMessage, renderResolution.getWidth() / 2.0F, renderResolution.getHeight() / 8.0F, 0.5F, 0xFF0000);
	        }
        }
    }

	public void openGui(Gui gui) {
		gui.initBase(client, this);
		activeGui = gui;
	}

    public void closeGui() {
    	activeGui = null;
    }

    public Gui	getGui() {
    	return(activeGui);
    }
}
