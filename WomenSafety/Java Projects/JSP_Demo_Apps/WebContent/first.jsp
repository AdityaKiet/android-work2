<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"  import="java.sql.*"
    autoFlush="true" buffer="8kb" errorPage="error.jsp" session="true"
    info="AAAAAAA" 
    
     %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! int x =10; %>
<%
int a = 100;
int b = 200;
int c = a +b ;

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<b>Sum is <%=c %></b>
</body>
</html>