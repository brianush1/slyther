package com.buckosoft.slythermon.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import com.buckosoft.slythermon.Main;
import com.buckosoft.slythermon.SMProperties;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private Main main;
	SMProperties properties;

	private JPanel contentPane;
	private JTextField connectedClients;
	private JTextField txtConnectedTo;
	private JButton btnConnect;
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("slythermon");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JScrollPane consolePane = new JScrollPane();
		tabbedPane.addTab("Console", null, consolePane, null);
		
		JPanel panel = new JPanel();
		consolePane.setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		txtConnectedTo = new JTextField();
		txtConnectedTo.setEditable(false);
		txtConnectedTo.setText("Not Connected to a server");
		GridBagConstraints gbc_txtConnectedTo = new GridBagConstraints();
		gbc_txtConnectedTo.anchor = GridBagConstraints.WEST;
		gbc_txtConnectedTo.gridwidth = 2;
		gbc_txtConnectedTo.insets = new Insets(0, 0, 5, 5);
		gbc_txtConnectedTo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtConnectedTo.gridx = 0;
		gbc_txtConnectedTo.gridy = 0;
		panel.add(txtConnectedTo, gbc_txtConnectedTo);
		txtConnectedTo.setColumns(10);
		
		JLabel lblOfConnected = new JLabel("# of connected clients:");
		GridBagConstraints gbc_lblOfConnected = new GridBagConstraints();
		gbc_lblOfConnected.insets = new Insets(0, 0, 5, 5);
		gbc_lblOfConnected.anchor = GridBagConstraints.WEST;
		gbc_lblOfConnected.gridx = 0;
		gbc_lblOfConnected.gridy = 1;
		panel.add(lblOfConnected, gbc_lblOfConnected);
		
		connectedClients = new JTextField();
		connectedClients.setEditable(false);
		connectedClients.setText("0");
		GridBagConstraints gbc_connectedClients = new GridBagConstraints();
		gbc_connectedClients.anchor = GridBagConstraints.WEST;
		gbc_connectedClients.weightx = 1.0;
		gbc_connectedClients.insets = new Insets(0, 0, 5, 5);
		gbc_connectedClients.gridx = 1;
		gbc_connectedClients.gridy = 1;
		panel.add(connectedClients, gbc_connectedClients);
		connectedClients.setColumns(1);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		GridBagConstraints gbc_horizontalGlue = new GridBagConstraints();
		gbc_horizontalGlue.fill = GridBagConstraints.HORIZONTAL;
		gbc_horizontalGlue.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalGlue.gridx = 2;
		gbc_horizontalGlue.gridy = 1;
		panel.add(horizontalGlue, gbc_horizontalGlue);
		
		JTextPane textPane = new JTextPane();
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.gridheight = 2;
		gbc_textPane.gridwidth = 2;
		gbc_textPane.insets = new Insets(0, 0, 0, 5);
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridx = 0;
		gbc_textPane.gridy = 2;
		panel.add(textPane, gbc_textPane);
		
		JScrollPane configPane = new JScrollPane();
		tabbedPane.addTab("Config", null, configPane, null);
		
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.connectToServer();
			}		
		});
		toolBar.add(btnConnect);
		
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public JTextField getServerConnectedTo() {
		return(txtConnectedTo);
		
	}

	public JTextField getConnectedClients() {
		return(connectedClients);
	}
	
	public JButton getConnectButton() {
		return(btnConnect);
	}
	
	public void setSMProperties(SMProperties properties) {
		this.properties = properties;
		this.setSize(properties.getMainWidth(), properties.getMainHeight());
		this.setLocation(properties.getMainX(), properties.getMainY());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				mainFrameShuttingDown();
			}
		});
	}
	private void mainFrameShuttingDown() {
		if (true) {
			System.out.println("x=" + this.getX() + " y=" + this.getY());
			System.out.println("h/w = " + this.getHeight() + " / " + this.getWidth());
		}
		properties.setMainX(getX());
		properties.setMainY(getY());
		properties.setMainWidth(getWidth());
		properties.setMainHeight(getHeight());
		
		properties.shuttingDown();
	}

}
