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
				<caption>${ACT.name}<span class="caption_title"> | <f:link href="/intgratedService/wsBankCardBinding/wsBankVerBindingReg/list.do?goBack=goBack">返回列表</f:link></span></caption>
				<tr>
					<td>登记ID</td>
					<td>${wsBankVerBindingReg.bindingId}</td>
					<td>卡号</td>
					<td>${wsBankVerBindingReg.cardId}</td>
					<td>外部号码</td>
					<td>${wsBankVerBindingReg.extCardId}</td>
					<td>发卡机构</td>
					<td>${wsBankVerBindingReg.cardIssuer}-${fn:branch(wsBankVerBindingReg.cardIssuer)}</td>
				</tr>
				<tr>
					<td>绑定方式</td>
					<td>${wsBankVerBindingReg.setStyleName}</td>
					<td>开户行名称</td>
					<td>${wsBankVerBindingReg.bankName}</td>
					<td>开户行号</td>
					<td>${wsBankVerBindingReg.bankNo}</td>
					<td>账户地区</td>
					<td>${wsBankVerBindingReg.accAreaCode}</td>
			  	</tr>
				<tr>
					<td>银行账户类型</td>
					<td>
						<s:if test='wsBankVerBindingReg.bankaccttype=="1"'>对公账户</s:if>
						<s:if test='wsBankVerBindingReg.bankaccttype=="0"'>对私账户</s:if>
					</td>
					<td>账户户名</td>
					<td>${wsBankVerBindingReg.bankAcctName}</td>
					<td>账号</td>
					<td><f:mask value="${wsBankVerBindingReg.bankAcct}" maskLength="8"/></td>
					<td>身份证号</td>
					<td><f:mask value="${wsBankVerBindingReg.idcard}" maskLength="8"/></td>
			  	</tr>
				<tr>
					<td>开户手机号</td>
					<td><f:mask value="${wsBankVerBindingReg.mobile}" maskLength="4"/></td>
			  		<td>状态</td>
					<td>${wsBankVerBindingReg.statusName }</td>
					<td>更新时间</td>
					<td><s:date name="wsBankVerBindingReg.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td>更新用户名</td>
					<td>${wsBankVerBindingReg.updateUser}-${fn:user(wsBankVerBindingReg.updateUser) }</td>
				</tr>
				<tr>
			  		<td>备注</td>
					<td>${wsBankVerBindingReg.remark }</td>
			  		<td>处理结果描述</td>
					<td colspan="11">${wsBankVerBindingReg.postScript }</td>
				</tr>
			</table>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>