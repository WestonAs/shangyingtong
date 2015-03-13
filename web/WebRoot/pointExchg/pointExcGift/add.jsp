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
			$('#cardId').blur(function(){
				$('#giftId').html('');
				var cardId = $('#cardId').val();
				if(!checkCardNum(cardId)){
					return false;
				}
				
				$.post(CONTEXT_PATH + '/pointExchg/pointExcGift/validateCardId.do', {'giftExcReg.cardId':cardId, 'callId':callId()},
					function(json){
						if (json.success){
							$(':submit').removeAttr('disabled');
						} else {
							showMsg(json.error);
							$(':submit').attr('disabled', 'true');
						}
					}, 'json');
				
				$("#ptClass").load(CONTEXT_PATH + "/pointExchg/pointExcGift/ptClassList.do",{'giftExcReg.cardId':cardId, 'callId':callId()});
			});
		});
		
		function getPtClass(){
		
		}
		
		function queryGiftList(){
			$('#idPtValueTd').val('');
			var ptClass = $('#ptClass').val(); 
			var cardId = $('#cardId').val();
			
			if(!isEmpty(ptClass) && !isEmpty(cardId)){
				$.post(CONTEXT_PATH + '/pointExchg/pointExcGift/validatePtClass.do', {'giftExcReg.ptClass':ptClass, 'giftExcReg.cardId':cardId, 'callId':callId()},
				function(json){
					if (json.success){
						$('#id_PtAval').val(json.resultPtAvlb);
						$(':submit').removeAttr('disabled');
					} else {
						showMsg(json.error);
						$('#idPtValueTd').val('');
						$('#id_PtAval').val('');
						$(':submit').attr('disabled', 'true');
					}
				}, 'json');
			}
			else {
				$('#idPtValueTd').val('');
				$('#id_PtAval').val('');
			}
			
			$("#giftId").load(CONTEXT_PATH + "/pointExchg/pointExcGift/giftList.do",{'giftExcReg.ptClass':ptClass, 'giftExcReg.cardId':cardId, 'callId':callId()});
		}
		
		function loadPoint(){
			var giftId = $('#giftId').val(); 
			
			if(!isEmpty(giftId)){
				$.post(CONTEXT_PATH + "/pointExchg/pointExcGift/getPtValue.do",{'giftExcReg.giftId':giftId, 'callId':callId()}, 
				function(json){
					if (json.success){
						$('#idPtValueTd').val(json.resultPtValue);
						$(':submit').removeAttr('disabled');
					} else {
						showMsg(json.error);
						$('#idPtValueTd').val('');
						$(':submit').attr('disabled', 'true');
					}
				}, 'json');
			}
			else {
				$('#idPtValueTd').val('');
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
							<td width="80" height="30" align="right">卡号</td>
							<td>
							<s:textfield id="cardId" name="giftExcReg.cardId" onkeyup="this.value=this.value.replace(/\D/g,'')" 
							onafterpaste="this.value=this.value.replace(/\D/g,'')" cssClass="{required:true, digit:true}"  maxlength="19" />
							<span class="field_tipinfo">请输入数字卡号</span>
							</td>
						</tr>	
						<tr>	
							<td width="80" height="30" align="right">积分类型</td>
							<td>
								<select name="giftExcReg.ptClass" id="ptClass" class="{required:true}" 
								onchange="queryGiftList();"></select>									
							</td>
							<td width="80" height="30" align="right">可用积分</td>
							<td>
								<s:textfield id="id_PtAval" cssClass="readonly" readonly="true" />								
							</td>	
						</tr>
						<tr>	
							<td width="80" height="30" align="right">礼品</td>
							<td>
								<select name="giftExcReg.giftId" id="giftId" class="{required:true}" 
								onchange="loadPoint();">
								</select>			
							</td>
							<td width="80" height="30" align="right">兑换积分</td>
							<td>
								<s:textfield id="idPtValueTd" cssClass="readonly" readonly="true" />								
							</td>	
						</tr>
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td>
							<s:textfield name="giftExcReg.remark" />
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pointExchg/pointExcGift/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_POINTEXCGIFT_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>