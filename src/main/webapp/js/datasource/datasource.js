var legend_datas = [];
var series_datas = [];

//记录当前选中的类别id
var currentGroupId = 0;
var currentGroupName = "";
var currentModalType = "";//当前显示modal的类型
var isChangeReport = false;//是否改变数据报表
// 用来判断是否有数据改变，优化图表渲染
var isModified = false;
//设置数据
setReportList(groups);

var option = {
    title : {
        text: '数据源统计',
        subtext: '统计每个类别下的数据源数量',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    toolbox:{
    	itemSize:20,
    	left:'30px',
    	show:true,
        feature : {
            myAddTool:{
            	show: true,
            	backgroundColor:"black",
            	name:"添加数据源类别",
                title: '添加数据源类别',
                icon: 'path://M511.829333 975.725714C242.517333 975.725714 24.210286 757.369905 24.210286 488.106667 24.210286 218.794667 242.517333 0.487619 511.829333 0.487619 781.141333 0.487619 999.448381 218.794667 999.448381 488.106667 999.448381 757.369905 781.141333 975.725714 511.829333 975.725714ZM509.098667 91.428571C290.304 91.428571 120.56381 268.824381 120.56381 487.619048 120.56381 706.413714 290.304 877.714286 509.098667 877.714286 727.942095 877.714286 905.337905 706.413714 905.337905 487.619048 905.337905 268.824381 727.942095 91.428571 509.098667 91.428571ZM707.267048 536.380952C707.267048 536.380952 560.932571 536.380952 560.932571 536.380952 560.932571 536.380952 560.932571 682.715429 560.932571 682.715429 560.932571 709.632 539.136 731.428571 512.219429 731.428571 512.219429 731.428571 512.121905 731.428571 512.121905 731.428571 485.205333 731.428571 463.408762 709.632 463.408762 682.715429 463.408762 682.715429 463.408762 536.380952 463.408762 536.380952 463.408762 536.380952 317.074286 536.380952 317.074286 536.380952 290.157714 536.380952 268.361143 514.584381 268.361143 487.66781 268.361143 487.66781 268.361143 487.570286 268.361143 487.570286 268.361143 460.653714 290.157714 438.857143 317.074286 438.857143 317.074286 438.857143 463.408762 438.857143 463.408762 438.857143 463.408762 438.857143 463.408762 292.522667 463.408762 292.522667 463.408762 265.606095 485.205333 243.809524 512.121905 243.809524 512.121905 243.809524 512.219429 243.809524 512.219429 243.809524 539.136 243.809524 560.932571 265.606095 560.932571 292.522667 560.932571 292.522667 560.932571 438.857143 560.932571 438.857143 560.932571 438.857143 707.267048 438.857143 707.267048 438.857143 734.183619 438.857143 755.98019 460.653714 755.98019 487.570286 755.98019 487.570286 755.98019 487.66781 755.98019 487.66781 755.98019 514.584381 734.183619 536.380952 707.267048 536.380952Z',
                onclick: function (){
                    addGroup();
                },
                iconStyle:{
                	color:'black'
                }
            }
        }
    },
    legend: {
        type: 'scroll',
        orient: 'vertical',
        right: 10,
        top: 20,
        bottom: 20,
        data: legend_datas,
        selected: false
    },
    series : [
        {
            name: '数据源类别',
            type: 'pie',
            radius : '55%',
            center: ['40%', '50%'],
            data: series_datas,
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
var myChart = null;
jQuery(function(){
	myChart = echarts.init(document.getElementById('group_report'));
	myChart.on('click', function (params) {
		console.log(datasources)
	    var id = params.data.id;
	    jQuery("#group_report").hide();
        showGroupList(groups);
	    jQuery("#list").show();
        currentGroupId = id;
	    jQuery("#group_list>a").removeClass("current");
	    jQuery("#group_list>a#" + id).addClass('current');

        //渲染数据源数据
        var name = params.data.name;
        var list = datasources[id];
        jQuery("#table_list>.tools-bar>.tip-title").text("当前数据源类别：" + name);
        if(list != undefined){
            var str = getDataSourcesTableStr(list);
            jQuery("#data_none_tip").hide();
            jQuery("#data_table").show();
            jQuery("#data_table").html(str);
        }else{
            jQuery("#data_none_tip").show();
            jQuery("#data_table").hide();
        }

	});
	myChart.setOption(option);

	//返回数据报表
	jQuery("#back_to_report").click(function(){
        if(isModified){
            option.legend.data = legend_datas;
            option.series[0].data = series_datas;
            myChart.setOption(option);
            isModified = false;
        }
		jQuery("#group_report").show();
	    jQuery("#list").hide();
	})

    //关闭modal
    jQuery(".modal .cancel").click(function(){
        jQuery("#modalContainer").hide();
    })
    
})

// 改变数据报表显示
function changeReport() {
    option.legend.data = legend_datas;
    option.series[0].data = series_datas;
    myChart.setOption(option);
}

// 点击类别显示数据源列表
function showDatasourceList(that){
    //改变a标签的选中状态
    jQuery(jQuery(that).siblings("a")).removeClass("current");
    jQuery(that).addClass("current");

    console.log(datasources);
    var id = parseInt(jQuery(that).attr("id"));

    currentGroupId = id;//记录当前选中的id
    //名称
    var name = jQuery(jQuery(that).children(".group_name")).text();
    //列表数据
    var list = datasources[id];

    jQuery("#table_list>.tools-bar>.tip-title").text("当前数据源类别：" + name);
    if(list != undefined){
        var str = getDataSourcesTableStr(list);
        jQuery("#data_none_tip").hide();
        jQuery("#data_table").show();
        jQuery("#data_table").html(str);
    }else{
        jQuery("#data_none_tip").show();
        jQuery("#data_table").hide();
    }
}

//获取datasource的列表字符串
function getDataSourcesTableStr(list){
    var str = '<tr>' +
                '<th>序号</th>' +
                '<th>名称</th>' +
                '<th>数据源类型</th>' +
                '<th>创建时间</th>' +
                '<th>操作</th>' +
            '</tr>';
    for(var i = 0;i < list.length;i++){
        var obj = list[i];
        var typeName = obj.type == 0 ? "HDFS" : "HIVE";
        str += '<tr>' +
                    '<td>' + (i + 1) + '</td>' +
                    '<td>' + obj.name + '</td>' +
                    '<td>' + typeName + '</td>' +
                    '<td>' + obj.createtime + '</td>' +
                    '<td>' +
                        '<span class="move" id="move_' + obj.id + '" onclick="openMoveModal(this)">' +
                            '<i class="icon-exchange"></i>' +
                            '<span>移动至</span>' +
                        '</span>' +
                        '<span class="show" id="show_' + obj.id + "_" + i + '" iscreate="0" onclick="showDatasourceInfo(this)">' +
                            '<i class="icon-eye-open"></i>' +
                            '<span>查看</span>' +
                        '</span>' +
                        '<span class="trash" id="trash_' + obj.id + '" onclick="openDelModal(this)">' +
                            '<i class="icon-trash"></i>' +
                        '<span>删除</span>' +
                        '</span>' +
                    '</td>' +
                '</tr>';
    }
    return str;
}

// 数据源点击查看查看相应的信息
function showDatasourceInfo(that){
    var iscreate = jQuery(that).attr("iscreate");
    if(iscreate == "0"){//说明没有创建，要显示
        //数据获取
        var index = parseInt(jQuery(that).attr("id").split("_")[2]);
        var id = parseInt(currentGroupId);//转成int类型
        var obj = datasources[id][index];//得到相应的对象
        var str = "";
        if(obj.type == 0){
            str = "<div title='点击隐藏' style='background: #efef;opacity: 1;z-index: 999;'>" +
                "<i class=' icon-angle-up'></i>" +
                "<div class='info-item'><span class='title-info'>上传时间</span><span class='value'>" + obj.createtime + "</span></div>" +
                "<div class='info-item'><span class='title-info'>存放路径</span><span class='value'>" + obj.hdfsPath + "</span></div>" +
                "</div>";
        }else{
            var fields = obj.tableDesc.split(",");//把每一个字段拆分出来
            var tablestr = "<table><tr class='alt'><th>字段名称</th><th>字段类型</th></tr>";
            for(var i = 0;i < fields.length;i++){
                var field = fields[i].split("_");
                tablestr += "<tr class='" + (i % 2 == 0 ? "" : "alt") + "'>" +
                    "<td>" + field[0] + "</td>" +
                    "<td>" + field[1] + "</td>" +
                    "</tr>"
            }
            str = "<div title='点击隐藏' style='background: #efef;opacity: 1;z-index: 999;'>" +
                "<i class=' icon-angle-up'></i>" +
                "<div class='info-item'><span class='title-info'>上传时间</span><span class='value'>" + obj.createtime + "</span></div>" +
                "<div class='info-item'><span class='title-info'>表名称</span><span class='value'>" + obj.tableName + "</span></div>" +
                "<div>" + tablestr + "</div>" +
                "</div>";
        }
        //显示
        jQuery(jQuery(that).children("i")).attr("class", "icon-eye-close");
        jQuery(that).append(str);
        jQuery(that).attr("iscreate", "1");
    }else{//要隐藏，就是直接除去
        jQuery(jQuery(that).children("i")).attr("class", "icon-eye-open");
        jQuery(jQuery(that).children("div")).remove();
        jQuery(that).attr("iscreate", "0");
    }
}

// 点击添加弹出数据源分组对话框
function addGroup(){
    currentModalType = "add";
    jQuery("#modal_title").text("添加数据源类别");
    jQuery(".modal-container form").hide();
    jQuery(".modal-container form#data_group_add_form").show();
    jQuery(".modal-container").show();
}

// 点击编辑按钮编辑分组信息
function editGroup(that){
    currentModalType = "edit";
    //获取参数
    var id = jQuery(that).attr("id").split("_")[1];
    var groupName = jQuery(jQuery(jQuery(that).parent()).siblings(".group_name")).text();
    currentGroupName = groupName;
    jQuery("#modal_title").text("修改数据源类别名称");
    // 设置参数
    jQuery("#data_group_edit_form #id").attr("value", id);
    jQuery("#data_group_edit_form #name").attr("value", groupName);
    // 显示modal
    jQuery(".modal-container form").hide();
    jQuery(".modal-container form#data_group_edit_form").show();
    jQuery("#modalContainer").show();
}

//点击删除按钮弹出提示信息
function delGroup(that){
    currentModalType = "del";
    //获取参数
    var id = jQuery(that).attr("id").split("_")[1];
    var number = jQuery(jQuery(jQuery(that).parent()).siblings(".number")).text();
    
    jQuery("#modal_title").text("系统提示");
    // 提示信息
    var tipstr = "";
    if(parseInt(number) > 0){
        tipstr = "该类别下还有 " + number + " 个数据源，是否删除？";
    }else{
        tipstr = "是否删除该类别？";
    }
    jQuery("#data_group_del_form #data_group_del").text(tipstr);
    jQuery("#data_group_del_form #id").attr("value", id);
    // 显示modal
    jQuery(".modal-container form").hide();
    jQuery(".modal-container form#data_group_del_form").show();
    jQuery("#modalContainer").show();
}

/***** 数据源点击事件 *****/
// 修改数据源所在分组
function openMoveModal(that){

    currentModalType = "source_move";
    var id = jQuery(that).attr("id").split("_")[1];

    //设置modal title
    jQuery("#modal_title").text("移动数据源");
    
    //设置可选组的option
    var str = "";
    for(var i = 0;i < groups.length;i++){
        var obj = groups[i];
        if(currentGroupId != obj.id)
            str += "<option value=" + obj.id + ">" + obj.name + "</option>";
    }
    //设置option
    //设置数据源的id，方便移动时使用
    jQuery("#move_datasource_form #id").attr("value", id);
    jQuery("#move_datasource_form select#group_select").html(str);

    jQuery(".modal-container form").hide();
    jQuery(".modal-container form#move_datasource_form").show();
    jQuery("#modalContainer").show();
}

// 打开删除数据源的提示框
function openDelModal(that){
    currentModalType = "source_del";
    //获取参数
    var id = jQuery(that).attr("id").split("_")[1];
    
    jQuery("#modal_title").text("系统提示");
    // 提示信息
    jQuery("#del_datasource_form #data_source_del").text("是否删除该数据源？");
    jQuery("#del_datasource_form #id").attr("value", id);
    // 显示modal
    jQuery(".modal-container form").hide();
    jQuery(".modal-container form#del_datasource_form").show();
    jQuery("#modalContainer").show();
}

//modal 确定点击事件
function modalClick(){
    //组 添加Modal
    if(currentModalType == "add")
        saveGroup();
    else if(currentModalType == "edit")//组编辑
        editGroup();
    else if(currentModalType == "del")//组删除
        deleteSelectGroup();
    else if(currentModalType == "source_move")
        moveDataSource();
    else if(currentModalType == "source_del")
        delDataSource();
}

//删除
function deleteSelectGroup(){
     var params = getFormParams("data_group_del_form");
    if(params.id != ""){
        jQuery.ajax({
            url:"datagroup/delete",
            type:"post",
            data:params,
            dataType:"json",
            success:function(data){
                jQuery(".modal-container").hide();
                if(data.result){
                    groups = data.groups;
                    showGroupList(data.groups);
                    setReportList(data.groups);
                    isModified = true;
                    alert("删除成功");
                }else{
                    alert("删除失败");
                }
            }
        })
    }else{
        alert("数据异常，请联系系统管理员");
    }
}

//保存数据源分组信息
function saveGroup(){
    var params = getFormParams("data_group_add_form");
    if(params.name != ""){
        jQuery.ajax({
            url:"datagroup/add",
            type:"post",
            data:params,
            dataType:"json",
            success:function(data){
                jQuery(".modal-container").hide();
                if(data.result == 2){
                    groups = data.groups;
                    isChangeReport = true;
                    showGroupList(data.groups);
                    setReportList(data.groups);
                    isModified = true;
                    alert("添加成功");
                }else if(data.result == 1){
                    alert("数据源类别名称已存在！");
                }else{
                    alert("添加失败");
                }
            }
        })
    }else{
        jQuery("#data_group_add_form>span.tip").text("名称不能为空！");
    }
}

// 保存修改内容信息
function saveModify(){
    var params = getFormParams("data_group_edit_form");
    if(params.id != "" && params.name != ""){
        if(params.name == currentGroupName){
            alert("保存成功");
        }else{
            jQuery.ajax({
                url:"datagroup/modify",
                type:"post",
                data:params,
                dataType:"json",
                success:function(data){
                    jQuery(".modal-container").hide();
                    if(data.result == 2){
                        groups = data.groups;
                        showGroupList(data.groups);
                        setReportList(data.groups);
                        isModified = true;
                        alert("保存成功");
                    }else if(data.result == 1){
                        alert("数据源类别名称已存在！");
                    }else{
                        alert("保存失败");
                    }
                }
            })
        }
    }else{
        if(params.name == "")
            jQuery("#data_group_add_form>span.tip").text("名称不能为空！");
        else{
            alert("数据异常，请联系系统管理员");
        }
    }
}

//数据源移动
function moveDataSource(){
    var params = getFormParams("move_datasource_form");
    console.log("move params", params);
    if(params.id != "" && params.groupId != ""){
        jQuery.ajax({
            url:"datasource/move",
            type:"post",
            data:params,
            dataType:"json",
            success:function(data){
                //隐藏modal
                jQuery(".modal-container").hide();
                if(data.result){
                    groups = data.groups;
                    datasources = data.datasources;
                    //更改数据源列表显示
                    var list = data.datasources[currentGroupId];
                    //判断是否有数据
                    if(list != undefined){
                        var str = getDataSourcesTableStr(list);
                        jQuery("#data_none_tip").hide();
                        jQuery("#data_table").show();
                        jQuery("#data_table").html(str);
                    }else{
                        jQuery("#data_none_tip").show();
                        jQuery("#data_table").hide();
                    }
                    //更改数据类别显示
                    showGroupList(data.groups);
                    setReportList(data.groups);
                    isModified = true;
                    alert("修改成功");
                }else{
                    alert("修改失败");
                }
            }
        })
    }else{
        alert("数据异常，请联系系统管理员");
    }
}

//数据源删除
function delDataSource(){
    var params = getFormParams("del_datasource_form");
    console.log("move params", params);
    if(params.id != ""){
        jQuery.ajax({
            url:"datasource/delete",
            type:"post",
            data:params,
            dataType:"json",
            success:function(data){
                jQuery(".modal-container").hide();
                if(data.result){
                    groups = data.groups;
                    datasources = data.datasources;
                    //因为删除了，所以需要更改显示
                    currentGroupId = data.groups[0].id;
                    //更改数据源列表显示
                    var str = getDataSourcesTableStr(data.datasources[currentGroupId]);
                    jQuery("#data_none_tip").hide();
                    jQuery("#data_table").show();
                    jQuery("#data_table").html(str);
                    //更改数据类别显示
                    showGroupList(data.groups);
                    setReportList(data.groups);
                    isModified = true;
                    alert("删除成功");
                }else{
                    alert("删除失败");
                }
            }
        })
    }else{
        alert("数据异常，请联系系统管理员");
    }
}

//设置图标需要的参数
function setReportList(groups){
    legend_datas = [];
    series_datas = [];
    for(var i = 0;i < groups.length;i++){
        legend_datas.push(groups[i].name);
        groups[i].value = groups[i].userId;
        series_datas.push(groups[i]);
   }
   if(isChangeReport)
       changeReport();
}

//  显示 数据源分组列表
function showGroupList(groups){
    var str = "";
    for(var i = 0;i < groups.length;i++){
        var obj = groups[i];
        var curr = obj.id == currentGroupId ? "current" : "";
        str += '<a id="' + obj.id + '" class="group-item ' + curr + '" onclick="showDatasourceList(this)">' + 
                '<i class="icon-asterisk"></i>' + 
                '<span class="group_name">' + obj.name + '</span>' + 
                '<span class="tools">' +
                    '<i title="修改" class="icon-edit" id="edit_' + obj.id + '" onclick="editGroup(this)"></i>' +
                    '<i title="删除" class="icon-minus-sign" id="del_' + obj.id + '" onclick="delGroup(this)"></i>' +
                '</span>' +
                '<span class="number">' + obj.userId + '</span>' +
            '</a>';
    }
    jQuery("#group_list").html(str);
}

// 输入框校验
function onInput(that){
    var value = jQuery(that).attr("value");
    var obj = jQuery(jQuery(that).parent()).siblings(".tip");
    if(value == "")
        jQuery(obj).text("名称不能为空！");
    else
        jQuery(obj).text("");
}

// 获取form元素参数，以json对象格式返回
function getFormParams(id){
    var params = {};
    var list = jQuery("#" + id).serializeArray();
    for(var i = 0;i < list.length;i++){
        var obj = list[i];//获取json对象
        params[obj.name] = obj.value;
    }
    return params;
}