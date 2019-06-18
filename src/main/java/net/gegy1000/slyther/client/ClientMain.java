package net.gegy1000.slyther.client;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import net.gegy1000.slyther.client.db.DatabaseImpl;
import net.gegy1000.slyther.client.db.GameStatistic;
import net.gegy1000.slyther.util.Log;

public class ClientMain {

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		//loadNatives();
		SlytherClient client = new SlytherClient();
		client.database = new DatabaseImpl();
		try {
			client.gameStatistic = client.database.getMostRecentGame();
			if (client.gameStatistic == null)
				client.gameStatistic = new GameStatistic();
		} catch (ExceptionInInitializerError e) {
			JOptionPane.showMessageDialog(null, "Failed to initialize database.\nIs another copy of Slyther running?",
					"Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e1) {

		}
		if (args.length >= 1)
			Log.showDebug = true;
		client.run();
	}


}
