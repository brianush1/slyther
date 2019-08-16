package net.gegy1000.slyther.server;

import net.gegy1000.slyther.util.Log;

public class ServerMain {
	public static void main(String[] args) throws Exception {
		if (args.length >= 1)
			Log.showDebug = true;
		new SlytherServer();
	}
}
