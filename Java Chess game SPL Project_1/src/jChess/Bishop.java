package jChess;

public class Bishop extends Piece{

//    public Bishop(boolean available, int x, int y) {
//        super(available, x, y);
//        // TODO Auto-generated constructor stub
//    }
	
	public Bishop() {
		
	}

    public boolean isValid(int[][] pieceColor, int fromX, int fromY, int toX, int toY) {
        if(super.isValid(fromX, fromY, toX, toY) == false)
            return false;

        if(Math.abs(toX - fromX) ==Math.abs(toY - fromY) && pieceColor[fromX][fromY] != pieceColor[toX][toY] )		//BISHOP WORKS!!
            return true;

        return false;
    }

}