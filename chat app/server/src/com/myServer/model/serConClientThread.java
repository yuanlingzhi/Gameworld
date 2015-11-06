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
	//���̱߳���Ҫ�и�socket ���������socket
	Socket s;
	

	public serConClientThread(Socket s)
	{
		this.s=s;
	}	
	
	
	public void notifyOtherOnline(String self)
	{
		//����û���������ߵ��û�
		HashMap hm=ManageClientThread.hm;
		Iterator it=hm.keySet().iterator();
		
		while(it.hasNext())
		{
			Message m=new Message();
			m.setContent(self);
			m.setMesType(MessageType.message_ret_onlineFriend);
			
			//ȡ�������˵�ID��
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
			//���߳̿��Խ��ܿͻ��˵���Ϣ(���޽���)
			try {
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				Message mes=(Message)ois.readObject();
				System.out.println(mes.getSendTime()+" "+mes.getSender()+"��"+mes.getGetter()+"˵"+mes.getContent());
				//�Դӿͻ��˷�������Ϣ�� message �����ж� Ȼ��������Ӧ�Ĵ���
				
				
				if(mes.getMesType().equals(MessageType.message_normal_mes))
				{
				// ���ת������ 
				serConClientThread  sc=ManageClientThread.getClientThread(mes.getGetter());
		/*         //�ٰ� �����trim()Ū��3��Сʱ���ҵ�����  �����ڻ�TM��֪��Ϊɶ ���˸�����
		 * ����if else���Ϊ�������
		 * 		 */
		
				ObjectOutputStream oos=new ObjectOutputStream(sc.s.getOutputStream());			
				oos.writeObject(mes);
				}
				else if(mes.getMesType().equals(MessageType.message_get_onlineFriend))
				{
					//�Ѹ��˻��ڷ�������¼�Ⱥ��ѷ���
					String result=ManageClientThread.getAllOnlineClient();
					//��Message ����ȥ���ط��ص���Ϣ
					Message m=new Message();
					m.setMesType(MessageType.message_ret_onlineFriend);
					m.setContent(result);  //�����ݷŽ�ȥ
					//��Ϊ���Ƿ������ˣ�����sender�Ƿ������� getter��Ҫ���͸��Ķ��� ����
					m.setGetter(mes.getSender());
					//�����m.setGetter(mes.getSender())������
					//setGetter�����÷���������ȥ���°���������Ĳ����ǿͻ��˷��������˵���Ϣ ����Ҫ��
					//��ǰ��Message����mes
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(m);
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
