<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<html xmlns="http://www.w3.org/1999/xhtml">
    
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<meta http-equiv="x-ua-compatible" content="ie=7" />
	<meta name="robots" content="noindex" />
	<meta name="Copyright" content="广州银联网络支付有限公司" />
	<meta name="Author" content="银联网络支付" />
	<title>银联网络积分卡管理系统</title>
	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/sys.jsp"%>
	<f:css href="/images/login.css"/>
	
	<f:js src="/js/jquery.js"/>
	<f:js src="/js/common.js"/>
	
	<script>
	/**
	 * 持卡人登录
	 * @return
	 */
	function cardholderLogin(){
		var cardNo = $("#Id_cardId");
		var password = $("#userName");
		
		if(validateCardId(cardNo) && validateUserPwd(password)){
			var cardId = $("#Id_cardId").val();
			var pwd = $("#userName").val();
			$.post(CONTEXT_PATH + "/cardholder/checkCardholderLogin.do", {'cardExtraInfo.cardId':cardId, 'cardExtraInfo.password':pwd, 'callId':callId()},
					function(data){
				if(data.success){
					$('#loginForm').submit();
					$('#id_enter').attr('disabled', 'true');
				} else {
					alert(data.msg);
					$("#userName").val('');
					$("#userName").focus();
					return false;
				}
			}, 'json');
		} else {
			return false;
		}
	}
	
	/**
	 * 密码卡号
	 */
	function validateCardId(target){
		var tarValue = target.val();
		
		if($.trim(tarValue) == ""){
			alert("卡号不能为空！");
			$("#Id_cardId").focus();
			return false;
		}
		
		if($.trim(tarValue).length != 18 && $.trim(tarValue).length != 19 && $.trim(tarValue).length != 11){
			alert("卡号需要为11位、18位或19位!");
			$("#Id_cardId").focus();
			return false;
		}
		return true;
	}
	
	//按回车自动提交表单
	function autoCommit(e){   
	   	e = e ? e :(window.event?window.event:null); 
	   	if(e.keyCode == 13){ cardholderLogin();} 
	}
	
	/**
	 * 密码验证
	 */
	function validateUserPwd(target){
		var tarValue = target.val();
		
		if($.trim(tarValue) == ""){
			alert("密码不能为空！");
			$("#userName").focus();
			return false;
		}
		
		if($.trim(tarValue).length < 6){
			alert("密码至少六位!");
			$("#userName").focus();
			return false;
		}
		return true;
	}
	</script>	
</head>

    
<body class="fullwidth" scroll="auto" leftmargin="0" topmargin="0">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="center" valign="middle"><table width="900" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="left"><img src="../images/logo.jpg" width="177" height="53" /></td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><span class="logo"><img src="../images/login.jpg" width="503" height="357" title="银联网络积分卡管理系统" /></span></td>
            <td width="390" valign="top">
            <div class="mainright">
            <form id="loginForm" action="cardholderLogin.do" method="post">
              <div class="formbox">
                <div class="formleft"><img title="持卡人登陆" src="../images/user.gif" /></div>
                <div class="formright"><span style="font-size:14px;font-weight:bold; padding-left:5px;">持卡人登陆</span></div>
              </div>
              <div class="formbox">
                <div class="formleft">卡号</div>
                <div class="formright">
                  <input id="Id_cardId" class="forminput" name="cardExtraInfo.cardId" type="text" onkeypress="autoCommit(event)" value=""/>
                </div>
              </div>
              <div class="formbox">
                <div class="formleft">密码</div>
                <div class="formright">
                  <input id="userName" class="forminput" name="cardExtraInfo.password" type="password" onkeypress="autoCommit(event)" value=""/>
                </div>
              </div>
              <div class="formbox">
                <div class="formleft"></div>
                <div class="formbtbox">
                	<img class="formbt" id="id_enter" src="../images/enter.gif" alt="" onFocus="this.blur()" onclick="cardholderLogin()" title="点击持卡人登录" align="middle" border="0" style="cursor: hand"/> 
                </div>
              </div>
              
	        </form>
            </div>
            </td>
          </tr>
        </table></td>
      </tr>
</table>

    <!--登陆口底部-->
	<div class="footer">
		<div class="footshadow"></div>
		<div class="foottext">
		<div class="textleft">建议使用IE6.0版本以上浏览器+1024分辨率获得最佳操作体验</div>
		<div class="textright">版权所有&copy;<a href="http://www.gnete.com" title="访问银联网络官方网站" target="_blank">广州银联网络支付有限公司</a> 全国客服热线：4008-333-222</div>
	</div>
</div>
</body>
</html>

