////////////////////
//      ajax
////////////////////

//method请求的类型,url地址,type类型,fun成功时运行的函数,syn是否异步（true异步）
function fajax(method, url, type, fun, syn) {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            (type == "json") ? fun(JSON.parse(xhr.responseText)) : fun(xhr.responseText);
        }
    }
    xhr.open(method, url, syn);
    xhr.send();
}

function fget(url, fun) {
    return fajax("get", "../" + url, null, fun, true);
}

function fgetJSON(url, fun) {
    return fajax("get", "../" + url, "json", fun, true);
}

//////////////////
//   普通函数
//////////////////

function isUndefined(obj) {
    obj = obj ? obj : "";
}

//加载流程列表
function loadFlowList() {
    fgetJSON("GetFlowlist", (data) => {
        let dftable = ``;
        for (x in data) {
            //检查属性是否被定义
            data[x].sourceName = data[x].sourceName ? data[x].sourceName : "";
            data[x].hive_sql = data[x].hive_sql ? data[x].hive_sql : "";
            data[x].algorithm_name = data[x].algorithm_name ? data[x].algorithm_name : "";
            data[x].result_table = data[x].result_table ? data[x].result_table : "";
            data[x].result_path = data[x].result_path ? data[x].result_path : "";
            dftable += `<tr>
                <td>` + data[x].id + `</td>
                <td>` + data[x].name + `</td>
                <td>` + data[x].userName + `</td>
                <td>` + data[x].flow_type + `</td>
                <td>` + data[x].flow_status + `</td>
                <td>` + data[x].sourceName + `</td>
                <td>` + data[x].algorithm_name + `</td>
                <td>` + data[x].result_path + `</td>
                <td>` + data[x].hive_sql + `</td>
                <td>` + data[x].result_table + `</td>
                <td>
                    <button type="button" class="btn btn-success" onclick="fget('RunFlow?id=` + data[x].id + `', info => window.location.reload())">使用</button>
                    <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#myModal" onclick="modify(` + data[x].id + `,'` + data[x].flow_type + `')">修改</button>
                    <button type="button" class="btn btn-danger" onclick="del(` + data[x].id + `)">删除</button>
                </td>
            </tr>`
        }
        document.getElementById("dfTable").innerHTML = dftable
    })
}

function modify(fid, type) {
    document.getElementById("myModalLabel").innerText = "修改" + type + "流程";
    document.getElementById("modifyFlowFam").src = "../AnalysisServlet?method=query_exist_flow&flowId=" + fid
}

//删除流程
function del(id) {
    fget("DelFlow?id=" + id, (info) => {
        console.log(info);
        if (info >= 0) {
            alert("删除成功");
            window.location.reload()
        } else
            alert("删除失败")
    })
}

loadFlowList();
