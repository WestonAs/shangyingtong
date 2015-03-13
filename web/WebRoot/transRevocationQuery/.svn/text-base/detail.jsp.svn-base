<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 transRevocationitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transRevocationitional.dtd">
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
			<caption>${ACT.name}<span class="caption_title"></span></caption>
			<tr>
				<td>撤销流水</td>
				<td>${transRevocation.revcSn}</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>POS流水</td>
				<td>${transRevocation.posSn}</td>
				<td>原POS流水</td>
				<td>${transRevocation.origPosSn}</td>
			</tr>
			<tr>
				<td>系统跟踪号</td>
				<td>${transRevocation.sysTraceNum}</td>
				<td>原系统跟踪号</td>
				<td>${transRevocation.origSysTraceNum}</td>
			</tr>
			<tr>
				<td>受理机构标识码</td>
				<td>${transRevocation.acqInstIdCode}</td>
				<td>发送机构标识码</td>
				<td>${transRevocation.fwdInstIdCode}</td>
		  	</tr>
		  	<tr>
				<td>原受理机构标识码</td>
				<td>${transRevocation.origAcqInstIdCode}</td>
				<td>原发送机构标识码</td>
				<td>${transRevocation.origFwdInstIdCode}</td>
			</tr>
			<tr>
				<td>交易类型</td>
				<td>${transRevocation.revcTypeName}</td>
				<td>原交易类型</td>
				<td>${transRevocation.origTransTypeName}</td>
			</tr>
			<tr>
				<td>交易币种</td>
				<td>${transRevocation.curCode}</td>
				<td>撤销/退回/冲正金额</td>
				<td>${fn:amount(transRevocation.revcAmt)}</td>
			</tr>
			<tr>
				<td>处理时间</td>
				<td><s:date name="transRevocation.procTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>处理状态</td>
				<td>${transRevocation.revcStatusName}</td>
		  	</tr>
		  	 	<tr>
				<td>清算日期</td>
				<td>${transRevocation.settDate}</td>
				<td>接受时间</td>
				<td><s:date name="transRevocation.rcvTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr>
				<td>商户</td>
				<td>${fn:merch(transRevocation.merNo)}</td>
				<td>原交易发起机构</td>
				<td>${fn:branch(transRevocation.origCardIssuer)}</td>
			</tr>
			<tr>
				<td>备注</td>
				<td>${transRevocation.remark}</td>
				<td></td>
				<td></td>
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