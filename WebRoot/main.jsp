<%@page import="com.ordersystem.common.model.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user=null;
if(session.getAttribute("user")==null){

response.sendRedirect(basePath);
}
else{
  user= (User)session.getAttribute("user");
  String name=user.getUser_name();
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<title>Main</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/main.css" rel="stylesheet">
	<style type="text/css">
#pagewrap, #headwrap, #contentwrap, #footer {
  width:100%;
}
table{
  border:1px solid;
  text-align:center;}
  
td>img{
  width:40px;
  height:40px;} 
</style>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">--></head>

<body>
	<div id="pagewrap">
		<div id="headwrap"><h1>欢迎使用订餐后台管理系统</h1><p>管理员：<%=user.getUser_name()%></p></div>
		<div id="contentwrap">
			<div class="tabbable tabs-left" style="margin-bottom: 18px;">
				<ul id="myTab" class="nav nav-tabs">
					<li class="active">
						<a href="#m_dish" data-toggle="tab">菜品管理</a>
					</li>
					<li class="">
						<a href="#m_material" data-toggle="tab">食材管理</a>
					</li>

					<li class="">
						<a href="#m_order" data-toggle="tab">订单管理</a>
					</li>
					<li class="">
						<a href="#m_user" data-toggle="tab">用户管理</a>
					</li>
				</ul>
				<div class="tab-content" style="padding-bottom: 9px; border-bottom: 1px solid #ddd;">
					<div class="tab-pane active" id="m_dish"></div>
					<div class="tab-pane" id="m_material"></div>
					<div class="tab-pane" id="m_order"></div>
					<div class="tab-pane" id="m_user"></div>
					
				</div>

			</div>
		</div>
		<div id="footer">
			Copyright © 2013
			<a href="#">陈畅</a>
			版权所有。保留所有权利。
		</div>
		<!-- Modal -->
        <div id="panel" class="hide"></div>
<script src="js/jquery.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/ajaxfileupload.js"></script>
<script src="js/class.js"></script>
<script src="js/table.js"></script>
<script src="js/panel.js"></script>
<script src="js/content.js"></script>

<script type="text/javascript">
$('#myTab a').click(function (e) {
  e.preventDefault();
  $(this).tab('show');
});
</script>
</body>
</html>