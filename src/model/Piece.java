package model;

/**

 */
public class Piece {
    private int x, y;
    private int chessMan;

    public Piece(int x, int y, int chessMan) {
        this.x = x;
        this.y = y;
        this.chessMan = chessMan;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getChessMan() {
        return chessMan;
    }

    public void setChessMan(int chessMan) {
        this.chessMan = chessMan;
    }
}
