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
		
		<script>
			$(function(){
				$('#input_btn2').attr('disabled', 'true');
				
				var loginBranchCode = $('#idLoginBranchCode').val();  //登录用户的机构号
				var loginRoleType = $('#idLoginRoleType').val(); // 登录机构类型
				
				// 发卡机构改变时。从发卡机构自己这里领卡时，要选择库存状态
				$('#IdCardIssuer').change(function(){
					var cardIssuer = $('#IdCardIssuer').val(); // 选中的发卡机构号
					var cardBranch = $('#IdCardSectorId').val(); // 领出机构号
					
					$('#idCardSubClass_sel').unbind().removeMulitselector();
					$('#idCardSubClass_sel').val('');
					$('#idCardSubClass').unbind();
					$('#idCardSubClass').val('');
					$('#idStrCardId').val('').attr('readonly', 'true').addClass('readonly');
					
					// 发卡机构登录时
					if(loginRoleType == '20'){
						// 发卡机构为自己时，要选择库存状态为“在库”还是“已领卡”的卡
						if(loginBranchCode == cardIssuer){
							showStockStatus();
						} else {
							hideStockStatus();
							if((!isEmpty(cardIssuer)) && (!isEmpty(cardBranch))){
								Selector.selectCardSubclass('idCardSubClass_sel', 'idCardSubClass', true, '', cardIssuer, '', '', showStrCardId);
							}
						}
					}
				});
				
				// 卡库存状态变更时
				$('#id_CardStockState').change(function(){
					var stockStatus = $('#id_CardStockState').val(); 
					var cardIssuer = $('#IdCardIssuer').val(); // 选中的发卡机构号
					var cardBranch = $('#IdCardSectorId').val(); // 领出机构号
					
					$('#idCardSubClass_sel').unbind().removeMulitselector();
					$('#idCardSubClass_sel').val('');
					$('#idCardSubClass').unbind();
					$('#idCardSubClass').val('');
					$('#idStrCardId').val('').attr('readonly', 'true').addClass('readonly');
					if((!isEmpty(cardIssuer)) && (!isEmpty(cardBranch))){
						if(!isEmpty(stockStatus)){
							Selector.selectCardSubclass('idCardSubClass_sel', 'idCardSubClass', true, '', cardIssuer, '', '', showStrCardId);
							
							$('[id^="id_appOrgDept_Td"]').hide();
							$('#deptNoId').attr('disabled', 'true'); // 部门
							$('[id^="IdAppOrgName_Td"]').hide();
							$('#IdAppCardBranch').attr('disabled', 'true'); //发卡机构
							$('[id^="id_AppProxy_Td"]').hide();
							$('#IdAppCardProxy').attr('disabled', 'true'); // 售卡代理
							
							$('#appOrgTypeId').load(CONTEXT_PATH + '/cardReceive/receive/getAppOrgType.do', {'stockStatus':stockStatus, 'callId':callId});
						}
					}
				});
				
				// 卡号变更时触发
				$('#idStrCardId').change(function(){
					$('#id_strCardId_field').html('请输入起始卡号');
					
					var cardSubClass = $('#idCardSubClass').val(); // 卡类型号
					var branchCode = $('#IdCardSectorId').val(); // 卡的领出机构 
					//var appOrgId = $('#id_AppOrgId').val(); // 卡的领入机构号
					
					var appOrgType = $('#appOrgTypeId').val();
					var appOrgId = "";
					if(appOrgType == '0'){
						// 选择的是部门
						appOrgId = $('#deptNoId').val();
					} else if(appOrgType == '1') {
						// 选择的是发卡机构
						appOrgId = $('#IdAppCardBranch').val();
					} else if(appOrgType == '3') {
						// 选择的是售卡代理
						appOrgId = $('#IdAppCardProxy').val();
					}
					
					var strCardId = $('#idStrCardId').val();
					var stockStatus = $('#id_CardStockState').val(); 
				
					if(!$('#id_CardStockState').attr('disabled') && isEmpty(stockStatus)){
						return false;
					}
					if(isEmpty(cardSubClass)){
						return false;
					}
				
					$.post(CONTEXT_PATH + '/cardReceive/receive/checkUserId.do', {'appReg.cardSubClass':cardSubClass, 
								'appOrgId':appOrgId, 'branchCode':branchCode, 'stockStatus':stockStatus, 'strCardId':strCardId}, 
						function(json){
							//alert("success: "+json.success+ ";cardId:" +json.strCardId);
							if(json.success){
								$('#idStrCardId').removeAttr('readonly').removeClass('readonly');
								$('#idStrCardId').val(json.strCardId);
								$('#id_strCardId_field').html(json.msg).addClass('field_tipinfo').show();
							} else {
								$('#id_strCardId_field').html(json.msg).addClass('error_tipinfo').show();
							}
							clearCardNumAndRelatedFields();
						}, 'json');
				});
				
				$('#idCardNum').change(function(){
					$('#input_btn2').attr('disabled', 'true');
					
					$('#idCardNum_field').html('请输入正整数');

					var strCardId = $('#idStrCardId').val();
					var cardNum = $(this).val(); //领卡数量
					var cardSubClass = $('#idCardSubClass').val(); // 卡类型
					var cardSectorId = $('#IdCardSectorId').val(); // 卡领出机构号
					//var appOrgId = $('#id_AppOrgId').val(); // 卡的领入机构号
					
					var appOrgType = $('#appOrgTypeId').val();
					var appOrgId = "";
					if(appOrgType == '0'){
						// 选择的是部门
						appOrgId = $('#deptNoId').val();
					} else if(appOrgType == '1') {
						// 选择的是发卡机构
						appOrgId = $('#IdAppCardBranch').val();
					} else if(appOrgType == '3') {
						// 选择的是售卡代理
						appOrgId = $('#IdAppCardProxy').val();
					}
					
					var stockStatus = $('#id_CardStockState').val(); 
				
					if(!$('#id_CardStockState').attr('disabled') && isEmpty(stockStatus)){
						showMsg("请选择库存状态");
						return false;
					}
					
					if(isEmpty(cardSectorId)){
						showMsg("请选择领出机构");
						return false;
					}
					if(isEmpty(cardSubClass)){
						showMsg("请选择卡类型");
						return false;
					}
					$.post(CONTEXT_PATH + '/cardReceive/receive/canReaciveCardNum.do', 
						{'appReg.cardSubClass':cardSubClass, 'appReg.cardNum':cardNum, 'stockStatus':stockStatus,
						 'appReg.strNo':strCardId, 'appOrgId':appOrgId, 'appReg.cardSectorId':cardSectorId}, 
						function(json){
							if (parseFloat(json.thisTimeCanReacive) < parseFloat(cardNum)){
								$('#input_btn2').attr('disabled', 'true');
								$('#idEndCardId').val('')
								$('#idCardNum_field').html('本次可领卡数为['+ json.thisTimeCanReacive +']，可领卡总数为['+ json.canReaciveCardNum +']，请更换重试！').addClass('error_tipinfo').show();
								//showMsg('本次可领卡数为['+ json.thisTimeCanReacive +']，可领卡总数为['+ json.canReaciveCardNum +']，请更换重试！');
							} else {
								$('#idEndCardId').val(json.endCardId);
								$('#id_appFaceValue').val(json.appFaceValue);
								$('#id_appSumAmt').val(json.appSumAmt);
								clearCarBinError();
							}
						}, 'json');
				});
				
				$('#appOrgTypeId').change(function(){
					var value = $(this).val();
					
					if(!$('#id_CardStockState').attr('disabled') && isEmpty($('#id_CardStockState').val())){
						alert("请先选择库存状态，再选择领入机构类型");
						return false;
					}
					
					if(value == '0'){
						// 选择的是部门
						$('[id^="id_appOrgDept_Td"]').show();
						$('#deptNoId').removeAttr('disabled').val(''); // 部门
						$('[id^="IdAppOrgName_Td"]').hide();
						$('#IdAppCardBranch').attr('disabled', 'true'); //发卡机构
						$('[id^="id_AppProxy_Td"]').hide();
						$('#IdAppCardProxy').attr('disabled', 'true'); // 售卡代理
					} else if(value == '1') {
						// 选择的是发卡机构
						$('[id^="id_appOrgDept_Td"]').hide();
						$('#deptNoId').attr('disabled', 'true'); // 部门
						$('[id^="IdAppOrgName_Td"]').show();
						$('#IdAppCardBranch').removeAttr('disabled').val(''); //发卡机构
						$('[id^="id_AppProxy_Td"]').hide();
						$('#IdAppCardProxy').attr('disabled', 'true'); // 售卡代理
					} else if(value == '3') {
						// 选择的是售卡代理
						$('[id^="id_appOrgDept_Td"]').hide();
						$('#deptNoId').attr('disabled', 'true'); // 部门
						$('[id^="IdAppOrgName_Td"]').hide();
						$('#IdAppCardBranch').attr('disabled', 'true'); //发卡机构
						$('[id^="id_AppProxy_Td"]').show();
						$('#IdAppCardProxy').removeAttr('disabled').val(''); // 售卡代理
					} else {
						$('[id^="id_appOrgDept_Td"]').hide();
						$('#deptNoId').attr('disabled', 'true'); // 部门
						$('[id^="IdAppOrgName_Td"]').hide();
						$('#IdAppCardBranch').attr('disabled', 'true'); //发卡机构
						$('[id^="id_AppProxy_Td"]').hide();
						$('#IdAppCardProxy').attr('disabled', 'true'); // 售卡代理
					}
				});
				
				$('#input_btn2').click(function(){
					if($('#inputForm').validate().form()){
						$('#inputForm').submit();
						$('#input_btn2').attr('disabled', 'true');
						//window.parent.showWaiter();
						$("#loadingBarDiv").css("display","inline");
						$("#contentDiv").css("display","none");
					}
				});
			});
			function showStockStatus(){
				$('[id^="id_stockStatus_td"]').show();
				$('#id_CardStockState').removeAttr('disabled').val('');
			}
			function hideStockStatus(){
				$('[id^="id_stockStatus_td"]').hide();
				$('#id_CardStockState').attr('disabled', 'true');
			}
			
			function clearCarBinError(){
				$('#idCardNum_field').removeClass('error_tipinfo').html('请输入正整数');
				$('#input_btn2').removeAttr('disabled');
			}

			/** 清除领卡数量及相关字段 */
			function clearCardNumAndRelatedFields(){
				$('#idCardNum').val('');
				$('#idCardNum_field').removeClass('error_tipinfo').html('请输入正整数');
				
				$('#idEndCardId').val('');
				$('#id_appFaceValue').val('');
				$('#id_appSumAmt').val('');
			}
			
			function showStrCardId() {
				clearCardNumAndRelatedFields();
				$('#id_strCardId_field').removeClass('error_tipinfo').html('请输入起始卡号');
				
				var cardSubClass = $('#idCardSubClass').val();//卡类型
				//var branchCode = $('#IdCardIssuer').val(); //发卡机构
				var cardSectorId = $('#IdCardSectorId').val(); //卡的领出机构
				//var appOrgId = $('#id_AppOrgId').val(); // 卡的领入机构号
				
				var appOrgType = $('#appOrgTypeId').val();
				var appOrgId = "";
				if(appOrgType == '0'){
					// 选择的是部门
					appOrgId = $('#deptNoId').val();
				} else if(appOrgType == '1') {
					// 选择的是发卡机构
					appOrgId = $('#IdAppCardBranch').val();
				} else if(appOrgType == '3') {
					// 选择的是售卡代理
					appOrgId = $('#IdAppCardProxy').val();
				}
				//alert('appOrgId:' + appOrgId);
				
				var stockStatus = $('#id_CardStockState').val(); 
				if(!$('#id_CardStockState').attr('disabled') && isEmpty(stockStatus)){
					return false;
				}
				if(isEmpty(cardSubClass) || isEmpty(cardSectorId) || isEmpty(appOrgId)){
					return false;
				}
				
				$.post(CONTEXT_PATH + '/cardReceive/receive/getStrCardId.do', 
					{'appReg.cardSubClass':cardSubClass, 'appReg.cardSectorId':cardSectorId, 
						'appReg.appOrgId':appOrgId, 'stockStatus': stockStatus, 'callId':callId()}, 
					function(json){
						//alert("success: "+json.success+ ";cardId:" +json.strCardId);
						if(json.success){
							$('#idStrCardId').removeAttr('readonly').removeClass('readonly');
							$('#idStrCardId').val(json.strCardId);
							$('#idCardNum_field').removeClass('error_tipinfo').html('请输入正整数');
						} else {
							showMsg(json.errorMsg);
							$('#idStrCardId').val('').attr('readonly', 'true').addClass('readonly');
						}
					}, 'json');
			}
		</script>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div id="loadingBarDiv"	style="display: none; width: 100%; height: 100%">
			<table width=100% height=100%>
				<tr>
					<td align=center >
						<center>
							<img src="${CONTEXT_PATH}/images/ajax_saving.gif" align="middle">
							<div id="processInfoDiv"
								style="font-size: 15px; font-weight: bold">
								正在处理中，请稍候...
							</div>
							<br>
							<br>
							<br>
						</center>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="contentDiv" class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption><span class="caption_title"><f:link href="/cardReceive/receive/showAdd.do">${ACT.name}</f:link></span> | 替别人领卡</caption>
						<!-- 卡的领出机构号 -->
						<s:hidden id="IdCardSectorId" name="appReg.cardSectorId" />
						<s:hidden id="idLoginBranchCode" name="loginBranchCode" />
						<s:hidden id="idLoginRoleType" name="loginRoleType" />
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:select id="IdCardIssuer" name="appReg.cardIssuer" list="cardIssuerList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName"
									cssClass="{required:true}" onmouseover="FixWidth(this)"></s:select>
								<span class="field_tipinfo">请选择机构</span>
							</td>
							<td width="80" height="30" align="right" id="id_stockStatus_td1" style="display: none;">卡库存状态</td>
							<td id="id_stockStatus_td2" style="display: none;">
								<s:select id="id_CardStockState" name="appReg.cardStockStatus" headerKey="" headerValue="--请选择--" list="stockStateList" listKey="value" listValue="name"
									cssClass="{required:true}" disabled="true"></s:select>
								<span class="field_tipinfo">请选择状态</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">领入机构类型</td>
							<td>
								<s:select id="appOrgTypeId" name="appReg.appOrgType" list="appOrgTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"
									cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
						
							<td id="IdAppOrgName_Td1" style="display: none;" width="80" height="30" align="right">领入机构</td>
							<td id="IdAppOrgName_Td2" style="display: none;" >
								<s:select id="IdAppCardBranch" name="appReg.appOrgId" list="cardBranchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName"
									cssClass="{required:true}" disabled="true" onmouseover="FixWidth(this)"></s:select>
								<span class="field_tipinfo">请选择机构</span>
							</td>

							<td id="id_AppProxy_Td1" style="display: none;" width="80" height="30" align="right">领入机构</td>
							<td id="id_AppProxy_Td2" style="display: none;">
								<s:select id="IdAppCardProxy" name="appReg.appOrgId" list="cardSellList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName"
									cssClass="{required:true}" disabled="true" onmouseover="FixWidth(this)"></s:select>
								<span class="field_tipinfo">请选择代理</span>
							</td>

							<td id="id_appOrgDept_Td1" style="display: none;" width="80" height="30" align="right">领入部门</td>
							<td id="id_appOrgDept_Td2" style="display: none;">
								<s:if test="deptList != null">
								<s:select id="deptNoId" name="appReg.appOrgId" list="deptList" headerKey="" headerValue="--请选择--" listKey="deptId" listValue="deptName"
									cssClass="{required:true}" disabled="true" onmouseover="FixWidth(this)"></s:select>
								</s:if>
								<span class="field_tipinfo">请选择部门</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">卡类型</td>
							<td>
								<s:hidden id="idCardSubClass" name="appReg.cardSubClass" />
								<s:textfield id="idCardSubClass_sel" name="cardSubClass_sel" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择卡类型</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">卡起始号</td>
							<td colspan="3">
								<s:textfield id="idStrCardId" name="appReg.strNo" cssClass="{required:true, digit:true} readonly" readonly="true" maxlength="19"/>
								<span id="id_strCardId_field" class="field_tipinfo">请输入起始卡号</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">卡数量</td>
							<td colspan="3">
								<s:textfield id="idCardNum" name="appReg.cardNum" cssClass="{required:true, digit:true, min:1}" maxlength="8"/>
								<span id="idCardNum_field" class="field_tipinfo">请输入正整数</span>
							</td>
						</tr>
						
						<tr id="id_str_cardid_tr2">
							<td width="80" height="30" align="right">结束卡号</td>
							<td>
								<s:textfield id="idEndCardId" cssClass="readonly" readonly="true"/>
							</td>
							<td width="80" height="30" align="right">联系人</td>
							<td>
								<s:textfield name="appReg.linkMan" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入联系人</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">领卡面值</td>
							<td>
								<s:textfield id="id_appFaceValue" cssClass="readonly" readonly="true"/>
								<span class="field_tipinfo"></span>
							</td>
							<td width="80" height="30" align="right">领卡总金额</td>
							<td>
								<s:textfield id="id_appSumAmt" cssClass="readonly" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">联系方式</td>
							<td>
								<s:textfield name="appReg.contact" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入联系方式</span>
							</td>
							<td width="80" height="30" align="right">联系地址</td>
							<td>
								<s:textfield name="appReg.address"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">申购单号</td>
							<td>
								<s:textfield name='appReg.orderNo'/>
								<span class="field_tipinfo"></span>
							</td>
							<td width="80" height="30" align="right">申购单位</td>
							<td>
								<s:textfield name='appReg.orderUnit'/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">身份证号</td>
							<td>
								<s:textfield name="appReg.identityCard"/>
								<span class="field_tipinfo"></span>
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="appReg.memo" />
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="提交" id="input_btn2" name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/cardReceive/receive/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARDREACIVE_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>