$(function(){
	var tableDesc;
	// 选中MapReduce时
	$(".mapreduce").click(function(){
		$(".hql").removeClass("active");
		$(this).addClass("active");
		
		var method = "query_mr_flow";
		$.ajax({
			url : 'AnalysisServlet',
			type : 'post',
			dataType : 'json',
			data : {
				method : method
			},
			success : function(data){
				
				var dataSource = data[0].dataSource;
				var algorithm = data[0].algorithm;
				$(".showmsg").html("");
				$(".showmsg").append("请输入流程名：");
				$(".showmsg").append("<input type='text' class='process_name' name='process_name' /><br /><br />");
				$(".showmsg").append("请选择数据源：");
				var select1 = $("<select class='chooseData'></select>");
				for(index in dataSource){
					select1.append("<option value='" + (parseInt(index) + 1) + "'>" + dataSource[index] + "</option>");
				}
				$(".showmsg").append(select1);
				
				
				
				$(".showmsg").append("<br /><br />请选择算法：");
				var select2 = $("<select class='chooseAlgorithm'></select>");
				for(index in algorithm){
					select2.append("<option value='" + (parseInt(index) + 1) + "'>" + algorithm[index] + "</option>");
				}
				$(".showmsg").append(select2);
				$(".showmsg").append("<br /><br />请输入结果保存路径：");
				$(".showmsg").append("<input type='text' class='resultPath' name='resultPath' />");
				$(".showmsg").append("<br /><br /><button class='saveflow' name='saveflow' value='保存计算流程'>保存计算流程</button>");
				$(".showmsg").append("<input type='hidden' class='flow_type' name='flow_type' value='0' />");
			}
		})
	})
	// 点击hql时
	$(".hql").click(function(){
		$(".mapreduce").removeClass("active");
		$(this).addClass("active");
		var method = "query_hql_flow";
		$.ajax({
			url : 'AnalysisServlet',
			type : 'post',
			dataType : 'json',
			data : {
				method : method
			},
			success : function(data){
				$(".showmsg").html("");
				$(".showmsg").append("请输入流程名：");
				$(".showmsg").append("<input type='text' class='process_name' name='process_name' /><br /><br />");
				$(".showmsg").append("选择数据源：");
				var select = $("<select class='chooseData'></select>");
				select.append("<option class='chooseTable'></option>");
				for(index in data){
					select.append("<option class='chooseTable' value='" + (parseInt(index) + 1) + "'>" + data[index] + "</option>");
				}
				$(".showmsg").append(select);
				$(".chooseData").after("<nobr class='dataDiv'></nobr>");
				$(".dataDiv").after("<nobr class='algorithmDiv'></nobr>");
				
				$(".showmsg").append("<br /><br />请输入结果表名称：");
				$(".showmsg").append("<input type='text' class='result_table' name='result_table' />");
				$(".showmsg").append("<br /><br /><button class='saveflow' name='saveflow' value='保存计算流程'>保存计算流程</button>");
				$(".showmsg").append("<input type='hidden' class='flow_type' name='flow_type' value='1' />");
				}
		})
		
	})
	// 改变下拉列表的表名时
	$(".showmsg").on("change",".chooseData",function(){
		var method = "query_table_field";
		var tableName = $(".chooseTable:selected").text();
		if(tableName == null || tableName == ""){
			$(".dataDiv").empty();
		}else{
			$.ajax({
				url : 'AnalysisServlet',
				type : 'post',
				dataType : 'json',
				data : {
					method : method,
					tableName : tableName
				},
				success : function(data){
					tableDesc = data;
					
					$(".dataDiv").empty();
					$(".algorithmDiv").empty();
					$(".dataDiv").append("<br /><br />请选择要操作的列：");
					var select = $("<select class='fieldName'></select>");
					select.append("<option></option>");
					for(index in data){
						select.append("<option value='" + data[index].split("\t")[0] + "'>" + data[index].split("\t")[0] + "</option>");
					}

					$(".dataDiv").append(select);
				}
				
			})
		}
		
		
	})
	// 选择要进行分析的列，通过判断，如果是int类型则给出6种方法，如果是string类型只给出2种方法
	$(".showmsg").on("change",".fieldName",function(){
	//	$(".algorithmDiv").empty();
		var fieldName = $(".fieldName").val();
		if(fieldName == null || fieldName == ""){
			$(".algorithmDiv").empty();
		}else{
			for(index in tableDesc){
				var fieldType;
				if(tableDesc[index].indexOf(fieldName) != -1){
					fieldType = tableDesc[index].split("\t")[1];
				}
				if(fieldType == "string"){
					$(".algorithmDiv").html("");
					$(".algorithmDiv").append("<br /><br />选择算法：");
					
					var selectAl = $("<select class='chooseAlgorithm'></select>")
					selectAl.append("<option value='5'>计数</option><option value='6'>自定义SQL</option>");
					$(".algorithmDiv").append(selectAl);
					$(".algorithmDiv").append("<span class='typeSql'></span>");
					$(".chooseAlgorithm").change(function(){
						$(".typeSql").html("");
						if($(".chooseAlgorithm").val() == 6){
							$(".typeSql").append("&nbsp;&nbsp;&nbsp;&nbsp;请输入自定义SQL：");
							$(".typeSql").append("<input type='text' class='hive_sql' name='input_sql' />");
						}
					})
					break;
				}else if(fieldType == "int" || fieldType == "bigint"){
					$(".algorithmDiv").html("");
					$(".algorithmDiv").append("<br /><br />选择算法：");
					var selectAl = $("<select class='chooseAlgorithm'></select>")
					selectAl.append("<option value='1'>最大值</option><option value='2'>最小值</option><option value='3'>平均值</option><option value='4'>求和</option><option value='5'>计数</option><option value='6'>自定义SQL</option>");
					$(".algorithmDiv").append(selectAl);
					$(".algorithmDiv").append("<span class='typeSql'></span>");
					$(".chooseAlgorithm").change(function(){
						$(".typeSql").html("");
						if($(".chooseAlgorithm").val() == 6){
							$(".typeSql").append("&nbsp;&nbsp;&nbsp;&nbsp;请输入自定义HQL：");
							$(".typeSql").append("<input type='text' class='hive_sql' name='input_sql' />");
						}
					})
					 break;
				}
			}
		}
		
	})
	
	$(".showmsg").on("click",".saveflow",function(){
		var method = "save_flow";
		var name = $(".process_name").val();
		var userId = 1;
		var flow_status = 0;
		var source_id = $(".chooseData").val();
		var flow_type = $(".flow_type").val();
		var hive_sql;
		var mr_id = $(".chooseAlgorithm").val();
		var fieldName = $(".fieldName").val();
		var tableName = $(".chooseTable:selected").text();
		if(flow_type == 0){
			
		}else{
			if(mr_id == 1){
				hive_sql = "select max("+ fieldName +") from " + tableName;
			}else if(mr_id == 2){
				hive_sql = "select min("+ fieldName +") from " + tableName;
			}else if(mr_id == 3){
				hive_sql = "select avg("+ fieldName +") from " + tableName;
			}else if(mr_id == 4){
				hive_sql = "select sum("+ fieldName +") from " + tableName;
			}else if(mr_id == 5){
				hive_sql = "select count("+ fieldName +") from " + tableName;
			}else if(mr_id == 6){
				hive_sql = $(".hive_sql").val();
			}
		}
		
		console.log(hive_sql);
		var result_table = $(".result_table").val();
		var result_path = $(".resultPath").val();
		
		$.ajax({
			url : 'AnalysisServlet',
			type : 'post',
			data : {
				method : method,
				name : name,
				userId :userId,
				flow_status : flow_status,
				source_id : source_id,
				flow_type : flow_type,
				hive_sql : hive_sql,
				mr_id : mr_id,
				result_table : result_table,
				result_path : result_path
			},
			success : function(data){
				console.log(data);
				if(data == "success"){
					alert('保存流程成功！');
					window.location.href='flowManage/flowManage.html';
				}
				
			}
		})
	})
	
	
})