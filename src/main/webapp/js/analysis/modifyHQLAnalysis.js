$(function(){
	var tableDesc;
	$(".typeSql").css("display","none");
	$(".chooseData").change(function(){
		var method = "query_table_field";
		var tableName = $(".chooseTable:selected").text();
		$(".chooseAlgorithm").empty();
		$(".typeSql").css("display","none");
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
				
				$(".fieldName").empty();
				$(".fieldName").append("<option></option>");
				for(index in data){
					$(".fieldName").append("<option value='" + data[index].split("\t")[0] + "'>" + data[index].split("\t")[0] + "</option>");
				}
			}
			
		})
		
	})
	$(".fieldName").change(function(){
		var fieldName = $(".fieldName").val();
		
		$(".typeSql").css("display","none");
		if(fieldName == null || fieldName == ""){
			$(".chooseAlgorithm").empty();
		}else{
			for(index in tableDesc){
				var fieldType;
				if(tableDesc[index].indexOf(fieldName) != -1){
					fieldType = tableDesc[index].split("\t")[1];
				}
				if(fieldType == "string"){
					$(".chooseAlgorithm").empty();
					$(".chooseAlgorithm").append("<option></option>");
					$(".chooseAlgorithm").append("<option value='5'>计数</option><option value='6'>自定义SQL</option>");
					$(".chooseAlgorithm").change(function(){
						$(".typeSql").css("display","none");
						if($(".chooseAlgorithm").val() == 6){
							$(".typeSql").css("display","");
						}
					})
					break;
				}else if(fieldType == "int" || fieldType == "bigint"){
					$(".chooseAlgorithm").empty();
					$(".chooseAlgorithm").append("<option value='1'>最大值</option><option value='2'>最小值</option><option value='3'>平均值</option><option value='4'>求和</option><option value='5'>计数</option><option value='6'>自定义SQL</option>");
					$(".chooseAlgorithm").change(function(){
						$(".typeSql").css("display","none");
						if($(".chooseAlgorithm").val() == 6){
							$(".typeSql").css("display","");
						}
					})
					 break;
				}
			}
		}
	})
	
	$(".submit").on("click",function(){
		var method = "update_hql_flow";
		var flowId = $(".flowId").val();
		var name = $(".process_name").val();
		var hive_sql;
		var mr_id = $(".chooseAlgorithm").val();
		var fieldName = $(".fieldName").val();
		var tableName = $(".chooseTable:selected").text();
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
			hive_sql = $("#hive_sql").val();
		}
		var result_table = $(".result_table").val();
		$.ajax({
			url : 'AnalysisServlet',
			type : 'post',
			data : {
				method : method,
				flowId : flowId,
				name : name,
				hive_sql : hive_sql,
				result_table : result_table,
			},
			success : function(data){
				console.log(data);
				if(data == "success"){
					alert('修改HQL流程成功！');
					window.location.href='flowManage/flowManage.html';
				}
				
			}
		})
	})
})