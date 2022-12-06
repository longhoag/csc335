package client;

import client.Client;
import server.ConnectionThread;

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

public class ClientUI extends JFrame {
	//-- Auto generated
	private static final long serialVersionUID = -4128374419991472184L;
	
	//-- size of the window
	
	private final int WIDTH = 900;
	private final int HEIGHT = 550;
	
	protected static String serveripaddress = "0";
	
	protected static String username = "";
	protected static String password = "";
	protected static String emailAddress = "";
	
	private UIPanel uiPanel;
	private ControlPanel controlPanel;
	
	//testing
	private static int count = 0;
	
	private Client client;
	
	public ClientUI() {
		super();
		
		this.setTitle("Client");
		
		this.setSize(WIDTH, HEIGHT);
		
		this.setLocationRelativeTo(null);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout(5, 5));
		
		uiPanel = new UIPanel();
		this.add(uiPanel, BorderLayout.WEST);
		
		controlPanel = new ControlPanel();
		controlPanel.setSize(200, 200);
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		this.add(controlPanel, BorderLayout.EAST);

		this.setResizable(true);
		
		this.setVisible(true);
		
		this.revalidate();

	}
	
	public class UIPanel extends JPanel {
		// auto generated
		private static final long serialVersionUID = 799337552754632496L;
		
		protected static JTextArea outConsole;
		private JScrollPane scrollableTextArea;
		
		public UIPanel() {
			super();
			
			//--scrollable field
			outConsole = new JTextArea(30, 30);
			outConsole.setFocusable(false);
			scrollableTextArea = new JScrollPane(outConsole);  
	        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
	        this.add(scrollableTextArea); 
	        this.revalidate();
			
		}
	}

	public class ControlPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private IpPanel ipBox;
		private UsernamePanel usernameBox;
		private PasswordPanel passwordBox;
		private EmailPanel emailBox;
		private LoginPanel loginBox;
		private RecoverPanel recoverBox;
		private LogOutPanel logOutBox;
		
		//--text field
		public ControlPanel() {
			
			setLayout(new FlowLayout());
			
			ipBox = new IpPanel();
			usernameBox = new UsernamePanel();
			passwordBox = new PasswordPanel();
			emailBox = new EmailPanel();
			loginBox = new LoginPanel();
			recoverBox = new RecoverPanel();
			logOutBox = new LogOutPanel();
	
			this.add(ipBox);
			this.revalidate();
			
		}
		
	}
	
	
	public class IpPanel extends JPanel {

		private static final long serialVersionUID = 5978273664496847630L;
		
		private JLabel ipLabel = new JLabel("Enter IP Address: ");
		private JTextField ipField;
		//--button 
		private JButton connectButton;
		
		public IpPanel() {
			functionalityHandlers();
			
			setLayout(new FlowLayout());
			
			this.add(ipLabel);
			
			//-- construct text field
			ipField = new JTextField("", 20);
			
			//--add items 
			this.add(ipField);
			
			//this.add(emailField);
			this.add(connectButton);
			this.revalidate();
		}
		
		private void functionalityHandlers() {
			connectButton = new JButton("Connect");
			connectButton.addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
				
							serveripaddress = ipField.getText();
							
							
							
							if(count % 2 == 0) {
								client = new Client(serveripaddress);
								String firstCommand = "hello";
								String firstReply = client.sendString(firstCommand);
								
								if (firstReply.equals("world!")) {
									connectButton.setText("Disconnect");
									
									addE();
									
									controlPanel.revalidate();
									controlPanel.repaint();
									
									UIPanel.outConsole.append("Successfully connected to " + serveripaddress + "\n");
								}
								else {
									connectButton.setText("Connect");
								}
							}
							//-- if count is odd
							else {
								connectButton.setText("Connect");
								client.disconnect();
								
								removeE();
								controlPanel.revalidate();
								controlPanel.repaint();
								
								UIPanel.outConsole.append("Disconnected from server " + serveripaddress + "\n");
							
							}
							count++; 
							
							System.out.println(ipField);
						}
					}
					);
		}
	}
	
	public class UsernamePanel extends JPanel {

		private static final long serialVersionUID = -4668744419229124149L;
		
		private JLabel userLabel = new JLabel("Username");
		private static JTextField usernameField;
		
		public UsernamePanel() {
			setLayout(new FlowLayout());
			
			this.add(userLabel);
			
			usernameField = new JTextField("", 15);
			
			this.add(usernameField);
			this.revalidate();
			
		}
		
	}
	
	public class PasswordPanel extends JPanel {

		private static final long serialVersionUID = -4668744419229124149L;
		
		private JLabel passwordLabel = new JLabel("Password");
		private static JTextField passwordField;
		
		public PasswordPanel() {
			
			setLayout(new FlowLayout());
			
			this.add(passwordLabel);
			
			passwordField = new JTextField("", 15);
			
			this.add(passwordField);
			this.revalidate();
			
		}
		
	}
	
	public class EmailPanel extends JPanel {

		private static final long serialVersionUID = -4668744419229124149L;
		
		private JLabel emailLabel = new JLabel("Email Address");
		private static JTextField emailField;
		
		public EmailPanel() {
			
			setLayout(new FlowLayout());
			
			this.add(emailLabel);
			
			emailField = new JTextField("", 15);
			
			this.add(emailField);
			this.revalidate();
			
		}
		
	}
	
	public class LoginPanel extends JPanel {
		private static final long serialVersionUID = 1523514871004368755L;
		
		private JButton loginButton;
		private JButton createUserButton;
		
		public LoginPanel() {
			functionalityHandlers();
			
			setLayout(new FlowLayout());
			
			this.add(loginButton);
			this.add(createUserButton);
			this.revalidate();
			
		}
		
		private void functionalityHandlers() {
			loginButton = new JButton("Login");
			loginButton.addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							
							username = UsernamePanel.usernameField.getText();
							password = PasswordPanel.passwordField.getText();
							emailAddress = EmailPanel.emailField.getText();
							
							
							
							try {
				 				Thread.sleep(1000);				 				
				 			} catch (InterruptedException e1) {
				 				e1.printStackTrace();
				 			};
				 			
				 			String command = "login";
				 			
				 			String rep = client.sendString(username + "::" + password + "::" + emailAddress);
				 			
				 			UIPanel.outConsole.append(rep + "\n");
							
							String response = client.sendString(command);

							UIPanel.outConsole.append(response + "\n");
							
							
							
							if(response.contains("uccessful")) {

								removeE();
								controlPanel.logOutBox.logInAsLabel.setText("Logged in as " + username);
								controlPanel.add(controlPanel.logOutBox);

								controlPanel.revalidate();
								controlPanel.repaint();
							}
							
						}
					}
					);
			
			createUserButton = new JButton("Create New User");
			createUserButton.addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							
							username = UsernamePanel.usernameField.getText();
							password = PasswordPanel.passwordField.getText();
							emailAddress = EmailPanel.emailField.getText();
							
							try {
				 				Thread.sleep(1000);				 				
				 			} catch (InterruptedException e1) {
				 				e1.printStackTrace();
				 			};
							
							if (!RegexEmail.validEmailAddress(emailAddress)) {
								UIPanel.outConsole.append("Error: Invalid Email Address! \n");
							}
							
							if (!RegexEmail.validPassword(password)) {
								UIPanel.outConsole.append("Error: Invalid Password Set! \n");
							}
							
							if (RegexEmail.validEmailAddress(emailAddress) && RegexEmail.validPassword(password)) {

								String command = "register";

								String rep = client.sendString(username + "::" + password + "::" + emailAddress);
								
								UIPanel.outConsole.append(rep + "\n");
								
								String response = client.sendString(command);

								UIPanel.outConsole.append(response + "\n");

							}
						}
					}
					);
		}
	}
	
	public class RecoverPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		private JButton recoverPasswordButton;
		
		public RecoverPanel() {
			functionalityHandlers();
			
			setLayout(new FlowLayout());
			
			this.add(recoverPasswordButton);
			
			this.revalidate();
			
		}
		
		private void functionalityHandlers() {
			recoverPasswordButton = new JButton("Recover Password");
			recoverPasswordButton.addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							username = UsernamePanel.usernameField.getText();
							password = PasswordPanel.passwordField.getText();
							emailAddress = EmailPanel.emailField.getText();
							
							String command = "recover";
							
							String rep = client.sendString(username + "::" + password + "::" + emailAddress);
							
							UIPanel.outConsole.append(rep + "\n");
							
							String response = client.sendString(command);

							UIPanel.outConsole.append(response + "\n");
						}
					}
					);
		}
	}
	
	public class LogOutPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		private JLabel logInAsLabel = new JLabel("Logged in as ...");
		private JButton logOutButton;
		
		public LogOutPanel() {
			functionalityHandlers();
			
			setLayout(new FlowLayout());
			
			this.add(logInAsLabel);
			this.add(logOutButton);
			
			this.revalidate();
		}
		
		private void functionalityHandlers() {
			logOutButton = new JButton("Logout");
			logOutButton.addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							String command = "logout";
							String response = client.sendString(command);
							controlPanel.remove(controlPanel.logOutBox);
							controlPanel.revalidate();
							controlPanel.repaint();
							
							addE();
							
							controlPanel.revalidate();
							UIPanel.outConsole.append(response);
						}
					}
					);
		}
	}
	
	private void removeE() {
		controlPanel.remove(controlPanel.usernameBox);
		controlPanel.remove(controlPanel.passwordBox);
		controlPanel.remove(controlPanel.emailBox);
		controlPanel.remove(controlPanel.loginBox);
		controlPanel.remove(controlPanel.recoverBox);
		controlPanel.remove(controlPanel.logOutBox);
		
		controlPanel.revalidate();
	}
	
	private void addE() {
		controlPanel.add(controlPanel.usernameBox);
		controlPanel.add(controlPanel.passwordBox);
		controlPanel.add(controlPanel.emailBox);
		controlPanel.add(controlPanel.loginBox);
		controlPanel.add(controlPanel.recoverBox);
		
		controlPanel.revalidate();
	}
	
	public static void main (String[] args) {
		new ClientUI();
		System.out.println("ClientUI thread terminating");
	}
}


