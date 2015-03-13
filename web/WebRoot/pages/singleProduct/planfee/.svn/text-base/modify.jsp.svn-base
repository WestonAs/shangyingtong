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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
			$(function(){
				Selector.selectStyle('id_cardStyleNames', 'id_cardStyleIds', false, '${loginBranchCode}');
				
				$('#id_chargeType').change(function(){
					var value = $(this).val();
					if(value == '1'){ //按固定比例收费才显示收费金额
						$('#id_chargeAmt_Tr').show();
						$('#idChargeAmt').removeAttr('disabled');
						//$('#idChargeAmt').attr('disabled', 'false');
					} else {
						$('#id_chargeAmt_Tr').hide();
						$('#idChargeAmt').attr('disabled', 'true');
					}
				});
			});
			
			function addItem() {
				var count = $('tr[id^="idDetail_"]').size();
					var content = '<tr id="idDetail_'+ (count+1) +'">' 
						+ '<td align="center"><s:hidden name="subPeriods" value="' + (count+1) + '" />第'+ (count+1) +'年</td>' 
						+ '<td align="center"><input type="text" name="subParams" value=""/></td>' 
						+ '<td align="center"><input type="text" name="subSendSum" value="" maxlength="8"/></td></tr>';
				
				$('#idLink').before(content);
			
				// 设置样式
				SysStyle.setFormGridStyle();
			}
			function deleteItem(){
				var count = $('tr[id^="idDetail_"]').size();
				if (count == 1) {
					showMsg('必须设置一项');
					return;
				}
				$('tr[id^="idDetail_"]:last').remove();
			}
			
			function checkSubmitModel(){
				
				var count = $('tr[id^="idDetail_"]').size();
				for(i=0; i<count; i++){
					var comm = $('tr[id^="idDetail_"]').eq(i).children();
					
					var subParams = comm.eq(1).children().val();
					var sendSum = comm.eq(2).children().val();
					if(isEmpty(subParams)){
					 	showMsg('第' + (i+1) + '年的收费子规则参数不能为空');
					 	return false;
					}
					if(!isDecimal(subParams, '14,2')){
						showMsg('第' + (i+1) + '年的收费子规则参数必须为Number(14,2)');
					 	return false;
					}
					if(isEmpty(sendSum)){
					 	showMsg('第' + (i+1) + '年的赠送卡数量不能为空');
					 	return false;
					}
				
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
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">发卡机构编号</td>
							<td>
								<s:textfield name="cardissuerPlanFee.issId" cssClass="{required:true} readonly" readonly="true" />
								<span class="field_tipinfo">该项非空</span>
							</td>
							<td width="80" height="30" align="right">发卡机构名称</td>
							<td>
								<s:textfield name="cardBranchName" cssClass="{required:true} readonly" readonly="true" />
								<span class="field_tipinfo">该项非空</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">收费标准</td>
							<td>
								<s:select id="id_chargeType" name="cardissuerPlanFee.chargeType" list="chargeTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
									cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
							
							<td width="80" height="30" align="right">套餐编号</td>
							<td>
								<s:textfield name="cardissuerPlanFee.planId" cssClass="{required:true} readonly" readonly="true" />
								<span class="field_tipinfo">该项非空</span>
							</td>
						</tr>
						<tr id="id_chargeAmt_Tr" style="display: none;">
							<td width="80" height="30" align="right">参考金额</td>
							<td colspan="3">
								<s:textfield id="idChargeAmt" name="cardissuerPlanFee.chargeAmt" cssClass="{required:true, decimal:'14,2'}" disabled="true" />
								<span class="field_tipinfo">按百分比收费时的基本金额</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">标准制卡单价</td>
							<td>
								<s:textfield name="cardissuerPlanFee.defauleAmt" cssClass="{required:true, decimal:'14,2'}" />
								<span class="field_tipinfo">请输入金额</span>
							</td>
							
							<td width="80" height="30" align="right">定制制卡单价</td>
							<td>
								<s:textfield name="cardissuerPlanFee.customAmt" cssClass="{required:true, decimal:'14,2'}" />
								<span class="field_tipinfo">请输入金额</span>
							</td>
						</tr>
					</table>
					
					<table class="form_grid" width="60%" border="0" cellspacing="3" cellpadding="0" id="idSectTbl">
						<tr>
						   <td align="center" nowrap class="titlebg">周期</td>
						   <td align="center" nowrap class="titlebg">收费子规则参数或百分比</td>
						   <td align="center" nowrap class="titlebg">赠送卡数量</td>
						</tr>
					
						<s:iterator value="planFeeRuleList" status="status">
						<tr id="idDetail_<s:property value="#status.count"/>">
							<td align="center" nowrap="nowrap">第${period}年</td>
							<td align="center">
								<input type="hidden" name="subPeriods" value="${period}" />
								<input type="text" id="id_subParams_<s:property value="#status.count"/>" name="subParams" value="${ruleParam}" />
							</td>
							<td align="center">
								<input type="text" id="id_subSendSum_<s:property value="#status.count"/>" name="subSendSum" value="${sendNum}" maxlength="8" />
							</td>
						</tr>
						</s:iterator>
						
						<s:if test="(planFeeRuleList == null) || (planFeeRuleList.size == 0)">
						<tr id="idDetail_1">
							<td align="center"><s:hidden name="subPeriods" value="1" />第1年</td>
							<td align="center"><s:textfield id="id_subParams" name="subParams" /></td>
							<td align="center"><s:textfield id="id_subSendSum" name="subSendSum" maxlength="8"/></td>
						</tr>
						</s:if>
						<tr id="idLink" style="line-height: 30px;">
							<td align="left" colspan="4" style="padding-left: 20px;">
								<span class="redlink"><a href="javascript:void(0);" onclick="javascript:addItem();">增加一项</a><a class="ml30" href="javascript:void(0);" onclick="javascript:deleteItem();">删除一项</a></span>
							</td>
						</tr>
					</table>
					
					<table class="form_grid" width="60%" border="0" cellspacing="3" cellpadding="0">
						<tr style="margin-top: 30px;">
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" name="ok" onclick="return checkSubmitModel();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/singleProduct/planfee/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARD_PLANFEE_ADD"/>
				
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>