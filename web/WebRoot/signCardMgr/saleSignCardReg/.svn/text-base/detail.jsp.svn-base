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
			<caption>签单卡售卡信息<span class="caption_title"> | <f:link href="/signCardMgr/saleSignCardReg/list.do">返回列表</f:link></span></caption>
			<tr>
				<td>卡号</td>
				<td>${saleSignCardReg.cardId}</td>
		  		<td>售卡状态</td>
				<td>${saleSignCardReg.statusName}</td> 
		  		<td>购卡客户</td>
				<td>${saleSignCardReg.signCustName}</td>				
			</tr>
		  	<tr>				
				<td>工本费</td>
				<td>${saleSignCardReg.expenses}</td>
		  		<td>透支金额</td>
				<td>${fn:amount(saleSignCardReg.overdraft)}</td>
				<td>售卡日期</td>
				<td><s:date name="saleSignCardReg.saleDate" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>		  
		  	<tr>
				<td>下一年费结算日</td>
				<td>${saleSignCardReg.nextBalDate}</td>
				
				<td>持卡人</td>
				<td>${saleSignCardReg.custName}</td>
				<td>证件类型</td>
				<td>${saleSignCardReg.certTypeName}</td>				
			</tr>
		  	<tr>				
				<td>证件号码</td>
				<td>${saleSignCardReg.certNo}</td>				
				<td>邮编</td>
				<td>${saleSignCardReg.zip}</td>
				<td>地址</td>
				<td>${saleSignCardReg.address}</td>				
			</tr>
		  	<tr>
				<td>联系电话</td>
				<td>${saleSignCardReg.phone}</td>
		  		<td>传真号码</td>
				<td>${saleSignCardReg.fax}</td>
		  		<td>Email</td>
				<td>${saleSignCardReg.email}</td>
			</tr>
		  	<tr>
		  		<td>更新人</td>
				<td>${saleSignCardReg.updateUser}</td>
		  		<td>更新时间</td>
				<td><s:date name="saleSignCardReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  		<td>备注</td>
		  		<td>${saleSignCardReg.remark}</td>
			</tr>
		  	
		</table>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>