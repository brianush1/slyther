package net.gegy1000.slyther.client.db;

import java.util.Date;

public class GameStatistic {
	private Date	gamedate;
	private	int		duration;
	private	int		kills;
	private	int		length = -1;
	private	int		rank;
	private	int		snakeCount;
	
	// not persistent
	private	int		timeIndex = 0;

	public void reset() {
		gamedate = new Date();
		duration = 0;
		kills = 0;
		length = -1;
	}

	public boolean isValid() {
		return(length != -1);
	}
	/**
	 * @return the gamedate
	 */
	public Date getGamedate() {
		return gamedate;
	}
	/**
	 * @param gamedate the gamedate to set
	 */
	public void setGamedate(Date gamedate) {
		this.gamedate = gamedate;
	}
	/** During gameplay, this is the same as {@link #duration}, during playback
	 * this is the time into the playback 
	 * @return the timeIndex
	 */
	public int getTimeIndex() {
		return timeIndex;
	}

	/**
	 * @param timeIndex the timeIndex to set
	 */
	public void setTimeIndex(int timeIndex) {
		this.timeIndex = timeIndex;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	/** Increment the number of seconds this game has been played */
	public void incDuration(boolean replaying) {
		if (!replaying) {
			this.duration++;
			this.timeIndex = duration;
		} else
			this.timeIndex++;
	}

	/**
	 * @return the kills
	 */
	public int getKills() {
		return kills;
	}
	/**
	 * @param kills the kills to set
	 */
	public void setKills(int kills) {
		this.kills = kills;
	}
	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}
	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}
	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * @return the snakeCount
	 */
	public int getSnakeCount() {
		return snakeCount;
	}

	/**
	 * @param snakeCount the snakeCount to set
	 */
	public void setSnakeCount(int snakeCount) {
		this.snakeCount = snakeCount;
	}

	
}
