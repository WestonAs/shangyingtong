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
		
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>虚户体系详细信息<span class="caption_title"> | <f:link href="/businessSubAccount/accountSystemInfo/list.do">返回列表</f:link></span></caption>
			<tr>
				<td width="15%">虚户体系编号</td>
				<td width="18%">${accountSystemInfo.systemId}</td>
				<td width="15%">虚账户体系名称</td>
				<td width="18%">${accountSystemInfo.systemName}</td>
				<td width="15%">状态</td>
				<td width="18%">
					<s:if test="accountSystemInfo.state == 00">正常</s:if>
					<s:elseif test="accountSystemInfo.state == 01">停用</s:elseif>
				</td>
			</tr>
			<tr>	
				<td>开设机构号</td>
				<td>${accountSystemInfo.custId}</td>
				<td>开设机构名称</td>
				<td>${accountSystemInfo.custName}</td>
				<td>渠道商户号</td>
				<td>${accountSystemInfo.chnlMerNo}</td>
		  	</tr> 
		  	<tr>
		  	    <td>账号</td>
				<td>${accountSystemInfo.acctNo}</td>	
				<td>户名</td>
				<td>${accountSystemInfo.acctName}</td>
				<td>开户行</td>
				<td>${accountSystemInfo.bankName}</td>
		  	</tr>
		  	<tr>
		  	    <td>ftp地址</td>
				<td>${accountSystemInfo.ftpAdd}</td>	
				<td>ftp端口</td>
				<td>${accountSystemInfo.ftpPort}</td>
				<td>ftp用户名</td>
				<td>${accountSystemInfo.ftpUser}</td>
		  	</tr>
		  	<tr>
		  	    <td>ftp密码</td>
				<td>${accountSystemInfo.ftpPwd}</td>	
				<td>ftp路径</td>
				<td>${accountSystemInfo.ftpPath}</td>
				<td>单笔转账手续费</td>
				<td>${fn:amount(accountSystemInfo.unitFee)}</td>	
		  	</tr>
		  	<tr>
		  	    <td>备注</td>
				<td>${accountSystemInfo.remark}</td>
				<td>创建时间</td>
				<td><s:date name="accountSystemInfo.createTime" format="yyyy-MM-dd HH:mm:ss"/> </td>
				<td>最后更新时间</td>
				<td><s:date name="accountSystemInfo.lastUpdateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  	</tr>   
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>