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
		
		<!-- 数据列表区 -->
		<div class="userbox">
		<div class="tablebox">
		<form id="id_detail_bat" method="post" action="chgDetail.do?cardBranch=${branchSellAmt.cardBranch}&sellBranch=${branchSellAmt.sellBranch}">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<caption>配额变动明细查询<span class="caption_title"> | <f:link href="/sellAmt/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">售卡机构</td>
			   <td align="center" nowrap class="titlebg">售卡机构类型</td>
			   <td align="center" nowrap class="titlebg">调整类型</td>
			   <td align="center" nowrap class="titlebg">相关金额</td>
			   <td align="center" nowrap class="titlebg">可用充值售卡金额</td>
			   <td align="center" nowrap class="titlebg">参考ID</td>
			   <td align="center" nowrap class="titlebg">变化日期</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td nowrap>${cardBranch}-${fn:branch(cardBranch)}</td>
			  <td nowrap>${sellBranch}-${fn:branch(sellBranch)}${fn:dept(sellBranch)}</td>
			  <td align="center" nowrap>${sellTypeName}</td>
			  <td align="center" nowrap>${adjTypeName}</td>
			  <td align="center" nowrap>${fn:amount(amt)}</td>
			  <td align="center" nowrap>${fn:amount(availableAmt)}</td>
			  <td align="center" nowrap>${refid}</td>
			  <td align="center" nowrap><s:date name="changeDate" format="yyyy-MM-dd HH:mm:ss"></s:date></td>
			</tr>
			</s:iterator>
			</table>
		</form>
			<f:paginate name="page"/>
		</div>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>