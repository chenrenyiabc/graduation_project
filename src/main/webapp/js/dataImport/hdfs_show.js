$(function(){
	var frame_msg = self.parent.frames["frame_showmsg"]; 
	$("input[id='hdfsManage']").click(function(){
		//首先清除页面的全部信息再进行加载
		frame_msg.$("#uploadFile-container").css({"display":"block"});
	})
	
	var hdfsHomeDif = "/";
	//获取用户家目录列表
	$.ajax({
		url:"HDFSFileServlet",
		type:"post",
		dataType:"json",
		data:{
			"method":"showDir",
			"dir":"indexpage"
		},
		success:function(data){
			//首先获取到key的值
			var keys = "";
			for(var key in data[0]){
				keys = key;
			}
			//遍历找出所有目录
			$("ul[id='showDirUl']").empty(); //首先进行清空
			$("#homePath").text(hdfsHomeDif); //显示家目录
			$("#currenPath").text(keys);     //显示当前目录
			for(var index in data[0][keys]){
				$("ul[id='showDirUl']").append("<li class='dir list-group-item' dizhi='" + keys  + data[0][keys][index].fileName + "'>" + "<span>" + data[0][keys][index].dirFile + "</span>" + " " +"<span>" + data[0][keys][index].filesize + "</span>" + " " +"<span>" + data[0][keys][index].owner + "</span>"+ " " +"<span>" + data[0][keys][index].ownerGroup + "</span>"+ " " +"<span>" + data[0][keys][index].fileName + "</span>" +"</li>");
			}
		}
	})
	
	//目录再次点击
	$("ul[id='showDirUl']").on("click","li[class='dir list-group-item']",function(){
		$.ajax({
			url:"HDFSFileServlet",
			type:"post",
			dataType:"json",
			data:{
				"method":"showDir",
				"dir":$(this).attr("dizhi")
			},
			success:function(data){
				//首先获取到key的值
				var keys = "";
				for(var key in data[0]){
					keys = key;
				}
				
				//获取上上级目录(可以在目录返回)
				var str="";
				var arr=new Array();
				arr=keys.split('/');
				for(var i=0;i<arr.length-1;i++){
					str += arr[i] + "/";
				}
				str = str.substring(0,str.length-1);
				//遍历找出所有目录
				$("#currenPath").text(keys);     //显示当前目录
				$("ul[id='showDirUl']").empty(); //首先进行清空
				$("ul[id='showDirUl']").append("<li class='dir list-group-item' dizhi='" + str + "'>" + "<span>" + ".." + "</span>" );
				for(var index in data[0][keys]){
					if(data[0][keys][index].dirFile == "目录" ){
						$("ul[id='showDirUl']").append("<li class='dir list-group-item' dizhi='" + keys + "/" + data[0][keys][index].fileName + "'>" + "<span>" + data[0][keys][index].dirFile + "</span>" + " " +"<span>" + data[0][keys][index].filesize + "</span>" + " " +"<span>" + data[0][keys][index].owner + "</span>"+ " " +"<span>" + data[0][keys][index].ownerGroup + "</span>"+ " " +"<span>" + data[0][keys][index].fileName + "</span>" +"</li>");
					} else{
						$("ul[id='showDirUl']").append("<li class='list-group-item' dizhi='" + keys + "/" + data[0][keys][index].fileName + "'>" + "<span>" + data[0][keys][index].dirFile + "</span>" + " " +"<span>" + data[0][keys][index].filesize + "</span>" + " " +"<span>" + data[0][keys][index].owner + "</span>"+ " " +"<span>" + data[0][keys][index].ownerGroup + "</span>"+ " " +"<span>" + data[0][keys][index].fileName + "</span>" +"</li>");
					}
				}
			}
		})
	})
	
	//自动加载mysql数据库数据
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
	
//	//当用户选择了Mysql表后自动展示数据
//	$("body").on("change","#mysqlTableLoadSelect",function(){
//		$.ajax({
//			url:"HDFSMysqlServlet",
//			type:"post",
//			dataType:"json",
//			data:{
//				"method":"showMysqldata",
//				"type":"mysql",
//				"tableName":$(this).val()
//			},
//			success:function(data){
//				$("ul[id='tabledata-example']").empty(); //首先进行清空
//				for(var index in data){
//					$("ul[id='tabledata-example']").append("<li class='list-group-item'>" + data[index] + "</li>");
//				}
//			}
//		})
//	})
	
		//当用户选择了Mysql表后自动展示数据
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
				$("table[id='mysqltablesShow']").empty(); //首先进行清空
								
				for(var index in data){
					$("#mysqltablesShow").append("<tr></tr>");
					for(var index1 in data[index].split(",")[0].split(" "))
						$("#mysqltablesShow tr:last").append("<td>"+ data[index].split(",")[0].split(" ")[index1] +"</td>")
				}
			}
		})
	})
	
	
	//用户点开单表导入选项卡时自动加载mysql数据库数据
	$('#myModal4').on('shown.bs.modal',function(){
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
					$("div[class= 'modal-body'] #mysqlTableLoadSelectmotai").append("<option value='"+ data[index] +"'>" + data[index] + "</option>");
				}       
			}
		})
	})

	$("#myModal4").on("change","#mysqlTableLoadSelectmotai",function(){
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
					$("#mysqlColumnLoadSelect").append( "<input type='checkbox' name='column' value='" + data[index] + "' />" + data[index] );
				}
			}
		})
	})

	//用户单表导入提交操作
	$("#myModal4").on("click","#subButton",function(){
		var checked = []; //由于是复选框，需要定义数组
		
		$("div[id='mysqlColumnLoadSelect'] input[name='column']:checked").each(function(){ //由于复选框一般选中的是多个,所以可以循环输出
			checked.push($(this).val());
			});

		$.ajax({
			url:"HDFSMysqlServlet",
			type:"post",
			dataType:"json",
			data:{
				"method":"inputMysql2HdfsSingle",
				"tableName":$("select[id='mysqlTableLoadSelectmotai'] ").val(),
				"targetDir":$("#msqlInputHdtfdanDir").val(),
				"columns":checked
			},
			traditional: true,//传递数组时使用
			success:function(data){
				$("#myModal4").modal('hide');
			}
		})
	})
//	
//	$("#mysqlselfLoadButton").click(function(){
//		$.ajax({
//			url:"HDFSMysqlServlet",
//			type:"post",
//			dataType:"json",
//			data:{
//				"method":"inputMysql2HdfsSelf",
//				"selfCmd":$("#mysqlselfLoad").val(),
//			},
//			success:function(data){
//				
//			}
//		})
//	})
//	
//	//页面自动加载可以进行Mr程序的数据文件
//	$.ajax({
//		url:"HDFSFileServlet",
//		type:"post",
//		dataType:"json",
//		data:{
//			"method":"getMrsourceFileAlgorithm",
//			},
//		success:function(data){
//			for(var index in data){
//				$("select[name='inputPathMr']").append("<option value='" + data[index] + "'>" + data[index] + "</option>")
//			}
//		}
//	})
})

