package com.qqClient.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/*
 * �ͻ������ӷ������ĺ�̨����
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
			//����ߵĵ�¼��Ϣ�����͹�ȥ
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			
			//������������Ϣ
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			Message mes=(Message)ois.readObject();
			if(mes.getMesType().equals("1"))
			{
				//��ʾ��½�ɹ���Ȼ��Ҫ����һ�� �ͻ�������������ӳɹ����߳�	
				ClientConServerThread ccst=new ClientConServerThread(s);
				//���� ��ϣ �����б�  ˳�������߳�
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
