/**
 * 
 */
package net.gegy1000.slyther.game;

import net.gegy1000.slyther.util.Log;

/**
 * @author dick
 *
 */
public class SkinCustom implements Skin {

	private Color[] colorsUnpacked;
	private	byte[] colorsPacked;
	

	@Override
	public int ordinal() {
		return SkinEnum.CUSTOM.ordinal();
	}

	@Override
	public boolean isCustom() {
		return true;
	}
	
	public byte[] getColorsPacked() {
		return(colorsPacked);
	}

	public Color[] getColorsUnpacked() {
		return(colorsUnpacked);
	}

	public void setPackedColors(byte[] packedColors) {
		colorsPacked = packedColors;
		int numColors = 0;
		for (int i = 8; i<packedColors.length; i+=2) {
			numColors += packedColors[i];
		}
		colorsUnpacked = new Color[numColors];
		
		for (int i = 8, c=0; i<packedColors.length; i+=2) {
			byte colorsize = packedColors[i];
			byte colorid = packedColors[i+1];
			Log.debug("SkinCustom colorsize={} colorid={}", colorsize, colorid);
			for (int j=0; j<colorsize; j++, c++)
				colorsUnpacked[c] = Color.values()[colorid];
		}
	}

	public void setUnpackedColors(Color[] colors) {
		colorsUnpacked = colors;
		int plength = 1;
		byte cur = -1;
		for (int i=0; i<colors.length; i++) {
			if (i == 0) 
				cur = (byte)colors[i].ordinal();
			else if (cur != (byte)colors[i].ordinal()) {
				cur = (byte)colors[i].ordinal();
				plength++;
			}
		}
		colorsPacked = new byte[plength*2+8];
		colorsPacked[0] = colorsPacked[1] = colorsPacked[2] = (byte)255;
		colorsPacked[3] = colorsPacked[4] = colorsPacked[5] = (byte)0;
		colorsPacked[6] = (byte)Math.floor(256 * Math.random());
		colorsPacked[7] = (byte)Math.floor(256 * Math.random());
		cur = -1;
		int index = 8;
		for (int i=0; i<colors.length; i++) {
			if (i == 0) {
				plength = 1;
				cur = (byte)colors[i].ordinal();
			} else if (cur != (byte)colors[i].ordinal()) {
				colorsPacked[index] = (byte)plength;
				colorsPacked[index+1] = (byte)cur;
				plength = 1;
				cur = (byte)colors[i].ordinal();
				index += 2;
			} else
				plength++;
		}
		colorsPacked[index] = (byte)plength;
		colorsPacked[index+1] = cur;
	}

	@Override
	public int getHeadColor() {
		return(colorsUnpacked[0].ordinal());
	}

}
