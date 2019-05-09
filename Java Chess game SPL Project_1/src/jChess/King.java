package jChess;

public class King extends Piece{

//    public King(boolean available, int x, int y) {
//        super(available, x, y);
//        // TODO Auto-generated constructor stub
//    }
	
	public King() {
		
	}

    @Override
//    public boolean isValid(int fromX, int fromY, int toX, int toY) {
//        if(super.isValid(fromX, fromY, toX, toY) == false)
//            return false;
//        if(Math.sqrt(Math.pow(Math.abs((toX - fromX)),2)) + Math.pow(Math.abs((toY - fromY)), 2) != Math.sqrt(2)){		//DOES NOT WORK
//            return false;
//        }
//        return true;
//    }
    public boolean isValid(int fromX, int fromY, int toX, int toY) {
        if(super.isValid(fromX, fromY, toX, toY) == false)
            return false;
        //diagonal,bishop
        int KingMoveX = Math.abs(toX - fromX);
        int KingMoveY = Math.abs(toY - fromY);
        if((Math.abs(toX - fromX) ==Math.abs(toY - fromY))&&(KingMoveX==1)&&(KingMoveY==1)) //from bishop
            return true;
        //straight,rook
        if((toX == fromX)&&(KingMoveY==1))
            return true;
        if((toY == fromY)&&(KingMoveX==1))
            return true;

        return false;
    }

}
