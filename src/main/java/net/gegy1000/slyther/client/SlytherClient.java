package net.gegy1000.slyther.client;

import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import net.gegy1000.slyther.client.controller.DefaultController;
import net.gegy1000.slyther.client.controller.IController;
import net.gegy1000.slyther.client.db.Database;
import net.gegy1000.slyther.client.db.GameStatistic;
import net.gegy1000.slyther.client.game.entity.ClientFood;
import net.gegy1000.slyther.client.game.entity.ClientPrey;
import net.gegy1000.slyther.client.game.entity.ClientSnake;
import net.gegy1000.slyther.client.gui.Gui;
import net.gegy1000.slyther.client.gui.GuiAbout;
import net.gegy1000.slyther.client.gui.GuiMainMenu;
import net.gegy1000.slyther.client.render.RenderHandler;
import net.gegy1000.slyther.game.ConfigHandler;
import net.gegy1000.slyther.game.Game;
import net.gegy1000.slyther.game.entity.Entity;
import net.gegy1000.slyther.game.entity.Food;
import net.gegy1000.slyther.game.entity.Prey;
import net.gegy1000.slyther.game.entity.Sector;
import net.gegy1000.slyther.game.entity.Snake;
import net.gegy1000.slyther.network.ServerHandler;
import net.gegy1000.slyther.network.message.client.MessageAccelerate;
import net.gegy1000.slyther.network.message.client.MessageSetAngle;
import net.gegy1000.slyther.util.Log;
import net.gegy1000.slyther.util.SystemUtils;
import net.gegy1000.slyther.util.UIUtils;

public class SlytherClient extends Game<ClientNetworkManager, ClientConfig> implements Runnable {
	public int GAME_RADIUS;
	public int MSCPS;
	public int SECTOR_SIZE;
	public int SECTORS_ALONG_EDGE;
	public float SPANG_DV;
	public float NSP1;
	public float NSP2;
	public float NSP3;
	public float SNAKE_TURN_SPEED;
	public float PREY_TURN_SPEED;
	public float CST;
	public int PROTOCOL_VERSION;

	public static final int RFC = 43; // C = Count?
	public static final int AFC = 26;
	public static final int LFC = 128;
	public static final int HFC = 92;
	public static final int VFC = 62;
	public static final float NSEP = 4.5F;
	public static final float INITIAL_SCALE = 0.9F;
	public static float MAX_QSM = 1.7F;
	public static final double PI_2 = Math.PI * 2.0;
	public static final float[] LFAS = new float[LFC];
	public static final float[] HFAS = new float[HFC];
	public static final float[] AFAS = new float[AFC];
	public static final float[] RFAS = new float[RFC];
	public static final float[] VFAS = new float[VFC];
	public static final float[] AT2LT = new float[65536];

	private int fps;

	private RenderHandler renderHandler;
	private ClientNetworkManager networkManager;
	public Database	database;

	public ClientSnake player;

	public long lastTickTime;
	public boolean lagging;
	public float lagMultiplier = 1.0F;
	public float errorTime;
	public float lastTicks;
	public float ticks;
	public float lastTicks2;
	public float ticks2;
	private boolean remainOpen = true;

	public float frameTicks;

	public float globalAlpha;
	public float qsm = 1.0F;

	private long lastAccelerateUpdateTime;
	public float lastSendAngle = Float.MIN_VALUE;
	public long lastSendAngleTime;

	public float viewX;
	public float viewY;

	public float viewAngle;
	public float viewDist;

	public float originalViewX;
	public float originalViewY;

	public float globalScale = INITIAL_SCALE;

	public int fvpos;
	public float fvx;
	public float fvy;
	public float[] fvxs = new float[VFC];
	public float[] fvys = new float[VFC];
	public int fvtg;

	private float[] fpsls;
	private float[] fmlts;

	public GameStatistic gameStatistic = new GameStatistic();

	private final int WINDOW_WIDTH  = 640;
	private final int WINDOW_HEIGHT = 480;
	private int displayWidth = WINDOW_WIDTH;
	private int displayHeight = WINDOW_HEIGHT;
	public long windowId;

	public float	mouseX;
	public float	mouseY;
	//    public double	mouseWheelX;
	public double	mouseWheelY;
	public int		frameBufferWidth = displayWidth;
	public int		frameBufferHeight = displayHeight;

