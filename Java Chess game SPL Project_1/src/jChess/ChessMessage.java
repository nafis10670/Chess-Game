package jChess;

import java.io.Serializable;

public class ChessMessage implements Serializable {
	int[][] chessBoardArray;
	int[][] pieceColor;
	int type;
	String message;
	static final long serialVersionUID = 1112122200L;
	
	static final int BOARD = 0, ERROR = -1;
	
	int getType() {
		return type;
	}
	
	String getMessage() {
		return message;
	}
	
}
