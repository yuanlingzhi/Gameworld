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
	//最北边的是一个label
	JLabel label1;
	
	//中部的网格布局组件
/*
 *  QQ 登陆的选项卡
 */
	JTabbedPane jtp;
	JPanel jp2;             //QQ登陆选项卡
	JLabel jp2_label1;      //QQ号码
	JLabel jp2_label2;      //QQ密码
	JLabel jp2_label3;      //忘记密码
	JLabel jp2_label4;      //申请密码保护
	JTextField jp2_jtf;         //输入QQ号的区域
	JPasswordField jp2_jpf;     //输入QQ密码的区域
	JButton jp2_jb1;        //"注册号码"按钮	
	JCheckBox jp2_jcb1;     //隐身登陆
	JCheckBox jp2_jcb2;     //记住密码
	
	JPanel jp3;            //手机登陆 选项卡
	JPanel jp4;            //电子邮件 选项卡
	
	//最南边的是3个button在 South
	JButton jp1_jb1;
	JButton jp1_jb2;
	JButton jp1_jb3;
	JPanel jp1;
	public login_view()
	{
		//north 和 south 比较好处理
		setSize(350,250);
		setTitle("QQ山寨版");
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
		
		//中部是一个选项卡 操作3个JPanel互相切换
		jp2=new JPanel();            //创造3个jpanel对象
		jp3=new JPanel();
		jp4=new JPanel();
		jtp=new JTabbedPane();       //创建选项卡对象

		//实例化所有组件
		jp2_label1=new JLabel("QQ号码",JLabel.CENTER);
		jp2_label2=new JLabel("QQ密码",JLabel.CENTER);
		jp2_label3=new JLabel("找回密码",JLabel.CENTER);
		jp2_label4=new JLabel("申请密码保护",JLabel.CENTER);
		jp2_label3.setForeground(Color.RED);
		jp2_label4.setForeground(Color.RED);
		jp2_jtf=new JTextField();
		jp2_jpf=new JPasswordField();
		jp2_jb1=new JButton(new ImageIcon("image/register.jpg"));
		jp2_jcb1=new JCheckBox("隐身登陆");
		jp2_jcb2=new JCheckBox("记住密码");
		
		jp2.setLayout(new GridLayout(3,3));   //设置jpanel为 gridlayout布局
		jp2.add(jp2_label1);                               //把乱七八糟的组件按顺序加入 panel2里面
		jp2.add(jp2_jtf);
		jp2.add(jp2_jb1);
		jp2.add(jp2_label2);
		jp2.add(jp2_jpf);
		jp2.add(jp2_label3);
		jp2.add(jp2_jcb1);
		jp2.add(jp2_jcb2);
		jp2.add(jp2_label4);
		
		jtp.add(jp2,"QQ登陆");                //把3个jpanel对象加入选项卡 
		jtp.add(jp3,"手机登陆");
		jtp.add(jp4,"邮箱登陆");
		
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
				//检查是否该用户已然登陆了
				
				//显示好友列表 并把好友列表放入到相应的哈希表中
				friend_view fv=new friend_view(u.getId());
				ManageClientFriendList.addfriend_view(u.getId(),fv );
				
				
				
				//登陆后向服务器发出请求，为了去获得在线好友的信息，并且在好友列表的图标中显示出来
				//关键是向服务器发送请求！所以是out
				try {
					//打通socket通道
				ObjectOutputStream oos=new ObjectOutputStream
				((ManageClientConServerThread.getClientConServerThread
				(u.getId()).getS().getOutputStream()));
				//做一个Message包 用来请求服务器
				Message m=new Message();
				m.setMesType(MessageType.message_get_onlineFriend);
				// 表明 要的是自己的QQ好友列表
				m.setSender(u.getId());
				oos.writeObject(m);									
									
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				//dispose登录表
				this.dispose();
			}
			else
			{
				JOptionPane.showMessageDialog(this,"对不起 您输入的用户名或者密码错误或者重复登陆了");
			}
		}
	}
}
