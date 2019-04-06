<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>大数据计算平台</title>
	<link rel="stylesheet" href="css/font-awesome.min.css">
	<link rel="stylesheet" href="css/index/index.css">
	<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="js/echarts.js"></script>
	<script type="text/javascript" src="js/index/index.js"></script>
	<script type="text/javascript" src="js/index/common-index.js"></script>
</head>
<body>
	<!-- 整体 -->
	<div class="main">

		<div class="top"><i class="icon-bar-chart"></i>大数据计算平台</div>

		<div class="description">
			
			<div><i class="icon-bar-chart"></i>Generate Your Reports And Analyse It.</div>

		</div>

		<!-- 登录 -->
		<div class="content">

			<form id="login_form">
				
				<div class="form-title">账号密码登录</div>

				<div class="username-item">
					<input type="text" name="username" id="username" placeholder="账号" oninput="onInput(this)" onblur="onInput(this)" xzcd="20"/>
					<div class="tip"></div>
				</div>

				<div class="password-item">
					<input type="password" name="password" id="password" placeholder="密码" oninput="onInput(this)" onblur="onInput(this)" xzcd="20"/>
					<div class="tip"></div>
				</div>

				<div class="form-submit">
					<input type="button" onclick="login()" value="登录">
					<a class="forget" href="index/forgetPwd.html" target="_blank">忘记密码?</a>
					<a class="regist" href="index/regist.html" target="_blank">注册账号</a>
				</div>

			</form>
			
		</div>

	</div>
	
</body>
</html>