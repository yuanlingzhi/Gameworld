package eblock;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class eblock extends JFrame
{
	public eblock() throws IOException
	{
		setSize(380,420);
		setTitle("YLZµÄ¶íÂÞË¹·½¿é");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		setLocation((screenSize.width-380)/2,(screenSize.height-420)/2);		
	//	setResizable(false);
		Image icon=ImageIO.read(new File("image\\1.jpg"));
		setIconImage(icon);
		eblockLogic blockLogic=new eblockLogic();
		add(blockLogic);
	}
	public static void main(String []args) throws IOException
	{
		eblock block=new eblock();
		block.setVisible(true);
		
	}
}
