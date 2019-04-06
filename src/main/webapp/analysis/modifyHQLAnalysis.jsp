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
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/analysis/modifyHQLAnalysis.js"></script>
</head>
<body>
	<h2>修改HQL流程</h2>

		请重新输入流程名：
		<input type='text' class='process_name' name='process_name' value='${dataFlow.name }' /><br /><br />
		原HQL语句：
		<input type='text' class='hive_sql' name='hive_sql' value='${dataFlow.hive_sql }' /><br /><br />
		请重新选择数据源：
		<select class='chooseData' name="chooseTable">
			<option class="chooseTable"></option>
			<c:forEach items="${hqlTableList }" var="ht" varStatus="htv">
				<option class="chooseTable" value="${htv.index + 1 }">${ht }</option>
			</c:forEach>
		</select><br /><br />
		请重新选择列：
		<select class='fieldName' name="fieldName"></select><br /><br />
		请重新选择算法：
		<select class='chooseAlgorithm' name="chooseAlgorithm"></select>
		<span class='typeSql'>&nbsp;&nbsp;&nbsp;&nbsp;请输入自定义SQL：<input type='text' id='hive_sql' name='hive_sql' /></span><br /><br />
		请重新输入结果保存路径：
		<input type='text' class='result_table' name='result_table' value='${dataFlow.result_table }' /><br /><br />
		<input type="hidden" class="flowId" name="flowId" value="${flowId }" />
		<input type='submit' class='submit' name='submit' value='提交修改的信息' />

</body>
</html>