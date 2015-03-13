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
		
		<f:js src="/js/biz/gift/addGift.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#id_cardId').blur(function(){
				var value = $(this).val();
				if(isEmpty(value)){
					return;
				}
				$.post(CONTEXT_PATH + '/pointExchg/pointExc/getPtClassAndPointBal.do', {'pointExcReg.cardId':value, 'callId':callId()}, 
				function(json){
					if (json.success){
						$('#id_ptClass').val(json.ptClass);
						$('#id_ptClassName').val(json.ptClassName);
						$('#id_ptRef').val(json.ptRef);
						$('#id_ptDiscntRate').val(json.ptDiscntRate);
						$('#id_ptAvlbId').val(json.ptAvlb);
						$('#id_ptExchgRuleTypeName').val(json.ptExchgRuleTypeName);
						$(':submit').removeAttr('disabled');
					} else {
						showMsg(json.error);
						$('#id_ptClass').val('');
						$('#id_ptClassName').val('');
						$('#id_ptRef').val('');
						$('#id_ptDiscntRate').val('');
						$('#id_ptAvlbId').val('');
						$('#id_ptExchgRuleTypeName').val('');
						
						$(':submit').attr('disabled', 'true');
					}
				}, 'json');
			});
			
			$('#newExpirDate').change(function(){
				var newExpirDate = $('#newExpirDate').val();
				var cardId = $('#cardId').val();
				if(isEmpty(newExpirDate)){
					return;
				}
				if(isEmpty(cardId)){
					showMsg("请先输入卡号。");
					$('#newExpirDate').val('');
				}
			});
			
		});
		
		$(function(){
			$('#excPointId').change(getExcAmt);
		});

		// 根据返利积分算出返利金额
		function getExcAmt(){
			var excPoint = $('#excPointId').val();
			var ptClass = $('#id_ptClass').val();
			var ptAvlb = $('#id_ptAvlbId').val();
			if(isEmpty(excPoint)){
				return;
			}
			
			$.post(CONTEXT_PATH + '/pointExchg/pointExc/getExcAmt.do', {'pointExcReg.excPoint':excPoint,'pointBal.ptClass':ptClass, 'pointBal.ptAvlb':ptAvlb, 'callId':callId()},
				function(json){
					if (json.success){
						$('#excAmtId').val(json.excAmt);
						$(':submit').removeAttr('disabled');
					} else {
						showMsg(json.error);
						$(':submit').attr('disabled', 'true');
						$('#excAmtId').val('');
					}
				}, 'json');
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
							<s:textfield id="id_cardId" name="pointExcReg.cardId" onkeyup="this.value=this.value.replace(/\D/g,'')" 
							onafterpaste="this.value=this.value.replace(/\D/g,'')" cssClass="{required:true}"  maxlength="19" />
								<span class="field_tipinfo" id=""></span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">积分类型</td>
							<td>
								<s:textfield id="id_ptClass" name="pointExcReg.ptClass" cssClass="readonly" readonly="true" />
							</td>
							<td width="80" height="30" align="right">类型名称</td>
							<td>
								<s:textfield id="id_ptClassName" name="" cssClass="readonly" readonly="true" />
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">参考积分</td>
							<td>
								<s:textfield id="id_ptRef" name="" cssClass="readonly" readonly="true"/>
							</td>
							<td width="80" height="30" align="right">兑换率</td>
							<td>
								<s:textfield id="id_ptDiscntRate" name="" cssClass="readonly" readonly="true" />
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">可用积分</td>
							<td>
								<s:textfield id="id_ptAvlbId" name="" cssClass="readonly" readonly="true" />
							</td>
							<td width="80" height="30" align="right">返利规则</td>
							<td>
								<s:textfield id="id_ptExchgRuleTypeName" name="" cssClass="readonly" readonly="true" />
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">返利积分</td>
							<td colspan="3">
								<s:textfield id="excPointId" name="pointExcReg.excPoint" cssClass="{required:true, digit:true}" maxlength="8" />
								<span class="field_tipinfo">请输入整数，大于或者等于参考积分，小于或者等于可用积分</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">返利金额</td>
							<td>
								<s:textfield id="excAmtId" name="pointExcReg.excAmt" readonly="true" cssClass="{required:true, decimal:'14,2'} readonly" /><span>元</span>
								<span class="field_tipinfo">返利积分x兑换率</span>
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="pointExcReg.remark" />
								<span class="field_tipinfo">请输入备注</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pointExchg/pointExc/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_POINTEXC_ADD"/>
				</s:form>
			</div>
			<div id="pointBal_div">
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>