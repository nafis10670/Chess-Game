package jChess;

public class Queen extends Piece{

//    public Queen(boolean available, int x, int y) {
//        super(available, x, y);
//    }
	
//	public Queen() {
//		
//	}

    public boolean isValid(int[][] pieceColor, int fromX, int fromY, int toX, int toY) {
        if(super.isValid(fromX, fromY, toX, toY) == false)
            return false;
        
        //diagonal,bishop	
        if(Math.abs(toX - fromX) ==Math.abs(toY - fromY) && pieceColor[fromX][fromY] != pieceColor[toX][toY]) 		//from bishop
            return true;
        
        //straight,rook
        if(toX == fromX && pieceColor[fromX][fromY] != pieceColor[toX][toY])			//QUEEN WORKS!!
            return true;
        if(toY == fromY && pieceColor[fromX][fromY] != pieceColor[toX][toY])
            return true;

        return false;
    }

}
