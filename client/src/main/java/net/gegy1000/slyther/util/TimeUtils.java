/**
 * 
 */
package net.gegy1000.slyther.util;

import java.text.DecimalFormat;

/**
 * @author dick
 *
 */
public class TimeUtils {
    private final static DecimalFormat df2 = new DecimalFormat("00");

	public static String toString(int seconds) {
		String fl;
   		int secs;
		int mins;
		int hrs = 0;
		secs = seconds % 60;
		mins = seconds / 60;
		if (mins > 60) {
			hrs = mins / 60;
			mins = mins % 60;
		}
		if (hrs == 0)
			fl = "" + mins + ":" + df2.format(secs);
		else
			fl = "" + hrs + ":" + df2.format(mins) + ":" + df2.format(secs);
		return(fl);
	}
}
