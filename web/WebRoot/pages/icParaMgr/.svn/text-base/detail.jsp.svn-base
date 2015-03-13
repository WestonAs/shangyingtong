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
			<caption>IC卡卡片参数修改登记明细信息<span class="caption_title"> | <f:link href="/pages/icParaMgr/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>批次ID</td>
				<td>${icCardParaModifyReg.id}</td>
				<td>卡号</td>
				<td>${icCardParaModifyReg.cardId}</td>
				<td>卡类型号</td>
				<td>${icCardParaModifyReg.cardSubClass}</td>
			</tr>
			<tr>
				<td>是否自动圈存</td>
				<td>${icCardParaModifyReg.autoDepositFlagName}</td>
				<td>自动圈存金额</td>
				<td>${fn:amount(icCardParaModifyReg.autoDepositAmt)}</td>
				<td>状态</td>
				<td>${icCardParaModifyReg.statusName}</td>
			</tr>
		  	<tr>
		  		<td>操作机构或部门号</td>
				<td>${icCardParaModifyReg.branchCode}</td>
		  		<td>电子现金余额上限</td>
				<td>${fn:amount(icCardParaModifyReg.balanceLimit)}</td>
				<td>电子现金交易限额</td>
				<td>${fn:amount(icCardParaModifyReg.amountLimit)}</td>
			</tr>
		  	<tr>
				<td>操作机构或部门名</td>
				<td>${fn:branch(icCardParaModifyReg.branchCode)}${fn:dept(icCardParaModifyReg.branchCode)}</td>
		  		<td>发卡机构编号</td>
				<td>${icCardParaModifyReg.cardBranch}</td>
				<td>发卡机构名</td>
				<td>${fn:branch(icCardParaModifyReg.cardBranch)}</td>
			</tr>
		  	<tr>
		  		<td>是否写卡成功</td>
				<td>${icCardParaModifyReg.writeCardFlagName}</td>
		  		<td>更新人</td>
				<td>${icCardParaModifyReg.updateUser}</td>
		  		<td>更新时间</td>
				<td><s:date name="icCardParaModifyReg.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		  	<tr>
				<td>备注</td>
		  		<td colspan="5">${icCardParaModifyReg.remark}</td>
		  	</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>