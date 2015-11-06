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
			//��ͣ�Ķ�ȡ�ӷ������˷�������Ϣ
			try {
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				Message m=(Message)ois.readObject();
				System.out.println("�ӷ�������������Ϣ:"+m.getSender()+"���͸�"+m.getGetter()+" "+"����Ϊ"+m.getContent());
				//�ӷ������õ��İ��ﲻ������װ��������Ϣ��������װ������������ߵ���Ϣ�����������У�
				if(m.getMesType().equals(MessageType.message_normal_mes))
				{
					//�Ѵӷ�������õ���Ϣ����ʾ������ʾ�ĵط�
					chat c=ManageQQChat.getManageQQChat(m.getGetter()+" "+m.getSender());
					c.update(m);
				}
				else if(m.getMesType().equals(MessageType.message_get_onlineFriend));
				{
					String con=m.getContent();
					String friend[]=con.split(" ");
					//���ظ�˭
					String getter=m.getGetter();
					//�޸ĺ����б�
					friend_view qqFriendList=ManageClientFriendList.getfriend_view(m.getGetter());
					
					//��friend_view�еķ����Լ����º����б�
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
