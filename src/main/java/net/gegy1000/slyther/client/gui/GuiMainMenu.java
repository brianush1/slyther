package net.gegy1000.slyther.client.gui;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Modifier;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.google.gson.GsonBuilder;

import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.client.gui.element.ButtonElement;
import net.gegy1000.slyther.client.gui.element.TextBoxElement;
import net.gegy1000.slyther.util.Log;
import net.gegy1000.slyther.util.TimeUtils;

public class GuiMainMenu extends Gui {
    private float backgroundX;
    private float backgroundY;

    private LetterPart[] logoLetterParts = new LetterPart[0];

    private double logoAlpha = 0, logoSlither = 0, logoFrame = 0;

    private long logoTime = System.currentTimeMillis();

    private final double lgsc = 1;

    private final int logoWidth = 900, logoHeight = 270;
    private String killAndTimeMessage = null;
    private float PlayY1			= 0F;
    private float ChangeSkinY1 		= 50F;
    private float SelectServerY1	= 100F;
    private float ShowStatsY1		= 150F;
    private float ReplayGameY1		= 200F;
    private float QuitY1			= 200F;
    private float PlayY2			= 0F;
    private float ChangeSkinY2 		= 50F;
    private float SelectServerY2	= 100F;
    private float ShowStatsY2		= 150F;
    private float QuitY2			= 200F;
    
    private float playY;
    private float changeSkinY;
    private float selectServerY;
    private float showStatsY;
    private float replayGameY;
    private float quitY;
    
    public GuiMainMenu() {
        try (Reader reader = new InputStreamReader(GuiMainMenu.class.getResourceAsStream("/data/logo.json"))) {
            logoLetterParts = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .create().fromJson(reader, LetterPart[].class);
        } catch (IOException e) {
            Log.catching(e);
        }
    }

	void calcElementPos() {
		playY = PlayY1;
		changeSkinY = ChangeSkinY1;
		selectServerY = SelectServerY1;
		showStatsY = ShowStatsY1;
		quitY = QuitY1;
	}

