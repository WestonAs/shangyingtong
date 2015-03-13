<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base target="_self" />
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>${ACT.name}</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<s:form id="detailForm" method="post">
				<s:token name="_TOKEN_BD_DETAIL"/>
			</s:form>
			<caption>${ACT.name}
				<span class="caption_title"> | <f:link href="/cps/BDPtDtotal/list.do?goBack=goBack">返回列表</f:link> 
				  <s:if test="centerOrCenterDeptRoleLogined || fenzhiRoleLogined || bdPtDtotal.cardIssuer == loginBranchCode">
					  	<f:pspan pid="BDPtDtotal_manual" >
							<s:if test="bdPtDtotal.cpStatus=='05'"> | <a href="javascript:submitUrl('detailForm', '/cps/BDPtDtotal/collectManual.do?bdPtDtotal.cpSn=${bdPtDtotal.cpSn}', '确定要手动发起代收付吗？');" >手动发起代收付</a></s:if> 
						</f:pspan>
						<f:pspan pid="BDPtDtotal_confirm" >
							<s:if test="bdPtDtotal.cpStatus=='05' || bdPtDtotal.cpStatus=='02'"> | <a href="javascript:submitUrl('detailForm', '/cps/BDPtDtotal/collectConfirm.do?bdPtDtotal.cpSn=${bdPtDtotal.cpSn}', '确定要手动确认吗？');" >代收付确认</a></s:if>
					  	</f:pspan>
				  </s:if>
			  </span>
			</caption>
			<tr>
				<td>代收付流水</td>
				<td>${bdPtDtotal.cpSn}</td>
				<td>代收付日期</td>
				<td>${bdPtDtotal.cpDate}</td>
			</tr>
			<tr>
				<td>代收付状态</td>
				<td>${bdPtDtotal.cpStatusName}</td>
				<td>代收付文件</td>
				<td>${bdPtDtotal.cpFile}</td>
			</tr>
			<tr>
				<td>发卡机构编号</td>
				<td>${bdPtDtotal.cardIssuer}</td>
				<td>发卡机构名称</td>
				<td>${fn:branch(bdPtDtotal.cardIssuer)}</td>
			</tr>
			<tr>
				<td>商户编号</td>
				<td>${bdPtDtotal.merNo}</td>
				<td>商户名称</td>
				<td>${fn:merch(bdPtDtotal.merNo)}${fn:branch(bdPtDtotal.merNo)}</td>
			</tr>
			<tr>
				<td>交易日期</td>
				<td>${bdPtDtotal.settDate}</td>
				<td>代收付金额</td>
				<td>${fn:amount(bdPtDtotal.settAmt)}</td>
			</tr>
			<tr>
				<td>回盘日期</td>
				<td>${bdPtDtotal.returnDate}</td>
				<td>回盘记录编号</td>
				<td>${bdPtDtotal.recordId}</td>
			</tr>
			<tr>
				<td>回盘文件</td>
				<td colspan="3">${bdPtDtotal.returnFile}</td>
			</tr>
			<tr>
				<td>积分消费清算金额</td>
				<td>${fn:amount(bdPtDtotal.ptConsumSettAmt)}</td>
				<td>积分赠送清算金额</td>
				<td>${fn:amount(bdPtDtotal.ptSendSettAmt)}</td>
			</tr>
			<tr>
				<td>积分失效清算金额</td>
				<td>${fn:amount(bdPtDtotal.ptInvalidSettAmt)}</td>
				<td>积分交易手续费</td>
				<td>${fn:amount(bdPtDtotal.ptFeeAmt)}</td>
			</tr>
			<tr>
				<td>积分失效发卡机构收益</td>
				<td>${fn:amount(bdPtDtotal.ptInvalidIncomeAmt)}</td>
				<td>积分过期发卡机构收益</td>
				<td>${fn:amount(bdPtDtotal.ptExpirIncomeAmt)}</td>
			</tr>
			<tr>
				<td>代收付标志</td>
				<td>${bdPtDtotal.cpFlagName}</td>
				<td>积分生效标志</td>
				<td>
				<s:if test="bdPtDtotal.ptActivateFlag == 1">失效</s:if>
				<s:else>未生效</s:else>
				</td>
			</tr>
			<tr>
				<td>消费赠送手续费</td>
				<td>${fn:amount(bdPtDtotal.consumeSendFee)}</td>
				<td>备注</td>
				<td>${bdPtDtotal.remark}</td>
			</tr>
		</table>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>