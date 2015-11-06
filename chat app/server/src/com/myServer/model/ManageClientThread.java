package com.myServer.model;

import java.util.*;

public class ManageClientThread  
{
	public static HashMap hm=new HashMap<String,serConClientThread>();   //服务器只需要一份哈希表去管理
	public static void addClientThread(String userID,serConClientThread sct)  //向哈希表中加入线程
	{
		hm.put(userID, sct);
	}
	//向哈希表中得到线程
	public static serConClientThread getClientThread(String userID)
	{
		return (serConClientThread)hm.get(userID);
	}
	//还可以通过哈希map中是否能拿到线程来判断用户是否在线
	public static String getAllOnlineClient()
	{
		//使用迭代器去遍历hm
		Iterator it=hm.keySet().iterator();
		String result="";
		while(it.hasNext())
		{
			result+= it.next().toString()+" ";
		}
		return result;
	}
}
