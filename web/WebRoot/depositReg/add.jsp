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
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<f:js src="/js/cert/AC_ActiveX.js"/>
		<f:js src="/js/cert/AC_RunActiveContent.js"/>
		
		<s:if test='@gnete.card.service.mgr.BranchBizConfigCache@isNeedReadM1CardId(loginBranchCode)'>
			<%-- 从读卡器读取M1卡的卡号 --%>
			<object id="MWRFATL" codeBase="${CONTEXT_PATH}/activeX/R6-W8.cab#version=1,0,0,1" classid="clsid:2C65E17A-734D-4014-9813-D3E6B1C7EF0C" width="0" height="0" style="display: none"></object>
			<f:js src="/js/mwReader/mwReader.js"/>
		</s:if>
		
		<s:if test='@gnete.card.service.mgr.BranchBizConfigCache@isNeedReadIcCardId(loginBranchCode)'>
			<%-- 从读卡器读取IC卡的卡号 --%>
			<object id="HiddenCOM" classid="clsid:681A7F73-27BA-4362-A899-897D94DAF08A"></object>
			<f:js src="/js/icReader/icReader.js"/>
		</s:if>
		
		<f:js src="/js/biz/depositReg/add.js"/>
		<script>
		$(function(){
			$("#cardId").focus();
			$('#submitBtn').attr('disabled', 'true');
			
			$(":radio[name='timesDepositType']").click(changeDepositType);
		});

		
		function changeDepositType(){
			var value = $('input:radio[name="timesDepositType"]:checked').val();
			if (value == '0'){
				$('#idAmtLable1').html('充值次数');
				$('#idAmtLable2').html('请输入充值次数');
				$('#idLastAvblLabel').html('前期次数');
			} else if (value == '1' || value == '2'){
				$('#idAmtLable1').html('充值金额');
				$('#idAmtLable2').html('请输入充值金额');
				$('#idLastAvblLabel').html('前期余额');
			}
			var cardId = $('#cardId').val();
			if (isEmpty(cardId)){
				return;
			}
			$.post(CONTEXT_PATH + '/depositReg/searchForAccu.do', 
				{'type': value, 'cardId': cardId}, 
				function(data){
					$('#avblBal').val(fix(data.resultAvblBal));
				},'json');
			calRealAmt();
		}
		
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<jsp:include flush="true" page="/common/loadingBarDiv.jsp"></jsp:include>
		
		<div id="contentDiv" class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<s:if test='@gnete.card.service.mgr.BranchBizConfigCache@isNeedReadM1CardId(loginBranchCode)'>
							<tr>
								<td width="80" height="30" align="right"></td>
								<td height="45" colspan="3">
									<input id="readM1CardIdBtn" type="button" value="读取M1卡卡号" 
										onclick='$("#cardId").val(MwReader.readM1CardId(this)); $("#cardId").change();'
										title="请确保已经链接M1卡读卡器"/>
								</td>
							</tr>
						</s:if>
						<s:if test='@gnete.card.service.mgr.BranchBizConfigCache@isNeedReadIcCardId(loginBranchCode)'>
							<tr>
								<td width="80" height="30" align="right"></td>
								<td height="45" colspan="2" align="left">
									IC卡读卡方式：
									<label><input type="radio" id="idForTouch" name="touchType" value="0" />接触式</label>
									<label><input type="radio" id="idForUntouch" name="touchType" value="1" checked="checked"/>非接触式</label>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<input id="readIcCardIdBtn" type="button" value="读取IC卡卡号"  onclick="readIcCardId();" title="请确保已经链接IC卡读卡器"/>
								</td>
							</tr>
						</s:if>
						
						<s:if test="@gnete.card.service.mgr.BranchBizConfigCache@isNeedDepositRebateAcct(loginBranchCode)">
							<input type="hidden" id="isNeedDepositRebateAcct" value="1" />
						</s:if>
						<tr id="depositTypeTr" class="no" style="margin-top: 20px" >
							<td width="70" height="30" align="right">
								充值方式：
							</td>
							<td height="30" colspan="3"><%-- 跟登记表记录的充值类型有关，但字段值有转换 --%>
								<label id="depositTypeAmtLabel"><input type="radio" id="depositTypeAmt" name="timesDepositType" value="1" checked="checked" />充值资金子帐户</label>
								<label id="depositTypeRebateAmtLabel"><input type="radio" id="depositTypeRebateAmt" name="timesDepositType" value="2" />返利资金子帐户</label>
								<label id="depositTypeTimesLabel"><input type="radio" id="depositTypeTimes" name="timesDepositType" value="0" />次卡子账户</label>
							</td>
						</tr>
						
						<tr>
							<td width="70" height="30" align="right">卡号</td>
							<td>
								<s:textfield id="cardId" name="depositReg.cardId" cssClass="{required:true}" maxlength="19" 
									onchange="searchCardInfo();"/>
								<span class="field_tipinfo">请输入卡号</span>
							</td>
							<td width="90" height="30" align="right" id="idAmtLable1">充值金额</td>
							<td>
								<s:textfield id="amt" name="depositReg.amt" cssClass="{required:true}" onchange="calRealAmt();"/>
								<span class="field_tipinfo" id="idAmtLable2">请输入充值金额</span>
							</td>
						</tr>
						<tr id="idRebateTypeTr" style="display: none;">
							<td width="80" height="30" align="right">返利方式</td>
							<td>
								<s:select id="rebateType" name="depositReg.rebateType" list="rebateTypeList" 
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
									cssClass="{required:true}" disabled="true" onchange="calRealAmt();">
								</s:select>
								<span class="field_tipinfo">请选择返利方式</span>
							</td>
							<td width="80" height="30" align="right">手续费比例</td>
							<td>
								<s:textfield id="id_FeeRate" name="depositReg.feeRate" maxlength="20" cssClass="{num:true, required:true, decimal:'5,3'}" maxLength="9" value="0" onchange="calRealAmt();"/>
								<span class="field_tipinfo">%最多3位小数</span>
							</td>
						</tr>
					</table>
					
					<div class="tablebox" id="idRebateRulePage"></div>
					
					<table id="idDepositTbl" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0" style="display: none;">
						<tr>
							<td width="90" height="30" align="right">账户</td>
							<td><s:textfield id="acctId" readonly="true" cssClass="readonly"/></td>
							
							<s:hidden id="depositTypeIsTimes" name="depositTypeIsTimes"></s:hidden>
							<%-- 
							<td width="90" height="30" align="right">充值类型</td>
							<td>
								<s:if test="depositTypeIsTimes==true" >
									<s:textfield id="depositType" name="depositType" value="按次充值" readonly="true" cssClass="readonly"/>
								</s:if>
								<s:else>
									<s:textfield id="depositType" name="depositType" value="按金额充值" readonly="true" cssClass="readonly"/>
								</s:else>
							</td>
							 --%>
							 
							<td width="90" height="30" align="right">卡类型</td>
							<td><s:textfield id="cardClassName" readonly="true" cssClass="readonly"/></td>							
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡子类型</td><td><s:textfield id="cardSubClassName" readonly="true" cssClass="readonly"/></td>
							<td width="80" height="30" align="right">购卡客户</td><td><s:textfield id="cardCustomerName" readonly="true" cssClass="readonly"/></td>							
							<td width="80" height="30" align="right">团购卡号</td><td><s:textfield id="groupCardID" readonly="true" cssClass="readonly"/></td>							
						</tr>
						<tr>
							<td width="80" height="30" align="right">持卡人</td><td><s:textfield id="custName" name="depositReg.custName" readonly="true" cssClass="readonly"/></td>							
							<td width="80" height="30" align="right">证件名称</td><td><s:textfield id="credTypeName" readonly="true" cssClass="readonly"/></td>							
							<td width="80" height="30" align="right">证件号码</td><td><s:textfield id="credNo" readonly="true" cssClass="readonly"/></td>							
						</tr>
						<tr>
							<td width="80" height="30" align="right">实收金额</td>
							<td>
								<s:textfield id="realAmt" name="depositReg.realAmt" cssClass="{required:true} readonly bigred" readonly="true" />
							</td>
							<td width="80" height="30" align="right">
								返利金额
								<span id="periodLabel" class="no">(分期)</span>
							</td>
							<td>
								<s:textfield id="rebateAmt" name="depositReg.rebateAmt" cssClass="{required:true} readonly" readonly="true" />
							</td>
							<td width="80" height="30" align="right" id="idLastAvblLabel">前期余额</td>
							<td>
								<s:textfield id="avblBal" name="depositReg.avblBal" readonly="true" cssClass="readonly"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">手续费</td>
							<td>
								<s:textfield id="id_FeeAmt" name="depositReg.feeAmt" cssClass="{required:true, num:true} readonly " readonly="true" />
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield id="remark" name="depositReg.remark" />
							</td>
							<td width="80" >返利计算方式</td>
							<td>
								<input type="radio" id="idCalcModeAuto" name="calcMode" value="0" checked="checked" onclick="selectCalcMode()"/>
								<label for="idCalcModeAuto">自动计算</label>
								<input type="radio" id="idCalcModeManual" name="calcMode" value="1" onclick="selectCalcMode()"/>
								<label for="idCalcModeManual">手工指定</label>
								<!-- 
								<input type="button" value="计算费用" onclick="calRealAmt()" />
								 -->
							</td>
						</tr>
						<tr>
							<td></td>
							<td colspan="11">
								<span id='isUsedPeriodRuleTip' class="bigred no">该卡已经使用过所选择的分期返利！</span>
							</td>
						</tr>
					</table>
					
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td><s:hidden id="cardClass" name="depositReg.cardClass"  /></td>							
							<td><s:hidden id="cardSubClass"  /></td>							
							<td><s:hidden id="cardCustomerId" name="depositReg.cardCustomerId"  /></td>							
							<td><s:hidden id="resultRebateType" name="rebateType"  /></td>							
							<td><s:hidden id="rebateId" name="depositReg.rebateId"  /></td>							
							<td><s:hidden id="calType" name="depositReg.calType"  /></td>							
							<td><s:hidden name="actionSubName" /></td>			
							<td><s:hidden id="id_randomSessionId" name="depositReg.randomSessionId" /></td>	
							<td><s:hidden id="id_signature" name="depositReg.signature" /></td>	
							<s:hidden id="id_signatureReg" name="signatureReg" />
							<s:hidden id="id_serialNo" name="serialNo"/>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button"  id="submitBtn" value="提交"  name="ok"  onclick="return submitDepositForm();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/depositReg/list.do?goBack=goBack')" class="ml30" />
							</td>
							<td></td><td></td>
						</tr>
					</table>
					<s:token name="_TOKEN_DEPOSIT_REG_ADD"/>
				</s:form>
				 <script type="text/javascript">
					AC_AX_RunContent( 'classid','clsid:B494F2C1-57A7-49F7-A7AF-0570CA22AF80','id','FTUSBKEYCTRL','style','DISPLAY:none' ); //end AC code
				</script>
				<noscript><OBJECT classid="clsid:B494F2C1-57A7-49F7-A7AF-0570CA22AF80" id="FTUSBKEYCTRL" style="DISPLAY:none"/></OBJECT></noscript>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>