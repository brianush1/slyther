package net.gegy1000.slyther.game;


public class LeaderboardEntry {
	public String name;
	public int score;
	public Color color;
	public boolean player;


	@Override
	public String toString() {
		return name + " - " + score;
	}
}
