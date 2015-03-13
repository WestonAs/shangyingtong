<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
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
		
		<!-- 风险保证金参数信息明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>风险保证金参数详细信息<span class="caption_title"> | <f:link href="/earnestMoney/earnestMoneyReg/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>机构编号</td>
				<td>${earnestMoney.branchCode}</td>
				<td>资产情况</td>
				<td>${fn:amount(earnestMoney.property)}</td>
				<td>负债情况</td>
				<td>${fn:amount(earnestMoney.debt)}</td>
			</tr>
			<tr>
				<td>风险等级</td>
				<td>${cardTypeCode.riskLevel}</td>
				<td>预警线</td>
				<td>${fn:amount(earnestMoney.warnAmt)}</td>
				<td>累计交纳风险金金额</td>
				<td>${fn:amount(earnestMoney.sumRiskAmt)}</td>
			</tr>
			<tr>
				<td>发卡资金总额</td>
				<td>${fn:amount(earnestMoney.sumCardAmt)}</td>
				<td>基准额度</td>
				<td>${fn:amount(earnestMoney.datumAmt)}</td>
				<td>风险准备金余额</td>
				<td>${fn:amount(earnestMoney.remainRiskAmt)}</td>
			</tr>	
			<tr>
				<td>风险准备金率</td>
				<td>${earnestMoney.riskRate}</td>
				<td>可用发卡金额</td>
				<td>${fn:amount(earnestMoney.remainUseAmt) }</td>
				<td>已发卡资金帐户余额</td>
				<td>${fn:amount(earnestMoney.usedAmt) }</td>
			</tr>	
			<tr>
				<td>未清算资金总额</td>
				<td>${fn:amount(earnestMoney.noSetAmt)}</td>
				<td>状态</td>
				<td>${earnestMoney.statusName}</td>
				<td>备注</td>
				<td>${earnestMoney.memo}</td>
			</tr>	
			<tr>
				<td>更新用户名</td>
				<td>${earnestMoney.updateBy}</td>
				<td>更新时间</td>
				<td>
				<s:date name="earnestMoney.updateTime" format="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td></td>
				<td></td>
			</tr>				 
		</table>
		</div>

		<!-- 调整风险保证金记录信息明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>调整风险保证金记录详细信息<span class="caption_title"> </span></caption>
			<tr>
				<td>申请登记编号</td>
				<td>${riskMarginReg.earnestRegId}</td>
				<td>机构编号</td>
				<td>${riskMarginReg.branchCode}</td>
				<td>机构名称</td>
				<td>${riskMarginReg.branchName}</td>
			</tr>
			<tr>
				<td>保证金余额</td>
				<td>${fn:amount(riskMarginReg.remainRiskAmt)}</td>
				<td>调整方向</td>
				<td>${riskMarginReg.adjDirectionName}</td>
				<td>调整金额</td>
				<td>${fn:amount(riskMarginReg.adjAmt)}</td>
			</tr>
			<tr>
				<td>状态</td>
				<td>${riskMarginReg.statusName}</td>
				<td>更新用户名</td>
				<td>${riskMarginReg.updateBy}</td>
				<td>更新时间</td>
				<td>
				<s:date name="riskMarginReg.updateTime" format="yyyy-MM-dd HH:mm:ss" />
		        </td>	
			</tr>

		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>