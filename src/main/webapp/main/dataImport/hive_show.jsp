<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"; %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<base href="<%=path%>">
<link rel="stylesheet" href="css/bootstrap.min.css">
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/dataImport/hdfs_show.js"></script>
<script type="text/javascript" src="js/dataImport/hive_show.js"></script>

</head>

<body>
	<div class="container">
		<!-- 导航栏 -->
  		<ul class="nav nav-tabs">
  			<li role="presentation" ><a href="main/dataImport/hdfs_show.jsp">HDFS管理</a></li>
  			<li role="presentation" class="active"><a href="main/dataImport/hive_show.jsp">Hive管理</a></li>
		</ul>
		
		<!-- 页头 -->
		<div class="page-header">
  			<h1>HIVE管理</h1>
		</div>
	
		<div>
		<!-- Hive数据仓库展示 -->
		<div>
			<h5>Hive表数据预览：</h5>
			<select class="selectpicker" name="hiveTableshow" id="hiveTableshowXiala">
        	</select>
        	<table class="table table-striped table-hover" id="hiveDatashow">
			</table>
			
		</div>
		
		<!-- Hive操作组 -->
		<!-- 文件上传 -->
		<div class="btn-group" role="group" aria-label="...">
  			<button type="button" class="btn btn-default" id="mkDirButton" data-toggle="modal" data-target="#myModal1" >文件上传</button>
  				<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" id="myModal1" >
  				<div class="modal-dialog modal-lg" role="document">
    			<div class="modal-content">
    				<div class="row">
    				<div class="col-md-8">
    				
    				<!--  模态框标题  -->
    				<div class="modal-header">
    					<h4>文件上传</h4>
    				</div>
    				
    				<!--  模态框内容，我在此处添加一个表单 -->
    				<div class="modal-body">
	    				<form class="form-horizontal" role="form" action="HiveServlet" method="post" enctype="multipart/form-data">
	      				<div class="form-group">
	      					<input type="hidden" name="method" value="uploadFile2Hive">
	      					<input type="file" name="uploadFile" />
	      					<input type="text" class="form-control" name="tableName"  placeholder="请输入表名">
	      					<input type="text" class="form-control" name="delimiter"  placeholder="请输入分割符">
	      					<input type="radio" name="overwrite" value="overwrite" />覆盖目标数据
							<input type="radio" name="overwrite" value="notoverwrite" checked="checked">不覆盖目标数据<br/>
							<input type="radio" name="addcolumn" value="autoColumn" />自动根据首行匹配列
							<input type="radio" name="addcolumn" value="selfColumn" checked="checked">自定义列<br/>
							<input type="button" value="添加列信息" id="addColumnButton"/><br/>
	      				</div>
	      				    <button type="submit" class="btn btn-info" id="uploadButton">确定</button>
	                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	      				</form>
      				</div>
      				
      				</div>
      				</div>
    			</div>
  				</div>
				</div>
		</div>	
		
		
		<!-- 数据库与Hive交互 -->
		<div class="page-header">
  			<h1>数据库导出</h1>
		</div>
		
		<!-- HiveMysql操作组 -->
		<!-- 单表导入 -->
		<div class="btn-group" role="group" aria-label="...">
  			<button type="button" class="btn btn-default" id="mkDirButton" data-toggle="modal" data-target="#myModal2" >单表导入</button>
  				<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" id="myModal2" >
  				<div class="modal-dialog modal-lg" role="document">
    			<div class="modal-content">
    				<!--  模态框标题  -->
    				<div class="modal-header">
    					<h4>单表导入</h4>
    				</div>
    				
    				<!--  模态框内容，我在此处添加一个表单 -->
    				<div class="modal-body">
	    				<form class="form-horizontal" role="form" action="HiveServlet" method="post">
	      				<div class="form-group">
	      					<input type="hidden" name="method" value="inputMysql2HdfsSingle">
							<select name="mysqlTableLoad" id="mysqlTableLoadSelect">		   
        					</select>
							<div id="mysqlColumnLoadSelect">
							</div>
        					<input type="text" class="form-control" name="tableName"  placeholder="HIVE目标表" id="msqlInputHivetable">
	      				</div>
	      				    <button type="button" class="btn btn-info" id="uploadButton">确定</button>
	                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	      				</form>
      				</div>
    			</div>
  				</div>
				</div>
		</div>	
		
		<!-- 自定义导入 -->
		<div class="btn-group" role="group" aria-label="...">
  			<button type="button" class="btn btn-default" id="mkDirButton" data-toggle="modal" data-target="#myModal3" >自定义导入</button>
  				<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" id="myModal3" >
  				<div class="modal-dialog modal-lg" role="document">
    			<div class="modal-content">
    				<!--  模态框标题  -->
    				<div class="modal-header">
    					<h4>自定义导入</h4>
    				</div>
    				
    				<!--  模态框内容，我在此处添加一个表单 -->
    				<div class="modal-body">
	    				<form class="form-horizontal" role="form" action="HiveServlet" method="post">
	      				<div class="form-group">
	      					<input type="hidden" name="method" value="inputMysql2HiveSelf">
        					<input type="text" class="form-control" name="selfCmd"  placeholder="自定义Mysql导入Hive语句" id="msqlInputHivetable">
	      				</div>
	      				    <button type="submit" class="btn btn-info" id="mysqlselfLoadButton">确定</button>
	                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	      				</form>
      				</div>
    			</div>
  				</div>
				</div>
		</div>	
	</div>
</div>

</body>
</html>