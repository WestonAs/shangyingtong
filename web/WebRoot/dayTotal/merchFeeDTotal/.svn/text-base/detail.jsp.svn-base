<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base target="_self" />
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>${ACT.name}</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<!-- 发卡机构与商户手续费明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>发卡机构与商户手续费详细信息<span class="caption_title"></span></caption>
			<tr>
				<td>清算日期</td>
				<td>${merchFeeDTotal.feeDate}</td>
				<td>机构代码</td>
				<td>${merchFeeDTotal.branchCode}</td>
				<td>机构名称</td>
				<td>${fn:branch(merchFeeDTotal.branchCode)}</td>
			</tr>
			<tr>
				<td>交易类型</td>
				<td>${merchFeeDTotal.transTypeName}</td>
				<td>交易笔数</td>
				<td>${merchFeeDTotal.transNum}</td>
				<td>日交易金额</td>
				<td>${fn:amount(merchFeeDTotal.amount)}</td>
			</tr>
			<tr>
				<td>收费金额/收费比例</td>
				<td>${fn:percentPre(merchFeeDTotal.feeRate, 4)}</td>
				<td>手续费计费方式</td>
				<td>${merchFeeDTotal.feeTypeName}</td>
				<td>计费周期</td>
				<td>${merchFeeDTotal.costCycle}</td>
			</tr>
			<tr>
				<td>商户号</td>
				<td>${merchFeeDTotal.merchId}</td>
				<td>商户名称</td>
				<td>${fn:merch(merchFeeDTotal.merchId)}</td>
				<td>商户分润比率</td>
				<td>${fn:percentPre(merchFeeDTotal.shareFeeRate, 4)}</td>
			</tr>
			<tr>
				<td>商户交易币种</td>
				<td>${merchFeeDTotal.curCode}</td>
				<td>商户组号</td>
				<td>${merchFeeDTotal.groupId}</td>
				<td>商户应付手续费金额</td>
				<td>${fn:amount(merchFeeDTotal.merchPayFee)}</td>
			</tr>
			<tr>
				<td>商户代理商编号</td>
				<td>${merchFeeDTotal.merchProxy}</td>
				<td>商户代理商名称</td>
				<td>${fn:branch(merchFeeDTotal.merchProxy)}</td>
				<td>商户代理商分润金额</td>
				<td>${fn:amount(merchFeeDTotal.merchProxyFee)}</td>
			</tr>
			<tr>
				<td>卡BIN</td>
				<td>${merchFeeDTotal.cardBin}</td>
				<td>运营中心应收手续费</td>
				<td>${fn:amount(merchFeeDTotal.centerFee)}</td>
				<td>售卡代理商返利</td>
				<td>${fn:amount(merchFeeDTotal.saleProxyFee)}</td>
			</tr>
			<tr>
				<td>更新用户名</td>
				<td>${merchFeeDTotal.updateBy}</td>
				<td>更新时间</td>
				<td><s:date name="merchFeeDTotal.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td></td>
				<td></td>
			</tr>
		</table>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
		<b><s:label>交易笔数明细 </s:label></b>
		<form id="detailForm" method="post" action="detail.do?merchFeeDTotal.feeDate=${merchFeeDTotal.feeDate}&merchFeeDTotal.branchCode=${merchFeeDTotal.branchCode}&merchFeeDTotal.merchId=${merchFeeDTotal.merchId}&merchFeeDTotal.cardBin=${merchFeeDTotal.cardBin}&merchFeeDTotal.transType=${merchFeeDTotal.transType}">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">系统跟踪号</td>
			   <td align="center" nowrap class="titlebg">清算日期</td>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">卡Bin</td>
			   <td align="center" nowrap class="titlebg">交易类型</td>
			   <td align="center" nowrap class="titlebg">商户</td>
			   <td align="center" nowrap class="titlebg">商户应付手续费</td>
			   <td align="center" nowrap class="titlebg">终端号</td>
			   <td align="center" nowrap class="titlebg">交易金额</td>
			   <td align="center" nowrap class="titlebg">清算金额</td>
			   <td align="center" nowrap class="titlebg">处理状态</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td style="display: none">${transSn}</td>
			  <td align="center" nowrap>${sysTraceNum}</td>
			  <td align="center" nowrap>${settDate}</td>
			  <td align="center" nowrap>${cardId}</td>
			  <td align="center" nowrap>${fn:branch(cardIssuer)}(${cardIssuer})</td>		  
			  <td align="center" nowrap>${cardBin}</td>		  
			  <td align="center" nowrap>${transTypeName}</td>
			  <td align="center" nowrap>${fn:merch(merNo)}${fn:branch(merNo)}(${merNo})</td>	
			  <td align="center" nowrap>${fn:amount(merFee)}</td>	
			  <td align="center" nowrap>${termlId}</td>	
			  <td align="center" nowrap>${fn:amount(merPaidAmt)}</td>	
			  <td align="center" nowrap>${fn:amount(settAmt)}</td>	
			  <td align="center" nowrap>${procStatusName}</td>		  
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
			</form>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>