package jChess;

public class Queen extends Piece{

//    public Queen(boolean available, int x, int y) {
//        super(available, x, y);
//    }
	
	public Queen() {
		
	}

    @Override
    public boolean isValid(int fromX, int fromY, int toX, int toY) {
        if(super.isValid(fromX, fromY, toX, toY) == false)
            return false;
        
        //diagonal,bishop	
        if(Math.abs(toX - fromX) ==Math.abs(toY - fromY)) 		//from bishop
            return true;
        
        //straight,rook
        if(toX == fromX)			//QUEEN WORKS!!
            return true;
        if(toY == fromY)
            return true;

        return false;
    }

}
