/*
 * 本js管理登录
 */
$(function(){
	$("#login_submit").parent().html('<img id="loading" src="images/loading_big.gif" hidden="hidden"/><button name="submit_login" id="login_submit"  class="btn btn-primary">登录</button><a href="reg.html" class="btn btn-secondary">注册</a>');
	//点击登录
	$("#login_submit").click(function(){
		if($("#login_username").val()==""){
			$("#login_username").focus();
		}else{
			if($("#login_password").val()==""){
				$("#login_password").focus();
			}else{
				queryUserByName($("#login_username").val(),$("#login_password").val());
			}

			
		}

	});
});