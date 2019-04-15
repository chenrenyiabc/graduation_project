$(function(){
	var source_id;
	var tableDesc;
	var tableName;
	var fieldName;
	var algorithmval;
	$(".chooseTable").on("click",function(){
		tableName = $(this).text();
		$("#show-first-data").html("");
		$("#show-first-data").append(tableName);
		$("#show-first-algorithm").html("");
		$(".type_hql").css("display","none");
	//	source_id = $(this).parent().val();
		var method = "query_table_field";
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
				$("#show-first-field").html("");
				$("#show-first-field").append(data[0].split("\t")[0]);
				$("#dropdown-menu-2").html("");
				for(index in data){
					$("#dropdown-menu-2").append("<li value='"+(parseInt(index) + 1)+"'><a class='chooseField' href='javascript:void(0)'>" + data[index].split("\t")[0] + "</a></li>");
				}
			}
		})
	})
	
	$("#dropdown-menu-2").on("click",".chooseField",function(){
		fieldName = $(this).text();
		$("#show-first-field").html("");
		$("#show-first-field").append(fieldName);
		$("#show-first-algorithm").html("");
		$(".type_hql").css("display","none");
		for(index in tableDesc){
			var fieldType;
			if(tableDesc[index].indexOf(fieldName) != -1){
				fieldType = tableDesc[index].split("\t")[1];
			}
			if(fieldType == "string"){
				$("#dropdown-menu-3-int").css("display","none");
				$("#dropdown-menu-3-string").css("display","");
			}else if(fieldType == "int" || fieldType == "bigint"){
				$("#dropdown-menu-3-string").css("display","none");
				$("#dropdown-menu-3-int").css("display","");
			}
		}
	})
	
	$(".dropdown-menu-3").on("click",".chooseAlgorithm",function(){
		var algorithm = $(this).text();
		$("#show-first-algorithm").html("");
		$("#show-first-algorithm").append(algorithm);
		algorithmval = $(this).parent().val();
		if(algorithmval == 6){
			$(".type_hql").css("display","");
		}else{
			$(".type_hql").css("display","none");
		}
	})
	
	$("#saveflow").on("click",function(){
		var method = "save_flow_hql";
		var name = $(".process_name").val();
		var flow_status = 0;
		var flow_type = 1;
		var hive_sql;
		
		var result_table = $(".result_table").val();
		var result_path = $(".resultPath").val();
		
		if(algorithmval == 1){
			hive_sql = "select max("+ fieldName +") from " + tableName;
		}else if(algorithmval == 2){
			hive_sql = "select min("+ fieldName +") from " + tableName;
		}else if(algorithmval == 3){
			hive_sql = "select avg("+ fieldName +") from " + tableName;
		}else if(algorithmval == 4){
			hive_sql = "select sum("+ fieldName +") from " + tableName;
		}else if(algorithmval == 5){
			hive_sql = "select count("+ fieldName +") from " + tableName;
		}else if(algorithmval == 6){
			hive_sql = $(".hive_sql").val();
		}
		
		$.ajax({
			url : 'AnalysisServlet',
			type : 'post',
			data : {
				method : method,
				name : name,
				flow_status : flow_status,
				flow_type : flow_type,
				hive_sql : hive_sql,
				result_table : result_table
			},
			success : function(data){
				console.log(data);
				if(data == "success"){
					alert('保存流程成功！');
					window.location.href='main/flowManage/flowManage.html';
				}
			},
			
		})
		
	})
	
	
})