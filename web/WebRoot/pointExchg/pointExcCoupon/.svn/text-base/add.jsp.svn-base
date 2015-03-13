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
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/validate.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			
		$(function(){
			$('#cardId').change(getPtClass);
			//$('#ptValueId').blur(getPtClass);
		});
		
		function getPtClass(){
			var cardId = $('#cardId').val();
			if(cardId == null || cardId == undefined || cardId == ""){
				return false;
			}
			
			$.post(CONTEXT_PATH + '/pointExchg/pointExcCoupon/validateCardId.do', 
			{'pointExcCouponReg.cardId':cardId, 'callId':callId()},
				function(json){
					if (json.success){
						$('#acctId').val(json.resultAcctId);
					} else {
						$('#acctId').val('');
						showMsg(json.error);
					}
				}, 'json');
			
			$("#ptClass").load(CONTEXT_PATH + 
			"/pointExchg/pointExcCoupon/getPointBalAvalList.do",
			{'pointExcCouponReg.cardId':cardId, 'callId':callId()});
		}
		
		function queryCouponList(){
			var acctId = $('#acctId').val(); 
			var ptClass = $('#ptClass').val(); 
			if(isEmpty(ptClass)){
				$('#ptAvlbId').val('');
				$('#couponClassId').html('');
				$('#ptExchgRule_Id').val('');
				$('#ptParamId').val('');
				$('#ruleParam1Id').val('');
				$('#couponAmtId').val('');
				$('#pointConsmRule_div').show().html(LOAD_IMAGE).load(CONTEXT_PATH+
				'/pointExchg/pointExcCoupon/getPtConsmRuleList.do?',
				{'pointExcCouponReg.ptClass':ptClass, 'pointExcCouponReg.couponClass':''},function() {
					SysStyle.setDataGridStyle();
				});
				return;
			}
			$.post(CONTEXT_PATH + '/pointExchg/pointExcCoupon/getPtAvlb.do',
			 {'pointExcCouponReg.ptClass':ptClass, 'pointExcCouponReg.acctId':acctId,'callId':callId()},
				function(json){
					if (json.success){
						$('#ptAvlbId').val(json.ptAvlb);
					} else {
						$('#ptAvlbId').val('');
						$('#couponAmtId').val('');
						showMsg(json.error);
					}
				}, 'json');
				
			$('#couponClassId').html('');
			$("#couponClassId").load(CONTEXT_PATH + 
			"/pointExchg/pointExcCoupon/getCouponAvalList.do",
			{'pointExcCouponReg.ptClass':ptClass, 'callId':callId()});
		}
		
		function getCouponAmt(){
			var ptClass = $('#ptClass').val(); 
			var couponClass = $('#couponClassId').val(); 
			var ptValue = $('#ptValueId').val(); 
			var ptAvlb = $('#ptAvlbId').val(); 
			
			if(isEmpty(ptValue)){
				return;
			}
			if(!checkIsInteger(ptValue)){
				return;
			}
			$.post(CONTEXT_PATH + '/pointExchg/pointExcCoupon/getCouponAmt.do', 
			{'pointBal.ptAvlb':ptAvlb, 'pointExcCouponReg.ptValue':ptValue, 
			'pointExcCouponReg.ptClass':ptClass, 'pointExcCouponReg.couponClass':couponClass, 'callId':callId()},
				function(json){
					if (json.success){
						if(isEmpty(json.ptValue)){
						
						}
						else if(json.ptValue == '0'){
							$('#id_ptValueField').html("请输入积分(整数)");
						} 
						else {
							$('#id_ptValueField').html("实际使用兑换积分["+ 
							(parseFloat(ptValue)- parseFloat(json.ptValue)) +"].").addClass('error_tipinfo').show();;
						}
						$('#couponAmtId').val(json.couponAmt);
					} else {
						$('#couponAmtId').val('');
						$('#ptValueId').val('');
						showMsg(json.error);
					}
				}, 'json');
		}
		
		function loadPointConsmRuleList(){
			var ptClass = $('#ptClass').val(); 
			var couponClass = $('#couponClassId').val(); 
			if(isEmpty(ptClass)){
				showMsg("请先选择积分类型。");
				return;
			}
			$('#pointConsmRule_div').show().html(LOAD_IMAGE).load(CONTEXT_PATH+
			'/pointExchg/pointExcCoupon/getPtConsmRuleList.do?',
			{'pointExcCouponReg.ptClass':ptClass, 'pointExcCouponReg.couponClass':couponClass},function() {
				SysStyle.setDataGridStyle();
			});
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
							<td width="80" height="30" align="right">卡号</td>
							<td>
								<s:textfield id="cardId" name="pointExcCouponReg.cardId" 
								cssClass="{required:true, digit:true}" maxlength="19"></s:textfield>
								<span class="field_tipinfo">请输入数字卡号</span>
							</td>
							<td width="80" height="30" align="right">账户</td>
							<td>
								<s:textfield id="acctId" name="pointExcCouponReg.acctId" 
								cssClass="readonly" readonly="true" ></s:textfield>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">积分类型</td>
							<td>
								<select name="pointExcCouponReg.ptClass" id="ptClass" class="{required:true}" 
								onchange="queryCouponList();"></select>									
								<span class="field_tipinfo">请选择积分</span>
							</td>
							<td width="80" height="30" align="right">可用积分</td>
							<td>
								<s:textfield name="pointBal.ptAvlb" id="ptAvlbId" cssClass="readonly" readonly="true" />
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">赠券类型</td>
							<td>
								<select name="pointExcCouponReg.couponClass" id="couponClassId" class="{required:true}" 
								onchange="loadPointConsmRuleList();">
								</select>			
								<span class="field_tipinfo">请选择赠券</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">兑换积分</td>
							<td>
								<s:textfield name="pointExcCouponReg.ptValue" id="ptValueId" cssClass="{required:true, digit:true}" 
								onchange="getCouponAmt();"/>
								<span id="id_ptValueField" class="field_tipinfo">请输入积分（整数）</span>
							</td>
							<td width="80" height="30" align="right">兑换赠券</td>
							<td>
								<s:textfield name="pointExcCouponReg.couponAmt" id="couponAmtId" cssClass="{required:true}" readonly="true" />
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="pointExcCouponReg.remark" />
								<span class="field_tipinfo">请输入备注</span>
							</td>
						</tr>
					</table>
					<div>
					<tr></tr>
					</div>
					<div id="pointConsmRule_div">
						<jsp:include flush="true" page="/pointExchg/pointExcCoupon/pointConsmRuleList.jsp"></jsp:include>
					</div>
					<table class="form_grid" width="60%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pointExchg/pointExcCoupon/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
				<s:token name="_TOKEN_POINTEXCCOUPON_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>