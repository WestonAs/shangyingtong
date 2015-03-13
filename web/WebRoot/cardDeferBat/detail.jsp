<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="gnete.etc.WorkflowConstants"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>${ACT.name}</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div id="printArea" >
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>卡延期明细信息<span class="caption_title"> | <f:link href="/cardDeferBat/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>卡延期ID</td>
				<td>${cardDeferReg.cardDeferId}</td>
				<td>卡号ID</td>
				<td>${cardDeferReg.cardId}</td>
				<td>状态</td>
				<td>${cardDeferReg.statusName}</td>

			</tr>
			<tr>
				<td>原生效日期</td>
				<td>${cardDeferReg.effDate}</td>
				<td>原失效日期</td>
				<td>${cardDeferReg.expirDate}</td>
				<td>延期日期</td>
				<td>${cardDeferReg.newExpirDate}</td>

			</tr>
			<tr>
				<td>机构名称</td>
				<td>${cardDeferReg.branchName}</td>
				<td>发卡机构</td>
				<td>${cardDeferReg.cardBranch}</td>
				<td>领卡机构</td>
				<td>${cardDeferReg.appOrgId}</td>

			</tr>
			<tr>
				<td>开始卡号</td>
				<td>${cardDeferReg.startCard}</td>
				<td>结束卡号</td>
				<td>${cardDeferReg.endCard}</td>
				<td>卡数量</td>
				<td>${cardDeferReg.cardNum}</td>

			</tr>
			<tr>
				<td>操作员</td>
				<td>${cardDeferReg.updateUser}</td>
				<td>操作机构</td>
				<td>${cardDeferReg.branchCode}-${fn:branch(cardDeferReg.branchCode)}</td>
				<td>操作时间</td>
				<td><s:date name="cardDeferReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>

			</tr>
			<tr>
				<td>是否文件方式</td>
				<td>${cardDeferReg.deferTypeName}</td>
				<td>处理备注</td>
				<td>${cardDeferReg.procNote}</td>
				<td>备注</td>
				<td>${cardDeferReg.remark}</td>

			</tr>
		</table>
		</div>
		</div>
		
		<div style="padding-left: 30px;margin: 0px;">
			<input type="button" value="打印" onclick="openPrint();"/>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.WORKFLOW_CARD_DEFFER%>"/>
			<jsp:param name="refId" value="${cardDeferReg.cardDeferId}"/>
		</jsp:include>
		<br />
		
		<s:if test="batPage.data.size > 0">
		<!-- 批量赠送登记簿明细 -->
		<div class="tablebox">
			<b><s:label>卡延期登记簿明细 </s:label></b>
			<form id="id_detail_bat" method="post" action="detail.do?cardDeferReg.cardDeferId=${cardDeferReg.cardDeferId}">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">			
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">卡延期明细id</td>
			   <td align="center" nowrap class="titlebg">卡延期批次id</td>
			   <td align="center" nowrap class="titlebg">卡号</td>			   
			   <td align="center" nowrap class="titlebg">延期日期</td>			   
			   <td align="center" nowrap class="titlebg">插入时间</td>			   
			   <td align="center" nowrap class="titlebg">备注</td>			   
			</tr>
			</thead>
			<s:iterator value="batPage.data"> 
			<tr>
			  <td nowrap>${cardDeferBatId }</td>
			  <td align="center" nowrap>${cardDeferId}</td>
			  <td align="center" nowrap>${cardId}</td>
			  <td align="center" nowrap>${newExpirDate}</td>
			  <td align="center" nowrap><s:date name="insertTime"  format="yyyy-MM-dd HH:mm:ss" /></td>
			  <td align="center" nowrap>${reMark}</td>	  
			</tr>
			</s:iterator>
			</table>
			</form>
			<f:paginate name="batPage"/>
		</div>
		</s:if>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>