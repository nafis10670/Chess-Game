package jChess;

public class King extends Piece {
	
	public boolean isValid(int[][] pieceColor, int fromX, int fromY, int toX, int toY) {
		
		if (super.isValid(fromX, fromY, toX, toY) == false)
			return false;

		// diagonal,bishop
		int KingMoveX = Math.abs(toX - fromX);
		int KingMoveY = Math.abs(toY - fromY);
		if ((Math.abs(toX - fromX) == Math.abs(toY - fromY)) && (KingMoveX == 1) && (KingMoveY == 1)
				&& pieceColor[fromX][fromY] != pieceColor[toX][toY]) // from bishop
			return true;

		// straight,rook
		if ((toX == fromX) && (KingMoveY == 1) && pieceColor[fromX][fromY] != pieceColor[toX][toY])
			return true;
		if ((toY == fromY) && (KingMoveX == 1) && pieceColor[fromX][fromY] != pieceColor[toX][toY])
			return true;

		return false;
	}
}