    @Override
    public void init() {
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
        elements.add(new ButtonElement(this, "Change Skin", renderResolution.getWidth() / 2.0F,
        		renderResolution.getHeight() / 2.0F + changeSkinY, 150.0F, 40.0F, (button) -> {
            closeGui();
            renderHandler.openGui(new GuiSelectSkin(this));
            return true;
        }));
        elements.add(new ButtonElement(this, "Select Server", renderResolution.getWidth() / 2.0F,
        		renderResolution.getHeight() / 2.0F + selectServerY, 150.0F, 40.0F, (button) -> {
            closeGui();
            renderHandler.openGui(new GuiSelectServer(this));
            return true;
        }));
        elements.add(new ButtonElement(this, "Show Stats", renderResolution.getWidth() / 2.0F,
        		renderResolution.getHeight() / 2.0F + showStatsY, 150.0F, 40.0F, (button) -> {
            closeGui();
            renderHandler.openGui(new GuiStatistics(this));
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
        float backgroundMoveX = Mouse.getX() - (Display.getWidth() / 2.0F);
        float backgroundMoveY = (Display.getHeight() / 2.0F) - Mouse.getY();
        float angle = (float) Math.atan2(backgroundMoveY, backgroundMoveX);
        backgroundX += Math.cos(angle) * 1.5F;
        backgroundY += Math.sin(angle) * 1.5F;

        textureManager.bindTexture("/textures/background.png");
        drawTexture(0.0F, 0.0F, backgroundX, backgroundY, renderResolution.getWidth() / client.globalScale, renderResolution.getHeight() / client.globalScale, 599, 519);
        long time = System.currentTimeMillis();
        double delta = (time - logoTime) / 25D;
        logoTime = time;
        logoFrame += 0.05 * delta;
        if (logoAlpha != 1) {
            logoAlpha += 0.05 * delta;
            if (logoAlpha > 1) {
                logoAlpha = 1;
            }
        }
        if (logoSlither != 1) {
            logoSlither += 0.00375 * delta;
            if (logoSlither > 1) {
                logoSlither = 1;
            }
        }
        renderHandler.textureManager.bindTexture("/textures/circle.png");
        GL11.glPushMatrix();
        GL11.glTranslatef(renderResolution.getWidth() / 2, renderResolution.getHeight() / 6, 0);
        final float s = 0.5F;
        GL11.glScalef(s, s, 1);
        GL11.glTranslatef(-logoWidth / 2, -logoHeight / 2, 0);
        for (int i = 0; i < logoLetterParts.length; i++) {
            LetterPart part = logoLetterParts[i];
            double parts[] = part.parts,
                wiggleAmountStep = part.wiggleAmountStep,
                wiggleStep = part.wiggleStep,
                wiggleSpeed = part.wiggleSpeed,
                thicknessMultiplier = part.thicknessMultiplier,
                offsetWiggleMultiplier = part.offsetWiggleMultiplier;
            int resolution = part.resolution,
                wiggleAmount = part.wiggleAmount,
                thickness = part.thickness;
            if (part.wch && offsetWiggleMultiplier != 0) {
                offsetWiggleMultiplier *= 0.982;
                offsetWiggleMultiplier -= 0.001 * delta;
                if (offsetWiggleMultiplier < 0) {
                    offsetWiggleMultiplier = 0;
                }
                part.offsetWiggleMultiplier = offsetWiggleMultiplier;
            }
            int color = i < 9 ? 0x40BF70 : 0x6030AF;
            double travelAngle = 0;
            boolean hasTravelAngle = false;
            int totalSteps = 0;
            double prevX = parts[0], lastPartToX = prevX;
            double prevY = parts[1], lastPartToY = prevY;
            double wiggle = logoFrame * wiggleSpeed;
            for (int n = 2; n < parts.length; n += 4) {
                double partFromX = parts[n];
                double partFromY = parts[n + 1];
                double partToX = parts[n + 2];
                double partToY = parts[n + 3];
                for (int step = 1; step <= resolution; step++) {
                    totalSteps++;
                    // second degree bezier curve
                    double t = step / (double) resolution;
                    double x = lastPartToX + (partFromX - lastPartToX) * t;
                    double y = lastPartToY + (partFromY - lastPartToY) * t;
                    double targetX = partFromX + (partToX - partFromX) * t;
                    double targetY = partFromY + (partToY - partFromY) * t;
                    x += (targetX - x) * t;
                    y += (targetY - y) * t;
                    double targetTravelAngle = Math.atan2(y - prevY, x - prevX);
                    if (hasTravelAngle) {
                        if (targetTravelAngle - travelAngle > Math.PI) {
                            targetTravelAngle -= 2 * Math.PI;
                        } else if (targetTravelAngle - travelAngle < -Math.PI) {
                            targetTravelAngle += 2 * Math.PI;
                        }
                        travelAngle += 0.05 * (targetTravelAngle - travelAngle);
                        travelAngle %= 2 * Math.PI;
                    } else {
                        hasTravelAngle = true;
                        travelAngle = targetTravelAngle;
                    }
                    prevX = x;
                    prevY = y;
                    x += Math.cos(Math.PI / 2 + travelAngle) * Math.sin(wiggle) * wiggleAmount * offsetWiggleMultiplier / s;
                    y += Math.sin(Math.PI / 2 + travelAngle) * Math.sin(wiggle) * wiggleAmount * offsetWiggleMultiplier / s;
                    wiggle -= 0.76 * wiggleStep * wiggleAmount;
                    wiggleAmount += wiggleAmountStep;
                    double radius = 1.15 * thickness * Math.min(1, lgsc * (0.2 + 0.8 * logoAlpha) * (30 * logoSlither * thicknessMultiplier - totalSteps / 20D - i / 2D));
                    if (radius > 0.5) {
                        GL11.glColor4f((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, 1.0F);
                        drawTexture((float) (x * lgsc - radius), (float) (y * lgsc - radius), (float) (radius * 2), (float) (radius * 2));
                        part.wch = true;
                    }
                }
                lastPartToX = partToX;
                lastPartToY = partToY;
            }
        }
        GL11.glPopMatrix();
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

    private class LetterPart {
        double[] parts;
        int resolution;
        int wiggleAmount;
        double wiggleAmountStep;
        double wiggleStep;
        double wiggleSpeed;
        int thickness;
        double thicknessMultiplier;
        double offsetWiggleMultiplier;
        transient boolean wch;
    }
}
