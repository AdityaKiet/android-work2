<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org   /TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login !!!!</title>
</head>
<body>

	<table border="1">
		<form action="Login" method="post">
			<tr>
				<td>Name</td>
				<td><input type="text" name="userid" /></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /> <br></td>
				<td><input type="reset" value="Reset" /> <br></td>
			</tr>
		</form>
		<%
			if (request.getAttribute("message") != (""))
				out.println(request.getAttribute("message"));
			else
				out.println("");
		%>

	</table>
	<div id="footer">
		<p>
		<div id="footer">
			<form action="admin.jsp" method="post">
				<input type="submit" value="Admin Login!!" />
			</form>
		</div>
		</p>
	</div>
</body>
</html>