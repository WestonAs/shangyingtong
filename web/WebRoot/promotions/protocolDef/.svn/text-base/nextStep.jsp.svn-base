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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#promtRuleTypeId').change(function(){
				var value = $(this).val();
				if (value == '01'  || value == '22') {
					loadSubClass(value);
					$('#subClassId').removeAttr('disabled');
					$('[id^="subClassId_td"]').show();
					$('#rateId').removeAttr('disabled');
					$('[id^="rateId_td"]').show();
					$('#amountId1').attr('disabled', 'true');
					$('[id^="amountId1_td"]').hide();
					$('#amountId2').attr('disabled', 'true');
					$('[id^="amountId2_td"]').hide();
					$('#pointId').attr('disabled', 'true');
					$('[id^="pointId_td"]').hide();
				} else if (value == '02'){
					loadSubClass(value);
					$('#subClassId').removeAttr('disabled');
					$('[id^="subClassId_td"]').show();
					$('#pointId').removeAttr('disabled');
					$('[id^="pointId_td"]').show();
					$('#rateId').attr('disabled', 'true');
					$('[id^="rateId_td"]').hide();
					$('#amountId1').attr('disabled', 'true');
					$('[id^="amountId1_td"]').hide();
					$('#amountId2').attr('disabled', 'true');
					$('[id^="amountId2_td"]').hide();
				} else if (value == '04'){
					loadSubClass(value);
					$('#amountId1').removeAttr('disabled');
					$('[id^="amountId1_td"]').show();
					$('#subClassId').removeAttr('disabled');
					$('[id^="subClassId_td"]').show();
					$('#pointId').removeAttr('disabled');
					$('[id^="pointId_td"]').show();
					$('#rateId').attr('disabled', 'true');
					$('[id^="rateId_td"]').hide();
					$('#amountId2').attr('disabled', 'true');
					$('[id^="amountId2_td"]').hide();
				} else if (value == '06' || value == '11' || value == '12' 
						|| value == '13' || value == '14' || value == '15'){ 
					$('#rateId').removeAttr('disabled');
					$('[id^="rateId_td"]').show();
					$('#amountId1').attr('disabled', 'true');
					$('[id^="amountId1_td"]').hide();
					$('#amountId2').attr('disabled', 'true');
					$('[id^="amountId2_td"]').hide();
					$('#pointId').attr('disabled', 'true');
					$('[id^="pointId_td"]').hide();
					$('#subClassId').attr('disabled', 'true');
					$('[id^="subClassId_td"]').hide();
				} else if (value == '21'){
					loadSubClass(value);
					$('#subClassId').removeAttr('disabled');
					$('[id^="subClassId_td"]').show();
					$('#amountId2').removeAttr('disabled');
					$('[id^="amountId2_td"]').show();
					$('#rateId').attr('disabled', 'true');
					$('[id^="rateId_td"]').hide();
					$('#amountId1').attr('disabled', 'true');
					$('[id^="amountId1_td"]').hide();
					$('#pointId').attr('disabled', 'true');
					$('[id^="pointId_td"]').hide();
				} else if (value == '23'){
					loadSubClass(value);
					$('#amountId1').removeAttr('disabled');
					$('[id^="amountId1_td"]').show();
					$('#subClassId').removeAttr('disabled');
					$('[id^="subClassId_td"]').show();
					$('#amountId2').removeAttr('disabled');
					$('[id^="amountId2_td"]').show();
					$('#rateId').attr('disabled', 'true');
					$('[id^="rateId_td"]').hide();
					$('#pointId').attr('disabled', 'true');
					$('[id^="pointId_td"]').hide();
				} else {
					$('#rateId').attr('disabled', 'true');
					$('[id^="rateId_td"]').hide();
					$('#amountId1').attr('disabled', 'true');
					$('[id^="amountId1_td"]').hide();
					$('#amountId2').attr('disabled', 'true');
					$('[id^="amountId2_td"]').hide();
					$('#pointId').attr('disabled', 'true');
					$('[id^="pointId_td"]').hide();
					$('#subClassId').attr('disabled', 'true');
					$('[id^="subClassId_td"]').hide();
				}
			});
			$('#input_btn1').click(function(){
				$('#inputForm').attr('action', 'secondStep.do').submit();
			});
			$('#input_btn2').click(function(){
				$('#inputForm').attr('action', 'save.do').submit();
			});
		});
		function loadSubClass(value){
			$('#subClassId').load(CONTEXT_PATH + '/promotions/protocolDef/getSubClassList.do', {'ruleType':value, 'callId':callId()});
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="inputForm" method="post" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
					<!-- 
							<td width="80" height="30" align="right"></td>
							<td>
							</td>
							<td width="80" height="30" align="right">规则ID</td>
							<td>
								<s:textfield name="promtRuleDef.promtRuleId" cssClass="{required:true, digitOrLetter:true}"/>
								<span class="field_tipinfo">该项为数字或字母</span>
							</td>
					 -->
							<td width="80" height="30" align="right">仅生日有效</td>
							<td>
								<s:select name="promtRuleDef.birthdayFlag" list="birthdayFlagList"
									listKey="value" listValue="name" cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择是否生日有效</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">金额类型</td>
							<td>
								<s:select name="promtRuleDef.amtType" list="amtTypeList" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name" cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择金额类型</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">规则类型</td>
							<td>
								<s:select id="promtRuleTypeId" name="promtRuleDef.promtRuleType" list="promtRuleTypeList" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name" cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择规则类型</span>
							</td>
						</tr>

						<tr>
							<td width="80" height="30" align="right">参考金额</td>
							<td>
								<s:textfield name="promtRuleDef.amtRef" cssClass="{required:true, num:true, decimal:'12,2'}"/>
								<span class="field_tipinfo">请输入参考金额</span>
								<span class="error_tipinfo">该项为Number(12,2)</span>
							</td>
						</tr>
						
						<tr id="rateId_td3" style="display: none;">
							<td id="rateId_td1" width="80" height="30" align="right" style="display: none;">倍率参数</td>
							<td id="rateId_td2" style="display: none;">
								<s:textfield id="rateId" name="promtRuleDef.ruleParam3" cssClass="{required:true, num:true, decimal:'6,2'}" disabled="true"/>
								<span class="field_tipinfo">请输入倍率参数</span>
								<span class="error_tipinfo">该项为Number(6,2)</span>
							</td>
						</tr>
						
						<tr id="amountId1_td3" style="display: none;">
							<td id="amountId1_td1" width="80" height="30" align="right" style="display: none;">金额参数1</td>
							<td id="amountId1_td2" style="display: none;">
								<s:textfield id="amountId1" name="promtRuleDef.ruleParam1" cssClass="{required:true, num:true, decimal:'12,2'}" disabled="true"/>
								<span class="field_tipinfo">请输入金额参数1</span>
								<span class="error_tipinfo">该项为Number(12,2)</span>
							</td>
						</tr>
						<tr id="amountId2_td3" style="display: none;">
							<td id="amountId2_td1" width="80" height="30" align="right" style="display: none;">金额参数2</td>
							<td id="amountId2_td2" style="display: none;">
								<s:textfield id="amountId2" name="promtRuleDef.ruleParam2" cssClass="{required:true, num:true, decimal:'12,2'}" disabled="true"/>
								<span class="field_tipinfo">请输入金额参数2</span>
								<span class="error_tipinfo">该项为Number(12,2)</span>
							</td>
						</tr>
						
						<tr id="pointId_td3" style="display: none;">
							<td id="pointId_td1" width="80" height="30" align="right" style="display: none;">积分参数</td>
							<td id="pointId_td2" style="display: none;">
								<s:textfield id="pointId" name="promtRuleDef.ruleParam4" cssClass="{required:true, num:true, decimal:'10,2'}" disabled="true"/>
								<span class="field_tipinfo">请输入积分参数</span>
								<span class="error_tipinfo">该项为Number(10,2)</span>
							</td>
						</tr>
						<tr id="subClassId_td3" style="display: none;">
							<td id="subClassId_td1" width="80" height="30" align="right" style="display: none;">积分子类型</td>
							<td id="subClassId_td2" style="display: none;">
								<select id="subClassId" name="promtRuleDef.ruleParam5" class="{required:true}">
								</select>
								<span class="field_tipinfo">请选择积分子类型</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="1">
								<input type="button" value="再加一条规则" id="input_btn1"  name="ok1"/>
								<input type="button" value="保存活动" id="input_btn2"  name="ok2" class="ml30"/>
								<input type="button" value="返回列表" name="escape" onclick="gotoUrl('/promotions/protocolDef/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_PROTOCOL_DEF_NEXTSTEP"/>
				</s:form>
				
				<span class="note_div">规则类型说明</span>
				<ul class="showli_div">
					<li class="showli_div"><b>按倍率送积分：</b>例如消费100块钱以上时每一元送0.5沃尔玛积分，那么参考金额填100，倍率参数填0.5，子类型参数选择沃尔玛积分。如果持卡人消费了250块钱，那么会送250 X 0.5 = 125沃尔玛积分。</li>
					<li class="showli_div"><b>送固定积分：</b>例如消费100块钱以上送50沃尔玛积分，那么参考金额填100，积分参数填50，子类型参数选择沃尔玛积分。如果持卡人消费了250块钱，那么会送50沃尔玛积分。</li>
					<li class="showli_div"><b>金额每满一定金额就送固定积分：</b>例如消费200元以上，每消费100块钱送50沃尔玛积分，参考金额填200，金额参数1填100，那么积分参数填50，子类型参数选择沃尔玛积分。如果持卡人消费了250块钱，那么会送50 X 2 = 100沃尔玛积分。</li>
				</ul>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>