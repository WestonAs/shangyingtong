$(function() {
	$('.forminput,.formbt').each(function() {
		$(this)
			.focus(function() {$(this).addClass('sffocus');})
			.blur(function() {$(this).removeClass('sffocus');});
	});
	var message = $('#message').val();
	if($.trim(message) != ""){
		alert(message);
	}
	$('#userId').focus();
	
	$.post(CONTEXT_PATH + "/hasNotice.do", {'callId':callId()}, function(json){
		if (json.success){
			$('#pubTitle').html(json.title);
			$('#pubDate').html(json.pubTime);
			$('#pubUser').html(json.pubUser);
			$('#pubContent').html(json.content);
			
			$("#id_Notice").PopupDiv({
	            PopupDivId: "id_Notice",
	            title: '系统通知',
	            modal:true,
	            close_btn:true,
	            minHeight: 300,
	            minWidth: 500,
	            close_fn_later:userIdFocus
		     });
		}
	}, 'json');
});

function userIdFocus(){
	$("#userId").focus();
}

function login(){
	var userName = $("#userId");
	var userPwd = $("#userPwd");
	var role = $('#roleId');
	var code = $('#validateCode');
	
	if(validateUserName(userName) && validateUserPwd(userPwd) && checkValidateCode(code) && validateRole(role)){
		var userId = $("#userId").val();
		var encryptPwd = b64_md5($("#random").val()+b64_md5($("#userPwd").val()).toUpperCase()).toUpperCase();
		var validateCode = $('#validateCode').val();
		var roleId = $('#roleId').val();
		$.post(CONTEXT_PATH + "/checkLogin.do", {'userInfo.userId':userId, 'userInfo.userPwd':encryptPwd
				, 'roleId':roleId, 'validateCode':validateCode, 'formMap.random':$("#random").val(), 'callId':callId()},
			function(data){
				if(data.success){
					$("#encryptPwd").val(encryptPwd);
					$("#userPwd").attr("disabled","disabled");
					$('#loginForm').submit();
					$('#id_enter').attr('disabled', 'true');
				} else {
					alert(data.msg);
					if(data.isExpired){
						if(location.href.indexOf("certLogin.do")>0){
							location = CONTEXT_PATH;
						}else{
							location=location;
						}
					}else if(data.imgError){
						refresh();
						$('#validateCode').val('');
						$("#validateCode").focus();
					}else{
						$("#userPwd").val('');
						refresh();
						$('#validateCode').val('');
						$("#userPwd").focus();
					}
					return false;
				}
			}, 'json');
	} else {
		return false;
	}
}


//按回车自动提交表单
function autoCommit(e){   
   	e = e ? e :(window.event?window.event:null); 
   	if(e.keyCode == 13){ login();} 
}

function callId(){
	var random = Math.floor(Math.random() * 10001);
  	return (random + "_" + new Date().getTime()).toString();
}

// 加载角色
function loadRole(){
	var userId = $.trim($('#userId').val()) ;
	if (isEmpty(userId)){return;}
	$('#roleId').html('').load(CONTEXT_PATH + "/loadRole.do", {'userInfo.userId':userId, 'callId':callId()});
}

/**
 * 用户名验证
 */
function validateUserName(target){
	var tarValue = target.val();
	
	if($.trim(tarValue) == ""){
		alert("用户名不能为空！");
		$("#userId").focus();
		return false;
	}
	
	//验证非法字符
	if($.trim(tarValue) != ""){
		var reg = /[@#$%^&*<>!]+/g;
		if(reg.test(tarValue)){
			alert("用户名包含非法字符！");
			$("#userId").focus();
			return false;
		}
	}
	return true;
}

/**
 * 验证码验证
 */
function checkValidateCode(target){
	var tarValue = target.val();
	
	if($.trim(tarValue).length < 4){
		alert("请输入4位验证码!");
		$("#validateCode").focus();
		return false;
	}
	return true;
}

/**
 * 密码验证
 */
function validateUserPwd(target){
	var tarValue = target.val();
	
	if($.trim(tarValue) == ""){
		alert("密码不能为空！");
		$("#userPwd").focus();
		return false;
	}
	
	if($.trim(tarValue).length < 6){
		alert("密码至少六位!");
		$("#userPwd").focus();
		return false;
	}
	return true;
}

/**
 * 角色验证
 */
function validateRole(target){
	var tarValue = target.val();
	if($.trim(tarValue) == ""){
		alert("请选择一个角色!");
		return false;
	}
	return true;
}

/**
 * 刷新验证码
 */
function refresh() {
	var image = document.getElementById('validateImage');
	image.src = 'validateImage.do?r=' + Math.random();
}

function refreshCode() {
	refresh();
	$('#validateCode').val('');
	$('#validateCode').focus();
}

function CheckUSBKey() {
	var USBKeyFlag = 0; //USBKeyFlag＝0：科友的key；USBKeyFlag＝1：非科友的key

    try{
		//捷德的key不在线，检查飞天的key
		var isOnline = document.all.FTUSBKEYCTRL.IsOnline;
		if (isOnline == 0) {
			if(FTDoSign()){ //调用FT的签名函数
				return true;
			} else {
				return false;
			}
		} else {
			alert("请检查USB Key是否插入或USB Key是否正确！");
			return false;
	    }
	} catch(ex) {
		alert(ex);
	}
	return true;
}

/*飞天的Key的签名函数*/
function FTDoSign(){
	var SetCertResultRet = document.all.FTUSBKEYCTRL.SetCertResult; //选择证书
	if (SetCertResultRet == 0)
	{
		var serialNumber = document.all.FTUSBKEYCTRL.GetCertSerial;
		$('#serialNo').val(serialNumber);
		//document.UserForm.MySerialNumber.value = serialNumber;
   		
        var SignResultRet = document.all.FTUSBKEYCTRL.SignResult($("#random").val());
		if (SignResultRet == 0){
			var signData = document.all.FTUSBKEYCTRL.GetSignature;
			$('#mySign').val(signData);
			$('#loginForm').attr('action', 'certLogin.do').submit();
		}
		else
		{
			alert("Sign data failed");
		}

		//document.UserForm.submit();
	}
	else
	{
		alert("Choose cert failed");
	}
}
