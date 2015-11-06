package com.myServer.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import com.common.Message;
import com.common.MessageType;

public class serConClientThread extends Thread
{
	//该线程必须要有个socket 服务于这个socket
	Socket s;
	

	public serConClientThread(Socket s)
	{
		this.s=s;
	}	
	
	
	public void notifyOtherOnline(String self)
	{
		//必须得获得所有在线的用户
		HashMap hm=ManageClientThread.hm;
		Iterator it=hm.keySet().iterator();
		
		while(it.hasNext())
		{
			Message m=new Message();
			m.setContent(self);
			m.setMesType(MessageType.message_ret_onlineFriend);
			
			//取出在线人的ID号
			String OnlineUser = it.next().toString();
			try {
				ObjectOutputStream oos=new ObjectOutputStream
				((ManageClientThread.getClientThread(OnlineUser)).s.getOutputStream());
				m.setGetter(OnlineUser);
				oos.writeObject(m);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void run()
	{
		while(true)
		{
			//该线程可以接受客户端的信息(无限接受)
			try {
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				Message mes=(Message)ois.readObject();
				System.out.println(mes.getSendTime()+" "+mes.getSender()+"给"+mes.getGetter()+"说"+mes.getContent());
				//对从客户端发来的信息做 message 类型判断 然后再做响应的处理
				
				
				if(mes.getMesType().equals(MessageType.message_normal_mes))
				{
				// 完成转发任务 
				serConClientThread  sc=ManageClientThread.getClientThread(mes.getGetter());
		/*         //操啊 上面的trim()弄了3个小时才找到问题  到现在还TM不知道为啥 我了个擦啊
		 * 下列if else语句为调试语句
		 * 		 */
		
				ObjectOutputStream oos=new ObjectOutputStream(sc.s.getOutputStream());			
				oos.writeObject(mes);
				}
				else if(mes.getMesType().equals(MessageType.message_get_onlineFriend))
				{
					//把该账户在服务器登录等好友返回
					String result=ManageClientThread.getAllOnlineClient();
					//用Message 对象去承载返回的信息
					Message m=new Message();
					m.setMesType(MessageType.message_ret_onlineFriend);
					m.setContent(result);  //把内容放进去
					//因为这是服务器端，所以sender是服务器， getter是要发送给的对象 所以
					m.setGetter(mes.getSender());
					//上面的m.setGetter(mes.getSender())别搞错了
					//setGetter是设置服务器发回去的新包，而里面的参数是客户端发过来的人的信息 所以要用
					//以前的Message对象mes
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(m);
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
