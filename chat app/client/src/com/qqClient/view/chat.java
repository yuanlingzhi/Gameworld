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
//����chat���촰��Ҫһֱ�ڽ��ܷ�������������Ϣ��������1 Ҫô��һ��while��true��������ѭ��
//2 Ҫô��chat����һ���߳�         ��������while���Ĳ��벻���㣬���������߳�
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
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  �����һ�����ڵĹرգ� ��ô֮ǰ�����б��Ǹ�����Ҳ��ر�
		setTitle(ownerID+"������ "+friend+" ����");
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		setLocation((screenSize.width-300)/2,(screenSize.height-200)/2);
		
		this.ownerID=ownerID;
		this.friendID=friend;
		
		jta=new JTextArea();
		jp=new JPanel();
		jtf=new JTextField(15);
		jb=new JButton("����");
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
			//����û�����˷��Ͱ�ť�� ��ôҪ�����е���Ϣ ����һ��message  Ȼ���͵����������н���
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
	//дһ������ ��chat������²���ʾ��Ϣ
	public void update(Message m)
	{
		String info=m.getSender()+" �� "+m.getGetter()+" ˵:"+m.getContent()+"\r\n";
		jta.append(info);
	}
/*
	@Override

public void run()
{
	while(true)
	{
		//һֱ��ȡ������ת������Ϣ
		try {
			
			ObjectInputStream ois=new ObjectInputStream(myQQconServer.s.getInputStream());
			Message mes=(Message)ois.readObject();
		//	System.out.println("������chat�����Ѿ���ȡ����Ϣ");
		// ��ʾ��JTextArea
			String info=mes.getSender()+" �� "+mes.getGetter()+" ˵ "+mes.getContent()+"\r\n";
			jta.append(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}*/

}
