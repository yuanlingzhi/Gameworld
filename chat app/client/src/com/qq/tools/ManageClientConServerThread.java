package com.qq.tools;

import java.util.HashMap;

//管理  客户端保持与服务器通讯线程  的工具
public class ManageClientConServerThread 
{
	public static HashMap hm=new HashMap<String,ClientConServerThread>();        //第一个为用户的账号为KEY值  为了区别
	public static void addClientConServerThread(String userID,ClientConServerThread ccst)
	{
		hm.put(userID, ccst);
	}
	public static ClientConServerThread getClientConServerThread(String userID)
	{
		return (ClientConServerThread)hm.get(userID);
	}
}
