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
		
		<!-- 账户明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>
				账户详细信息
				<span class="caption_title"> | 
					<s:if test='formMap.fromPage=="listDiscontinuousCard"'>
						<f:link href="/cardAcct/listDiscontinuousCard.do?goBack=goBack">返回列表</f:link>
					</s:if>
					<s:else>
						<f:link href="/cardAcct/list.do?goBack=goBack">返回列表</f:link>
					</s:else>
				</span>
			</caption>
			<tr>
				<td>账号</td>
				<td>${acctInfo.acctId}</td>
				<td>发卡机构编号</td>
				<td>${acctInfo.cardIssuer}</td>
				<td>发卡机构名称</td>
				<td>${fn:branch(acctInfo.cardIssuer)}</td>
			</tr>
			<tr>
				<td>币种</td>
				<td>${acctInfo.curCode} </td>
				<td>卡类型</td>
				<td>${acctInfo.cardClassName} </td>
				<td>卡子类型</td>
				<td>${acctInfo.cardSubclass}</td>
			</tr>
			<tr>
				<td>会员级别</td>
				<td>${acctInfo.membLevel}</td>
				<td>会员类型</td>
				<td>${acctInfo.membClass}</td>
				<td>开卡积分</td>
				<td>${fn:amount(acctInfo.ptOpencard)}</td>
			</tr>
			<tr>
				<td>折扣类型</td>
				<td>${acctInfo.discntClass}</td>
				<td>赠券类型</td>
				<td>${acctInfo.couponClass}</td>
				<td>次数卡类型</td>
				<td>${acctInfo.frequencyClass}</td>
			</tr>
			<tr>
				<td>积分类型</td>
				<td>${acctInfo.ptClass}</td>
				<td>签单客户号</td>
				<td>${acctInfo.signingCustomerId}</td>
				<td>签单透支额度</td>
				<td>${fn:amount(acctInfo.signingOverdraftLimit)}</td>
			</tr>
			<tr>
				<td>交易次数</td>
				<td>${acctInfo.tradeCnt}</td>
				<td>初始充值金额</td>
				<td>${fn:amount(acctInfo.initialChargeAmt)}</td>
				<td>累积充值金额</td>
				<td>${fn:amount(acctInfo.accuChargeAmt)}</td>
			</tr>
			<tr>
				<td>生效日期</td>
				<td>${acctInfo.effDate}</td>
				<td>失效日期</td>
				<td>${acctInfo.expirDate}</td>
				<td>最后修改日期</td>
				<td>${acctInfo.lastModifiedDate}</td>
			</tr>		 
		</table>
		</div>

		<!-- 子账户数据列表区 -->
		<s:if test="subAcctBalList.size() > 0">
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<caption>相关子账户详细信息</caption>	
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">子账户类型</td>
			   <td align="center" nowrap class="titlebg">可用余额</td>
			   <td align="center" nowrap class="titlebg">冻结金额</td>
			   <td align="center" nowrap class="titlebg">生效日期</td>
			   <td align="center" nowrap class="titlebg">失效日期</td>
			</tr>
			</thead>
		
			<s:iterator value="subAcctBalList"> 
			<tr>		
			  <td align="center" nowrap>${subacctTypeName}</td>
			  <td align="right" nowrap>${fn:amount(avlbBal)}</td>
			  <td align="right" nowrap>${fn:amount(fznAmt)}</td>
			  <td align="center" nowrap>${effDate}</td>
			  <td align="center" nowrap>${expirDate}</td>			  
			</tr>
			</s:iterator>
			</table>
		</div>
		</s:if>
		
		<!-- 积分账户数据列表区 -->
		<s:if test="pointBalList.size() > 0">
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<caption>相关积分账户详细信息</caption>	
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">积分类型</td>
			   <td align="center" nowrap class="titlebg">积分名称</td>
			   <td align="center" nowrap class="titlebg">联名机构编号</td>
			   <td align="center" nowrap class="titlebg">联名机构名称</td>
			   <td align="center" nowrap class="titlebg">本期起始日</td>
			   <td align="center" nowrap class="titlebg">本期结束日</td>
			   <td align="center" nowrap class="titlebg">新增积分</td>
			   <td align="center" nowrap class="titlebg">可用积分</td>
			   <td align="center" nowrap class="titlebg">冻结积分</td>
			</tr>
			</thead>
		
			<s:iterator value="pointBalList"> 
			<tr>		
			  <td align="center" nowrap>${ptClass}</td>
			  <td align="center" nowrap>${ptClassName}</td>
			  <td align="center" nowrap>${jinstId}</td>
			  <td align="center" nowrap>${fn:branch(jinstId)}${fn:merch(jinstId)}${groupName}</td>
			  <td align="center" nowrap>${beginDate}</td>			  
			  <td align="center" nowrap>${endDate}</td>			  
			  <td align="right" nowrap>${fn:amount(ptInc)}</td>
			  <td align="right" nowrap>${fn:amount(ptAvlb)}</td>			  
			  <td align="right" nowrap>${fn:amount(fznPt)}</td>			  
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="pointPage"/>
		</div>
		</s:if>
		
		<!-- 赠券账户数据列表区 -->
		<s:if test="couponBalList.size() > 0">
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<caption>相关赠券账户详细信息</caption>	
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">赠券类型</td>
			   <td align="center" nowrap class="titlebg">赠券名称</td>
			   <td align="center" nowrap class="titlebg">联名机构编号</td>
			   <td align="center" nowrap class="titlebg">联名机构名称</td>
			   <td align="center" nowrap class="titlebg">生效日期</td>
			   <td align="center" nowrap class="titlebg">失效日期</td>
			   <td align="center" nowrap class="titlebg">余额</td>
			</tr>
			</thead>
		
			<s:iterator value="couponBalList"> 
			<tr>		
			  <td align="center" nowrap>${couponClass}</td>
			  <td align="center" nowrap>${className}</td>
			  <td align="center" nowrap>${jinstId}</td>
			  <td align="center" nowrap>${fn:branch(jinstId)}${fn:merch(jinstId)}${groupName}</td>
			  <td align="center" nowrap>${effDate}</td>
			  <td align="center" nowrap>${expirDate}</td>			  
			  <td align="right" nowrap>${fn:amount(balance)}</td>			  
			</tr>
			</s:iterator>
			</table>
		</div>
		</s:if>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>