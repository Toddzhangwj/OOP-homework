import javax.swing.*;
import java.awt.*;

public class ChessTable extends JPanel{
    public int[][] board = new int[Config.ROWS][Config.COLUMNS]; //棋盘
    private ChessListener cl;

    public static void main(String[] args) {
        ChessTable chessTable=new ChessTable();
        chessTable.showUI();
    }


    public void showUI(){
        JFrame frame=new JFrame();  //创建窗体  frame
        frame.setTitle("棋类游戏");   //设置窗体的标题
        frame.setSize(750,650);		//设置大小
        frame.setResizable(false);	//大小不可变
        frame.setLocationRelativeTo(null);	//窗体居中
        cl=new ChessListener(this);			// 实例化事件处理类的对象，将棋盘面板作为参数传递过去
        centerPanel(frame);         //在窗体frame上添加中间面板  ---棋盘
        eastPanel(frame);			//窗体frame上添加东边面板	 ---功能按钮

        frame.setVisible(true);		//设置窗体可见
        cl.setExist(board);			//将棋子数组传入到事件监听类中
    }

    public void centerPanel(JFrame frame){
        this.setBackground(Color.ORANGE);
        frame.add(this);
    }


    public void eastPanel(JFrame frame){
        JPanel epanel=new JPanel();			//创建一个面板对象
        epanel.setBackground(Color.GRAY);	//背景颜色设置为gray
        epanel.setPreferredSize(new Dimension(150,600));   //设置大小
        epanel.setLayout(new FlowLayout());//默认也是流式布局
        String[] buttonArray={"开始游戏","悔棋","认输","重新开始"}; //数组存储     功能按钮命令

        for(int i=0;i<buttonArray.length;i++){			//使用循环实例化按钮对象
            JButton button=new JButton(buttonArray[i]); //实例化按钮对象
            button.setPreferredSize(new Dimension(100,50));	//设置大小
            epanel.add(button);							//在面板上添加按钮
            button.addActionListener(cl);				//为每一个按钮添加监听
        }

        String[] radioArray={"五子棋","围棋"};	//数组存储      单选按钮命令
        ButtonGroup bg=new ButtonGroup();			//实例化一个按钮组的对象
        for(int i=0;i<radioArray.length;i++){		//使用循环创建单选按钮对象
            JRadioButton radioButton=new JRadioButton(radioArray[i]);	//实例化单选按钮对象
            bg.add(radioButton);					//将每个创建的单选按钮添加到同一组中
            radioButton.setPreferredSize(new Dimension(100,50));	//设置单选按钮大小
            radioButton.setOpaque(false);			//设置不透明
            radioButton.setForeground(Color.WHITE);	//前景色为白色
            if(i==0){								//默认选中第一个单选按钮
                radioButton.setSelected(true);
            }
            epanel.add(radioButton);				//在面板上添加单选按钮
            radioButton.addActionListener(cl);		//加监听器
        }
        frame.add(epanel,BorderLayout.EAST);		//为窗体(边框布局)添加面板---放置在东侧
    }

    public void paint(Graphics g){
        super.paint(g);
        drawChessTable(g);
        reDrawChess(g);
    }

    public void drawChessTable(Graphics g){
        for(int r=0;r<Config.ROWS;r++){    			//行           x 不变    y变
            g.drawLine(Config.X0, Config.Y0+r*Config.SIZE, Config.X0+(Config.COLUMNS-1)*Config.SIZE, Config.Y0+r*Config.SIZE);
        }
        for(int c=0;c<Config.COLUMNS;c++){			//列            x变         y不变
            g.drawLine(Config.X0+Config.SIZE*c,Config.Y0, Config.X0+Config.SIZE*c, Config.Y0+(Config.ROWS-1)*Config.SIZE);
        }
    }

    public void reDrawChess(Graphics g){
        Graphics2D g2d=(Graphics2D) g;		//转为Graphics2D   后面要为画笔设置颜色
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(int  r=0;r<Config.ROWS;r++){			//外循环控制行
            for(int c=0;c<Config.COLUMNS;c++){		//内循环控制列
                if(board[r][c]!=0){			//如果该位置不为空
                    if(board[r][c]==1){			//该位置是黑子
                        g2d.setColor(Color.BLACK);
                        g2d.fillOval(Config.X0+c*Config.SIZE-Config.CHESS_SIZE/2,Config.Y0+r*Config.SIZE-Config.CHESS_SIZE/2 , Config.CHESS_SIZE, Config.CHESS_SIZE);
                    }else if(board[r][c]==2){  //该位置是白子
                        g2d.setColor(Color.WHITE);
                        g2d.fillOval(Config.X0+c*Config.SIZE-Config.CHESS_SIZE/2,Config.Y0+r*Config.SIZE-Config.CHESS_SIZE/2 , Config.CHESS_SIZE, Config.CHESS_SIZE);
                    }
                }
            }
        }
    }
}
