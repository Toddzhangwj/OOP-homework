import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ChessListener extends MouseAdapter implements ActionListener{
    private ChessTable chessTable;
    private int[][] board; //棋盘
    private String mode; //模式
    private int now_color; // 1代表黑 2代表白
    private int step; // 下棋步数
    private ArrayList<Chess> chessList = new ArrayList<>();

    private int x;
    private int y; //点击点
    private Graphics g;
    private Graphics2D g2d;

    private WhoWin win;


    public ChessListener(ChessTable chessTable) {
        this.chessTable = chessTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JRadioButton){		//点击单选按钮
            mode=e.getActionCommand();
        }
        else if(e.getSource() instanceof JButton){
            if(e.getActionCommand().equals("开始游戏")){
                MouseListener[] mls=chessTable.getMouseListeners();
                if(mls.length>0){		//如果还有其他监听  ---移除
                    chessTable.removeMouseListener(this);
                }
                reset();				//调用棋盘回到初始状态的方法
                System.out.println("游戏开始");
                chessTable.addMouseListener(this);		// 为棋盘添加鼠标监听
            }
            else if(e.getActionCommand().equals("悔棋")){
                if(chessList.size()>1){		//存储棋子的数组队列长度大于1
                    Chess chess=chessList.get(chessList.size()-1);   //获取最后一个被存入数组队列的棋子对象
                    int r=chess.getX();               //分别获取棋子的x与y坐标
                    int c=chess.getY();
                    board[r][c]=0;						//设置最后一步所下棋子的位置为空
                    chessList.remove(chessList.size()-1);			//移除队列中最后一个棋子对象
                    chessTable.repaint();				//调用面板重绘的方法
                    if(now_color==1){			//如果悔棋的是黑色方，下一步下棋的还是黑色方
                        now_color=2;			//如果悔棋的是白色方，下一步下棋的还是白色方
                    }else{
                        now_color=1;
                    }
                }
            }
            else if(e.getActionCommand().equals("认输")){
                if(now_color == 1){
                    JOptionPane.showMessageDialog(chessTable, "白色棋子获得胜利");
                }
                else{
                    JOptionPane.showMessageDialog(chessTable, "黑色棋子获得胜利");
                }
            }
            else if(e.getActionCommand().equals("重新开始")){
                reset();
            }

        }

    }

    public void mouseClicked(MouseEvent e){			//鼠标点击事件的处理方法
        x=e.getX();		//获取点击位置的x坐标
        y=e.getY();		//获取点击位置的y坐标
        if(mode.equals("五子棋")){
            gobangMove(x,y);
        }
        else{
            goMove(x,y);
        }
    }

    private void gobangMove(int x, int y) {
        step++;		//步数+1
        g=chessTable.getGraphics();	//从棋盘面板类中获取画布
        g2d=(Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(int  r=0;r<Config.ROWS;r++){	//行
            for(int c=0;c<Config.COLUMNS;c++){	//列
                if(board[r][c]==0){				//判断该位置上是否有棋子
                    if((Math.abs(x-Config.X0-c*Config.SIZE)<Config.SIZE/3.0)&&(Math.abs(y-Config.Y0-r*Config.SIZE)<Config.SIZE/3.0)){//将棋子放到交叉点上，误差为1/3

                        if(now_color==1){			//count 为1放置黑子
                            g2d.setColor(Color.BLACK);
                            board[r][c]=1;		//记录下了黑色棋子r与c的位置
                            g2d.fillOval(Config.X0+c*Config.SIZE-Config.CHESS_SIZE/2,Config.Y0+r*Config.SIZE-Config.CHESS_SIZE/2 , Config.CHESS_SIZE, Config.CHESS_SIZE);
                            now_color=2;			//下一次点击时  下白色的棋子
                            chessList.add(new Chess(r,c));		//将棋子对象存到数组队列中，保存了棋子的属性  r，c

                            if(win.checkGobangWin()==1){		//判断黑色棋子是否赢了
                                JOptionPane.showMessageDialog(chessTable, "黑色棋子获得胜利");
                                chessTable.removeMouseListener(this);	//获胜之后，不允许再在棋盘上下棋子，所以移除鼠标监听
                                return;
                            }

                            System.out.println("黑棋落子："+r+"行"+c+"列");
                        }else if(now_color==2){	//放置白子
                            g2d.setColor(Color.WHITE);
                            board[r][c]=2;
                            g2d.fillOval(Config.X0+c*Config.SIZE-Config.CHESS_SIZE/2,Config.Y0+r*Config.SIZE-Config.CHESS_SIZE/2 , Config.CHESS_SIZE, Config.CHESS_SIZE);
                            now_color=1;		//下一步要下黑色棋子
                            chessList.add(new Chess(r,c));		//将棋子对象存到数组队列中，保存了棋子的属性  r，c

                            if(win.checkGobangWin()==2){		//判断白色棋子是否赢了
                                JOptionPane.showMessageDialog(chessTable, "白色棋子获得胜利");
                                chessTable.removeMouseListener(this);
                                return;
                            }

                            System.out.println("白棋落子："+r+"行"+c+"列");
                        }
                    }
                }
            }
        }
    }

    private void goMove(int x,int y){

    }
    private void reset() {
        this.now_color=1;		//默认黑色棋子先下棋
        this.step=0;			//下棋步数重置为0
        for(int i = 0;i < Config.ROWS;i++){
            for (int j = 0;j < Config.COLUMNS;j++){
                board[i][j] = 0;
            }
        }
        chessList.clear();
        chessTable.repaint();   //调用棋盘重绘的方法
    }

    public void setExist(int[][] board) {
        this.board = board;
        win=new WhoWin(board);
    }
}
