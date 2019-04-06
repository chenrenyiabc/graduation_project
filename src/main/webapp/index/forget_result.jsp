<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>重置密码成功</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="css/index/result.css">
	<link rel="stylesheet" href="css/font-awesome.min.css">

  </head>
  
  <body>
    <div class="main">

		<div class="top"><i class="icon-bar-chart"></i>大数据计算平台</div>

		<div class="result">
			
			<i class="icon-ok-sign"></i>
			<span>密码重置成功</span>

		</div>

		<div class="tologin">
			<a href="index/index.jsp"><span class="icon-hand-right"></span>点击我登录哦</a>
		</div>

	</div>
  </body>
</html>
