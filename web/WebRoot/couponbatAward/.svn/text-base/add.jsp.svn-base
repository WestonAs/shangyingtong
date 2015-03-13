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
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script >
		$(function(){
			$('#startCardId').blur(validateCardId);
			$('#cardIssuerId').blur(validateCardId);
			$('#NumId').blur(validateCardNum);
			$('#faceValueId_1').blur(validateFaceValue);
		});
		
		// 校验起始卡号
		function validateCardId(){
			var startCardId = $('#startCardId').val();
			var cardIssuerId = $('#cardIssuerId').val();
			
			if(startCardId == null || startCardId == undefined || startCardId == ""){
				return;
			}
			
			if(isEmpty(cardIssuerId)){
				showMsg("请先选择发行机构。");
				return false;
			}
			
			$.post(CONTEXT_PATH + '/couponbatAward/validateCardId.do', {'couponBatReg.startCard':startCardId, 'couponBatReg.cardIssuer':cardIssuerId, 'callId':callId()},
				function(json){
					if (json.success){
						$('#input_btn2').removeAttr('disabled');
					} else {
						showMsg(json.error);
						$('#input_btn2').attr('disabled',true); 
					}
					if (json.faceValueFlag){
						$('#faceValue_td_1').show();
						$('#faceValue_td_2').show();
						$('#faceValueId_1').removeAttr('disabled');
						$('#faceValue_td_3').hide();
						$('#faceValue_td_4').hide();
						$('#faceValueId_2').attr('disabled',true); 
					}
					else {
						$('#faceValue_td_1').hide();
						$('#faceValue_td_2').hide();
						$('#faceValueId_1').attr('disabled',true); 
						$('#faceValue_td_3').show();
						$('#faceValue_td_4').show();
						$('#faceValueId_2').removeAttr('disabled'); 
						$('#faceValueId_2').val(json.resultFaceValue);
					}
				}, 'json');
		}
		
		// 校验卡数量
		function validateCardNum(){
		
			var startCardId = $('#startCardId').val();
			var cardIssuerId = $('#cardIssuerId').val();
			var NumId = $('#NumId').val();
			if(isEmpty(startCardId) || isEmpty(cardIssuerId)){
				showMsg("请先选择发行机构和起始卡号。");
				$('#NumId').val('');
				return;
			}
			if(isEmpty(NumId)){
				return;
			}
			if(!checkIsInteger(NumId)){
				return;
			}
			if(parseInt(NumId)<=0){
				showMsg("卡数量需要大于等于0.");
				return;
			}
			$.post(CONTEXT_PATH + '/couponbatAward/validateCardNum.do', {'couponBatReg.startCard':startCardId,  'couponBatReg.cardIssuer':cardIssuerId, 'couponBatReg.cardNum':NumId, 'callId':callId()},
				function(json){
					if (json.success){
						$('#input_btn2').removeAttr('disabled');
					} 
					else {
						showMsg(json.error);
						$('#input_btn2').attr('disabled',true); 
					}
				}, 'json');
		}
		
		// 校验初始面值
		function validateFaceValue(){
			var faceValue = $('#faceValueId_1').val();
			if(parseFloat(faceValue)<0){
				showMsg("初始面值不能小于0.");
				$('#input_btn2').attr('disabled',true); 
			} else {
				$('#input_btn2').removeAttr('disabled');
			}
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
						<tr>
							<td width="80" height="30" align="right">发行机构</td>
							<td>
								<s:select id="cardIssuerId" name="couponBatReg.cardIssuer" list="cardIssuerList" headerKey="" headerValue="--请选择--" 
								listKey="branchCode" listValue="branchName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择发行机构</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">起始卡号</td>
							<td>
							<s:textfield id="startCardId" name="couponBatReg.startCard" cssClass="{required:true, digit:true}" maxlength="19"/>
								<span class="field_tipinfo">请输入起始卡号</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡数量</td>
							<td>
								<s:textfield id="NumId" name="couponBatReg.cardNum" cssClass="{required:true, digit:true}" maxlength="8"/>
								<span class="field_tipinfo">请输入整数卡数量</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">派赠备注</td>
							<td>
							<s:textfield name="couponBatReg.remark" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入备注</span>
							</td>
						</tr>	
						<!-- 初始面值为0时 -->
						<tr>
							<td id="faceValue_td_1" width="80" height="30" align="right" style="display: none">初始面值</td>
							<td id="faceValue_td_2" style="display: none">
							<s:textfield id="faceValueId_1" name="couponBatReg.faceValue" cssClass="{required:true, decimal:'12,2'}" maxlength="12"/>
								<span class="field_tipinfo">最大10位整数，2位小数。</span>
							</td>
						</tr>
						<!-- 初始面值非0时 -->
						<tr>
							<td id="faceValue_td_3" width="80" height="30" align="right" style="display: none">初始面值</td>
							<td id="faceValue_td_4" style="display: none">
							<s:textfield id="faceValueId_2" name="couponBatReg.faceValue" readonly="true" cssClass="{required:true}"/>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="登记" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/couponbatAward/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_COUPONBATAWARD_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>