import java.util.HashMap;
import java.util.Map;

public class WhoWin {
    private int[][]	board;
    public WhoWin(int board[][]){
        this.board=board;
    }

    public int checkGobangWin(){
        if((rowWin()==1)||(columnWin()==1)||(rightSideWin()==1)||(leftSideWin()==1)){
            return 1;
        }else if((rowWin()==2)||(columnWin()==2)||(rightSideWin()==2)||(leftSideWin()==2)){
            return 2;
        }
        return 0;
    }

    public int checkGoWin(){
        int n = board.length;
        int m = board[0].length;
        int cnt_black = 0;
        int cnt_white = 0;


        for(int i = 0;i < n;i++){
            for (int j = 0;j < m;j++) {

                if (board[i][j] == 1) {
                    cnt_black += 1;
                }
                else if(board[i][j] == 2) {
                    cnt_white += 1;
                }

            }
        }
        if(cnt_black*3 > 184.25){
            return 1;
        }
        if(cnt_white*3 > 176.75){
            return 2;
        }
        return 0;
    }

    /**
     * 以行的方式赢
     */
    public int rowWin(){
        for(int i=0;i<Config.ROWS;i++){
            for(int j=0;j<Config.COLUMNS-4;j++){
                if(board[i][j]==2){
                    if(board[i][j+1]==2&&board[i][j+2]==2&&board[i][j+3]==2&&board[i][j+4]==2){
                        return 2;
                    }
                }
                if(board[i][j]==1){
                    if(board[i][j+1]==1&&board[i][j+2]==1&&board[i][j+3]==1&&board[i][j+4]==1){
                        return 1;
                    }
                }
            }
        }
        return 0;
    }
    /**
     * 以列的方式赢
     */
    public int columnWin(){
        for(int i=0;i<Config.ROWS-4;i++){
            for(int j=0;j<Config.COLUMNS;j++){
                if(board[i][j]==2){
                    if(board[i+1][j]==2&&board[i+2][j]==2&&board[i+3][j]==2&&board[i+4][j]==2){
                        return 2;
                    }
                }
                if(board[i][j]==1){
                    if(board[i+1][j]==1&&board[i+2][j]==1&&board[i+3][j]==1&&board[i+4][j]==1){
                        return 1;
                    }
                }
            }
        }
        return 0;
    }
    /**
     * 斜的方式赢
     */
    public int rightSideWin(){  //正斜
        for(int i=0;i<Config.ROWS-4;i++){
            for(int j=0;j<Config.COLUMNS-4;j++){
                if(board[i][j]==2){
                    if(board[i+1][j+1]==2&&board[i+2][j+2]==2&&board[i+3][j+3]==2&&board[i+4][j+4]==2){
                        return 2;
                    }
                }
                if(board[i][j]==1){
                    if(board[i+1][j+1]==1&&board[i+2][j+2]==1&&board[i+3][j+3]==1&&board[i+4][j+4]==1){
                        return 1;
                    }
                }
            }
        }
        return 0;
    }
    public int leftSideWin(){   //反斜
        for(int i=4;i<Config.ROWS;i++){
            for(int j=0;j<Config.COLUMNS-4;j++){
                if(board[i][j]==2){
                    if(board[i-1][j+1]==2&&board[i-2][j+2]==2&&board[i-3][j+3]==2&&board[i-4][j+4]==2){
                        return 2;
                    }
                }
                if(board[i][j]==1){
                    if(board[i-1][j+1]==1&&board[i-2][j+2]==1&&board[i-3][j+3]==1&&board[i-4][j+4]==1){
                        return 1;
                    }
                }
            }
        }
        return 0;
    }
}
