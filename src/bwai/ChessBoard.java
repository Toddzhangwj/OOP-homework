package bwai;

import java.util.LinkedList;

public class ChessBoard {
    private int width;
    private int height;
    private int minX = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxY = Integer.MIN_VALUE;
    private int[][] board;
    private int freeSize;

    private LinkedList<Point> trace;    // 落子轨迹

    public void setFreeSize(int freeSize) {
        this.freeSize = freeSize;
    }

    public void setTrace(LinkedList<Point> trace) {
        this.trace = trace;
    }

    public ChessBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[width][height];
        this.board[width / 2 - 1][height / 2 - 1] = 1;
        this.board[width / 2][height / 2 - 1] = -1;
        this.board[width / 2][height / 2] = 1;
        this.board[width / 2 - 1][height / 2] = -1;
        this.freeSize = width * height - 4; 
        this.minX = width / 2 - 1;
        this.maxX = width / 2;
        this.minY = height / 2 - 1;
        this.maxY = height / 2;

        this.trace = new LinkedList<>();
        this.trace.addFirst(new Point(Integer.MIN_VALUE, Integer.MIN_VALUE)); // 占位对象
        this.trace.addFirst(new Point(Integer.MIN_VALUE, Integer.MIN_VALUE)); // 占位对象
    }

    public ChessBoard(ChessBoard cb) {
        this.width = cb.width;
        this.height = cb.height;
        this.board = new int[width][height];
        this.freeSize = cb.freeSize;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.board[i][j] = cb.board[i][j];
            }
        }
        this.minX = cb.minX;
        this.maxX = cb.maxX;
        this.minY = cb.minY;
        this.maxY = cb.maxY;
    }

    public int fall(int x, int y, int piece) {
        if (x >= width || y >= height) {
            return 0;
        }

        if (board[x][y] == 0) {
            if (trans(x, y, piece) == 0) { // 未翻转任何棋子
                return 0; // 落子失败
            }

            board[x][y] = piece;
            if (x < minX) {
                minX = x;
            } else if (x > maxX) {
                maxX = x;
            }
            if (y < minY) {
                minY = y;
            } else if(y > maxY) {
                maxY = y;
            }
            this.freeSize--;

            if (this.trace != null) {
                this.trace.addLast(new Point(x, y));
                this.trace.removeFirst();
            }
            
            return piece;
        } else {
            return 0;
        }
    }

    public int trans(int x, int y, int piece) {
        int result = 0;
        // left
        result += trans(x, y, piece, -1, 0, x);
        
        // right
        result += trans(x, y, piece, 1, 0, width - x - 1);

        // top
        result += trans(x, y, piece, 0, -1, y);

        // bottom
        result += trans(x, y, piece, 0, 1, height - y - 1);

        // left-top
        result += trans(x, y, piece, -1, -1, Math.min(x, y));

        // right-top
        result += trans(x, y, piece, 1, -1, Math.min(width - x - 1, y));

        // left-bottom
        result += trans(x, y, piece, -1, 1, Math.min(x, height - y - 1));

        // right-bottom
        result += trans(x, y, piece, 1, 1, Math.min(width - x - 1, height - y - 1));
        return result;
    }

    private int trans(int x, int y, int piece, int xIncr, int yIncr, int length) {
        int result = 0;
        for (int i = 1; i <= length; i++) {
            if (board[x + xIncr * i][y + yIncr * i] == piece) {
                for (int j = 1; j < i; j++) {
                    board[x + xIncr * j][y + yIncr * j] = piece;
                    result++;
                }
                break;
            } else if (board[x + xIncr * i][y + yIncr * i] == 0) {
                break;
            }
        }
        return result;
    }

    public boolean hasChoice(Piece piece) {
        for (int x = Math.max(this.getMinX() - 1, 0); x < Math.min(this.getMaxX() + 2, this.getWidth()); x++) {
            for (int y = Math.max(this.getMinY() - 1, 0); y < Math.min(this.getMaxY() + 2, this.getHeight()); y++) {
                if (this.getBoard()[x][y] == 0) {
                    ChessBoard childCb = new ChessBoard(this);
                    if (childCb.fall(x, y, piece.val()) != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int result() {
        Integer black = 0;
        Integer white = 0;
        int[][] board = this.getBoard();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (board[x][y] == 1) {
                    black++;
                } else if (board[x][y] == -1) {
                    white++;
                } 
            }
        }
        return black.compareTo(white);
    }

    public LinkedList<Point> getTrace() {
        return trace;
    }

    public int[][] getBoard() {
        return this.board;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getFreeSize() {
        return this.freeSize;
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }
}