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
		<f:js src="/js/biz/retransCardReg/retrans.js"/>
		
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
		// 服务端查询充值子账户余额、赠券子账户余额
		$(function(){
			$('#cardId').blur(querySubAcctBal);
			$("#merchId").change(function(){
				var merchValue = $('#merchId').val(); 
				if(merchValue!=null && merchValue!=""){
					$("#termId_Id").load(CONTEXT_PATH + "/retransCardReg/getTermList.do",{'retransCardReg.merchId':merchValue, 'callId':callId()});
				}
			});

		});
		
		function querySubAcctBal(){
			var cardId = $('#cardId').val();
			
			if(cardId == null || cardId == undefined || trim(cardId) == ""){
				return;
			}
			$.post(CONTEXT_PATH + '/retransCardReg/querySubAcctBal.do', {'retransCardReg.cardId':cardId}, 
				function(json){
					if (json.success){	//ajax检测cardId合法
						
						$("#merchName").removeMulitselector();
						Selector.selectMerch('merchName', 'merchId', true, json.cardIssuer,"","",""
								,function(){$('#merchId').change();});
									
						$('#acctId').val(json.resultAcctId);
						// 将"使用赠券"列表清空
						$('#Id_couponUsage_td2').val('');
						if(json.couponFlag){
							$('#couAccu_tr').show();
							$('#coupon_td_1').show();
							$('#coupon_td_2').show();
							$('#coupon_tr').hide();
							$('#Id_couponUsage_td2').attr('disabled', 'true');
							$('#depReb_tr').hide();
							$('#deposit_td_1').hide();
							$('#deposit_td_2').hide();
							$('#rebate_td_1').hide();
							$('#rebate_td_2').hide();
							
							// 将"使用赠券"列表清空
							$('#Id_couponUsage_td2').val('');
							
							$('#couponAmt').val(json.resultCouponAmt);
						}else{
							$('#couAccu_tr').hide();
							
							$('#depReb_tr').hide();
							$('#coupon_tr').show();
							$('#Id_couponUsage_td1').show();
							$('#Id_couponUsage_td2').show();
							$('#Id_couponUsage_td2').removeAttr('disabled');
							$('#overDraft_td_1').hide();
							$('#overDraft_td_2').hide();
							$('#couponAmt').val(json.resultCouponAmt);
							$('#depositAmt').val(json.resultDepositAmt);
							$('#rebateAmt').val(json.resultRebateAmt);
							
							// 签单卡
							if(json.signFlag){
								$('#overDraft_td_1').show();
								$('#overDraft_td_2').show();
								$('#overDraftAmt').val(json.resultOverDraftAmt);
							}
						}
						$(':submit').removeAttr('disabled');
					} else {	//ajax检测cardId非法
						$("#merchName").removeMulitselector();
						
						showMsg(json.error);
						$(':submit').attr('disabled', 'true');
						$('#coupon_tr').hide();
						$('#depReb_tr').hide();
						$('#couAccu_tr').hide();
						$('#sign_tr').hide();
						// 将"使用赠券"列表清空
						$('#Id_couponUsage_td2').val('');
					}
				}, 'json');
		}
		
		function checkField(){			
			var acctId = $("#acctId").val();
			var depositAmt = $("#depositAmt").val();
			var rebateAmt = $("#rebateAmt").val();
			var couponAmt = $("#couponAmt").val();
			var accuAmt = $("#accuAmt").val();
			var amt = $("#amt").val();
			var couponUsage = $('#Id_couponUsage_td2').val();
			var couponFalg = $("#couponFalg").val();
			var accuFlag = $("#accuFalg").val();
			
			alert(accuAmt);
			alert(couponUsage);
			
			// 先用赠券账户
			if(couponUsage=='0'){
				couponAmt = (isEmpty(couponAmt))?("0"):(couponAmt);
				depositAmt = "0";
				rebateAmt = "0";
				accuAmt = "0";
			} 
			// 先不使用赠券账户
			else if(couponUsage=='1'){
				depositAmt = (isEmpty(depositAmt))?("0"):(depositAmt);
				rebateAmt = (isEmpty(rebateAmt))?("0"):(rebateAmt);
				accuAmt = "0";
				couponAmt = "0";
			}
			else {
				alert(isEmpty(accuAmt));
				couponAmt = (isEmpty(couponAmt))?("0"):(couponAmt);
				accuAmt = (isEmpty(accuAmt))?("0"):(accuAmt);
				depositAmt = "0";
				rebateAmt = "0";
				
			}
			
			alert("充值："+depositAmt);
			alert("返利："+rebateAmt);
			alert("赠券："+couponAmt);
			alert("次卡："+accuAmt);
			
			amt = (isEmpty(amt))?("0"):(amt);	
			
			if(isEmpty(acctId)){
				showMsg("账号不能为空！");
				return false;
			}

			if(parseFloat(amt) > (parseFloat(depositAmt) + parseFloat(rebateAmt) + parseFloat(couponAmt) + parseFloat(accuAmt))){
				showMsg("补账金额不能大于可用余额，请重新输入补账金额！");
				//$("#amt:text").select().focus();
				return false;
			}
			return true;
		}
		
		function submitForm(){
			try{
				if(!checkField()){
					return false;
				}
				$("#inputForm").submit();
				return true;
			}catch(err){
	 			txt="本页中存在错误。\n\n 错误描述：" + err.description + "\n\n"
				alert(txt)
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
					<s:hidden name="couponFalg"></s:hidden>
					<s:hidden name="accuFlag"></s:hidden>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
								<s:textfield name="retransCardReg.cardId" id="cardId"
								cssClass="{required:true, digit:true}" maxlength="19"/>
								<span class="field_tipinfo">请输入卡号</span>
							</td>							
							<td width="80" height="30" align="right">账号</td>
							<td>
								<s:textfield name="retransCardReg.acctId" id="acctId" cssClass="readonly" readonly="true" />								
							</td>							
						</tr>
						<tr>
							<td width="80" height="30" align="right">商户</td>
							<td>
								<!-- 
								<select name="retransCardReg.merchId" id="merchId_Id"
								class="{required:true}"></select> -->
								<input type="hidden" id="merchId" name="retransCardReg.merchId" class="{required:true}"/>
								<input type="text" id="merchName" class="{required:true}"/>
								
								<span class="field_tipinfo">请选择商户</span>								
							</td>
							<td width="80" height="30" align="right">终端号</td>
							<td>
								<select name="retransCardReg.termId" id="termId_Id"
								class="{required:true}"></select>
								<span class="field_tipinfo">请选择终端号</span>								
							</td>							
						</tr>
						<tr id="coupon_tr" style="display:none">
						<td id="Id_couponUsage_td1" width="80" height="30" align="right">使用账户</td>							
						<td>
							<s:select id="Id_couponUsage_td2" name="retransCardReg.coupon" list="couponUseStateList" headerKey="" headerValue="--请选择--" 
								listKey="value" listValue="name" cssClass="{required:true}" disabled="disabled" onchange="Retrans.couponUsageChange()" >
							</s:select>
							<span class="field_tipinfo">请选择使用账户</span>
							<td id="overDraft_td_1" width="80" height="30" align="right" style="display:none">签单透支额度</td>
							<td id="overDraft_td_2" style="display:none">
								<s:textfield id="overDraftAmt" cssClass="readonly" readonly="true"/><span>元</span>								
							</td>																
						</td>
						</tr>
						<tr id="depReb_tr" style="display:none" >
							<td id="deposit_td_1" width="80" height="30" align="right" style="display:none">充值账户余额</td>
							<td id="deposit_td_2" style="display:none">
								<s:textfield id="depositAmt" cssClass="readonly" readonly="true"/><span>元</span>								
							</td>							
							<td id="rebate_td_1" width="80" height="30" align="right" style="display:none">返利账户余额</td>
							<td id="rebate_td_2" style="display:none"> 
								<s:textfield id="rebateAmt" cssClass="readonly" readonly="true"/><span>元</span>								
							</td>						
						</tr>
						<tr id="couAccu_tr"  style="display:none">
							<td id="coupon_td_1" width="80" height="30" align="right" style="display:none">赠券账户余额</td>
							<td id="coupon_td_2" style="display:none">
								<s:textfield id="couponAmt" cssClass="readonly" readonly="true"/><span>元</span>								
							</td>
							<!--<td id="accu_td_1" width="80" height="30" align="right" style="display:none">次卡账户余额</td>
							<td id="accu_td_2" style="display:none">
								<s:textfield id="accuAmt" cssClass="readonly" readonly="true"/>								
							</td>
							-->
						</tr>
						<tr>
							<td width="80" height="30" align="right">补账金额</td>
							<td>
								<s:textfield name="retransCardReg.amt" id="amt" cssClass="{required:true, num:true, decimal:'12,2'}" cssStyle="width:110px;" maxlength="14"/><span>元</span>	
								<span class="field_tipinfo">最大10位整数，2位小数</span>	
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="retransCardReg.remark" />								
							</td>													
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/retransCardReg/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_RETRANSCARDREG_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>