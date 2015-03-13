<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>${ACT.name}</title>
		
		<f:css href="/css/page.css"/>
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:js src="/js/date/WdatePicker.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#cardId').blur(function(){
				$('#expirDateId').val("");
				$('#cardStatusName').val("");
				$('#balance').val("");
				var value = $(this).val();
				if(value == null || value == undefined || value == ""){
					return;
				}
				$.post(CONTEXT_PATH + '/carddDefer/getExpirDate.do', {'cardDeferReg.cardId':value, 'callId':callId()}, 
				function(json){
					if (json.success){
						$('#expirDateId').val(json.expirDate);
						$('#cardStatusName').val(json.cardStatusName);
						$('#balance').val(json.balance);
						$(':submit').removeAttr('disabled');
					} else {
						showMsg(json.error);
						$(':submit').attr('disabled', 'true');
					}
				}, 'json');
			});
			
			$('#newExpirDate').blur(function(){
				var newExpirDate = $('#newExpirDate').val();
				var cardId = $('#cardId').val();
				if(isEmpty(newExpirDate)){
					return;
				}
				if(isEmpty(cardId)){
					showMsg("请先输入卡号。");
					$('#newExpirDate').val('');
				}
			});
			
		});
		function getNewExpirDate(){
			//WdatePicker({minDate:'#F{$dp.$D(\'expirDateId\')}'});
			WdatePicker();
		} 
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>添加单张卡延期</caption>
						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
							<s:textfield id="cardId" name="cardDeferReg.cardId" onkeyup="this.value=this.value.replace(/\D/g,'')" 
							onafterpaste="this.value=this.value.replace(/\D/g,'')" cssClass="{required:true}"  maxlength="19" />
								<span class="field_tipinfo" id=""></span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right" >原失效日期: </td>
							<td>
								<s:textfield id="expirDateId" readonly="true" cssClass="readonly"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right" >卡状态: </td>
							<td>
								<s:textfield id="cardStatusName" readonly="true" cssClass="readonly"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right" >余额: </td>
							<td>
								<s:textfield id="balance" readonly="true" cssClass="readonly"/>
								<span class="field_tipinfo">（充值资金余额 + 返利资金余额）</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">延期日期</td>
							<td>
								<s:textfield id="newExpirDate" name="cardDeferReg.newExpirDate" onclick="getNewExpirDate();" cssClass="{required:true}" ></s:textfield>
								<span class="field_tipinfo">请输入延期日期</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="cardDeferReg.remark" />
								<span class="field_tipinfo">请输入备注</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/carddDefer/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARDDDEFER_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>