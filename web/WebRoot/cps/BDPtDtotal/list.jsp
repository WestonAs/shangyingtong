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
		<f:js src="/js/date/WdatePicker.js"/>

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
							<td width="80" height="30" align="right">代收付流水</td>
							<td><s:textfield name="bdPtDtotal.cpSn"/></td>
							<td width="80" height="30" align="right">代收付日期</td>
							<td><s:textfield name="bdPtDtotal.cpDate" onclick="WdatePicker()"/></td>
							<td width="80" height="30" align="right">代收付状态</td>
							<td>
								<s:select id="" name="bdPtDtotal.cpStatus" list="cpStatusList" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name" ></s:select>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">回盘日期</td>
							<td><s:textfield name="bdPtDtotal.returnDate" onclick="WdatePicker()"/></td>
							
							<s:if test="!cardOrCardDeptRoleLogined">
								<td width="80" height="30" align="right">发卡机构名称</td>
								<td><s:textfield name="bdPtDtotal.cardIssuerName"/></td>
							</s:if>
							<s:if test="!merchantRoleLogined">
								<td width="80" height="30" align="right">商户名称</td>
								<td><s:textfield name="bdPtDtotal.merName"/></td>
							</s:if>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td height="30" colspan="4" >
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					
					<s:token name="_TOKEN_BDPTDTOTAL_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">代收付流水</td>
			   <td align="center" nowrap class="titlebg">代收付日期</td>
			   <td align="center" nowrap class="titlebg">回盘日期</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">商户</td>
			   <td align="center" nowrap class="titlebg">交易日期</td>			   
			   <td align="center" nowrap class="titlebg">代收付金额</td>			   
			   <td align="center" nowrap class="titlebg">代收付状态</td>
			   <td align="center" nowrap class="titlebg">代收付标志</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td align="left" nowrap>${cpSn}</td>
			  <td align="center" nowrap>${cpDate}</td>
			  <td align="center" nowrap>${returnDate}</td>
			  <td align="left" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
			  <td align="left" nowrap>${merNo}-${fn:merch(merNo)}${fn:branch(merNo)}</td>
			  <td align="center" nowrap>${settDate}</td>
			  <td align="right" nowrap>${fn:amount(settAmt)}</td>
			  <td align="center" nowrap>${cpStatusName}</td>
			  <td align="center" nowrap>${cpFlagName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/cps/BDPtDtotal/detail.do?bdPtDtotal.cpSn=${cpSn}">查看</f:link>	
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