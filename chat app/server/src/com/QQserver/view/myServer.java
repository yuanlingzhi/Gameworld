/*
 *  �������˵Ŀ��ƽ���    ���� �ر�  ���������û��ȹ���
 */
package com.QQserver.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.myServer.model.myQQserver;

public class myServer extends JFrame implements ActionListener
{
	JButton jb1;
	JButton jb2;
	JPanel jp;
	
	public myServer()
	{
		setSize(500,400);
		setTitle("������");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		setLocation((screenSize.width-500)/2,(screenSize.height-400)/2);
		jb1=new JButton("����������");
		jb1.addActionListener(this);
		jb2=new JButton("�رշ�����");
		jb2.addActionListener(this);
		jp=new JPanel();
		jp.add(jb1);
		jp.add(jb2);
		add(jp);
		setVisible(true);
	}
	public static void main(String[] args)
	{
		myServer server=new myServer();
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jb1)
		{
			new myQQserver();
		}
	//	if(e.getSource()==jb2)
	//	{
	//		
	//	}
	}
}
