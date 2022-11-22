package client;

import java.util.Scanner;

public class MultiThreading extends Thread {
	@Override 
	public void run() {
//		Scanner kb = new Scanner(System.in);
//		System.out.print("Server IP Address: ");
		String serveripaddress = ClientUI.ipString;
		
		// -- instantiate a Client object
		//    the constructor will attempt to connect to the server
		Client client = new Client(serveripaddress);
		
		String commandString;
		String replyString;
		
		for (int i = 0; i < 10; ++i) {
			commandString = "hello";
			ClientUI.ControlPanel.outConsole.append("CLIENT send:  " + commandString + "");
			//System.out.println("CLIENT send:  " + commandString);
			// -- send message to server and receive reply.
			ClientUI.ControlPanel.outConsole.append("\n");
			replyString = client.sendString(commandString);
			ClientUI.ControlPanel.outConsole.append("CLIENT receive: " + replyString + "\n");
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			};
		}
		client.disconnect();
	}
}
