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
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>积分返利记录详细信息<span class="caption_title"> | <f:link href="/pointExchg/pointExc/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>积分返利编号</td>
				<td>${pointExcReg.pointExcId}</td>
				<td>卡号</td>
				<td>${pointExcReg.cardId}</td>
			</tr>
			<tr>
				<td>机构编号</td>
				<td>${pointExcReg.jinstId}</td>
				<td>机构名称</td>
				<td>${fn:branch(pointExcReg.jinstId)}</td>
			</tr>
			<tr>
				<td>积分类型</td>
				<td>${pointExcReg.ptClass}</td>
				<td>积分名称</td>
				<td>${pointClassDef.className}</td>
			</tr>
			<tr>
				<td>参考积分</td>
				<td>${fn:amount(pointExcReg.ptRef)}</td>
				<td>兑换率</td>
				<td>${pointExcReg.ptDiscntRate}</td>
			</tr>
			</tr>
				<td>返利积分</td>
				<td>${fn:amount(pointExcReg.excPoint)}</td>
				<td>返利金额</td>
				<td>${fn:amount(pointExcReg.excAmt)}</td>
			</tr>
			</tr>
				<td>状态</td>
				<td>${pointExcReg.statusName}</td>
				<td>备注</td>
				<td>${pointExcReg.remark}</td>
			</tr>
		  	<tr>
				<td>更新用户名</td>
				<td>${pointExcReg.updateBy}</td>
		  		<td>更新时间</td>
				<td><s:date name="pointExcReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>