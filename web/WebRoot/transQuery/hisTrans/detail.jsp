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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>${ACT.name}<span class="caption_title"></span></caption>
			<tr>
				<td>系统跟踪号</td>
				<td colspan="3">${trans.sysTraceNum}</td>
			</tr>
			<tr>
				<td>交易流水</td>
				<td>${trans.transSn}</td>
				<td>交易类型</td>
				<td>${trans.transTypeName}</td>
			</tr>
			<tr>
				<td>卡号</td>
				<td>${trans.cardId}</td>
				<td>账号</td>
				<td>${trans.acctId}</td>
			</tr>
			<tr>
				<td>发卡机构编号</td>
				<td>${trans.cardIssuer}</td>
				<td>发卡机构名称</td>
				<td>${fn:branch(trans.cardIssuer)}</td>
			</tr>
			<tr>
				<td>发起平台</td>
				<td>${trans.platformName}</td>
				<td>汇率</td>
				<td>${trans.xrate}</td>
			</tr>
			<tr>
				<td>清算日期</td>
				<td>${trans.settDate}</td>
				<td>清算金额</td>
				<td>${fn:amount(trans.settAmt)}</td>
		  	</tr>
		  	<tr>
				<td>发起方编号</td>
				<td>${trans.merNo}</td>
				<td>发起方名称</td>
				<td>${fn:merch(trans.merNo)}${fn:branch(trans.merNo)}</td>
			</tr>
			<tr>
				<td>商户应付手续费</td>
				<td>${fn:amount(trans.merFee)}</td>
				<td>终端号</td>
				<td>${trans.termlId}</td>
				
			</tr>
			<tr>
				<td>交易金额/次数</td>
				<td>${fn:amount(trans.transAmt)}</td>
				<td>交易币种</td>
				<td>${trans.curCode}</td>
		  	</tr>
		  	<tr>
				<td>应付商户金额</td>
				<td>${fn:amount(trans.merPyaAmt)}</td>
				<td>实付商户金额</td>
				<td>${fn:amount(trans.merPaidAmt)}</td>
		  	</tr>
		  	 	<tr>
				<td>应收持卡人金额</td>
				<td>${fn:amount(trans.chdrRvaAmt)}</td>
				<td>实收持卡人金额</td>
				<td>${fn:amount(trans.chdrPdpAmt)}</td>
			</tr>
			<tr>
				<td>转账入账卡号</td>
				<td>${trans.eiaCardId}</td>
		  	 	<td>资金子帐户扣款</td>
				<td>${fn:amount(trans.dedSubacctAmt)}</td>
			</tr>
			<tr>
				<td>赠券帐户扣款</td>
				<td>${fn:amount(trans.dedCouponAmt)}</td>
				<td>其他帐户扣款</td>
				<td>${fn:amount(trans.dedOtherAmt)}</td>
			</tr>
			<tr>
		  		<td>商户代理商编号</td>
				<td>${trans.merProxyNo}</td>
		  		<td>商户代理商名称</td>
				<td>${fn:branch(trans.merProxyNo)}</td>
			</tr>
			<tr>
				<td>商户代理分润</td>
				<td>${fn:amount(trans.merProxyFee)}</td>
				<td>备注</td>
				<td>${trans.remark }</td>
		  	</tr>
		  	<tr>
				<td>运营代理机构编号</td>
				<td>${trans.centProxyNo}</td>
				<td>运营代理机构名称</td>
				<td>${fn:branch(trans.centProxyNo)}</td>
		  	</tr>
			<tr>
				<td>接收时间</td>
				<td><s:date name="trans.rcvTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>处理时间</td>
				<td><s:date name="trans.procTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr>
				<td>保留域2</td>
				<td><s:property value="trans.reserved2"/></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>处理状态</td>
				<td>${trans.procStatusName}</td>
				<td>退货状态</td>
				<td>${trans.goodsStatusName}</td>
			</tr>
		</table>
		</div>
		
		<!-- 交易账户变动明细 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<caption>相关交易账户变动明细</caption>	
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">子账户类型</td>
			   <td align="center" nowrap class="titlebg">子类型编号</td>
			   <td align="center" nowrap class="titlebg">账号</td>
			   <td align="center" nowrap class="titlebg">交易类型</td>
			   <td align="center" nowrap class="titlebg">期初余额</td>
			   <td align="center" nowrap class="titlebg">扣款金额</td>
			   <td align="center" nowrap class="titlebg">期末余额</td>
			   <td align="center" nowrap class="titlebg">处理状态</td>
			   <td align="center" nowrap class="titlebg">变更时间</td>
			   <td align="center" nowrap class="titlebg">清算日期</td>
			</tr>
			</thead>
			
			<s:iterator value="acctPage.data"> 
			<tr>		
			  <td align="center" nowrap>${subacctKindName}</td>
			  <td align="center" nowrap>${classId}</td>
			  <td align="center" nowrap>${acctId}</td>
			  <td align="center" nowrap>${transTypeName}</td>
			  <td align="right" nowrap>${fn:amount(beginBal)}</td>
			  <td align="right" nowrap>${fn:amount(dedAmt)}</td>			  
			  <td align="right" nowrap>${fn:amount(endBal)}</td>		
			  <td align="center" nowrap>${procStatusName}</td> 
			  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td align="center" nowrap>${settDate}</td>  
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="acctPage"/>
		</div>
		
		<!-- 交易积分变动数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<caption>相关交易积分变动明细</caption>	
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">积分类型</td>
			   <td align="center" nowrap class="titlebg">账号</td>
			   <td align="center" nowrap class="titlebg">交易类型</td>
			   <td align="center" nowrap class="titlebg">期初积分</td>
			   <td align="center" nowrap class="titlebg">期末积分</td>
			   <td align="center" nowrap class="titlebg">期初分期积分</td>
			   <td align="center" nowrap class="titlebg">期末分期积分</td>
			   <td align="center" nowrap class="titlebg">本期变动积分</td>
			   <td align="center" nowrap class="titlebg">积分兑赠券金额</td>
			   <td align="center" nowrap class="titlebg">处理状态</td>
			   <td align="center" nowrap class="titlebg">变更时间</td>
			   <td align="center" nowrap class="titlebg">清算日期</td>
			</tr>
			</thead>
		
			<s:iterator value="pointPage.data"> 
			<tr>		
			  <td align="center" nowrap>${ptClass}</td>
			  <td align="center" nowrap>${acctId}</td>
			  <td align="center" nowrap>${transTypeName}</td>
			  <td align="right" nowrap>${fn:amount(beginPoint)}</td>
			  <td align="right" nowrap>${fn:amount(endPoint)}</td>	
			  <td align="right" nowrap>${fn:amount(beginInstmPoint)}</td>	
			  <td align="right" nowrap>${fn:amount(endInstmPoint)}</td>	
			  <td align="right" nowrap>${fn:amount(ptCur)}</td>			  
			  <td align="right" nowrap>${fn:amount(ptCouponAmt)}</td>		
			  <td align="center" nowrap>${procStatusName}</td>
			  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>  
			  <td align="center" nowrap>${settDate}</td>  	  
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="pointPage"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>