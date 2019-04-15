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
</head>
<body>
	<h2>修改MapReduce流程</h2>
	<form action="AnalysisServlet?method=update_mr_flow" method="post">
		请重新输入流程名：
		<input type='text' class='process_name' name='process_name' value='${dataFlow.name }' /><br /><br />
		请重新选择数据源：
		<select class='chooseData' name="chooseData">
			<c:forEach items="${dataSource }" var="ds" varStatus="dsv">
				<option ${dsv.index + 1==dataFlow.source_id ? 'selected':'' } value="${dsv.index + 1 }">${ds }</option>
			</c:forEach>
		</select><br /><br />
		请重新选择算法：
		<select class='chooseAlgorithm' name="chooseAlgorithm">
			<c:forEach items="${algorithm }" var="al" varStatus="alv">
				<option ${alv.index + 1==dataFlow.mr_id ? 'selected':'' } value="${alv.index + 1 }">${al }</option>
			</c:forEach>
		</select><br /><br />
		请重新输入结果保存路径：
		<input type='text' class='resultPath' name='resultPath' value='${dataFlow.result_path }' /><br /><br />
		<input type="hidden" name="flowId" value="${flowId }" />
		<input type='submit' class='submit' name='submit' value='提交修改的信息' />
	</form>
	
</body>
</html>