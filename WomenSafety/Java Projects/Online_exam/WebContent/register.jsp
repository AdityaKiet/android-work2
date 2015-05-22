<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="register" method="post">
<table>
<tr>
<td>Name </td><td><input type="text" name="name" required="required"/></td>
</tr>
<tr>
<td>Age </td><td><input type="number" name="age" min="1" max="99" required="required"/></td>
</tr>
<tr>
<td>Email </td><td><input type="text" name="email" required="required"/></td>
</tr>
<tr>
<td>UserId </td><td><input type="text" name="userid" required="required"/></td>
</tr>
<tr>
<td>Password </td><td><input type="password" name="password" required="required"/></td>
</tr>
<tr>
<td><input type="submit" value="Submit"></td><td><input type="reset" value="Clear"></td>
</tr>
</table>
</form>
</body>
</html>