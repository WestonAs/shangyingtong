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
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="list.do" id="searchForm" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>分支机构发卡机构手续费核销</caption>
						<tr>
							<td align="right">分支机构</td>
							<s:if test="showFenzhi">
							<td>
								<s:select name="cardIssuerFeeMSet.chlCode" list="chlList" listKey="branchCode" listValue="branchName"></s:select>
							</td>
							</s:if>
							<s:else>
								<td><s:textfield name="cardIssuerFeeMSet.chlName" /></td>
							</s:else>
							<td width="80" height="30" align="right">发卡机构</td>
							<s:if test="showCard">
							<td>
								<s:select name="cardIssuerFeeMSet.branchCode" list="branchList" listKey="branchCode" listValue="branchName"></s:select>
							</td>
							</s:if>
							<s:else>
								<td><s:textfield name="cardIssuerFeeMSet.branchName" /></td>
							</s:else>
							<td align="right">核销状态</td>
							<td>
								<s:select name="cardIssuerFeeMSet.chkStatus" list="chkStatusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">结算月范围</td>
							<td>
								<s:textfield id="startDate" name="startDate" onclick="getStartMonth();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="endDate" name="endDate" onclick="getEndMonth();"  cssStyle="width:68px;"/>
							</td>
							<td height="30" colspan="3">
								<input type="submit" class="ml30" value="查询" id="input_btn2"  name="ok" />
								<input type="button" class="ml30" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_VERIFY_CARD_ISSUER_FEE_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">结算月份</td>
			   <td align="center" nowrap class="titlebg">分支机构</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">手续费币种</td>
			   <td align="center" nowrap class="titlebg">上期结转</td>
			   <td align="center" nowrap class="titlebg">分支机构手续费</td>
			   <td align="center" nowrap class="titlebg">实收金额</td>
			   <td align="center" nowrap class="titlebg">结转下期</td>
			   <td align="center" nowrap class="titlebg">核销状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${feeMonth}</td>
			  <td align="center" nowrap>${chlName}</td>
			  <td align="center" nowrap>${branchName}</td>
			  <td align="center" nowrap>${curCode}</td>
			  <td align="right" nowrap>${fn:amount(lastFee)}</td>
			  <td align="right" nowrap>${fn:amount(chlFeeAmt)}</td>
			  <td align="right" nowrap>${fn:amount(recvAmt)}</td>
			  <td align="right" nowrap>${fn:amount(nextFee)}</td>
			  <td align="center" nowrap>${chkStatusName}</td>
			  <td align="center" nowrap>
			  	<s:if test="chkStatus==01 || chkStatus==04">
				  	<span class="redlink">
				  		<f:pspan pid="verifycardissuerfee_verify">
				  			<f:link href="/verify/verifyCardIssuerFee/showVerify.do?cardIssuerFeeMSet.chlCode=${chlCode}&cardIssuerFeeMSet.branchCode=${branchCode}&cardIssuerFeeMSet.feeMonth=${feeMonth}">核销</f:link>
				  		</f:pspan>
				  	</span>
				</s:if>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>