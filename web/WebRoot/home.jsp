<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>

		<f:css href="/css/page.css"/>
	</head>

	<body>
		<div class="location">
			您当前所在位置：<span class="redlink"><a href="javascript:void(0);">首页</a></span>
		</div>
		
		<!-- 登陆成功提示区 -->
		<div class="okbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<div style="height:30px; padding:15px 15px 25px 15px; overflow:hidden;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="52" align="center" valign="top">
								<img src="images/icon14.gif" width="37" height="41" />
							</td>
							<td>
								<p style="font-size:14px; font-weight:bold;">
									系统登录提示
								</p>
								<p style="text-indent:4em; line-height:20px;">
									尊敬的用户：
									<c:choose>
										<c:when test="${SESSION_USER.certUser && SESSION_USER.userCertificate.daysToExpire<=30 && SESSION_USER.userCertificate.daysToExpire>=0}">
											<span class="redfont" style="font-weight: bold;">你绑定的证书还有 ${SESSION_USER.userCertificate.daysToExpire}天 即将过期，请尽快请联系相关部门更新证书！</span>
											<script type="text/javascript">
												alert("尊敬的用户：你绑定的证书还有 ${SESSION_USER.userCertificate.daysToExpire}天 即将过期，请尽快请联系相关部门更新证书！")
											</script>
										</c:when>
										<c:when test="${!SESSION_USER.certUser && SESSION_USER.initPwd}">
											<span class="redfont" style="font-weight: bold;">你的初始登录密码未修改，为了账户安全，请 <a href="${CONTEXT_PATH}/pages/user/showModifyPass.do">修改登录密码</a>！</span>
											<script type="text/javascript">
												alert("尊敬的用户：你的初始登录密码未修改，为了账户安全，请先修改登录密码！")
											</script>
										</c:when>
										<c:when test="${!SESSION_USER.certUser && SESSION_USER.daysOfPwdChanged>90}">
											<span class="redfont" style="font-weight: bold;">你的登录密码已经${SESSION_USER.daysOfPwdChanged}天（超过90天）未修改，为了账户安全，请 <a href="${CONTEXT_PATH}/pages/user/showModifyPass.do">修改登录密码</a>！</span>
											<script type="text/javascript">
												alert("尊敬的用户：你的登录密码已经${SESSION_USER.daysOfPwdChanged}天（超过90天）未修改，为了账户安全，请先修改登录密码！")
											</script>
										</c:when>
										<c:otherwise>
											欢迎使用预付卡（商盈通）管理系统。
										</c:otherwise>
									</c:choose>
								</p>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<%-- 角色流程帮助区 --%>
		<%--
		<div class="processtitle">
			<div class="processtitleimg"><img src="images/icon15.gif" width="37" height="41" /></div>
			<div class="processtitletx">积分卡产品介绍</div>
		</div>
		
		<div class="processbox">
			<div>
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<div style="padding:15px 15px 15px 15px; overflow:hidden;">
					<table width="100%" height="100%" border="0" cellspacing="10" cellpadding="0">
					  <tr>
					    <td valign="middle"><img src="images/img.jpg" width="110" height="112" /></td>
					    <td valign="middle" ><p style=" text-indent:2em; line-height:20px;">积分卡产品是为广大商户提供的积分消费业务系统，整个系统包括终端、积分卡管理系统和积分卡交易处理系统三个部分，能够在商户的终端上实现积分交易功能。终端只需在原有设备基础上对应用程序进行简单改造，交易处理系统直接与增值平台相连。商户可以根据需求和业务范围使用单体发卡和第三方发卡等几种不同的业务方式，系统将提供完善的支付和清算服务，协助企业实现积分消费功能。</p>
					      <p style=" text-indent:2em; line-height:20px;">积分卡是商家稳定客户来源的良好方式，特别大型商场，如零售业，百货类，饮食类，消费品类等行业，是一种回馈忠诚客户的首选手段。我司积分卡系统已相当完善，成功案例丰富，能配合商家实际情况迅速开展业务。</p></td>
					   </tr>
					</table>
					</div>
				</div>
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
		</div>
		<div class="processbox" style="padding-top: 10px;">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb" align="center">
				<img src="images/home.jpg" border="0"/>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		--%>
	</body>
</html>
