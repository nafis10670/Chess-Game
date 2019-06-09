package jChess;

public class Bishop extends Piece{

	public boolean isPathValid(int[][] chessBoardArray, int fromX, int fromY, int toX, int toY) {
		boolean valid = false;
		int i = 0, j = 0;
		int diffX = Math.abs(fromX - toX);
		int diffY = Math.abs(fromY - toY);
		
		if(diffX >= 2 && diffY >= 2) {
			
			if(fromX < toX && fromY < toY) {
				
				for(i=fromX+1,j=fromY+1; i<toX && j<toY; i++, j++) {
					
					if(chessBoardArray[i][j] == -1) {
						System.out.println("HERE");
						valid = true;
					}
					else {
						valid = false;
					}
				}
			}
			
			else if(fromX < toX && fromY > toY) {
				
				for(i=fromX+1,j=fromY-1; i<toX && j>toY; i++, j--) {
					
					if(chessBoardArray[i][j] == -1) {
						valid = true;
					}
					else {
						valid = false;
					}
				}
			}
			
			else if(fromX > toX && fromY < toY) {
				
				for(i=fromX-1,j=fromY+1; i>toX && j<toY; i--, j++) {
					
					if(chessBoardArray[i][j] == -1) {
						valid = true;
					}
					else {
						valid = false;
					}
				}
			}
			
			else if(fromX > toX && fromY > toY) {
				
				for(i=fromX-1,j=fromY-1; i>toX && j>toY; i--, j--) {
					
					if(chessBoardArray[i][j] == -1) {
						valid = true;
					}
					else {
						valid = false;
					}
				}
			}
		}
		
		else if(diffX == 1 && diffY == 1) {
			valid = true;
		}
		
		return valid;
	}

    public boolean isValid(int[][] chessBoardArray, int[][] pieceColor, int fromX, int fromY, int toX, int toY) {
        if(super.isValid(fromX, fromY, toX, toY) == false)
            return false;
        
        if(isPathValid(chessBoardArray, fromX, fromY, toX, toY) == true) {
        	
        	if(Math.abs(toX - fromX) ==Math.abs(toY - fromY) && pieceColor[fromX][fromY] != pieceColor[toX][toY] )		//BISHOP WORKS!!
                return true;

        }

        return false;
    }

}