$(function () {
    var method = "query_algorithm";
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
                //$("#tbale_body").append("<tr></tr>");
                //for(var col in data[row]){
                //    console.log("col---" + col);
                $("#tbale_body").append("<tr>" +
                    "<td>"+ data[row]["id"] +"</td>" +
                    "<td>"+ data[row]["algorithm_name"] +"</td>" +
                    "<td>"+ data[row]["algorithm_path"] +"</td>" +
                    "<td>" +
                        "<button type='button' id='modify' class='btn btn-warning' value='"+data[row]["id"]+"'>修改</button>" +
                        "<button type='button' id='delete' class='btn btn-danger' value='"+data[row]["id"]+"'>删除</button>" +
                    "</td>" +
                    "</tr>"
                );

            }
        }
    })

    $("#tbale_body").on("click","#modify",function(){
        var value = $(this).val();


    })

    $("#tbale_body").on("click","#delete",function(){
        alert("clickdelete");
    })

})