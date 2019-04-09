$(function () {
    var method = "query_algorithm";
    console.log("dddd");
    $.ajax({
        url : 'AlgorithmServlet',
        type : 'post',
        dataType : 'json',
        data : {
            method : method
        },
        success : function(data){
            for(var row in data){
                console.log("row---" + data[row]);
                $("#tbale_body").append("<tr></tr>");
                //for(var col in data[row]){
                //    console.log("col---" + col);
                $("#tbale_body tr:last").append("<td>"+ data[row]["id"] +"</td>");
                $("#tbale_body tr:last").append("<td class='changemodify' id='name' value='"+data[row]["id"]+"'>"+ data[row]["algorithm_name"] +"</td>");
                $("#tbale_body tr:last").append("<td class='changemodify' id='path' value='"+data[row]["id"]+"'>"+ data[row]["algorithm_path"] +"</td>");
                $("#tbale_body tr:last").append("<td>" +
                    "<div class='operate'><button type='button' id='modify' class='btn btn-warning' value='"+data[row]["id"]+"'>修改</button></div>" +
                    "<div class='operate'><button type='button' id='delete' class='btn btn-danger' value='"+data[row]["id"]+"'>删除</button></div>" +
                    "</td>");
                //}

            }
        }
    })

    $("#tbale_body").on("click","#modify",function(){
        var value = $(this).val();
        console.log("value---" + value);
        console.log("name_value---" + $(this).find("#name").html());
        console.log("path_value---" + $(this).find("#path").html());

        if($("#name").attr("value") == value){
            $("#name").html("");
            $("#name").html("<input type='text' class='form-control' placeholder='请输入新的算法名' aria-describedby='basic-addon1'>");
        }
        if($("#path").attr("value") == value) {
            $("#path").html("");
            $("#path").html("<input type='text' class='form-control' placeholder='请输入新的算法路径' aria-describedby='basic-addon1'>");
        }

    })

    $("#tbale_body").on("click","#delete",function(){
        alert("clickdelete");
    })

})