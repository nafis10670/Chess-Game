package jChess;

public class Pawn extends Piece {
	
public Pawn() {
		
	}
@Override
public boolean isValid(int fromX, int fromY, int toX, int toY) {
    if(super.isValid(fromX, fromY, toX, toY) == false)
        return false;
//    if(toX == fromX)
//        return true;
    int PawnMoveY=toY-fromY;
    if((toY == fromY)&&(PawnMoveY==2))
        return true;
    return false;
}
}