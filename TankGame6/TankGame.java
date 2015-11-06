package com.TankGame6;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.security.PublicKey;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.html.HTMLDocument.Iterator;

public class TankGame extends JFrame implements ActionListener 
{
	MyPanel myPanel=null;
	StartPanel sp=null;
	
	JMenuBar jmb=null;
	JMenu jm1=null;
	JMenuItem jmi4=null;
	JMenuItem jmi=null;
	JMenuItem jmi1=null;
	JMenuItem jmi2=null;
	JMenuItem jmi3=null;
	JMenu jm2=null;
	JMenuItem jmi5=null;
	
//	public static LinkedList<Thread> ll=new LinkedList<Thread>();

	public static void main(String[]  args)
	{
		TankGame tg=new TankGame();
	}
	public TankGame()
	{
		setSize(718,588);
		setTitle("Tank World");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension ss=tk.getScreenSize();
		setLocation((ss.width-618)/2,(ss.height-588)/2);

		jmb=new JMenuBar();
		setJMenuBar(jmb);
		jm1=new JMenu("game");
		jm2=new JMenu("tool");
		jm1.setMnemonic('f');
		jmb.add(jm1);
		jmb.add(jm2);
		jmi=new JMenuItem("new game");
		jmi1=new JMenuItem("exit");
		jmi2=new JMenuItem("save exit");
		jmi3=new JMenuItem("previous");
		jmi4=new JMenuItem("pause(p)");
		jmi5=new JMenuItem("continue(c)");
		jm2.add(jmi4);
		jmi4.addActionListener(this);
		jm2.addSeparator();
		jm2.add(jmi5);
		jmi5.addActionListener(this);
				
		jmi3.addActionListener(this);
		jmi2.addActionListener(this);
		jmi.addActionListener(this);
		jmi1.addActionListener(this);
		jm1.add(jmi);
		jm1.addSeparator();
		jm1.add(jmi1);
		jm1.addSeparator();
		jm1.add(jmi2);
		jm1.addSeparator();
		jm1.add(jmi3);
		sp=new StartPanel();
		Thread t=new Thread(sp);
		t.start();
		add(sp);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jmi)
		{
			myPanel=new MyPanel("newGame");				
			Thread t=new Thread(myPanel);
			this.remove(sp);
			t.start();
			this.addKeyListener(myPanel);
			add(myPanel);
			jmi.setEnabled(false);
			setVisible(true);
		}
		else if(e.getSource()==jmi1)
		{// exit button
			Recorder.RecordToTxt();
			System.exit(0);
		}
		else if(e.getSource()==jmi2)
		{// clicked save and exit button
			System.out.println("save and exit");
			Recorder.RecordAllInfo();
		//should record enemy's number and coordinates
			System.exit(0);
		}
		else if(e.getSource()==jmi3)
		{
			//previous
			
			myPanel=new MyPanel("continue");				
			Thread t=new Thread(myPanel);
			this.remove(sp);
			t.start();
			this.addKeyListener(myPanel);
			add(myPanel);
			jmi.setEnabled(false);
			jmi3.setEnabled(false);
			setVisible(true);			
		}
		else if(e.getSource()==jmi4||e.getSource()==jmi5)
		{
			JOptionPane.showMessageDialog(this, "pause=p  AND contunue=c");
		}
	}
}

class StartPanel extends JPanel implements Runnable
{
	boolean isAlive=true;
	public StartPanel()
	{
		
		
	}
	
