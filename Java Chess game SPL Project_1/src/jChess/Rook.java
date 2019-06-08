package jChess;

public class Rook extends Piece{

	public boolean isPathValid(int[][] chessBoardArray, int fromX, int fromY, int toX, int toY) {
		boolean valid = false;
		
		if(fromY == toY) {
			
			if(fromX > toX) {
				
				for(int i=(fromX-1); i>=toX+1; i--) {
					
					if(chessBoardArray[i][toY] == -1) {
						valid = true;
					}
					
					else {
						valid = false;
						return valid;
					}
				}
			}
			
			if(fromX < toX) {
				
				for(int i=(fromX+1); i<=toX-1; i++) {
					
					if(chessBoardArray[i][toY] == -1) {
						valid = true;
					}
					
					else {
						valid = false;
						return valid;
					}
				}
			}
		}
		
//		if(fromX == toX) {
//			
//			if(fromX > toX) {
//				
//				for(int i=(fromX-1); i>=toX; i--) {
//					
//					if(chessBoardArray[i][toY] == -1) {
//						valid = true;
//					}
//					
//					else {
//						valid = false;
//						return valid;
//					}
//				}
//			}
//			
//			if(fromX < toX) {
//				
//				for(int i=(fromX+1); i<=toX; i++) {
//					
//					if(chessBoardArray[i][toY] == -1) {
//						valid = true;
//					}
//					
//					else {
//						valid = false;
//						return valid;
//					}
//				}
//			}
//		}
//		
		return valid;
	}

    public boolean isValid(int[][] chessBoardArray, int[][] pieceColor, int fromX, int fromY, int toX, int toY) {
        if(super.isValid(fromX, fromY, toX, toY) == false)
            return false;
        
        if(isPathValid(chessBoardArray, fromX, fromY, toX, toY) == true) {
        	
        	if(toX == fromX && pieceColor[fromX][fromY] != pieceColor[toX][toY])
                return true;
            
            if(toY == fromY && pieceColor[fromX][fromY] != pieceColor[toX][toY])
                return true;
        }
        
        return false;
    }
}
