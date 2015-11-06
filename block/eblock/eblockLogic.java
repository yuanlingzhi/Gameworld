package eblock;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class eblockLogic extends JPanel implements ActionListener,KeyListener,Runnable
{
	//Timer t;
	Thread t;
	boolean start=false;
	Random r=new Random();
	int score;
	int flag;
	int temp;
	int speed=1;
	int line=0;
	static int totalLine=0;
	JButton startGame=new JButton("开始");
	JButton stopGame=new JButton("退出");
	block bt[]=new block[4];    //bt==block temp;   //主要操作的方块
	block bt1[]=new block[4];   //下一块中的方块
	int map[][]=new int [10][18];         //10为 x 为列， 18为y 为行
	JDialog dialog;
	JButton ok;
	JLabel label;
	eblockLogic()
	{
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(startGame);
		add(stopGame);
		startGame.addActionListener(this);
		stopGame.addActionListener(this);
		
		dialog=new JDialog();
		ok=new JButton("ok");
		label=new JLabel();
		dialog.setTitle("游戏结束啦");
		dialog.setSize(200,150);
		dialog.setVisible(false);
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		dialog.setLocation((screenSize.width-200)/2,(screenSize.height-150)/2);	
		dialog.setLayout(new GridLayout(2,1));
		dialog.add(label);
		dialog.add(ok);
		
		ok.addActionListener(this);
		
		this.addKeyListener(this);	
		for (int i = 0; i < bt.length; i++) 
		{
			bt[i]=new block();  
			bt1[i]=new block();               //实例化block数组里面的4个对象
		}
		for (int i = 0; i <10; i++)           //为0的时候不绘制 ; 
		{
			for (int j = 0; j < 18; j++)
			{
				map[i][j]=0;
			}
		}
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		g.setColor(Color.BLACK);
		g.fillRect(9, 10, 200, 360);
		g.drawString("分数:"+score, 220, 60);
		g.setColor(Color.RED);
		g.drawLine(220, 65, 360, 65);
		g.setColor(Color.BLACK);
		g.drawString("速度:"+speed, 220, 90);
		g.setColor(Color.RED);
		g.drawLine(220, 95, 360, 95);
		g.setColor(Color.BLACK);
		g.drawString("下一个方块" , 260, 130);
		g.setFont(new Font("SanSerif",Font.ITALIC,12));
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(),BorderFactory.createRaisedBevelBorder()));
		g.setColor(Color.DARK_GRAY);
		g.drawString("神奇小点:", 215, 320);
		g.setFont(new Font("SanSerif",Font.PLAIN,11));
		g.drawString("穿越砖块的刺激", 270, 320);
		g.setColor(Color.BLACK);
		g.setFont(new Font("SanSerif",Font.BOLD,13));
		g.drawString("俄罗斯方块2.0 " , 215, 340);
		g.drawString("YLZ作品" , 215, 360);		
	//	g.setColor(new Color(0,255,0));
	//	g2.fill3DRect(10*20, 10*20, 20, 20,true);
		//  开始画方块了
		
		if(start)
		{
			g2.setColor(Color.YELLOW);                   //先画地图
			for (int i = 0; i < 10; i++)
			{				
				for (int j = 0; j <18; j++) 
				{
					if(map[i][j]==1)
						{
							g2.fill3DRect(10+i*20, 10+j*20, 20, 20,true);
						}
				}
			}
			if(bt[0].getX()==bt[1].getX()&&bt[0].getY()==bt[1].getY())    //画小点
			{
				g2.setColor(Color.CYAN);
				g2.fill3DRect(10+bt[0].getX()*20, 10+bt[0].getY()*20, 20, 20,true);
			}
			else                   //画其他
			{
				g2.setColor(Color.YELLOW);
				for (int i = 0; i < 4; i++) 
				{
					g2.fill3DRect(10+bt[i].getX()*20, 10+bt[i].getY()*20, 20, 20,true);				
				}
			}
			g2.setColor(Color.GREEN);
	//		if(temp==7)
	//		{
	//			g2.fill3DRect(bt1[0].getX()*20, 180+bt1[0].getY()*20, 20, 20,true);
	//			System.out.println("!!");
	//		}
			for (int i = 0; i <4; i++)
			{
				g2.fill3DRect(183+bt1[i].getX()*20, 180+bt1[i].getY()*20, 20, 20,true);
			}
		}		
	}
	public boolean clear_map1()
	{
		//构建一个虚拟map1 用来判断 小点所走的每一步是否都满足消行；
		int map1[][]=new int[10][18];
		for (int a = 0; a <10; a++)            
		{
			for (int b = 0; b < 18; b++)
			{
				map1[a][b]=map[a][b];
			}
		}
		map1[bt[0].getX()][bt[0].getY()]=1;
		int temp;
		for (int i = 17; i >=0; i--) 
		{
			temp=0;
			for (int j = 0; j < 10; j++) 
			{
				if(map1[j][i]==1)
				{
					temp++;
				}
				else 
				{
					break;
				}
				if(temp==10)
				{
		//			System.out.println("hhaa");
					totalLine++;
		//			System.out.println(totalLine);
					if(totalLine%10==0&&totalLine!=0)
					{
		//				System.out.println(totalLine);
						speed++;
					}
		//			System.out.println(totalLine);
					line++;
					score+=10*speed;
					for (int i1=i; i1 >0; i1--)   //不能让i1=0  数组越界
					{
		//				System.out.println("把第"+(i1-1)+"行"+"赋值给了"+i1+"行");
						for (int j1 = 0; j1 < 10; j1++)
						{
							map1[j1][i1]=map1[j1][i1-1];
						}
					}
					i++;
					while(line>0)
					{
						for (int i2 = 0; i2 < 10; i2++) 
						{
							map1[i2][line-1]=0;
						}
						line--;
					}
					//消行消得是 map1 虚拟的地图    要把虚拟地图重新赋值给map
					for (int a1 = 0; a1 <10; a1++)            
					{
						for (int b1 = 0; b1 < 18; b1++)
						{
							map[a1][b1]=map1[a1][b1];
						}
					}
					return true;
				}
			}
		}
		return false;
	}
	public void clear_map()
	{
		int temp;
		for (int i = 17; i >=0; i--) 
		{
			temp=0;
			for (int j = 0; j < 10; j++) 
			{
				if(map[j][i]==1)
				{
					temp++;
				}
				else 
				{
					break;
				}
				if(temp==10)
				{
		//			System.out.println("hhaa");
					totalLine++;
		//			System.out.println(totalLine);
					if(totalLine%10==0&&totalLine!=0)
					{
		//				System.out.println(totalLine);
						speed++;
					}
		//			System.out.println(totalLine);
					line++;
					score+=10*speed;
					for (int i1=i; i1 >0; i1--)   //不能让i1=0  数组越界
					{
		//				System.out.println("把第"+(i1-1)+"行"+"赋值给了"+i1+"行");
						for (int j1 = 0; j1 < 10; j1++)
						{
							map[j1][i1]=map[j1][i1-1];
						}
					}
					i++;
					while(line>0)
					{
						for (int i2 = 0; i2 < 10; i2++) 
						{
							map[i2][line-1]=0;
						}
						line--;
					}
				}
			}
		}
	}