	public void paint(Graphics g)
	{
		int i=(int)(Math.random()*4);
//		System.out.println(i);
		super.paint(g);
		g.fillRect(0, 0, 500, 400);
		Font font=new Font("华文新魏",Font.BOLD,35);
		g.setFont(font);
		switch (i) {
		case 0:
			g.setColor(Color.GREEN);
			g.drawString("STAGE 1", 170, 200);
			break;
		case 1:
			g.setColor(Color.YELLOW);
			g.drawString("STAGE 1", 170, 200);
			break;
		case 2:
			g.setColor(Color.RED);
			g.drawString("STAGE 1", 170, 200);
			break;
		case 3:
			g.setColor(Color.CYAN);
			g.drawString("STAGE 1", 170, 200);
			break;
		}	
	}
	public void run()
	{
		while(isAlive)
		{
			try {
				Thread.sleep(100);
				this.repaint();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}


class MyPanel extends JPanel implements KeyListener ,Runnable
{
	
	
	 myTank myTank=null;
	 Vector<enemyTank> enemy=new Vector<enemyTank>();
	int enemySize=Recorder.getEnNum();
	//both the three picture below belong one explode
	Image im1=null;
	Image im2=null;
	Image im3=null;
	
	boolean If_I_Can_Move=true;
	//define a vector of explode
	Vector<Bomb> bomb=new Vector<Bomb>();
	Vector<point> points=new Vector<point>();
	
	
	public MyPanel(String flag)
	{
		
		Recorder.GetRecordFromTxt();
		setSize(500,400);
		myTank=new myTank(10,10);	
		if(flag.equals("newGame"))
		{
			//reset the record of enDie
			Recorder.setEnDie(0);
			for (int i = 0; i < enemySize; i++)
			{
				enemyTank et= new enemyTank((i+1)*40, 100);
				et.setColor(1);
				et.setDirection(2);
				et.setVectorTank(enemy);
				//enemyTank's Thread
				Thread t=new Thread(et);
		//		TankGame.ll.add(t);
		//		System.out.println("Mypanel init");
				t.start();
				//enemyBullet's Thread 
				shot s=new shot(et.x+10, et.y+30, et.direction);
				Thread t1=new Thread(s);
				t1.start();
				et.enemyShot.add(s);
				enemy.add(et);
			}
		}
		else if(flag.equals("continue")){
			
			points=Recorder.PreviousGame();
//			System.out.println("points.size()"+points.size());
			for (int i = 0; i < points.size(); i++)
			{
//				System.out.println("points.size()"+points.size());
				point p=points.get(i);
				enemyTank et= new enemyTank(p.x, p.y);
				et.setColor(1);
				et.setDirection(p.direction);
				et.setVectorTank(enemy);
				Thread t=new Thread(et);
				t.start();
				shot s=new shot(et.x+10, et.y+30, et.direction);
				Thread t1=new Thread(s);
				t1.start();
				et.enemyShot.add(s);
				enemy.add(et);
			}
		}
		
		try {
			im1=ImageIO.read(new File("image/1.jpg"));
			im2=ImageIO.read(new File("image/2.jpg"));
			im3=ImageIO.read(new File("image/3.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Recorder.setEt(enemy);
//		im1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/1.jpg"));
//		im2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/2.jpg"));
//		im3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/3.jpg"));
	}
	public void paint_info(Graphics g)
	{
		//draw two tank to show the rest number or life of them
		drawTank(50, 420, g, 0, 0);
		g.setColor(Color.BLACK);
		g.drawString(String.valueOf(Recorder.getMyLife()),75 , 440);
		
		drawTank(95, 420, g, 0, 1);
		g.setColor(Color.BLACK);
		g.drawString(String.valueOf(Recorder.getEnNum()),120 , 440);
		
		//show the information of user's total killing;
		g.setColor(Color.BLACK);
		Font font=new Font("宋体",Font.BOLD,15);
		g.setFont(font);
		g.drawString("The Total Killing:", 520, 20);
		drawTank(530, 40, g, 0, 1);
		g.setColor(Color.BLACK);
		g.drawString(Recorder.getEnDie()+"", 555, 57);
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		// fill the background - black
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 500, 400);
		paint_info(g);
		
		if(Recorder.getMyLife()>0)
		{
			if(myTank.isAlive)
			{
				drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirection(), 0);
			}
		}
		
		//shot the bullet after myTank was drawed

			for (int i = 0; i < myTank.vs.size(); i++)
			{		
				shot shotTemp =myTank.vs.get(i);
				if(shotTemp!=null&&shotTemp.isAlive==true)
				{
					g.draw3DRect(shotTemp.x, shotTemp.y, 1, 1, false);
				}
				if(shotTemp.isAlive==false)
				{
					myTank.vs.remove(shotTemp);
				}
			}
		//draw bomb
		for (int i = 0; i < bomb.size(); i++)
		{//get one 
			Bomb b=bomb.get(i);
			if(b.BombLife>6)
			{				
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
				}
				g.drawImage(im1,b.x,b.y,30,30,this);
			}else if(b.BombLife>3)
			{
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
				}
				g.drawImage(im2,b.x,b.y,30,30,this);
			}
			else
			{
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
				}
				g.drawImage(im3,b.x,b.y,30,30,this);
			}
			b.decreaseLife();	
			if(b.BombLife==0)
			{
				bomb.remove(b);
			}
		}
		
		
		for (int i = 0; i < enemy.size(); i++) 
		{
			if(enemy.get(i).isAlive)
			{
				drawTank(enemy.get(i).getX(),enemy.get(i).getY(), g, enemy.get(i).getDirection(), enemy.get(i).getColor());
				//draw it's bullet
				for (int j = 0; j < enemy.get(i).enemyShot.size(); j++)
				{
					shot enemyTempShot=enemy.get(i).enemyShot.get(j);
					if(enemyTempShot.isAlive)
					{
						g.draw3DRect(enemyTempShot.x, enemyTempShot.y, 1, 1, false);
					}
					else
					{// if the bullet die then remove it from vector
						enemy.get(i).enemyShot.remove(enemyTempShot);
					}
				}
			}
		}
		

	}
	//type 0 represents I shot enemy
	//type 1 represents enemy shot me;
	public void explode(shot s,Tank enemy,int type)
	{//  enemy's different direction, lead to different size area, so we need switch
		
		switch (enemy.direction) {
		case 0:
		case 2:
			if(s.x>enemy.x&&s.x<(enemy.x+20)&&s.y>enemy.y&&s.y<(enemy.y+30))
			{
				// shot enemy
				// bullet die
				s.isAlive=false;
				// enemy die
//				if(type==0)
//				{
//					Recorder.decline_enemy();
//					System.out.println(Recorder.getEnNum());
//				}				
				enemy.isAlive=false;
				//create a bomb to show some explode impact
				Bomb b=new Bomb(enemy.x, enemy.y);
				bomb.add(b);
				if(type==1)
				{
					Recorder.decline_myLife();
					if(Recorder.getMyLife()>0)
					{
						myTank.x=10;
						myTank.y=10;
						myTank.direction=0;
						myTank.isAlive=true;
						myTank.vs.clear();
					}
//					System.out.println(Recorder.getEnNum());
				}
			}
		case 1:
		case 3:
			if(s.x>enemy.x&&s.x<(enemy.x+30)&&s.y>enemy.y&&s.y<(enemy.y+20))
			{
//				if(type==0)
//				{
//					Recorder.decline_enemy();
//					System.out.println(Recorder.getEnNum());
//				}

				s.isAlive=false;
				enemy.isAlive=false;
				Bomb b=new Bomb(enemy.x, enemy.y);
				bomb.add(b);
				if(type==1)
				{
					Recorder.decline_myLife();
					if(Recorder.getMyLife()>0)
					{
						myTank.x=10;
						myTank.y=10;
						myTank.direction=0;
						myTank.isAlive=true;
						myTank.vs.clear();
					}
//					System.out.println(Recorder.getEnNum());
				}
			}
		}		
	}
	
	public void drawTank(int x,int y,Graphics g,int direction,int type)
	{
		switch (type)
		{
		// 0 represents self
		case 0:
			g.setColor(Color.YELLOW);
			break;
		// 1 represents enemy
		case 1:
			g.setColor(Color.CYAN);
			break;
		};
		switch (direction)
		{
		//0 means up direction
		case 0:
			g.fill3DRect(x,y, 5,30, false);
			g.fill3DRect(x+15,y, 5,30,false);
			g.fill3DRect(x+5,y+5, 10,20, false);
			g.fillOval(x+5,y+8, 8,12);
			g.drawLine(x+9,y,x+9,y+15);
			break;
		case 1:
			g.fill3DRect(x,y, 30,5, false);
			g.fill3DRect(x,y+15,30 ,5,false);
			g.fill3DRect(x+5,y+5, 20,10, false);
			g.fillOval(x+8,y+5,12 ,8);
			g.drawLine(x+15,y+9,x+30,y+9);
			break;
		case 2:
			g.fill3DRect(x,y, 5,30, false);
			g.fill3DRect(x+15,y, 5,30,false);
			g.fill3DRect(x+5,y+5, 10,20, false);
			g.fillOval(x+5,y+8, 8,12);
			g.drawLine(x+9,y+15,x+9,y+30);
			break;
		case 3:
			g.fill3DRect(x,y, 30,5, false);
			g.fill3DRect(x,y+15,30 ,5,false);
			g.fill3DRect(x+5,y+5, 20,10, false);
			g.fillOval(x+8,y+5,12 ,8);
			g.drawLine(x,y+9,x+15,y+9);
			break;			
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(If_I_Can_Move)
		{
			switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				myTank.direction=0;
				myTank.moveUP();
				break;
			case KeyEvent.VK_D:
				myTank.direction=1;
				myTank.moveRIGHT();
				break;
			case KeyEvent.VK_S:
				myTank.direction=2;
				myTank.moveDOWN();
				break;
			case KeyEvent.VK_A:
				myTank.direction=3;
				myTank.moveLEFT();
				break;
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_J&&If_I_Can_Move)
		{
			if(myTank.vs.size()<=4)
			{
				myTank.shotBullet();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_P)
		{
			suspend();
		}
		if(e.getKeyCode()==KeyEvent.VK_C)
		{
			continueGame();
		}
		repaint();
	}
	public void continueGame()
	{
		this.If_I_Can_Move=true;
		for (int i = 0; i < enemy.size(); i++) 
		{
			enemyTank et=enemy.get(i);
			et.continueGame();				
		}
		for (int i = 0; i < myTank.vs.size(); i++)
		{
			shot s =myTank.vs.get(i);
			s.continueGame();
		}
	}
	public void suspend()
	{
		this.If_I_Can_Move=false;
		for (int i = 0; i < enemy.size(); i++) 
		{
			enemyTank et=enemy.get(i);
			et.suspend();				
		}
		for (int i = 0; i < myTank.vs.size(); i++)
		{
			shot s =myTank.vs.get(i);
			s.suspend();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	public void enemy_hit_mine()
	{
		for (int i = 0; i < enemy.size(); i++)
		{
			//get enemy' tank
			enemyTank et=enemy.get(i);
			
			for (int j = 0; j <et.enemyShot.size(); j++) 
			{
				//get this enemy's bullet
				shot s=et.enemyShot.get(j);
				if(s.isAlive)
				{
					if(myTank.isAlive)
					{
						explode(s, myTank,1);
				//		myTank.isAlive=false;
				//		System.out.println(myTank.isAlive);
					}
				}
			}
		}
	}
	
	public void mine_hit_enemy()
	{
		for (int i = 0; i < myTank.vs.size(); i++)
		{//get the bullet
			shot shotTemp=myTank.vs.get(i);
		 // judge if the bullet is available
			if(shotTemp.isAlive)
			{//get every enemy tank to judge whether they are explode
				for (int j = 0; j < enemy.size(); j++) 
				{
					enemyTank et=enemy.get(j);
					if(et.isAlive)
					{
						explode(shotTemp, et,0);
					}
				}
			}				
		}
	}
	public void run()
	{
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//judge if every bullet right shot on the every Tank
			this.mine_hit_enemy();
			this.enemy_hit_mine();		
			
			ChangeINFO();
			
			repaint();
			
		}
	}
	public void ChangeINFO()
	{
		for (int i = 0; i < enemy.size(); i++)
		{
			enemyTank et=enemy.get(i);
			if(!et.isAlive)
			{
				Recorder.decline_enemy();
				Recorder.increase_enDie();
				enemy.remove(i);
			}
		}
	}
}