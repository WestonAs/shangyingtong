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
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>缴费审核详细信息<span class="caption_title"> | <f:link href="/washCarService/washCarServiceMemberShipDues/checkList.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>编号</td>
				<td>${washCarSvcMbShipDues.id}</td>
				<td>卡号</td>
				<td>${washCarSvcMbShipDues.cardId}</td>
			</tr>
			<tr>
				<td>外部卡号</td>
				<td>${washCarSvcMbShipDues.externalCardId}</td>
				<td>缴费金额</td>
				<td>${washCarSvcMbShipDues.tollAmt}</td>
			</tr>
			<tr>
				<td>缴费状态</td>
				<td>${washCarSvcMbShipDues.statusName}</td>
				<td>pos缴费时间</td>
				<td>${washCarSvcMbShipDues.posTollDate}</td>
				
			</tr>
			<tr>
				<td>缴费有效结束时间</td>
				<td>${washCarSvcMbShipDues.tollEndDate}</td>
				<td>缴费有效开始时间</td>
				<td>${washCarSvcMbShipDues.tollStartDate}</td>
			</tr>
			<tr>
				<td>pos缴费银行卡号</td>
				<td>${washCarSvcMbShipDues.posTollBankCard}</td>
				<td>插入时间</td>
				<td><s:date name="washCarSvcMbShipDues.insertTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				
				<td>插入操作人</td>
				<td>${washCarSvcMbShipDues.insertUser}</td>
				<td>更新用户名</td>
				<td>${washCarSvcMbShipDues.updateUser}</td>
			</tr>
			<tr>
				<td>更新时间</td>
				<td><s:date name="washCarSvcMbShipDues.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>审核状态</td>
				<td>${washCarSvcMbShipDues.checkStatusName}</td>
			</tr> 
			<tr>
				<td>备注</td>
				<td>${washCarSvcMbShipDues.remark}</td>
				<td></td>
				<td></td>
			</tr>
		</table>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.WASH_CAR_SVC_MB_SHIP_DUES%>"/>
			<jsp:param name="refId" value="${washCarSvcMbShipDues.id}"/>
		</jsp:include>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>