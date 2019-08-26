/**
 * 
 */
package net.gegy1000.slyther.client.gui;

import java.text.SimpleDateFormat;
import java.util.List;

import net.gegy1000.slyther.client.gui.element.ButtonElement;
import net.gegy1000.slyther.client.gui.element.CheckBoxElement;
import net.gegy1000.slyther.client.recording.Replay;
import net.gegy1000.slyther.client.recording.ReplayMan;
import net.gegy1000.slyther.game.Color;
import net.gegy1000.slyther.util.TimeUtils;

/**
 * @author dick
 *
 */
public class GuiReplayMan extends GuiWithBanner {
	private float margin;
	private float lineHeight;
	private float entryHeight;	// how tall is each Replay on the screen
	private float entryWidth;
	private float playWidth;
	private float keepLeft;
	private float keepWidth;
	private float deleteWidth;
	private float dateWidth;
	private float killsWidth;
	private float lengthWidth;
	private float rankWidth;
	private float left;
	
	private float entryTop = 40F;	// top line for the entries
	
	private final SimpleDateFormat dateformat = new SimpleDateFormat("MMMM d, YYYY  HH:mm");
	
	@Override
	public void init() {
		elements.clear();
		elements.add(new ButtonElement(this, "Done", renderResolution.getWidth() / 2.0F, 
				renderResolution.getHeight() - 40.0F, 
				100.0F, 40.0F,
				(button) -> {
					renderHandler.openGui(new GuiMainMenu());
					return true;
				}));
		ReplayMan.INSTANCE.getReplayFiles();
		List<Replay> list = ReplayMan.INSTANCE.replayFiles;
		if (list == null || list.isEmpty())
			return;
		margin = renderResolution.getWidth() * 0.01F;
		left = renderResolution.getWidth()/2;
		lineHeight = font.getHeight();
		entryHeight = lineHeight*3 + margin*3;

		playWidth = font.getWidth("Play") + 40F;
		keepWidth = font.getWidth("Keep") + 20F + margin;
		deleteWidth = font.getWidth("delete");
		String s;
		int w;
		for (Replay r : list) {
			s = dateformat.format(r.getGamedate());
			w = font.getWidth(s);
			if (w > dateWidth)
				dateWidth = w;
		}
		keepLeft = left + dateWidth + margin + margin;
		int entryIndex = 0;
		float lineOfs = lineHeight/2; 
		for (Replay r : list) {
			float line = entryIndex*entryHeight+entryTop;
			elements.add(new CheckBoxElement(this, r.isKeep(), "Keep", keepLeft, line+lineOfs, 25F, 25F, (checkbox) -> {
				return true;
			}));

			entryIndex++;
		}

	}
	@Override
	public void render(float mouseX, float mouseY) {
		renderBackground();
		drawCenteredLargeString("Replay Manager", renderResolution.getWidth() / 2.0F, 25.0F, 0.5F, 0xFFFFFF);

		List<Replay> list = ReplayMan.INSTANCE.replayFiles;
		if (list == null || list.isEmpty())
			return;
		String s;
		int entryIndex = 0;
		for (Replay r : list) {
			float line = entryIndex*entryHeight+entryTop;
			s = dateformat.format(r.getGamedate());
			drawString(s, left, line, 1.0F, Color.WHITE.toHex());
			
			line += lineHeight;
			s = "Length: " + r.getLength();
			drawString(s, left, line, 0.6F, Color.YELLOW.toHex());
			
			line += lineHeight*0.6F;
			s = "Duration: " + TimeUtils.toString(r.getDuration());
			drawString(s, left, line, 0.6F, Color.YELLOW.toHex());

			entryIndex++;

		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(int key, char character) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(float mouseX, float mouseY, int button) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize() {
		super.resize();
		init();
	}


//	private void readReplayFiles() {
//		
//	}
}
