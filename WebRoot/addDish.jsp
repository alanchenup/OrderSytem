<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'addDish.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>

		<form method="post" action="<%=basePath%>pic" name="update" >
		<input type="file"  name="image"/> 
		<input type="submit" value="上传"/>
		</form>
		
	   <form method="post" action="/add" name="addDish">
		
		<input type="text" name="dish_name"/>
		<input type="text" name="dish_content"/>
		<input type="text" name="dish_price"/>
		<input type="text" name="dish_c_price"/>
		<input type="text" name="dish_sales"/>
		<input type="text" name="m_mate"/>
		<input type="text" name="v_mate"/>
		<input type="submit" name="submit" value="submit"/>
		<input type="button" name="cancel" value="cancel"/>
	</form>
</body>
</html>
