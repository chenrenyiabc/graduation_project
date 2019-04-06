$(function() {
	$.ajax({
		url: "../../getResultTables",
		type: "post",
		dataType: "json",
		//加载所有的结果表
		success: function(data) {
			var options = "";
			for(index in data[0]["table"]) {
				options += ("<option class='resultFile' method='table' value='" + data[0]["table"][index] + "'>" + data[0]["table"][index] + "</option>");
			}
			for(index in data[0]["path"]) {
				options += ("<option class='resultFile' method='path' value='" + data[0]["path"][index] + "'>" + data[0]["path"][index] + "</option>");
			}
			$("#resultTable").html("");
			$("#resultTable").append(options);
		}
	})

	$("#submit").click(function() {
		var showResult = self.parent.frames["showResult"];
		showResult.window.location.reload();
	})
	
	$("#option").change(function(){
		var resultTable = $("#resultTable option:selected").attr("method");
		var optionType = $(this).val(); 
		var resTable = $("#resultTable").val();
		var appends = "";
		if(optionType == "optionPie"){
			appends = "";
			$("#column").html("");
			$("#column").append(appends);
		}
		else if(optionType == "optionBar"){
			appends = "<tr><td>请选择X轴</td><td><select id='Xcolumn'>";
			$.ajax({
				type: "get",
				data: {
					tableName: resTable
				},
				url: "../../GetResultTableColumns",
				async: true,
				dataType: 'json',
				success: function(data) {
					var temp = "";
					for(index in data) {
						temp += ("<option value='" + data[index].split("\t")[0] + "'>" + data[index].split("\t")[0] + "</option>");
					}
					appends += temp + "</select></td></tr>"
					$("#column").html("");
					$("#column").append(appends);
				}
			});
		}
		else if(optionType == "optionLine"){
			if(resultTable != "table"){
				$("#column").html("只有表结构支持折线图");
				$("#column").append(appends);
			}
			else{
				appends = "<tr><td>X轴</td><td><select id='Xcolumn'>";
				$.ajax({
					type: "get",
					data: {
						tableName: resTable
					},
					url: "../../GetResultTableColumns",
					async: true,
					dataType: 'json',
					success: function(data) {
						var temp = "";
						for(index in data) {
							temp += ("<option value='" + data[index].split("\t")[0] + "'>" + data[index].split("\t")[0] + "</option>");
						}
						appends += temp + "</select></td></tr><tr><td>Y轴</td><td><select id='Ycolumn'>"
						appends += temp + "</select></td></tr>"
						$("#column").html("");
						$("#column").append(appends);
					}
				});
			}
		}
	})
	
	
	$("document").on("change", "#option", function() {
		var resTable = $(this).val();
		$.ajax({
			type: "get",
			data: {
				tableName: resTable
			},
			url: "GetResultTableColumns",
			async: true,
			dataType: 'json',
			success: function(data) {
				var options = "";
				for(index in data) {
					options.append("<option value='" + datap[index] + "'>" + data[index] + "</option>");
				}
				$("#Xcolumn").html("");
				$("#Xcolumn").append(options);
				$("#Ycolumn").html("");
				$("#Ycolumn").append(options);
			}
		});
	})
})