/**
 * 校验表单的完整性，并给出提示
 * @param params form表单的数据
 * @param formId form表单的id
 * @return boolean 是否完整
 */
function valiForm(params){
    var flag = true;
    for(var key in params){
        if(params[key] == ""){
            flag = false;
            var title = jQuery("#" + key).attr("placeholder");
            jQuery("#" + key).addClass("required");
            jQuery(jQuery("#" + key).siblings('.tip')).text(title + "不能为空！");
        }
    }
    return flag;
}

/**
 * 把form参数转成json map 格式
 * @param id form的id
 * @return json对象
 */
function getFormParams(id){
    var params = {};
    var list = jQuery("#" + id).serializeArray();
    for(var i = 0;i < list.length;i++){
        var obj = list[i];//获取json对象
        params[obj.name] = obj.value;
    }
    return params;

}

/**
 * 输入即时校验
 */
function onInput(that,isFilter=true, fn=xzcd){
    var value = jQuery(that).val();
    if(isFilter)
        value = value.replace(/[^0-9a-zA-Z]/g,"");
    if(value == ""){
        jQuery(that).addClass("required");
        var title = jQuery(that).attr("placeholder");
        jQuery(jQuery(that).siblings('.tip')).text(title + "不能为空！");
    }else{
        var len = jQuery(that).attr("xzcd");
        value = fn(value, len);
        jQuery(that).removeClass("required");
        jQuery(jQuery(that).siblings('.tip')).text("");
    }
    jQuery(that).val(value);
}

//校验2次密码是否一致
function valipass(that){
    onInput(that);
    var id = jQuery(that).attr("id");
    var pass = jQuery("#" + id.substring(0, id.length - 1)).val();
    var pass2 = jQuery(that).val();
    if(pass != "" && pass2 != "" && pass != pass2){
        jQuery(jQuery(that).siblings(".tip")).text("2次密码输入不一致！");
    }
}

/**
 * 长度限制
 */
function xzcd(value, len){
    if(value.length <= len)
        return value;
    else
        return value.substring(0, len);
}

function xzcd2(value, len){
    var index = getLastIndex(value, len);
    if(index == -1)
        return value;
    else
        return value.substring(0, index);
}

function getLastIndex(str, len){
    var realLength = 0;
    var current = -1;
    if(str != ""){
        for(var i = 0;i < str.length;i++){
            var charCode = str.charCodeAt(i);
            if (charCode >= 0 && charCode <= 128) {//非中文
                realLength += 1;
            }else{
                // 如果是中文则长度加3
                realLength += 3;
            }
            if(realLength > len){
                current = i;
                break;
            }
        }
    }
    return current;
}

/**
 * 输入即时校验2不限定
 */
function onInput2(that){
    onInput(that, false, xzcd2);
}

/**
 * 重置密码表单提交
 */
function resetPass(){
    var params = getFormParams("forget_form");//获取login表单参数
    if(valiForm(params)){
        if(params.password == params.password2){
        	jQuery.ajax({
        		url:"../user/resetpwd",
        		type:"post",
        		data:params,
        		dataType:"json",
        		success:function(data){
        			if(data.result){
        				window.location.href = "forget_result.jsp";
        			}else{
        				jQuery(jQuery("#forget_form #username").siblings(".tip")).text("重置密码失败！");
        			}
        		}
        	})
        }else{
            jQuery(jQuery("#forget_form #password2").siblings(".tip")).text("2次密码输入不一致！");
        }
    }
}

/**
 * 注册表单提交
 */
function regist(){
    var params = getFormParams("regist_form");//获取login表单参数
    if(valiForm(params)){
        if(params.password == params.password2){
        	jQuery.ajax({
        		url:"../user/regist",
        		type:"post",
        		data:params,
        		dataType:"json",
        		success:function(data){
        			if(data.result == 0){
        				window.location.href = "regist_result.jsp";
        			}else if(data.result == 1){
        				jQuery(jQuery("#regist_form #username").siblings(".tip")).text("注册失败！");
        			}else if(data.result == 2){
        				jQuery(jQuery("#regist_form #username").siblings(".tip")).text("该用户名已存在！");
        			}
        		}
        	})
        }else{
            jQuery(jQuery("#regist_form #password2").siblings(".tip")).text("2次密码输入不一致！");
        }
    }
}

/**
 * 修改密码
 * */
function modifyPass() {
    var params = getFormParams("main_modifyPass_form");//获取login表单参数
    if(valiForm(params)){
        if(params.password == params.password2){
            jQuery.ajax({
                url:"user/modifyPass",
                type:"post",
                data:params,
                dataType:"json",
                success:function(data){
                    if(data.result){
                        window.location.href = "main/modifyPass_result.jsp";
                    }else{
                        jQuery(jQuery("#main_modifyPass_form #username").siblings(".tip")).text("修改密码失败！");
                    }
                }
            })
        }else{
            jQuery(jQuery("#main_modifyPass_form #password2").siblings(".tip")).text("2次密码输入不一致！");
        }
    }
}