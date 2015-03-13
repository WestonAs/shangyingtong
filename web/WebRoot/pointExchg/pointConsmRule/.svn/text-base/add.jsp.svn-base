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
			//$('#Id_ptClass').change(getPtClass);
			$('#Id_ptClass').change(loadPointConsmRuleList);
			$('#Id_couponClass').change(loadPointConsmRuleList);
			$('#Id_ptParam').blur(validatePtParam);
			$('#Id_ruleParam1').blur(validateRuleParam1);
		});
		
		function loadPointConsmRuleList(){
			var ptClass = $('#Id_ptClass').val(); 
			var couponClass = $('#Id_couponClass').val(); 
			if(ptClass==''||couponClass==''){
				//showMsg("请先选择积分类型和。");
				return;
			}
			$('#pointConsmRule_div').show().html(LOAD_IMAGE).load(CONTEXT_PATH+
			'/pointExchg/pointConsmRule/getPtConsmRuleList.do?',
			{'pointConsmRuleDef.ptClass':ptClass, 'pointConsmRuleDef.couponClass':couponClass},function() {
				SysStyle.setDataGridStyle();
			});
		}
		
		function validatePtParam(){
			var ptClass = $('#Id_ptClass').val(); 
			var couponClass = $('#Id_couponClass').val(); 
			var ptParam = $('#Id_ptParam').val(); 
			
			if(ptParam == ''){
				clearIpInfo();
				$('#Id_ruleParam1').val('');
				return;
			}
			if(ptClass == ''||couponClass == ''){
				showMsg("请先选择积分类型和赠券类型。");
				$('#Id_ptParam').val(''); 
				return;
			}
			
			// 根据输入的积分参数提示可输入的兑换赠券范围
			$.post(CONTEXT_PATH + '/pointExchg/pointConsmRule/couponAmtSpan.do', 
			{'pointConsmRuleDef.ptClass':ptClass, 'pointConsmRuleDef.couponClass':couponClass, 'pointConsmRuleDef.ptParam':ptParam }, 
			function(json){
				if (json.success){
					$('#componAmtMin').val('');
					$('#componAmtMax').val('');
					if(isEmpty(json.couponAmtMin) && isEmpty(json.couponAmtMax)){
						$('#Id_ruleParam1Field').html("请输入兑换的赠券值（整数）");
					} else if(json.couponAmtMin!='' && json.couponAmtMax==''){
						$('#Id_ruleParam1Field').html('可输入的兑换赠券最小为['+ json.couponAmtMin +
							']。').addClass('error_tipinfo').show();
						$('#componAmtMin').val(json.couponAmtMin);
					} else if(json.couponAmtMin=='' && json.couponAmtMax!=''){
						$('#Id_ruleParam1Field').html('可输入的兑换赠券最大为['+ json.couponAmtMax +
							']。').addClass('error_tipinfo').show();
						$('#componAmtMax').val(json.couponAmtMax);
					} else {
						$('#Id_ruleParam1Field').html('可输入的兑换赠券最小为['+ json.couponAmtMin +
							']，最大为['+ json.couponAmtMax +']。').addClass('error_tipinfo').show();
						$('#componAmtMin').val(json.couponAmtMin);
						$('#componAmtMax').val(json.couponAmtMax);
					}
					$(':submit').removeAttr('disabled');
				} else {
					showMsg(json.error);
					clearIpInfo();
					$(':submit').attr('disabled', 'true');
					$('#Id_ptParam').val('');
				}
			}, 'json');
		}
		
		function clearIpInfo(){
			$('#Id_ruleParam1Field').removeClass('error_tipinfo').html('请输入兑换的赠券值（整数）');
		}
		
		function validateRuleParam1(){
			var ptParam = $('#Id_ptParam').val(); 
			var ruleParam1 = $('#Id_ruleParam1').val(); 
			var componAmtMin = $('#componAmtMin').val(); 
			var componAmtMax = $('#componAmtMax').val(); 
			
			if(ruleParam1 == ''||ruleParam1 == undefined){
				return;
			}
			if(ptParam == ''){
				showMsg("请先输入积分参数。");
				$('#Id_ruleParam1').val(''); 
				return;
			}
			if(componAmtMin!=''&componAmtMax==''){
				if(parseFloat(componAmtMin)>parseFloat(ruleParam1)){
					showMsg("请输入有效的兑换赠券。");
					$(':submit').attr('disabled', 'true');
				} else {
					$(':submit').removeAttr('disabled');
				}
				
			}
			if(componAmtMin==''&componAmtMax!=''){
				if(parseFloat(componAmtMax)<parseFloat(ruleParam1)){
					showMsg("请输入有效的兑换赠券。");
					$(':submit').attr('disabled', 'true');
				} else {
					$(':submit').removeAttr('disabled');
				}
			}
			if(componAmtMin!=''&componAmtMax!=''){
				if(parseFloat(componAmtMax)<parseFloat(ruleParam1)||parseFloat(componAmtMin)>parseFloat(ruleParam1)){
					showMsg("请输入有效的兑换赠券。");
					$(':submit').attr('disabled', 'true');
				} else {
					$(':submit').removeAttr('disabled');
				}
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
				<s:hidden id="componAmtMin"></s:hidden>
				<s:hidden id="componAmtMax"></s:hidden>
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td width="80" height="30" align="right">积分类型</td>
							<td>
								<s:select id="Id_ptClass" name="pointConsmRuleDef.ptClass" list="pointClassDefList" headerKey="" 
								headerValue="--请选择--" listKey="ptClass" listValue="className" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择积分类型</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">赠券类型</td>
							<td>
								<s:select id="Id_couponClass" name="pointConsmRuleDef.couponClass" list="couponClassDefList" headerKey="" 
								headerValue="--请选择--" listKey="coupnClass" listValue="className" cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择赠券类型</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">积分参数</td>
							<td>
							<s:textfield id="Id_ptParam" name="pointConsmRuleDef.ptParam" cssClass="{required:true,digit:true}"/>
							<span class="field_tipinfo">请输入兑换的积分值（整数）</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">兑换赠券</td>
							<td>
								<s:textfield id="Id_ruleParam1" name="pointConsmRuleDef.ruleParam1" cssClass="{required:true, digit:true}"/>
								<span id="Id_ruleParam1Field" class="field_tipinfo">请输入兑换的赠券值（整数）</span>
							</td>
						</tr>
					</table>
					<div id="pointConsmRule_div">
						<jsp:include flush="true" page="/pointExchg/pointConsmRule/pointConsmRuleList.jsp"></jsp:include>
					</div>
					<table>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pointExchg/pointConsmRule/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_POINTCONSMRULE_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>