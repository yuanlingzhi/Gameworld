package snak;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class snackMain extends JFrame 
{
	public snackMain()
	{
		snackWin snackW=new snackWin();
		add(snackW);
		setTitle("Ì°Ê³Éß-YLZ×÷Æ·");
		setSize(435,390);
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		int HEIGHT=screenSize.height;
		int WIDTH=screenSize.width;
		int x=(WIDTH-435)/2;
		int y=(HEIGHT-390)/2;
		setLocation(x,y);
		setVisible(true);
	}
	public static void main(String []args)
	{
		snackMain snackM=new snackMain();
	}
}
