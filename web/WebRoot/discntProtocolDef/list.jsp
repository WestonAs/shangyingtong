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
							<td align="right">折扣协议号</td>
							<td>
								<s:textfield name="discntProtclDef.discntProtclNo" cssClass="{digitOrLetter:true}"/>
							</td>

							<td align="right">折扣协议名</td>
							<td>
								<s:textfield name="discntProtclDef.discntProtclName"/>
							</td>

							<td align="right">规则状态</td>
							<td>
								<s:select name="discntProtclDef.ruleStatus" list="ruleStatusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select>
							</td>
						</tr>
						<tr>
							<td height="30">
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="discntprotcldef_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/discntProtocolDef/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_DISCNT_PROTOCOL_DEF_LIST"/>
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
			   <td align="center" nowrap class="titlebg">折扣类型</td>
			   <td align="center" nowrap class="titlebg">折扣特征码</td>
			   <td align="center" nowrap class="titlebg">折扣率</td>
			   <td align="center" nowrap class="titlebg">暗折折扣率</td>
			   <td align="center" nowrap class="titlebg">生效日期</td>
			   <td align="center" nowrap class="titlebg">失效日期</td>
			   <td align="center" nowrap class="titlebg">规则状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${discntProtclNo}</td>
			  <td align="center" nowrap>${discntProtclName}</td>
			  <td align="center" nowrap>${className}</td>
			  <td align="center" nowrap>${discntLabelCode}</td>
			  <td align="right" nowrap>${fn:perDecimal(discntRate)}</td>
			  <td align="right" nowrap>${fn:perDecimal(settDiscntRate)}</td>
			  <td align="center" nowrap>${effDate}</td>
			  <td align="center" nowrap>${expirDate}</td>
			  <td align="center" nowrap>${ruleStatusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/discntProtocolDef/detail.do?discntProtclDef.discntProtclNo=${discntProtclNo}">明细</f:link>
			  	</span>
			  		<s:if test="ruleStatus == 01">
			  		<f:pspan pid="discntprotcldef_cancel">
			  				<a href="javascript:submitUrl('searchForm', '/discntProtocolDef/cancel.do?discntprotclNo=${discntProtclNo}', '确定要注销吗？');" />注销</a>
			  		</f:pspan>
			  		</s:if>
			  		<s:else>
			  		<span class="redlink">
			  		<f:pspan pid="discntprotcldef_enable">
			  				<a href="javascript:submitUrl('searchForm', '/discntProtocolDef/enable.do?discntprotclNo=${discntProtclNo}', '确定要生效吗？');" />生效</a>
			  		</f:pspan>
			  		</span>
			  		</s:else>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>