package jChess;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Server {
	private static int uniqueID;
	private int port;
	private boolean keepGoing;
	private ArrayList<ClientThread> al;
	private ChessGUI sg;

	public Server(int port, ChessGUI sg) {
		this.port = port;
		this.sg = sg;
		al = new ArrayList<ClientThread>();
	}

	public void Start() {
		keepGoing = true;

		try {

			ServerSocket serversocket = new ServerSocket(port);

			while (keepGoing == true) {

				System.out.println("Server waiting for Clients on port " + port + ".");

				Socket socket = serversocket.accept();

				if (!keepGoing) {
					break;
				}
				ClientThread t = new ClientThread(socket);
				al.add(t);
				t.start();
			}

			try {
				serversocket.close();
				for (int i = 0; i < al.size(); i++) {
					ClientThread tc = al.get(i);
					try {
						tc.sInput.close();
						tc.sOutput.close();
						tc.socket.close();
					} catch (IOException e) {

					}
				}

			} catch (IOException e) {
				System.out.println("Exception on new ServerSocket " + e);
			}
		} catch (Exception e) {

		}
	}

	public void Stop() {
		keepGoing = false;
		try {
//			new Socket("localhost", port);
		} catch (Exception e) {

		}
	}

	public void BroadCast() {
		ChessMessage cm = new ChessMessage();
		
		cm.chessBoardArray = sg.getChessBoardConfig();
		cm.pieceColor = sg.getColorInfo();
		
		for(int i = al.size(); --i >= 0; ) {
			ClientThread ct = al.get(i);
			if(ct.WriteMessage(cm)) {
				
			}
		}
	}

	class ClientThread extends Thread {
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		ChessMessage cm;

		ClientThread(Socket socket) {
			this.socket = socket;

			System.out.println("Thread trying to create Object Input/Output Streams");
			try {
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput = new ObjectInputStream(socket.getInputStream());

			} catch (IOException e) {
				return;
			}

		}

		public void Run() {
			boolean keepGoing = true;

			while (keepGoing) {
				try {
					cm = (ChessMessage) sInput.readObject();
				} catch (Exception e) {
					break;
				}

				switch (cm.getType()) {
				case ChessMessage.BOARD:
					sg.serverChange(cm.chessBoardArray, cm.pieceColor);
					sg.drawBoard();
					if (sg.isInCheck() == true) {// && isCheckmate() == false) {
						sg.message.setText("CHECK!");
					}
					break;
				case ChessMessage.ERROR:
					sg.message.setText(cm.message);

					break;
				}
			}

			Close();
		}

		private void Close() {
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

		private boolean WriteMessage(ChessMessage cm) {
			if (!socket.isConnected()) {
				Close();
				return false;
			}

			try {
				sOutput.writeObject(cm);
			} catch (Exception e) {

			}

			return true;
		}
	}

}
