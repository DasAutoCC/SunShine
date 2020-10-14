var preUserName;
var preEmail;
var userNamePassed;
var emailPassed;


function checkSpecialChar(charsquen){
	var isPassed = false;
	if( !charsquen.match( /^[\u4E00-\u9FA5a-zA-Z0-9]{1,}$/ ) ){
	    // 没有通过验证，
		isPassed = false;
	}else{
	    //通过了验证
		isPassed = true;
	}
	return isPassed;
}
function checkUsername(){
	var username = $("#usernameIns").val();
	var isPassed = false;
	if(username == ''){
		//输入为空的处理
		$("#userNameAlert").html("请输入用户名")
		return false;
	}
	if(username.length > 15){
		//名字太长
		$("#userNameAlert").html("用户名过长")
		return false;
	}
	var hasSpecialChar =  checkSpecialChar(username);
	if(!hasSpecialChar){
		//有特殊字符的处理
		$("#userNameAlert").html("您的用户名包含特殊字符")
		return false;
	}
	if(username != preUserName){
		$.post("/count-user",{"username":username},function(data){
			preUserName = username;
			if(data.respCode!=200){
				//名字被注册的处理
				$("#userNameAlert").html("此用户名已被注册")
				userNamePassed = false;
				return false;
			}else{
				$("#userNameAlert").html("可以使用的昵称/用户名")
				userNamePassed = true;
				return true;
			}
		})
	}else{
		return userNamePassed;
	}
	return true;
}
function checkPassword(){
	var password = $("#passwordIns").val();
	var isPassed = false;
	if(password == ''){
		//输入为空的处理
		 $("#passwordAlert").html("请输入密码");
		return false;
	}
	if(password.length <7 ){
		//输入为空的处理
		 $("#passwordAlert").html("密码太短");
		return false;
	}
	if(password.length >24 ){
		//输入为空的处理
		 $("#passwordAlert").html("密码这么长？");
		return false;
	}
	var hasSpecialChar =  checkSpecialChar(password);
	if(!hasSpecialChar){
		//有特殊字符的处理
		$("#passwordAlert").html("您的密码包含特殊字符");
		return false;
	}
	$("#passwordAlert").html("")
	return true;
}
function checkEmail(){
	var email = $("#emailIns").val();
	var isPassed = false;
	if(email == ''){
		//输入为空的处理
		$("#emailAlert").html("请输入邮箱");
		return false;
	}
	if(!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)){
		//邮箱格式不正确的处理
		$("#emailAlert").html("邮箱格式错误");
		return false;
	}
	if(preEmail!=email){
		preEmail = email;
		$.post("/count-email",{"email":email},function(data){
			if(data.respCode!=200){
				$("#emailAlert").html(data.message);
				emailPassed = false;
				return false;
			}else{
				$("#emailAlert").html(data.message);
				emailPassed = true;
				return true;
			}
		})
	}else{
		return emailPassed;
	}
	return true;
}
function checkCode(){
	var verifCode = $("#verifTextInstence").val();
	if(verifCode == ''){
		//输入为空的处理
		$("#verifAlert").html("请输入验证码");
		return false;
	}
	if(verifCode.length!=4){
		//输入的验证码长度错误的处理
		$("#verifAlert").html("验证码长度有误");
		return false;
	}
	$("#verifAlert").html("");
	return true;
}

function doChangeSeconds(){
	
	var time = 60;
	$("#sendMail").attr("disabled","disabled")
	$("#sendMail").css("cursor", "not-allowed")
	var t2 = window.setInterval(function() {
		time = time -1 ;
		$("#sendMail").attr("value",time+"s")
	},1000)
	
	var t1 = window.setTimeout(function() {
		window.clearInterval(t2)
		$("#sendMail").removeAttr("disabled")
		$("#sendMail").css("cursor", "pointer")
		$("#sendMail").attr("value", "发送验证码")
	},60000)
}

function sendMailService(){
	var isPassed = checkEmail();
	if(!isPassed){
		return;
	}
	$("#emailAlert").html("发送中...");
	$("#sendMail").attr("disabled","disabled")
	$("#sendMail").css("cursor", "not-allowed")
	$.post("/send-verif-code",{"mailAddress":$("#emailIns").val()},function(data){
		if(data.respCode == 200){
			$("#emailAlert").html("发送成功！");
			doChangeSeconds();
		}else{
			$("#emailAlert").html("发送失败:"+data.message);
			$("#sendMail").removeAttr("disabled")
			$("#sendMail").css("cursor", "pointer")
			$("#sendMail").attr("value", "发送验证码") 
		}
	})
}
 
$("#usernameIns").focusout(function(){
	checkUsername();
})
$("#passwordIns").focusout(function(){
	checkPassword();
})
$("#emailIns").focusout(function(){
	checkEmail();
})
$("#verifTextInstence").focusout(function(){
	checkCode();
})

$("#sendMail").click(function(){
	sendMailService();
})
//提交检测
$("#formSubmit").click(function(){
	var u1 = checkUsername();
	var u2 = checkPassword();
	var u3 = checkEmail();
	var u4 = checkCode();
	if(u1&&u2&&u3&&u4){
		if(!confirm("您将以用户名:【"+$("#usernameIns").val()+"】进行注册，用户名暂时无法更改,您确认提交么？")){
			return ;
		}
		var DTO = {
			"verifCode":$("#verifTextInstence").val(),
			"nickName":  $("#usernameIns").val(),
			"password": $("#passwordIns").val(),
			"email" : $("#emailIns").val(),
			"sex" : $("#sexCheck").val()
		}
		$.post("/reg",DTO,function(data){
			if(data.respCode == 200){
				alert("注册成功！")
				window.location.href="/";
			}
			else{
				alert("非常抱歉注册失败:"+data.message)
			}
		})
	}else{
		alert("您的输入有误，请核实您的输入再试！")
	}
})
