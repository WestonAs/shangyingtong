<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>${ACT.name}</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
  
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
			<div class="userbox">
			<table class="detail_grid" width="80%" border="1" cellspacing="0" cellpadding="1">
				<caption>${ACT.name}<span class="caption_title"> | <f:link href="/intgratedService/cardExtraInfo/list.do?goBack=goBack">返回列表</f:link></span></caption>
				<tr>
					<td>卡号</td>	
					<td>${cardExtraInfo.cardId}</td>	
					<td>持卡人姓名</td>
					<td>${cardExtraInfo.custName}</td>
				</tr>
				<tr>
					<td>证件类型</td>
					<td>${cardExtraInfo.credTypeName}</td>	
					<td>证件号码</td>	
					<td>${cardExtraInfo.credNo}</td>	
				</tr>
				<tr>
					<td>证件有效期</td>
					<td>${cardExtraInfo.credNoExpiryDate}</td>	
					<td>职业</td>	
					<td>${cardExtraInfo.career}</td>	
				</tr>
				<tr>
					<td>国籍</td>
					<td>${cardExtraInfo.nationality}</td>	
					<td></td>	
					<td></td>	
				</tr>
				<tr>
					<td>联系地址</td>
					<td>${cardExtraInfo.address}</td>	
					<td>联系电话</td>	
					<td>${cardExtraInfo.telNo}</td>	
				</tr>
				<tr>
					<td>手机号</td>	
					<td>${cardExtraInfo.mobileNo }</td>	
					<td>邮件地址</td>
					<td>${cardExtraInfo.email}</td>	
				</tr>
				<tr>
					<td>是否开通短信通知</td>	
					<td>${cardExtraInfo.smsFlagName}</td>	
					<td>生日</td>
					<td>${cardExtraInfo.birthday}</td>	
				</tr>
				<tr>
					<td>密码失效时间</td>
					<td><s:date name = "cardExtraInfo.pwdExpirTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td>购卡客户ID</td>
					<td>${cardExtraInfo.cardCustomerId}</td>
				</tr>
				<tr>
					<td>备注</td>
					<td colspan="3">${cardExtraInfo.remark}</td>	
				</tr>
				<tr>
					<td>更新用户名</td>
					<td>${cardExtraInfo.updateBy}</td>	
					<td>更新时间</td>
					<td><s:date name = "cardExtraInfo.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>	
				</tr>
			</table>
			</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>
