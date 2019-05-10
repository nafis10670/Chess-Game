package jChess;

public class Pawn extends Piece {
	
public Pawn() {
		
	}

public boolean isInitialPosition(int fromX) {
	boolean valid = false;
	
	if(fromX == 6 || fromX == 1) {
		valid = true;
	}
	
	else {
		valid = false;
	}
	
	return valid;
}

//public boolean isPieceAvailable(int toX, int toY) {
//	boolean valid = false;
//	
//	if(toX != BLANK)
//	return valid;
//}

@Override
public boolean isValid(int fromX, int fromY, int toX, int toY) {
	 int MoveX = Math.abs(toX - fromX);
     int MoveY = Math.abs(toY - fromY);
	
	if(super.isValid(fromX, fromY, toX, toY) == false)
        return false;

    
    if(toY == fromY && (isInitialPosition(fromX) == true) &&  (Math.abs(MoveX)) <= 2) {			//NEED TO IMPLEMENT ISPIECEAVAILABLE METHOD
    																							//CANNOT IMPORT CHESSGUI BECAUSE DEFAULT PACKAGE
    	return true;
    }
    
    else if(toY == fromY && (isInitialPosition(fromX) == false) &&  (Math.abs(MoveX)) <= 1) {
    	return true;
    }

    return false;
	}
}