package com.qq.tools;

import java.util.HashMap;

import com.qqClient.view.chat;

public class ManageQQChat
{
	private static HashMap hm=new HashMap<String,chat>();  //��һ��String�����ֱ𴰿����ĸ����ĸ���
	public static void addManageQQChat(String LoginIdWithAntoher,chat c)
	{
		hm.put(LoginIdWithAntoher,c);
	}
	public static chat getManageQQChat(String LoginIdWithAntoher)
	{
		return (chat)hm.get(LoginIdWithAntoher);
	}
}
