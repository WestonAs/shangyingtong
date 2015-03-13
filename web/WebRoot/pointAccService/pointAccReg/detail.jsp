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
				<caption>${ACT.name}<span class="caption_title"> | <f:link href="/pointAccService/pointAccReg/list.do?goBack=goBack">返回列表</f:link></span></caption>
				<tr>
					<td>编号</td>	
					<td>${pointAccReg.pointAccId}</td>	
					<td>业务类型</td>
					<td>${pointAccReg.transTypeName}</td>
				</tr>
				<tr>
					<td>发卡机构</td>
					<td>${pointAccReg.cardIssuer}-${fn:branch(pointAccReg.cardIssuer)}</td>	
					<td>业务代码</td>	
					<td>${pointAccReg.eventCode}</td>	
				</tr>
				<tr>
					<td>文件名称</td>
					<td colspan="3">${pointAccReg.fileName}</td>	
				</tr>
				<tr>
					<td>记录总数</td>	
					<td>${pointAccReg.recordNum}</td>	
					<td>总金额</td>
					<td>${fn:amount(pointAccReg.amt)}</td>	
				</tr>
				<tr>
					<td>导入时间</td>	
					<td><s:date name = "pointAccReg.time" format="yyyy-MM-dd HH:mm:ss"/></td>	
					<td>状态</td>
					<td>${pointAccReg.statusName}</td>	
				</tr>
				<tr>
					<td>短信登记编号</td>
					<td>${pointAccReg.messageRegId}</td>	
					<td>更新时间</td>	
					<td><s:date name = "pointAccReg.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>	
				</tr>
				<tr>
					<td>备注</td>
					<td colspan="3">${pointAccReg.remark}</td>	
				</tr>
			</table>
			</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>
