$(function(){
	//页面一开始就进行加载Hive
	$.ajax({ 
		url:"HiveServlet",
		type:"post",
		dataType:"json",
		data:{
			"method":"showtable",
		},
		success:function(data){
			$("#hiveTableshowXiala").empty();
			for(var index in data){
				$("#hiveTableshowXiala").append("<option value='" + data[index] + "'>" + data[index] + "</option>");
			}
			$("#hiveTableshowXiala2").empty();
			for(var index in data){
				$("#hiveTableshowXiala2").append("<option value='" + data[index] + "'>" + data[index] + "</option>");
			}
		}
	})
	
	//用户选择表后自动显示表中的数据
	$("body").on("change","#hiveTableshowXiala",function(){
		$.ajax({ 
			url:"HiveServlet",
			type:"post",
			dataType:"json",
			data:{
				"method":"showdata",
				"tableName": $(this).val(),
			},
			success:function(data){
				$("#hiveDatashow").empty();
				for(var index in data){
					$("#hiveDatashow").append("<tr></tr>");
					
					for(var index1 in data[index].split("\t")){
						if(index==0){
							if(index1 %2 ==0){
								$("#hiveDatashow tr:last").append("<td>"+ data[index].split("\t")[index1] +"</td>")
							}
						}else{
								$("#hiveDatashow tr:last").append("<td>"+ data[index].split("\t")[index1] +"</td>")
							}
					}
				}
			}
		})
	})
	
	//用户上传文件时点击添加列信息
	$("#addColumnButton").click(function(){
		//判断用户选择自动拂去列还是选择自定义列
		if($("input[name='addcolumn']:checked").val() == 'autoColumn'){
			var coltypes = $(this).before("<input type='text' name='coltypes' placeholder='请输入列类型'/><br/>");
		}
		if($("input[name='addcolumn']:checked").val() == 'selfColumn'){
			var colnames = $(this).before("<input type='text' name='colnames' placeholder='请输入列名'/>");
			var coltypes = $(this).before("<input type='text' name='coltypes' placeholder='请输入列类型'/><br/>");
		}
	})
	
	//数据库表自动展示
	$.ajax({
		url:"HDFSMysqlServlet",
		type:"post",
		dataType:"json",
		data:{
			"method":"showMysqlTableInput",
		},
		success:function(data){
			$("#mysqlTableLoadSelect").empty();
			for(var index in data){
				$("#mysqlTableLoadSelect").append("<option value='"+ data[index] +"'>" + data[index] + "</option>");
			}
		}
	})
	
	//数据库表数据自动显示
	$("body").on("change","#mysqlTableLoadSelect",function(){
		$.ajax({
			url:"HDFSMysqlServlet",
			type:"post",
			dataType:"json",
			data:{
				"method":"showMysqldata",
				"type":"mysql",
				"tableName":$(this).val()
			},
			success:function(data){
				$("ul[id='tabledata-example']").empty(); //首先进行清空
				for(var index in data){
					$("ul[id='tabledata-example']").append("<li>" + data[index] + "</li>");
				}
			}
		})
	})
	
	//数据库表导入选择框自动显示
	$("#myModal2").on("change","#mysqlTableLoadSelect",function(){
		$.ajax({
			url:"HDFSMysqlServlet",
			type:"post",
			dataType:"json",
			data:{
				"method":"showMysqlColumnInput",
				"tableName":$(this).val()
			},
			success:function(data){
				$("#mysqlColumnLoadSelect").empty(); //首先进行清空
				for(var index in data){
					$("#mysqlColumnLoadSelect").append("<input type='checkbox' name='column' value='" + data[index] + "' />" + data[index] );
				}
			}
		})
	})
	
	//提交用户根据msql表选择的字段导入到hive中
	$("#myModal2").on("click","#uploadButton",function(){
		var checked = []; //由于是复选框，需要定义数组
		
		$("div[id='mysqlColumnLoadSelect'] input[name='column']:checked").each(function(){ //由于复选框一般选中的是多个,所以可以循环输出
			checked.push($(this).val());
			});

		$.ajax({
			url:"HiveServlet",
			type:"post",
			dataType:"json",
			data:{
				"method":"inputMysql2HdfsSingle",
				"tableName":$("select[id='mysqlTableLoadSelect'] ").val(),
				"targetHiveTable":$("#msqlInputHivetable ").val(),
				"columns":checked
			},
			traditional: true,//传递数组时使用
			success:function(data){
				$("#myModal2").modal('hide');
			}
		})
	})
	
	//提交用户自动的sqool语句把mysql数据导到hive中
	$("#mysqlselfLoadButton").click(function(){
		$.ajax({
			url:"HiveServlet",
			type:"post",
			dataType:"json",
			data:{
				"method":"inputMysql2HiveSelf",
				"selfCmd":$("#mysqlselfLoad").val(),
			},
			success:function(data){
				
			}
		})
	})
	
	//当用户选择添加列时，弹出列下拉框和简单统计的多选框
	$("#addcolumnButton").click(function(){
		$.ajax({
			url:"HiveServlet",
			type:"post",
			dataType:"json",
			data:{
				"method":"showHiveColumns",
				"HiveTablename":$("#hiveTableshowXiala2").val(),
			},
			success:function(data){
				var myDate = new Date();
				var h=myDate.getHours();       
				var m=myDate.getMinutes();     
				var s=myDate.getSeconds();
				var clazz = "xialaColumnsclass" + h+m+s;
				var xiala = "<select name='xialaColumns' class='" + clazz +"'></select> "
				$("#xialaHiveColumnsDuoxuan").append(xiala);	
				for(var index in data){
					$("." + clazz).append("<option value='"+ data[index] +"'>" + data[index] + "</option>");
				}
				//定义一个多选框
				var duoxuan = "<input type='checkbox' name='simpleTongji' value='最值'/>最值  <input type='checkbox' name='simpleTongji' value='求和'/>求和 <input type='checkbox' name='simpleTongji' value='均值'/>均值  <input type='checkbox' name='simpleTongji' value='计数'/>计数 <br/>" ;
				$("." + clazz).after(duoxuan);
			}
		})
	})
})