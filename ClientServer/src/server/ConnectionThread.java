package server;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.w3c.dom.Text;

import client.ClientUI;
import database.DBaseConnection;
import gmail.SendEmailUsingGMailSMTP;


public class ConnectionThread extends Thread {
	private boolean go;
	private String name;
	private int id;
	
	// -- the main server (port listener) will supply the socket
	//    the thread (this class) will provide the I/O streams
	//    BufferedReader is used because it handles String objects
	//    whereas DataInputStream does not (primitive types only)
	private BufferedReader datain;
	private DataOutputStream dataout;
	
	// -- this is a reference to the "parent" Server object
	//    it will be set at time of construction
	private Server server;
	
//	public static String username = "h";
//	public static String password = "h";
//	public static String email = "j";
	
	private String username = "";
	private String password = "";
	private String email = "";
	
	private boolean isLoggedIn;
	
	

	public ConnectionThread (int id, Socket socket, Server server) {
		this.server = server;
		this.id = id;
		this.name = Integer.toString(id);
		isLoggedIn = false;
		go = true;
		
		// -- create the stream I/O objects on top of the socket
		try {
			datain = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			dataout = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	public String toString() {
		return name;
	}
	
	public String getname() {
		return name;
	}
	
	public void run() {
		// -- server thread runs until the client terminates the connection
		while (go) {
			try {
				// -- always receives a String object with a newline (\n)
				//    on the end due to how BufferedReader readLine() works.
				//    The client adds it to the user's string but the BufferedReader
				//    readLine() call strips it off
				String txt = datain.readLine();
				System.out.println("SERVER receive: " + txt);
				// -- if it is not the termination message, send it back adding the
				//    required (by readLine) "\n"

				// -- if the disconnect string is received then 
				//    close the socket, remove this thread object from the
				//    server's active client thread list, and terminate the thread
				if (txt.equals("disconnect")) {
					
					// Decrement number of connected clients
					--Server.numConnected;
					logout();
					
					datain.close();
					server.removeID(id);
					go = false;
				}
				else if (txt.equals("hello")) {
						
					dataout.writeBytes("world!" + "\n");
					dataout.flush();
					
				}
				
				else if (txt.contains("::")) {
					String[] parts = txt.split("::");
					username = parts[0];
					password = parts[1];
					email = parts[2];
					
					dataout.writeBytes("Loading..." + "\n");
						dataout.flush();
				}
				
				else if (txt.equals("login")) {
					login();
				}
				
				else if(txt.equals("logout")) {
					logout();
				}
				
				else if(txt.equals("register")) {
					register();
				}
				
				else if(txt.equals("recover")) {
					recover();
				}
				else {
					System.out.println("unrecognized commadsfand >>" + txt + "<<");
					dataout.writeBytes(txt + "\n");
					dataout.flush();
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
				go = false;
			}
			
		}
	}
	
	private void login() throws IOException {
		String output = "";
		//--check if user exists, matched email and password 
			if(Server.db.userExists(username)) {
				if(Server.db.getLockCount(username) < 3) {
					if (password.equals(Server.db.getPassword(username))) {
						//--reset
						Server.db.resetLockCount(username);
						output = "Successful Logging in as " + username + "\n";
						
						isLoggedIn = true;
						Server.usersLoggedIn.add(username);
					}
					else {
						//--increase if wrong password
						Server.db.increaseLockCount(username);
						
						output = "Wrong Password! Attempt " + Server.db.getLockCount(username)  + "\n";
					}
				}
				else {
					output = username + " was locked out of the system! Please recover your password!" + "\n";
				}
				
			}

			else {
				output = "There's no file on the record! Please Register!" + "\n";
			}
			
			dataout.writeBytes(output);
			dataout.flush();
	}
	
	private void logout() throws IOException {
		if (isLoggedIn) {
			isLoggedIn = false;
			Server.usersLoggedIn.removeElement(username);
			dataout.writeBytes("Logging Out..." + "\n");
			dataout.flush();
		}
	}
	
	private void register() throws IOException {
		String output = "";
		if (Server.db.userExists(username)) {
			output = "User " + username + " already exists" + "\n";
		}
		else {
			Server.db.registerNewUser(username, password, email);
			output = "User " + username + " was registered" + "\n";
		}
		dataout.writeBytes(output);
		dataout.flush();
	}
	
	private void recover() throws IOException {
		String output = "";
		if (Server.db.userExists(username)) {
			SendEmailUsingGMailSMTP.SendRecoveryEmail(Server.db.getEmailAddress(username), Server.db.getPassword(username));
			output = "Password recovery email sent to " + Server.db.getEmailAddress(username) + "\n";
			Server.db.resetLockCount(username);
		}
		else {
			output = "User " + username + " does not exist" + "\n";
		}
		dataout.writeBytes(output);
		dataout.flush();
	}
}

