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
		
		<div id="printArea" >
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>机构详细信息<span class="caption_title"> | <f:link href="/pages/branch/checkList.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>机构编号</td>
				<td>${branch.branchCode}</td>
				<td>机构名称</td>
				<td>${branch.branchName}</td>
		  	</tr>
			<tr>
				<td>机构简称</td>
				<td>${branch.branchAbbname}</td>
				<td>机构币种</td>
				<td>${branch.curCode}</td>
		  	</tr>
			<tr>
				<td>机构类型</td>
				<td>${branchHasTypes}</td>
				<td>机构索引号</td>
				<td>${branch.branchIndex}</td>
		  	</tr>
		  	<s:if test="showSettle">
			  	<tr>
					<td>刷卡充值是否独立清算</td>
					<td><s:if test='"Y".equals(branch.isSettle)'>是</s:if><s:elseif test='"N".equals(branch.isSettle)'>否</s:elseif></td>
					<td></td>
					<td></td>
			  	</tr>
		  	</s:if>
		  	<s:if test="showSetMode">
			  	<tr>
					<s:if test="branch.status == 20 && cardBranch">
						<td>清算模式</td>
						<td>${setModeName}</td>
						<td>资信额度</td>
						<td>${branch.cardRiskAmt}万元</td>
					</s:if>
					<s:else>
						<td>清算模式</td>
						<td>${setModeName}</td>
						<td></td>
						<td></td>
					</s:else>
			  	</tr>
		  	</s:if>
		  	<tr>
		  		<td>上级机构</td>
				<td>${branch.superBranchCode}-${fn:branch(branch.superBranchCode)}</td>
				<td>管理机构</td>
				<td>${fn:branch(branch.parent)}</td>
			</tr>
			
		  	<tr>
				<td>所属地区</td>
				<td>${areaName}</td>
				<td>地址</td>
				<td>${branch.address}</td>
		  	</tr>
		  	<tr>
				<td>联系人</td>
				<td>${branch.contact}</td>
				<td>联系电话</td>
				<td>${branch.phone}</td>
		  	</tr>
			<tr>
				<td>传真号码</td>
				<td>${branch.fax}</td>
				<td>邮编</td>
				<td>${branch.zip}</td>
		  	</tr>
		  	<tr>
				<td>账户类型</td>
				<td>${branch.acctTypeName}</td>
				<td>账户地区</td>
				<td>${accAreaName}</td>
		  	</tr>
			<tr>
				<td>开户行行号</td>
				<td>${branch.bankNo}</td>
				<td>开户行名称</td>
				<td>${branch.bankName}</td>
		  	</tr>
			<tr>
				<td>账号</td>
				<td>${branch.accNo}</td>
				<td>户名</td>
				<td>${branch.accName}</td>
		  	</tr>
		  	<tr>
				<td>风险等级</td>
				<td>${branch.riskLevelName}</td>
				<td></td>
				<td></td>
			</tr>
		  	<tr>
				<td>营业执照</td>
				<td>${branch.license}</td>
				<td>营业执照有效期</td>
				<td><s:date name="branch.licenseExpDate" format="yyyy-MM-dd" /></td>
		  	</tr>
			<tr>
				<td>组织机构号</td>
				<td>${branch.organization}</td>
				<td>组织机构代码有效期</td>
				<td>${branch.organizationExpireDate}</td>
		  	</tr>
		  	<tr>
				<td>税务登记号</td>
				<td>${branch.taxRegCode}</td>
				<td>法人姓名</td>
				<td>${branch.legalPersonName}</td>
		  	</tr>
		  	<tr>
				<td>法人身份证号码</td>
				<td>${branch.legalPersonIdcard}</td>
				<td>法人身份证有效期</td>
				<td><s:date name="branch.legalPersonIdcardExpDate" format="yyyy-MM-dd" /></td>
		  	</tr>
			<s:if test="cardBranch">
		  		<tr>
			  		<td>是否单机产品机构</td>
					<td>${branch.singleProductName}</td>
					<td></td>
					<td></td>
		  		</tr>
		  	</s:if>
		  	<tr>
		  		<td>备注</td>
		  		<td>${branch.remark}</td>
				<td>状态</td>
				<td>${branch.statusName}</td>
		  	</tr>
			<tr>
				<td>更新人</td>
				<td>${branch.updateUser}</td>
				<td>更新时间</td>
				<td><s:date name="branch.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>
		</div>
		<div style="padding-left: 30px;margin: 0px;">
			<input type="button" value="打印" onclick="openPrint();"/>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.WORKFLOW_ADD_BRANCH%>"/>
			<jsp:param name="refId" value="${branch.branchCode}"/>
		</jsp:include>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>