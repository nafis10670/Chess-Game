package jChess;

import java.net.*;
import java.io.*;
import java.util.*;


public class Client {
	private ObjectInputStream sInput;
	private ObjectOutputStream sOutput;
	private Socket socket;
	private ChessGUI cg;
	
	private String server, username;
	private int port;
	
	Client(String server, int port, String username, ChessGUI cg){
		this.server = server;
		this.port = port;
		this.username = username;
		this.cg = cg;
//		System.out.println("Initializing");
	}
	
	public boolean Start() {
		try {
			socket = new Socket(server,port); 
		}catch(Exception e) {
			System.out.println("Error connecting to server: " + e);
			return false;
		}
		
		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		System.out.println(msg);
		
		try {
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());

		}catch(Exception e) {
			return false;
		}
		
//		waitForOtherPlayer();
		
		new ListenFromServer().start();
		
		try {
			sOutput.writeObject(username);
		}catch(Exception e) {
			System.out.println("Exception doing login : " + e);
			disconnect();
			return false;
		}
		
		return true;
	}
	
	private void display(ChessMessage cm) {
		
		switch (cm.getType()) {
		case ChessMessage.BOARD:
			cg.serverChange(cm.chessBoardArray, cm.pieceColor);
			cg.drawBoard();
			cg.currentMove = 1 - cg.currentMove;
			if(cg.isInCheck() == true) {// && isCheckmate() == false) {
        		cg.message.setText("CHECK!");
        	}
			break;
		case ChessMessage.ERROR:
			cg.message.setText(cm.message);
			break;
		}
	}
	
	public void sendMessage(ChessMessage msg) {
		
		try {
			sOutput.writeObject(msg);
		}catch(Exception e) {
			
		}
	}
	
	private void disconnect() {
		try {
			if (sOutput != null)
				sOutput.close();
		} catch (Exception e) {

		}

		try {
			if (sInput != null)
				sInput.close();
		} catch (Exception e) {

		}
		try {
			if (socket != null)
				socket.close();
		} catch (Exception e) {

		}
	}
	
	class ListenFromServer extends Thread{
		public void run() {
			while(true) {
				try {
					ChessMessage msg = (ChessMessage) sInput.readObject();
					display(msg);
				}catch(Exception e) {
					System.out.println("Exception: " + e);
				}
			}
		}
	}
}
