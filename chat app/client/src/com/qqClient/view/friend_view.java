package com.qqClient.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;

import com.common.Message;
import com.common.User;
import com.qq.tools.ManageQQChat;

public class friend_view extends JFrame implements ActionListener,MouseListener
{
	
	CardLayout cl;
	/*
	 *    ��һ����Ƭ����    ��һ��JPanel ������������
	 *    ������   �ҵĺ��� jbutton
	 *    �м��Ǹ�   jscrollpane    ���������һ��jpanel ����Ϊ gridlayout
	 *    �ϱ��� һ��jpanel�������������jbutton  ����Ϊ gridlayout
	 */
	JPanel jp_friend;
	JButton friend;
	JScrollPane friend_jsp;
	JPanel jsp_jp1;
	JLabel label[];
	JPanel jp;
	JButton unknow;
	JButton disgusted;            //��������ҵĺ��ѿ�Ƭ�Ĳ��֣� ���Ҫ���� İ���� �� �������� ����Ҫ�ظ��˹���2��
	
	//����ڶ�����Ƭ��İ����ϵ�У�
	JPanel jp_unknow;
	JButton friend1;
	JScrollPane unknow_jsp;
	JPanel jsp_jp2;
	JPanel jp1;
	JButton unknow1;
	JButton disgusted1;
	String ownerID;
	
	
	public friend_view(String ownerID)
	{
		setSize(140,400);
		setTitle(ownerID);	
		this.ownerID=ownerID;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		setLocation((screenSize.width-140)/2,(screenSize.height-400)/2);
		// �����һ����Ƭ
		
		jp_friend=new JPanel(new BorderLayout());
		friend=new JButton("�ҵĺ���");
		jp_friend.add(friend,BorderLayout.NORTH);
		
		//JScrollPane������ add()���������м�����Ҫ�л����������
		//Ҫ�� ��ʼ��ʱ ���� JScrollPane jsp=new JScrollPane()����������
		//������Ҫ�л����������  �����Ǹ���panel�� textfield ����area������
		//���Ǳ���Ҫ�ڳ�ʼ����ʱ�����
		jsp_jp1=new JPanel(new GridLayout(50,1,4,4));
		label=new JLabel[50];
		for (int i = 0; i < label.length; i++) 
		{
			label[i]=new JLabel((i+1)+" ",new ImageIcon("image/face.jpg"),JLabel.LEFT);
			label[i].setEnabled(false);
			if(label[i].getText().trim().equals(ownerID))
			{
				label[i].setEnabled(true);
			}
			label[i].addMouseListener(this);
			jsp_jp1.add(label[i]);
		}
		friend_jsp=new JScrollPane(jsp_jp1);
		
		jp_friend.add(friend_jsp,BorderLayout.CENTER);
		
		jp=new JPanel(new GridLayout(2,1));
		unknow=new JButton("İ����");
		unknow.addActionListener(this);
		disgusted=new JButton("������");
		jp.add(unknow);
		jp.add(disgusted);
		jp_friend.add(jp,BorderLayout.SOUTH);
		
		//����ڶ�����Ƭ
		//������
		jp_unknow=new JPanel(new BorderLayout());
		jp1=new JPanel(new GridLayout(2,1));
		friend1=new JButton("�ҵĺ���");
		friend1.addActionListener(this);
		unknow1=new JButton("İ����");
		jp1.add(friend1);
		jp1.add(unknow1);
		jp_unknow.add(jp1,BorderLayout.NORTH);
		
		//�����в�
		jsp_jp2=new JPanel(new GridLayout(20,1,4,4));
		JLabel label1[]=new JLabel[20];
		for (int i = 0; i < label1.length; i++) 
		{
			label1[i]=new JLabel((i+1)+" ",new ImageIcon("image/face.jpg"),JLabel.LEFT);
			label1[i].addMouseListener(this);
			jsp_jp2.add(label1[i]);
		}
		unknow_jsp=new JScrollPane(jsp_jp2);		
		jp_unknow.add(unknow_jsp,BorderLayout.CENTER);
		
		//�����ϲ�
		disgusted1=new JButton("������");
		jp_unknow.add(disgusted1,BorderLayout.SOUTH);
		
		cl=new CardLayout();
		setLayout(cl);
		add(jp_friend,"1");
		add(jp_unknow,"2");
		setVisible(true);		
	}
//	public static void main(String[] args)
//	{
//		friend_view fv=new friend_view();
//	}
	public void update(Message m)
	{
		String onLineFriend[]=m.getContent().split(" ");
		for (int i = 0; i < onLineFriend.length; i++) 
		{
			label[Integer.parseInt(onLineFriend[i])-1].setEnabled(true);
		}
	}
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==unknow)
		{
			cl.show(this.getContentPane(), "2");
		}
		if(e.getSource()==friend1)
		{
			cl.show(this.getContentPane(), "1");
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==2&&((JLabel)e.getSource()).isEnabled())
		{
			//����TMD�Ƚ��ƿڣ� �õ����¼�e.getsource��������JLabelת���ɱ�ǩ��Ȼ����getText�����õ�string
			// Ȼ�� ����Stringǿ��ת��
			String label=(String)(((JLabel)e.getSource()).getText()).trim(); 
			chat c=new chat(label,ownerID);
	//		Thread t=new Thread(c);
	//		t.start();
			//�����������뵽  ����chat�Ĺ�ϣ����
			ManageQQChat.addManageQQChat(this.ownerID+" "+label, c);
		}
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		JLabel label =(JLabel)e.getSource();
		label.setForeground(Color.RED);		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		JLabel label =(JLabel)e.getSource();
		label.setForeground(Color.BLACK);	
		
	}
}
