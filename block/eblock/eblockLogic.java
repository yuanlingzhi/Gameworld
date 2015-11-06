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
	JButton startGame=new JButton("��ʼ");
	JButton stopGame=new JButton("�˳�");
	block bt[]=new block[4];    //bt==block temp;   //��Ҫ�����ķ���
	block bt1[]=new block[4];   //��һ���еķ���
	int map[][]=new int [10][18];         //10Ϊ x Ϊ�У� 18Ϊy Ϊ��
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
		dialog.setTitle("��Ϸ������");
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
			bt1[i]=new block();               //ʵ����block���������4������
		}
		for (int i = 0; i <10; i++)           //Ϊ0��ʱ�򲻻��� ; 
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
		g.drawString("����:"+score, 220, 60);
		g.setColor(Color.RED);
		g.drawLine(220, 65, 360, 65);
		g.setColor(Color.BLACK);
		g.drawString("�ٶ�:"+speed, 220, 90);
		g.setColor(Color.RED);
		g.drawLine(220, 95, 360, 95);
		g.setColor(Color.BLACK);
		g.drawString("��һ������" , 260, 130);
		g.setFont(new Font("SanSerif",Font.ITALIC,12));
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(),BorderFactory.createRaisedBevelBorder()));
		g.setColor(Color.DARK_GRAY);
		g.drawString("����С��:", 215, 320);
		g.setFont(new Font("SanSerif",Font.PLAIN,11));
		g.drawString("��Խש��Ĵ̼�", 270, 320);
		g.setColor(Color.BLACK);
		g.setFont(new Font("SanSerif",Font.BOLD,13));
		g.drawString("����˹����2.0 " , 215, 340);
		g.drawString("YLZ��Ʒ" , 215, 360);		
	//	g.setColor(new Color(0,255,0));
	//	g2.fill3DRect(10*20, 10*20, 20, 20,true);
		//  ��ʼ��������
		
		if(start)
		{
			g2.setColor(Color.YELLOW);                   //�Ȼ���ͼ
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
			if(bt[0].getX()==bt[1].getX()&&bt[0].getY()==bt[1].getY())    //��С��
			{
				g2.setColor(Color.CYAN);
				g2.fill3DRect(10+bt[0].getX()*20, 10+bt[0].getY()*20, 20, 20,true);
			}
			else                   //������
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
		//����һ������map1 �����ж� С�����ߵ�ÿһ���Ƿ��������У�
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
					for (int i1=i; i1 >0; i1--)   //������i1=0  ����Խ��
					{
		//				System.out.println("�ѵ�"+(i1-1)+"��"+"��ֵ����"+i1+"��");
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
					//���������� map1 ����ĵ�ͼ    Ҫ�������ͼ���¸�ֵ��map
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
					for (int i1=i; i1 >0; i1--)   //������i1=0  ����Խ��
					{
		//				System.out.println("�ѵ�"+(i1-1)+"��"+"��ֵ����"+i1+"��");
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
		case 0:                             //����
			bt1[0].setX(3);bt1[0].setY(0);
			bt1[1].setX(4);bt1[1].setY(0);
			bt1[2].setX(5);bt1[2].setY(0);
			bt1[3].setX(6);bt1[3].setY(0);			
			break;
		case 1:                             //������
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
		case 3:                               //��L
			bt1[0].setX(5);bt1[0].setY(0);
			bt1[1].setX(5);bt1[1].setY(1);
			bt1[2].setX(5);bt1[2].setY(2);
			bt1[3].setX(4);bt1[3].setY(2);			
			break;
		case 4:                              //��  ��
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
		case 6:                               //��Z
			bt1[0].setX(6);bt1[0].setY(0);
			bt1[1].setX(5);bt1[1].setY(0);
			bt1[2].setX(5);bt1[2].setY(1);
			bt1[3].setX(4);bt1[3].setY(1);			
			break;	
		case 7:                                   //����С��
			bt1[0].setX(5);bt1[0].setY(0);
			bt1[1].setX(5);bt1[1].setY(0);
			bt1[2].setX(5);bt1[2].setY(0);
			bt1[3].setX(5);bt1[3].setY(0);
		}
	}
	private boolean newBlock()
	{
		switch (flag) {
		case 0:                             //����
			bt[0].setX(3);bt[0].setY(0);
			bt[1].setX(4);bt[1].setY(0);
			bt[2].setX(5);bt[2].setY(0);
			bt[3].setX(6);bt[3].setY(0);			
			break;
		case 1:                             //������
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
		case 3:                               //��L
			bt[0].setX(5);bt[0].setY(0);
			bt[1].setX(5);bt[1].setY(1);
			bt[2].setX(5);bt[2].setY(2);
			bt[3].setX(4);bt[3].setY(2);			
			break;
		case 4:                              //��  ��
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
		case 6:                               //��Z
			bt[0].setX(6);bt[0].setY(0);
			bt[1].setX(5);bt[1].setY(0);
			bt[2].setX(5);bt[2].setY(1);
			bt[3].setX(4);bt[3].setY(1);			
			break;
		case 7:                                   //����С��
			bt[0].setX(5);bt[0].setY(0);
			bt[1].setX(5);bt[1].setY(0);
			bt[2].setX(5);bt[2].setY(0);
			bt[3].setX(5);bt[3].setY(0);
		default:
			break;
		}
		//�������ɵ�ת����map�����е��в����غ� ��ô���޷������µ�ש����
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
		for (int i = 0; i < 10; i++)           //��ֹש�����
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
		block temp[]=new block[4];          //�����С��  ���˳���ת
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
			if(judge(0, 1))          //judge���������е���������������Ӧ��������С��
			{
				for (int i = 0; i < 4; i++) 
				{
					bt[i].setX(bt[i].getX()+0);
					bt[i].setY(bt[i].getY()+1);			
				}
				repaint();
			}
			else {          //�������µ�ʱ������ж������� �������ݸ�map��¼
				for (int i = 0; i < 4; i++)
				{
					map[bt[i].getX()][bt[i].getY()]=1;
				}
		//		t.stop();
				flag=temp;
				if(!newBlock())         //
				{
					startGame.setText("��ʼ");				
					label.setText("���µĵ÷�Ϊ:"+score);
					dialog.setVisible(true);
					System.out.println("��������� ���˸�ȥ");
					t.stop();
					//��������������µĿ�  �͹���
				}
				else {		
					clear_map();
					temp=r.nextInt(8);
					Setbt1(temp);
					repaint();
				}			
			}
		}
		else                      //����С������䷽ʽ
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
		//     		������гɹ�����Ѹõ��������ײ��һ��mapΪ1��λ�ӣ��Ӷ���ֹС����ƶ�
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
					startGame.setText("��ʼ");				
					label.setText("���µĵ÷�Ϊ:"+score);
					dialog.setVisible(true);
					System.out.println("��������� ���˸�ȥ");
					t.stop();
					//��������������µĿ�  �͹���
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
				up();                                  //������ת 			
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
			if(e.getActionCommand().equals("��ʼ"))
			{
				start=true;
				startGame.setText("����");
				requestFocus(true);
				flag=r.nextInt(8);
				temp=r.nextInt(8);
				if(!newBlock())
				{
					//������game over������
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
				startGame.setText("��ʼ");
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
			startGame.setText("��ʼ");
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
