<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.exam.dto.UserDTO"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Profile !!</title>
</head>
<body>
	Welcome
	<%=session.getAttribute("user")%>
	<br></br>
	<%
		UserDTO userDTO = (UserDTO) request.getAttribute("userDTO");
	%>
	<table border="1">
		<tr>
			<td>Name :</td>
			<td><%=userDTO.getName()%></td>
		</tr>
		<tr>
			<td>Email :</td>
			<td><%=userDTO.getEmail()%></td>
		</tr>
		<tr>
			<td>Age :</td>
			<td><%=userDTO.getAge()%></td>
		</tr>
		<tr>
			<td>UserName :</td>
			<td><%=userDTO.getUserid()%></td>
		</tr>
	</table>


	<table border="1">
		<tr>
			<td>Choose Quiz</td>
			<td><form action="quiz_master">
					<input type="submit" value="Choose Quiz" />
				</form></td>
		</tr>
		<tr>
			<td>Upload a Quiz</td>
			<td><form action="quiz_upload_master">
					<input type="submit" value="Create Quiz" />
				</form></td>
		</tr>
		<tr>

			<td>Show Results</td>
			<td><form action="allresults">
					<input type="submit" value="My Results" />
				</form></td>
		</tr>

	</table>
</body>
</html>