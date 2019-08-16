package net.gegy1000.slyther.client.db;

import java.util.List;

public interface Database {
	void	addGame(GameStatistic game);
	List<GameStatistic>	getGames();
	GameStatistic getMostRecentGame();
}
