<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<s:form action="loginAC" method="post">
		<s:textfield name="userid" label="Enter Userid" />
		<s:password name="password" label="Enter Password" />
		<s:submit action="add" />
		<s:reset />

	</s:form>
</body>
</html>