package jChess;

public class Rook extends Piece{

//    public Rook(boolean available, int x, int y) {
//        super(available, x, y);
//        // TODO Auto-generated constructor stub
//    }
	
	public Rook() {
		
	}


    @Override
    public boolean isValid(int fromX, int fromY, int toX, int toY) {
        if(super.isValid(fromX, fromY, toX, toY) == false)
            return false;
        if(toX == fromX)
            return true;
        if(toY == fromY)
            return true;
        return false;
    }
}
