package com.myServer.model;

import java.util.*;

public class ManageClientThread  
{
	public static HashMap hm=new HashMap<String,serConClientThread>();   //������ֻ��Ҫһ�ݹ�ϣ��ȥ����
	public static void addClientThread(String userID,serConClientThread sct)  //���ϣ���м����߳�
	{
		hm.put(userID, sct);
	}
	//���ϣ���еõ��߳�
	public static serConClientThread getClientThread(String userID)
	{
		return (serConClientThread)hm.get(userID);
	}
	//������ͨ����ϣmap���Ƿ����õ��߳����ж��û��Ƿ�����
	public static String getAllOnlineClient()
	{
		//ʹ�õ�����ȥ����hm
		Iterator it=hm.keySet().iterator();
		String result="";
		while(it.hasNext())
		{
			result+= it.next().toString()+" ";
		}
		return result;
	}
}
