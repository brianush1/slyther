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

	private byte[] colorsUnpacked;
	private	byte[] colorsPacked;
	

	@Override
	public int ordinal() {
		return SkinEnum.CUSTOM.ordinal();
	}

	public void setPackedColors(byte[] packedColors) {
		colorsPacked = packedColors;
		int numColors = 0;
		for (int i = 8; i<packedColors.length; i+=2) {
			numColors += packedColors[i];
		}
		colorsUnpacked = new byte[numColors];
		
		for (int i = 8, c=0; i<packedColors.length; i+=2) {
			byte colorsize = packedColors[i];
			byte colorid = packedColors[i+1];
			Log.debug("SkinCustom colorsize={} colorid={}", colorsize, colorid);
			for (int j=0; j<colorsize; j++, c++)
				colorsUnpacked[c] = colorid;
		}
	}
	

}
