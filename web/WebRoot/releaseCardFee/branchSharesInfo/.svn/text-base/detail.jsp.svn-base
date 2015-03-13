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
		
		<!-- 运营分支机构分润明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/releaseCardFee/branchSharesInfo/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>清算日期</td>
				<td>${releaseCardFeeDTotal.feeDate}</td>
				<td>机构编号</td>
				<td>${releaseCardFeeDTotal.branchCode}</td>
				<td>机构名称</td>
				<td>${fn:branch(releaseCardFeeDTotal.branchCode)}</td>
			</tr>
			<tr>
				<td>商户号</td>
				<td>${releaseCardFeeDTotal.merchId}</td>
				<td>商户名称</td>
				<td>${fn:merch(releaseCardFeeDTotal.merchId)}</td>
				<td>卡Bin</td>
				<td>${releaseCardFeeDTotal.cardBin}</td>
			</tr>
			<tr>
				<td>手续费计费方式</td>
				<td>${releaseCardFeeDTotal.feeTypeName}</td>
				<td>运营手续费费率</td>
				<td>${releaseCardFeeDTotal.feeRate}</td>
				<td>发卡机构货币符</td>
				<td>${releaseCardFeeDTotal.branchCurCode}</td>
			</tr>
			<tr>
				<td>交易金额</td>
				<td>${fn:amount(releaseCardFeeDTotal.amount)}</td>
				<td>发卡机构应付运营手续费金额</td>
				<td>${fn:amount(releaseCardFeeDTotal.feeAmt)}</td>
				<td>汇率</td>
				<td>${releaseCardFeeDTotal.exchRate}</td>
			</tr>
			<tr>
				<td>运营中心币种</td>
				<td>${releaseCardFeeDTotal.centCurCode}</td>
				<td>运营中心应收手续费</td>
				<td>${fn:amount(releaseCardFeeDTotal.centFeeAmt)}</td>
				<td>中心最终收入金额</td>
				<td>${fn:amount(releaseCardFeeDTotal.centRecvAmt)}</td>
			</tr>
			<tr>
				<td>交易类型</td>
				<td>${releaseCardFeeDTotal.transTypeName}</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>分支机构编号</td>
				<td>${releaseCardFeeDTotal.chlCode}</td>
				<td>分支机构名称</td>
				<td>${fn:branch(releaseCardFeeDTotal.chlCode)}</td>
				<td>分支机构分润金额</td>
				<td>${fn:amount(releaseCardFeeDTotal.chlFeeAmt)}</td>
			</tr>
			<s:if test="showFenzhi">
			</s:if>
			<s:else>
			<tr>
				<td>运营机构代理编号</td>
				<td>${releaseCardFeeDTotal.proxyId}</td>
				<td>运营机构代理名称</td>
				<td>${fn:branch(releaseCardFeeDTotal.proxyId)}</td>
				<td>运营代理机构分润金额</td>
				<td>${fn:amount(releaseCardFeeDTotal.proxyFeeAmt)}</td>
			</tr>
			<tr>
				<td>出机方编号</td>
				<td>${releaseCardFeeDTotal.posProvId}</td>
				<td>出机方名称</td>
				<td>${fn:branch(releaseCardFeeDTotal.posProvId)}</td>
				<td>出机方分润金额</td>
				<td>${fn:amount(releaseCardFeeDTotal.provFeeAmt)}</td>
			</tr>
			<tr>
				<td>维护方编号</td>
				<td>${releaseCardFeeDTotal.posManageId}</td>
				<td>维护方名称</td>
				<td>${fn:branch(releaseCardFeeDTotal.posManageId)}</td>
				<td>维护方分润金额</td>
				<td>${fn:amount(releaseCardFeeDTotal.manageFeeAmt)}</td>
			</tr>
			</s:else>
			<tr>
				<td>更新用户名</td>
				<td>${releaseCardFeeDTotal.updateBy}</td>
				<td>更新时间</td>
				<td><s:date name="releaseCardFeeDTotal.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td></td>
				<td></td>
			</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>