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
							<td align="right">折扣协议名</td>
							<td><s:textfield name="greatDiscntProtclDef.greatDiscntProtclName" /></td>
							<td align="right">状态</td>
							<td>
								<s:select name="greatDiscntProtclDef.ruleStatus" list="greatDiscntProtclDefStatusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="greatdiscntprotcldef_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/greatDiscount/greatDiscntProtclDef/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_GREATDISCNTPROTCLDEF_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			    <td align="center" nowrap class="titlebg">折扣协议号</td>
				<td align="center" nowrap class="titlebg">折扣协议名</td>
				<td align="center" nowrap class="titlebg">联名发卡机构</td>
				<td align="center" nowrap class="titlebg">商户</td>
<%--				<td align="center" nowrap class="titlebg">卡bin范围</td>--%>
				<td align="center" nowrap class="titlebg">交易支付方式</td>
				<td align="center" nowrap class="titlebg">失效日期</td>
				<td align="center" nowrap class="titlebg">生效日期</td>
<%--				<td align="center" nowrap class="titlebg">运营机构收益</td>--%>
				<td align="center" nowrap class="titlebg">规则状态</td>
			    <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			    <td align="center" nowrap>${greatDiscntProtclId}</td>
				<td align="center" nowrap>${greatDiscntProtclName}</td>
				<td align="center" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
				<td align="center" nowrap>${merchNo}-${fn:merch(merchNo)}</td>
<%--				<td align="center" nowrap>${cardBinScope}</td>--%>
				<td align="center" nowrap>${payWayName}</td>
				<td align="center" nowrap>${expirDate}</td>
				<td align="center" nowrap>${effDate}</td>
<%--				<td align="center" nowrap>${chlIncomeFee}</td>--%>
				<td align="center" nowrap>${ruleStatusName}</td>
				<td align="center" nowrap>
				  	<span class="redlink">
				  		<f:link href="/greatDiscount/greatDiscntProtclDef/detail.do?greatDiscntProtclDef.greatDiscntProtclId=${greatDiscntProtclId}">明细</f:link>
					  	<s:if test="ruleStatus==02">
					  		<f:pspan pid="greatdiscntprotcldef_modify"><f:link href="/greatDiscount/greatDiscntProtclDef/showModify.do?greatDiscntProtclDef.greatDiscntProtclId=${greatDiscntProtclId}">编辑</f:link></f:pspan>
					  	    <f:pspan pid="greatdiscntprotcldef_delete">
					  			<a href="javascript:submitUrl('searchForm', '/greatDiscount/greatDiscntProtclDef/delete.do?greatDiscntProtclDef.greatDiscntProtclId=${greatDiscntProtclId}', '确定要删除吗？');" />删除</a>
					  	    </f:pspan>	
					  	</s:if>
					  	<s:if test="ruleStatus==00">
					  		<f:pspan pid="greatdiscntprotcldef_modify">
					  		    <a href="javascript:submitUrl('searchForm', '/greatDiscount/greatDiscntProtclDef/enable.do?greatDiscntProtclDef.greatDiscntProtclId=${greatDiscntProtclId}', '确定要启用吗？');" />启用</a>
					  		</f:pspan>
					  	</s:if>
					  	<s:if test="ruleStatus==01">
					  		<f:pspan pid="greatdiscntprotcldef_modify">
					  		    <a href="javascript:submitUrl('searchForm', '/greatDiscount/greatDiscntProtclDef/cancel.do?greatDiscntProtclDef.greatDiscntProtclId=${greatDiscntProtclId}', '确定要注销吗？');" />注销</a>
					  		</f:pspan>
					  	</s:if>
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