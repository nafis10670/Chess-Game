package jChess;

public class Pawn extends Piece {
	
public Pawn() {
		
	}
@Override
public boolean isValid(int fromX, int fromY, int toX, int toY) {
    if(super.isValid(fromX, fromY, toX, toY) == false)
        return false;
    if(toX == fromX&&(toX-fromX)==1)
        return true;
//    if(toY == fromY)
//        return true;
    return false;
	}
}