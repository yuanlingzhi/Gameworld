package com.qq.tools;

import java.util.HashMap;

//����  �ͻ��˱����������ͨѶ�߳�  �Ĺ���
public class ManageClientConServerThread 
{
	public static HashMap hm=new HashMap<String,ClientConServerThread>();        //��һ��Ϊ�û����˺�ΪKEYֵ  Ϊ������
	public static void addClientConServerThread(String userID,ClientConServerThread ccst)
	{
		hm.put(userID, ccst);
	}
	public static ClientConServerThread getClientConServerThread(String userID)
	{
		return (ClientConServerThread)hm.get(userID);
	}
}
