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
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">流程编号</td>
							<td><s:textfield name="workflow.workflowId" cssClass="{digitOrLetter:true}"/></td>
							
							<td align="right">流程名称</td>
							<td><s:textfield name="workflow.workflowName"/></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="workflowmgr"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/workflow/define/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_WORKFLOW_DEFINE_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">流程编号</td>
			   <td align="center" nowrap class="titlebg">流程名称</td>
			   <td align="center" nowrap class="titlebg">录入权限点</td>
			   <td align="center" nowrap class="titlebg">审批权限点</td>
			   <td align="center" nowrap class="titlebg">默认审批级数</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${workflowId}</td>
			  <td align="left" nowrap>${workflowName}</td>
			  <td align="left" nowrap>${inputLimit}</td>
			  <td align="left" nowrap>${auditLimit}</td>
			  <td align="center" nowrap>${defaultLevel}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:pspan pid="workflowmgr"><f:link href="/workflow/define/showModify.do?workflow.workflowId=${workflowId}">编辑</f:link></f:pspan>
		  			<f:pspan pid="workflowmgr">
		  				<a href="javascript:submitUrl('searchForm', '/workflow/define/delete.do?workflowId=${workflowId}', '确定要删除吗，流程配置相关的全部数据都会被删除？');" />删除</a>
		  			</f:pspan>
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>