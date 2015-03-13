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
				<caption>${ACT.name}<span class="caption_title"> | <f:link href="/unfreeze/list.do?goBack=goBack">返回列表</f:link></span></caption>
				<tr>
					<td>解付编号</td>
					<td>${UnfreezeReg.unfreezeId}</td>	
					<td>卡号</td>	
					<td>${UnfreezeReg.cardId}</td>	
				</tr>
				<tr>
					<td>账号</td>
					<td>${UnfreezeReg.acctId}</td>
					<td>子账户类型</td>
					<td>${UnfreezeReg.subacctTypeName}</td>
				</tr>
				<tr>
					<td>可用余额</td>
					<td>${fn:amount(UnfreezeReg.avlbBal)}</td>	
					<td>冻结金额</td>	
					<td>${fn:amount(UnfreezeReg.fznAmt)}</td>
				</tr>
				<tr>
					<td>解付金额</td>	
					<td>${fn:amount(UnfreezeReg.unfznAmt)}</td>	
					<td>状态</td>
					<td>${UnfreezeReg.statusName}</td>	
				</tr>
				<tr>
					<td>操作机构</td>	
					<td>${UnfreezeReg.branchCode }</td>	
					<td>机构名称</td>
					<td>${UnfreezeReg.branchName}</td>	
				</tr>
				<tr>
					<td>操作员</td>	
					<td>${UnfreezeReg.updateUser}</td>	
					<td>操作时间</td>
					<td><s:date name = "UnfreezeReg.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>	
				</tr>
				<tr>
					<td>备注</td>	
					<td colspan="3">${UnfreezeReg.remark}</td>	
				</tr>
			</table>
			</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>
