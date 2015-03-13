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
		<style type="text/css">
			html { overflow-y: scroll; }
			#tranEnableDiv table table .headcell { text-align: right; }
		</style>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
				<caption>${ACT.name}<span class="caption_title"> | <f:link href="/para/webServiceConf/wsMsgTypeCtrl/list.do?goBack=goBack">返回列表</f:link></span></caption>
				<tr>
					<td>客户机构编号-名称</td>
					<td>${wsMsgTypeCtrl.branchCode}-${fn:branch(wsMsgTypeCtrl.branchCode)}</td>
					
					<td>接口类型</td>
					<td>${wsMsgTypeCtrl.msgType}</td>
					
					<td>关联发卡机构编号-名称</td>
					<td>${wsMsgTypeCtrl.relatedIssNo}-${fn:branch(wsMsgTypeCtrl.relatedIssNo)}</td>
				</tr>
				<tr>
					<td>状态</td>
					<td>
						<s:if test='wsMsgTypeCtrl.status=="1"'>正常</s:if>
						<s:else><font color="red">注销</font></s:else>
					</td>
					
			  		<td>备注</td>
					<td>${wsMsgTypeCtrl.remark }</td>
					
					<td></td>
					<td></td>
			  	</tr>
				<tr>
					<td>插入时间</td>
					<td><s:date name="wsMsgTypeCtrl.insertTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					
					<td>更新时间</td>
					<td><s:date name="wsMsgTypeCtrl.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					
					<td>更新用户名</td>
					<td>${wsMsgTypeCtrl.updateBy}</td>

				</tr>
			</table>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>