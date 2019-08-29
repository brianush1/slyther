/**
 * 
 */
package net.gegy1000.slyther.client.gui;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Modifier;

import org.lwjgl.opengl.GL11;

import com.google.gson.GsonBuilder;

import net.gegy1000.slyther.Version;
import net.gegy1000.slyther.util.Log;

/** Subclass {@link Gui} and provide some common services, like draw the logo and background. 
 * @author Dick Balaska
 *
 */
public abstract class GuiWithBanner extends Gui {

    private LetterPart[] logoLetterParts = new LetterPart[0];

    private double logoAlpha = 0, logoSlither = 0, logoFrame = 0;

    private long logoTime = System.currentTimeMillis();

    private final double lgsc = 1;

    private int logoWidth = 900, logoHeight = 270;
    protected float backgroundColor = 1.0F;

	public GuiWithBanner() {
		String s = "/data/logo.json";
		if (Version.SlytherEdition.equals("db")) {
			s = "/data/logo_db.json";
			logoWidth = 1050;
		} 
		try (Reader reader = new InputStreamReader(GuiMainMenu.class.getResourceAsStream(s))) {
			logoLetterParts = new GsonBuilder()
					.excludeFieldsWithModifiers(Modifier.TRANSIENT)
					.create().fromJson(reader, LetterPart[].class);
		} catch (IOException e) {
			Log.catching(e);
		} catch ( IllegalArgumentException e1) {
			Log.catching(e1);        	
		}

	}
    /* (non-Javadoc)
	 * @see net.gegy1000.slyther.client.gui.Gui#init()
	 */
	@Override
	public void init() {
		logoFrame = 0;
		logoTime = System.currentTimeMillis();
		logoSlither = 0;
	}

	protected void renderBanner() {
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
					offsetWiggleMultiplier = part.offsetWiggleMultiplier,
					letterOffset = part.letterOffset;
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
			double prevX = parts[0]+ letterOffset, lastPartToX = prevX;
			double prevY = parts[1], lastPartToY = prevY;
			double wiggle = logoFrame * wiggleSpeed;
			for (int n = 2; n < parts.length; n += 4) {
				double partFromX = parts[n]		+ letterOffset;
				double partFromY = parts[n + 1];
				double partToX = parts[n + 2]	+ letterOffset;
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

	}

	/** Draw the gui's background.  
	 * Store the background's position in client so that we can seamlessly change screens without resetting the 
	 * background's position.
	 */
	protected void renderBackground() {

        double backgroundMoveX = client.mouseX - (client.frameBufferWidth / 2.0F);
        double backgroundMoveY = (client.frameBufferHeight / 2.0F) - client.mouseY;
        double angle = Math.atan2(backgroundMoveY, backgroundMoveX);
        client.menuBackgroundX += Math.cos(angle) * 1.5F;
        client.menuBackgroundY += Math.sin(angle) * 1.5F;

        textureManager.bindTexture("/textures/background.png");
        GL11.glColor4f(1F, 1F, 1F, 1F);
        drawTexture(0.0F, 0.0F, client.menuBackgroundX, client.menuBackgroundY, renderResolution.getWidth() / client.globalScale, renderResolution.getHeight() / client.globalScale, 599, 519);
		
	}

	private class LetterPart {
		double[] parts;
		int		resolution;
		int		wiggleAmount;
		double	wiggleAmountStep;
		double	wiggleStep;
		double	wiggleSpeed;
		int		thickness;
		double	thicknessMultiplier;
		double	offsetWiggleMultiplier;
		double	letterOffset;
		transient boolean wch;
	}
	
}
