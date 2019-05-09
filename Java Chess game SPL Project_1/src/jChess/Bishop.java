package jChess;

public class Bishop extends Piece{

//    public Bishop(boolean available, int x, int y) {
//        super(available, x, y);
//        // TODO Auto-generated constructor stub
//    }
	
	public Bishop() {
		
	}

	@Override
    public boolean isValid(int fromX, int fromY, int toX, int toY) {
        if(super.isValid(fromX, fromY, toX, toY) == false)
            return false;

        if(Math.abs(toX - fromX) ==Math.abs(toY - fromY))	
            return true;

        return false;
    }

}