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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>大数据计算平台</title>
    <base href="<%=basePath%>">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" href="css/font-awesome.min.css">
	<link rel="stylesheet" href="css/main/main.css">
	<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="js/main/main.js"></script>

  </head>
  
  <body>
    
	<div class="main">

		<div class="top">
			<i class="icon-bar-chart"></i>
			<span>大数据计算平台</span>
			<!-- 右边导航栏 -->
			<div class="user">
				<!-- 右边用户信息展示 -->
				<div class="userInfo">
                    <i class="icon-user"></i>
                    <span>${user.username}</span>

                    <!-- 悬浮用户信息栏 -->
                    <div class="info">
                    	<i class="icon-caret-up"></i>
                        <div class="TXZH">
                            <div class="TX"><i class="icon-user"></i></div>
                            <div class="ZH" title="${user.username}">${user.username}</div>
                        </div>
                        <div class="XGMM item"><a href="main/modifyPass.jsp" target="_blank">修改密码</a></div>
                        <div class="QUIT item"><a href="user/exit">退出</a></div>
                    </div>
				</div>
			</div>
		</div>
 
		<div class="content">
			
			<!-- 左边导航 -->
			<div class="left">

				<a class="left-nav-item" id="data_source"><i class="icon-cloud"></i>数据源管理</a>

				<a class="left-nav-item" id="data_import"><i class="icon-cloud-upload"></i>数据导入</a>

				<a class="left-nav-item" id="algorithm_manage"><i class="icon-cog"></i>算法管理</a>

				<a class="left-nav-item" id="data_analyze"><i class="icon-signal"></i>数据分析</a>

				<a class="left-nav-item" id="flow_manage"><i class="icon-tasks"></i>流程管理</a>

				<a class="left-nav-item" id="data_show"><i class="icon-bar-chart"></i>数据展示</a>

				<a class="left-nav-item" id="azkaban" href="http://www.renyichen.club:22222" target="_blank"><i class="icon-font"></i>Azkaban</a>

				<a class="left-nav-item" id="hdfs" href="http://www.renyichen.club:50070" target="_blank"><i class="icon-h-sign"></i>HDFS</a>

				<a class="left-nav-item" id="yarn" href="http://www.renyichen.club:8088" target="_blank"><i class="icon-flag"></i>YARN</a>
				
			</div>

			<!-- 右边导航 -->
			<div class="right">
				
				<iframe src="main/datasource/report.html"></iframe>

			</div>
			<!-- right -->

		</div>
		<!-- content -->

  </body>
</html>
