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
				if (value == '01' || value == '06' || value == '22') {//按倍率送积分,送固定积分
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
				} else if (value == '12' || value == '14' || value == '15'){ // 对于发卡方折扣
					$('#rateId').removeAttr('disabled');
					$('[id^="rateId_td"]').show();
					$('#amountId1').attr('disabled', 'true');
					$('[id^="amountId1_td"]').hide();
					$('#amountId2').attr('disabled', 'true');
					$('[id^="amountId2_td"]').hide();
					$('#pointId').removeAttr('disabled');
					$('[id^="pointId_td"]').show();
					$('#subClassId').attr('disabled', 'true');
					$('[id^="subClassId_td"]').hide();
				} else if (value == '11' || value == '13' ){ 
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
		});
		function loadSubClass(value){
			$('#subClassId').load(CONTEXT_PATH + '/promotions/promtDef/getSubClassList.do', {'ruleType':value, 'callId':callId()});
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="ruleAdd.do" id="inputForm" cssClass="validate">
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
								<s:hidden name="promtRuleDef.promtId" />
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
							<td id="pointId_td1" width="80" height="30" align="right" style="display: none;">积分参数/商户折扣</td>
							<td id="pointId_td2" style="display: none;">
								<s:textfield id="pointId" name="promtRuleDef.ruleParam4" cssClass="{required:true, num:true, decimal:'10,2'}" disabled="true"/>
								<span class="field_tipinfo">请输入积分参数/商户折扣</span>
								<span class="error_tipinfo">该项为Number(10,2)</span>
							</td>
						</tr>
						<tr id="subClassId_td3" style="display: none;">
							<td id="subClassId_td1" width="80" height="30" align="right" style="display: none;">积分/赠券类型</td>
							<td id="subClassId_td2" style="display: none;">
								<select id="subClassId" name="promtRuleDef.ruleParam5" class="{required:true}">
								</select>
								<span id="subClass_fieldId" class="field_tipinfo">请输入积分/赠券类型</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="1">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/promotions/promtDef/ruleList.do?promtRuleDef.promtId=${promtRuleDef.promtId}&goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_PROMT_DEF_RULEADD"/>
				</s:form>
				
				<span class="note_div">促销活动规则说明</span>
				<ul class="showli_div">
					<li class="showli_div">金额类型：表示按清算金额还是按交易金额参与活动，在使用商户赠券等情况下，清算金额小于交易金额。</li>
					<li class="showli_div">参考金额：只有交易金额(或清算金额，取决于金额类型)大于或等于参考金额才参与活动规则，如果任何交易金额都可以参与活动，那么参考金额填0。</li>
					<li class="showli_div">规则类型：主要包含三类规则</li>
					<ul class="showCicleLi_div">
						<li class="showCicleLi_div">送积分</li>
						<ul class="showSquareLi_div">
							<li class="showSquareLi_div">按倍率送积分。例如消费100块钱以上时每一元送0.5沃尔玛积分，那么参考金额填100，倍率参数填0.5，子类型参数选择沃尔玛积分。如果持卡人消费了250块钱，那么会送250 X 0.5 = 125沃尔玛积分。</li>
							<li class="showSquareLi_div">送固定积分。例如消费100块钱以上送50沃尔玛积分，那么参考金额填100，积分参数填50，子类型参数选择沃尔玛积分。如果持卡人消费了250块钱，那么会送50沃尔玛积分。</li>
							<li class="showSquareLi_div">金额每满一定金额就送固定积分。例如消费200元以上，每消费100块钱送50沃尔玛积分，参考金额填200，金额参数1填100，那么积分参数填50，子类型参数选择沃尔玛积分。如果持卡人消费了250块钱，那么会送50 X 2 = 100沃尔玛积分。</li>
							<li class="showSquareLi_div">N倍协议积分。例如假设已经定义了一条送消费积分协议的规则–按倍率消费100以上送50沃尔玛积分，现在需要加多一条N倍协议积分的活动规则，倍率参数填3，子类型参数选择沃尔玛积分。如果持卡人消费了250块钱，遵循“按倍率消费100以上送50沃尔玛积分”这条规则会送125沃尔玛积分，而遵循“N倍协议积分”，会额外再送125 X 3=375沃尔玛积分。</li>
						</ul>
						
						<li class="showCicleLi_div">送赠券</li>
							<ul class="showSquareLi_div">
							<li class="showSquareLi_div">额外派送赠券。例如，消费100块钱以上送50元沃尔玛国庆赠券，那么参考金额填100，金额参数2填50，子类型参数选择沃尔玛国庆赠券。如果持卡人消费了250块钱，那么会送50元沃尔玛国庆赠券。</li>
							<li class="showSquareLi_div">按倍率派送赠券。例如，消费100块钱以上时每一元送0.5元沃尔玛国庆赠券，那么参考金额填100，倍率参数填0.5，子类型参数选择沃尔玛国庆赠券。如果持卡人消费了250块钱，那么会送250 X 0.5 = 125元沃尔玛国庆赠券。</li>
							<li class="showSquareLi_div">金额每满一定金额就派送赠券。例如消费200元以上，每消费100块钱送50沃尔玛国庆赠券，那么参考金额填200，金额参数1填100，金额参数2填50，子类型参数选择沃尔玛国庆赠券。如果持卡人消费了250块钱，那么会送50 X 2 = 100元沃尔玛国庆赠券。</li>
						</ul>
						
						<li class="showCicleLi_div">打折</li>
						<ul class="showSquareLi_div">
							<li class="showSquareLi_div">商家消费打折(非排他折扣)。只有商户定义的活动才能有这个规则。例如，某商场定义一个97折的活动，那么倍率参数填0.97。</li>
							<li class="showSquareLi_div">发卡方打折(非排他折扣)。只有发卡机构定义的活动才能有这个规则。例如，某发卡机构为持卡人定义了一个98折的活动，但发卡机构只需要按97折向商户清算交易资金(折扣之差会产生活动清算)，那么，持卡人折扣填0.98，商户折扣填0.97。再例如，如果发卡机构也是按97折向商户清算交易资金，那么商户折扣也填0.97，此时该活动不产生活动清算。一般情况下，商户折扣只能小于或等于持卡人折扣。</li>
							<li class="showSquareLi_div">商家消费打折(排他折扣)。与商家消费打折(非排他折扣)类似，区别是不能与其他折扣同时使用此规则。</li>
							<li class="showSquareLi_div">发卡方打折(排他折扣)。与发卡方打折(非排他折扣)类似，区别是不能与其他折扣同时使用此规则。</li>
							<li class="showSquareLi_div">发卡方事后返还折扣(排他规则)。只有发卡机构定义的活动才能有这个规则。积分参数必须大于倍率参数，最终商户需要按照两个折扣之差向发卡机构付活动清算成本。</li>
						</ul>
					</ul>
				</ul>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>