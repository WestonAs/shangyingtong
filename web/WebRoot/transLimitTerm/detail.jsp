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
			#tranEnableDiv table table .headcell { text-align: right; }
		</style>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
				<caption>${ACT.name}<span class="caption_title"> | <f:link href="/transLimitTerm/list.do?goBack=goBack">返回列表</f:link></span></caption>
				<tr>
					<td>发卡机构</td>
					<td>${transLimitTerm.cardIssuer}-${fn:branch(transLimitTerm.cardIssuer)}</td>
					<td>商户</td>
					<td>${transLimitTerm.merNo}-${fn:merch(transLimitTerm.merNo)}</td>
				</tr>
				<tr>
					<td>卡BIN</td>
					<td>${transLimitTerm.cardBin}</td>
					<td>终端号</td>
					<td>${transLimitTerm.termNo}</td>
				</tr>
				<tr>
					<td>状态</td>
					<td>${transLimitTerm.statusName}</td>
					<td>更新用户名</td>
					<td>${transLimitTerm.updateBy}</td>
			  	</tr>
				<tr>
			  		<td>更新时间</td>
					<td colspan="11"><s:date name="transLimitTerm.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
			</table>
		</div>
		<div class="userbox" id="tranEnableDiv">
			<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
				<tr>
					<td><b>1、预付业务：</b></td>
					<td colspan="10">
						<table class="detail_grid" width="98%" border="0" cellspacing="0" cellpadding="1">
							<tr>
								<td>部分消费</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(11)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
								<td>过期消费</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(13)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
								<td>转账</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(3)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
								<td>银行卡充值</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(4)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
							</tr>
							<tr>
								<td>现金充值</td>
								<td colspan="11">
									<s:if test="transLimitTerm.isTranEnable(2)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
				<tr>
					<td><b>2、积分业务：</b></td>
					<td colspan="10">
						<table class="detail_grid" width="98%" border="0" cellspacing="0" cellpadding="1">
							<tr>
								<td>消费赠送</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(80)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
								<td>积分消费</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(16)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
								<td>积分兑换礼品</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(22)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
								<td>积分返利</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(20)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
							</tr>
							<tr>
								<td>积分兑换赠券</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(21)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
								<td>扣减积分</td>
								<td colspan="11">
									<s:if test="transLimitTerm.isTranEnable(44)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			
			<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
				<tr>
					<td><b>3、次卡业务：</b></td>
					<td colspan="10">
						<table class="detail_grid" width="98%" border="0" cellspacing="0" cellpadding="1">
							<tr>
								<td>次卡消费</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(12)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			
			<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
				<tr>
					<td><b>4、单机版业务：</b></td>
					<td colspan="10">
						<table class="detail_grid" width="98%" border="0" cellspacing="0" cellpadding="1">
							<tr>
								<td>制卡</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(52)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
								<td>售卡</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(53)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
								<td>卡挂失</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(55)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
								<td>卡解挂</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(63)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
							</tr>
							<tr>
								<td>卡延期</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(54)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
								<td>卡销户</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(56)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
								<td>卡冻结</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(57)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
								<td>卡解冻</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(58)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
							</tr>
							<tr>
								<td>缴费</td>
								<td>
									<s:if test="transLimitTerm.isTranEnable(59)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
								<td>换卡</td>
								<td colspan="11">
									<s:if test="transLimitTerm.isTranEnable(62)"><font color="red">允许</font></s:if>
									<s:else>禁止</s:else>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>