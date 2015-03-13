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
		<f:js src="/js/datePicker/WdatePicker.js"/>
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
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
						<td align="right">营运机构代理</td>
						<td>
						<s:if test="showAgent">
						<s:select name="releaseCardFeeDTotal.proxyId" headerKey="" headerValue="--请选择--" list="proxyList" listKey="branchCode" listValue="branchName"></s:select>
						</s:if>
						<s:else>
						<s:textfield name="releaseCardFeeDTotal.proxyName"></s:textfield>
						</s:else>
						</td>
						<td align="right">清算日期</td>
						<td>
							<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
							<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
						</td>
						<td align="right">交易类型</td>
						<td>
						<s:select name="releaseCardFeeDTotal.transType" list="transTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
						</td>
						</tr>
						<tr>
						<td></td>	
						<td height="30" colspan="4">
						<input type="submit" value="查询" id="input_btn2"  name="ok" />
						<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
						</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CENTERPROXYSHARESINFO_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">清算日期</td>
			   <td align="center" nowrap class="titlebg">分支机构</td>
			   <td align="center" nowrap class="titlebg">营运代理</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">商户</td>
			   <td align="center" nowrap class="titlebg">卡Bin</td>
			   <td align="center" nowrap class="titlebg">交易类型</td>
			   <td align="center" nowrap class="titlebg">计费方式</td>
			   <td align="center" nowrap class="titlebg">手续费费率</td>
			   <td align="center" nowrap class="titlebg">交易金额</td>
			   <td align="center" nowrap class="titlebg">手续费金额</td>
			   <td align="center" nowrap class="titlebg">运营代理分润金额</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td align="center" nowrap>${feeDate}</td>
			  <td >${fn:branch(chlCode)}</td>
			  <td >${proxyName}</td>
			  <td >${branchName}</td>
			  <td >${merchName}</td>
			  <td align="center" nowrap>${cardBin}</td>
			  <td align="center" nowrap>${transTypeName}</td>
			  <td align="center" nowrap>${feeTypeName}</td>
			 <td align="right" nowrap>
			  <s:if test="feeType == 0">${fn:amount(feeRate)}</s:if><s:else>${fn:percentPre(feeRate, 4)}</s:else>
			  </td>
			  <td align="right" nowrap>${fn:amount(amount)}</td>
			  <td align="right" nowrap>${fn:amount(feeAmt)}</td>
			  <td align="right" nowrap>${fn:amount(proxyFeeAmt)}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/releaseCardFee/centerProxySharesInfo/detail.do?releaseCardFeeDTotal.feeDate=${feeDate}&releaseCardFeeDTotal.branchCode=${branchCode}&releaseCardFeeDTotal.merchId=${merchId}&releaseCardFeeDTotal.cardBin=${cardBin}&releaseCardFeeDTotal.transType=${transType}&releaseCardFeeDTotal.posManageId=${posManageId}&releaseCardFeeDTotal.posProvId=${posProvId}">查看</f:link>
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>