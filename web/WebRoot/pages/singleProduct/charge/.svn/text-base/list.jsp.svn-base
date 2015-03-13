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
		
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="list.do" id="searchForm" cssClass="validate-tip">
					<table id="form_grid" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">id</td>
							<td>
								<s:textfield name="costRecord.id" />
							</td>
							<td align="right">发卡机构</td>
							<td>
								<s:textfield name="costRecord.branchCode" />
							</td>
							<td align="right">类型</td>
							<td>
								<s:select name="costRecord.type" list="typeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">缴费时间</td>
							<td>
								<s:textfield id="chargeStartDate" name="chargeStartDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="chargeEndDate" name="chargeEndDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
							</td>
							
							<td align="right">产生时间</td>
							<td>
								<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
							</td>
							<td align="right">缴费方式</td>
							<td>
								<s:select name="costRecord.payType" list="payTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">状态</td>
							<td>
								<s:select name="costRecord.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td align="right">是否已开发票</td>
							<td>
								<s:select name="costRecord.invoiceStatus" list="invoiceList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="single_product_charge_add">
									<input style="margin-left:10px;" type="button" value="开发票" onclick="javascript:gotoUrl('/pages/singleProduct/charge/showInvoice.do');" id="input_btn3"  name="escape" />
								</f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARD_SINGLECARD_CHARGE_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">ID</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">类型</td>
			   <td align="center" nowrap class="titlebg">是否已开发票</td>
			   <td align="center" nowrap class="titlebg">应收金额</td>
			   <td align="center" nowrap class="titlebg">已交金额</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">缴费方式</td>
			   <td align="center" nowrap class="titlebg">缴费时间</td>
			   <td align="center" nowrap class="titlebg">产生时间</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data">
			<tr>
			  <td align="center" nowrap>${id}</td>
			  <td nowrap>${branchCode}-${fn:branch(branchCode)}</td>
			  <td align="center" nowrap>${typeName}</td>
			  <td align="center" nowrap>${invoiceStatusName}</td>
			  <td align="right" nowrap>${fn:amount(amt)}</td>
			  <td align="right" nowrap>${fn:amount(paidAmt)}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>${payTypeName}</td>
			  <td align="center" nowrap><s:date name="payTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			  <td align="center" nowrap><s:date name="genTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/singleProduct/charge/detail.do?costRecord.id=${id}">明细</f:link>
			  	</span>
			  	<f:pspan pid="single_product_charge_add">
				  	<s:if test="'01'.equals(status) && '01'.equals(loginRoleType)">
				  		<f:link href="/pages/singleProduct/charge/showCharge.do?costRecord.id=${id}">缴费</f:link>
				  	</s:if>
			  	</f:pspan>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>