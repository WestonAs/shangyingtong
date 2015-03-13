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
				<td>${fn:amount(businessSubAccountInfo.cashAmount)}</td>
				<td>冻结金额</td>
				<td>${fn:amount(businessSubAccountInfo.freezeCashAmount)}</td>
				<td>状态</td>
				<td>${businessSubAccountInfo.stateName}</td> 
		  	</tr>
		  	<tr>
				<td>创建时间</td>
				<td><s:date name="businessSubAccountInfo.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>最后修改时间</td>
				<td colspan="3"><s:date name="businessSubAccountInfo.lastUpdateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  	</tr>
		</table>
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1" style="margin-top: 20px">
			<caption>所属体系信息</caption>
			<tr>
				<td width="15%">体系编号</td>
				<td width="18%">${asi.systemId}</td>
				<td width="15%">体系名称</td>
				<td width="18%">${asi.systemName}</td>
				<td width="15%">所属机构</td>
				<td width="18%">${asi.custName}</td> 
		  	</tr>
		  	<tr>
				<td>开户行</td>
				<td>${asi.bankName}</td>
				<td>账号</td>
				<td>${asi.acctNo}</td>
				<td>户名</td>
				<td>${asi.acctName}</td>
		  	</tr>
		  	<tr>
		  		<td>单笔转账手续费</td>
				<td colspan="5">${fn:amount(asi.unitFee)}</td>
		  	</tr>
		</table>
		</div>
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<s:if test="#request.list.size > 0">
			<thead>
					<tr>
						<td align="center" nowrap class="titlebg">
							账号
						</td>
						<td align="center" nowrap class="titlebg">
							户名
						</td>
						<td align="center" nowrap class="titlebg">
							开户行
						</td>
						<td align="center" nowrap class="titlebg">
							是否默认账户
						</td>
						<td align="center" nowrap class="titlebg">
							绑定时间
						</td>
					</tr>
				</thead>
			</s:if>	
			<s:iterator value="#request.list"> 
			<tr>
			  <td align="center" nowrap>${bankAcct.acctNo}</td>
			  <td align="center" nowrap>${bankAcct.acctName}</td>
			  <td align="center" nowrap>${bankAcct.bankName}</td>
			  <td align="center" nowrap>
			  	<c:if test="${'Y' eq isDefault}">是</c:if>
			  	<c:if test="${'Y' ne isDefault}">否</c:if>
			  </td>
			  <td align="center" nowrap><s:date name="bankAcct.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>