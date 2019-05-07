package jChess;

public class Position {
    int x;
    int y;
    Piece piece;

    public Position(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        piece = null;
    }

    public void occupyPosition(Piece piece){
        //if piece already here, delete it, i. e. set it dead
        if(this.piece != null)
            this.piece.setAvailable(false);
        //place piece here
        this.piece = piece;
    }

    public boolean isOccupied() {
        if(piece != null)
            return true;
        return false;
    }

    public Piece releaseSpot() {
        Piece releasedPiece = this.piece;
        this.piece = null;
        return releasedPiece;
    }

}
