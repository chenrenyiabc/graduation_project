/**
 * 登录提交
 */
function login(){
    var params = getFormParams("login_form");//获取login表单参数
    if(valiForm(params)){
    	jQuery.ajax({
    		url:"user/login",
    		type:"post",
    		data:params,
    		dataType:"json",
    		success:function(data){
    			if(data.result){
    				window.location.href = "main/main.jsp";
    			}else{
    				jQuery(jQuery("#login_form #username").siblings(".tip")).text("用户名或密码错误！");
    			}
    		}
    	})
}
}