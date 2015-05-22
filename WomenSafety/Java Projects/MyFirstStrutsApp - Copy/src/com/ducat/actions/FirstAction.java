package com.ducat.actions;

public class FirstAction {
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String execute()
	{
		this.setMsg("Hello Client !");
		return "success";
	}

}
