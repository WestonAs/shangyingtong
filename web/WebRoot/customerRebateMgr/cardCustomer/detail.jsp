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
			<caption>购卡客户详细信息<span class="caption_title"> | <f:link href="/customerRebateMgr/cardCustomer/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>购卡客户ID</td>
				<td>${cardCustomer.cardCustomerId}</td>
				<td>购卡客户名称</td>
				<td>${cardCustomer.cardCustomerName}</td>
				<td>发卡机构</td>
				<td>${cardCustomer.cardBranch}-${fn:branch(cardCustomer.cardBranch)}</td>
			</tr>
		  	<tr>
				<td>返利方式</td>
				<td>${cardCustomer.rebateTypeName}</td>
				<td>返利卡号</td>
				<td>${cardCustomer.rebateCard}</td>
		  		<td>团购卡号</td>
				<td>${cardCustomer.groupCardId}</td>
			</tr>
		  	<tr>
				<td>开户行名</td>
				<td>${cardCustomer.bank}</td>
				<td>开户行号</td>
				<td>${cardCustomer.bankNo}</td>
		  		<td>银行账号</td>
				<td>${cardCustomer.bankAccNo}</td>
			</tr>
		  	<tr>
				<td>营业执照</td>
				<td>${cardCustomer.license}</td>
				<td>组织机构代码</td>
				<td>${cardCustomer.organization}</td>
		  		<td>国/地税号</td>
				<td>${cardCustomer.taxNo}</td>
			</tr>
		  	<tr>
				<td>单位地址</td>
				<td>${cardCustomer.companyAddress}</td>
		  		<td>单位经营范围</td>
				<td>${cardCustomer.companyBusinessScope}</td>
		  		<td>风险等级</td>
				<td>${cardCustomer.riskLevelDesc}</td>
			</tr>
		  	<tr>
				<td>经办人</td>
				<td>${cardCustomer.contact}</td>
				<td>证件类型</td>
				<td>${cardCustomer.credTypeName}</td>
				<td>证件号码</td>
				<td>${cardCustomer.credNo}</td>
			</tr>
		  	<tr>
				<td>证件有效期</td>
				<td>${cardCustomer.credNoExpiryDate}</td>
				<td>职业</td>
				<td>${cardCustomer.career}</td>
				<td>国籍</td>
				<td>${cardCustomer.nationality}</td>
			</tr>
			<tr>
		  		<td>地址</td>
				<td>${cardCustomer.address}</td>
				<td>邮编</td>
				<td>${cardCustomer.zip}</td>
				<td>联系电话</td>
				<td>${cardCustomer.phone}</td>
			</tr>
		  	<tr>
				<td>传真号</td>
				<td>${cardCustomer.fax}</td>
		  		<td>Email</td>
				<td>${cardCustomer.email}</td>
				<td></td>
				<td></td>
			</tr>
		  	<tr>
				<td>状态</td>
				<td>${cardCustomer.statusName}</td>
				<td>更新人</td>
				<td>${cardCustomer.updateUser}</td>
		  		<td>更新时间</td>
				<td><s:date name="cardCustomer.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>