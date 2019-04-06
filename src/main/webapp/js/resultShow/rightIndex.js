$(function() {
	var doOptionStype = self.parent.frames["optionStype"];

	var myEchart = echarts.init(document.getElementById("show"));
	

	//折线图的数据
	var Xdata = [];
	var Ydata = [];
	
	//柱形图的数据
	var xAxisData = [];
	var data1 = [];
	
	//饼状图的数据
	var seriesData = [];

	// 可以再添加数据,根据返回的数据维度而定

	// 显示的样式
	var optionPie = {
		title: {
			text: 'Customized line',
			left: 'center',
			top: 20,
			textStyle: {
				color: '#ccc'
			}
		},
		tooltip: {
			trigger: 'item',
			formatter: "{a} <br/>{b}: {c} ({d}%)"
		},
		legend: {
			orient: 'vertical',
			x: 'left'
		},
		series: [{
			name: '访问来源',
			type: 'pie',
			radius: '50%',
			avoidLabelOverlap: false,
			labelLine: {
				normal: {
					show: true
				}
			},
			data: seriesData
		}]
	};
	
	

	var optionBar = {
			title: {
		        text: '柱状图动画延迟'
		    },
		    toolbox: {
		        // y: 'bottom',
		        feature: {
		            magicType: {
		                type: ['stack', 'tiled']
		            },
		            dataView: {},
		            saveAsImage: {
		                pixelRatio: 2
		            }
		        }
		    },
		    tooltip: {},
		    xAxis: {
		        data: xAxisData,
		        silent: false,
		        axisLabel:{
		            rotate:-45
		        },
		        splitLine: {
		            show: false
		        }
		    },
		    yAxis: {
		    },
		    series: [{
		        name: 'bar',
		        type: 'bar',
		        data: data1,
		        animationDelay: function (idx) {
		            return idx * 10;
		        }
		    }],
		    animationEasing: 'elasticOut',
		    animationDelayUpdate: function (idx) {
		        return idx * 5;
		    }
	};
	
	
	var optionLine = {
		title: {
			text: 'Customized line',
			left: 'center',
			top: 20,
			textStyle: {
				color: '#ccc'
			}
		},
		xAxis: {
			type: 'category',
			axisLabel:{
	            rotate:-45
	        }
		},
		yAxis: {
			type: 'value'
		},
		series: [{
			data: Ydata,
			type: 'line'
		}]
	};
	// 待添加的样式.....

	
	
	
	
	
	var title = doOptionStype.document.getElementById("title").value;
	var type = doOptionStype.document.getElementById("option").value;
	var name = doOptionStype.document.getElementById("resultTable").value;

	
	var options = doOptionStype.document.getElementsByClassName("resultFile")
	var method = "";
	for(var i=0;i<options.length;i++){
		if(name == options[i].value){
			method = options[i].getAttribute("method");
			break;
		}
	}
	
	var index = options.selectedIndex;
	//对折线图的ajax请求
	if(type == "optionLine") {
		var x = doOptionStype.document.getElementById("Xcolumn").value;
		var y = doOptionStype.document.getElementById("Ycolumn").value;
		$.ajax({
			url:"../../GetResultServlet",
			data:{
				method:method,
				name:name
			},
			type:"get",
			dataType:"json",
			success:function(data){
				Xdata = data[x];
				optionLine.xAxis.data = Xdata;
				console.log(Xdata);
				for(index in data[y]){
					Ydata[index] = parseInt(data[y][index]);
				}
				optionLine.title.text = title;
				myEchart.setOption(optionLine);	
			}
		});
	} 
	//对柱形图
	else if(type == "optionBar"){
		$.ajax({
			url : '../../GetResultServlet',
			type : 'get',
			dataType : 'json',
			data:{
				method:method,
				name:name
			},
			success : function(data) {
				var x = doOptionStype.document.getElementById("Xcolumn").value;
				//X轴数据
				xAxisData = data[x];
				data[x] = [];
				
				var columns = [];
				var Data = [];
				//获得到data的所有列名
				for(index in data){
					columns.push(index);
				}
				
				console.log(columns);
				//将其存放到到Data中
				for(var i = 0;i<columns.length;i++){
					Data[i] = data[columns[i]];
				}
				console.log(Data);
				//Y轴数据
				for(var i= 0;i<Data.length;i++){
					for(var j = 0;j<Data[i].length;j++){
						Data[i][j] = parseFloat(Data[i][j]);
					}
				}
				console.log(Data);
				//将Y轴的数据添加到seriesData中
				for(var i = 0;i<Data.length;i++){
					seriesData.push({
						data:Data[i],
						type:"bar",
						animationDelay: function (idx) {
				            return idx * 10;
				        }
					})
				}
				console.log(seriesData);
				//配置配置文件
				optionBar.series = seriesData;
				optionBar.xAxis.data = xAxisData;
				optionBar.title.text = title;
				myEchart.setOption(optionBar);
			}
		})
	}
	//对普通饼状图的ajax请求
	else {
		$.ajax({
			url:"../../GetResultServlet",
			data:{
				method:method,
				name:name
			},
			type:"get",
			dataType:"json",
			success:function(data){
				var keys = [];
				var values = [];
				var columns = [];
				for(index in data){
					columns.push(index);
				}
				keys = data[columns[0]];
				values = data[columns[1]];
				for(index in keys){
					seriesData.push({
						value:values[index],
						name:keys[index]
					})
				}
				optionPie.legend.data = keys;
				optionPie.title.text = title;
				myEchart.setOption(optionPie);
			}
		});
	}
	
})