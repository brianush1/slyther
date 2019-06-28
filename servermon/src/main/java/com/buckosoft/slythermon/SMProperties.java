/******************************************************************************
 * SMProperties.java - Manage user configuration
 */

/* 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * The Original Code is BuckoFIBS, <http://www.buckosoft.com/BuckoFIBS/>.
 * The Initial Developer of the Original Code is Dick Balaska and BuckoSoft, Corp.
 * 
 */
package com.buckosoft.slythermon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

/** user configuration with persistence.
 * @author Dick Balaska
 * @since 2008/03/30
 */
public class SMProperties extends Properties {
	private	final static boolean DEBUG = true;

	private static final long serialVersionUID = 1L;
	final static String s_slytherMon	= "slythermon";
	final static String	s_propFile		= "slythermon.properties";

	final static String s_mHeight			= "mainHeight";
	final static String s_mWidth			= "mainWidth";
	final static String s_mX				= "mainX";
	final static String s_mY				= "mainY";
	final static String s_autoConnect		= "autoConnect";



	/** If there are no props saved, then this is our first time running SlytherMon */
	private  boolean newbie = false;
	private String actualPropertiesFile;
	
	private class SMProp {
		String	key;
		String	value;
		SMProp(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}

	private SMProp[] smProps = {
		new SMProp(s_mWidth, "450"),
		new SMProp(s_mHeight, "300"),
		new SMProp(s_mX, "100"),
		new SMProp(s_mY, "100"),
		new SMProp(s_autoConnect, "1"),
	};

	/** Create a new SMProperties. <br>
	 * File reading and parsing is done in this constructor.
	 */
	public SMProperties() {
		String s = System.getProperty("user.home") + File.separator + ".config";
		File file = new File(s);
		if (file.exists()) {
			s += File.separator + s_slytherMon;
			file = new File(s);
			if (!file.exists()) {
				file.mkdir();
			}
			s += File.separator + s_propFile;
			actualPropertiesFile = s;
		} else {
			s = System.getProperty("user.home") + File.separator + s_propFile;
			actualPropertiesFile = s;
		}
		initialize(actualPropertiesFile);
	}
	
	private void initialize(String filename) {
		try {
		    FileInputStream fip = new FileInputStream(filename);
		    load(fip);
		}
		catch (Exception e) {
		    System.out.println("Exception while loading props: " + e);
		    newbie = true;
		}
		
		for (SMProp smProp : smProps) {
			if (getProperty(smProp.key) == null)
				setProperty(smProp.key, smProp.value);
		}
	}

	/** Force a save of the properties */
	public void shuttingDown() {
		save();
	}

	private	Timer	myTimer = null;
	private	TimerTask	myTimerTask = new MyTimerTask(this);
	
	/** If the properties have changed, save them after 1 second */
	private	void	propsChanged() {
		if (myTimer != null) {
			return;
		}
		myTimer = new Timer();
		try {
			myTimerTask = new MyTimerTask(this);
			myTimer.schedule(myTimerTask, 1000);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	private class MyTimerTask extends TimerTask {
		private	SMProperties props;

		MyTimerTask(SMProperties props) {
			super();
			this.props = props;
		}
		/* (non-Javadoc)
		 * @see java.util.TimerTask#run()
		 */
		@Override
		public void run() {
			this.props.save();
			this.props.myTimer.cancel();
			this.props.myTimer = null;
		}
	}
	
	/** Is this user a newbie?
	 * @return the newbie
	 */
	public boolean isNewbie() {
		return newbie;
	}


	/** Get the width for the MainDialog
	 * @return The width in pixels
	 */
	public int		getMainWidth() {
		String s = getProperty(s_mWidth);
		return(Integer.parseInt(s));
	}
	
	/** Set the width of the MainDialog
	 * @param width The width in pixels
	 */
	public void setMainWidth(int width) {
		if (width != getMainWidth())
			propsChanged();
		setProperty(s_mWidth, new Integer(width).toString());
	}



	/** Get the height for the MainDialog
	 * @return The height in pixels
	 */
	public int		getMainHeight() {
		String s = getProperty(s_mHeight);
		return(Integer.parseInt(s));
	}

	/** Set the height for the MainDialog
	 * @param height The height in pixels.
	 */ 
	public void setMainHeight(int height) {
		if (height != getMainHeight())
			propsChanged();
		setProperty(s_mHeight, new Integer(height).toString());
	}

	/** Get the X position for the MainDialog. <br>
	 * This is where to place the dialog on the screen during creation
	 * @return The X position
	 */
	public int		getMainX() {
		String s = getProperty(s_mX);
		return(Integer.parseInt(s));
	}
	
	/** Set the X position of the MainDialog on the screen.
	 * @param x The X position
	 */
	public void setMainX(int x) {
		if (x != getMainX())
			propsChanged();
		setProperty(s_mX, new Integer(x).toString());
	}

	/** Get the Y position for the MainDialog. <br>
	 * This is where to place the dialog on the screen during creation
	 * @return The Y position
	 */
	public int		getMainY() {
		String s = getProperty(s_mY);
		return(Integer.parseInt(s));
	}

	/** Set the Y position of the MainDialog on the screen.
	 * @param y The Y position
	 */
	public void setMainY(int y) {
		if (y != getMainY())
			propsChanged();
		setProperty(s_mY, new Integer(y).toString());
	}

	/** Should we automatically connect to the server when the app starts up?
	 * @return true == yes
	 */
	public boolean isAutoConnect() {
		String s = getProperty(s_autoConnect);
		return(s.equals("1"));
	}
	
	/** Set the auto connect value
	 * @param b true == yes
	 */
	public void setAutoConnect(boolean b) {
		if (b != isAutoConnect())
			propsChanged();
		setProperty(s_autoConnect, b ? "1" : "0");
	}
	
	/** Save the properties file
	 */
	private void save() {
		if (DEBUG)
			System.out.println("Saving properties");
		try {
			FileOutputStream fos = new FileOutputStream(actualPropertiesFile);
			store(fos, "SlytherMon properties");
		} catch (Exception e) {
			System.out.println("Exception while saving props:" + e);
		}
	}


}