	public float menuBackgroundX = 0;
	public float menuBackgroundY = 0;

	public String longestPlayerName;
	public int longestPlayerScore;
	public String longestPlayerMessage;
	public String fpsMessage = "";

	public ClientConfig configuration;

	public String userServerSelection;

	//private static final boolean START_FULLSCREEN = false;
	private boolean accelerating = false;
	private boolean isMouseDown = false;

	public static final File RECORD_FILE = new File(SystemUtils.getGameFolder(), "game.record");

	static {
		for (int i = 0; i < LFC; i++) {
			LFAS[i] = (float) (0.5F * (1.0F - Math.cos(Math.PI * (LFC - 1.0F - i) / (LFC - 1.0F))));
		}
		for (int i = 0; i < HFC; i++) {
			HFAS[i] = (float) (0.5F * (1.0F - Math.cos(Math.PI * (HFC - 1.0F - i) / (HFC - 1.0F))));
		}
		for (int i = 0; i < AFC; i++) {
			AFAS[i] = (float) (0.5F * (1.0F - Math.cos(Math.PI * (AFC - 1.0F - i) / (AFC - 1.0F))));
		}
		for (int i = 0; i < RFC; i++) {
			RFAS[i] = (float) (0.5F * (1.0F - Math.cos(Math.PI * (RFC - 1.0F - i) / (RFC - 1.0F))));
		}
		for (int i = 0; i < VFC; i++) {
			float vf = (float) (0.5F * (1.0F - Math.cos(Math.PI * (VFC - 1.0F - i) / (VFC - 1.0F))));
			vf += 0.5F * (0.5F * (1.0F - Math.cos(Math.PI * vf)) - vf);
			VFAS[i] = vf;
		}
		for (int y = 0; y < 256; y++) {
			for (int x = 0; x < 256; x++) {
				AT2LT[y << 8 | x] = (float) Math.atan2(y - 128, x - 128);
			}
		}
	}

	public float delta;
	public float lastDelta;
	public double frameDelta;

	private boolean allowUserInput = true;

	public float zoomOffset;

	public IController controller = new DefaultController();

	private static final File CONFIGURATION_FILE = new File(SystemUtils.getGameFolder(), "config.json");
	private String server;

	GLFWErrorCallback errorCallback;
	/**
	 * A reference to the cursor pos callback.
	 */
	@SuppressWarnings("unused")
	private GLFWCursorPosCallback cursorPosCallback;
	/**
	 * A reference to the mouse buttom callback.
	 */
	@SuppressWarnings("unused")
	private GLFWMouseButtonCallback mouseButtonCallback;

	@SuppressWarnings("unused")
	private GLFWScrollCallback mouseWheelCallback;
	@SuppressWarnings("unused")
	private GLFWFramebufferSizeCallback	framebufferSizeCallback;

	public SlytherClient() {
		try {
			configuration = ConfigHandler.INSTANCE.readConfig(CONFIGURATION_FILE, ClientConfig.class);
			saveConfig();
		} catch (IOException e) {
			UIUtils.displayException("Unable to read config", e);
			configuration = new ClientConfig();
			Log.catching(e);
		}
		userServerSelection = configuration.server;
		setController(new DefaultController());
		setup();

	}


	@Override
	public void run() {

		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createThrow());
		if (!glfwInit())
		{
			System.err.println("Error initializing GLFW");
			System.exit(1);
		}

		// Window Hints for OpenGL context
		glfwWindowHint(GLFW_SAMPLES, 4);
		//		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		//		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		//		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		//		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 1);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);

		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		displayWidth = WINDOW_WIDTH;
		displayHeight = WINDOW_HEIGHT;
		long monitor = 0;
		if(configuration.showFullScreen) {
			//Get the primary monitor.
			monitor = glfwGetPrimaryMonitor();
			//Retrieve the desktop resolution
			GLFWVidMode vidMode = glfwGetVideoMode(monitor);
			displayWidth = vidMode.width();
			displayHeight = vidMode.height();
			glfwWindowHint(GLFW_MAXIMIZED, GL_TRUE);

		}
		windowId = glfwCreateWindow(displayWidth, displayHeight, "Slyther", NULL, NULL);

