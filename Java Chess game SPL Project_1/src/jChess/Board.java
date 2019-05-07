package jChess;

public class Board {
    private Position[][] Position = new Position[8][8];

    public Board() {
        super();
        for(int i=0; i<Position.length; i++){
            for(int j=0; j<Position.length; j++){
                this.Position[i][j] = new Position(i, j);
            }
        }
    }

    public Position getPos(int x, int y) {
        return Position[x][y];
    }

}
