<%@ taglib prefix="s" uri="/struts-tags" %>
<html>

 
<body>
<h1>Struts 2 ModelDriven Example</h1>

<h2>Add a New Customer</h2>
<s:form  action="customerAction" >
  <s:textfield name="firstName" label="First Name" />
  <s:textfield name="lastName" label="Last name" />
  <s:submit />
</s:form>

</body>
</html>