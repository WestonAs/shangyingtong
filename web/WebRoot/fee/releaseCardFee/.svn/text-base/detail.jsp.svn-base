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
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/fee/releaseCardFee/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>分支机构</td>
				<td>${releaseCardFee.chlCode}-${fn:branch(releaseCardFee.chlCode)}</td>
				<td>发卡机构</td>
				<td>${releaseCardFee.branchCode}-${fn:branch(releaseCardFee.branchCode)}</td>
			</tr>
			<tr>
				<td>商户标志</td>
				<td><s:if test="releaseCardFee.merchFlag==0">商户</s:if>
				<s:elseif test="releaseCardFee.merchFlag==1">商户组</s:elseif>
				<td>商户(组)</td>
				<td><s:if test="releaseCardFee.merchId == 0">通用商户</s:if>
				<s:else>${releaseCardFee.merchId}-${fn:merch(releaseCardFee.merchId)}</td></s:else>
			</tr>
			<tr>
				<td>交易类型</td>
				<td>${releaseCardFee.transTypeName}</td>
				<td>费率规则</td>
				<td>${releaseCardFee.feeRuleId}</td>
			</tr>
			<tr>
				<td>发卡机构币种</td>
				<td>${releaseCardFee.curCode}</td>
				<td>卡BIN</td>
				<td>${releaseCardFee.cardBin}</td>
			</tr>
			<tr>
				<td>计费方式</td>
				<td>${releaseCardFee.feeTypeName}</td>
				<td>计费周期</td>
				<td>${releaseCardFee.costCycleName}</td>
			</tr>
			<tr>
				<td>金额段</td>
				<td>${releaseCardFee.ulMoney}</td>
				<td>费率</td>
				<td>${releaseCardFee.feeRate}</td>
			</tr>
			<tr>
				<td>单笔最高手续费</td>
				<td>${releaseCardFee.maxAmt}</td>
				<td>单笔最低手续费</td>
				<td>${releaseCardFee.minAmt}</td>
			</tr>
			<tr>
				<td>计费周期类型</td>
				<td>${releaseCardFee.feeCycleTypeName}</td>
				<td>调整周期类型</td>
				<td>${releaseCardFee.adjustCycleTypeName}</td>
			</tr>
			<tr>
				<td>调账月数</td>
				<td>${releaseCardFee.adjustMonths}</td>
				<td>计费原则</td>
				<td>${releaseCardFee.feePrincipleName}</td>
			</tr>
			<tr>
				<td>状态</td>
				<td>${releaseCardFee.statusName}</td>
				<td>触发日期</td>
				<td>${releaseCardFee.feeDate}</td>
			</tr>
			<tr>
				<td>生效日期</td>
				<td>${releaseCardFee.effDate}</td>
				<td>失效日期</td>
				<td>${releaseCardFee.expirDate}</td>
			</tr>
			<tr>
				<td>更新用户名</td>	
				<td>${releaseCardFee.updateBy}</td>
				<td>操作时间</td>
				<td><s:date name = "releaseCardFee.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>	
			</tr>
			
		</table>
		</div>

		<s:if test="showRelaseCardFeeDtl">
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<caption>相关分段金额详细信息</caption>	
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">费率规则</td>
			   <td align="center" nowrap class="titlebg">金额段</td>
			   <td align="center" nowrap class="titlebg">费率</td>
			</tr>
			</thead>
		
			<s:iterator value="releaseCardFeeDtlPage.data"> 
			<tr>		
			  <td align="center" nowrap>${feeRuleId}</td>
			  <td align="right" nowrap>${fn:amount(ulMoney)}</td>
			  <td align="right" nowrap>${fn:amount(feeRate)}</td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="releaseCardFeeDtlPage"/>
		</div>
		</s:if>

		<s:if test="showFeeCycleStage">
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<caption>计费周期分期详细信息</caption>	
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">费率规则</td>
			   <td align="center" nowrap class="titlebg">计费周期起始日</td>
			   <td align="center" nowrap class="titlebg">计费周期结束日</td>
			</tr>
			</thead>
		
			<s:iterator value="feeCycleStagePage.data"> 
			<tr>		
			  <td align="center" nowrap>${feeRuleId}</td>
			  <td align="right" nowrap>${feeBeginDate}</td>
			  <td align="right" nowrap>${feeEndDate}</td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="feeCycleStagePage"/>
		</div>
		</s:if>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>