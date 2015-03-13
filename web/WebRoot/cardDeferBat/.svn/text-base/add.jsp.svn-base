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
			$('#startCardId').change(function(){
				if (isEmpty($('#endCardId').val())) {
					return false;
				}
				ajaxCheckCardId();
			});
			
			$('#endCardId').change(ajaxCheckCardId);
			
			$('#newExpirDate').blur(function(){
				var startCardId = $('#startCardId').val();
				var endCardId = $('#endCardId').val();
				if(isEmpty(startCardId)||isEmpty(endCardId)){
					showMsg("请先输入起始卡号和结束卡号。");
					$('#newExpirDate').val('');
				}
			});
			
		});

		function ajaxCheckCardId(){
			$("#waiterImg").show();
			var startCardId = $('#startCardId').val();
			var endCardId = $('#endCardId').val();
			$.post(CONTEXT_PATH + '/cardDeferBat/checkCardId.do', 
				{'cardDeferReg.startCard':startCardId, 'cardDeferReg.endCard':endCardId, 'callId':callId()}, 
				function(json){
					$("#waiterImg").hide();
					if (json.success){
						$('#expirDateId').val(json.expirDate);
						$('#id_cardNum').val(json.cardNum);
						$(':submit').removeAttr('disabled');
					} else {
						showMsg(json.error);
						$(':submit').attr('disabled', 'true');
					}
				}, 
				'json'
			);
		}
		function validateForm(){
			var startCardId = $('#startCardId').val();
			var endCardId = $('#endCardId').val();
			//起始卡号和结束卡号位数要一致
			if(startCardId.length!=endCardId.length){
				showMsg("起始卡号和结束卡号的位数不一致。");
				return false;
			}
			if (startCardId > endCardId) {
				showMsg("起始卡号必须小于等于结束卡号。");
				return false;
			}
			return true;
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">起始卡号</td>
							<td>
							<s:textfield id="startCardId" name="cardDeferReg.startCard" onkeyup="this.value=this.value.replace(/\D/g,'')" 
							onafterpaste="this.value=this.value.replace(/\D/g,'')" cssClass="{required:true}"  maxlength="19" />
							<s:if test="showCard"><span class="field_tipinfo">批量延期的卡必须为发卡机构发行或售出的卡</span></s:if>
							<s:if test="showSellProxy"><span class="field_tipinfo">批量延期的卡必须为售卡代理所售的卡</span></s:if>
							<span class="error_tipinfo">卡号不正确</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">结束卡号</td>
							<td>
							<s:textfield id="endCardId" name="cardDeferReg.endCard" cssClass="{required:true}"  maxlength="19" />
								<span class="field_tipinfo" id=""></span>
								<span class="error_tipinfo">卡号不正确</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡数量</td>
							<td>
								<s:textfield id="id_cardNum" name="cardDeferReg.cardNum" cssClass="{required:true} readonly" readonly="true" />
								<span class="field_tipinfo">卡数量不能为空</span>
								<img id="waiterImg" height="20" src="${CONTEXT_PATH}/images/ajax_loader.gif" style="display: none"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">延期日期</td>
							<td>
							<s:textfield id="newExpirDate" name="cardDeferReg.newExpirDate" onclick="WdatePicker()" cssClass="{required:true}" ></s:textfield>
								<span class="field_tipinfo">请选择延期日期</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td>
							<s:textfield name="cardDeferReg.remark" />
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return validateForm();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/cardDeferBat/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_BAT_CARDDDEFER_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>