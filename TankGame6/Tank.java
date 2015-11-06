package com.TankGame6;

import java.awt.Point;
import java.awt.im.InputContext;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.RepaintManager;
import javax.xml.transform.Templates;



class Tank
{
	int x;
	int y;
	int color;
	boolean isAlive=true;

	

	//0 means up;
	//1        right
	//2        down
	//3        left
	int direction=0;
	int speed=1;
	
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public Tank(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
}
class shot implements Runnable
{
	int x=0;
	int y=0;
	int direction=0;
	int speed=2;
	boolean isAlive=true;
//	boolean if_It_Can_Move=true;
	public shot(int x,int y,int direction)
	{
		this.x=x;
		this.y=y;
		this.direction=direction;
		
	}
	public void suspend()
	{
		speed=0;
	}
	public void continueGame()
	{
		speed=2;
	}
	public void run()
	{

		while(true)
		{	
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}
			switch(direction)
			{
			case 0: this.y-=speed;
			break;
			case 1: this.x+=speed;
			break;
			case 2: this.y+=speed;
			break;
			case 3: this.x-=speed;
			break;
			}
	//		System.out.println("x="+x+" "+"y="+y);
			if(x<0||x>500||y<0||y>400)
			{
				this.isAlive=false;
				break;
			}
		}
		
	}
}
class point
{
	int x=0;
	int y=0;
	int direction=0;
	public point(int x,int y,int direction)
	{
		this.x=x;
		this.y=y;
		this.direction=direction;
	}
}
class Recorder
{
	static FileReader fr=null;
	static BufferedReader br=null;
	static FileWriter fw=null;
	static FileWriter fw1=null;
	static BufferedWriter bw1=null;
	static BufferedWriter bw=null;
	private static int enNum=10;
	private static int enDie=0;
	private static Vector<enemyTank> et=new Vector<enemyTank>();
	static Vector<point> points = new Vector<point>();
	public static Vector<enemyTank> getEt() {
		return et;
	}
	public static void setEt(Vector<enemyTank> et) {
		Recorder.et = et;
	}
	public static int getEnDie() {
		return enDie;
	}
	public static void setEnDie(int enDie) {
		Recorder.enDie = enDie;
	}
	public static int getEnNum() {
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}
	public static int getMyLife() {
		return myLife;
	}
	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}
	private static int myLife=3;
	public static void decline_enemy()
	{
		enNum--;
	}
	public static void decline_myLife()
	{
		myLife--;
	}
	public static void increase_enDie()
	{
		enDie++;
	}
	//read the information after we save game
	public static Vector<point> PreviousGame()
	{
		try {
			fr=new FileReader("image/lastTime.txt");
			br=new BufferedReader(fr);
			enDie=Integer.parseInt(br.readLine());
			String n="";
			while((n=br.readLine())!=null)
			{
				String xyDirection[]=n.split(" ");
				point p=new point(Integer.parseInt(xyDirection[0]),Integer.parseInt(xyDirection[1]),Integer.parseInt(xyDirection[2]));
				points.add(p);
			}
			
			// get the rest tanks coordinates
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			try {
				br.close();
				fr.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return points;
	}
	
	public static void RecordAllInfo()
	{

		try {
			fw1=new FileWriter("image/lastTime.txt");
			bw1=new BufferedWriter(fw1);
			bw1.write(enDie+""+"\r\n");
			
			//record rest enemy's all information
			for (int i = 0; i < et.size(); i++)
			{
				enemyTank enemy=et.get(i);
				if(enemy.isAlive)
				{
					String info=enemy.x+" "+enemy.y+" "+enemy.direction;
					//write into file
					bw1.write(info+"\r\n");
				}
				
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			try {
				bw1.close();
				fw1.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}		
	}
	public static void GetRecordFromTxt()
	{
		try {
			fr=new FileReader("image/lastTime.txt");
			br=new BufferedReader(fr);
			String str=br.readLine();
			enDie=Integer.parseInt(str);						
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			try {
				br.close();
				fr.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	public static void RecordToTxt()
	{

		try {
			fw=new FileWriter("image/1.txt",true);
			fw1=new FileWriter("image/lastTime.txt");
			bw1=new BufferedWriter(fw1);
			bw=new BufferedWriter(fw);
			try {
				bw.write("Killing enemies:"+enDie+""+"\t\t"+getTime()+"\r\n");
				bw1.write(enDie+""+"\r\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			try {
				bw1.close();
				bw.close();
				fw1.close();
				fw.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}		
	}
	
	public static String getTime()
	{
		int year=0;
		int month=0;
		int day=0;
		Calendar c=Calendar.getInstance();
		year=c.get(Calendar.YEAR);
		month=c.get(Calendar.MONTH)+1;
		day=c.get(Calendar.DAY_OF_MONTH);
		String str=year+"-"+month+"-"+day;
		return str;
	}
	
}

class enemyTank extends Tank implements Runnable
{
	 static int shotNumber=0;
	Vector<enemyTank> enemyTanks=new Vector<enemyTank>();
	int time=0;
//	boolean isAlive=true;
	//define a vector to store enemy's bullet
	Vector<shot> enemyShot=new Vector<shot>();
	boolean allow_direction=true;
	boolean allow_all=true;
	
	public enemyTank(int x,int y)
	{
		super(x,y);
	}
	public void setVectorTank(Vector v)
	{
		this.enemyTanks=v;
	}
	
	public boolean overlap()
	{
		boolean touch=false;
		switch (this.direction)
		{
		case 0:
			//get tanks
			for (int i = 0; i < enemyTanks.size(); i++) 
			{
				enemyTank et=enemyTanks.get(i);
				//judge this tank is not myself
				if(et!=this)
				{
					if(et.direction==0||et.direction==2)
					{
						if(this.x>=et.x&&this.x<=(et.x+20)&&this.y>=et.y&&this.y<=(et.y+30))
						{
							return true;
						}
						if((this.x+20)>=et.x&&(this.x+20)<=(et.x+20)&&this.y>=et.y&&this.y<=(et.y+30))
						{
							return true;
						}
					}
					else if(et.direction==1||et.direction==3)
					{
						if(this.x>=et.x&&this.x<=(et.x+30)&&this.y>=et.y&&this.y<=(et.y+20))
						{
							return true;
						}
						if((this.x+20)>=et.x&&(this.x+20)<=(et.x+30)&&this.y>=et.y&&this.y<=(et.y+20))
						{
							return true;
						}
					}
				}
			}
			break;
		case 1:	
			//get tanks
			for (int i = 0; i < enemyTanks.size(); i++) 
			{
				enemyTank et=enemyTanks.get(i);
				//judge this tank is not myself
				if(et!=this)
				{
					if(et.direction==0||et.direction==2)
					{
						if(this.x+30>=et.x&&this.x+30<=(et.x+20)&&this.y>=et.y&&this.y<=(et.y+30))
						{
							return true;
						}
						if((this.x+30)>=et.x&&(this.x+30)<=(et.x+20)&&this.y+20>=et.y&&this.y+20<=(et.y+30))
						{
							return true;
						}
					}
					else if(et.direction==1||et.direction==3)
					{
						if(this.x+30>=et.x&&this.x+30<=(et.x+30)&&this.y>=et.y&&this.y<=(et.y+20))
						{
							return true;
						}
						if((this.x+30)>=et.x&&(this.x+30)<=(et.x+30)&&this.y+20>=et.y&&this.y+20<=(et.y+20))
						{
							return true;
						}
					}
				}
			}
			break;
		case 2:	
			//get tanks
			for (int i = 0; i < enemyTanks.size(); i++) 
			{
				enemyTank et=enemyTanks.get(i);
				//judge this tank is not myself
				if(et!=this)
				{
					if(et.direction==0||et.direction==2)
					{
						if(this.x>=et.x&&this.x<=(et.x+20)&&this.y+30>=et.y&&this.y+30<=(et.y+30))
						{
							return true;
						}
						if(this.x+20>=et.x&&this.x+20<=(et.x+20)&&this.y+30>=et.y&&this.y+30<=(et.y+30))
						{
							return true;
						}
					}
					else if(et.direction==1||et.direction==3)
					{
						if(this.x>=et.x&&this.x<=(et.x+30)&&this.y+30>=et.y&&this.y+30<=(et.y+20))
						{
							return true;
						}
						if(this.x+20>=et.x&&this.x+20<=(et.x+30)&&this.y+30>=et.y&&this.y+30<=(et.y+20))
						{
							return true;
						}
					}
				}
			}
			break;
		case 3:		
			//get tanks
			for (int i = 0; i < enemyTanks.size(); i++) 
			{
				enemyTank et=enemyTanks.get(i);
				//judge this tank is not myself
				if(et!=this)
				{
					if(et.direction==0||et.direction==2)
					{
						if(this.x>=et.x&&this.x<=(et.x+20)&&this.y>=et.y&&this.y<=(et.y+30))
						{
							return true;
						}
						if(this.x>=et.x&&this.x<=(et.x+20)&&this.y+20>=et.y&&this.y+20<=(et.y+30))
						{
							return true;
						}
					}
					else if(et.direction==1||et.direction==3)
					{
						if(this.x>=et.x&&this.x<=(et.x+30)&&this.y>=et.y&&this.y<=(et.y+20))
						{
							return true;
						}
						if(this.x>=et.x&&this.x<=(et.x+30)&&this.y+20>=et.y&&this.y+20<=(et.y+20))
						{
							return true;
						}
					}
				}
			}
			break;
		}		
		
		return touch;
	}
	public void suspend()
	{
		allow_direction=false;
		speed=0;
		allow_all=false;
		for (int i = 0; i < enemyShot.size(); i++)
		{
			shot s=enemyShot.get(i);
			s.speed=0;
		}
	}
	public void continueGame()
	{
		allow_direction=true;
		speed=1;
		allow_all=true;
		for (int i = 0; i < enemyShot.size(); i++)
		{
			shot s=enemyShot.get(i);
			s.speed=1;
		}
	}
	public void run()
	{
		while(true)
		{
			while(allow_all)
			{
	
		//		System.out.println("1");
				switch (this.direction) {
				case 0:
					for (int i = 0; i <40; i++)
					{
						if(y>0&&x>0&&x<470&&!overlap())
						{
							y-=speed;
						}
						try {
							Thread.sleep(50);
						} catch (Exception e) {
							e.printStackTrace();
						}					
					}				
					break;
				case 1:
					for (int i = 0; i <40; i++)
					{
						if(x<470&&y>0&&y<370&&!overlap())
						{
							x+=speed;
						}
						try {
							Thread.sleep(50);
						} catch (Exception e) {
							e.printStackTrace();
						}					
					}				
					break;
				case 2:
					for (int i = 0; i <40; i++)
					{
						if(y<370&&x>0&&x<470&&!overlap())
						{
							y+=speed;
						}
						try {
							Thread.sleep(50);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}				
					break;
				case 3:
					for (int i = 0; i <40; i++)
					{
						if(x>0&&y>0&&y<370&&!overlap())
						{
							x-=speed;
						}
						try {
							Thread.sleep(50);
						} catch (Exception e) {
							e.printStackTrace();
						}					
					}				
					break;
				}
				this.time++;
				if(time%2==0)
				{
					if(isAlive)
					{
								if(enemyShot.size()<5)
								{
									shot s=null;
									switch(direction)
									{
									case 0:s=new shot(x+9, y,0);  //create a new bullet 
											enemyShot.add(s);            //add into vector
									break;
									case 1:s=new shot(x+30, y+9,1);
											enemyShot.add(s);
									break;
									case 2:s=new shot( x+9,  y+30,2);
											enemyShot.add(s);
									break;
									case 3:s=new shot(x, y+9,3);
											enemyShot.add(s);
									break;
									}
									Thread t=new Thread(s);
	//								TankGame.ll.add(t);
	//								System.out.println("bullet thread");
									t.start();
								}
							}
	
				}
				if(allow_direction)
				{
					this.direction=(int)(Math.random()*4);
				}
				// enemy down
				if(this.isAlive==false)
				{
					break;
				}
			}
		}
	}
//	public boolean overlap()
//	{
//		boolean allowed=true;
//		//this tank's four direction 
//		switch (this.direction)
//		{
//		case 0:		
//			for (int i = 0; i < MyPanel.enemy.size(); i++)
//			{
//				enemyTank et=MyPanel.enemy.get(i);
//				//if up  direction happened to another up or down
//				if(et.direction==1||et.direction==3)   
//				{
//					if(this.x>(et.x-20)&&this.x<(et.x+30)&&this.y<(et.y+20)&&this.y>(et.y-30))
//					{
//						allowed=false;
//						return allowed;
//					}
//				}
//				if(et.direction==2)
//				{
//					if(this.x>(et.x-20)&&this.x<(et.x+20)&&this.y<(et.y+30)&&this.y>(et.y-30))
//					{
//						allowed=false;
//						return allowed;
//					}
//				}
//			}
//		case 1:
//			for (int i = 0; i < MyPanel.enemy.size(); i++)
//			{
//				enemyTank et=MyPanel.enemy.get(i);
//				//if up  direction happened to another up or down
//				if(et.direction==0||et.direction==2)   
//				{
//					if(this.x>(et.x-30)&&this.x<(et.x+20)&&this.y<(et.y+30)&&this.y>(et.y-20))
//					{
//						allowed=false;
//						return allowed;
//					}
//				}
//				if(et.direction==3)
//				{
//					if(this.x>(et.x-30)&&this.x<(et.x+30)&&this.y<(et.y+20)&&this.y>(et.y-20))
//					{
//						allowed=false;
//						return allowed;
//					}
//				}
//			}
//		case 2:
//			for (int i = 0; i < MyPanel.enemy.size(); i++)
//			{
//				enemyTank et=MyPanel.enemy.get(i);
//				//if up  direction happened to another up or down
//				if(et.direction==1||et.direction==3)   
//				{
//					if(this.x>(et.x-20)&&this.x<(et.x+30)&&this.y<(et.y+20)&&this.y>(et.y-30))
//					{
//						allowed=false;
//						return allowed;
//					}
//				}
//				if(et.direction==0)
//				{
//					if(this.x>(et.x-20)&&this.x<(et.x+20)&&this.y<(et.y+30)&&this.y>(et.y-30))
//					{
//						allowed=false;
//						return allowed;
//					}
//				}
//			}
//		case 3:
//			for (int i = 0; i < MyPanel.enemy.size(); i++)
//			{
//				enemyTank et=MyPanel.enemy.get(i);
//				if(et.direction==0||et.direction==2)   
//				{
//					if(this.x>(et.x-30)&&this.x<(et.x+20)&&this.y>(et.y-20)&&this.y<(et.y+30))
//					{
//						allowed=false;
//						return allowed;
//					}
//				}
//				if(et.direction==1)
//				{
//					if(this.x>(et.x-30)&&this.x<(et.x+30)&&this.y>(et.y-20)&&this.y<(et.y+20))
//					{
//						allowed=false;
//						return allowed;
//					}
//				}
//			}
//		}
//		return allowed;
//	}
}

class myTank extends Tank
{
	int speed=7;
	shot s=null;
//	boolean isAlive=true;
	Vector<shot> vs=new Vector<shot>();
	public myTank(int x,int y)
	{
		super(x, y);
	}
	public void moveUP()
	{
		y-=speed;
	}
	public void moveRIGHT()
	{
		x+=speed;
	}
	public void moveDOWN()
	{
		y+=speed;
	}
	public void moveLEFT()
	{
		x-=speed;
	}
	public void shotBullet()
	{
		
		switch(this.direction)
		{
		case 0:s=new shot(x+9, y,0);  //create a new bullet 
				vs.add(s);            //add into vector
		break;
		case 1:s=new shot(x+30, y+9,1);
				vs.add(s);
		break;
		case 2:s=new shot(x+9, y+30,2);
				vs.add(s);
		break;
		case 3:s=new shot(x,y+9,3);
				vs.add(s);
		break;
		}
		Thread t=new Thread(s);
		t.start();
	}
}

class Bomb
{
	int x=0;
	int y=0;
	int BombLife=9;
	boolean isAlive=true;
	public Bomb(int x,int y) 
	{
		this.x=x;
		this.y=y;
	}
	public void decreaseLife()
	{
		if(BombLife>0)
		{
			BombLife--;
		}
		else {
			this.isAlive=false; 
		}
	}
}