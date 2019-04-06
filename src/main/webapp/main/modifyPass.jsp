<%@ page language="java" import="java.util.*,com.bigdata.bean.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user = (User)session.getAttribute("user");
if(user == null){
	response.sendRedirect("../index/index.jsp");
	return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>修改密码</title>
<base href="<%=basePath%>">
<link rel="stylesheet" href="css/main/modifyPass.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/index/common-index.js"></script>
</head>
<body>

<!-- 整体 -->
<div class="main">

<!-- 顶部s -->
<div class="top"><i class="icon-bar-chart"></i>大数据计算平台</div>

<!-- content -->
<div class="content">

<form id="main_modifyPass_form">

<div class="form-title">账号设置&nbsp;|&nbsp;<span>修改密码</span></div>

<div class="password-item">
<input type="hidden" name="id" value="${user.id}"/>
<input type="password" name="password" id="password" placeholder="密码" xzcd="20" oninput="onInput(this)" onblur="onInput(this)"/>
<div class="tip"></div>
</div>

<div class="password-item">
<input type="password" name="password2" id="password2" placeholder="密码确认" xzcd="20" oninput="onInput(this)" onblur="valipass(this)"/>
<div class="tip"></div>
</div>

<div class="form-submit">
<input type="button" onclick="modifyPass()" value="重置密码">
</div>

</form>

</div>

</div>

</body>
</html>
