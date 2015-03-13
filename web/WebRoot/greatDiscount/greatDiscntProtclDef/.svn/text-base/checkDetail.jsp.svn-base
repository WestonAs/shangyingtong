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
			<caption><span class="caption_title">高级折扣协议详细信息 | <f:link href="/greatDiscount/greatDiscntProtclDef/checkList.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>折扣协议号</td>
				<td >
					${greatDiscntProtclDef.greatDiscntProtclId}
				</td>
				<td>折扣协议名</td>
				<td >
					${greatDiscntProtclDef.greatDiscntProtclName}
				</td>
			</tr>
			<tr>
				<td>联名发卡机构</td>
				<td >
					${greatDiscntProtclDef.cardIssuer}
				</td>
				<td >商户</td>
				<td>
					${greatDiscntProtclDef.merchNo}
				</td>
			</tr>
			<tr>	
				<td>卡bin范围</td>
				<td>
					${greatDiscntProtclDef.cardBinScope}
				</td>
				<td>状态</td>
				<td>
					${greatDiscntProtclDef.ruleStatusName}
				</td>
			</tr>
			<tr>
				<td>发卡机构-持卡人折扣率</td>
				<td >
					${greatDiscntProtclDef.issuerHolderDiscntRate}
				</td>
				<td>发卡机构-商户折扣率</td>
				<td >
					${greatDiscntProtclDef.issuerMerchDiscntRate}
				</td>
			</tr>
			<tr>	
				<td >交易支付方式</td>
				<td>
					${greatDiscntProtclDef.payWayName}
				</td>
				<td>折扣排他标志</td>
				<td >
					${greatDiscntProtclDef.discntExclusiveFlag}
				</td>
			</tr>
			<tr>
				<td>生效日期</td>
				<td >
				    ${greatDiscntProtclDef.effDate}
				</td>
				<td>失效日期</td>
				<td >
			    	${greatDiscntProtclDef.expirDate}
				</td>
			</tr>
			<tr>
				<td>纸质协议号</td>
				<td >
					${greatDiscntProtclDef.protclPaperNo}
				</td>
				<td>运营机构收益</td>
				<td >
					${greatDiscntProtclDef.chlIncomeFee}
				</td>
			</tr>
			<tr>
				<td>更新时间</td>
				<td >
					<s:date name="greatDiscntProtclDef.updateTime" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>更新人</td>
				<td >
					${greatDiscntProtclDef.updateBy}
				</td>
			</tr>
			<tr>
				<td>入库时间</td>
				<td>
					<s:date name="greatDiscntProtclDef.insertTime" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>备注</td>
				<td>
					${greatDiscntProtclDef.remark}
				</td>
			</tr>
		</table>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.WORKFLOW_GREAT_DISCNT_PROTCL_DEF%>"/>
			<jsp:param name="refId" value="${greatDiscntProtclDef.greatDiscntProtclId}"/>
		</jsp:include>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>