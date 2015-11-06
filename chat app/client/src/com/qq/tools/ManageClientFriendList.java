package com.qq.tools;

import java.util.HashMap;

import com.qqClient.view.friend_view;

public class ManageClientFriendList
{
	public static HashMap hm=new HashMap<String,friend_view >();
	
	public static void addfriend_view(String userID,friend_view fv)
	{
		hm.put(userID, fv);
	}
	public static friend_view getfriend_view(String userID)
	{
		return (friend_view)hm.get(userID);
	}
}
