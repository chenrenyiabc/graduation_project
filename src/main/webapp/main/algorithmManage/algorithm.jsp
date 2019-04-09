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
    <script type="text/javascript" src="js/algorithm/algorithm.js"></script>
    <script src="js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>

    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <link rel="stylesheet" href="css/algorithm/algorithm.css" />
</head>
<body>
<div class="container">

    <table class="table table-hover">
        <thead>
            <tr>
                <th>算法ID</th>
                <th>算法名</th>
                <th>算法路径</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody id="tbale_body"></tbody>
    </table>


</div>


</body>
</html>