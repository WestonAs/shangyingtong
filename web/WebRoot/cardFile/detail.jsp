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
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 卡信息明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>卡信息详细信息<span class="caption_title"> | <f:link href="/cardFile/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>卡号</td>
				<td>${cardInfo.cardId}</td>
				<td>卡BIN</td>
				<td>${cardInfo.cardBin}</td>
			</tr>
			<tr>
				<td>卡种类</td>
				<td>${cardTypeCode.cardTypeName}</td>
				<td>卡子类</td>
				<td>${cardInfo.cardSubclass}</td>
			</tr>
			<tr>
				<td>账号</td>
				<td>${cardInfo.acctId}</td>
				<td>交易总金额</td>
				<td>${fn:amount(cardInfo.amount)}</td>
			</tr>	
			<tr>
				<td>有效日期</td>
				<td>${cardInfo.effDate}</td>
				<td>失效日期</td>
				<td>${cardInfo.expirDate}</td>
			</tr>	
			<tr>
				<td>是否使用密码标志</td>
				<td>${cardInfo.pwdflagName}</td>
				<td>领卡机构</td>
				<td>${cardInfo.appOrgId}-${fn:branch(cardInfo.appOrgId)}${fn:dept(cardInfo.appOrgId)}</td>
			</tr>
			<tr>
				<td>卡状态</td>
				<td>${cardInfo.cardStatusName}</td>
				<td>外部号码</td>
				<td>${cardInfo.externalCardId }</td>
			</tr>
			<tr>
				<td>售卡机构</td>
				<td>${cardInfo.saleOrgId}-${fn:branch(cardInfo.saleOrgId)}${fn:dept(cardInfo.saleOrgId)}</td>
				<td>售卡日期</td>
				<td>${cardInfo.saleDate}</td>
			</tr>	
			<tr>
				<td>购卡客户ID</td>
				<td>${cardInfo.cardCustomerId}</td>
				<td>批次号</td>
				<td>${cardInfo.batchNo}</td>
			</tr>
			<tr>
				<td>建档日期</td>
				<td>${cardInfo.createDate}</td>
				<td>撤销日期</td>
				<td>${cardInfo.cancelDate}</td>				
			</tr>	
			<tr>
				<td>发卡机构</td>
				<td>${fn:branch(cardInfo.cardIssuer)}</td>
				<td>工本费</td>
				<td>${fn:amount(cardInfo.expenses)}</td>
			</tr>	
			<tr>
				<td>更新用户名</td>
				<td>${cardInfo.updateBy}</td>
				<td>更新时间</td>
				<td>
				<s:date name="cardInfo.updateTime" format="yyyy-MM-dd HH:mm:ss" />
				</td>
			</tr>				 
		</table>
		</div>

		<!-- 持卡人信息明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>持卡人详细信息<span class="caption_title"> </span></caption>
			<tr>
				<td>卡号</td>
				<td>${cardExtraInfo.cardId}</td>
				<td>持卡人姓名</td>
				<td>${cardExtraInfo.custName}</td>
			</tr>
			<tr>
				<td>证件类型</td>
				<td>${cardExtraInfo.credTypeName}</td>
				<td>证件号</td>
				<td>${cardExtraInfo.credNo}</td>
			</tr>
			<tr>
				<td>联系地址</td>
				<td>${cardExtraInfo.address}</td>
				<td>联系电话</td>
				<td>${cardExtraInfo.telNo}</td>
			</tr>		
			<tr>
				<td>手机号</td>
				<td>${cardExtraInfo.mobileNo}</td>
				<td>邮件地址</td>
				<td>${cardExtraInfo.email}</td>
			</tr>
			<tr>
				<td>是否开通短信通知</td>
				<td>${cardExtraInfo.smsFlag}</td>
				<td>生日</td>
				<td>${cardExtraInfo.birthday}</td>
			</tr>
			<tr>
				<td>更新用户名</td>
				<td>${cardExtraInfo.updateBy}</td>
				<td>更新时间</td>
				<td>
				<s:date name="cardExtraInfo.updateTime" format="yyyy-MM-dd HH:mm:ss" />
		        </td>	
			</tr>

		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>