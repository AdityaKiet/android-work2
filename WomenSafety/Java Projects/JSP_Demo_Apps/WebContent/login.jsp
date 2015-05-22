<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login JSP</title>
</head>
<body>
<%
String userid = request.getParameter("userid");
String password = request.getParameter("password");
if(userid!=null && password!=null){
	if(userid.trim().length()>0 && password.trim().length()>0){
if(userid.equals("amit") && password.equals("123")){
response.sendRedirect("product.jsp");
%>

<%	
}
else
{
%>
<h2>Invalid Userid and Password</h2>
<%
}
}
}
%>
<form action="login.jsp" method="post">
<table>
<tr>
<td>Userid </td><td><input type="text" name="userid"/></td>
</tr>
<tr>
<td>Password </td><td><input type="password" name="password"/></td>

</tr>

<tr>
<td><input type="submit" value="Submit"></td><td><input type="reset" value="Clear"></td>
</tr>
</table>
</form>
</body>
</html>