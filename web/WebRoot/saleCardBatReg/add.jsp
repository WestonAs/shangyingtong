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
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		
		<f:js src="/js/biz/saleCardBatReg/add.js?v=20140703"/>
		<f:js src="/js/cert/AC_ActiveX.js"/>
		<f:js src="/js/cert/AC_RunActiveContent.js"/>
		
		<s:if test='@gnete.card.service.mgr.BranchBizConfigCache@isNeedReadM1CardId(loginBranchCode)'>
			<!-- 从读卡器读取M1卡的卡号 -->
			<object id="MWRFATL" codeBase="${CONTEXT_PATH}/activeX/R6-W8.cab#version=1,0,0,1" classid="clsid:2C65E17A-734D-4014-9813-D3E6B1C7EF0C" width="0" height="0" style="display: none"></object>
			<f:js src="/js/mwReader/mwReader.js"/>
		</s:if>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<jsp:include flush="true" page="/common/loadingBarDiv.jsp"></jsp:include>
		
		<div id="contentDiv" class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<s:hidden id="id_RandomSessionId" name="saleCardReg.randomSessionId" />
					<s:hidden id="id_Signature" name="saleCardReg.signature" />
					<s:hidden id="id_signatureReg" name="signatureReg" />
					<s:hidden id="id_serialNo" name="serialNo"/>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td width="80" height="30" align="right"></td>
							<td colspan="3">
								<input type="button" value="点击获取" title="填待售的最小卡号到起始卡号" onclick="ajaxFindFirstCardToSold();"/>
								最小待售卡号
								<img id="firstCardWaiterImg" height="20" src="${CONTEXT_PATH}/images/ajax_loader.gif" style="display: none"/>
							</td>
						</tr>
						<s:if test='@gnete.card.service.mgr.BranchBizConfigCache@isNeedReadM1CardId(loginBranchCode)'>
							<tr>
								<td width="80" height="30" align="right"></td>
								<td >
									<input id="readM1CardIdBtn" type="button" value="读取M1卡卡号" 
										onclick='$("#cardId").val(MwReader.readM1CardId(this)); $("#cardId").change();'
										title="请确保已经链接M1卡读卡器"/>
									从读卡器读取
								</td>
							</tr>
						</s:if>
						<tr>
							<td width="80" height="30" align="right">起始卡号</td>
							<td>
								<s:textfield id="cardId" name="saleCardReg.strCardId" maxlength="19" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入起始卡号</span>
							</td>
							<td width="80" height="30" align="right">卡连续数</td>
							<td>
								<s:textfield id="id_CardCount" name="cardCount" cssClass="{required:true, num:true}"/>
								<span class="field_tipinfo">请输入整数</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">结束卡号</td>
							<td>
								<s:textfield id="id_EndCardId" name="saleCardReg.endCardId" maxlength="19" readonly="true" cssClass="{required:true, num:true} readonly"/>
								<span class="field_tipinfo">自动匹配</span>
								<img id="endCardIdWaiterImg" height="20" src="${CONTEXT_PATH}/images/ajax_loader.gif" style="display: none"/>
							</td>
							<td width="80" height="30" align="right">购卡客户</td>
							<td>
								<s:hidden id="idCardCustomerId" name="saleCardReg.cardCustomerId" />
								<s:textfield id="idCardCustomerId_sel" name="cardCustomerName" cssClass="{required:true}" readonly="true"/>
								<span class="field_tipinfo">请选择购卡客户</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right"><s:if test="depositTypeIsTimes==true">单张充值次数</s:if><s:else>单张售卡金额</s:else></td>
							<td>
								<s:textfield id="defaultAmt" name="amt" cssClass="{required:true}" onchange="calRealAmt();"/>
								<span class="field_tipinfo">请输入数字</span>
							</td>
							<td width="80" height="30" align="right">单张工本费</td>
							<td>
								<s:textfield id="defaultExpense" name="expenses" cssClass="{required:true}" value="0.00" onchange="calRealAmt();"/>
								<span class="field_tipinfo">请输入金额</span>
							</td>
						</tr>
						<tr>							
							<td width="80" height="30" align="right">付款户名</td>
							<td>
								<s:textfield name="saleCardReg.payAccName" maxlength="20"/>
								<span class="field_tipinfo">请输入付款户名</span>
							</td>
							<td width="80" height="30" align="right">付款账号</td>
							<td>
								<s:textfield name="saleCardReg.payAccNo" cssClass="{digitOrAllowChars:'-'}" maxlength="32"/>
								<span class="field_tipinfo">请输入付款账号</span>
							</td>
						</tr>
						<s:if test='saleCardBatReg.presell=="1" && actionSubName=="PreDeposit" && cardRoleLogined'> <%-- 预售储值卡并且是发卡机构用户 --%>
							<tr>
								<td></td>
								<td height="25" colspan="11">
									<label for="isDepositCoupon">
										<s:checkbox id="isDepositCoupon" name="formMap.isDepositCoupon"/>
										赠送赠券
									</label>
								</td>
							</tr>
							<tr id="depositCouponTr" style="display: none">
								<td width="80" height="30" align="right">赠券类型</td>
								<td>
									<select id="couponClassSel" name="saleCardReg.couponClass">
									</select>
									<span class="field_tipinfo">请选择赠券类型</span>
								</td>
								
								<td width="80" height="30" align="right">赠券金额</td>
								<td>
									<s:textfield id="couponAmt" name="saleCardReg.couponAmt" cssClass="{num:true, decimal:'12,2'}"/>
									<span class="field_tipinfo">金额最多两位小数，每张卡赠送</span>
								</td>
							</tr>
						</s:if>
						<tr id="idRebateTypeTr" style="display: none;">							
							<td width="80" height="30" align="right">返利方式</td>
							<td>
								<s:select id="rebateType" name="saleCardReg.rebateType" list="rebateTypeList" 
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
									cssClass="{required:true}" disabled="true" onchange="calRealAmt();">
								</s:select>
								<span class="field_tipinfo">请选择返利方式</span>
							</td>
							<td width="80" height="30" align="right">手续费比例</td>
							<td>
								<s:textfield id="id_FeeRate" name="saleCardReg.feeRate" maxlength="20" cssClass="{num:true, required:true, decimal:'5,3'}" maxLength="9" value="0" onchange="calRealAmt(false);"/>
								<span class="field_tipinfo">%最多3位小数</span>
							</td>
						</tr>
						<tr id="idRebateCardTr" style="display: none;">
							<td width="80" height="30" align="right" id="idRebateCardTd1" style="display: none;">返利卡卡号</td>
							<td id="idRebateCardTd2" style="display: none;">
								<s:textfield id="idRebateCardId" name="saleCardReg.rebateCard" cssClass="{required:true}" disabled="true"/>
								<span class="field_tipinfo">请输入返利卡卡号</span>
							</td>
							<td width="80" height="30" align="right" id="idRebateCountTd1" style="display: none;">返利卡张数</td>
							<td id="idRebateCountTd2" style="display: none;" colspan="3">
								<s:textfield id="idRebateCountId" name="rebateCount" cssClass="{required:true, digit:true, max:20, min: 2}" disabled="true"/>
								<span class="field_tipinfo">返利卡张数应大于等于2，小于等于20</span>
							</td>
						</tr>
					</table>
					<table class="form_grid" id="idWorkflowDefineTbl" 
						width="30%" border="0" cellspacing="3" cellpadding="0" style="display:none;">
						<tr id="idRebateTitle">
						   <td align="center" nowrap class="titlebg">序号</td>
						   <td align="center" nowrap class="titlebg">返利卡号</td>
						   <td align="center" nowrap class="titlebg">返利金额</td>
						   <td align="center" nowrap class="titlebg">序号</td>
						   <td align="center" nowrap class="titlebg">返利卡号</td>
						   <td align="center" nowrap class="titlebg">返利金额</td>
						</tr>
					</table>
					<span class="redfont" id="idTip" style="display: none;">
						注意：“返利卡号”和“返利金额”都不许为空，同时所有“返利卡”的“返利金额”的和应该等于“返利合计”。
					</span>
					
					<!-- 返利规则 -->
					<div class="tablebox" id="idRebateRulePage"></div>
					
					<table id="idCardInfoTbl" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0" style="display: none;">
						<tr>
							<td width="80" height="30" align="right">售卡方式</td>
							<td>
								<s:hidden name="saleCardReg.presell"></s:hidden>
								<s:hidden name="saleCardBatReg.presell"></s:hidden>
								<s:textfield id="presell" name="saleCardBatReg.presellName" readonly="true" cssClass="readonly"/>								
							</td>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:textfield id="cardBranchName" name="cardBranchName" readonly="true" cssClass="readonly"/>
							</td>
							<td width="80" height="30" align="right">卡BIN</td>
							<td>
								<s:textfield id="cardBin" name="saleCardReg.binNo" readonly="true" cssClass="readonly"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡BIN名称</td>
							<td>
								<s:textfield id="cardBinName" name="cardBinName" readonly="true" cssClass="readonly"/>
							</td>
							<td width="80" height="30" align="right"><s:if test="depositTypeIsTimes==true">售卡次数合计</s:if><s:else>售卡金额合计</s:else></td>
							<td>
								<s:textfield id="amt" name="saleCardReg.amt" readonly="true" cssClass="readonly bigred"/>
							</td>
							<td width="80" height="30" align="right">工本费合计</td>
							<td>
								<s:textfield id="expenses" name="saleCardReg.expenses" value="0.00" readonly="true" cssClass="readonly bigred"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">实收金额合计</td>
							<td>
								<s:textfield id="realAmt" name="saleCardReg.realAmt" readonly="true" cssClass="readonly bigred"/>
							</td>
							<td width="80" height="30" align="right">返利金额合计</td>
							<td >
								<s:textfield id="rebateAmt" name="saleCardReg.rebateAmt" readonly="true" cssClass="readonly "/>
							</td>
							<td width="80" height="30" align="right">手续费</td>
							<td >
								<s:textfield id="id_FeeAmt" name="saleCardReg.feeAmt" cssClass="{required:true, num:true} readonly " readonly="true" />
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield id="remark" name="saleCardReg.remark" />
							</td>
							<td width="80" height="30" align="right">返利计算方式</td>
							<td colspan="3">
								<input type="radio" id="idCalcModeAuto" name="calcMode" value="0" checked/><label for="idCalcModeAuto">自动计算</label>
								<input type="radio" id="idCalcModeManual" name="calcMode" value="1"/><label for="idCalcModeManual">手工指定</label>
							</td>
							<!-- 
							<td><input type="button" value="计算费用" onclick="calRealAmt()" /></td>
							 -->
						</tr>
					</table>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="提交" id="input_btn2"  name="ok" onclick="return checkForm();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/saleCardBatReg/list${actionSubName}.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
						<tr>
							<td><s:hidden id="idRebateId" name="saleCardReg.rebateId" /></td>
							<td><s:hidden name="actionSubName" /><s:hidden name="cardType" id="cardType"/>
							<s:hidden id="depositTypeIsTimes" name="depositTypeIsTimes"/></td>
							<td id="idHiddenElementTd" style="display:none;"></td>	
						</tr>
					</table>
					<s:token name="_TOKEN_SALE_CARD_BAT_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
		
		<script type="text/javascript">
			AC_AX_RunContent( 'classid','clsid:B494F2C1-57A7-49F7-A7AF-0570CA22AF80','id','FTUSBKEYCTRL','style','DISPLAY:none' ); //end AC code
		</script>
		<noscript><OBJECT classid="clsid:B494F2C1-57A7-49F7-A7AF-0570CA22AF80" id="FTUSBKEYCTRL" style="DISPLAY:none"/></OBJECT></noscript>
	</body>
</html>