/*	private class keepMove implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(start)
				down();
		}		
	}
*/
	private void Setbt1(int temp)
	{
		switch (temp) {
		case 0:                             //长条
			bt1[0].setX(3);bt1[0].setY(0);
			bt1[1].setX(4);bt1[1].setY(0);
			bt1[2].setX(5);bt1[2].setY(0);
			bt1[3].setX(6);bt1[3].setY(0);			
			break;
		case 1:                             //田字形
			bt1[0].setX(4);bt1[0].setY(0);
			bt1[1].setX(5);bt1[1].setY(0);
			bt1[2].setX(4);bt1[2].setY(1);
			bt1[3].setX(5);bt1[3].setY(1);			
			break;
		case 2:                              //L
			bt1[0].setX(4);bt1[0].setY(0);
			bt1[1].setX(4);bt1[1].setY(1);
			bt1[2].setX(4);bt1[2].setY(2);
			bt1[3].setX(5);bt1[3].setY(2);			
			break;
		case 3:                               //反L
			bt1[0].setX(5);bt1[0].setY(0);
			bt1[1].setX(5);bt1[1].setY(1);
			bt1[2].setX(5);bt1[2].setY(2);
			bt1[3].setX(4);bt1[3].setY(2);			
			break;
		case 4:                              //土  型
			bt1[0].setX(5);bt1[0].setY(0);
			bt1[1].setX(4);bt1[1].setY(1);
			bt1[2].setX(5);bt1[2].setY(1);
			bt1[3].setX(6);bt1[3].setY(1);			
			break;
		case 5:                                //z
			bt1[0].setX(4);bt1[0].setY(0);
			bt1[1].setX(5);bt1[1].setY(0);
			bt1[2].setX(5);bt1[2].setY(1);
			bt1[3].setX(6);bt1[3].setY(1);			
			break;
		case 6:                               //反Z
			bt1[0].setX(6);bt1[0].setY(0);
			bt1[1].setX(5);bt1[1].setY(0);
			bt1[2].setX(5);bt1[2].setY(1);
			bt1[3].setX(4);bt1[3].setY(1);			
			break;	
		case 7:                                   //神奇小点
			bt1[0].setX(5);bt1[0].setY(0);
			bt1[1].setX(5);bt1[1].setY(0);
			bt1[2].setX(5);bt1[2].setY(0);
			bt1[3].setX(5);bt1[3].setY(0);
		}
	}
	private boolean newBlock()
	{
		switch (flag) {
		case 0:                             //长条
			bt[0].setX(3);bt[0].setY(0);
			bt[1].setX(4);bt[1].setY(0);
			bt[2].setX(5);bt[2].setY(0);
			bt[3].setX(6);bt[3].setY(0);			
			break;
		case 1:                             //田字形
			bt[0].setX(4);bt[0].setY(0);
			bt[1].setX(5);bt[1].setY(0);
			bt[2].setX(4);bt[2].setY(1);
			bt[3].setX(5);bt[3].setY(1);			
			break;
		case 2:                              //L
			bt[0].setX(4);bt[0].setY(0);
			bt[1].setX(4);bt[1].setY(1);
			bt[2].setX(4);bt[2].setY(2);
			bt[3].setX(5);bt[3].setY(2);			
			break;
		case 3:                               //反L
			bt[0].setX(5);bt[0].setY(0);
			bt[1].setX(5);bt[1].setY(1);
			bt[2].setX(5);bt[2].setY(2);
			bt[3].setX(4);bt[3].setY(2);			
			break;
		case 4:                              //土  型
			bt[0].setX(5);bt[0].setY(0);
			bt[1].setX(4);bt[1].setY(1);
			bt[2].setX(5);bt[2].setY(1);
			bt[3].setX(6);bt[3].setY(1);			
			break;
		case 5:                                //z
			bt[0].setX(4);bt[0].setY(0);
			bt[1].setX(5);bt[1].setY(0);
			bt[2].setX(5);bt[2].setY(1);
			bt[3].setX(6);bt[3].setY(1);			
			break;
		case 6:                               //反Z
			bt[0].setX(6);bt[0].setY(0);
			bt[1].setX(5);bt[1].setY(0);
			bt[2].setX(5);bt[2].setY(1);
			bt[3].setX(4);bt[3].setY(1);			
			break;
		case 7:                                   //神奇小点
			bt[0].setX(5);bt[0].setY(0);
			bt[1].setX(5);bt[1].setY(0);
			bt[2].setX(5);bt[2].setY(0);
			bt[3].setX(5);bt[3].setY(0);
		default:
			break;
		}
		//当新生成的转款与map中已有的有部分重合 那么久无法生成新的砖块了
		for (int i = 0; i < 10; i++) 
		{
			for (int j = 0; j < 18; j++)
			{
				for (int j2 = 0; j2 < 4; j2++)
				{
					if(map[bt[j2].getX()][bt[j2].getY()]==1)
						return false;
				}				
			}
		}
		return true;
	}
	public boolean judge(int x,int y)
	{
		for (int i = 0; i < 4; i++) 
		{
			if(bt[i].getX()+x<0||bt[i].getX()+x>=10||bt[i].getY()+y>=18)
				return false;
		}
		for (int i = 0; i < 10; i++)           //防止砖块叠加
		{
			for (int j = 0; j < 18; j++)
			{
				for (int j2 = 0; j2 <4; j2++) {
					if(map[bt[j2].getX()+x][bt[j2].getY()+y]==1)
						return false;
				}
			}
		}
		return true;
	}
	private void move1(int x,int y)
	{
		if(!(bt[0].getX()==bt[1].getX()&&bt[0].getY()==bt[1].getY()))
		{
			if(judge(x, y))
			{
				for (int i = 0; i < 4; i++) 
				{
					bt[i].setX(bt[i].getX()+x);
					bt[i].setY(bt[i].getY()+y);			
				}
				repaint();
			}
		}
		else
		{
			if(bt[0].getX()+x>=0&&bt[0].getX()+x<10&&bt[0].getY()+y>=0&&bt[0].getY()+y<18)
			{
				for (int i = 0; i < 4; i++) 
				{
					bt[i].setX(bt[i].getX()+x);
					bt[i].setY(bt[i].getY()+y);			
				}
				repaint();
			}
		}
	}
	private void up()
	{
		block temp[]=new block[4];          //如果是小点  就退出旋转
		if(bt[0].getX()==bt[1].getX()&&bt[0].getY()==bt[1].getY())
			return ;
		for (int i = 0; i < 4; i++)
		{
			temp[i]=new block();
			temp[i].setX(bt[i].getX());
			temp[i].setY(bt[i].getY());
		}
		double cx=(temp[0].getX()+temp[1].getX()+temp[2].getX()+temp[3].getX())/4;
		double cy=(temp[0].getY()+temp[1].getY()+temp[2].getY()+temp[3].getY())/4;
		for (int i = 0; i < 4; i++)
		{
			temp[i].setX((int)(cx+cy)-bt[i].getY());
			temp[i].setY((int)(cy-cx)+bt[i].getX());
		}
		for (int i = 0; i < 4; i++)
		{
			if(temp[i].getX()<0||temp[i].getX()>=10||temp[i].getY()>=18||temp[i].getY()<0
					||map[temp[i].getX()][temp[i].getY()]==1)
			{
				return;
			}				
		}
		for (int i = 0; i < 4; i++) 
		{
			bt[i].setX(temp[i].getX());
			bt[i].setY(temp[i].getY());			
		}
		repaint();
	}
	private void down()
	{
		if(!(bt[0].getX()==bt[1].getX()&&bt[0].getY()==bt[1].getY()))
		{
			if(judge(0, 1))          //judge集中了所有的限制条件，不好应用与神奇小点
			{
				for (int i = 0; i < 4; i++) 
				{
					bt[i].setX(bt[i].getX()+0);
					bt[i].setY(bt[i].getY()+1);			
				}
				repaint();
			}
			else {          //不能再下的时候就是判定条件， 返回数据给map记录
				for (int i = 0; i < 4; i++)
				{
					map[bt[i].getX()][bt[i].getY()]=1;
				}
		//		t.stop();
				flag=temp;
				if(!newBlock())         //
				{
					startGame.setText("开始");				
					label.setText("阁下的得分为:"+score);
					dialog.setVisible(true);
					System.out.println("哥们你挂了 我了个去");
					t.stop();
					//如果不能下来个新的块  就挂了
				}
				else {		
					clear_map();
					temp=r.nextInt(8);
					Setbt1(temp);
					repaint();
				}			
			}
		}
		else                      //神奇小点的下落方式
		{
			if(bt[0].getX()>=0&&bt[0].getX()<10&&bt[0].getY()+1>=0&&bt[0].getY()+1<18)
			{
				for (int i = 0; i < 4; i++) 
				{
					bt[i].setX(bt[i].getX()+0);
					bt[i].setY(bt[i].getY()+1);			
				}
				if(clear_map1())
				{
		//			System.out.println(clear_map1());
					flag=temp;
					if(newBlock())
					{						
						temp=r.nextInt(8);
						Setbt1(temp);
		//     		如果消行成功，则把该点放置在最底层的一个map为1的位子，从而终止小点的移动
		//			for (int i = 0; i < 10; i++) 
		//			{
		//				if(map[i][17]==1)
		//				{
		//					for (int j = 0; j <4; j++)
		//					{
		//						bt[j].setX(i);
		//						bt[j].setY(17);
		//					}
		//				}
		//			}
				
						repaint();
						return;
					}
				}
			}
			else if(bt[0].getY()==17)
			{
				for (int i = 0; i < 4; i++)
				{
					map[bt[i].getX()][bt[i].getY()]=1;
				}
				flag=temp;
				if(!newBlock())         //
				{
					startGame.setText("开始");				
					label.setText("阁下的得分为:"+score);
					dialog.setVisible(true);
					System.out.println("哥们你挂了 我了个去");
					t.stop();
					//如果不能下来个新的块  就挂了
				}
				else {		
					clear_map();
					temp=r.nextInt(8);
					Setbt1(temp);
					repaint();
				}
			}
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyPressed(KeyEvent e) 
	{	
		if(start)
		{
			switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN:				
				down();				
				break;
			case KeyEvent.VK_UP: 
				up();                                  //上是旋转 			
				break;
			case KeyEvent.VK_LEFT:
				move1(-1,0);				
				break;
			case KeyEvent.VK_RIGHT:
				move1(1,0);				
				break;
			default:
				break;
			}
		}
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==startGame)
		{
			if(e.getActionCommand().equals("开始"))
			{
				start=true;
				startGame.setText("重置");
				requestFocus(true);
				flag=r.nextInt(8);
				temp=r.nextInt(8);
				if(!newBlock())
				{
					//这里是game over的区域
					return ;
				}
				else {
	//				t=new Timer(1000-100*(speed), new keepMove());
					t=new Thread(this);	
					Setbt1(temp);
					t.start();
					repaint();
				}
			}			
			else
			{
				totalLine=0;
				speed=1;
				score=0;
				for (int i = 0; i < 10; i++)
				{
					for (int j = 0; j < 18; j++) 
					{
						map[i][j]=0;
					}
				}
				startGame.setText("开始");
				dialog.setVisible(false);
				repaint();
				start=false;
			}
		}
		if(e.getSource()==stopGame)
		{
			System.exit(0);
		}
		if(e.getSource()==ok)
		{
			
			speed=1;
			totalLine=0;
			score=0;
			for (int i = 0; i < 10; i++)
			{
				for (int j = 0; j < 18; j++) 
				{
					map[i][j]=0;
				}
			}
			startGame.setText("开始");
			dialog.setVisible(false);		
			start=false;
			repaint();
	
		}
	}
	@Override
	public void run() 
	{
		while(start)
		{
			try {
				Thread.sleep(1000-(speed)*100);
			} catch (Exception e) {
			}down();
		}
	}
}
