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
			<caption><s:text name="cardTypeCode.cardTypeName"></s:text>售卡信息<span class="caption_title"> | <f:link href="/saleCardReg/list${actionSubName}.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>卡号</td>
				<td>${saleCardReg.cardId}</td>
		  		<td>售卡方式</td>
				<td>${saleCardReg.presellName}</td>
		  		<td>售卡状态</td>
				<td>${saleCardReg.statusName}</td> 
			</tr>
		  	<tr>
				<td>卡BIN</td>
				<td>${cardBin.binNo}</td>
				<td>卡BIN名称</td>
				<td>${cardBin.binName}</td>
				<td>是否已经撤销</td>
				<td>${saleCardReg.cancelFlagName}</td>
			</tr>
		  	<tr>
		  		<td>售卡日期</td>
				<td>${saleCardReg.saleDate}</td>
				<td>卡类型</td>
				<td>${cardTypeCode.cardTypeName}</td>
		  		<td>购卡客户</td>
				<td>${cardCustomer.cardCustomerName}</td>
			</tr>
			<tr>
		  		<td>售卡返利规则</td>
				<td>${saleRebateRule.rebateName}</td>
				<td>返利方式</td>
				<td>${saleCardReg.rebateTypeName}</td>
				<td></td>
				<td></td>
			</tr>
		  	<tr>
		  		<s:if test="depositTypeIsTimes==true" >
			  		<td>充值次数</td>
					<td>${saleCardReg.amt}</td>
		  		</s:if>
		  		<s:else>
			  		<td>售卡金额</td>
					<td>${fn:amount(saleCardReg.amt)}</td>
		  		</s:else>
				
		  		<td>返利金额</td>
				<td>${fn:amount(saleCardReg.rebateAmt)}</td>
				<td>手续费</td>
				<td>${fn:amount(saleCardReg.feeAmt)}</td>
			</tr>
			<tr>
				<td>工本费</td>
				<td>${fn:amount(saleCardReg.expenses)}</td>
				<td>实收金额</td>
				<td class="bigred">${fn:amount(saleCardReg.realAmt)}</td>
				<td>手续费比例</td>
				<td>${fn:percent(saleCardReg.feeRate)}</td>
			</tr>
			<tr>
		  		<td>售卡机构</td>
				<td>${saleCardReg.branchCode}-${fn:branch(saleCardReg.branchCode)}${fn:dept(saleCardReg.branchCode)}</td>
				<td>激活机构</td>
				<td>${saleCardReg.activateBranch}-${fn:branch(saleCardReg.activateBranch)}${fn:dept(saleCardReg.activateBranch)}</td>
				<td>售卡撤销标志</td>
				<td>${saleCardReg.saleCancelName}</td>
			</tr>
		  	<tr>
				<td>发卡机构</td>
		  		<td>${saleCardReg.cardBranch}-${fn:branch(saleCardReg.cardBranch)}</td>
		  		<td>付款户名</td>
		  		<td>${saleCardReg.payAccName}</td>
		  		<td>付款账号</td>
		  		<td>${saleCardReg.payAccNo}</td>
			</tr>
			<tr>
		  		<td>更新人</td>
				<td>${saleCardReg.updateUser}</td>
		  		<td>更新时间</td>
				<td><s:date name="saleCardReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<s:if test="saleCardReg.couponClass!=null && saleCardReg.couponAmt!=null">
			  	<tr>
					<td>赠券类型编号</td>
			  		<td>${saleCardReg.couponClass}</td>
			  		<td>赠券金额</td>
			  		<td>${fn:amount(saleCardReg.couponAmt)}</td>
			  		<td></td>
			  		<td></td>
				</tr>
			</s:if>
		  	<tr>
		  		<td>备注</td>
		  		<td colspan="5">${saleCardReg.remark}</td>
		  	</tr>
		</table>
		</div>
		
		<!-- 售卡返利规则分段比例明细 -->
		<div class="tablebox">
			<b><s:label>售卡返利规则分段比例明细 </s:label></b>
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">			
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">售卡返利规则ID</td>
			   <td align="center" nowrap class="titlebg">售卡返利分段号</td>
			   <td align="center" nowrap class="titlebg">售卡分段金额上限</td>
			   <td align="center" nowrap class="titlebg">售卡返利比（%）或返利金额</td>			   
			</tr>
			</thead>
			<s:iterator value="saleRebateRuleDetailList"> 
			<tr>
			  <td nowrap><s:text name="rebateId"></s:text></td>
			  <td align="center" nowrap><s:text name="rebateSect"></s:text></td>			  
			  <td align="center" nowrap><s:text name="rebateUlimit"></s:text></td>
			  <td align="center" nowrap><s:text name="rebateRate"></s:text></td>			  
			</tr>
			</s:iterator>
			</table>
		</div>

		<s:if test="cardExtraInfo != null">
		<!-- 卡附加信息 -->
		<div class="tablebox">
			<b><s:label>持卡人信息</s:label></b>
			<table class="detail_grid" width="95%" border="1" cellspacing="0" cellpadding="1">			
				<tr>
					<td>卡号</td>	
					<td>${cardExtraInfo.cardId}</td>	
					<td>持卡人姓名</td>
					<td>${cardExtraInfo.custName}</td>
					<td>手机号</td>	
					<td>${cardExtraInfo.mobileNo }</td>	
				</tr>
				<tr>
					<td>生日</td>
					<td>${cardExtraInfo.birthday}</td>	
					<td>证件类型</td>
					<td>${cardExtraInfo.credTypeName}</td>	
					<td>证件号码</td>	
					<td>${cardExtraInfo.credNo}</td>	
				</tr>
				<tr>
					<td>证件有效期</td>
					<td>${cardExtraInfo.credNoExpiryDate}</td>	
					<td>职业</td>	
					<td>${cardExtraInfo.career}</td>	
					<td>国籍</td>
					<td>${cardExtraInfo.nationality}</td>	
				</tr>
				<tr>
					<td>邮件地址</td>
					<td>${cardExtraInfo.email}</td>	
					<td>联系地址</td>
					<td>${cardExtraInfo.address}</td>	
					<td>联系电话</td>	
					<td>${cardExtraInfo.telNo}</td>	
				</tr>
				<tr>
					<td>是否开通短信通知</td>	
					<td>${cardExtraInfo.smsFlagName}</td>	
					<td>密码失效时间</td>
					<td><s:date name="cardExtraInfo.pwdExpirTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td>购卡客户ID</td>
					<td>${cardExtraInfo.cardCustomerId}</td>
				</tr>
				<tr>
					<td>更新用户名</td>
					<td>${cardExtraInfo.updateBy}</td>	
					<td>更新时间</td>
					<td colspan="3"><s:date name = "cardExtraInfo.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>	
				</tr>
			</table>
		</div>
		</s:if>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>