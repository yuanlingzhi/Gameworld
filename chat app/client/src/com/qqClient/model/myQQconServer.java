package com.qqClient.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/*
 * 客户端连接服务器的后台部分
 */

import com.common.Message;
import com.common.User;
import com.qq.tools.ClientConServerThread;
import com.qq.tools.ManageClientConServerThread;

public class myQQconServer
{
	/*
	public  myQQconServer()
	{
		try {
			Socket s=new Socket("192.168.0.100",9998);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	*/
	public  Socket s;
	public boolean sendLoginInfoToServer(Object o)
	{
		boolean b=false;
		try {
			
			s=new Socket("192.168.0.100",9988);
			System.out.println("socket"+s==null);
			//把这边的登录信息对象发送过去
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			
			//服务器返回信息
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			Message mes=(Message)ois.readObject();
			if(mes.getMesType().equals("1"))
			{
				//表示登陆成功，然后要创建一个 客户端与服务器连接成功的线程	
				ClientConServerThread ccst=new ClientConServerThread(s);
				//加入 哈希 控制列表  顺便启动线程
				ccst.start();
				User u=(User)o;
				ManageClientConServerThread.addClientConServerThread(u.getId(), ccst);
				b=true;
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
}
