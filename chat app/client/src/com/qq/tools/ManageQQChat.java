package com.qq.tools;

import java.util.HashMap;

import com.qqClient.view.chat;

public class ManageQQChat
{
	private static HashMap hm=new HashMap<String,chat>();  //第一个String用来分别窗口是哪个与哪个聊
	public static void addManageQQChat(String LoginIdWithAntoher,chat c)
	{
		hm.put(LoginIdWithAntoher,c);
	}
	public static chat getManageQQChat(String LoginIdWithAntoher)
	{
		return (chat)hm.get(LoginIdWithAntoher);
	}
}
