import javax.swing.*;
import java.awt.*;

public class FiveChess extends JPanel{
    public int[][] board = new int[Config.ROWS][Config.COLUMNS]; //棋盘
    private ChessListener cl;

    public static void main(String[] args) {
        FiveChess chess=new FiveChess();
        chess.showUI();
    }

    public void showUI(){
        JFrame frame=new JFrame();  //创建窗体  frame
        frame.setTitle("五子棋 ");   //设置窗体的标题
        frame.setSize(750,650);		//设置大小
        frame.setResizable(false);	//大小不可变
        frame.setLocationRelativeTo(null);	//窗体居中
        cl=new ChessListener(this);			// 实例化事件处理类的对象，将棋盘面板作为参数传递过去
        centerPanel(frame);         //在窗体frame上添加中间面板  ---棋盘
        eastPanel(frame);			//窗体frame上添加东边面板	 ---功能按钮

        frame.setVisible(true);		//设置窗体可见
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
}
