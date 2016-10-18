/**
 * 跨页方法
 **/

$(function() {
	//跳转视频上传页面
	goUpload = function() {
			window.location.href = "upload.html";
		}
		//查询用户名是否存在
	queryUserIsExisted = function(name) {
			$.ajax({
				type: 'post',
				url: 'http://localhost:8080/iwk/wUser_queryUserIsExisted',
				data: {
					name: name
				},
				dataType: 'jsonp',
				jsonp: "callback",
				success: function(data) {
					if (data.json == "用户名存在") {
						alert(data.json);
						$("#name").val("").focus();
					}

				},
				error: function() {
					alert('网络异常');
				}
			});

		}
		//查询用户是否登录
	queryUserIsLogined = function() {
		if (sessionStorage.getItem("user") == "" || sessionStorage.getItem("user") == null) {

		} else {
			var user = JSON.parse(sessionStorage.getItem("user"));
			$("#showName").html(user.name);
		}
	}
	queryUserIsLogined();
	//通过学号查询用户
	queryUserBySno = function(sno) {
			$.ajax({
				type: 'post',
				url: 'http://localhost:8080/iwk/wUser_queryUserBySno',
				data: {
					sno: sno
				},
				dataType: 'jsonp',
				jsonp: "callback",
				success: function(data) {

					//把用户信息放到localStorage
					var reginfo = {
						id: data.json[0].id, //编号
						icons: data.json[0].icons, //头像
						name: data.json[0].name, //登陆名（昵称）
						pwd: data.json[0].pwd, //登陆密码
						question: data.json[0].question, //找回密码问题
						answer: data.json[0].answer, //找回密码答案
						school: data.json[0].school, //学校
						colleage: data.json[0].colleage, //学院
						professional: data.json[0].professional, //专业
						clazz: data.json[0].clazz, //班级
						sno: data.json[0].sno, //学号
						sname: data.json[0].sname, //学生姓名
						ssex: data.json[0].ssex, //学生性别
						email: data.json[0].email, //邮箱
						introduce: data.json[0].introduce //自我介绍
					};
					sessionStorage.setItem("user", JSON.stringify(reginfo));
				},
				error: function() {
					alert('网络异常');
				}
			});
		}
		//通过用户名查询用户
	queryUserByName = function(name, pwd) {
		console.log(name + "," + pwd);
		$.ajax({
			type: 'post',
			url: 'http://localhost:8080/iwk/wUser_queryUserByName',
			data: {
				name: name,
				pwd: pwd
			},
			dataType: 'jsonp',
			jsonp: "callback",
			beforeSend: function() {
				// Handle the beforeSend event
				$("#loading").show();
			},
			success: function(data) {

				if (data.json == null)
					alert("用户名或者密码有误！");
				else {
					//登录成功保存用户信息
					console.log(JSON.stringify(data));
					//关闭登录显示用户名
					$("#loading").hide();
					$("#login-modal").hide(1000);
					$(".modal-backdrop").hide();

					//把用户信息放到localStorage
					var reginfo = {
						id: data.json[0].id, //编号
						icons: data.json[0].icons, //头像
						name: data.json[0].name, //登陆名（昵称）
						pwd: data.json[0].pwd, //登陆密码
						question: data.json[0].question, //找回密码问题
						answer: data.json[0].answer, //找回密码答案
						school: data.json[0].school, //学校
						colleage: data.json[0].colleage, //学院
						professional: data.json[0].professional, //专业
						clazz: data.json[0].clazz, //班级
						sno: data.json[0].sno, //学号
						sname: data.json[0].sname, //学生姓名
						ssex: data.json[0].ssex, //学生性别
						email: data.json[0].email, //邮箱
						introduce: data.json[0].introduce //自我介绍
					};
					sessionStorage.setItem("user", JSON.stringify(reginfo));
					//显示用户名在顶部
					queryUserIsLogined();
				}

			},
			error: function() {
				alert('网络异常');
			}
		});
	}

});