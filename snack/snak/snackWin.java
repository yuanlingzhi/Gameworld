package snak;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.GraphicAttribute;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class snackWin extends JPanel implements ActionListener, KeyListener,Runnable
{
	int flag=0;
	Random r=new Random();
	int tempEat=0;
	int rx=0,ry=0;
	boolean start=false;
	int speed=0,score=0,score1=0;
	JButton newGame,stopGame;
	List<snake> list=new ArrayList<snake>();
	JDialog dialog=new JDialog();
	JLabel label=new JLabel();
	JButton ok =new JButton("好吧-_-");
	//JButton ok1 =new JButton("好吧1-_-");
	Thread snakeThread;
	 public snackWin()
	 {
		 newGame=new JButton("开始");
		 stopGame=new JButton("结束");
		 setLayout(new FlowLayout(FlowLayout.LEFT));
		 newGame.addActionListener(this);         //监听自己（对象）
		 stopGame.addActionListener(this);
		 ok.addActionListener(this);
		 ok.addKeyListener(this);
		 this.addKeyListener(this);
		 add(newGame);
		 add(stopGame);
		 dialog.setLayout(new GridLayout(2,2));
		 dialog.add(label);
		 dialog.add(ok);
		// dialog.add(ok1);
		 dialog.setSize(200,200);
		 Toolkit tk=Toolkit.getDefaultToolkit();
		 Dimension screenSize=tk.getScreenSize();
		 int HEIGHT=screenSize.height;
		 int WIDTH=screenSize.width;
		 int x=(WIDTH-200)/2;
		 int y=(HEIGHT-200)/2;
		 dialog.setLocation(x,y);
		 dialog.setVisible(false);
	 } 
	 public void eat()
	 {
		 if(rx==list.get(0).getX()&&ry==list.get(0).getY())
		 {
			 rx=r.nextInt(40);
			 ry=r.nextInt(30);
			 snake tempAct=new snake();
			 tempAct.setX(list.get(list.size()-1).getX());
			 tempAct.setY(list.get(list.size()-1).getY());
			 list.add(tempAct);
			 score+=100+10*speed;
			 tempEat++;
			 if(tempEat%10==0)
			 {
				if(speed<=9)
				 speed++;
			 }
		 }
	 }
	 public void chase()
	 {
		snake tempAct=new snake();
		for (int i = 0; i < list.size(); i++)  //蛇头永远不动
		{
			if(i==1)
			{ 
				list.get(i).setX(list.get(0).getX());
				list.get(i).setY(list.get(0).getY());
			}
			else if(i>1)
			{
				tempAct=list.get(i-1);
				list.set(i-1,list.get(i));
				list.set(i, tempAct);
			}
		}
	 }
	 public void move(int x,int y)
	 {
		 if(judge1(x, y))
		 {	
			 chase();
			 list.get(0).setY(list.get(0).getY()+y);
			 list.get(0).setX(list.get(0).getX()+x);
			 eat();				 
			 repaint();
		 }
		 else {	
			 snakeThread=null;
			 start=false;
			 label.setText("你挂了 你的分数为:"+score);
			 dialog.setVisible(true);			 			 
		}
	 }
	 public boolean judge1(int x,int y)
	 {
		 if(!judge(list.get(0).getX()+x, list.get(0).getY()+y))	
			 return false;
		 return true;
	 }
	 public boolean judge(int x,int y)
	 {
		 if(x<0||x>=40||y<0||y>=30)
		 {
			 return false;
		 }
		 for (int i = 0; i < list.size(); i++) 
		 {
			 if(i>1&&list.get(i).getX()==list.get(0).getX()&&list.get(i).getY()==list.get(0).getY())
			 {
				 return false;
			 }
		 }
		 return true;
	 }
	 public void paintComponent(Graphics g)
	 {
		 super.paintComponent(g);
		 Rectangle rec=new Rectangle(10,40,400,300);
		 Graphics2D g2=(Graphics2D)g;
		 g2.setPaint(Color.black);
		 g2.fill(rec);
		// g.drawRect(10, 40, 400,300);
		 g.drawString("分数:"+score, 150, 18);
		 g.drawString("速度:"+speed, 150, 34);
		 g.setColor(new Color(1,255,200));
		 if(start)
		 {
			// Ellipse2D e1=new Ellipse2D.Double();
			// e1.setFrameFromCenter(10+rx*10, 40+ry*10, 10+rx*10+7, 40+ry*10+7);
			// g2.fill(e1);
			 g.fillOval(10+rx*10, 40+ry*10, 10, 10);
			// g.fillRect(10+rx*10, 40+ry*10, 10, 10);
			 g.setColor(new Color(255,0,0));
			for (int i = 0; i < list.size(); i++)
			{
				g.fillRect(10+list.get(i).getX()*10, 40+list.get(i).getY()*10, 10, 10);
			}
		 }
	 }
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e)
	{
		if(start)
		{
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:  move(0,-1);flag=1;				
				break;
			case KeyEvent.VK_DOWN:  move(0,1);	flag=2;			
			break;
			case KeyEvent.VK_LEFT:  move(-1,0);	flag=3;			
			break;
			case KeyEvent.VK_RIGHT:  move(1,0);	flag=4;			
			break;
			default:
				break;
			}
		}
		if(!start&&e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			list.clear();
			speed=0;
			score=0;
			start=false;
			newGame.setEnabled(true);
			dialog.setVisible(false);
			repaint();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {}

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==newGame)
		{
			newGame.setEnabled(false);
			start=true;
			rx=r.nextInt(40);
			ry=r.nextInt(30);
			snake snakeAct=new snake();
			snakeAct.setX(20);
			snakeAct.setY(15);
			list.add(snakeAct);
			requestFocus(true);
			Thread snakeThread=new Thread(this);
			snakeThread.start();
			repaint();
		}
		if(e.getSource()==stopGame)
			System.exit(0);
		if(e.getSource()==ok)
		{
			list.clear();
			speed=0;
			score=0;
			start=false;
			newGame.setEnabled(true);
			dialog.setVisible(false);
			repaint();
		}
	}
	@Override
	public void run() 
	{
		while(start)
		{
			switch (flag) {
			case 1:
				move(0,-1);
				break;
			case 2:
				move(0,1);
				break;
			case 3:
				move(-1,0);
				break;
			case 4:
				move(1,0);
				break;
			default:
				move(1, 0);
				break;
			}
			score+=10*speed;
			repaint();	
			try{Thread.sleep(500-(50*speed));}
			catch(Exception e){}
		}
	}
}
