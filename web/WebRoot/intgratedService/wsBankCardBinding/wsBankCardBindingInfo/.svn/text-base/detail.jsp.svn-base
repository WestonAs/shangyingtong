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
		<style type="text/css">
			html { overflow-y: scroll; }
			#tranEnableDiv table table .headcell { text-align: right; }
		</style>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
				<caption>${ACT.name}<span class="caption_title"> | <f:link href="/intgratedService/wsBankCardBinding/wsBankCardBindingInfo/list.do?goBack=goBack">返回列表</f:link></span></caption>
				<tr>
					<td>登记ID</td>
					<td>${wsBankCardBindingInfo.seqId}</td>
					<td>卡号</td>
					<td>${wsBankCardBindingInfo.cardId}</td>
					<td>外部号码</td>
					<td>${wsBankCardBindingInfo.externalCardId}</td>
					<td>发卡机构</td>
					<td>${wsBankCardBindingInfo.cardIssuer}-${fn:branch(wsBankCardBindingInfo.cardIssuer)}</td>
				</tr>
				<tr>
					<td>绑定状态</td>
					<td>
						<s:if test='wsBankCardBindingInfo.bindingStatus=="1"'>绑定</s:if>
						<s:if test='wsBankCardBindingInfo.bindingStatus=="0"'>已解绑</s:if>
					</td>
					<td>是否默认卡</td>
					<td>
						<s:if test='wsBankCardBindingInfo.isDefault=="1"'>是</s:if>
						<s:if test='wsBankCardBindingInfo.isDefault=="0"'>否</s:if>
					</td>
					<td>开户行名称</td>
					<td>${wsBankCardBindingInfo.bankName}</td>
					<td>开户行号</td>
					<td>${wsBankCardBindingInfo.elecbankno}</td>
			  	</tr>
				<tr>
					<td>账户地区</td>
					<td>${wsBankCardBindingInfo.accAreaCode}</td>
					<td>银行账户类型</td>
					<td>
						<s:if test='wsBankCardBindingInfo.bankaccttype=="1"'>对公账户</s:if>
						<s:if test='wsBankCardBindingInfo.bankaccttype=="0"'>对私账户</s:if>
					</td>
					<td>账户户名</td>
					<td>${wsBankCardBindingInfo.bankAcctName}</td>
					<td>账号</td>
					<td><f:mask value="${wsBankCardBindingInfo.bankCard}" maskLength="8"/></td>
			  	</tr>
				<tr>
					<td>身份证号</td>
					<td><f:mask value="${wsBankCardBindingInfo.credno}" maskLength="8"/></td>
					<td>开户手机号</td>
					<td><f:mask value="${wsBankCardBindingInfo.phoneNum}" maskLength="4"/></td>
			  		<td>备注</td>
					<td colspan="11">${wsBankCardBindingInfo.remark }</td>
				</tr>
			</table>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>