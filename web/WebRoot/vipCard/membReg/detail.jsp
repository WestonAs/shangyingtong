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
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>会员注册资料详细信息<span class="caption_title"> | <f:link href="/vipCard/membReg/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>注册编号</td>
				<td>${membReg.membRegId}</td>
				<td>客户名称</td>
				<td>${membReg.custName}</td>
				<td>卡号</td>
				<td>${membReg.cardId}</td>
		  	</tr>
			<tr>
				<td>发卡机构</td>
				<td>${membReg.cardIssuer}</td>
				<td>会员类型</td>
				<td>${membReg.membClass}</td>
				<td></td>
				<td></td>
		  	</tr>
		  	<tr>
				<td>性别</td>
				<td>${membReg.sexName}</td>
				<td>年龄</td>
				<td>${membReg.age}</td>
				<td>学历</td>
				<td>${membReg.educationName}</td>
			</tr>
		  	<tr>
				<td>证件类型</td>
				<td>${membReg.credTypeName}</td>
				<td>证件号码</td>
				<td>${membReg.credNo}</td>
				<td>联系地址</td>
				<td>${membReg.address}</td>
		  	</tr>
		  	 <tr>
				<td>联系电话</td>
				<td>${membReg.telNo}</td>
				<td>手机号</td>
				<td>${membReg.mobileNo}</td>
				<td>邮件地址</td>
				<td>${membReg.email}</td>
		  	</tr>
		  	<tr>
				<td>行业</td>
				<td>${membReg.job}</td>
				<td>月收入</td>
				<td>${membReg.salary}</td>
				<td>生日</td>
				<td>${membReg.birthday}</td>
		  	</tr>
			<s:if test='@gnete.card.service.mgr.BranchBizConfigCache@isNeedMembBankAcctInfo(membReg.cardIssuer)'>
			  	<tr>
					<td>开户行号</td>
					<td>${membReg.bankNo}</td>
					<td>开户行名称</td>
					<td>${membReg.bankName}</td>
					<td>账户户名</td>
					<td>${membReg.accName}</td>
			  	</tr> 	
			  	<tr>
					<td>账号</td>
					<td>${membReg.accNo}</td>
					<td>账户地区</td>
					<td>${formMap.accAreaName}</td>
					<td>账户类型</td>
					<td>${membReg.acctTypeName}</td>
			  	</tr> 	
			  	<tr>
					<td>账户介质类型</td>
					<td>${membReg.acctMediaTypeName}</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
			  	</tr> 	
		  	</s:if>
		  	<tr>
				<td>登记日期</td>
				<td><s:date name="membReg.regTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>更新用户名</td>
				<td>${membReg.updateBy}</td>
		  		<td>更新时间</td>
				<td><s:date name="membReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>

		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>