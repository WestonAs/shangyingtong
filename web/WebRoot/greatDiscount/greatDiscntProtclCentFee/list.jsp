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
		<f:js src="/js/date/WdatePicker.js"  defer="defer"/>

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
							<td><s:textfield name="greatDiscntProtclCentFee.greatDiscntProtclNo" /></td>
							<td align="right">折扣协议名</td>
							<td><s:textfield name="greatDiscntProtclCentFee.greatDiscntProtclName" /></td>

						</tr>

							<td>&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="greatdiscntcentfee_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/greatDiscount/greatDiscntProtclCentFee/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_GREATDISCNTPROTCLCENTFEE_LIST"/>
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
      <td align="center" nowrap class="titlebg">运营机构</td>
      <td align="center" nowrap class="titlebg">运营中心收益</td>
      <td align="center" nowrap class="titlebg">备注</td>

      <td align="center" nowrap class="titlebg">操作</td>
		</tr>
		</thead>
		<s:iterator value="page.data"> 
		<tr>		
      <td align="center" nowrap>${greatDiscntProtclNo}</td>
      <td align="center" nowrap>${greatDiscntProtclName}</td>
      <td align="center" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
      <td align="center" nowrap>${chlCode}-${fn:branch(chlCode)}</td>
      <td align="center" nowrap>${fn:perDecimal(centIncomeFee)}</td>
      <td align="center" nowrap>${remark}</td>

		  <td align="center" nowrap>
		  	<span class="redlink">
		  		<f:link href="/greatDiscount/greatDiscntProtclCentFee/detail.do?greatDiscntProtclCentFee.greatDiscntProtclNo=${greatDiscntProtclNo}">查看</f:link>
		  		<f:link href="/greatDiscount/greatDiscntProtclCentFee/showModify.do?greatDiscntProtclCentFee.greatDiscntProtclNo=${greatDiscntProtclNo}">修改</f:link>
			  	<f:pspan pid="greatdiscntcentfee_delete">
			  		<a href="javascript:submitUrl('searchForm', '/greatDiscount/greatDiscntProtclCentFee/delete.do?greatDiscntProtclNo=${greatDiscntProtclNo}', '确定要删除吗？');" />删除</a>
			  	</f:pspan>
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