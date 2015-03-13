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
			<caption>商户详细信息<span class="caption_title"> | <f:link href="/pages/merch/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>商户编号</td>
				<td colspan="3">${merchInfo.merchId}</td>
		  	</tr>
			<tr>
				<td>商户名称</td>
				<td>${merchInfo.merchName}</td>
				<td>商户简称</td>
				<td>${merchInfo.merchAbb}</td>
		  	</tr>
			<tr>
				<td>英文名称</td>
				<td>${merchInfo.merchEn}</td>
				<td>商户类型</td>
				<td>${merchTypeName}</td>
		  	</tr>
		  	<tr>
				<td>管理机构编号</td>
				<td>${fn:branch(merchInfo.manageBranch)}</td>
				<td></td>
				<td></td>
		  	</tr>
			<tr>
				<td>地区</td>
				<td>${areaName}</td>
				<td>货币代码</td>
				<td>${merchInfo.currCode}</td>
		  	</tr>
			<tr>
				<td>联系人</td>
				<td>${merchInfo.linkMan}</td>
				<td>联系电话</td>
				<td>${merchInfo.telNo}</td>
		  	</tr>
			<tr>
				<td>传真</td>
				<td>${merchInfo.faxNo}</td>
				<td>邮件地址</td>
				<td>${merchInfo.email}</td>
		  	</tr>
			<tr>
				<td>商户地址</td>
				<td>${merchInfo.merchAddress}</td>
				<td>客户经理</td>
				<td>${merchInfo.unPay}</td>
		  	</tr>
		  	<tr>
				<td>账户介质类型</td>
				<td>${merchInfo.acctMediaTypeName}</td>
				<td>直属发卡机构</td>
				<td>${merchInfo.cardBranch}-${fn:branch(merchInfo.cardBranch)}</td>
		  	</tr>
		  	<tr>
				<td>账户类型</td>
				<td>${merchInfo.acctTypeName}</td>
				<td>账户地区</td>
				<td>${accAreaName}</td>
		  	</tr>
			<tr>
				<td>开户行名</td>
				<td>${merchInfo.bankName}</td>
				<td>开户行号</td>
				<td>${merchInfo.bankNo}</td>
		  	</tr>
			<tr>
				<td>账户户名</td>
				<td>${merchInfo.accName}</td>
				<td>账号</td>
				<td>${merchInfo.accNo}</td>
		  	</tr>
		  	<!-- 
			<tr>
				<td>营业时间</td>
				<td><s:date name="merchInfo.businessTime" format="yyyy年MM月dd日 HH:mm:ss" /></td>
				<td>成立时间</td>
				<td><s:date name="merchInfo.createTime" format="yyyy年MM月dd日 HH:mm:ss" /></td>
		  	</tr>
		  	 -->
			<tr>
				<td>是否使用密码</td>
				<td>${merchInfo.usePwdFlagName}</td>
				<td>清算资金是否轧差</td>
				<td>${merchInfo.isNettingName}</td>
		  	</tr>
			<tr>
				<td>清算周期</td>
				<td>${merchInfo.setCycleName}</td>
				<td>是否单机产品机构</td>
				<td>${merchInfo.singleProductName}</td>
		  	</tr>
		  	<tr>
				<td>签约时间</td>
				<td><s:date name="merchInfo.signingTime" format="yyyy年MM月dd日 HH:mm:ss" /></td>
				<td>开通标志</td>
				<td>${merchInfo.openFlagName}</td>
		  	</tr>
		  	<tr>
				<td>风险等级</td>
				<td>${merchInfo.riskLevelName}</td>
		  	</tr>
			<tr>
				<td>营业执照</td>
				<td>${merchInfo.merchCode}</td>
				<td>营业执照有效期</td>
				<td><s:date name="merchInfo.licenseExpDate" format="yyyy-MM-dd" /></td>
			</tr>
			<tr>
				<td>组织机构号</td>
				<td>${merchInfo.organization}</td>
				<td>组织机构代码有效期</td>
				<td>${merchInfo.organizationExpireDate}</td>
		  	</tr>
		  	<tr>
				<td>税务登记号</td>
				<td>${merchInfo.taxRegCode}</td>
				<td>法人姓名</td>
				<td>${merchInfo.legalPersonName}</td>
		  	</tr>
		  	<tr>
				<td>法人身份证号码</td>
				<td>${merchInfo.legalPersonIdcard}</td>
				<td>法人身份证有效期</td>
				<td><s:date name="merchInfo.legalPersonIdcardExpDate" format="yyyy-MM-dd" /></td>
		  	</tr>
		  	<tr>
				<td>单位经营范围</td>
				<td>${merchInfo.companyBusinessScope}</td>
				<td>易航宝虚拟帐号</td>
				<td>${merchInfo.yhbVirtualAcct}</td>
		  	</tr>
			<tr>
				<td>状态</td>
				<td>${merchInfo.statusName}</td>
			</tr>
		  	<tr>
				<td>备注</td>
				<td>${merchInfo.remark}</td>
				<td>管理员用户名</td>
				<td>${merchInfo.adminId}</td>
		  	</tr>
		  	<tr>
				<td>更新人</td>
				<td>${merchInfo.updateBy}</td>
				<td>更新时间</td>
				<td><s:date name="merchInfo.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>
		</div>
		
		<div style="padding-left: 30px;margin: 0px;">
			<input type="button" value="打印" onclick="openPrint();"/>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.WORKFLOW_ADD_MERCH%>"/>
			<jsp:param name="refId" value="${merchInfoReg.id}"/>
		</jsp:include>
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.WORKFLOW_CARD_ADD_MERCH%>"/>
			<jsp:param name="refId" value="${merchInfoReg.id}"/>
		</jsp:include>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>