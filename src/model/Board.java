package model;

/**
 *
 * 棋盘类主要是存储棋盘，单例模式
 * 主要是三个数组
 * board数组存储棋盘状态
 * pieces数组主要是用来判断棋子的死活，这个具体怎么优化我还没想好，但是不是最完善的
 * ko数组是用来存储禁着点，也就是打劫
 */
public class Board {
//    private static Board instance = new Board();

    private int[][] board = new int[19][19];
    private Piece[][] pieces = new Piece[19][19];
    private int[] ko = {-1, -1};

//    public static Board getInstance() {
//        return instance;
//    }

//    public static void setInstance(Board instance) {
//        Board.instance = instance;
//    }


    public void setBoard(int[][] board) {
        this.board = board;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public void setPieces(Piece[][] pieces) {
        this.pieces = pieces;
    }

    public int[] getKo() {
        return ko;
    }

    public void setKo(int[] ko) {
        this.ko = ko;
    }

    public Board() {}
    public Piece newPiece(int x, int y, int chessMan) {
        if (x > 18 || x < 0 || y > 18 || y < 0) {
            return null;
        }
        if (pieces[x][y] == null) {
            pieces[x][y] = new Piece(x, y, chessMan);
        }
        pieces[x][y].setChessMan(chessMan);
        return pieces[x][y];
    }

//    public static Board newInstance() {
//        return instance;
//    }

    public int[][] getBoard() {
        return board;
    }

    public int getcell(int x, int y) {
        return board[x][y];
    }

    public boolean setCell(Piece piece) {
//        System.out.println("Board里的board");
//        BoardView.printBoard(board);
        if (board[piece.getX()][piece.getY()] != 0) {
            return false;
        }
        board[piece.getX()][piece.getY()] = piece.getChessMan();
        return true;
    }

    public void setKo(int x, int y) {
        ko[0] = x;
        ko[1] = y;
    }

    public boolean isKo(int x, int y) {
        return ko[0] == x && ko[1] == y;
    }
}
