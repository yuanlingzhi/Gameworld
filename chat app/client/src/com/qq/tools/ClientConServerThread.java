package com.qq.tools;

import java.io.ObjectInputStream;
import java.net.Socket;

import com.common.Message;
import com.common.MessageType;
import com.qqClient.view.chat;
import com.qqClient.view.friend_view;

public class ClientConServerThread  extends Thread
{
	private Socket s;
	public ClientConServerThread(Socket s)
	{
		this.s =s;
	}
	public void run()
	{
		while(true)
		{
			//不停的读取从服务器端发来的信息
			try {
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				Message m=(Message)ois.readObject();
				System.out.println("从服务器发来的消息:"+m.getSender()+"发送给"+m.getGetter()+" "+"内容为"+m.getContent());
				//从服务器得到的包里不仅仅有装了聊天信息，还可能装有请求好友在线等信息（持续扩充中）
				if(m.getMesType().equals(MessageType.message_normal_mes))
				{
					//把从服务器获得的消息，显示到该显示的地方
					chat c=ManageQQChat.getManageQQChat(m.getGetter()+" "+m.getSender());
					c.update(m);
				}
				else if(m.getMesType().equals(MessageType.message_get_onlineFriend));
				{
					String con=m.getContent();
					String friend[]=con.split(" ");
					//返回给谁
					String getter=m.getGetter();
					//修改好友列表
					friend_view qqFriendList=ManageClientFriendList.getfriend_view(m.getGetter());
					
					//让friend_view中的方法自己更新好友列表
					if(qqFriendList!=null)
					{
						qqFriendList.update(m);
					}
				}
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public Socket getS() {
		return s;
	}
	public void setS(Socket s) {
		this.s = s;
	}
}
