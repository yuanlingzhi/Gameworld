/*
 * 此乃QQ服务器   起监听作用 等待客户端连接
 */
package com.myServer.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import com.common.Message;
import com.common.User;

public class myQQserver 
{
	ServerSocket server;
	User u;
	public myQQserver()
	{ 
		try {
			server=new ServerSocket(9988);
			System.out.println("服务器在9988监听");
			
			while(true)
			{	
				//因为每次在login窗口点登陆的时候，每次都创建了个新的socket去连接服务器，所以要重新监听，即在服务器重新创建个accept去响应新的连接
				//如果把Socket socket=server.accept(); 放在while语句外面， 那么socket对象始终是第一次点击login登陆所传进来的对象  无论第二次是什么信息
				//不会有变化
				Socket socket=server.accept(); 
				System.out.println("连上啦");
				//接受用户发来的信息， 显然是用户名与密码
				ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
				u=(User)ois.readObject();
				Message mes=new Message();			
				ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
				if(u.getPassword().equals("123456")&&checkRepeatLog())
				{				
					mes.setMesType("1");
					oos.writeObject(mes);	 
					// 要单开一个线程，让该线程服务于   服务器与该客户端的通信
					serConClientThread scct=new serConClientThread(socket);
					//创建出来的新线程加入到哈希表中 
					ManageClientThread.addClientThread(u.getId(), scct);
			
					
					//启动与该客户端通讯的线程
					scct.start();
					
					// 通知自己，让服务器随时刷新好友列表
					scct.notifyOtherOnline(u.getId());
				}
				else
				{
					mes.setMesType("2");
					oos.writeObject(mes);	
					socket.close();	
				}

				
			}
 
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	public boolean checkRepeatLog()
	{
		HashMap hm=ManageClientThread.hm;
		Iterator it=hm.keySet().iterator();
		while(it.hasNext())
		{
			if(it.next().equals(u.getId()))
			{
				return false;
			}
		}
		return true;
	}
}
