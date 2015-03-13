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
			<caption>渠道交易详细信息<span class="caption_title"> | <f:link href="/businessSubAccount/chnlTrade/list.do">返回列表</f:link></span></caption>
			<tr>
				<td width="15%">交易编号</td>
				<td width="18%">${channelTrade.id}</td>
				<td width="15%">交易类型</td>
				<td width="18%">${channelTrade.tradeTypeName}</td> 
				<td width="15%">关联交易号</td>
		  		<td width="18%">${channelTrade.refId}</td>
		  	</tr>
		  	<tr>
		  	    <td>交易金额</td>
				<td>${fn:amount(channelTrade.amount)}</td>
				<td>行别</td>
				<td>${channelTrade.bankTypeName}</td>
				<td>开户行</td>
				<td>${channelTrade.bankName}</td> 
		  	</tr>  
		  	<tr>
		  		<td>账号</td>
				<td>${channelTrade.acctNo}</td>
				<td>户名</td>
				<td>${channelTrade.acctName}</td>
				<td>创建时间</td>
				<td><s:date name="channelTrade.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  	</tr> 
		  	<tr>
		  		<td>回盘时间</td>
				<td><s:date name="channelTrade.returnTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  		<td>交易返回码</td>
		  		<td>
		  			${channelTrade.returnCode}
		  		</td>
		  		<td>交易返回信息</td>
		  		<td>
		  			${channelTrade.returnInfo}
		  		</td>
		  	</tr>
		  	<tr>
		  		<td>交易状态</td>
		  		<td>
		  			${channelTrade.returnStateName}
		  		</td>
		  		<td>交易结果</td>
		  		<td>
		  			${channelTrade.resultName}
		  		</td>
		  		<td>交易文件名</td>
				<td class="redlink"><f:link href="/businessSubAccount/chnlTrade/download.do?channelTrade.id=${channelTrade.id}&channelTrade.fileType=T">${channelTrade.tradeFileName}</f:link></td>
		  		</td>
		  	</tr>
		  	<tr>
				<td>回盘文件名</td>
				<td class="redlink"><f:link href="/businessSubAccount/chnlTrade/download.do?channelTrade.id=${channelTrade.id}&channelTrade.fileType=R">${channelTrade.returnFileName}</f:link></td>
				<td>备注</td>
				<td colspan="3">${channelTrade.remark}</td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>