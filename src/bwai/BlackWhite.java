package bwai;

import bwai.Player.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;


public class BlackWhite extends JFrame {

    private static final long serialVersionUID = -5050817808729948877L;

    private BWState state;  // 棋局状态
    private ChessBoard cb;  // 棋盘
    private Player player1; // 玩家1，先手
    private Player player2; // 玩家2
    private Player currentPlayer;   // 当前待落子玩家

    private JButton[][] bs;     // 棋盘棋子按钮
    private JLabel scoreInfoLabel;  // 得分Label
    private JLabel currentPieceLabel;   // 当前待落棋子Label

    private JPanel p1;
    private JButton renewB=new JButton("重新开始");
    private JButton regret=new JButton("悔棋");
    private JButton renshu=new JButton("认输");
    private JButton beginB=new JButton("开始游戏");

    // 构造函数
    public BlackWhite(Player player1, Player player2, ChessBoard cb) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = this.player1;
        this.cb = cb;
        this.state = BWState.PLAYING;

        // 初始化棋盘panel
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(cb.getWidth(), cb.getHeight(), 0, 0));
        bs = new JButton[cb.getWidth()][cb.getHeight()];
        for (int y = 0; y < cb.getHeight(); y++) {
            for (int x = 0; x < cb.getWidth(); x++) {

                JButton button = new JButton();
                button.addActionListener(new HumanPlayerActionListener(x, y, this));
                button.setBackground(new Color(37, 105, 46));
                button.setSize(58, 58);
                button.setBorder(BorderFactory.createLineBorder(new Color(123, 179, 128)));
                boardPanel.add(button);
                bs[x][y] = button;
//                System.out.println(x+" "+y+" "+bs[x][y]);
            }
        }
        boardPanel.setSize(300, 500);
        this.add(boardPanel, BorderLayout.CENTER);



        JPanel p1 = new JPanel();
        this.add(p1, BorderLayout.NORTH);
        p1.add(renewB);
        p1.add(regret);
        p1.add(renshu);
        p1.add(beginB);


        renewB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] tempcb = cb.getBoard();

                int width = cb.getWidth();
                int height = cb.getHeight();
                for(int x = 0;x < height;x++){
                    for(int y = 0;y < width;y++){
                        tempcb[x][y] = 0;
                    }
                }
                tempcb[width / 2 - 1][height / 2 - 1] = 1;
                tempcb[width / 2][height / 2 - 1] = -1;
                tempcb[width / 2][height / 2] = 1;
                tempcb[width / 2 - 1][height / 2] = -1;
                for(int x = 0;x < height;x++){
                    for(int y = 0;y < width;y++){
                        if (tempcb[x][y] == Piece.BLACK.val()) {
                            bs[x][y].setIcon(Piece.BLACK.getIcon());
                        } else if (tempcb[x][y] == Piece.WHITE.val()){
                            bs[x][y].setIcon(Piece.WHITE.getIcon());
                        }
                        else{
                            bs[x][y].setIcon(null);
                        }
                        bs[x][y].setBorder(BorderFactory.createLineBorder(new Color(123, 179, 128)));
                    }
                }
                currentPlayer = player1;
                System.out.println(currentPlayer);
                // 进行逻辑处理即可
                System.out.println("****重新开始***");

            }
        });

        renshu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(null, resultMsg(-getCurrentPlayer().getPiece().val()), "黑白棋", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 设置窗体属性
        this.setTitle("黑白棋");
        this.setSize(480, 530);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(200, 200);

        // 显示
        this.setVisible(true);

        refush();
    }

    public void refush() {
        int[][] board = this.cb.getBoard();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (board[x][y] == Piece.BLACK.val()) {
                    bs[x][y].setIcon(Piece.BLACK.getIcon());
                } else if (board[x][y] == Piece.WHITE.val()){
                    bs[x][y].setIcon(Piece.WHITE.getIcon());
                }
                bs[x][y].setBorder(BorderFactory.createLineBorder(new Color(123, 179, 128)));
            }
        }

        for (Point p : cb.getTrace()) {
            if (p.getX() < 0 || p.getX() > board.length - 1 || p.getY() < 0 || p.getY() > board.length - 1) {
                continue;
            } else {
                bs[p.getX()][p.getY()].setBorder(BorderFactory.createLineBorder(Color.RED));
            }
        }
    }

    public Piece nextPlayer() {
        if (this.currentPlayer == this.player1) {
            this.currentPlayer = this.player2;
        } else {
            this.currentPlayer = this.player1;
        }

        if (this.cb.hasChoice(this.currentPlayer.getPiece())) {
            this.currentPlayer.think();
            return this.currentPlayer.getPiece();
        } else if (isFinish()) {
            this.state = BWState.FINISH;
            return null;
        } else {
            return nextPlayer();
        }
    }

    private boolean isFinish() {
        return this.cb.getFreeSize() <= 0 
            || (!this.cb.hasChoice(this.player1.getPiece()) && !this.cb.hasChoice(this.player2.getPiece()));
    }

    public void start() {
        this.currentPlayer.think();

        while (this.state != BWState.FINISH) {
            if (this.currentPlayer.getState() == State.THINKING) {
                continue;
            } else if (this.currentPlayer.getState() == State.DONE) {
                refush();
                nextPlayer();
            }
        }

        // finished
        JOptionPane.showMessageDialog(this, resultMsg(this.cb.result()), "黑白棋", JOptionPane.INFORMATION_MESSAGE);
    }


    private String resultMsg(int result) {
        return result > 0 ? "对局结束，黑方胜！" : result < 0 ? "对局结束，白方胜！" : "对局结束，双方平局！";
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public static void main(String[] args) {
        System.out.println("请输入棋盘大小8~18");
        Scanner in = new Scanner(System.in);//定义scanner，等待输入
//        int size = 8;
        int size = in.nextInt();
        System.out.println("棋盘大小为"+size+"*"+size);
        ChessBoard cb = new ChessBoard(size, size);
        System.out.println("请输入游戏模式:human-human,human-AI,AI-AI");
        in = new Scanner(System.in);//定义scanner，等待输入
        String mode = in.nextLine();
        if (mode.equals("human-human")){
            Player player1 = new HumanPlayer(cb, Piece.BLACK);
            player1.setState(State.THINKING);
            Player player2 = new HumanPlayer(cb, Piece.WHITE);
            player2.setState(State.DONE);
            BlackWhite bw = new BlackWhite(player1, player2, cb);
            bw.start();
        }
        else if(mode.equals("human-AI")){
            Player player1 = new HumanPlayer(cb, Piece.BLACK);
            player1.setState(State.THINKING);
            Player player2 = new BlackWhiteAI(cb, Piece.WHITE);
            player2.setState(State.DONE);
            BlackWhite bw = new BlackWhite(player1, player2, cb);
            bw.start();
        }
        else if(mode.equals("AI-AI")){
            Player player1 = new BlackWhiteAI(cb, Piece.BLACK);
            player1.setState(State.THINKING);
            Player player2 = new BlackWhiteAI(cb, Piece.WHITE);
            player2.setState(State.DONE);
            BlackWhite bw = new BlackWhite(player1, player2, cb);
            bw.start();
        }


    }

}