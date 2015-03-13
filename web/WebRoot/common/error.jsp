<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.SQLException"%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		
		<title>预付卡系统</title>
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<script>
			$(function(){
				window.parent.unblock();
			});
		</script>
	</head>

	<body class="fullwidth">
	    <div class="location">您当前所在位置： <span class="redlink"><a href="<%=_contextPath%>/home.jsp">首页</a></span> &gt; 系统提示 &gt; 操作失败</div>

		<!-- 操作结果提示区 -->
		<div class="userbox"><div>
		<b class="b1"></b>
		<b class="b2"></b>
		<b class="b3"></b>
		<b class="b4"></b>
		<div class="contentb">
		<table width="100%" border="0" cellspacing="5" cellpadding="0">
		  <tr>
		    <td width="200" rowspan="3" align="center" valign="middle"><img src="<%=_contextPath%>/images/error.gif" width="158" height="141" /></td>
		    <td width="100" height="30"><span style="font-size:14px; font-weight:bold; padding-bottom:10px;">操作失败信息</span></td>
		    <td height="30" colspan="3">&nbsp;</td>
		  </tr>
		  <tr>
		    <td height="60" colspan="4" align="left" valign="middle">
		    	${exception.message}
		    	<s:if test="hasActionErrors()"><s:iterator value="actionErrors"><s:property escape="false"/><br/></s:iterator></s:if>
		    </td>
		  </tr>
		  <tr>
		    <td height="30" colspan="4">
		      <!-- input type="submit" value=" 确 定 " class="inp_L3" onmouseover="this.className='inp_L4'" onmouseout="this.className='inp_L3'" id="input_btn2"  name="ok" /-->
		      <input style="margin-left:30px;" type="submit" value=" 返 回 " class="inp_L3" onmouseover="this.className='inp_L4'" onmouseout="this.className='inp_L3'" id="input_btn3"  name="escape" onclick="window.history.back();"/>    </td>
		    </tr>
		  </table>   
		</div>
		<b class="b4"></b>
		<b class="b3"></b>
		<b class="b2"></b>
		<b class="b1"></b>	
		</div>
		
		</div>
		
		<!--版权区域-->
		<div class="bottom">
		    <div style="float:left; padding-left:5px; color:#767676;">建议使用IE6.0版本以上浏览器+1024分辨率获得最佳操作体验</div>
		    <!-- 去掉版权信息 
		    <div style="float:right; padding-right:5px;color:#767676;">版权所有&copy;<a href="http://www.gnete.com" title="访问银联网络官方网站" target="_blank" style="color:#767676;">广州银联网络支付有限公司</a> 全国客服热线：4008-333-222</div>
		    -->
		</div>
	</body>
</html>
