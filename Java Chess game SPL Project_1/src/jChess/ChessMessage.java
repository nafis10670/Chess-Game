package jChess;

public class ChessMessage {
	int[][] chessBoardArray;
	int[][] pieceColor;
	int type;
	String message;
	
	static final int BOARD = 0, ERROR = -1;
	
	int getType() {
		return type;
	}
	
	String getMessage() {
		return message;
	}
	
}
