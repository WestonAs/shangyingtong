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
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<f:css href="/css/page.css"/>
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<f:js src="/js/cert/AC_ActiveX.js"/>
		<f:js src="/js/cert/AC_RunActiveContent.js"/>
		
		<script type="text/javascript" src="saleCardReg.js?v=20140703"></script>
		
		<s:if test='@gnete.card.service.mgr.BranchBizConfigCache@isNeedReadM1CardId(loginBranchCode)'>
			<%-- 从读卡器读取M1卡的卡号   --%>
			<object id="MWRFATL" codeBase="${CONTEXT_PATH}/activeX/R6-W8.cab#version=1,0,0,1" classid="clsid:2C65E17A-734D-4014-9813-D3E6B1C7EF0C" width="0" height="0" style="display: none"></object>
			<f:js src="/js/mwReader/mwReader.js"/>
		</s:if>
		
		<s:if test='@gnete.card.service.mgr.BranchBizConfigCache@isNeedReadIcCardId(loginBranchCode)'>
			<%-- 从读卡器读取IC卡的卡号 --%>
			<object id="HiddenCOM" classid="clsid:681A7F73-27BA-4362-A899-897D94DAF08A"></object>
			<f:js src="/js/icReader/icReader.js"/>
		</s:if>
		
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
						<caption>${ACT.name}</caption>
						
						<s:if test='@gnete.card.service.mgr.BranchBizConfigCache@isNeedReadM1CardId(loginBranchCode)'>
							<tr>
								<td width="80" height="30" align="right"></td>
								<td colspan="3">
									<input id="readM1CardIdBtn" type="button" value="读取M1卡卡号" 
										onclick='$("#cardId").val(MwReader.readM1CardId(this)); $("#cardId").change();'
										title="请确保已经链接M1卡读卡器"/>
								</td>
							</tr>
						</s:if>
						<s:if test='@gnete.card.service.mgr.BranchBizConfigCache@isNeedReadIcCardId(loginBranchCode)'>
							<tr>
								<td width="80" height="30" align="right"></td>
								<td height="30" colspan="2" align="left">
									IC卡读卡方式：
									<label><input type="radio" id="idForTouch" name="touchType" value="0" />接触式</label>
									<label><input type="radio" id="idForUntouch" name="touchType" value="1" checked="checked"/>非接触式</label>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<input id="readIcCardIdBtn" type="button" value="读取IC卡卡号"  onclick="readIcCardId();" title="请确保已经链接IC卡读卡器"/>
								</td>
							</tr>
						</s:if>
						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
								<s:textfield id="cardId" name="saleCardReg.cardId" cssClass="{required:true}" maxlength="19"/>
								<span class="field_tipinfo">请输入卡号</span>
							</td>
							<td width="80" height="30" align="right">
								<s:if test="depositTypeIsTimes==true" >充值次数</s:if><s:else>面值</s:else>
							</td>
							<td>
								<s:textfield id="amt" name="saleCardReg.amt" cssClass="{required:true, num:true} readonly" readonly="true" onchange="calRealAmt(false);"/>
								<span class="field_tipinfo">请输入<s:if test="depositTypeIsTimes==true" >充值次数</s:if><s:else>面值</s:else></span>
							</td>
						</tr>
						<tr>							
							<td width="80" height="30" align="right">购卡客户</td>
							<td>
								<s:hidden id="idCardCustomerId" name="saleCardReg.cardCustomerId" />
								<s:textfield id="idCardCustomerId_sel" name="cardCustomerName" cssClass="{required:true}" readonly="true"/>
								<span class="field_tipinfo">请选择购卡客户</span>
							</td>
							<td width="80" height="30" align="right">工本费</td>
							<td>
								<s:textfield id="expenses" name="saleCardReg.expenses" cssClass="{required:true, num:true}" onchange="calRealAmt(false);"/>
								<span class="field_tipinfo">请输入工本费</span>
							</td>
						</tr>
						<tr id="idRebateTypeTr" style="display: none;">							
							<td width="80" height="30" align="right">返利方式</td>
							<td>
								<s:select id="rebateType" name="saleCardReg.rebateType" list="rebateTypeList" 
									headerKey="" headerValue="--请选择--"  listKey="value" listValue="name" 
									cssClass="{required:true}" disabled="true" onchange="calRealAmt(false);">
								</s:select>
								<span class="field_tipinfo">请选择返利方式</span>
							</td>
							<td width="80" height="30" align="right">手续费比例</td>
							<td>
								<s:textfield id="id_FeeRate" name="saleCardReg.feeRate" maxlength="20" cssClass="{num:true, required:true, decimal:'5,3'}" maxLength="9" value="0" onchange="calRealAmt(false);"/>
								<span class="field_tipinfo">%最多3位小数</span>
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
						
						<s:if test='saleCardReg.presell=="1" && actionSubName=="PreDeposit" && cardRoleLogined'> <%--  预售储值卡并且是发卡机构用户 --%>
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
									<span class="field_tipinfo">最多两位小数</span>
								</td>
							</tr>
						</s:if>
						
					</table>
					<div class="tablebox" id="idRebateRulePage"></div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0" id="idConfirmTbl" style="display: none;">
						<tr>
							<td width="90" height="30" align="right">充值类型</td>
							<td>
								<s:hidden id="depositTypeIsTimes" name="depositTypeIsTimes"></s:hidden>
								<s:if test="depositTypeIsTimes==true" >
									<s:textfield name="depositType" value="按次充值" readonly="true" cssClass="readonly"/>
								</s:if>
								<s:else>
									<s:textfield name="depositType" value="按金额充值" readonly="true" cssClass="readonly"/>
								</s:else>
							</td>
							<td width="90" height="30" align="right">售卡方式</td>
							<td>
								<s:hidden id="id_presell" name="saleCardReg.presell"></s:hidden>
								<s:textfield id="presell" name="saleCardReg.presellName" readonly="true" cssClass="readonly"/>								
							</td>
							<td width="90" height="30" align="right">卡BIN</td>
							<td>
								<s:textfield id="cardBin" name="saleCardReg.binNo" readonly="true" cssClass="readonly"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡BIN名称</td>
							<td>
								<s:textfield id="cardBinName" name="cardBinName" readonly="true" cssClass="readonly"/>
							</td>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:hidden id="cardBranch" name="cardBranch"/>
								<s:textfield id="cardBranchName" name="cardBranchName" readonly="true" cssClass="readonly"/>
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield id="remark" name="saleCardReg.remark" />
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">实收金额</td>
							<td>
								<s:textfield id="realAmt" name="saleCardReg.realAmt" cssClass="{required:true, num:true} readonly bigred" readonly="true" />
							</td>
							<td width="80" height="30" align="right">
								返利金额
								<span id="periodLabel" class="no">(分期)</span>
							</td>
							<td>
								<s:textfield id="rebateAmt" name="saleCardReg.rebateAmt" cssClass="{required:true, num:true} readonly " readonly="true" />
							</td>
							<td width="80" height="30" align="right">手续费</td>
							<td>
								<s:textfield id="id_FeeAmt" name="saleCardReg.feeAmt" cssClass="{required:true, num:true} readonly " readonly="true" />
							</td>
							<!-- 
							<td colspan="2"><input style="margin-left:30px;" type="button" value="计算费用" onclick="calRealAmt(true)" /></td>
							 -->
						</tr>
						<tr id="isHasCtrl" >
							<td id="test" colspan="4">
								<label>
									<input type="checkbox"  name="isHasCustName" id="inputForm_isHasCustName" value="0"  />
									<b>录入持卡人信息</b>
								</label>
							</td>
						</tr>
					</table>
					<table id="idCardInfoTbl" style="display:none;" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td colspan="10">
								<div id="cardHolderMsgDiv" class="msg" style="display: none; float: left">
									<span id="cardHolderMsgContent" style="float: left"></span>
									<a id="cardHolderMsgClose" href="javascript:hideCardHolderMsg();" style="float: right;color: red">关闭 X</a>
								</div>
							</td>
						</tr>
						<tr>
							<td></td>
							<td colspan="10">
								<object id="CVR_IDCard" classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7" width="0" height="0" style="display: none"></object>
								<input type="button" value="加载身份证信息" onclick="loadIdCardInfo()" title="从读卡器读取二代身份证信息"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">持卡人姓名</td>
							<td>
								<s:textfield id="custName" name="cardExtraInfo.custName" cssClass="{required:true}" />
								<span class="field_tipinfo">请输入持卡人姓名</span>
							</td>							
							<td width="80" height="30" align="right">手机</td>
							<td>
								<s:textfield id="id_ExtraInfo_MobileNo" name="cardExtraInfo.mobileNo" 
									onblur="ajaxFindCardExtraInfos('mobileNo')" cssClass="{digit:true}"/>
								<span class="field_tipinfo">请输入手机号</span>
							</td>	
						</tr>
						<tr>
							<td width="80" height="30" align="right">证件类型</td>
							<td>
								<s:select id="id_ExtraInfo_CredType" name="cardExtraInfo.credType" list="certTypeList" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择证件类型</span>
							</td>
							<td width="80" height="30" align="right">证件号码</td>
							<td>
								<s:textfield id="id_ExtraInfo_CredNo" name="cardExtraInfo.credNo" 
									onblur="ajaxFindCardExtraInfos('credNo')" cssClass="{required:true, digitOrLetter:true}"/>
								<span class="field_tipinfo">请输入证件号</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">证件有效期</td>
							<td>
								<s:textfield name="cardExtraInfo.credNoExpiryDate" onfocus="WdatePicker({dateFmt:'yyyyMMdd',autoPickDate:true,isShowOthers:false})"/>
								<span class="field_tipinfo">请选择日期，格式：yyyyMMdd</span>
							</td>
							<td width="80" height="30" align="right">职业</td>
							<td>
								<s:textfield name="cardExtraInfo.career" cssClass="{maxlength:16}"/>
								<span class="field_tipinfo"></span>
							</td>
						</tr>						
						<tr>
							<td width="80" height="30" align="right">国籍</td>
							<td>
								<s:textfield name="cardExtraInfo.nationality" cssClass="{maxlength:16}"/>
								<span class="field_tipinfo"></span>
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">联系电话</td>
							<td>
								<s:textfield id="id_ExtraInfo_TelNo" name="cardExtraInfo.telNo" value="" cssClass="{digit:true}"/>
								<span class="field_tipinfo">请输入联系电话</span>
							</td>
							<td width="80" height="30" align="right">地址</td>
							<td>
								<s:textfield id="id_ExtraInfo_Address" name="cardExtraInfo.address"/>
								<span class="field_tipinfo">请输入地址</span>
							</td>							
						</tr>
						<tr>
							<td width="80" height="30" align="right">Email</td>
							<td>
								<s:textfield id="id_ExtraInfo_Email" name="cardExtraInfo.email" cssClass="{email:true}"/>
								<span class="field_tipinfo">请输入Email</span>
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield id="extraInfo_Remark" name="cardExtraInfo.remark" />
								<span class="field_tipinfo">请输入备注</span>
							</td>
						</tr>
					</table>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td><s:hidden id="idRebateId" name="saleCardReg.rebateId" /></td>
							<td><s:hidden name="actionSubName" /></td>
							<td><s:hidden name="cardType" id="cardType"/></td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<!-- 
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								 -->
								<input type="button" value="提交" id="input_btn2"  name="ok"  onclick="submitForm();"/>
								<input type="button" value="清除" id="input_btn_clear" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" id="input_btn_rtn" name="escape" onclick="gotoUrl('/saleCardReg/list${actionSubName}.do?goBack=goBack')" class="ml30" />
							</td>
							<td></td><td></td>
						</tr>
					</table>
					<s:token name="_TOKEN_SALE_CARD_ADD"/>
				</s:form>
				 <script type="text/javascript">
					AC_AX_RunContent( 'classid','clsid:B494F2C1-57A7-49F7-A7AF-0570CA22AF80','id','FTUSBKEYCTRL','style','DISPLAY:none' ); //end AC code
				</script>
				<noscript>
					<OBJECT classid="clsid:B494F2C1-57A7-49F7-A7AF-0570CA22AF80" id="FTUSBKEYCTRL" style="DISPLAY:none"/></OBJECT>
				</noscript>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>