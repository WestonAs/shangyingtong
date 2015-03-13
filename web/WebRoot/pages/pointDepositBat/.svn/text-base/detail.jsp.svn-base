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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div id="printArea" >
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>积分充值明细信息<span class="caption_title"> | <f:link href="/pages/pointDepositBat/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>充值批次ID</td>
				<td>${depositPointReg.depositBatchId}</td>
				<td>卡号</td>
				<td>${depositPointReg.cardId}</td>
				<td>充值日期</td>
				<td>${depositPointReg.depositDate}</td>
			</tr>
		  	<tr>
		  		<td>发卡机构号</td>
		  		<td>${depositPointReg.cardBranch}</td>
		  		<td>发卡机构名称</td>
		  		<td>${fn:branch(depositPointReg.cardBranch)}</td>
				<td>状态</td>
				<td>${depositPointReg.statusName}</td>
			</tr>
			<tr>
		  		<td>充值积分</td>
				<td>${fn:amount(depositPointReg.ptPoint)}</td>
				<td>折算金额</td>
				<td>${fn:amount(depositPointReg.refAmt)}</td>
				<td>起始卡号</td>
				<td>${depositPointReg.strCardId}</td>
			</tr>
			<tr>
				<td>充值机构号</td>
				<td>${depositPointReg.depositBranch}</td>
				<td>充值机构名</td>
				<td>${fn:branch(depositPointReg.depositBranch)}</td>
				<td>结束卡号</td>
				<td>${depositPointReg.endCardId}</td>
			</tr>
		  	<tr>
		  		<td>是否是文件方式</td>
				<td>${depositPointReg.fileDepositName}</td>
		  		<td>更新人</td>
				<td>${depositPointReg.updateUser}</td>
		  		<td>更新时间</td>
				<td><s:date name="depositPointReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		  	<tr>
				<td>备注</td>
		  		<td colspan="5">${depositPointReg.remark}</td>
		  	</tr>
		</table>
		</div>
		</div>
		
		<div style="padding-left: 30px;margin: 0px;">
			<input type="button" value="打印" onclick="openPrint();"/>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.WORKFLOW_DEPOSIT_POINT%>"/>
			<jsp:param name="refId" value="${depositPointReg.depositBatchId}"/>
		</jsp:include>
		<br />
		
		<s:if test="batPage.data.size > 0">
		<!-- 批量充值登记簿明细 -->
		<div class="tablebox">
			<b><s:label>批量积分充值登记簿明细 </s:label></b>
			<form id="id_detail_bat" method="post" action="depositPointReg.depositBatchId=${depositPointReg.depositBatchId}">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">			
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <td align="center" nowrap class="titlebg">充值积分</td>
			   <td align="center" nowrap class="titlebg">积分折算金额</td>			   
			   <td align="center" nowrap class="titlebg">状态</td>			   
			</tr>
			</thead>
			<s:iterator value="batPage.data"> 
			<tr>
			  <td nowrap>${cardId }</td>
			  <td align="right" nowrap>${fn:amount(ptPoint)}</td>
			  <td align="right" nowrap>${fn:amount(refAmt)}</td>
			  <td align="center" nowrap>${statusName}</td>	  
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