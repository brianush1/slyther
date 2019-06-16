/**
 * 
 */
package net.gegy1000.slyther.client.gui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.chart.JFreeChart;
import org.lwjgl.opengl.GL11;

import com.buckosoft.glelements.Texture;

import net.gegy1000.slyther.client.gui.element.ButtonElement;
import net.gegy1000.slyther.client.gui.stats.StatisticsThread;
import net.gegy1000.slyther.util.Log;

/**
 * @author dick
 *
 */
public class GuiStatistics extends Gui {

	private Gui parentMenu;
	private	boolean graphicsPrepared = false;
	private	StatisticsThread	statisticsThread;
	//private	int			drawnWidth;
	//private int			drawnHeight;
	
    public GuiStatistics(Gui parentMenu) {
        this.parentMenu = parentMenu;
    }

	@Override
	public void init() {
        elements.add(new ButtonElement(this, "Done", renderResolution.getWidth() / 2.0F,
        		renderResolution.getHeight() - 40.0F, 100.0F, 40.0F, (button) -> {
            exit();
            return true;
        }));
        graphicsPrepared = false;
        statisticsThread = new StatisticsThread(client.database);
        statisticsThread.start();
	}

	@Override
	public void render(float mouseX, float mouseY) {
		if (graphicsPrepared) {
			renderHandler.textureManager.bindTexture("chart");
			GL11.glScalef(1.0F, 1.0F, 1.0F);
			float x = 10F;
			float y = 10F;
			float width = client.frameBufferWidth-20F;
			float height = client.frameBufferHeight-150F;
			if (width > 1024)		// XXX: This hack shows a lack of understanding on my part about how
				width /= 2;			// XXX: opengl works.  Because this makes the graph work, but it makes no sense.
			if (height > 1024)
				height /= 2;
//			drawTexture(10, 10, Display.getWidth()-120, Display.getHeight()-150F);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glShadeModel(GL11.GL_FLAT);
			GL11.glTexCoord2f(0, 0);	GL11.glVertex2f(x, y);
			GL11.glTexCoord2f(1, 0);	GL11.glVertex2f(width, y);
			GL11.glTexCoord2f(1, 1);	GL11.glVertex2f(width, height);
			GL11.glTexCoord2f(0, 1);	GL11.glVertex2f(x, height);
			GL11.glEnd();
		}
	}

	@Override
	public void update() {
		if (!graphicsPrepared) {
			if (statisticsThread.isChartReady()) {
				JFreeChart chart = statisticsThread.getChart();
				Log.debug("Width/height= {} / {}", client.frameBufferWidth, client.frameBufferHeight);
				int w = client.frameBufferWidth;
				int h = client.frameBufferHeight;
				if (w > 1024)
					w = 1024;
				if (h > 1024)
					h = 1024;
				//w = 1024;
				//h = 1024;
				BufferedImage bi = chart.createBufferedImage(w, h);
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				try {
					ImageIO.write(bi, "png", os);
					Texture t = Texture.getTexture(new ByteArrayInputStream(os.toByteArray()));
					renderHandler.textureManager.cacheChart(t);
					graphicsPrepared = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return;
		}
		
	}

	@Override
	public void keyPressed(int key) {
	}

	@Override
	public void mouseClicked(float mouseX, float mouseY, int button) {
	}

	private void exit() {
		closeGui();
		renderHandler.openGui(parentMenu);
	}
	
}
