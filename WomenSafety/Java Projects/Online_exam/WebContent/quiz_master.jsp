<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz</title>
</head>
<body>
	
	<form action="question" method="post">
		
		<table border="1">
			<c:forEach items="${quizData}" var="x">
				<tr>
					<td>${x.quiz_id}</td>
					<td>${x.quiz_name}</td>
					<td><input type="radio" name="quiz_id" value="${x.quiz_id}">
				</tr>
			</c:forEach>
		</table>
		<table>
			<tr>
				<td><input type="submit" value="Ok"></td>
			</tr>
		</table>
	</form>
</body>
</html>