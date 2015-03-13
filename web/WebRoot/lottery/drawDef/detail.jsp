<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="gnete.etc.WorkflowConstants"%>
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
			<caption>抽奖活动定义详细信息<span class="caption_title"> | <f:link href="/lottery/drawDef/list.do">返回列表</f:link></span></caption>
			<tr>
				<td width="80" height="30" align="right">抽奖活动ID</td>
				<td>${drawDef.drawId}</td>
				
				<td width="80" height="30" align="right">抽奖活动名称</td>
				<td>${drawDef.drawName}</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">抽奖方法</td>
				<td>${drawDef.drawTypeName}</td>

				<td width="80" height="30" align="right">抽奖活动简称</td>
				<td>${drawDef.drawShortName}</td>
			</tr>
			
			<tr>
				<td width="80" height="30" align="right">生效日期</td>
				<td>${drawDef.effDate}</td>
				<td width="80" height="30" align="right">失效日期</td>
				<td>${drawDef.expirDate}</td>
			</tr>
			
			<tr>
				<td id="pInstType_td1" width="80" height="30" align="right" >参与机构类型</td>
				<td id="pInstType_td2" >${drawDef.pinstTypeName}</td>
				<td id="pinstId_td1" width="80" height="30" align="right" >参与机构编号</td>
				<td id="pinstId_td2" >${drawDef.pinstId}-${fn:branch(drawDef.pinstId)}${fn:merch(drawDef.pinstId)}</td>
			</tr>
			
			<c:if test="${showBrushSet}">
			<tr>
				<td width="80" height="30" align="right">卡BIN范围</td>
				<td>${drawDef.cardBinScope}</td>

				<td width="80" height="30" align="right">交易类型</td>
				<td>${drawDef.transTypeName}</td>
			</tr>
			
			<tr>
				<td width="80" height="30" align="right">金额类型</td>
				<td>${drawDef.amtTypeName}</td>

				<td width="80" height="30" align="right">参考金额</td>
				<td>${drawDef.refAmt}</td>
			</tr>
			
			<tr>
				<td width="80" height="30" align="right">概率折算方法</td>
				<td>${drawDef.probTypeName}</td>
				<td width="80" height="30" align="right">基准概率</td>
				<td>${drawDef.probBase}</td>
			</tr>
			
			<tr>
				<td width="80" height="30" align="right">最大概率倍数</td>
				<td>${drawDef.probMax}</td>
				<td width="80" height="30" align="right">最小概率倍数</td>
				<td>${drawDef.probMin}</td>
			</tr>
			</c:if>
		  	<tr>
				<td>更新人</td>
				<td>${drawDef.defOptr}</td>
				<td>更新时间</td>
				<td><s:date name="drawDef.defTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>
        <!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.DRAW_DEFINE%>"/>
			<jsp:param name="refId" value="${drawDef.drawId}"/>
		</jsp:include>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>