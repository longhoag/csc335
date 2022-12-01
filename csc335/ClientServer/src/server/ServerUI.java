package server;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.Client;
import client.ClientUI.UIPanel;
import database.DBaseConnection;

public class ServerUI extends JFrame {

	private static final long serialVersionUID = -4128374419991472184L;
	
	private final int WIDTH = 600;
	private final int HEIGHT = 550;
	
	private ServerUIPanel uiPanel;
	private ServerControlPanel ctrPanel;
	
	public ServerUI() {
		super();
		
		this.setTitle("Server");
		
		this.setSize(WIDTH, HEIGHT);
		
		this.setLocationRelativeTo(null);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout(100, 100));
		
		uiPanel = new ServerUIPanel();
		this.add(uiPanel, BorderLayout.WEST);
		
		ctrPanel = new ServerControlPanel();
		ctrPanel.setSize(100, 100);
		ctrPanel.setLayout(new BoxLayout(ctrPanel, BoxLayout.Y_AXIS));
		//ctrPanel.setLayout(new BorderLayout(5, 5));
		this.add(ctrPanel, BorderLayout.EAST);
		
		this.setResizable(true);
		
		this.setVisible(true);
		uiPanel.requestFocus();
	}
	
	public class ServerUIPanel extends JPanel {
		
		private static final long serialVersionUID = -3305338832194231356L;
		
		protected static JTextArea outConsole;
		private JScrollPane scrollableTextArea;
		
		public ServerUIPanel() {
			super();
			
			//--scrollable field
			outConsole = new JTextArea(30, 30);
			outConsole.setFocusable(false);
			scrollableTextArea = new JScrollPane(outConsole);
			//scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
	        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
	        this.add(scrollableTextArea); 
			
		}
	}
	
	public class ServerControlPanel extends JPanel {

		private static final long serialVersionUID = 7257933192381760134L;
		//--button 
		private JButton registered;
		private JButton connected;
		private JButton loggedIn;
		private JButton usersLoggedIn;
		private JButton usersLockedOut;
		
		public ServerControlPanel() {
			functionalityHandlers();
			
			//setLayout(new FlowLayout());
		
			this.add(registered);
			this.add(connected);
			this.add(loggedIn);
			this.add(usersLoggedIn);
			this.add(usersLockedOut);
			
		}
		
		private void functionalityHandlers() {
			DBaseConnection db = new DBaseConnection("root", "root123@");
			
			registered = new JButton("# registered users");
			registered.addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							int count = db.getUserCount();
							
							ServerUIPanel.outConsole.append(count + " users are registered!" + "\n\n");
						}
					}
					);
			
			connected = new JButton("# connected users");
			connected.addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							ServerUIPanel.outConsole.append(Server.nextId + " users are connected!" + "\n\n");
						}
					}
					);
			loggedIn = new JButton("# logged in users");
			loggedIn.addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

						}
					}
					);
			
			usersLoggedIn = new JButton("Users Logged In");
			usersLoggedIn.addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

						}
					}
					);
			
			usersLockedOut = new JButton("Users Locked Out");
			usersLockedOut.addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

						}
					}
					);
		}
		
	}
	
	//public class register
	
	public static void main (String[] args) {
		
		new ServerUI();
		System.out.println("ServerUI thread terminating");
	}
}
