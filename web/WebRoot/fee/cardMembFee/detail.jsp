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
				<caption>${ACT.name}<span class="caption_title"> | <f:link href="/fee/cardMembFee/list.do?goBack=goBack">返回列表</f:link></span></caption>
				<tr>
					<td>发卡机构</td>
					<td>${cardMembFee.branchCode}-${fn:branch(cardMembFee.branchCode)}</td>
					<td>会员卡号</td>
					<td>${cardMembFee.cardId}</td>
					<td>交易类型</td>
					<td>${cardMembFee.transTypeName}</td>
				</tr>
				<tr>
					<td>费率规则ID</td>
					<td>${cardMembFee.feeRuleId}</td>
					<td>计费方式</td>
					<td>${cardMembFee.feeTypeName}</td>
					<td>计费周期</td>
					<td>${cardMembFee.costCycleName}</td>
				</tr>
				<tr>
					<td>发卡机构币种</td>
					<td>${cardMembFee.curCode}</td>
					<td>费率</td>
					<td>
						<s:if test="cardMembFee.feeType == 0">${cardMembFee.feeRate} 元/笔</s:if>
					  	<s:else>${fn:percentPre(cardMembFee.feeRate, 4)}</s:else>
					</td>
					<td>单笔最低手续费</td>
					<td>${cardMembFee.minAmt} 元</td>
				</tr>
				<tr>
					<td>单笔最高手续费</td>
					<td>${cardMembFee.maxAmt} 元</td>
					<td>更新用户名</td>	
					<td>${cardMembFee.updateBy}</td>
					<td>操作时间</td>
					<td><s:date name = "cardMembFee.updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>	
				</tr>
			</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>