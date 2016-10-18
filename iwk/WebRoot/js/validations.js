function validationEmpty(objects,btn_close,btn_ok) { //非空验证	
	//非空验证
	if ($(objects).val() == null || $(objects).val() == "") {		
		popoverShow(objects,btn_close,btn_ok,"输入不能为空");
		return false;
	} else {
		popoverHide(objects,btn_close,btn_ok);
		return true;
	}
}

//popover显示
function popoverShow(objects,btn_close,btn_ok,contents){
	$(objects).popover({
			trigger: 'manual', //触发方式
			placement: 'bottom', //弹出位置
			title: '<label style="color:red;">ERROR<label', //弹窗题目
			html: 'true', //是否识别标签
			content: contents, //this is the content of the html box. add the image here or anything you want really.
			animation: false
		}).popover("show").focus();
	$(btn_close).attr("disabled",true);
	$(btn_ok).attr("disabled",true);
		
}

//popover隐藏
function popoverHide(objects,btn_close,btn_ok){
	$(objects).popover("hide");
	$(btn_close).attr("disabled",false);
	$(btn_ok).attr("disabled",false);
}