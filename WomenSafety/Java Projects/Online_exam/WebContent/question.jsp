<%@page import="java.util.ArrayList"%>
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
	<form action="result" method="get">

		<c:forEach items="${questionData}" var="x">

			<table border="1">
				<tr>
					<td>${x.question_id}</td>
					<td>${x.question}</td>
				</tr>
				<tr>
					<td><input type="radio" name="${x.question_id}"
						value="${x.option1}"></td>
					<td>${x.option1}</td>
				</tr>
				<tr>
					<td><input type="radio" name="${x.question_id}"
						value="${x.option2}"></td>
					<td>${x.option2}</td>
				</tr>
				<tr>
					<td><input type="radio" name="${x.question_id}"
						value="${x.option3}"></td>
					<td>${x.option3}</td>
				</tr>
				<tr>
					<td><input type="radio" name="${x.question_id}"
						value="${x.option4}"></td>
					<td>${x.option4}</td>
				</tr>
			</table>
		</c:forEach>
		<table>
			<tr>

				<td><input type="submit" value="Ok"></td>
			</tr>
		</table>
	</form>

</body>
</html>