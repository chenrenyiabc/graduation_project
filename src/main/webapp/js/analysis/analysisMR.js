$(function(){
	var source_id = $("#show-first-data").attr("value");
	var mr_id = $("#show-first-algorithm").attr("value");
	function abc(){
		$(".hql").removeClass("active");
		$(this).addClass("active");
		$("#showmsg").css("display","");
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
				console.log(algorithm);
				// 数据源填充
				$("#show-first-data").append(dataSource[0]);
				for(index in dataSource){
					$("#dropdown-menu-1").append("<li value='"+(parseInt(index) + 1)+"'><a class='chooseTable' href='javascript:void(0)'>" + dataSource[index] + "</a></li>");
					if(index != dataSource.length - 1){
						$("#dropdown-menu-1").append("<li role='separator' class='divider'></li>");
					}
				}
				// 算法填充
				$("#show-first-algorithm").append(algorithm[0]);
				for(index in algorithm){
					$("#dropdown-menu-2").append("<li value='"+(parseInt(index) + 1)+"'><a class='chooseAlgorithm' href='javascript:void(0)'>" + algorithm[index] + "</a></li>");
					if(index != dataSource.length - 1){
						$("#dropdown-menu-2").append("<li role='separator' class='divider'></li>");
					}
				}
			}
		})
	}
	$("#mapreduce").click(abc())
	
	$("#dropdown-menu-1").on("click",".chooseTable",function(){
		$("#show-first-data").html("");
		var source_name = $(this).text();
		$("#show-first-data").append(source_name);
		
		var method = "query_source_id";
		$.ajax({
			url : 'AnalysisServlet',
			type : 'post',
			data : {
				method : method,
				source_name : source_name
			},
			success : function(data){
				source_id = data;
				
			}
		})
		
		
	})
	$("#dropdown-menu-2").on("click",".chooseAlgorithm",function(){
		$("#show-first-algorithm").html("");
		$("#show-first-algorithm").append($(this).text());
		mr_id = $(this).parent().val();
	})
	
	$("#saveflow").on("click",function(){
		var method = "save_flow";
		var name = $(".process_name").val();
		var flow_status = 0;
		var flow_type = 0;
		var hive_sql;
		var fieldName = $(".fieldName").val();
		var tableName = $(".chooseTable:selected").text();
		
		var result_table = $(".result_table").val();
		var result_path = $(".resultPath").val();
		
		
		$.ajax({
			url : 'AnalysisServlet',
			type : 'post',
			data : {
				method : method,
				name : name,
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
					window.location.href='main/flowManage/flowManage.html';
				}
				
			}
		})
	})
	
})