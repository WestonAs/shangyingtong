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
		<script type="text/javascript">
			function check(id, pass){
				var state = "";
				if(pass){
					state = "00";
				}else{
					state = "05";
				}
				gotoUrl("/businessSubAccount/businessSubAccountInfo/changeState.do?businessSubAccountInfo.accountId="+id+"&businessSubAccountInfo.state="+state);
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>账户详细信息<span class="caption_title"> | <f:link href="/businessSubAccount/businessSubAccountInfo/list.do">返回列表</f:link></span></caption>
			<tr>
				<td width="15%">客户编号</td>
				<td width="18%">${businessSubAccountInfo.custId}</td>
				<td width="15%">客户类型</td>
				<td width="18%">
					<s:if test="businessSubAccountInfo.subAccountType == 0">商户</s:if>
				  	<s:else>机构</s:else>
				 </td>
				<td width="15%">账户名称</td>
				<td width="18%">${businessSubAccountInfo.custName}</td> 
		  	</tr>
		  	<tr>
				<td>账户余额</td>
				<td>${businessSubAccountInfo.cashAmount}</td>
				<td>冻结金额</td>
				<td>${businessSubAccountInfo.freezeCashAmount}</td>
				<td>状态</td>
				<td>${businessSubAccountInfo.stateName}</td> 
		  	</tr>
		  	<tr>
				<td>创建时间</td>
				<td><s:date name="businessSubAccountInfo.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>最后修改时间</td>
				<td colspan="3"/><s:date name="businessSubAccountInfo.lastUpdateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  	</tr>
		</table>
		</div>
		<div style="margin-top:3px; text-align:left">
			<input type="button" class="inp_L3" value="审核通过" onclick="check(${businessSubAccountInfo.accountId}, true)" id="btn"/>&nbsp;
			<input type="button" class="inp_L3" value="审核不通过" onclick="check(${businessSubAccountInfo.accountId}, false)"/>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>