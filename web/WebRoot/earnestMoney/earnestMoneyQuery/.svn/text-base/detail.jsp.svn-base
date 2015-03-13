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
			<caption>机构准备金详细信息<span class="caption_title"> | <f:link href="/earnestMoney/earnestMoneyQuery/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>机构编号</td>
				<td>${earnestMoney.branchCode}</td>
				<td>资产情况</td>
				<td>${earnestMoney.property}</td>
		  	</tr>
		  	<tr>
				<td>负债情况</td>
				<td>${earnestMoney.debt}</td>
				<td>风险等级</td>
				<td>${earnestMoney.riskLevel}</td>
		  	</tr>
		  	<tr>
				<td>预警线</td>
				<td>${earnestMoney.warnAmt}</td>
				<td>累积缴纳风险金金额</td>
				<td>${earnestMoney.sumRiskAmt}</td>
		  	</tr>
		  	<tr>
				<td>发卡资金总额</td>
				<td>${earnestMoney.sumCardAmt}</td>
				<td>基准额度</td>
				<td>${earnestMoney.datumAmt}</td>
		  	</tr>
		  	<tr>
				<td>风险准备金余额</td>
				<td>${earnestMoney.remainRiskAmt}</td>
				<td>风险准备金率</td>
				<td>${earnestMoney.riskRate}</td>
		  	</tr>
		  	<tr>
				<td>可用发卡金额</td>
				<td>${earnestMoney.remainUseAmt}</td>
				<td>已发卡资金账户余额</td>
				<td>${earnestMoney.usedAmt}</td>
		  	</tr>
		  	<tr>
				<td>未清算资金总额</td>
				<td>${earnestMoney.noSetAmt}</td>
				<td>状态</td>
				<td>${earnestMoney.statusName}</td>
		  	</tr>
		  	<tr>
				<td>备注</td>
				<td>${earnestMoney.memo}</td>
				<td>更新用户名</td>
				<td>${earnestMoney.updateBy}</td>
		  	</tr>
		  	<tr>
				<td>更新时间</td>	
				<td>${earnestMoney.updateTime}</td>
				<td></td>
				<td></td>
		  	</tr>
		  	
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>