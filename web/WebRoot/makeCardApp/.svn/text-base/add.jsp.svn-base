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
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		<f:js src="/js/validate.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			
			$('#strNoId').blur(function(){
				$('#strNoId_field').html('请输入19位数字的起始卡号');
				var value = $(this).val();
				var makeCardId = $('#makeCardId').val();
				if (value == undefined || value.length < 19){
					return;
				}
				if (!validator.isDigit(value)) {
					return;
				}
				$.post(CONTEXT_PATH + '/makeCardApp/isCorrectStrNo.do', {'makeCardApp.strNo':value,'makeCardApp.makeId':makeCardId}, function(json){
					if (!json.isCorrect){
						showMsg("起始卡号必须没被使用，且前11位格式不能更改！");
						$('#input_btn2').attr('disabled', 'true');
					} else {
						$('#strNoId_field').removeClass('error_tipinfo').html('请输入19位数字的起始卡号');
						clearError();
					}
				}, 'json');
			});
			
			$('#input_btn2').click(function(){
				if($('#inputForm').validate().form()){
					$('#inputForm').submit();
					$('#input_btn2').attr('disabled', 'true');
					//window.parent.showWaiter();
					$("#loadingBarDiv").css("display","inline");
					$("#contentDiv").css("display","none");
				}
			});
		});
		function clearError() {
			$('#input_btn2').removeAttr('disabled');
		}
		function loadStrNo() {
			var makeCardId = $('#makeCardId').val();
			$.post(CONTEXT_PATH + '/makeCardApp/getStrNo.do', {'makeCardApp.makeId':makeCardId}, function(json){
				$('#strNoId').val(json.strCardNo);
				}, 'json');
			
			$('[id^=faceValue]').hide();
			$('#faceValue'+$('#makeCardId').val()).show();
		}
		</script>
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
				<s:form action="add.do" id="inputForm" enctype="multipart/form-data" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:hidden name="makeCardApp.branchCode"/>
								<input type="text" value="${fn:branch(makeCardApp.branchCode)}" class="{required:true} readonly" readonly="readonly" maxlength="40"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡样定版</td>
							<td>
								<s:select id="makeCardId" name="makeCardApp.makeId" list="regList" headerKey="" headerValue="--请选择--" 
									listKey="makeId" listValue="makeName" cssClass="{required:true}" 
									onchange="loadStrNo();" onmouseover="FixWidth(this)"></s:select>
								<span class="field_tipinfo"></span>
								<%-- 
								--%>
									<span style="color: red">参考面值：</span>
									<s:iterator value="regList"><span class="readonly" style="display: none" id="faceValue${makeId}">${faceValue} 元</span></s:iterator>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">起始卡号</td>
							<td>
								<s:textfield id="strNoId" name="makeCardApp.strNo" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo" id="strNoId_field">请输入19位数字的起始卡号</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡数量</td>
							<td>
								<s:textfield name="makeCardApp.cardNum" cssClass="{required:true, digit:true}" maxlength="7"/>
								<span class="field_tipinfo">请输入卡数量</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">收货单位</td>
							<td>
								<s:textfield name="makeCardApp.deliveryUnit" />
								<span class="field_tipinfo">请输入收货单位</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">交货地点</td>
							<td>
								<s:textfield name="makeCardApp.deliveryAdd" />
								<span class="field_tipinfo">请输入交货地点</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="上一步"
									onclick="gotoUrl('/makeCardApp/preShowAdd.do?makeCardApp.branchCode=${makeCardApp.branchCode}')" />
								<input type="button" value="提交" id="input_btn2" id="input_btn2" name="ok"  class="ml30"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');clearError();" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/makeCardApp/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MAKECARD_APP_ADD"/>
					<s:hidden id="privileges" name="privileges" />
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>