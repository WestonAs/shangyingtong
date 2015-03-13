<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<html xmlns="http://www.w3.org/1999/xhtml">
	
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<meta http-equiv="x-ua-compatible" content="ie=7" />
	<meta name="robots" content="noindex" />
	<meta name="Copyright" content="广州银联网络支付有限公司" />
	<meta name="Author" content="银联网络支付" />
	<title>预付卡系统</title>
	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/sys.jsp"%>
	<f:css href="/images/login.css"/>
	<f:css href="/css/PopupDiv.css"/>
	
	<f:js src="/js/jquery.js"/>
	<f:js src="/js/plugin/md5.js?v=20140604"/>
	<f:js src="/js/index.js?v=201408021"/>
	<f:js src="/js/common.js"/>
	<f:js src="/js/popupDiv/PopupDiv_v1.0.js"/>
	
	<style type="text/css">
		body {background-image: url(images/bg.jpg);background-repeat: repeat-x;}
		.notice_div{
			display:none;
			padding: 10px 0px 10px 0px;
			text-align: center;
			font-family:Arial,Helvetica,sans-serif;
		}
		.notice_title{
			color:#03005C;
			font-family:"黑体";
			font-size:20px;
			font-weight:normal;
			height:30px;
			line-height:30px;
			overflow:hidden;
			text-align:center;
			width:90%;
			margin:5px;
			border-bottom:1px solid #C8D8F2;
		}
		.notece_info {
			line-height:14px;
			overflow:hidden;
			padding-top:10px;
			text-align:center;
		}
		.notice_content{
			text-indent:2em; 
			line-height:26px; 
			text-align: left;
			font-size: 14px; 
			margin-top:15px;
			padding: 0px 20px 0px 20px;
		}
	</style>
	<script type="text/javascript">
		$().ready(function(){
			var homeSmall = $('#idhomeSmall').val();
			var homeBig = $('#idhomeBig').val();
			$('#id_home_small').attr('src', homeSmall);
			$('#id_home_big').attr('src', homeBig);
			$('#id_content').show();
			$('#userId').focus();
		});
	</script>
</head>

    
<body class="fullwidth" id="id_content" scroll="auto" leftmargin="0" topmargin="0" style="display: none;">

	<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td align="center" valign="middle"><table width="900" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td align="left"><img id="id_home_small" src="images/logo.jpg" width="177" height="53" /></td>
	            <td>&nbsp;</td>
	          </tr>
	          <tr>
	            <td><span class="logo"><img id="id_home_big" src="images/login.jpg" width="503" height="357" title="预付卡管理系统" /></span></td>
	            <td width="390" valign="top">
		            <div class="mainright">
			            <form id="loginForm" action="login.do" method="post">
			            	<input type="hidden" name="logoConfig.loginSmall" value="${logoConfig.loginSmall}"/>
			            	<input type="hidden" id="idhomeSmall" name="logoConfig.homeSmall" value="${logoConfig.homeSmall}"/>
			            	<input type="hidden" id="idhomeBig" name="logoConfig.homeBig" value="${logoConfig.homeBig}"/>
			            	<s:hidden id="random" name="formMap.random" />
			            	<input type="hidden" id="encryptPwd" name="userInfo.userPwd" />
				            <div class="formbox">
				                <div class="formleft"><img title="用户登陆" src="images/user.gif" /></div>
				                <div class="formright"><span style="font-size:14px;font-weight:bold; padding-left:5px;">用户登陆</span></div>
				            </div>
				            <div class="formbox">
				                <div class="formleft">帐号</div>
				                <div class="formright">
				                  <input id="userId" class="forminput" name="userInfo.userId" type="text" onblur="loadRole();" onkeypress="autoCommit(event)" value=""/>
				                </div>
				            </div>
				            <div class="formbox">
				                <div class="formleft">密码</div>
				                <div class="formright">
				                  <input id="userPwd" class="forminput" type="password" onkeypress="autoCommit(event)" value=""/>
				                </div>
				            </div>
				            <div class="formbox">
				                <div class="formleft">验证码</div>
				                <div class="formright" >
					                <input id="validateCode" class="forminput" name="validateCode" type="text" value="" onkeypress="autoCommit(event)" maxlength="4" style="width:100px;"/>&nbsp;&nbsp;
					                <img style="border: 1px dashed; cursor: pointer" width="60" height="20" id="validateImage" alt="" title="看不清楚? 点击换一张" src="validateImage.do" onclick="refreshCode();" />&nbsp;<a href="javascript: refreshCode();">看不清？换一张</a>
				                </div>
				            </div>
				            <div class="formbox">
				                <div class="formleft">角色</div>
				                <div class="formright">
				                  <select id="roleId" name="roleId" class="select_width"></select>
				                </div>
				            </div>
				              
				            <div class="formbox">
				                <div class="formleft"></div>
				                <div class="formbtbox">
				                	<%-- 
					                	<input class="formbt" onclick="login()" onFocus="this.blur()" type="image" src="images/enter.gif" title="点击登录系统" align="middle" border="0" />&nbsp;
				                	--%>
				                	<img class="formbt" id="id_enter" src="images/enter.gif" alt="" onFocus="this.blur()" onclick="login()" 
				                		title="点击登录系统" align="middle" border="0" style="cursor: hand"/>
				                	<%--
					                	<f:link href="/cardholder/cardholderLogin.jsp">持卡人登录</f:link>
				                	--%> 
				                </div>
				            </div>
				              
				            <div class="formbox">
					            <div class="formleft"></div>
						        <div style="padding-top:20px; width: 280px">
						        	<div>
						          	<img src="images/down.gif" width="14px" height="15" /> 
						          	<a style="text-decoration:underline" href="javascript:void(0);" onclick="return CheckUSBKey()">证书登录</a> 
						          	<img style="padding-left:30px;" src="images/down.gif" width="14px" height="15" /> 
						          	<a style="text-decoration:underline;" href="activeX/ePass2001-SimpChinese.zip" title="下载安全插件">下载安全插件</a>
						          	</div>
						        </div>
						        
					        	<input type="hidden" id="serialNo" name="serialNo" value=""/>
					        	<input type="hidden" id="mySign" name="mySign" />
						        <input type="hidden" id="message" value="${message}"/>
				        	</div>
				        </form>
		            </div>
	            </td>
	          </tr>
	        </table></td>
	      </tr>
	      <%-- 信息通知  --%>
		  <div id="id_Notice" class="notice_div">
			<h1 class="notice_title" id="pubTitle"></h1>
			<div class="notice_info">
				<span id="pubDate"></span>&nbsp;&nbsp;
				<span id="pubUser"></span>
			</div>
			<p class="notice_content" id="pubContent">
			</p>
		 </div>
	</table>
	<jsp:include flush="true" page="/common/certJsIncluded.jsp"></jsp:include>

    <%-- 登陆口底部 --%>
	<div class="footer">
		<div class="footshadow"></div>
		<div class="foottext">
		<div class="textleft">建议使用IE6.0版本以上浏览器+1024分辨率获得最佳操作体验</div>
		<%-- 去掉版权信息 
		<div class="textright">版权所有&copy;<a href="http://www.gnete.com" title="访问银联网络官方网站" target="_blank">广州银联网络支付有限公司</a> 全国客服热线：4008-333-222</div>
		--%>
		</div>
	</div>
</body>
</html>

