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
<script type="text/javascript" src="js/analysis/analysisHQL.js"></script>
<script src="js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>

<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/analysis/analysisHQL.css" />
</head>
<body>

<div class="container">
	<ul class="nav nav-tabs">
  		<li role="presentation" class="mapreduce"><a href="main/analysis/analysisMR.jsp">MapReduce</a></li>
  		<li role="presentation" class="hql active"><a href="javascript:void(0)">HQL</a></li>
	</ul>
	<div id="showmsg" class="showmsg" style="position:absolute">
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
					<span id="show-first-data" value="1">请点击选择</span>&nbsp;<span class="caret"></span>
				</button>
				<ul id="dropdown-menu-1" class="dropdown-menu dropdown-menu-1">
					<li><a class='chooseTable' href='javascript:void(0)'>请点击选择</a></li>
					<c:forEach items="${tableList}" var="ds">
						<li><a class='chooseTable' href='javascript:void(0)'>${ds}</a></li>
					</c:forEach>
				</ul>
			</div><br /><br />
		</div>

		<div class="layoutDiv">
			<span class='typetext'>请选择要操作的列：</span>
			<div class="btn-group">
				<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					<span id="show-first-field" value="1">请点击选择</span>&nbsp;<span class="caret"></span>
				</button>
				<ul id="dropdown-menu-2" class="dropdown-menu dropdown-menu-2">
					<li value='${ftin.index + 1 }'><a class='chooseField' href='javascript:void(0)'>请点击选择</a></li>
					<c:forEach items="${firstTableField}" var="ftf" varStatus="ftin">
						<li value='${ftin.index + 1 }'><a class='chooseField' href='javascript:void(0)'>${ftf.split("\\t")[0]}</a></li>
					</c:forEach>
				</ul>
			</div><br /><br />
		</div>

		<div class="layoutDiv">
			<span class='typetext'>请选择算法：</span>
			<div class="btn-group">
				<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					<span id="show-first-algorithm" value="1"></span>&nbsp;<span class="caret"></span>
				</button>
				<ul id="dropdown-menu-3-string" class="dropdown-menu dropdown-menu-3" style="display:none">
					<li value='5'><a class='chooseAlgorithm' href='javascript:void(0)'>计数</a></li>
					<li value='6'><a class='chooseAlgorithm' href='javascript:void(0)'>自定义HQL</a></li>
				</ul>
				<ul id="dropdown-menu-3-int" class="dropdown-menu dropdown-menu-3" style="display:none">
					<li value='1'><a class='chooseAlgorithm' href='javascript:void(0)'>最大值</a></li>
					<li value='2'><a class='chooseAlgorithm' href='javascript:void(0)'>最小值</a></li>
					<li value='3'><a class='chooseAlgorithm' href='javascript:void(0)'>平均值</a></li>
					<li value='4'><a class='chooseAlgorithm' href='javascript:void(0)'>求和</a></li>
					<li value='5'><a class='chooseAlgorithm' href='javascript:void(0)'>计数</a></li>
					<li value='6'><a class='chooseAlgorithm' href='javascript:void(0)'>自定义HQL</a></li>
				</ul>
				
			</div><br /><br />
		</div>
		
		<div class="layoutDiv type_hql" style="display:none">
			<span class='typetext'>请输入自定义HQL：</span>
			<div class="input-group text3">
				<input type="text" class="form-control hive_sql" id='hive_sql' aria-describedby="sizing-addon1">
			</div>
		</div>

		<div class="layoutDiv type_result">
			<span class='typetext'>请输入结果表名称：</span>
			<div class="input-group text2">
				<input type="text" class="form-control result_table" id='result_table' aria-describedby="sizing-addon1">
			</div>
		

			<button class="btn btn-success" id="saveflow" name='saveflow'>保存计算流程</button>
		</div>

	</div>
</div>


</body>
</html>