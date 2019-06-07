package jChess;

public class Rook extends Piece{

//    public Rook(boolean available, int x, int y) {
//        super(available, x, y);
//        // TODO Auto-generated constructor stub
//    }
	
	public Rook() {
		
	}

    public boolean isValid(int[][] pieceColor, int fromX, int fromY, int toX, int toY) {
        if(super.isValid(fromX, fromY, toX, toY) == false)
            return false;
        if(toX == fromX && pieceColor[fromX][fromY] != pieceColor[toX][toY])
            return true;
        if(toY == fromY && pieceColor[fromX][fromY] != pieceColor[toX][toY])
            return true;
        return false;
    }
}
