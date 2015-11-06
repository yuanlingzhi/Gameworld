package com.qqClient.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.common.Message;
import com.common.MessageType;
import com.qq.tools.ManageClientConServerThread;
import com.qqClient.model.myQQconServer;
//由于chat聊天窗口要一直在接受服务器发来的信息包，所以1 要么有一个while（true）的无限循环
//2 要么把chat做成一个线程         但是由于while语句的插入不方便，所以做成线程
public class chat extends JFrame implements ActionListener {

	JTextArea jta;
	JTextField jtf;
	JButton jb;
	JPanel jp;
	JScrollPane jsp;
	String ownerID;
	String friendID;
	
	public chat(String friend,String ownerID)
	{
		setSize(300,200);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  如果点一个窗口的关闭， 那么之前好友列表那个程序也会关闭
		setTitle(ownerID+"正在与 "+friend+" 聊天");
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		setLocation((screenSize.width-300)/2,(screenSize.height-200)/2);
		
		this.ownerID=ownerID;
		this.friendID=friend;
		
		jta=new JTextArea();
		jp=new JPanel();
		jtf=new JTextField(15);
		jb=new JButton("发送");
		jb.addActionListener(this);
		jsp=new JScrollPane(jta);
		jp.add(jtf);
		jp.add(jb);
		add(jsp,BorderLayout.CENTER);
		add(jp,BorderLayout.SOUTH);
		setVisible(true);
	}
//	public static void main(String[] args) {
//		chat c=new chat("1");
//
//	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jb);
		{
			//如果用户点击了发送按钮， 那么要把所有的信息 包成一个message  然后发送到服务器进行解析
			Message mes=new Message();
			mes.setMesType(MessageType.message_normal_mes);
			mes.setSender(ownerID);
			mes.setGetter(friendID);
			mes.setContent(jtf.getText());
			mes.setSendTime(new Date().toString());
		//	myQQconServer mqcs=new myQQconServer();
			try {
				ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread((ownerID).trim()).getS().getOutputStream());
				oos.writeObject(mes);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
	}
	//写一个方法 让chat界面更新并显示消息
	public void update(Message m)
	{
		String info=m.getSender()+" 对 "+m.getGetter()+" 说:"+m.getContent()+"\r\n";
		jta.append(info);
	}
/*
	@Override

public void run()
{
	while(true)
	{
		//一直读取服务器转发的信息
		try {
			
			ObjectInputStream ois=new ObjectInputStream(myQQconServer.s.getInputStream());
			Message mes=(Message)ois.readObject();
		//	System.out.println("这里是chat，我已经读取了信息");
		// 显示在JTextArea
			String info=mes.getSender()+" 对 "+mes.getGetter()+" 说 "+mes.getContent()+"\r\n";
			jta.append(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}*/

}
