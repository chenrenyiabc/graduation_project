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
                <th>算法主类</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody id="tbale_body"></tbody>
    </table>

    <button type="button" id="newAlgo" class="btn btn-success" data-toggle='modal' data-target='#myModal1'>添加算法</button>

</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    修改算法
                </h4>
            </div>
            <div class="modal-body" id="modifyMain" style="height: 400px;">
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel1">
                    添加
                </h4>
            </div>
            <div class="modal-body" id="newMain" style="height: 400px;">
                <h2>添加算法</h2>
                <form action="AlgorithmServlet?method=newAlgorithm" method="post">
                    请输入算法名：
                    <input type='text' class='algorithm_name' name='algorithm_name' /><br /><br />
                    请输入算法路径：
                    <input type='text' class='algorithm_path' name='algorithm_path' /><br /><br />
                    请输入算法主类：
                    <input type='text' class='algorithm_class' name='algorithm_class' /><br /><br />

                    <input type='submit' class='submit' name='submit' value='保存新的算法' />
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel2">
                    删除算法
                </h4>
            </div>
            <div class="modal-body" id="deleteMain" style="height: 400px;">
            </div>
        </div>
    </div>
</div>

</body>
</html>