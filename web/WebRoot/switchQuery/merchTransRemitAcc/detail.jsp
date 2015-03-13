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
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>商户划付明细信息<span class="caption_title"></span></caption>
			<tr>
				<td>划付流水</td>
				<td>${merchTransRmaDetail.rmaSn}</td>
				<td>划付日期</td>
				<td>${merchTransRmaDetail.rmaDate}</td>
			</tr>
			<tr>
				<td>付款方</td>
				<td>${merchTransRmaDetail.payCode}-${fn:branch(merchTransRmaDetail.payCode)}</td>
				<td>收款方</td>
				<td>${merchTransRmaDetail.recvCode}-${fn:merch(merchTransRmaDetail.recvCode)}${fn:branch(merchTransRmaDetail.recvCode)}</td>
			</tr>
			<tr>
				<td>付款账户</td>
				<td>${merchTransRmaDetail.payAccNo}</td>
				<td>付款户名</td>
				<td>${merchTransRmaDetail.payAccName}</td>
			</tr>
			<tr>
				<td>付款行号</td>
				<td>${merchTransRmaDetail.payBankNo}</td>
				<td>付款行行名</td>
				<td>${merchTransRmaDetail.payBankName}</td>
			</tr>
			<tr>
				<td>收款账户</td>
				<td>${merchTransRmaDetail.recvAccNo}</td>
				<td>收款户名</td>
				<td>${merchTransRmaDetail.recvAccName}</td>
			</tr>
			<tr>
				<td>收款行号</td>
				<td>${merchTransRmaDetail.recvBankNo}</td>
				<td>收款行行名</td>
				<td>${merchTransRmaDetail.recvBankName}</td>
			</tr>
			<tr>
				<td>付款方货币符</td>
				<td>${merchTransRmaDetail.curCode}</td>
				<td>划账方式</td>
				<td>${merchTransRmaDetail.xferTypeName}</td>
			</tr>
			<tr>
				<td>周期日期</td>
				<td>${merchTransRmaDetail.dayOfCycleName}</td>
				<td>金额上限</td>
				<td>${fn:amount(merchTransRmaDetail.ulMoney)}</td>
			</tr>
			<tr>
				<td>清算笔数</td>
				<td>${merchTransRmaDetail.transNum}</td>
				<td>清算金额</td>
				<td>${fn:amount(merchTransRmaDetail.amount)}</td>
			</tr>
			<tr>
				<td>手续费金额</td>
				<td>${fn:amount(merchTransRmaDetail.feeAmt)}</td>
				<td>划账金额</td>
				<td>${fn:amount(merchTransRmaDetail.rmaAmt)}</td>
			</tr>
			<tr>
				<td>更新时间</td>
				<td colspan="3"><s:date name="merchTransRmaDetail.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr>
				<td>备注</td>
				<td colspan="3"><s:date name="merchTransRmaDetail.remark" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			
		</table>
		</div>
		
		<form id="detailForm" method="post" action="detail.do?merchTransRmaDetail.payCode=${merchTransRmaDetail.payCode}&merchTransRmaDetail.recvCode=${merchTransRmaDetail.recvCode}&merchTransRmaDetail.curCode=${merchTransRmaDetail.curCode}&merchTransRmaDetail.rmaDate=${merchTransRmaDetail.rmaDate}">
		<!-- 交易结算日结算数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<caption>相关交易日结算明细</caption>	
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">划付流水</td>
			   <td align="center" nowrap class="titlebg">结算日期</td>
			   <td align="center" nowrap class="titlebg">交易类型</td>
			   <td align="center" nowrap class="titlebg">交易笔数</td>
			   <td align="center" nowrap class="titlebg">交易金额</td>
			   <td align="center" nowrap class="titlebg">上期结转金额</td>
			   <td align="center" nowrap class="titlebg">手续费金额</td>
			   <td align="center" nowrap class="titlebg">实收金额</td>
			   <td align="center" nowrap class="titlebg">结转下期金额</td>
			   <td align="center" nowrap class="titlebg">核销编号</td>
			   <td align="center" nowrap class="titlebg">核销状态</td>
			   <td align="center" nowrap class="titlebg">核销用户名</td>
			   <td align="center" nowrap class="titlebg">划付状态</td>
			   <td align="center" nowrap class="titlebg">更新时间</td>
			</tr>
			</thead>
			
			<s:iterator value="merchTransDSetPage.data"> 
			<tr>		
			  <td align="center" nowrap>${rmaSn}</td>
			  <td align="center" nowrap>${setDate}</td>
			  <td align="center" nowrap>${transTypeName}</td>
			  <td align="center" nowrap>${transNum}</td>
			  <td align="right" nowrap>${fn:amount(amount)}</td>
			  <td align="right" nowrap>${fn:amount(lastAmt)}</td>			  
			  <td align="right" nowrap>${fn:amount(feeAmt)}</td>		
			  <td align="right" nowrap>${fn:amount(recvAmt)}</td>		
			  <td align="right" nowrap>${fn:amount(nextAmt)}</td>		
			  <td align="center" nowrap>${chkId}</td> 
			  <td align="center" nowrap>${chkStatusName}</td> 
			  <td align="center" nowrap>${updateBy}</td> 
			  <td align="center" nowrap>${rmaStateName}</td>  
			  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="merchTransDSetPage"/>
		</div>
		</form>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>