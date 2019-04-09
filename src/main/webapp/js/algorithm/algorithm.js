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
            console.log(data);
        }
    })
})