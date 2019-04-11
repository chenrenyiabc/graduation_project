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
    <h2>修改已有算法</h2>
    <form action="AlgorithmServlet?method=modifyAlgorithm" method="post">
        请重新输入算法名：
        <input type='text' class='algorithm_name' name='algorithm_name' value='${algorithm.algorithm_name }' /><br /><br />
        请重新输入算法路径：
        <input type='text' class='algorithm_path' name='algorithm_path' value='${algorithm.algorithm_path }' /><br /><br />

        <input type="hidden" name="algoId" value="${algoId }" />
        <input type='submit' class='submit' name='submit' value='提交修改的信息' />
    </form>

</body>
</html>