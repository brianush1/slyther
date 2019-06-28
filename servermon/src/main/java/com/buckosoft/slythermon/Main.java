
package com.buckosoft.slythermon;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buckosoft.slythermon.gui.MainFrame;
import com.buckosoft.slythermon.message.MessageInfo;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

	private MainFrame frame;
	private NetworkMan networkMan;
	private SMProperties	smProperties;
	
	private Main() {
		frame = new MainFrame();
		smProperties = new SMProperties();
		frame.setMain(this);
		frame.setSMProperties(smProperties);
		frame.setVisible(true);
	}

	public MainFrame getMainFrame() {
		return(frame);
	}
	public void onConnectedToServer() {
		log.info("Connected to server");
		networkMan.sendMessage(new MessageInfo(this));
		setConnected(true);
	}
	public void onDisconnectedFromServer() {
		setConnected(false);
		try {
			networkMan.getSocket().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		networkMan = null;
	}

	public void setConnected(boolean b) {
		if (b) {
			String s = "Connected to " + networkMan.getURI().toString();
			frame.getServerConnectedTo().setText(s);
			frame.getConnectButton().setEnabled(false);
		} else {
			frame.getServerConnectedTo().setText("Not Connected to a server");
			frame.getConnectButton().setEnabled(true);
		}
	}

	public void connectToServer() {
		String ip = "127.0.0.1:1445";
		URI uri;
		try {
			uri = new URI("ws://" + ip + "/slither");
		} catch (URISyntaxException ex) {
			log.warn("Can't make URI", ex);
			return;
		}
		networkMan = new NetworkMan(this, uri);	
	}
	
	private static Main main;
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main = new Main();
					main.connectToServer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
