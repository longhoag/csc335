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
	
	

	public ConnectionThread (int id, Socket socket, Server server) {
		this.server = server;
		this.id = id;
		this.name = Integer.toString(id);
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
	
	public String toString ()
	{
		return name;
	}
	
	public String getname ()
	{
		return name;
	}


	public void run() {
		DBaseConnection db = new DBaseConnection("root", "root");
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
					datain.close();
					server.removeID(id);
					go = false;
					// decrement (make static to server)
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
					//--check if user exists, matched email and password 
 					if(db.userExists(username)) {
 						if(db.getLockCount(username) < 3) {
 							if (password.equals(db.getPassword(username))) {
 								//--reset
 								db.resetLockCount(username);
 	 							dataout.writeBytes("Successful Logging in as " + username + "\n");
 	 	 						dataout.flush();
 	 						}
 	 						else {
 	 							//--increase if wrong password
 	 	 						db.increaseLockCount(username);
 	 	 						
 	 	 						dataout.writeBytes("Wrong Password! Attempt " + db.getLockCount(username)  + "\n");
 	 	 						dataout.flush();
 	 						}
 						}
 						else {
 							dataout.writeBytes(username + "was locked out of the system! Please recover your password!" + "\n");
	 	 					dataout.flush();
 						}
 						
 					}

 					else {
 						dataout.writeBytes("There's no file on the record! Please Register!" + "\n");
 						dataout.flush();
 					}
				}
				
				else if(txt.equals("register")) {
					
 					if (db.userExists(username)) {
 						dataout.writeBytes("User " + username + " already exists" + "\n");
 						dataout.flush();
 					}
 					else {
 						db.registerNewUser(username, password, email);
 						dataout.writeBytes("User " + username + " was registered" + "\n");
 						dataout.flush();
 					}
					
				}
				
				else if(txt.equals("recover")) {
					if (db.userExists(username)) {
 						SendEmailUsingGMailSMTP.SendRecoveryEmail(db.getEmailAddress(username), db.getPassword(username));
 						dataout.writeBytes("Password recovery email sent to " + email + "\n");
 						db.resetLockCount(username);
 					}
 					else {
 						dataout.writeBytes("User " + username + " does not exist" + "\n");
 					}
 					dataout.flush();
					
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
}

