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
			<caption>成品卡入库详细信息<span class="caption_title"> | <f:link href="/cardStock/finishedCard/list.do?goBack=goBack">返回列表</f:link></span></caption>
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								${fn:branch(cardInput.branchCode)}
							</td>
							<td width="80" height="30" align="right">卡类型</td>
							<td>
								${cardInput.cardTypeName}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">入库日期</td>
							<td>
								${cardInput.inputDate}
							</td>
							<td width="80" height="30" align="right">入库卡数量</td>
							<td>
								${cardInput.inputNum}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">起始卡号</td>
							<td>
								${cardInput.strNo}
							</td>
							<td width="80" height="30" align="right">结束卡号</td>
							<td>
								${cardInput.endNo}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">状态</td>
							<td>
								${cardInput.statusName}
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
								${cardInput.memo}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">审核人代码</td>
							<td>
								${cardInput.chkUser}
							</td>
							<td width="80" height="30" align="right">审核时间</td>
							<td>
								<s:date name="cardInput.chkTime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">更新用户名</td>
							<td>
								${cardInput.updateBy}
							</td>
							<td width="80" height="30" align="right">更新时间</td>
							<td>
								<s:date name="cardInput.updateTime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
		</table>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.FINISHED_CARD_STOCK%>"/>
			<jsp:param name="refId" value="${cardInput.id}"/>
		</jsp:include>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>