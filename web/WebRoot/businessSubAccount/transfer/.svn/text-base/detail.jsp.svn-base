<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
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
			<caption>转账记录详细信息<span class="caption_title"> | <f:link href="/businessSubAccount/transfer/list.do">返回列表</f:link></span></caption>
			<tr>
				<td width="15%">付款虚户账号</td>
				<td width="18%">${transBill.payerAccountId}</td>
				<td width="15%">付款客户编号</td>
				<td width="18%">${transBill.payerCustId}</td> 
				<td width="15%">付款客户名称</td>
				<td width="18%">${transBill.payerCustName}</td>
		  	</tr>
		  	<tr>
				<td>收款虚户账号</td>
				<td>${transBill.payeeAccountId}</td>
				<td>收款客户编号</td>
				<td>${transBill.payeeCustId}</td> 
				<td>收款客户名称</td>
				<td>${transBill.payeeCustName}</td>
		  	</tr>  
		  	<tr>
				<td>转账金额</td>
				<td>${fn:amount(transBill.amount)}</td>
				<td>手续费</td>
				<td>${fn:amount(transBill.fee)}</td>
		  		<td>状态</td>
				<td>${transBill.stateName}</td>
		  	</tr> 
		  	<tr>
		  	    <td>创建时间</td>
				<td><s:date name="transBill.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  	    <td>审核时间</td>
				<td><s:date name="transBill.checkTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  	    <td>完成时间</td>
				<td><s:date name="transBill.finishTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  	</tr>
		  	<tr>
		  		<td>是否跨体系</td>
		  		<td>
		  			<c:if test="${transBill.crossSystem eq 'Y'}">是</c:if>
		  			<c:if test="${transBill.crossSystem eq 'N'}">否</c:if>
		  		</td>
		  		<td>备注</td>
				<td colspan="3">${transBill.remark}</td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>