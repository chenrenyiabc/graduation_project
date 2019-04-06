<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"; %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<base href="<%=path%>">
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/echarts.js"></script>
<script type="text/javascript" src="js/analysis/analysis.js"></script>
<script src="js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>

<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/analysis/analysis.css" />
</head>
<body>

	<!--
    	<label><input type="radio" name="operate" class="mapreduce" value="MapReduce"/>MapReduce</label>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<label><input type="radio" name="operate" class="hql" value="HQL"/>HQL</label>
</div><br />
<h3>数据统计分析</h3>
<span>请选择要进行的操作</span><br /><br />
    -->
	<div class="container">
		<ul class="nav nav-tabs">
			<li role="presentation" class="mapreduce"><a href="javascript:void(0)">MapReduce</a></li>
			<li role="presentation" class="hql"><a href="javascript:void(0)">HQL</a></li>
		</ul>
		<div class="showmsg"></div>
	</div>



</body>
</html>