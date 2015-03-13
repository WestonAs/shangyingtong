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
				<caption>${ACT.name}<span class="caption_title"> | <f:link href="/intgratedService/unlosscard/list.do?goBack=goBack">返回列表</f:link></span></caption>
				<tr>
					<td>解挂编号</td>
					<td>${unlossCardReg.unlossBatchId}</td>	
					<td>卡号</td>	
					<td>${unlossCardReg.cardId}</td>	
				</tr>
				<tr>
					<td>持卡人姓名</td>
					<td>${unlossCardReg.custName}</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>证件类型</td>
					<td>${unlossCardReg.certTypeName}</td>	
					<td>证件号码</td>	
					<td>${unlossCardReg.certNo}</td>	
				</tr>
				<tr>
					<td>状态</td>
					<td>${unlossCardReg.statusName}</td>	
					<td>操作员</td>	
					<td>${unlossCardReg.updateUser}</td>	
				</tr>
				<tr>
					<td>操作机构</td>	
					<td>${unlossCardReg.branchCode }</td>	
					<td>机构名称</td>
					<td>${unlossCardReg.branchName}</td>	
				</tr>
				<tr>
					<td>操作时间</td>
					<td><s:date name = "unlossCardReg.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>	
					<td>备注</td>	
					<td>${unlossCardReg.remark}</td>	
				</tr>
			</table>
			</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>
