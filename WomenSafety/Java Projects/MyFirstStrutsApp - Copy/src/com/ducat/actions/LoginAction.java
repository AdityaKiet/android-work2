package com.ducat.actions;

public class LoginAction {
private String userid;
private String password;
private String message;
public String checkLogin()
{
	if(this.getUserid().equals("amit") && this.getPassword().equals("123")){
		this.setMessage("Welcome "+this.getUserid());
		return "success";
	}
	else
	{
		this.setMessage("Invalid Userid and Password ");
		return "fail";
	}
}
public String getUserid() {
	return userid;
}
public void setUserid(String userid) {
	this.userid = userid;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}

}
