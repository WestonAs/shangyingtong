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
		
		<!-- 日切明细 -->
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>日切详细信息<span class="caption_title"> | <f:link href="/switchQuery/daySwitchQuery/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td>日期</td>
				<td>${daySwitch.currDate}</td>
				<td>前一工作日</td>
				<td>${daySwitch.lastDate}</td>
			</tr>
			<tr>
				<td>日切状态</td>
				<s:if test="daySwitch.switchFlag!=2"><td style="color: red;"></s:if>
				<s:else><td></s:else>
				${daySwitch.switchFlagName}</td>
				<td>商户手续费计费状态</td>
				<s:if test="daySwitch.feeFlag!=2"><td style="color: red;"></s:if>
				<s:else><td></s:else>
				${daySwitch.feeFlagName}</td>
			</tr>
			<tr>
				<td>日终开始时间</td>
				<td><s:date name="daySwitch.strTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>售卡代理返利状态</td>
				<s:if test="daySwitch.refundFlag!=2"><td style="color: red;"></s:if>
				<s:else><td></s:else>
				${daySwitch.refundFlagName}</td>
			</tr>
			<tr>
				<td>试算平衡标志</td>
				<s:if test="daySwitch.trailBalanceFlag!=2"><td style="color: red;"></s:if>
				<s:else><td></s:else>
				${daySwitch.trailBalanceFlagName}</td>
				<td>商户代理分润状态</td>
				<s:if test="daySwitch.merProxyShareFlag!=2"><td style="color: red;"></s:if>
				<s:else><td></s:else>
				${daySwitch.merProxyShareFlagName}</td>
			</tr>
			<tr>
				<td>数据清理标志</td>
				<s:if test="daySwitch.dataClearFlag!=2"><td style="color: red;"></s:if>
				<s:else><td></s:else>
				${daySwitch.dataClearFlagName}</td>
				<td>运营手续费状态</td>
				<s:if test="daySwitch.centerFeeFlag!=2"><td style="color: red;"></s:if>
				<s:else><td></s:else>
				${daySwitch.centerFeeFlagName}</td>
			</tr>
			<tr>
				<td>积分过期处理</td>
				<s:if test="daySwitch.expiredPointFlag!=2"><td style="color: red;"></s:if>
				<s:else><td></s:else>
				${daySwitch.expiredPointFlagName}</td>
				<td>结算状态</td>
				<s:if test="daySwitch.setFlag!=2"><td style="color: red;"></s:if>
				<s:else><td></s:else>
				${daySwitch.setFlagName}</td>
			</tr>
			<tr>
				<td>数据迁移标志</td>
				<s:if test="daySwitch.dataMoveFlag!=2"><td style="color: red;"></s:if>
				<s:else><td></s:else>
				${daySwitch.dataMoveFlagName}</td>
				<td>划付标志</td>
				<s:if test="daySwitch.rmaFlag!=2"><td style="color: red;"></s:if>
				<s:else><td></s:else>
				${daySwitch.rmaFlagName}</td>
			</tr>
			<tr>
				<td>清算数据备份</td>
				<s:if test="daySwitch.dataBackupSettFlag!=2"><td style="color: red;"></s:if>
				<s:else><td></s:else>
				${daySwitch.dataBackupSettFlagName}</td>
				<td>交易数据备份</td>
				<s:if test="daySwitch.dataBackupTransFlag!=2"><td style="color: red;"></s:if>
				<s:else><td></s:else>
				${daySwitch.dataBackupTransFlagName}</td>
			</tr>
			<tr>
				<td>更新时间</td>
				<td><s:date name="daySwitch.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td></td>
				<td></td>
			</tr>
		</table>
		</div>
		
		<s:if test="showButton">
		<div class="contentb">
			<s:form id="searchForm" action="list.do" cssClass="validate-tip">
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
					<td height="30" colspan="4">
						<!--<input style="margin-left:30px;" type="button" value="手动日切"  name="escape" id="input_btn3" onclick="javascript:gotoUrl('/switchQuery/daySwitchQuery/manualHandle.do?daySwitch.currDate=${daySwitch.currDate}');"/>
						-->
						<input style="margin-left:30px;" type="button" value="手动日切"  name="escape" id="input_btn3" onclick="javascript:submitUrl('searchForm','/switchQuery/daySwitchQuery/manualHandle.do?daySwitch.currDate=${daySwitch.currDate}','日切需要慎重，确定要手动日切吗?');"/>
					</td>
					</tr>
				</table>
				<s:token />
			</s:form>
		</div>
		</s:if>

		<s:if test="showFeeButton">
		<div class="contentb">
			<s:form id="searchForm" action="list.do" cssClass="validate-tip">
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
					<td height="30" colspan="4">
						<!--<input style="margin-left:30px;" type="button" value="手动计费"  name="escape" id="input_btn3" onclick="javascript:gotoUrl('/switchQuery/daySwitchQuery/manualFee.do?daySwitch.currDate=${daySwitch.currDate}');"/>
						-->
						<input style="margin-left:30px;" type="button" value="手动计费"  name="escape" id="input_btn3" onclick="javascript:submitUrl('searchForm','/switchQuery/daySwitchQuery/manualFee.do?daySwitch.currDate=${daySwitch.currDate}','确定要手动计费吗?');"/>
					</td>
					</tr>
				</table>
				<s:token />
			</s:form>
		</div>
		</s:if>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>