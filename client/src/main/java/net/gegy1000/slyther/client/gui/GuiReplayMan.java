/**
 * 
 */
package net.gegy1000.slyther.client.gui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.gegy1000.slyther.client.gui.element.ArrowElement;
import net.gegy1000.slyther.client.gui.element.ButtonElement;
import net.gegy1000.slyther.client.gui.element.CheckBoxElement;
import net.gegy1000.slyther.client.recording.Replay;
import net.gegy1000.slyther.client.recording.ReplayMan;
import net.gegy1000.slyther.game.Color;
import net.gegy1000.slyther.util.TimeUtils;

/** Display the ReplayManager gui
 * @author dick
 *
 */
public class GuiReplayMan extends GuiWithBanner {
	private int page = 0;
	private int entriesPerPage;
	//private int numPages;
	private float margin;
	private float left;
	private float lineHeight;
	private float entryHeight;	// how tall is each Replay on the screen
	private float entryWidth;
	private float playLeft;
	private float playWidth;
	private float keepLeft;
	private float keepWidth;
	private float dateLeft;
	private float dateWidth;
	private float killsLeft;
	private float durationWidth;
	
	private final float entryTop = 40F;	// top line for the entries
	private final float tscale = 0.6F;
	
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
		lineHeight = font.getHeight();
		entryHeight = lineHeight + (lineHeight*tscale*2) + margin;

		playWidth = 40F;
		keepWidth = font.getWidth("Keep") + 20F + margin;
		String s;
		int w;
		for (Replay r : list) {
			s = dateformat.format(r.getGamedate());
			w = (int)font.getWidth(s);
			if (w > dateWidth)
				dateWidth = w;
			s = "Duration: " + r.getDuration();
			w = (int)font.getWidth(s);
			if (w > durationWidth)
				durationWidth = w;
		}
		dateLeft = playWidth + margin;
		keepLeft = dateLeft + dateWidth + margin*3;
		killsLeft = durationWidth + margin*2;
		
		entryWidth = keepLeft + keepWidth + margin;
		left = renderResolution.getWidth()/2 - entryWidth/2;
		
		int availableHeight = (int)(renderResolution.getHeight() - 40.0F - entryTop);
		entriesPerPage = (int)(availableHeight / entryHeight);
		//numPages = list.size() / entriesPerPage;
		ArrayList<String> al = client.configuration.replaysToKeep;
		int lineIndex = 0;
		float lineOfs = lineHeight/2;
		final String playTexture = "/textures/play2.png";
		int entry;
		for (lineIndex = 0, entry = entriesPerPage*page; lineIndex < entriesPerPage && entry < list.size(); lineIndex++, entry++) {
			Replay r = list.get(entry);
			float line = lineIndex*entryHeight+entryTop;
			boolean isKeep = al.contains(r.getPathname());
			elements.add(new CheckBoxElement(this, isKeep, "Keep", keepLeft+left, line+lineOfs, 25F, 25F, (checkbox) -> {
				keepChecked(r.getPathname(), checkbox.isChecked());
				return true;
			}));
			elements.add(new ButtonElement(this, playLeft+left, line+playWidth, playWidth, playWidth, playTexture, playTexture,(button) -> {
				client.replay(new File(r.getPathname()));
				renderHandler.openGui(new GuiGame());
				return true;
			}));
		}
		if (page > 0) {
	        elements.add(new ArrowElement(this, renderResolution.getWidth() / 9.0F, renderResolution.getHeight() / 2.0F, false, (arrow) -> {
	            page--;
	            init();
	            return true;
	        }));
		}
		if ((page+1)*entriesPerPage < list.size()) {
	        elements.add(new ArrowElement(this, renderResolution.getWidth() - renderResolution.getWidth() / 9.0F, renderResolution.getHeight() / 2.0F, true, (arrow) -> {
	            page++;
	            init();
	            return true;
	        }));
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
		int lineIndex = 0;
		final int yellow = Color.YELLOW.toHex();
		int entry;
		for (lineIndex = 0, entry = entriesPerPage*page; lineIndex < entriesPerPage && entry < list.size(); lineIndex++, entry++) {
			Replay r = list.get(entry);
			float line = lineIndex*entryHeight+entryTop;
			s = dateformat.format(r.getGamedate());
			drawString(s, dateLeft+left, line, 1.0F, Color.WHITE.toHex());
			
			line += lineHeight;
			s = "Length: " + r.getLength();
			drawString(s, dateLeft+left, line, tscale, yellow);
			
			s = "Kills: " + r.getKills();
			drawString(s, killsLeft+left, line, tscale, yellow);
			
			line += lineHeight*tscale;
			s = "Duration: " + TimeUtils.toString(r.getDuration());
			drawString(s, dateLeft+left, line, tscale, yellow);

			s = "Rank: " + r.getRank();
			drawString(s, killsLeft+left, line, tscale, yellow);

		}
	}

	@Override
	public void update() {
	}

	@Override
	public void keyPressed(int key) {
	}

	@Override
	public void mouseClicked(float mouseX, float mouseY, int button) {
	}

	@Override
	public void resize() {
		super.resize();
		init();
	}

	private void keepChecked(String filepath, boolean checked) {
		ArrayList<String> al = client.configuration.replaysToKeep;
		if (!checked) {
			al.remove(filepath);
		} else if (!al.contains(filepath))
			al.add(filepath);
        client.saveConfig();
	}
}
