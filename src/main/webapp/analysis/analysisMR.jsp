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
<script type="text/javascript" src="js/analysis/analysisMR.js"></script>
<script src="js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>

<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/analysis/analysisMR.css" />
</head>
<body>
<div class="container">
	<ul class="nav nav-tabs">
  		<li role="presentation" id='mapreduce' class="mapreduce active"><a href="javascript:void(0)">MapReduce</a></li>
  		<li role="presentation" id='hql' class="hql"><a href="AnalysisServlet?method=tohql">HQL</a></li>
	</ul>
	<div id="showmsg" class="showmsg" style="display:none; position:absolute">
		<div class="layoutDiv">
			<span class='typetext'>请输入流程名：</span>
			<div class="input-group text1">
	  			<input type="text" class="form-control process_name" id='process_name' aria-describedby="sizing-addon1">
			</div>
		</div>
		
		<div class="layoutDiv">
			<span class='typetext'>请选择数据源：</span>
			<div class="btn-group">
			  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			    <span id="show-first-data" value="1"></span>&nbsp;<span class="caret"></span>
			  </button>
			  <ul id="dropdown-menu-1" class="dropdown-menu"></ul>
			</div><br /><br />
		</div>
		
		<div class="layoutDiv">
			<span class='typetext'>请选择算法：</span>
			<div class="btn-group">
			  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			    <span id="show-first-algorithm" value="1"></span>&nbsp;<span class="caret"></span>
			  </button>
			  <ul id="dropdown-menu-2" class="dropdown-menu"></ul>
			</div><br /><br />
		</div>
		
		<div class="layoutDiv">
			<span class='typetext'>请输入结果保存路径：</span>
			<div class="input-group text2">
	  			<input type="text" class="form-control resultPath" id='resultPath' name='resultPath' aria-describedby="sizing-addon1">
			</div>
		</div>
		
		<div class="layoutDiv">
			<button class="btn btn-success" id="saveflow" name='saveflow'>保存计算流程</button>
		</div>
	</div>
	
</div>


</body>
</html>