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
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div id="printArea" >
		<div class="userbox">
		<table class="detail_grid" width="99%" border="1" cellspacing="0" cellpadding="1">
			<caption>用户数字证书详细信息<span class="caption_title"> | <f:link href="/pages/userCert/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
			    <td>用户编号</td>
				<td align="center">${userCertificate.userId}</td>
				<td>用户名</td>
				<td align="center">${fn:user(userCertificate.userId)}</td>
				<td>绑定机构</td>
				<td align="center">${fn:branch(userCertificate.bndBranch)}${fn:merch(userCertificate.bndBranch)}</td>		
				<td>所属机构</td>
				<td align="center">${fn:branch(userCertificate.branchCode)}</td>
			</tr>
			
			<tr>
			    <td>证书编号</td>
				<td align="center" colspan="7">${userCertificate.dnNo}</td>				
			</tr>
			
			<tr>			    
				<td>证书序号</td>
				<td align="center">${userCertificate.seqNo}</td>
				<td>证书开通日期</td>
				<td align="center">${userCertificate.startDate}</td>
				<td>证书文件名称</td>
				<td align="center">${userCertificate.fileName}</td>				
				<td>证书绑定状态</td>
				<td align="center">${userCertificate.useStateName}</td>			
			</tr>
			<tr>
				<td>证书生效状态</td>
				<td align="center">${userCertificate.stateName}</td>	
				<td>更新人</td>
				<td align="center">${userCertificate.updateUser}</td>
				<td>更新时间</td>
				<td colspan="5" align="center"><s:date name="userCertificate.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>		  	  	
		</table>
		</div>
        </div>
        
        <div style="padding-left: 30px;margin: 0px;">
			<input type="button" value="打印" onclick="openPrint();"/>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>