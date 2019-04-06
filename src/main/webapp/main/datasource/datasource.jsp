<%@ page language="java" import="java.util.*,com.bigdata.bean.*,com.bigdata.service.datasource.*,com.bigdata.service.datagroup.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user = (User)session.getAttribute("user");
if(user == null){
%>
<script type="text/javascript">
	window.parent.location.href = "<%=basePath%>index/index.jsp";
</script>
<%
	return;
}
List<DataGroup> list = (List<DataGroup>)session.getAttribute("groups");
//if(list == null){
	DataGroupService dgService = new DataGroupService();
	list = dgService.getDataGroups(user.getId());
	session.setAttribute("groups", list);
	dgService.close();
//}
Map<String, List<DataSource>> datasources = (Map<String, List<DataSource>>)session.getAttribute("datasources");
DataSourceService dsService = new DataSourceService();
//if(datasources == null){
	datasources = dsService.getDataSourceList(user.getId());
	session.setAttribute("datasources", datasources);
//}
String datasourceStr = dsService.getDataSourceListStr(datasources);
System.out.println(datasourceStr);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>登录成功</title>
    <base href="<%=basePath%>">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="css/font-awesome.min.css">
	<link rel="stylesheet" href="css/datasource/datasource.css">
	<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript">
		var groups = ${groups};
		var datasources = JSON.parse('<%=datasourceStr%>');
	</script>
	<script type="text/javascript" src="js/echarts.js"></script>
	<script type="text/javascript" src="js/datasource/datasource.js"></script>
  </head>
  
  <body>
	<!-- 列表 -->
	<div class="container">

		<!-- 显示数据类别报表 -->
		<div class="group-report" id="group_report">

		</div>

		<div class="list" id="list">
			
			<!-- 数据类别列表 -->
			<div class="group-list">

				<i class="icon-double-angle-left" title="返回数据源报表" id="back_to_report"></i>
				
				<div class="title">
					<i class="icon-th-list"></i>数据源类别<span class="icon-plus" onclick="addGroup()"></span>
				</div>

				<div id="group_list">

					<c:forEach items="${groups}" var="group" varStatus="status">
						<div class="a-item">
							<a id="${group.id}" class="group-item" onclick="showDatasourceList(this)">
								<i class="icon-asterisk"></i>
								<span class="group_name">${group.name}</span>
								<span class="tools">
									<i title="修改" class="icon-edit" id="edit_${group.id}" onclick="editGroup(this)"></i>
									<i title="删除" class="icon-minus-sign" id="del_${group.id}" onclick="delGroup(this)"></i>
								</span>
								<span class="number">${group.userId}</span>
							</a>
						</div>
						
					</c:forEach>

				</div>
				

			</div>

			<!-- 显示当前选中数据类别下的数据源 -->
			<div class="datasource-list" id="datasource_list">

				<div class="title">
					<i class="icon-th-list"></i>数据源列表
				</div>

				<div id="table_list">
					
					<div class="tools-bar">
						<div class="tip-title">当前数据源类别：默认</div>
					</div>

					<table id="data_table">
						
						<tr>
							<th>序号</th>
							<th>名称</th>
							<th>数据源类型</th>
							<th>创建时间</th>
							<th>操作</th>
						</tr>

					</table>

				</div>

				<!-- 用于无数据的展示 -->
				<div id="data_none_tip">
					该类别下暂无数据
				</div>
				
			</div>

		</div>

	</div>
	<!-- container 结束 -->

	<div class="modal-container" id="modalContainer">
		<div id="data_group_modal" class="modal">
			
			<!-- modal title -->
			<div class="modal-title">
				<div id="modal_title">添加数据源类别名称</div>
				<span class="cancel">&times;</span>
			</div>

			<!-- modal content -->
			<div class="modal-content">
			
				<!-- 添加 -->
				<form id="data_group_add_form">
					<div class="input-item">
						<input type="hidden" name="id" id="id"/>
						<input name="name" id="name" placeholder="请输入数据源类别名称" oninput="onInput(this)" />
					</div>
					<span class="tip"></span>
				</form>

				<!-- 编辑 -->
				<form id="data_group_edit_form">
					<div class="input-item">
						<input type="hidden" name="id" id="id"/>
						<input name="name" id="name" placeholder="请输入数据源类别名称" oninput="onInput(this)" />
					</div>
					<span class="tip"></span>
				</form>

				<!-- 删除分组 -->
				<form id="data_group_del_form">
					<input type="hidden" name="id" id="id"/>
					<div id="data_group_del">
					</div>
				</form>

				<!-- 移动数据源 -->
				<form id="move_datasource_form">
					<div class="input-item">
						<input type="hidden" name="id" id="id"/>
						<select name="groupId" id="group_select">
						</select>
					</div>
					<span class="tip"></span>
				</form>

				<!-- 删除数据源 -->
				<form id="del_datasource_form">
					<input type="hidden" name="id" id="id"/>
					<div id="data_source_del">
					</div>
				</form>
				
			</div>
			
			<!-- modal foot -->
			<div class="modal-foot">
				<button class="primary" onclick="modalClick()">保存</button>
				<button class="cancel">取消</button>
			</div>

		</div>
	</div>
</body>
</html>