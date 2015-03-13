<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>银联网络储值卡系统</title>
		
		<f:css href="/css/page.css"/>
		<f:css href="/css/screen.css"/>
		<f:js src="/js/jquery.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
	<center>
		<table width="952" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    	<td width="27%" valign="top">
    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
      	<tr>
        <td>
        <div class="userbox">
		<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
		<div class="contentb">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center">
            <table width="233" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr>
                <td colspan="2" height="6"></td>
              </tr>
              <tr>
                <td colspan="2" align="center">
                <table width="94%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="100%">
                    <table width="94%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td align="left" >欢迎您：</td>
                      </tr>
                	  <tr>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td align="left">${cardId} </td>
                      </tr>
                      <tr>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td align="right">
                        <a href="../cardholder/cardholderLogin.jsp" >退出登录</a></td>
                      </tr>
                    </table>
                    </td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
          </tr>
                <tr>
                  <td colspan="2" align="center">
                  <table width="216" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="20"></td>
                    </tr>
						<!-- 列表 start -->
						<tr>
							<td valign="middle" bgcolor="#ff0000" height="25" 
								onmouseover="this.bgColor = '#999999';"onmouseout="this.bgColor='#ff0000';"><a target="_cardserviceContent"
								href="../cardholder/queryBalance.do?cardId=${cardId}">
								余额查询</a></td>
						</tr>	
						<tr>
							<td></td>
						</tr>
						<tr>
							<td valign="middle" bgcolor="#ff0000" height="25" 
								onmouseover="this.bgColor = '#999999';"onmouseout="this.bgColor='#ff0000';">
								<a target="_cardserviceContent"
								href="../cardholder/queryTrans.do?cardId=${cardId}">交易查询</a></td>
						</tr>	
						<tr>
							<td></td>
						</tr>
						<tr>
							<td valign="middle" bgcolor="#ff0000" height="25" 
								onmouseover="this.bgColor = '#999999';"onmouseout="this.bgColor='#ff0000';"><a target="_cardserviceContent"
								href="../cardholder/showChangePassword.do?cardId=${cardId}">
								修改密码</a></td>
						</tr>
						<!-- 列表 end -->                    
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </table></td>
                </tr>
            </table>
            </div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
            </td>
          </tr>
          <tr>
            <td align="center">&nbsp;</td>
          </tr>
        </table>
        </td>
        <td width="72%" valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="center" valign="top" ><table width="657" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td colspan="3"  align="left">
					<!-- 引入内容  -->
					<iframe name="_cardserviceContent" src="loginResult.jsp" 
							width="100%" marginwidth="0" marginheight="0" hspace="0"
							vspace="0" frameborder="0" scrolling="no" 
							onload="this.height=_cardserviceContent.document.body.scrollHeight" />
                    </td>
                  </tr>

                </table></td>
              </tr>
              <tr>
                <td align="center" height="60">&nbsp;</td>
              </tr>
            </table></td>
          </tr>
        </table>
        </td>
      </tr>
    </table>
</center>
	</body>
</html>