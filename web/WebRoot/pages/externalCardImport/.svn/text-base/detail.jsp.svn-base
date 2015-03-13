<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<s:if test='externalCardImportReg.externalNumMakeCard'>
			<s:set var="pageTitle" value='"外部号码开卡"'/>
			<s:set var="listActionDo" value='"listExternalNumMakeCard.do"'/>
		</s:if>
		<s:else>
			<s:set var="pageTitle" value='"外部卡导入"'/>
			<s:set var="listActionDo" value='"list.do"'/>
		</s:else>
		<title>${pageTitle}</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<div class="location">您当前所在位置： <span class="redlink"><a href="<%=_contextPath%>/home.jsp">首页</a></span> > 综合业务  > ${pageTitle}</div>
		
		<div class="userbox">
			<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
				<caption>
					${pageTitle }详细信息 
					<span class="caption_title"> |
						<s:if test="formMap.useReferer=='true'">
							<a href="#" onclick="window.history.back();">返回列表</a>
						</s:if>
						<s:else>
							<f:link href="/pages/externalCardImport/${listActionDo}?goBack=goBack">返回列表</f:link>
						</s:else> 
					</span>
				</caption>
				<tr>
					<td>发卡机构</td>
					<td>${externalCardImportReg.cardBranch}-${fn:branch(externalCardImportReg.cardBranch)}</td>
					<td>卡BIN</td>
					<td>${externalCardImportReg.binNo}</td>
					<td>卡子类型</td>
					<td>${externalCardImportReg.cardSubclassName}</td>
			  	</tr>
				<tr>
					<td>文件名</td>
					<td>${externalCardImportReg.fileName}</td>
					<td>总笔数</td>
					<td>${externalCardImportReg.totalCount}</td>
					<td>总金额</td>
					<td>${fn:amount(externalCardImportReg.totalAmt)}</td>
			  	</tr>
				<tr>
					<td>总积分</td>
					<td>${externalCardImportReg.totalPoint}</td>
					<td>手工备注</td>
					<td>${externalCardImportReg.remark}</td>
					<td>状态</td>
					<td>${externalCardImportReg.statusName}</td>
			  	</tr>
			  	<tr>
					<td>更新人</td>
					<td>${externalCardImportReg.updateBy}</td>
					<td>更新时间</td>
					<td><s:date name="externalCardImportReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>后台处理备注</td>
					<td colspan="3">${externalCardImportReg.memo}</td>
			  	</tr>
			</table>
		</div>
		
		<s:if test=" externalCardImportReg.status == @gnete.card.entity.state.ExternalCardImportState@DEAL_FAILED.value">
			<div class="tablebox">
				<b><s:label>失败原因明细 </s:label></b>
				<form method="post" action="detail.do?externalCardImportReg.id=${externalCardImportReg.id}">
					<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">			
						<thead>
							<tr>
							   <td align="center" nowrap class="titlebg">导入id</td>
							   <td align="center" nowrap class="titlebg">明细行号</td>
							   <td align="center" nowrap class="titlebg">发卡机构</td>			   
							   <td align="center" nowrap class="titlebg">卡号（号码）</td>			   
							   <td align="center" nowrap class="titlebg">金额</td>			   
							   <td align="center" nowrap class="titlebg">积分</td>			   
							   <td align="center" nowrap class="titlebg">操作类型</td>			   
							   <td align="center" nowrap class="titlebg">处理状态</td>			   
							   <td align="center" nowrap class="titlebg">错误消息</td>			   
							   <td align="center" nowrap class="titlebg">导入时间</td>			   
							</tr>
						</thead>
						<s:iterator value="page.data"> 
							<tr>
							  <td nowrap>${impId}</td>
							  <td align="center" nowrap>${seqNo}</td>
							  <td align="center" nowrap>${cardIssuer}</td>
							  <td align="center" nowrap>${cardId}</td>
							  <td align="center" nowrap>${amount}</td>	  
							  <td align="center" nowrap>${ptAvlb}</td>	  
							  <td align="center" nowrap>${otTypeDesc}</td>	  
							  <td align="center" nowrap>${procStatusDesc}</td>	  
							  <td align="center" nowrap>${errMsg}</td>	  
							  <td align="center" nowrap><s:date name="impDate" format="yyyy-MM-dd HH:mm:ss" /></td>	  
							</tr>
						</s:iterator>
					</table>
				</form>
				<f:paginate name="page"/>
			</div>
		</s:if>
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=gnete.etc.WorkflowConstants.WORKFLOW_EXTERNAL_NUM_MAKE_CARD %>"/>
			<jsp:param name="refId" value="${externalCardImportReg.id}"/>
		</jsp:include>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>