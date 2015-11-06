package com.qqClient.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.common.Message;
import com.common.MessageType;
import com.common.User;
import com.qq.tools.*;
import com.qqClient.model.myQQuser;

public class login_view extends JFrame implements ActionListener
{
	//��ߵ���һ��label
	JLabel label1;
	
	//�в������񲼾����
/*
 *  QQ ��½��ѡ�
 */
	JTabbedPane jtp;
	JPanel jp2;             //QQ��½ѡ�
	JLabel jp2_label1;      //QQ����
	JLabel jp2_label2;      //QQ����
	JLabel jp2_label3;      //��������
	JLabel jp2_label4;      //�������뱣��
	JTextField jp2_jtf;         //����QQ�ŵ�����
	JPasswordField jp2_jpf;     //����QQ���������
	JButton jp2_jb1;        //"ע�����"��ť	
	JCheckBox jp2_jcb1;     //�����½
	JCheckBox jp2_jcb2;     //��ס����
	
	JPanel jp3;            //�ֻ���½ ѡ�
	JPanel jp4;            //�����ʼ� ѡ�
	
	//���ϱߵ���3��button�� South
	JButton jp1_jb1;
	JButton jp1_jb2;
	JButton jp1_jb3;
	JPanel jp1;
	public login_view()
	{
		//north �� south �ȽϺô���
		setSize(350,250);
		setTitle("QQɽկ��");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		setLocation((screenSize.width-350)/2,(screenSize.height-250)/2);
		label1=new JLabel(new ImageIcon("image\\1.jpg"));
		jp1_jb1=new JButton(new ImageIcon("image/log.jpg"));
		jp1_jb1.addActionListener(this);
		jp1_jb2=new JButton(new ImageIcon("image/cancel.jpg"));
		jp1_jb3=new JButton(new ImageIcon("image/guide.jpg"));
		jp1=new JPanel();
		add(label1,BorderLayout.NORTH);
		jp1.add(jp1_jb1);
		jp1.add(jp1_jb2);
		jp1.add(jp1_jb3);
		add(jp1,BorderLayout.SOUTH);
		
		//�в���һ��ѡ� ����3��JPanel�����л�
		jp2=new JPanel();            //����3��jpanel����
		jp3=new JPanel();
		jp4=new JPanel();
		jtp=new JTabbedPane();       //����ѡ�����

		//ʵ�����������
		jp2_label1=new JLabel("QQ����",JLabel.CENTER);
		jp2_label2=new JLabel("QQ����",JLabel.CENTER);
		jp2_label3=new JLabel("�һ�����",JLabel.CENTER);
		jp2_label4=new JLabel("�������뱣��",JLabel.CENTER);
		jp2_label3.setForeground(Color.RED);
		jp2_label4.setForeground(Color.RED);
		jp2_jtf=new JTextField();
		jp2_jpf=new JPasswordField();
		jp2_jb1=new JButton(new ImageIcon("image/register.jpg"));
		jp2_jcb1=new JCheckBox("�����½");
		jp2_jcb2=new JCheckBox("��ס����");
		
		jp2.setLayout(new GridLayout(3,3));   //����jpanelΪ gridlayout����
		jp2.add(jp2_label1);                               //�����߰���������˳����� panel2����
		jp2.add(jp2_jtf);
		jp2.add(jp2_jb1);
		jp2.add(jp2_label2);
		jp2.add(jp2_jpf);
		jp2.add(jp2_label3);
		jp2.add(jp2_jcb1);
		jp2.add(jp2_jcb2);
		jp2.add(jp2_label4);
		
		jtp.add(jp2,"QQ��½");                //��3��jpanel�������ѡ� 
		jtp.add(jp3,"�ֻ���½");
		jtp.add(jp4,"�����½");
		
		add(jtp,BorderLayout.CENTER);
		
		setVisible(true);
	}
	public static void main(String[] args)
	{
		login_view v=new login_view();
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jp1_jb1)
		{
			String id=jp2_jtf.getText().trim();
			String password=new String(jp2_jpf.getPassword());
			User u=new User();
			u.setId(id);
			u.setPassword(password);
			if(new myQQuser().checkUser(u))
			{
				//����Ƿ���û���Ȼ��½��
				
				//��ʾ�����б� ���Ѻ����б���뵽��Ӧ�Ĺ�ϣ����
				friend_view fv=new friend_view(u.getId());
				ManageClientFriendList.addfriend_view(u.getId(),fv );
				
				
				
				//��½�����������������Ϊ��ȥ������ߺ��ѵ���Ϣ�������ں����б��ͼ������ʾ����
				//�ؼ������������������������out
				try {
					//��ͨsocketͨ��
				ObjectOutputStream oos=new ObjectOutputStream
				((ManageClientConServerThread.getClientConServerThread
				(u.getId()).getS().getOutputStream()));
				//��һ��Message�� �������������
				Message m=new Message();
				m.setMesType(MessageType.message_get_onlineFriend);
				// ���� Ҫ�����Լ���QQ�����б�
				m.setSender(u.getId());
				oos.writeObject(m);									
									
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				//dispose��¼��
				this.dispose();
			}
			else
			{
				JOptionPane.showMessageDialog(this,"�Բ��� ��������û������������������ظ���½��");
			}
		}
	}
}
