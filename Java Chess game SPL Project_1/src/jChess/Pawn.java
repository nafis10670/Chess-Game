package jChess;

public class Pawn extends Piece {

	public boolean isInitialPosition(int fromX) {
		boolean valid = false;

		if (fromX == 6 || fromX == 1) {
			valid = true;
		}

		else {
			valid = false;
		}

		return valid;
	}

	public boolean isMovingBackward(int boardArray[][], int color[][], int fromX, int toX, int fromY, int toY) {
		boolean valid = false;

		if (color[fromX][fromY] == 1 && fromX <= toX) {
			valid = true;
		}

		else if (color[fromX][fromY] == 0 && fromX >= toX) {
			valid = true;
		}

		return valid;
	}

	public boolean isPieceAvailable(int[][] boardArray, int x, int y) {
		boolean valid = true;

		if (boardArray[x][y] == -1) {
			valid = false;
		}

		return valid;
	}

	public boolean isPathValid(int[][] chessBoardArray, int fromX, int fromY, int toX, int toY) {
		boolean valid = false;

		if (fromX > toX && isInitialPosition(fromX) == true && fromY == toY) {

			if (chessBoardArray[fromX - 1][toY] == -1) {
				valid = true;
				return valid;
			}

			else {
				valid = false;
				return valid;
			}
		}

		else if (fromX < toX && isInitialPosition(fromX) == true && fromY == toY) {

			if (chessBoardArray[fromX + 1][toY] == -1) {
				valid = true;
				return valid;
			}

			else {
				valid = false;
				return valid;
			}
		}

		else if (isInitialPosition(fromX) == false && fromY == toY) {
			if (chessBoardArray[toX][toY] == -1) {
				valid = true;
			}

			else {
				valid = false;
				return valid;
			}
		}

		else {
			valid = true;
			return valid;
		}

		return valid;
	}

	public boolean isValid(int[][] chessBoardArray, int[][] pieceColor, int fromX, int fromY, int toX, int toY) {
		int MoveX = Math.abs(toX - fromX);
		int MoveY = Math.abs(toY - fromY);

		if (super.isValid(fromX, fromY, toX, toY) == false)
			return false;

		if (isPathValid(chessBoardArray, fromX, fromY, toX, toY) == true) {
			// System.out.println("IN PATH_VALID IF CONDITION");

			if (toY == fromY && (isInitialPosition(fromX) == true) && (Math.abs(MoveX)) <= 2
					&& (isMovingBackward(chessBoardArray, pieceColor, fromX, toX, fromY, toY) == false)
					&& pieceColor[fromX][fromY] != pieceColor[toX][toY]) {
				// System.out.println("IN PATH_VALID LOOP 1");
				return true;
			}

			else if (toY == fromY && (isInitialPosition(fromX) == false) && (Math.abs(MoveX)) <= 1
					&& (isMovingBackward(chessBoardArray, pieceColor, fromX, toX, fromY, toY) == false)
					&& pieceColor[fromX][fromY] != pieceColor[toX][toY]) {
				// System.out.println("IN PATH_VALID LOOP 2");
				return true;
			}

			else if (toY != fromY && (Math.abs(MoveX)) == 1 && (Math.abs(MoveY)) == 1
					&& (isPieceAvailable(chessBoardArray, toX, toY) == true)
					&& pieceColor[fromX][fromY] != pieceColor[toX][toY]) {
				// System.out.println("IN PATH_VALID LOOP 3");
				return true;
			}
		}

		return false;
	}
}