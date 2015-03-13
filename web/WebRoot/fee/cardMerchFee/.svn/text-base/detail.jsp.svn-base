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
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/fee/cardMerchFee/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>发卡机构</td>
				<td>${fn:branch(cardMerchFee.branchCode)}</td>
				<td>交易类型</td>
				<td>${cardMerchFee.transTypeName}</td>
			</tr>
			<tr>
				<td>商户类型</td>
				<td>${cardMerchFee.merchFlag}</td>
				<td>商户(组)</td>
				<td>${fn:merch(cardMerchFee.merchId)}</td>
			</tr>
			<tr>
				<td>交易币种</td>
				<td>${cardMerchFee.curCode}</td>
				<td>卡BIN</td>
				<td>${cardMerchFee.cardBin}</td>
			</tr>
			<tr>
				<td>计费方式</td>
				<td>${cardMerchFee.feeTypeName}</td>
				<td>计费周期</td>
				<td>${cardMerchFee.costCycleName}</td>
			</tr>
			<tr>
				<td>费率规则</td>
				<td>${cardMerchFee.feeRuleId}</td>
				<td>金额段</td>
				<td>${cardMerchFee.ulMoney}</td>
			</tr>
			<tr>
				<td>费率</td>
				<td>${fn:amount(cardMerchFee.feeRate)}</td>
				<td>单笔最高手续费</td>
				<td>${fn:amount(cardMerchFee.maxAmt)}</td>
			</tr>
			<tr>
				<td>单笔最低手续费</td>
				<td>${fn:amount(cardMerchFee.minAmt)}</td>
				<td>更新用户名</td>	
				<td>${cardMerchFee.updateBy}</td>
			</tr>
			<tr>	
				<td>操作时间</td>
				<td colspan="3"><s:date name = "cardMerchFee.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>	
			</tr>
			
		</table>
		</div>

		<s:if test="showCardMerchFeeDtl">
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
		
			<s:iterator value="cardMerchFeeDtlPage.data"> 
			<tr>		
			  <td align="center" nowrap>${feeRuleId}</td>
			  <td align="right" nowrap>${fn:amount(ulMoney)}</td>
			  <td align="right" nowrap>${fn:amount(feeRate)}</td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="cardMerchFeeDtlPage"/>
		</div>
		</s:if>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>