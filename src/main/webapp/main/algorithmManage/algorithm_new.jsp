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
    <script type="text/javascript" src="js/algorithm/algorithm_new.js"></script>
    <script src="js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>

    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <link rel="stylesheet" href="css/algorithm/algorithm_new.css" />
</head>
<body>
<div class="container">
    <ul class="nav nav-tabs">
        <li role="presentation" id='algorithm_show' class="algorithm_show"><a href="main/algorothmManage/algorithm.jsp">查看</a></li>
        <li role="presentation" id='algorithm_new' class="algorithm_new active"><a href="main/algorothmManage/algorithm_new.jsp">新增</a></li>
        <li role="presentation" id='algorithm_modify' class="algorithm_modify"><a href="main/algorothmManage/algorithm_modify.jsp">修改</a></li>
        <li role="presentation" id='algorithm_delete' class="algorithm_delete"><a href="main/algorothmManage/algorithm_delete.jsp">删除</a></li>
    </ul>

    <div id="showmsg" class="showmsg" style="display:none; position:absolute">
        this is algorithm_new.jsp

    </div>


</div>


</body>
</html>