<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.util.List,com.ducat.dto.EmployeeDTO"
    %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 
    prefix="c" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
<form action ="search" method="post">
Name <input type="text" name="empName" >
<input type="submit" value = "search"> 
</form>
<table border="1">
<c:forEach items="${empData}" var="x">
<tr>
<td>${x.id}</td>
<td>${x.name}</td>
<td>${x.salary}</td>
</tr>
</c:forEach>
</table>
<%-- <% 
List<EmployeeDTO> empList = (List<EmployeeDTO>)request.getAttribute("empData");
if(empList!=null && empList.size()>0){
%>
List is ${empData}
<table border="1">
<tr><th>EmpNo</th><th>Name</th><th>Salary</th></tr>
<% 	
for(EmployeeDTO empDTO : empList){
%>
<tr>
<td><%=empDTO.getId() %></td>
<td><%=empDTO.getName() %></td>
<td><%=empDTO.getSalary() %></td>
</tr>
<% 	
}
%>
</table>
<%
}
%> --%>

</body>
</html>