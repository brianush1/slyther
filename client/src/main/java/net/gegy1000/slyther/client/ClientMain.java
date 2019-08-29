package net.gegy1000.slyther.client;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import net.gegy1000.slyther.Version;
import net.gegy1000.slyther.client.db.DatabaseImpl;
import net.gegy1000.slyther.client.db.GameStatistic;
import net.gegy1000.slyther.client.db.HibernateUtil;
import net.gegy1000.slyther.util.Log;
import net.gegy1000.slyther.util.SystemUtils;

public class ClientMain {
    public static boolean clientStarted = false;
    public static SlytherClient client;

	public static void main(String[] args) throws Exception {
		parseParams(args);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		client = new SlytherClient();
		HibernateUtil.buildSessionFactory(SystemUtils.getGameFolder());
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
		if (client.gameStatistic == null)
			client.gameStatistic = new GameStatistic();
		client.run();
	}

    private static void parseParams(String[] args) {
    	for (int i=0; i<args.length; i++) {
    		if (args[i].equals("-d")) {
   				Log.showDebug = true;
    		} else if (args[i].equals("-g")) {
    			if (i+1 < args.length) {
    				SystemUtils.setGameFolder(args[i+1]);
    				i++;
    			} else {
    				System.err.format("-g requires a directory option");
    			}
    		} else if (args[i].equals("-v")) {
    			System.out.println("Slyther version: " + Version.SlytherVersion);
    			System.exit(1);
    		}
    	}
    }

}
