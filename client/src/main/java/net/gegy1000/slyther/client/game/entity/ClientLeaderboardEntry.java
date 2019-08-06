/**
 * 
 */
package net.gegy1000.slyther.client.game.entity;

import net.gegy1000.slyther.game.Color;
import net.gegy1000.slyther.game.LeaderboardEntry;

/**
 * @author dick
 *
 */
public class ClientLeaderboardEntry extends LeaderboardEntry {
	
	public ClientLeaderboardEntry(String name, int score, Color color, boolean player) {
		this.name = name;
		this.score = score;
		this.color = color;
		this.player = player;
	}
	

}
