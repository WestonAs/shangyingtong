<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>

<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
			
		<title>${ACT.name}</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>

		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption><s:text name="cardTypeCode.cardTypeName"></s:text>预售卡信息</caption>
			<tr>
				<td>卡号</td>
				<td>${saleCardReg.cardId}</td>
		  		<td>售卡方式</td>
				<td>${saleCardReg.presellName}</td>
		  		<td>售卡状态</td>
				<td>${saleCardReg.statusName}</td> 
			</tr>
		  	<tr>
				<td>卡类型ID</td>
				<td>${cardTypeCode.cardTypeCode}</td>
				<td>卡类型</td>
				<td>${cardTypeCode.cardTypeName}</td>
		  		<td>购卡客户</td>
				<td>${cardCustomer.cardCustomerName}</td>
			</tr>
		  	<tr>
				<td>卡BIN</td>
				<td>${cardBin.binNo}</td>
				<td>卡BIN名称</td>
				<td>${cardBin.binName}</td>
		  		<td>售卡返利规则</td>
				<td>${saleRebateRule.rebateName}</td>
			</tr>
		  	<tr>
		  		<td><s:if test="depositTypeIsTimes==true" >充值次数</s:if><s:else>售卡金额</s:else></td>
				<td>${saleCardReg.amt}</td>
				<td>工本费</td>
				<td>${saleCardReg.expenses}</td>
		  		<td>返利金额</td>
				<td>${saleCardReg.rebateAmt}</td>
			</tr>
		  	<tr>
				<td>实收金额</td>
				<td>${saleCardReg.realAmt}</td>
				<td>备注</td>
		  		<td>${saleCardReg.remark}</td>
				<td>持卡人</td>
				<td>${cardExtraInfo.custName}</td>
			</tr>
		  	<tr>
				<td>地址</td>
				<td>${cardExtraInfo.address}</td>
				<td>联系电话</td>
				<td>${cardExtraInfo.telNo}</td>
		  		<td>手机</td>
				<td>${cardExtraInfo.mobileNo}</td>
			</tr>
		  	<tr>
				<td>Email</td>
				<td>${cardExtraInfo.email}</td>
		  		<td>更新人</td>
				<td>${saleCardReg.updateUser}</td>
		  		<td>更新时间</td>
				<td><s:date name="saleCardReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		</table>
		</div>
		
		<div class="userbox" style="padding-top: 0px;">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="activate.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}激活</caption>
						
						
					</table>
					<table id="idCardInfoTbl" style="display:none;" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
													
						
					</table>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td><s:hidden name="saleCardReg.saleBatchId" /></td>
							<td><s:hidden name="saleCardReg.status" /></td>
							<td><s:hidden name="actionSubName" /></td>
						</tr>						
						
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="激活" id="input_btn2"  name="ok"  /> <!-- onclick="submitForm();" -->
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/saleCardReg/list${actionSubName}.do?goBack=goBack')" class="ml30" />
							</td>
							<td></td><td></td>
						</tr>
					</table>
					<s:token name="_TOKEN_SALE_CARD_ACTIVATE"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 售卡返利规则分段比例明细 -->
		<div class="tablebox">
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

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>