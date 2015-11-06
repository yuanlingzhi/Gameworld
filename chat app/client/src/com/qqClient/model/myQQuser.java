package com.qqClient.model;

import com.common.User;
public class myQQuser
{
	public boolean checkUser(User u)
	{
		return new myQQconServer().sendLoginInfoToServer(u);
	}
}
