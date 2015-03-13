<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
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
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		<f:js src="/js/validate.js"/>
		<script>
		$(function(){
			$('#strNoCheckId').blur(function(){
				var value1 = $(this).val();
				var value2 = $('#strNoId').val();
				var value3 = $('#cardNumId').val();
				if (value1 == undefined || value1.length < 19){
					return;
				}
				if (!validator.isDigit(value1)) {
					return;
				}
				$.post(CONTEXT_PATH + '/cardReceive/receive/isCorrectCardNo.do', {'appReg.checkStrNo':value1, 'appReg.strNo':value2, 'appReg.cardNum':value3}, function(json){
					if (!json.isCorrectCardNo){
						showMsg(json.errMsg);
						$(':button').attr('disabled', 'true');
						$('#cardNumCheckId').attr('disabled', 'true');
					} else {
						$(':button').removeAttr('disabled');
						$('#cardNumCheckId').removeAttr('disabled');
					}
				}, 'json');
			});
			$('#cardNumCheckId').blur(function(){
				var value1 = $(this).val();
				var value2 = $('#strNoId').val();
				var value3 = $('#cardNumId').val();
				var value4 = $('#strNoCheckId').val();
				if (!validator.isDigit(value1)) {
					return;
				}
				$.post(CONTEXT_PATH + '/cardReceive/receive/isCorrectCardNum.do', {'checkCardNum':value1, 'appReg.strNo':value2, 'appReg.cardNum':value3,'appReg.checkStrNo':value4}, function(json){
					if (!json.isCorrectCardNum){
						showMsg("审核卡数量超出范围或不符合规范。");
						$(':button').attr('disabled', 'true');
					} else {
						$(':button').removeAttr('disabled');
					}
				}, 'json');
			});
		});
		</script>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<div id="loadingBarDiv"	style="display: none; width: 100%; height: 100%">
			<table width=100% height=100%>
				<tr>
					<td align=center >
						<center>
							<img src="${CONTEXT_PATH}/images/ajax_saving.gif" align="middle">
							<div id="processInfoDiv"
								style="font-size: 15px; font-weight: bold">
								正在处理中，请稍候...
							</div>
							<br>
							<br>
							<br>
						</center>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="contentDiv" class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<f:workflowparam adapter="cardReceiveAdapter" returnUrl="/cardReceive/receive/checkList.do" param="strNoCheckId,cardNumCheckId" refId="${appReg.id}">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}<span class="caption_title"> | <f:link href="/cardReceive/receive/checkList.do?goBack=goBack">返回列表</f:link></span></caption>
						<tr>
							<td width="80" height="30" align="right">领卡机构</td>
							<td>
								<s:hidden name="appReg.appOrgId" />
								<input id="appOrgIdName" value="${appOrgName}" class="readonly" readonly="readonly" />
							</td>
							<td width="80" height="30" align="right"></td>
							<td>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">申请卡起始号</td>
							<td>
								<s:textfield id="strNoId" name="appReg.strNo" cssClass="{required:true, digit:true, minlength:19} readonly" maxlength="19" readonly="true"/>
							</td>
							<td width="80" height="30" align="right">审核卡起始号</td>
							<td>
								<s:textfield id="strNoCheckId" name="appReg.checkStrNo" cssClass="{required:true, digit:true, minlength:19}" maxlength="19"/>
								<span class="field_tipinfo">该输入审核卡起始号</span>
								<span class="error_tipinfo">卡号必须为19位数字</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">申请卡数量</td>
							<td>
								<s:textfield id="cardNumId" name="appReg.cardNum" cssClass="{required:true, digit:true} readonly" maxlength="8" readonly="true" />
							</td>
							<td width="80" height="30" align="right">审核卡数量</td>
							<td>
								<s:textfield id="cardNumCheckId" name="appReg.checkCardNum" cssClass="{required:true, digit:true, min:1}" maxlength="8" />
								<span class="field_tipinfo">请输入审核卡数量</span>
								<span class="error_tipinfo">卡数量须为正整数</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">联系人</td>
							<td>
								<s:textfield name="appReg.linkMan" cssClass="readonly" readonly="true"/>
							</td>
							<td width="80" height="30" align="right">联系方式</td>
							<td>
								<s:textfield name="appReg.contact" cssClass="readonly" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">联系地址</td>
							<td>
								<s:textfield name="appReg.address" cssClass="readonly" readonly="true"/>
							</td>
							<td width="80" height="30" align="right" >备注</td>
							<td>
								<s:textfield name="appReg.memo" cssClass="readonly" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">申购单号</td>
							<td>
								<s:textfield name="appReg.orderNo" cssClass="readonly" readonly="true"/>
							</td>
							<td width="80" height="30" align="right">申购单位</td>
							<td>
								<s:textfield name="appReg.orderUnit" cssClass="readonly" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">身份证号</td>
							<td>
								<s:textfield name="appReg.identityCard" cssClass="readonly" readonly="true"/>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARDREACIVE_CHECK"/>
					</f:workflowparam>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.CARD_RECEIVE%>"/>
			<jsp:param name="refId" value="${appReg.id}"/>
		</jsp:include>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>