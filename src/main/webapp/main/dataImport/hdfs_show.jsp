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
  			<li role="presentation" class="active"><a href="main/dataImport/hdfs_show.jsp">HDFS管理</a></li>
  			<li role="presentation"><a href="main/dataImport/hive_show.jsp">Hive管理</a></li>
		</ul>
		
		<!-- 页头 -->
		<div class="page-header">
  			<h1>HDFS管理</h1>
		</div>
		
		<!-- 用户目录展示 -->
		<div id="hdfsDir-container">
		<h5>根目录： <small id="homePath"></small></h5>
		<h5>当前目录： <small id="currenPath"></small></h5>
		<ul class="list-group" id="showDirUl">
		</ul>
		</div>
		
		<!-- 文件操作组 -->
		<!-- 目录创建 -->
		<div class="btn-group" role="group" aria-label="...">
  			<button type="button" class="btn btn-default" id="mkDirButton" data-toggle="modal" data-target="#myModal1" >创建文件夹</button>
  				<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" id="myModal1" >
  				<div class="modal-dialog modal-lg" role="document">
    			<div class="modal-content">
    				<!--  模态框标题  -->
    				<div class="modal-header">
    					<h4>新建文件夹</h4>
    				</div>
    				
    				<!--  模态框内容，我在此处添加一个表单 -->
    				<div class="modal-body">
	    				<form class="form-horizontal" role="form" action="HDFSFileServlet" method="post">
	      				<div class="form-group">
	      					<input type="hidden" name="method" value="mkdir">
	      					<input type="text" class="form-control" name="idzhiname"  placeholder="请输入新文件夹目录">
	      					<input type="text" class="form-control" name="dirname"  placeholder="请输入要创建文件夹名称">
	      				</div>
	      				    <button type="submit" class="btn btn-info" id="mkDirButton">确定</button>
	                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	      				</form>
      				</div>
    			</div>
  				</div>
				</div>
		</div>	
		
		<!-- 目录文件删除 -->
		<div class="btn-group" role="group" aria-label="...">
  			<button type="button" class="btn btn-default" id="mkDirButton" data-toggle="modal" data-target="#myModal2" >目录/文件删除</button>
  				<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" id="myModal2" >
  				<div class="modal-dialog modal-lg" role="document">
    			<div class="modal-content">
    				<!--  模态框标题  -->
    				<div class="modal-header">
    					<h4>目录/文件删除</h4>
    				</div>
    				
    				<!--  模态框内容，我在此处添加一个表单 -->
    				<div class="modal-body">
	    				<form class="form-horizontal" role="form" action="HDFSFileServlet" method="post">
	      				<div class="form-group">
	      					<input type="hidden" name="method" value="deldir">
	      					<input type="text" class="form-control" name="idzhiname"  placeholder="请输入要删除的文件/目录">
	      				</div>
	      				    <button type="submit" class="btn btn-info" id="mkDirButton">确定</button>
	                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	      				</form>
      				</div>
    			</div>
  				</div>
				</div>		
		</div>
		
		<!-- 文件上传 -->  						
  		<div class="btn-group" role="group" aria-label="...">
  			<button type="button" class="btn btn-default" id="mkDirButton" data-toggle="modal" data-target="#myModal3" >文件上传</button>
  				<div class="modal fade tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" id="myModal3">
  				<div class="modal-dialog modal-lg" role="document">
    			<div class="modal-content">
    				<!--  模态框标题  -->
    				<div class="modal-header">
    					<h4>文件上传</h4>
    				</div>
    				
    				<!--  模态框内容，我在此处添加一个表单 -->
    				<div class="modal-body">
	    				<form class="form-horizontal" role="form" action="HDFSFileServlet" method="post" enctype="multipart/form-data">
	      				<div class="form-group">
	      					<input type="hidden" name="method" value="upload">
	      					<input type="file" name="uploadFile" />
	      					<input type="text" class="form-control" name="dirName"  placeholder="请输入保存至的目录名称">
	      					<input type="text" class="form-control" name="fileNewName"  placeholder="请输入保存至的文件名称">
	      					<input type="radio" name="isoverwrite" value="overwrite" />覆盖
							<input type="radio" name="isoverwrite" value="notoverwrite" checked="checked">不覆盖
	      				</div>
	      				    <button type="submit" class="btn btn-info" id="mkDirButton">确定</button>
	                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	      				</form>
      				</div>
    			</div>
  				</div>
				</div>		
		</div>	
		
		<!-- 数据库与Hdfs交互-->
		<!-- 页头 -->
		<div class="page-header">
  			<h1>数据库导出</h1>
		</div>
		
		<!-- mysql数据预览 -->
		<h5>Mysql数据预览：</h5>
		<div class="form-group">
  			<select class="selectpicker"  name="mysqlTableLoad" id="mysqlTableLoadSelect">
  			</select>
		</div>
		<table class="table table-striped table-hover" id="mysqltablesShow">
		</table>
		
		<!-- mysql操作组 -->
		<!-- 单表导入 -->
		<div class="btn-group" role="group" aria-label="...">
  			<button type="button" class="btn btn-default" id="mkDirButton" data-toggle="modal" data-target="#myModal4" >单表导入</button>
  				<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" id="myModal4" >
  				<div class="modal-dialog modal-lg" role="document">
    			<div class="modal-content">
    				<!--  模态框标题  -->
    				<div class="modal-header">
    					<h4>单表导入</h4>
    				</div>
    				
    				<!--  模态框内容，我在此处添加一个表单 -->
    				<div class="modal-body">
	    				<form class="form-horizontal" role="form" action="HDFSMysqlServlet" method="post">
	      				<div class="form-group">
	      					<input type="hidden" name="method" value="inputMysql2HdfsSingle">
	      					<select class="selectpicker"  name="mysqlTableLoad" id="mysqlTableLoadSelectmotai">
  							</select>
  							<div id="mysqlColumnLoadSelect">
  							</div>
  							<input type='text' placeholder='HDFS目标目录(需传入新目录)' id='msqlInputHdtfdanDir' name='targetDir' class="form-control"/>
	      				</div>
	      				    <button type="button" class="btn btn-info" id="subButton">确定</button>
	                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	      				</form>
      				</div>
    			</div>
  				</div>
				</div>
		</div>	
		
		<!-- 用户自定义导入 -->
		<div class="btn-group" role="group" aria-label="...">
  			<button type="button" class="btn btn-default" id="mkDirButton" data-toggle="modal" data-target="#myModal5" >用户自定义导入</button>
  				<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" id="myModal5" >
  				<div class="modal-dialog modal-lg" role="document">
    			<div class="modal-content">
    				<!--  模态框标题  -->
    				<div class="modal-header">
    					<h4>用户自定义导入</h4>
    				</div>
    				
    				<!--  模态框内容，我在此处添加一个表单 -->
    				<div class="modal-body">
	    				<form class="form-horizontal" role="form" action="HDFSMysqlServlet" method="post">
	      				<div class="form-group">
	      					<input type="hidden" name="method" value="inputMysql2HdfsSelf">
							<input type="text" class="form-control" name="selfCmd"  placeholder="请输入sqoop自定义语句">
	      				    <button type="submit" class="btn btn-info" id="subButton">确定</button>
	                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                    </div>
	      				</form>
      				</div>
    			</div>
  				</div>
				</div>
		</div>	
		
	</div>
	
	
</body>
</html>