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
			<div class="userbox">
			<table class="detail_grid" width="80%" border="1" cellspacing="0" cellpadding="1">
				<caption>卡挂失审核详细信息<span class="caption_title"> | <f:link href="/intgratedService/losscard/checkList.do?goBack=goBack">返回列表</f:link></span></caption>
				<tr>
					<td>挂失编号</td>
					<td>${lossCardReg.lossBatchId}</td>	
					<td>挂失类型</td>
					<td>${lossCardReg.batName}</td>
				</tr>
				<s:if test='lossCardReg.cardId == null || lossCardReg.cardId == ""'>
				<tr>
					<td>起始卡号</td>
					<td>${lossCardReg.startCard}</td>	
					<td>结束卡号</td>
					<td>${lossCardReg.endCard}</td>	
				</tr>
				<tr>
					<td>卡数量</td>
					<td>${lossCardReg.cardNum}</td>	
					<td>发卡机构</td>
					<td>${fn:branch(lossCardReg.cardBranch) }</td>
				</tr>
				</s:if>
				<s:else>
				<tr>
					<td>卡号</td>	
					<td>${lossCardReg.cardId}</td>	
					<td>持卡人姓名</td>
					<td>${lossCardReg.custName}</td>
				</tr>
				<tr>
					<td>证件类型</td>
					<td>${lossCardReg.certTypeName}</td>	
					<td>证件号码</td>	
					<td>${lossCardReg.certNo}</td>	
				</tr>
				</s:else>
				<tr>
					<td>状态</td>
					<td>${lossCardReg.statusName}</td>	
					<td>操作员</td>	
					<td>${lossCardReg.updateUser}</td>	
				</tr>
				<tr>
					<td>操作机构</td>	
					<td>${lossCardReg.branchCode }</td>	
					<td>机构名称</td>
					<td>${lossCardReg.branchName}</td>	
				</tr>
				<tr>
					<td>操作时间</td>
					<td><s:date name = "lossCardReg.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>	
					<td>备注</td>	
					<td>${lossCardReg.remark}</td>	
				</tr>
			</table>
			</div>
			
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.WORKFLOW_LOSS_CARD%>"/>
			<jsp:param name="refId" value="${lossCardReg.lossBatchId}"/>
		</jsp:include>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>
