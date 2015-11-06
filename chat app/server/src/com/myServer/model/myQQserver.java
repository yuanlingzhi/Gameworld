/*
 * ����QQ������   ��������� �ȴ��ͻ�������
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
			System.out.println("��������9988����");
			
			while(true)
			{	
				//��Ϊÿ����login���ڵ��½��ʱ��ÿ�ζ������˸��µ�socketȥ���ӷ�����������Ҫ���¼��������ڷ��������´�����acceptȥ��Ӧ�µ�����
				//�����Socket socket=server.accept(); ����while������棬 ��ôsocket����ʼ���ǵ�һ�ε��login��½���������Ķ���  ���۵ڶ�����ʲô��Ϣ
				//�����б仯
				Socket socket=server.accept(); 
				System.out.println("������");
				//�����û���������Ϣ�� ��Ȼ���û���������
				ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
				u=(User)ois.readObject();
				Message mes=new Message();			
				ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
				if(u.getPassword().equals("123456")&&checkRepeatLog())
				{				
					mes.setMesType("1");
					oos.writeObject(mes);	 
					// Ҫ����һ���̣߳��ø��̷߳�����   ��������ÿͻ��˵�ͨ��
					serConClientThread scct=new serConClientThread(socket);
					//�������������̼߳��뵽��ϣ���� 
					ManageClientThread.addClientThread(u.getId(), scct);
			
					
					//������ÿͻ���ͨѶ���߳�
					scct.start();
					
					// ֪ͨ�Լ����÷�������ʱˢ�º����б�
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
