package net.gegy1000.slyther.util;

import javax.swing.JOptionPane;

import net.gegy1000.slyther.client.ClientMain;

public final class UIUtils {
    private UIUtils() {}

    public static void displayException(String msg, Exception e) {
    	String s = msg + ":\n" + e.getClass().getSimpleName() + ": " + e.getMessage();
    	if (!ClientMain.clientStarted)
    		JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
    	else
    		ClientMain.client.errorMessage = s;
    	Log.error(s);
    	Log.catching(e);
    }
}
