package client;

import client.Client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientUI extends JFrame {
	//-- Auto generated
	private static final long serialVersionUID = -4128374419991472184L;
	
	//-- size of the window
	
	private final int WIDTH = 600;
	private final int HEIGHT = 500;
	
	protected static String ipString = "0";
	
	private UIPanel uiPanel;
	private ControlPanel controlPanel;
	
	public ClientUI() {
		super();
		
		this.setTitle("Client");
		
		this.setSize(WIDTH, HEIGHT);
		
		this.setLocationRelativeTo(null);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout(5, 5));
		
		//uiPanel = new UIPanel();
		//this.add(uiPanel, BorderLayout.CENTER);
		
		controlPanel = new ControlPanel();
		controlPanel.setSize(200, 200);
		this.add(controlPanel, BorderLayout.NORTH);
		
		this.setResizable(false);
		
		this.setVisible(true);
		//uiPanel.requestFocus();
		
		
		
	}
	
	public class UIPanel extends JPanel {
		// auto generated
		private static final long serialVersionUID = 799337552754632496L;
		
		public UIPanel() {
			super();
			
		}
	}

	public class ControlPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		//-- text field
		private JTextField ipField;
		private JTextField emailField;
		
		//--button 
		private JButton connectButton;
		
		private JLabel ipadress = new JLabel("Enter IP Adress: ");
		
		protected static JTextArea outConsole;
		private JScrollPane scrollableTextArea;
		
		//--text field
		public ControlPanel() {
			
			functionalityHandlers();
			
			setLayout(new FlowLayout());
			
			this.add(ipadress);
			
			//-- construct text field
			ipField = new JTextField("IP Adress", 20);
			emailField = new JTextField("0", 5);
			

			
			
			//--add items 
			this.add(ipField);
			
			//this.add(emailField);
			this.add(connectButton);
			
			//--scrollable fiel
			outConsole = new JTextArea(30, 25);
			outConsole.setFocusable(false);
			scrollableTextArea = new JScrollPane(outConsole);
			//scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
	        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
	        this.add(scrollableTextArea);  
		}
		
		private void functionalityHandlers() {
			connectButton = new JButton("Connect");
			connectButton.addActionListener(
					new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							ipString = ipField.getText();
							
							MultiThreading prog = new MultiThreading();
							prog.start();
							
							System.out.println(ipField);
						}
						
						
					}
					);
		}
		
	}
	
	public static void main (String[] args) {
		new ClientUI();
		System.out.println("Main thread terminating");
	}
	
	
}