		if (windowId == NULL) {
			System.err.println("Error creating a window");
			System.exit(1);
		}

		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		//Set resizable
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		//Request an OpenGL 3.3 Core context.
		//		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		//		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		//		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE); 
		//Create the window with the specified title.
		//        window = glfwCreateWindow(windowWidth, windowHeight, "Pong - LWJGL3", monitor, 0);       
		//
		//        if(window == 0) {
		//            throw new RuntimeException("Failed to create window");
		//        }
		//        //Make this window's context the current on this thread.
		//        glfwMakeContextCurrent(window);
		//        //Let LWJGL know to use this current context.

		//        initGL();
		/*		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
		GL11.glClearDepth(1);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE); 
		window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "slyther", 0, 0);

		setupDisplay();
		if (this.configuration.showFullScreen) {
			this.toggleFullscreen();
			setupDisplay();
		}
		 */
		glfwSetKeyCallback(windowId, (window, key, scancode, action, mods) -> {
			Log.debug("keyCallback key= {} scancode= {}", key, scancode);
			//			ctrlDown = (mods & GLFW_MOD_CONTROL) != 0;
			if (action == GLFW_RELEASE) {
				return;
			}
			handleKeyboard(key);
			//			for (Gui gui : renderHandler.getGuis()) {
			//				gui.keyPressedBase(key);
			//			}
		});
		glfwSetCharCallback(windowId, (window, codePoint) -> {
			Log.debug("charCallback {}", codePoint);

			renderHandler.activeGui.keyCharPressed(codePoint);

		});
		glfwSetMouseButtonCallback(windowId,  (mouseButtonCallback = new GLFWMouseButtonCallback() {

			@Override
			public void invoke(long window, int button, int action, int mods) {
				if(button == 0) {
					//Log.debug("mouse action = {} mods = {}", action, mods);
					if(action == GLFW_PRESS) {
						accelerating = true;
					} else if(action == GLFW_RELEASE) {
						accelerating = false;
					}
					if (action == GLFW_PRESS && !isMouseDown) {
						isMouseDown = true;
						renderHandler.activeGui.mouseClickedBase(mouseX, displayHeight - mouseY, button);

					}
					if (action == GLFW_RELEASE)
						isMouseDown = false;

				}
			}
		}));
		glfwSetCursorPosCallback(windowId, (cursorPosCallback = new GLFWCursorPosCallback() {

			@Override
			public void invoke(long window, double xpos, double ypos) {
				mouseX = (float)xpos;
				mouseY = displayHeight - (float)ypos;
				//cursorPos.y = framebuffer.height - ypos;
			}

		}));
		glfwSetScrollCallback(windowId, (mouseWheelCallback = new GLFWScrollCallback() {

			@Override
			public void invoke(long window, double xpos, double ypos) {
				//		        mouseWheelX = xpos;
				mouseWheelY += ypos*20;
				//cursorPos.y = framebuffer.height - ypos;
			}

		}));
		glfwSetFramebufferSizeCallback(windowId, (framebufferSizeCallback = new GLFWFramebufferSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				Log.debug("onResize");
				onResize(width, height);
			}
		}));
		onResize(displayWidth, displayHeight);
		//boolean doResize = false;
		// Make the OpenGL context current
		glfwMakeContextCurrent(windowId);
		// Enable v-sync
		glfwSwapInterval(1);
		glfwShowWindow(windowId);
		renderHandler = new RenderHandler(this);
		if(configuration.showFullScreen) {
			toggleFullscreen();
		}

		mainLoop();
		if (networkManager != null && networkManager.isOpen()) {
			networkManager.closeConnection(ClientNetworkManager.SHUTDOWN_CODE, "");
		}
		try {
			ConfigHandler.INSTANCE.saveConfig(CONFIGURATION_FILE, configuration);
		} catch (IOException e) {
			Log.error("Failed to save config");
			Log.catching(e);
		}
		glfwDestroyWindow(windowId);
	}

	private void onResize(int width, int height) {
		frameBufferWidth = width;
		frameBufferHeight = height;
	}

	private void setup() {
		clearEntities();
		delta = 0;
		ticks = 0;
		lastTicks = 0;
		player = null;
		lagging = false;
		globalScale = INITIAL_SCALE;
		lagMultiplier = 1.0F;
		zoomOffset = 0.0F;
		globalAlpha = 0.0F;
		ServerHandler.INSTANCE.pingServers();
	}

	private void mainLoop() {
		double delta = 0;
		long previousTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		int ups = 0;
		double nanoUpdates = 1000000000.0 / 30.0;

		GL.createCapabilities();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
		GL11.glClearDepth(1);
		glfwMakeContextCurrent(windowId);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		renderHandler.setup();
		Gui firstGui = configuration.virgin ? new GuiAbout() : new GuiMainMenu();
		configuration.virgin = false;	
		openGui(firstGui);
		while(!glfwWindowShouldClose(windowId) && remainOpen) {
			//if (Display.wasResized() && doResize) {
			//	setupDisplay();
			//}
			//doResize = true;

			long currentTime = System.nanoTime();
			double currentTickDelta = (currentTime - previousTime) / nanoUpdates;
			delta += currentTickDelta;
			frameDelta = (frameDelta + currentTickDelta) % 1.0;
			previousTime = currentTime;

			while (delta >= 1) {
				update();
				renderHandler.update();
				delta--;
				ups++;
			}

			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, frameBufferWidth, frameBufferHeight, 0, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glPushMatrix();

			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

			renderHandler.render();

			fps++;

			if (System.currentTimeMillis() - timer > 1000) {
				int bytesPerSecond = 0;
				int packetsPerSecond = 0;
				if (networkManager != null) {
					bytesPerSecond = networkManager.bytesPerSecond;
					packetsPerSecond = networkManager.packetsPerSecond;
					networkManager.bytesPerSecond = 0;
					networkManager.packetsPerSecond = 0;
				}
				fpsMessage = "FPS:" + fps + " - UPS: " + ups + " - BPS: " + bytesPerSecond + " - PPS: " + packetsPerSecond;
				//Display.setTitle("Slyther - " + fpsMessage);
				fps = 0;

				timer += 1000;
				//gamePlayTime++;
				gameStatistic.incDuration();
				ups = 0;
			}

			GL11.glPopMatrix();
			glfwPollEvents();
			glfwSwapBuffers(windowId);
		}

	}

	public void toggleFullscreen() {
		long 		monitor = glfwGetPrimaryMonitor();
		GLFWVidMode mode =  glfwGetVideoMode(monitor);
		//		if (mode.width() == displayWidth && mode.height() == displayHeight) {
		//			
		//		} else {
		glfwSetWindowMonitor(windowId, monitor, 0, 0, mode.width(), mode.height(), mode.refreshRate());
		//		}
		onResize(displayWidth, displayHeight);
		/*		try {
			if (Display.isFullscreen()) {
				Display.setFullscreen(false);
				Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
				configuration.showFullScreen = false;
				renderHandler.resize();
			} else {
				displayWidth = Display.getWidth();
				displayHeight = Display.getHeight();
				Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
				configuration.showFullScreen = true;
				renderHandler.resize();
			}
			saveConfig();
			} catch (LWJGLException e) {
			Log.warn("Can't set fullscreen mode");
		}
		 */	}

	/** Check and handle if this key is a global client key.
	 * Currently only F11=Fullscreen toggle is handled
	 * @param key
	 * @param character
	 * @return true if we ate the key
	 */
	public boolean handleKeyboard(int key) {
		//Log.debug("Key = {} char = {}", key, character);
		if (key == GLFW_KEY_F11) {
			toggleFullscreen();
			//			setupDisplay();
			return(true);
		}
		//		if (key == Keyboard.KEY_RMENU)	// eat the alt keys
		//			return(true);
		//		if (key == Keyboard.KEY_LMENU)
		//			return(true);
		renderHandler.activeGui.keyPressedBase(key);
		return(false);
	}

	void scrollCallback(long windowId, double xoffset, double yoffset) {

	}

	public void connect() {
		allowUserInput = true;
		new Thread(() -> {
			try {
				if (userServerSelection == null) {
					ServerHandler.Server server = ServerHandler.INSTANCE.getServerForPlay();
					networkManager = ClientNetworkManager.create(SlytherClient.this, server, configuration.shouldRecord);
				} else {
					networkManager = ClientNetworkManager.create(SlytherClient.this, userServerSelection, configuration.shouldRecord);
				}
				server = networkManager.getIp();
			} catch (Exception e) {
				UIUtils.displayException("Connection failed", e);
			}
		}).start();
	}

	public void replay() {
		allowUserInput = false;
		try {
			networkManager = ClientNetworkManager.create(this);
		} catch (Exception e) {
			UIUtils.displayException("Replay failed", e);
		}
	}

	public void setup(int gameRadius, int mscps, int sectorSize, int sectorCountAlongEdge, float spangDV, float nsp1, float nsp2, float nsp3, float snakeTurnSpeed, float preyTurnSpeed, float cst, int protocolVersion) {
		GAME_RADIUS = gameRadius;
		SECTOR_SIZE = sectorSize;
		SECTORS_ALONG_EDGE = sectorCountAlongEdge;
		SPANG_DV = spangDV;
		NSP1 = nsp1;
		NSP2 = nsp2;
		NSP3 = nsp3;
		SNAKE_TURN_SPEED = snakeTurnSpeed;
		PREY_TURN_SPEED = preyTurnSpeed;
		CST = cst;
		PROTOCOL_VERSION = protocolVersion;
		setMSCPS(mscps);
		Log.debug("PROTOCOL_VERSION: {}", PROTOCOL_VERSION);
		if (PROTOCOL_VERSION < 8) {
			throw new RuntimeException("Unsupported protocol version (" + PROTOCOL_VERSION + ")" + "!");
		}
	}

	public void setMSCPS(int mscps) {
		if (MSCPS != mscps) {
			MSCPS = mscps;
			fmlts = new float[mscps];
			fpsls = new float[mscps + 1];
			for (int i = 0; i <= mscps; i++) {
				if (i < mscps) {
					fmlts[i] = (float) Math.pow(1.0F - i / (float) mscps, 2.25F);
				}
				if (i != 0) {
					fpsls[i] = fpsls[i - 1] + 1.0F / fmlts[i - 1];
				}
			}
		}
	}

	@Override
	public float getFMLT(int i) {
		return fmlts[Math.min(i, fmlts.length - 1)];
	}

	@Override
	public float getFPSL(int i) {
		return fpsls[Math.min(i, fpsls.length - 1)];
	}

	public void update() {
		runTasks();
		if (networkManager != null) {
			long time = System.currentTimeMillis();
			float lastDelta2;
			delta = (time - lastTickTime) / 8.0F;
			lastTickTime = time;
			if (!lagging && networkManager.waitingForPingReturn && time - networkManager.lastPacketTime > 420) {
				lagging = true;
			}
			if (lagging) {
				lagMultiplier *= 0.85F;
				if (lagMultiplier < 0.01F) {
					lagMultiplier = 0.01F;
				}
			} else {
				if (lagMultiplier < 1.0F) {
					lagMultiplier += 0.05F;
					if (lagMultiplier >= 1.0F) {
						lagMultiplier = 1.0F;
					}
				}
			}
			if (delta > 120) {
				delta = 120;
			}
			delta *= lagMultiplier;
			errorTime *= lagMultiplier;
			lastTicks = ticks;
			ticks += delta;
			lastDelta = (float) (Math.floor(ticks) - Math.floor(lastTicks));
			lastTicks2 = ticks2;
			ticks2 += delta * 2;
			lastDelta2 = (float) (Math.floor(ticks2) - Math.floor(lastTicks2));
			if (globalAlpha < 1.0F) {
				globalAlpha += 0.0075F * delta;
				if (globalAlpha > 1.0F) {
					globalAlpha = 1.0F;
				}
			} else if (globalAlpha > 0.0F) {
				globalAlpha -= 0.0075F * delta;
				if (globalAlpha < 0.0F) {
					globalAlpha = 0.0F;
				}
			}
			if (qsm > 1.0F) {
				qsm -= 0.00004F * delta;
				if (qsm < 1.0F) {
					qsm = 1.0F;
				}
			} else if (qsm < MAX_QSM) {
				qsm += 0.00004F;
				if (qsm > MAX_QSM) {
					qsm = MAX_QSM;
				}
			}
			if (player != null) {
				if (!networkManager.waitingForPingReturn) {
					if (time - networkManager.lastPingSendTime > 250) {
						networkManager.lastPingSendTime = time;
						networkManager.ping();
					}
				}
				errorTime *= Math.pow(0.993, lastDelta);
				if (allowUserInput) {
					controller.update(this);
					float targetAngle = controller.getTargetAngle();
					targetAngle %= PI_2;
					if (targetAngle < 0) {
						targetAngle += PI_2;
					}
					if (targetAngle != lastSendAngle || lastSendAngleTime == 0) {
						if (time - lastSendAngleTime > 100) {
							lastSendAngle = targetAngle;
							networkManager.send(new MessageSetAngle(targetAngle));
						}
					}
					player.accelerating = accelerating;
					if (time - lastAccelerateUpdateTime > 150) {
						if (player.accelerating != player.wasAccelerating) {
							lastAccelerateUpdateTime = time;
							networkManager.send(new MessageAccelerate(player.accelerating));
							player.wasAccelerating = player.accelerating;
						}
					}
				}
				Iterator<Entity<?>> entityIter = entityIterator();
				while (entityIter.hasNext()) {
					@SuppressWarnings("rawtypes")
					Entity entity = entityIter.next();
					if (entity.updateBase(delta, lastDelta, lastDelta2)) {
						entityIter.remove();
					}
				}
			}
		} else {
			ticks++;
		}
	}

	public float getAngleTo(float x, float y) {
		return (float) Math.atan2(y - player.posY, x - player.posX);
	}

	public ClientSnake getSnake(int id) {
		for (@SuppressWarnings("rawtypes") Snake snake : getSnakes()) {
			if (snake.id == id) {
				return (ClientSnake) snake;
			}
		}
		return null;
	}

	public ClientPrey getPrey(int id) {
		for (@SuppressWarnings("rawtypes") Prey prey : getPreys()) {
			if (prey.id == id) {
				return (ClientPrey) prey;
			}
		}
		return null;
	}

	public ClientFood getFood(int id) {
		for (@SuppressWarnings("rawtypes") Food food : getFoods()) {
			if (food.id == id) {
				return (ClientFood) food;
			}
		}
		return null;
	}

	public void openGui(Gui gui) {
		renderHandler.openGui(gui);
	}

	public void closeGui() {
		renderHandler.closeGui();
	}

	//	public void closeAllGuis() {
	//		renderHandler.closeAllGuis();
	//	}

	public void reset() {
		closeGui();
		openGui(new GuiMainMenu());
		networkManager = null;
		setup();
	}

	public void setController(IController controller) {
		this.controller = controller;
	}

	@Override
	public int getGameRadius() {
		return GAME_RADIUS;
	}

	@Override
	public int getMSCPS() {
		return MSCPS;
	}

	@Override
	public int getSectorSize() {
		return SECTOR_SIZE;
	}

	@Override
	public int getSectorsAlongEdge() {
		return SECTORS_ALONG_EDGE;
	}

	@Override
	public float getSpangDv() {
		return SPANG_DV;
	}

	@Override
	public float getNsp1() {
		return NSP1;
	}

	@Override
	public float getNsp2() {
		return NSP2;
	}

	@Override
	public float getNsp3() {
		return NSP3;
	}

	@Override
	public float getBaseSnakeTurnSpeed() {
		return SNAKE_TURN_SPEED;
	}

	@Override
	public float getBasePreyTurnSpeed() {
		return PREY_TURN_SPEED;
	}

	@Override
	public float getCST() {
		return CST;
	}

	public void saveConfig() {
		try {
			ConfigHandler.INSTANCE.saveConfig(CONFIGURATION_FILE, configuration);
		} catch (Exception e) {
			Log.catching(e);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void removeSector(Sector sector) {
		super.removeSector(sector);
		if (sector != null) {
			int sectorSize = getSectorSize();
			List<Entity> entitiesInSector = new ArrayList<>();
			for (Entity entity : getFoods()) {
				int sectorX = (int) (entity.posX / sectorSize);
				int sectorY = (int) (entity.posY / sectorSize);
				if (sectorX == sector.posX && sectorY == sector.posY) {
					entitiesInSector.add(entity);
				}
			}
			for (Entity entity : entitiesInSector) {
				removeEntity(entity);
			}
		}
	}

	public void close() {
		if (networkManager != null) {
			networkManager.close(1000, "Forcefully closed by player");
		}
		glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
		player = null;
		networkManager = null;
	}

	public String getServer() {
		return server;
	}

	public void gameOver() {
		gameStatistic.setLength(player.getLength());
		saveConfig();
		database.addGame(gameStatistic);
	}
}
