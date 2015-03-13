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
			<caption>IC卡电子现金充值明细信息<span class="caption_title"> | <f:link href="/pages/icEcashDeposit/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>充值批次ID</td>
				<td>${icEcashDepositReg.depositBatchId}</td>
				<td>卡号</td>
				<td>${icEcashDepositReg.cardId}</td>
				<td>卡种类</td>
				<td>${icEcashDepositReg.cardClassName}</td>
			</tr>
		  	<tr>
		  		<td>发卡机构编号</td>
				<td>${icEcashDepositReg.cardBranch}</td>
				<td>发卡机构名</td>
				<td>${fn:branch(icEcashDepositReg.cardBranch)}</td>
				<td>卡类型号</td>
				<td>${icEcashDepositReg.cardSubClass}</td>
			</tr>
			<tr>
				<td>充值机构名</td>
				<td>${fn:branch(icEcashDepositReg.depositBranch)}</td>
				<td>充值机构号</td>
				<td>${icEcashDepositReg.depositBranch}</td>
				<td>上期余额</td>
				<td>${fn:amount(icEcashDepositReg.lastBalance)}</td>
			</tr>
		  	<tr>
		  		<td>充值金额</td>
				<td><span class="bigred">${fn:amount(icEcashDepositReg.depositAmt)}</span></td>
				<td>余额上限</td>
				<td>${fn:amount(icEcashDepositReg.balanceLimit)}</td>
				<td>状态</td>
				<td>${icEcashDepositReg.statusName}</td>
			</tr>
		  	<tr>
		  		<td>是否写卡成功</td>
				<td>${icEcashDepositReg.writeCardFlagName}</td>
				<td>冲正标志</td>
				<td>${icEcashDepositReg.reversalFlagName}</td>
				<td>充值工作日</td>
				<td>${icEcashDepositReg.depositDate}</td>
			</tr>
		  	<tr>
				<td>返利规则id</td>
				<td>${icEcashDepositReg.rebateId}</td>
				<td>预计返利金额</td>
				<td colspan="11"><span class="bigred">${fn:amount(icEcashDepositReg.rebateAmt)}</span></td>
			</tr>
		  	<tr>
		  		<td>更新人</td>
				<td>${icEcashDepositReg.updateUser}</td>
		  		<td>更新时间</td>
				<td><s:date name="icEcashDepositReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>备注</td>
		  		<td colspan="5">${icEcashDepositReg.remark}</td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>