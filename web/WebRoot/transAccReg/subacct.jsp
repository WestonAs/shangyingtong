<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
	<thead>
		<tr>
		   <td align="center" nowrap class="titlebg">请选择</td>
		   <td align="center" nowrap class="titlebg">子账户类型</td>
		   <td align="center" nowrap class="titlebg">可用余额</td>
		   <td align="center" nowrap class="titlebg">冻结金额</td>
		   <td align="center" nowrap class="titlebg">生效日期</td>
		</tr>
	</thead>
	<s:if test="subAcctList != null && subAcctList.size > 0">
		<s:iterator value="subAcctList"> 
			<tr>
			  <td align="center" nowrap><input type="radio" name="subAcctType" value="${subacctType}" onclick="radioInSubacctType()"/></td>
			  <td align="center" nowrap>${subacctTypeName}</td>
			  <td align="center" nowrap>${fn:amount(avlbBal)}</td>
			  <td align="center" nowrap>${fn:amount(fznAmt)}</td>
			  <td align="center" nowrap>${effDate}</td>
			</tr>
		</s:iterator>
	</s:if>
	<s:else>
		<tr>
		  <td align="center" nowrap colspan="5"> 该卡号下没有子账户，不能进行转帐!</td>
		</tr>
	</s:else>
</table>
<s:if test="subAcctList != null && subAcctList.size > 0">
	<div class="contentb" id="idInputPage" style="display: none;">
		<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
			<tr>
				<td width="80" height="30" align="right">转入卡号</td>
				<td>
					<s:textfield id="InCard_Id" name="transAccReg.inCardId" cssClass="{required:true, digitOrLetter:true}"/>
					<span class="field_tipinfo">请输入转入卡号</span>
				</td>
				
				<s:if test="@gnete.card.service.mgr.BranchBizConfigCache@isNeedDepositRebateAcct(loginBranchCode)">
					<td id="inSubacctTypeTd1" class="no" width="80" height="30" align="right">转入子账户类型</td>
					<td id="inSubacctTypeTd2" class="no" >
						<label><input type="radio" name="transAccReg.inSubacctType" value="01"/>充值资金</label>
						<label><input type="radio" name="transAccReg.inSubacctType" value="02" />返利资金</label>
					</td>
				</s:if>
				
				<td width="80" height="30" align="right">转帐金额</td>
				<td>
					<s:textfield id="Amt_Id" name="transAccReg.amt" cssClass="{required:true, num:true}"/>
					<span class="field_tipinfo">请输入转帐金额</span>
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">&nbsp;</td>
				<td height="30" colspan="11">
					<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return validateForm()"/>
					<input type="button" value="清除" name="escape" onclick="$('#InCard_Id').val('');$('#Amt_Id').val('');" class="ml30" />
				</td>
			</tr>
		</table>
	</div>
</s:if>