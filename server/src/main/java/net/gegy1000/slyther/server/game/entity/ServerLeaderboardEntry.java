/**
 * 
 */
package net.gegy1000.slyther.server.game.entity;

import net.gegy1000.slyther.game.LeaderboardEntry;
import net.gegy1000.slyther.server.ConnectedClient;

/**
 * @author dick
 *
 */
public class ServerLeaderboardEntry extends LeaderboardEntry {
	public ConnectedClient client;

	public ServerLeaderboardEntry(ConnectedClient client) {
		this.client = client;
	}

}
