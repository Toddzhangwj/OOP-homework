import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class ChessListener extends MouseAdapter implements ActionListener{
    private FiveChess fiveChess;
    private int[][] board; //棋盘
    private String mode; //模式
    private int now_color; // 1代表黑 -1代表白
    private int step; // 下棋步数
    private ArrayList<Chess> chessList = new ArrayList<>();


    public ChessListener(FiveChess fiveChess) {
        this.fiveChess = fiveChess;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JRadioButton){		//点击单选按钮
            mode=e.getActionCommand();
        }
        else if(e.getSource() instanceof JButton){
            if(e.getActionCommand().equals("开始游戏")){
                MouseListener[] mls=fiveChess.getMouseListeners();
                if(mls.length>0){		//如果还有其他监听  ---移除
                    fiveChess.removeMouseListener(this);
                }
                reset();				//调用棋盘回到初始状态的方法
                System.out.println("游戏开始");
                fiveChess.addMouseListener(this);		// 为棋盘添加鼠标监听
            }
            else if(e.getActionCommand().equals("悔棋")){
                if(chessList.size()>1){		//存储棋子的数组队列长度大于1
                    Chess chess=chessList.get(chessList.size()-1);   //获取最后一个被存入数组队列的棋子对象
                    int r=chess.getX();               //分别获取棋子的x与y坐标
                    int c=chess.getY();
                    board[r][c]=0;						//设置最后一步所下棋子的位置为空
                    chessList.remove(chessList.size()-1);			//移除队列中最后一个棋子对象
                    fiveChess.repaint();				//调用面板重绘的方法
                    if(now_color==1){			//如果悔棋的是黑色方，下一步下棋的还是黑色方
                        now_color=-1;			//如果悔棋的是白色方，下一步下棋的还是白色方
                    }else{
                        now_color=1;
                    }
                }
            }
            else if(e.getActionCommand().equals("认输")){
                if(now_color == 1){
                    JOptionPane.showMessageDialog(fiveChess, "白色棋子获得胜利");
                }
                else{
                    JOptionPane.showMessageDialog(fiveChess, "黑色棋子获得胜利");
                }
            }
            else if(e.getActionCommand().equals("重新开始")){
                reset();
            }

        }

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
    }

    public void setExist(int[][] board) {
        this.board = board;
    }
}
