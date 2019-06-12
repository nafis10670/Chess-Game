package jChess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jChess.Server.ClientThread;

public class SingleServer {
	private int port;
	private boolean keepGoing;
	private ClientThread c;
	private ChessGUI cg;

	public SingleServer(int port, ChessGUI cg) {
		this.port = port;
		this.cg = cg;
		c = null;
	}

	public void Start() {
		keepGoing = true;
		try {
			ServerSocket serversocket = new ServerSocket(port);
			while (keepGoing) {
				System.out.println("Server waiting for Clients on port " + port);

				Socket socket = serversocket.accept();
				c = new ClientThread(socket);
				if (c != null)
					break;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Stop() {
			keepGoing = false;
	}
	
	public void BroadCast()
	{
		System.out.print("Broadcasting");
		ChessMessage cm = new ChessMessage();
		
		cm.chessBoardArray = cg.getChessBoardConfig();
		cm.pieceColor = cg.getColorInfo();
		
		
		while(!c.WriteMessage(cm)) {}
		
	        	c.waitForOtherPlayer(cm);
	}

	class ClientThread {
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

		public void waitForOtherPlayer(ChessMessage cm2 ) {
			boolean keepGoing = true;
				cm = null;
			while (keepGoing) {
				System.out.println("Sending");
				
				try {
					cm = (ChessMessage) sInput.readObject();
					if(cm!=null)
						break;
				} catch (Exception e) {
//					break;
				}
			}

				switch (cm.getType()) {
				case ChessMessage.BOARD:
					cg.serverChange(cm.chessBoardArray, cm.pieceColor);
					cg.drawBoard();
					if (cg.isInCheck() == true) {// && isCheckmate() == false) {
						cg.message.setText("CHECK!");
					}
					break;
				case ChessMessage.ERROR:
					cg.message.setText(cm.message);

					break;
				}
			

		}

		public boolean WriteMessage(ChessMessage cm) {

			try {
				sOutput.writeObject(cm);
			} catch (Exception e) {
				return false;
			}

			return true;
		}
	}

}
