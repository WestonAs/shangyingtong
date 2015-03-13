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
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/validate.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#strNoId').blur(function(){
				checkNo();
			});
			$('#inputNumId').blur(function(){
				calcNo();
			});
			$('#cardTypeId').change(function(){
				checkNo();
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
		function checkNo(value,value2){
			$('#strNoId_field').html('请输入19位数字的起始卡号');
			var value = $('#strNoId').val();
			var value2 = $('#cardTypeId').val();
			if (isEmpty(value) || value.length < 19){
				return;
			}
			if (isEmpty(value2)) {
				return;
			}
			if (!validator.isDigit(value)) {
				return;
			}
			$.post(CONTEXT_PATH + '/cardStock/finishedCard/isCorrect.do', {'cardInput.strNo':value, 'cardInput.cardType':value2}, function(json){
				if (!json.isCorrect){
					$('#strNoId_field').html(json.errorMsg).addClass('error_tipinfo').show();
					$('#input_btn2').attr('disabled', 'true');
				} else {
					clearError();
				}
			}, 'json');
			calcNo();
		}
		function clearError() {
			$('#strNoId_field').removeClass('error_tipinfo').html('请输入19位数字的起始卡号');
			$('#input_btn2').removeAttr('disabled');
		}
		function calcNo(){
			var inputNum = $('#inputNumId').val();
			var strNo = $('#strNoId').val();
			if(isEmpty(inputNum) || isEmpty(strNo)){
				return;
			}
			$.post(CONTEXT_PATH + '/cardStock/finishedCard/getEndNo.do', {'cardInput.inputNum':inputNum, 'cardInput.strNo':strNo}, function(json){
				$('#endNoId').val(json.endNo);
				}, 'json');
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
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>成品卡入库新建</caption>
						
						<tr>
							<td width="80" height="30" align="right">卡类型</td>
							<td>
								<s:select id="cardTypeId" name="cardInput.cardType" list="cardTypeList" headerKey="" headerValue="--请选择--" 
									listKey="cardTypeCode" listValue="cardTypeName" cssClass="{required:true}">
								</s:select>
								<span class="field_tipinfo">请选择卡类型</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">起始卡号</td>
							<td>
								<s:textfield id="strNoId" name="cardInput.strNo" cssClass="{required:true, minlength:19, digit:true}" maxlength="19" />
								<span id="strNoId_field" class="field_tipinfo">请输入19位数字的起始卡号</span>
							</td>
						</tr>

						<tr>
							<td width="80" height="30" align="right">入库卡数量</td>
							<td>
								<s:textfield id="inputNumId" name="cardInput.inputNum" cssClass="{required:true, digit:true, min:1}" maxlength="8"/>
								<span class="field_tipinfo">请输入入库卡数量</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">结束卡号</td>
							<td>
								<s:textfield id="endNoId" name="cardInput.endNo" readonly="true" cssClass="readonly"/>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="cardInput.memo" />
							</td>
						</tr>
						
						<tr>
							<td height="30" colspan="2">
								<input type="button" value="确定" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');clearError();" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/cardStock/finishedCard/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_FINISHEDINPUT_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>