package jChess;

import java.net.*;
import java.io.*;
import java.util.*;


public class Client {
	private ObjectInputStream sInput;
	private ObjectOutputStream sOutput;
	private Socket socket;
	private ChessGUI cg;
	
	private String server;
	private int port;
	
	Client(String server, int port, ChessGUI cg){
		this.server = server;
		this.port = port;
		this.cg = cg;
	}
	
	public boolean Start() {
		try {
			socket = new Socket(server,port); 
		}catch(Exception e) {
			System.out.println("Error connecting to server: " + e);
		}
		
//		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
//		display(msg);
		
		try {
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());

		}catch(Exception e) {
			return false;
		}
		
		new ListenFromServer().start();
		
		try {
//			sOutput.writeObject(username);
		}catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	private void display(ChessMessage cm) {
		
		switch (cm.getType()) {
		case ChessMessage.BOARD:
			cg.serverChange(cm.chessBoardArray, cm.pieceColor);
			cg.drawBoard();
			if(cg.isInCheck() == true) {// && isCheckmate() == false) {
        		cg.message.setText("CHECK!");
        	}
			break;
		case ChessMessage.ERROR:
			cg.message.setText(cm.message);
			break;
		}
	}
	
	public void sendMessage(ChessMessage cm) {
		try {
			sOutput.writeObject(cm);
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
					ChessMessage cm = (ChessMessage) sInput.readObject();
					display(cm);
				}catch(Exception e) {
					break;
				}
			}
		}
	}
	
	
}
