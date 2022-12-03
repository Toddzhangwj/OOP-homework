package control;

import model.Board;
import model.Piece;
import model.PieceStatus;

import java.util.Map;

public class utils {
    public static void copyBFromA(Board a, Board b){

        int[][] tmp = new int[a.getBoard().length][a.getBoard()[0].length];
        copyBFromA(a.getBoard(),tmp);
        b.setBoard(tmp);

        Piece[][] tmp_pieces = new Piece[a.getBoard().length][a.getBoard()[0].length];
        copyBFromA(a.getPieces(),tmp_pieces);
        b.setPieces(tmp_pieces);

        int[] tmp_ko = new int[2];
        copyBFromA(a.getKo(),tmp_ko);
        b.setKo(tmp_ko);
    }

    public static void copyBFromA(int[][] a,int[][] b){
        int n = a.length;
        int m = a[0].length;
        for(int i = 0;i < n;i++){
            for(int j = 0;j < m;j++){
                b[i][j] = a[i][j];
            }
        }

    }

    public static void copyBFromA(Piece[][] a, Piece[][] b){
        int n = a.length;
        int m = a[0].length;
        for(int i = 0;i < n;i++){
            for(int j = 0;j < m;j++){
                b[i][j] = a[i][j];
            }
        }

    }

    public static void copyBFromA(int[] a,int[] b){
        int n = a.length;
        for(int i = 0;i < n;i++){
            b[i] = a[i];
        }

    }


    public static void copyBFromA(Map<Piece, PieceStatus> buffer, Map<Piece, PieceStatus> buffer1) {
        for(Piece key: buffer.keySet()){
            buffer1.put(key,buffer.get(key));
        }
    }
}
