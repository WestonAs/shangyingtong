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
		<f:js src="/js/validate.js"/>
		
		<f:js src="/js/biz/depositBatReg/add.js"/>
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
					<div>
					<s:hidden id="id_RandomSessionId" name="depositReg.randomSessionId" />
					<s:hidden id="id_Signature" name="depositReg.signature" />
					<s:hidden id="id_signatureReg" name="signatureReg" />
					<s:hidden id="id_serialNo" name="serialNo"/>
					
					<!-- 旧的批量充值的做法
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<caption>${ACT.name}</caption>
						<s:if test="cardType == 06">
						<tr id="idTimesTr">
							<td height="30" colspan="4" style="padding-left: 150px;">请选择次卡充值方式：
								<input type="radio" id="idForTimes" name="timesDepositType" value="0" checked="checked"/><label for="idForTimes">按次数充值</label>
								<input type="radio" id="idForAmt" name="timesDepositType" value="1"/><label for="idForAmt">按金额充值</label>
							</td>
						</tr>
						</s:if>
						<tr>
						   <td align="center" nowrap class="titlebg" style="display: none;">分段号</td>
						   <td align="center" nowrap class="titlebg">起始卡号</td>
						   <td align="center" nowrap class="titlebg">卡连续数</td>
						   <td align="center" nowrap class="titlebg" id="idAmtLable1"><s:if test="depositTypeIsTimes==true">单张充值次数</s:if><s:else>单张充值金额</s:else></td>
						</tr>
						<tr id="idDetail_1">
							<td align="center" style="display: none;">1</td>
							<td align="center"><s:textfield id="cardId" name="cardId" maxlength="19"/></td>
							<td align="center"><s:textfield name="cardCount" onchange="calRealAmt()"/></td>
							<td align="center"><s:textfield name="amt" onchange="calRealAmt()"/></td>
						</tr>
						<tr id="idLink" style="line-height: 30px;display: none;">
							<td align="left" colspan="4" style="padding-left: 20px;">
								<span class="redlink">
									<a class="ml30" href="javascript:void(0);" onclick="javascript:addItem();">增加一项</a>
									<a class="ml30" href="javascript:void(0);" onclick="javascript:deleteItem();">删除一项</a>
								</span>
							</td>
						</tr>
					</table>
					-->
					 
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<!-- 新的充值的想法，以后改进 -->
						<s:if test="cardType == 06">
							<tr id="idTimesTr">
								<td height="30" colspan="4" style="padding-left: 150px;">请选择次卡充值方式：
									<input type="radio" id="idForTimes" name="timesDepositType" value="0" checked="checked"/><label for="idForTimes">次卡子账户充值</label>
									<input type="radio" id="idForAmt" name="timesDepositType" value="1"/><label for="idForAmt">充值资金子账户充值</label>
								</td>
							</tr>
						</s:if>
						<s:if test='@gnete.card.service.mgr.BranchBizConfigCache@isNeedReadM1CardId(loginBranchCode)'>
							<tr>
								<td width="80" height="30" align="right"></td>
								<td colspan="3">
									<input id="readM1CardIdBtn" type="button" value="读取M1卡卡号" 
										onclick='$("#id_strCardId").val(MwReader.readM1CardId(this)); $("#id_strCardId").change();'
										title="请确保已经链接M1卡读卡器"/>
								</td>
							</tr>
						</s:if>
						<tr>
							<td width="80" height="30" align="right">起始卡号</td>
							<td>
								<s:textfield id="id_strCardId" name="depositReg.strCardId" maxLength="19" cssClass="{required:true, num:true}"/>
								<span class="field_tipinfo">请输入起始卡号</span>
							</td>
							<td width="80" height="30" align="right">卡连续数</td>
							<td>
								<s:textfield id="id_CardCount" name="cardCount" cssClass="{required:true, digit:true}"/>
								<span class="field_tipinfo">请输入整数</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">结束卡号</td>
							<td>
								<s:textfield id="id_endCardId" name="depositReg.endCardId" cssClass="{required:true, digit:true} readonly" maxlength="19" readonly="true" />
								<span class="field_tipinfo">请输入结束卡号</span>
							</td>
							<td width="80" height="30" align="right" id="idAmtLable1">
								<s:if test="depositTypeIsTimes==true">单张充值次数</s:if><s:else>单张充值金额</s:else>
							</td>
							<td>
								<s:textfield id="id_Amt" name="amt" cssClass="{required:true, decimal:'12,2'}"/>
								<span class="field_tipinfo">请输入金额</span>
							</td>
						</tr>
						 
						<tr id="idRebateTypeTr" style="display: none;">							
							<td width="80" height="30" align="right" id="idRebateTypeTd1">返利方式</td>
							<td id="idRebateTypeTd2">
								<s:select id="rebateType" name="depositReg.rebateType" list="rebateTypeList" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name" cssClass="{required:true}" disabled="true" onchange="calRealAmt();">
								</s:select>
								<span class="field_tipinfo">请选择返利方式</span>
							</td>
							
							<td width="80" height="30" align="right">手续费比例</td>
							<td>
								<s:textfield id="id_FeeRate" name="depositReg.feeRate" maxlength="20" cssClass="{num:true, required:true, decimal:'5,3'}" maxLength="9" value="0" onchange="calRealAmt();"/>
								<span class="field_tipinfo">%最多3位小数</span>
							</td>
						</tr>
						<tr id="idRebateCardTr" style="display: none;">
							<td width="80" height="30" align="right" id="idRebateCardTd1" style="display: none;">返利卡卡号</td>
							<td id="idRebateCardTd2" style="display: none;" colspan="3">
								<s:textfield id="idRebateCardId" name="depositReg.rebateCard" cssClass="{required:true, digit:true}" disabled="true" maxlength="19"/>
								<span class="field_tipinfo">请输入返利卡卡号</span>
							</td>
							<td width="80" height="30" align="right" id="idRebateCountTd1" style="display: none;">返利卡张数</td>
							<td id="idRebateCountTd12" style="display: none;" colspan="3">
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
					
					<div class="tablebox" id="idRebateRulePage"></div>
					<table id="idDepositTbl" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0" style="display: none;">
						<tr>
							<td width="70" height="30" align="right">卡BIN</td>
							<td>
								<s:textfield id="cardBin" name="depositReg.binNo" readonly="true" cssClass="readonly"/>
							</td>
							<td width="70" height="30" align="right">卡种</td>
							<td>
								<s:textfield id="cardClassName" name="cardClassName" readonly="true" cssClass="readonly"/>
							</td>
							<td width="70" height="30" align="right">发卡机构</td>
							<td>
								<s:textfield id="cardBranchName" name="cardBranchName" readonly="true" cssClass="readonly"/>
							</td>
						</tr>
						<tr>
							<td width="70" height="30" align="right">卡子类型</td><td><s:textfield id="cardSubClassName" readonly="true" cssClass="readonly"/></td>
							<td width="70" height="30" align="right">购卡客户</td><td><s:textfield id="cardCustomerName" readonly="true" cssClass="readonly"/></td>							
							<td width="70" height="30" align="right">团购卡号</td><td><s:textfield id="groupCardID" readonly="true" cssClass="readonly"/></td>
						</tr>
						<tr>
							<td width="70" height="30" align="right">实收合计:</td>
							<td>
								<s:textfield id="realAmt" name="depositReg.realAmt" readonly="true" cssClass="{required:true} readonly bigred"/>
							</td>
							<td width="70" height="30" align="right">返利合计:</td>
							<td>
								<s:textfield id="rebateAmt" name="depositReg.rebateAmt" readonly="true" cssClass="{required:true} readonly"/>
							</td>
							<td width="70" height="30" align="right">充值合计:</td>
							<td>
								<s:textfield id="amt" name="depositReg.amt" readonly="true" cssClass="{required:true} readonly"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">手续费</td>
							<td>
								<s:textfield id="id_feeAmt" name="depositReg.feeAmt" cssClass="{required:true, num:true} readonly" readonly="true"/>
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield id="remark" name="depositReg.remark" />
							</td>
							<td width="80" >返利计算方式</td>
							<td>
								<input type="radio" id="idCalcModeAuto" name="calcMode" value="0" checked="checked"/>
								<label for="idCalcModeAuto">自动计算</label>
								<input type="radio" id="idCalcModeManual" name="calcMode" value="1"/>
								<label for="idCalcModeManual">手工指定</label>
								<!-- 
								<input type="button" value="计算费用" onclick="calRealAmt()" />
								 -->
							</td>
						</tr>
					</table>
						
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="提交" id="input_btn2"  name="ok"  onclick="return submitDepositForm();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/depositBatReg/list${actionSubName}.do?goBack=goBack')" class="ml30" />
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td id="idHiddenElementTd" style="display:none;"></td>	
						</tr>
						</table>

						<s:hidden id="cardClass" name="depositReg.cardClass" />
						<s:hidden id="cardCustomerId" name="depositReg.cardCustomerId" />
						<s:hidden id="rebateId" name="depositReg.rebateId" />
						<s:hidden name="depositReg.calType" />
						<s:hidden name="actionSubName" />
						<s:hidden id="id_cardType" name="cardType" />
						<s:hidden id="depositTypeIsTimes" name="depositTypeIsTimes"/>
						<s:hidden id="fromPage" name="fromPage"/>
						<s:hidden id="preDeposit" name="preDeposit"/>
					<s:token name="_TOKEN_DEPOSIT_BAT_ADD"/>
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