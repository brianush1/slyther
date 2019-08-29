/**
 * 
 */
package net.gegy1000.slyther.client.recording;

import net.gegy1000.slyther.client.db.GameStatistic;

/** Define one Replay file.
 * @author dick
 *
 */
public class Replay extends GameStatistic implements Comparable<Replay>{
	private String	pathname;
//	private boolean	keep;

	/**
	 * @return the pathname
	 */
	public String getPathname() {
		return pathname;
	}

	/**
	 * @param pathname the pathname to set
	 */
	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

//	/** Should we keep this replay or let it auto-delete
//	 * @return the keep
//	 */
//	public boolean isKeep() {
//		return keep;
//	}
//
//	/**
//	 * @param keep the keep to set
//	 */
//	public void setKeep(boolean keep) {
//		this.keep = keep;
//	}
//
	@Override
	public int compareTo(Replay o) {
		return(Long.compare(o.gamedate.getTime(), gamedate.getTime()));
	}

}
