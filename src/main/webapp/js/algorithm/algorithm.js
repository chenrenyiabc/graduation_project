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
                    "<td id='myid1_"+ data[row]["id"] +"' value='"+ data[row]["algorithm_name"] +"'>"+ data[row]["algorithm_name"] +"</td>" +
                    "<td id='myid2_"+ data[row]["id"] +"' value='"+ data[row]["algorithm_path"] +"'>"+ data[row]["algorithm_path"] +"</td>" +
                    "<td>" +
                        "<button type='button' id='modify_"+data[row]["id"]+"' onclick='modifyFunction("+data[row]["id"]+")' data-toggle='modal' data-target='#myModal' class='btn btn-warning' value='"+data[row]["id"]+"'>修改</button>" +
                        "<button type='button' id='delete_"+data[row]["id"]+"' onclick='deleteFunction("+data[row]["id"]+")' data-toggle='modal' data-target='#myModal2' class='btn btn-danger' value='"+data[row]["id"]+"'>删除</button>" +
                    "</td>" +
                    "</tr>"
                );

            }
        }
    })

    

})

function modifyFunction(id) {
    console.log(id);
    //$("#myModalLabel").text("修改算法");
    var method = "query_exist_algorithm";
    $.ajax({
        url : 'AlgorithmServlet',
        type : 'post',
        dataType : 'json',
        data : {
            method : method,
            algoId : id
        },
        success : function (data) {

            $("#modifyMain").html("");
            $("#modifyMain").append("<h2>修改已有算法</h2>" +
                "<form action='AlgorithmServlet?method=modifyAlgorithm' method='post'>" +
                "请重新输入算法名：" +
                "<input type='text' class='algorithm_name' name='algorithm_name' value='"+data[0]['algorithm_name']+"' /><br /><br />" +
                "请重新输入算法路径：" +
                "<input type='text' class='algorithm_path' name='algorithm_path' value='"+data[0]['algorithm_path']+"' /><br /><br />" +
                "<input type='hidden' name='algoId' value='"+data[0]['id']+"' />" +
                "<input type='submit' class='submit' name='submit' value='提交修改的信息' />" +
                "</form>");
        }
    })
}

function deleteFunction(id){
    console.log(id);
    //$("#myModalLabel").text("删除算法");
    var method = "query_exist_algorithm";
    $.ajax({
        url : 'AlgorithmServlet',
        type : 'post',
        dataType : 'json',
        data : {
            method : method,
            algoId : id
        },
        success : function (data) {

            $("#deleteMain").html("");
            $("#deleteMain").append("<h2>确定要删除该算法吗?</h2>" +
                "<table class='table table-hover'>" +
                "<tr>" +
                "<th>算法ID</th>" +
                "<th>算法名</th>" +
                "<th>算法路径</th>" +
                "</tr>" +
                "<tr>" +
                "<td>" + data[0]["id"] + "</td>" +
                "<td>" + data[0]['algorithm_name'] + "</td>" +
                "<td>" + data[0]['algorithm_path'] + "</td>" +
                "</tr>" +
                "</table>" +
                "<form action='AlgorithmServlet?method=deleteAlgorithm' method='post'>" +
                "<input type='hidden' name='algoId' value='"+data[0]['id']+"' />" +
                "<button type='submit' class='btn btn-warning' name='submit'>删除</button>" +
                "</form>" +
                "<button type='button' class='btn btn-success' data-dismiss='modal'>取消</button>");
        }
    })
}
