package jChess;

public class Queen extends Piece {

	public boolean isPathValid(int[][] chessBoardArray, int fromX, int fromY, int toX, int toY) {
		boolean valid = false;
		int i, j;
		int diffX = Math.abs(fromX - toX);
		int diffY = Math.abs(fromY - toY);

		if (fromY == toY) { 		// ROOK PART

			if (fromX > toX && diffX >= 2) {

				for (i = (fromX - 1); i > toX; i--) {

					if (chessBoardArray[i][toY] == -1) {
						valid = true;
					}

					else {
						valid = false;
						return valid;
					}
				}
			}

			else if (fromX < toX && diffX >= 2) {

				for (i = (fromX + 1); i < toX; i++) {
					// System.out.println("HERE");

					if (chessBoardArray[i][toY] == -1) {
						valid = true;
					}

					else {
						valid = false;
						return valid;
					}
				}
			}

			else if (diffX == 1) {
				valid = true;
			}
		}

		if (fromX == toX) {

			if (fromY > toY && diffY >= 2) {

				for (i = (fromY - 1); i > toY; i--) {

					if (chessBoardArray[toX][i] == -1) {
						valid = true;
					}

					else {
						valid = false;
						return valid;
					}
				}
			}

			else if (fromY < toY && diffY >= 2) {

				for (i = (fromY + 1); i < toY; i++) {
					// System.out.println("HERE");

					if (chessBoardArray[toX][i] == -1) {
						valid = true;
					}

					else {
						valid = false;
						return valid;
					}
				}
			}

			else if (diffY == 1) {
				valid = true;
			}
		} 						// ROOK PART END
		

		if (diffX >= 2 && diffY >= 2) {				//BISHOP PART

			if (fromX < toX && fromY < toY) {

				for (i = fromX + 1, j = fromY + 1; i < toX && j < toY; i++, j++) {

					if (chessBoardArray[i][j] == -1) {
//						System.out.println("HERE");
						valid = true;
					} else {
						valid = false;
						return valid;
					}
				}
			}

			else if (fromX < toX && fromY > toY) {

				for (i = fromX + 1, j = fromY - 1; i < toX && j > toY; i++, j--) {

					if (chessBoardArray[i][j] == -1) {
						valid = true;
					} else {
						valid = false;
						return valid;
					}
				}
			}

			else if (fromX > toX && fromY < toY) {

				for (i = fromX - 1, j = fromY + 1; i > toX && j < toY; i--, j++) {

					if (chessBoardArray[i][j] == -1) {
						valid = true;
					} else {
						valid = false;
						return valid;
					}
				}
			}

			else if (fromX > toX && fromY > toY) {

				for (i = fromX - 1, j = fromY - 1; i > toX && j > toY; i--, j--) {

					if (chessBoardArray[i][j] == -1) {
						valid = true;
					} else {
						valid = false;
						return valid;
					}
				}
			}
		}

		else if (diffX == 1 && diffY == 1) {
			valid = true;
		}									//BISHOP PART END

		return valid;
	}

	public boolean isValid(int[][] chessBoardArray, int[][] pieceColor, int fromX, int fromY, int toX, int toY) {
		if (super.isValid(fromX, fromY, toX, toY) == false)
			return false;
		
		if(isPathValid(chessBoardArray, fromX, fromY, toX, toY) == true) {
			
			// diagonal,bishop
			if (Math.abs(toX - fromX) == Math.abs(toY - fromY) && pieceColor[fromX][fromY] != pieceColor[toX][toY]) // from
																													// bishop
				return true;

			// straight,rook
			if (toX == fromX && pieceColor[fromX][fromY] != pieceColor[toX][toY])
				return true;
			if (toY == fromY && pieceColor[fromX][fromY] != pieceColor[toX][toY])
				return true;
		}
		
		return false;
	}
}
