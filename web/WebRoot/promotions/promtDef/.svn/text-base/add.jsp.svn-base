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
		<f:js src="/js/validate.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			$(function(){
				timeFlagClick();
				
				var cardIssuerNo = $('#Id_cardIssuerNo').val();
				var merchantNo = $('#Id_merchantNo').val();
				
				// 卡BIN选择
				Selector.selectCardBin('cardBinScope_sel', 'idCardBinScope', false, cardIssuerNo, merchantNo);
				// 发卡机构的特约商户
				Selector.selectMerch('idsendPinstSel', 'idsendPinstId', true, cardIssuerNo);
				// 发卡机构的特约商户
				Selector.selectMerch('idjoinPinstSel', 'idjoinPinstId', true, cardIssuerNo);
				
				var roleType = '${loginRoleType}';
				// 登录用户角色为商户时
				if(roleType == '40'){
					$('[id^="pinstType_td"]').hide();
					//$('#idMerNoTd_1').hide();
					//$('#idMerNoTd_2').hide();
					$(':td[id=^"idMerNoTd_"]').hide();
					$('#pinstType_id').attr('disabled', 'true');
				} else {
					$('[id^="pinstType_td"]').show();
					//$('#idMerNoTd_2').show();
					$(':td[id=^"idMerNoTd_"]').show();
					Selector.selectMerch('idMerNo_Sel_1', 'idMerNo_1', true, cardIssuerNo);
					$('#pinstType_id').removeAttr('disabled');
				}
				
				$('#startIssType_id').change(function(){
					var value = $(this).val();
					if(value == '0') {//发卡机构
						$('#id_joinPinstType_tr1').show();
						$('#joinPinstType_id_1').removeAttr('disabled');
						$('#idjoinPinstId').removeAttr('disabled');
						$('#idjoinPinstSel').removeAttr('disabled');
						$('#id_joinMerchGroupId_1').removeAttr('disabled');
						
						$('#id_joinPinstType_tr2').hide();
						$('#joinPinstType_id_2').attr('disabled', 'true');
						$('#id_joinPinstSel_2').attr('disabled', 'true');
						$('#id_joinMerchGroupId_2').attr('disabled', 'true');
					}else if(value=='2'){//商圈
						$('#id_joinPinstType_tr1').hide();
						$('#joinPinstType_id_1').attr('disabled', 'true');
						$('#idjoinPinstId').attr('disabled', 'true');
						$('#idjoinPinstSel').attr('disabled', 'true');
						$('#id_joinMerchGroupId_1').attr('disabled', 'true');
						
						$('#id_joinPinstType_tr2').show();
						$('#joinPinstType_id_2').removeAttr('disabled');
						$('#id_joinPinstSel_2').removeAttr('disabled');
						$('#id_joinMerchGroupId_2').removeAttr('disabled');
					} 
				});

				$('#joinPinstType_id_1').change(function(){
					var value = $(this).val();
					if(value == '0') {//发卡机构
						$('#joinPinstId_td1').hide();
						$('#joinPinstId_td2').hide();
						$('#idjoinPinstId').attr('disabled', 'true');
						$('#idjoinPinstSel').attr('disabled', 'true');
						
						$('#joinMerchGroup_td1').hide();
						$('#joinMerchGroup_td2').hide();
						$('#id_joinMerchGroupId_1').attr('disabled', 'true');
					}if(value == '1') {//商户
						$('#joinPinstId_td1').show();
						$('#joinPinstId_td2').show();
						$('#idjoinPinstId').removeAttr('disabled');
						$('#idjoinPinstSel').removeAttr('disabled');
						
						$('#joinMerchGroup_td1').hide();
						$('#joinMerchGroup_td2').hide();
						$('#id_joinMerchGroupId_1').attr('disabled', 'true');
					}
					else if(value=='2'){//商圈
						$('#joinPinstId_td1').hide();
						$('#joinPinstId_td2').hide();
						$('#idjoinPinstId').attr('disabled', 'true');
						$('#idjoinPinstSel').attr('disabled', 'true');
						
						$('#joinMerchGroup_td1').show();
						$('#joinMerchGroup_td2').show();
						$('#id_joinMerchGroupId_1').removeAttr('disabled');
					}
				});
				
				$('#sendPinstType_id').change(function(){
					var value = $(this).val();
					if(value == '0') {//发卡机构
						$('#sendPinstId_td1').hide();
						$('#sendPinstId_td2').hide();
						$('#idsendPinstId').attr('disabled', 'true');
						$('#idsendPinstSel').attr('disabled', 'true');
						
						$('#sendMerchGroup_td1').hide();
						$('#sendMerchGroup_td2').hide();
						$('#id_sendMerchGroupId').attr('disabled', 'true');
					}if(value == '1') {//商户
						$('#sendPinstId_td1').show();
						$('#sendPinstId_td2').show();
						$('#idsendPinstId').removeAttr('disabled');
						$('#idsendPinstSel').removeAttr('disabled');
						
						$('#sendMerchGroup_td1').hide();
						$('#sendMerchGroup_td2').hide();
						$('#id_sendMerchGroupId').attr('disabled', 'true');
					}
					else if(value=='2'){//商圈
						$('#sendPinstId_td1').hide();
						$('#sendPinstId_td2').hide();
						$('#idsendPinstId').attr('disabled', 'true');
						$('#idsendPinstSel').attr('disabled', 'true');
						
						$('#sendMerchGroup_td1').show();
						$('#sendMerchGroup_td2').show();
						$('#id_sendMerchGroupId').removeAttr('disabled');
					}
				});
				
				$(':radio[name="addScope"]').click(function(){
					var value = $(this).val();
					if (value == 0) {
						$('[id^="idAddScope_"]').hide();
						$('#scopeTypeId').attr('disabled', 'true');
					} else {
						$('[id^="idAddScope_"]').show();
						Selector.selectCardBin('idCardBinScope_Sel_1', 'idCardBinScope_1', false, cardIssuerNo, merchantNo);
						Selector.selectCardSubclass('idCardSubclass_Sel_1', 'idCardSubclass_1', true, merchantNo, cardIssuerNo);
						$('#scopeTypeId').removeAttr('disabled');
					}
				});
				setTimeout(function(){
					$('#idDetail_1').find(':text').addClass('u_half');
				}, 100);
			});
				
			function addItem(){
				var cardIssuerNo = $('#Id_cardIssuerNo').val();
				var merchantNo = $('#Id_merchantNo').val();
			
				var count = $('tr[id^="idDetail_"]').size();
				var merShow = '';
				var role = '${loginRoleType}';
				if(role != '40'){
					merShow = '<td align="center" class="u_half" id="idMerNoTd_'+(count+1) +'">'
										+ '<input type="hidden" id="idMerNo_'+(count+1) +'" name="merNo"/>'
										+ '<input type="text" id="idMerNo_Sel_'+(count+1) +'" name="merNo_Sel" />'
								+ '</td>';
				} 
				var content = '<tr id="idDetail_'+ (count+1) +'">' 
								+ '<td align="center" class="u_half">'+ (count+1) +'</td>' 
								+ merShow
								+ '<td align="center" class="u_half">'
										+ '<input type="hidden" id="idCardBinScope_'+(count+1) +'" name="cardBinScope" />' 
										+ '<input type="text" id="idCardBinScope_Sel_'+(count+1) +'" name="cardBinScope_Sel" />'
								+ '</td>' 
								+ '<td align="center" class="u_half">'
										+ '<input type="hidden" id="idCardSubclass_'+(count+1) +'" name="cardSubclass" />' 
										+ '<input type="text" id="idCardSubclass_Sel_'+(count+1) +'" name="cardSubclass_Sel" />' 
								+ '</td>' 
								+ '<td align="center" class="u_half"><input type="text" name="ptLlimit" value=""/></td>' 
								+ '<td align="center" class="u_half"><input type="text" name="ptUlimit" value=""/></td>' 
								+ '<td align="center" class="u_half"><input type="text" name="membLevel" value=""/></td></tr>';
				$('#idLink').before(content);
				// 卡BIN选择
				Selector.selectCardBin('idCardBinScope_Sel_'+(count+1), 'idCardBinScope_'+(count+1), false, cardIssuerNo, merchantNo);
				// 商户选择
				Selector.selectMerch('idMerNo_Sel_'+(count+1), 'idMerNo_'+(count+1), true, cardIssuerNo);
				// 卡子类型选择
				Selector.selectCardSubclass('idCardSubclass_Sel_'+(count+1), 'idCardSubclass_'+(count+1), true, merchantNo, cardIssuerNo);
			
				// 设置样式
				SysStyle.setFormGridStyle();
				$('#idDetail_'+ (count+1)).find(':text').addClass('u_half');
			}
			function deleteItem(){
				var count = $('tr[id^="idDetail_"]').size();
				if (count == 1) {
					showMsg('必须设置一项');
					return;
				}
				$('tr[id^="idDetail_"]:last').remove();
			}
			
			function check(){
				if($("[name='promtDef.timeFlag']:checked").val()=="1"){//选择包含时间段
					if( !validator.isHHmm($("#effTime").val()) || !validator.isHHmm($("#expirTime").val()) ){
						showMsg("请按格式输入正确的时间段");
						return false;
					}
				}
				if($('#idAddScope_3').css('display') == 'none'){
					return true;
				}
				var count = $('tr[id^="idDetail_"]').size();
				for(var i=0; i<count; i++){
					var comm = $('tr[id^="idDetail_"]').eq(i).children();
					var mer = comm.eq(1).children().val();
					var soc = comm.eq(2).children().val();
					var sub = comm.eq(3).children().val();
					var ptL = comm.eq(4).children().val();
					var ptU = comm.eq(5).children().val();
					var lev = comm.eq(6).children().val();
					if(validator.isEmpty(mer) && validator.isEmpty(soc)
						&&validator.isEmpty(sub) && validator.isEmpty(ptL)
						&&validator.isEmpty(ptU) && validator.isEmpty(lev)){
						showMsg("促销活动范围不能全部为空！");
						return false;
					}
					if((validator.isEmpty(ptL)&&(!validator.isEmpty(ptU)))
						||((!validator.isEmpty(ptL))&&validator.isEmpty(ptU))){
						showMsg("积分上限和积分下限必须同为空或同时不为空。");
						return false;
					}
					if(!validator.isEmpty(ptL)&&(!validator.isEmpty(ptU))){
						if(!validator.isDecimal(ptL) || !validator.isDecimal(ptU)){
							showMsg("积分上限和积分下限必须为数值！");
							return false;
						}
						if(ptL >= ptU){
							showMsg("积分下限必须大于积分上限。");
							return false;
						}
					}
					if(!validator.isDigit(lev)){
						showMsg("“会员级别”请输入数字。");
						return false;
					}
				}
				return true;
			}
			
			function submitForm(){
				if(!check()){
					return false;
				}
				$('#inputForm').submit();
			}
			
			function getEffDateId() {
				WdatePicker({minDate:'%y-%M-%d', maxDate:'#F{$dp.$D(\'expirDateId\')}'});
			}
			function getExpirDateId(){
				WdatePicker({minDate:'#F{$dp.$D(\'effDateId\') || \'%y-%M-%d\'}'});
			}
			
			/** 根据是否包含时间段决定是否显示时间段输入框 */
			function timeFlagClick(){
				if($("[name='promtDef.timeFlag']:checked").val()=="1"){//选择包含时间段
					$("#effTime").removeAttr("disabled");
					$("#expirTime").removeAttr("disabled");
					$("#timePeriodDiv").show();
					$("#timePeriodTitle").show();
				} else {
					$("#effTime").attr("disabled","disabled");
					$("#expirTime").attr("disabled","disabled");
					$("#timePeriodDiv").hide();
					$("#timePeriodTitle").hide();
				}
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="nextStep.do" id="inputForm" cssClass="validate">
					<s:hidden id="Id_cardIssuerNo" name="cardIssuerNo" />
					<s:hidden id="Id_merchantNo" name="merchantNo" />
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">促销活动名</td>
							<td>
								<s:textfield name="promtDef.promtName" cssClass="{required:true}" maxlength="20"/>
								<span class="field_tipinfo">请输入活动名</span>
							</td>
							
							<td width="80" height="30" align="right"></td>
							<td>
							</td>
						</tr>
						<c:if test="${showCard}">
						<tr>
							<td id="startIssType_td1" width="80" height="30" align="right" >发起机构类型</td>
							<td id="startIssType_td2" >
								<s:select id="startIssType_id" name="promtDef.issType" headerKey="" headerValue="--请选择--" list="issTypeList" listKey="value" listValue="name"
									cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择机构类型</span>
							</td>
							<td  id="startMerchGroup_td1"  width="80" height="30" align="right" style="display:none">商圈</td>
							<td  id="startMerchGroup_td2"  style="display:none">
								<s:select id="id_startMerchGroupId"  disabled="true" name="promtDef.issId" list="merchGroupList" headerKey="" headerValue="--请选择--" listKey="groupId" listValue="groupName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择商圈</span>
							</td>
						</tr>
						
						<tr  id="id_joinPinstType_tr1"'>
							<td id="joinPinstType_td1" width="80" height="30" align="right" >参与机构类型</td>
							<td id="joinPinstType_td2" >
								<s:select id="joinPinstType_id_1" name="promtDef.pinstType" headerKey="" headerValue="--请选择--" list="pinstTypeList" listKey="value" listValue="name"
									cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择机构类型</span>
							</td>
							
							<td id="joinPinstId_td1" width="80" height="30" align="right" style="display: none">商户</td>
							<td id="joinPinstId_td2"   style="display: none" >
								<s:hidden id="idjoinPinstId" name="promtDef.pinstId" disabled="true" />
								<s:textfield id="idjoinPinstSel" name="joinPinstId_sel" cssClass="{required:true}" disabled="true" />
								<span class="field_tipinfo">请选择商户</span>
							</td>
							<td  id="joinMerchGroup_td1"  width="80" height="30" align="right" style="display:none">商圈</td>
							<td  id="joinMerchGroup_td2"   style="display: none" >
								<s:select id="id_joinMerchGroupId_1"  disabled="true" name="promtDef.pinstId" list="merchGroupList" headerKey="" headerValue="--请选择--" listKey="groupId" listValue="groupName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择商圈</span>
							</td>
						</tr>
						
						<tr id="id_joinPinstType_tr2"' style="display: none">
							<td id="joinPinstId_td3" width="80" height="30" align="right" >参与机构类型</td>
							<td id="joinPinstId_td4" >
								<s:hidden id="joinPinstType_id_2" name="promtDef.pinstType" disabled="true"  value="2"/>
								<s:textfield id="id_joinPinstSel_2" name="joinPinstId_sel_2" cssClass="{required:true} readonly" readonly="true" value="商圈" disabled="true" />
								<span class="field_tipinfo">请选择机构类型</span>
							</td>
							
							<td  id="joinMerchGroup_td3"  width="80" height="30" align="right" >参与机构</td>
							<td  id="joinMerchGroup_td4"  >
								<s:select id="id_joinMerchGroupId_2"  disabled="true" name="promtDef.pinstId" list="merchGroupList" headerKey="" headerValue="--请选择--" listKey="groupId" listValue="groupName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择商圈</span>
							</td>
						</tr>
						</c:if>
						
						<tr>
							<td id="sendPinstType_td1" width="80" height="30" align="right" >赠送机构类型</td>
							<td id="sendPinstType_td2" >
								<s:select id="sendPinstType_id" name="promtDef.reserved4" headerKey="" headerValue="--请选择--" list="sendTypeList" listKey="value" listValue="name"
									cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择机构类型</span>
							</td>
							
							<td id="sendPinstId_td1" width="80" height="30" align="right" style="display: none">商户</td>
							<td id="sendPinstId_td2" style="display: none">
								<s:hidden id="idsendPinstId" name="promtDef.reserved5" disabled="true" />
								<s:textfield id="idsendPinstSel" name="sendPinstId_sel" cssClass="{required:true}" disabled="true" />
								<span class="field_tipinfo">请选择商户</span>
							</td>
							<td  id="sendMerchGroup_td1"  width="80" height="30" align="right" style="display:none">商圈</td>
							<td  id="sendMerchGroup_td2"  style="display:none">
								<s:select id="id_sendMerchGroupId"  disabled="true" name="promtDef.reserved5" list="merchGroupList" headerKey="" headerValue="--请选择--" listKey="groupId" listValue="groupName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择商圈</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">交易类型</td>
							<td>
								<s:select name="promtDef.transType" list="transTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"
									cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择交易类型</span>
							</td>
							
							<td width="80" height="30" align="right">卡BIN范围</td>
							<td>
								<s:hidden id="idCardBinScope" name="promtDef.cardBinScope" />
								<s:textfield id="cardBinScope_sel" name="cardBinScope_sel" />
								<span class="field_tipinfo">请选择卡BIN</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">生效日期</td>
							<td>
								<s:textfield name="promtDef.effDate" id="effDateId" onclick="getEffDateId();"
									cssClass="{required:true}" />
								<span class="field_tipinfo">选择生效日期</span>
							</td>
							
							<td width="80" height="30" align="right">失效日期</td>
							<td>
								<s:textfield name="promtDef.expirDate" id="expirDateId" onclick="getExpirDateId();" 
									cssClass="{required:true}"/>
								<span class="field_tipinfo">选择失效日期</span>
							</td>
						</tr>
						<!-- 注释掉是否使用时间段等3个字段，目前还没有用到，所以不显示到页面
						<tr>
							<td width="80" height="30" align="right"></td>
							<td>
								是否使用时间段？ 
								<s:radio list='#{"1":"是", "":"否"}' name="promtDef.timeFlag" onclick="timeFlagClick();"/>
							</td>
							
							<td id="timePeriodTitle" width="80" height="30" align="right">时间段</td>
							<td>
								<div id='timePeriodDiv' style="display:none;">
									<s:textfield name="promtDef.effTime" id="effTime" cssStyle="width:70px;" disabled="true"/>
									-
									<s:textfield name="promtDef.expirTime" id="expirTime" cssStyle="width:70px;" disabled="true"/>
									<span class="field_tipinfo">格式HHmm</span>
								</div>
							</td>
						</tr>
						 -->
						<tr>
							<td width="80" height="30" align="right"></td>
							<td>
								是否有附加范围？
								<input type="radio" name="addScope" value="1" id="idForAddScopeYes"/><label for="idForAddScopeYes">是</label>
								<input type="radio" name="addScope" value="0" id="idForAddScopeNo" checked/><label for="idForAddScopeNo">否</label>
							</td>
							
							<td id="idAddScope_1" width="80" height="30" align="right" style="display: none">范围类型</td>
							<td id="idAddScope_2" style="display: none">
								<s:select id="scopeTypeId" name="scopeType" list="scopeTypeList" listKey="value" listValue="name">
								</s:select>
							</td>
						</tr>
					</table>
					
					<hr style="width: 98%; margin: 10px 0px 10px 0px;"/>
					<table class="form_grid" id="idAddScope_3" style="display: none" 
							width="60%" border="0" cellspacing="3" cellpadding="0">
						<tr>
						   <td align="center" nowrap class="titlebg">序号</td>
						   <td id="idMerNoTd_2" align="center" nowrap class="titlebg">商户号</td>
						   <td align="center" nowrap class="titlebg">卡bin范围</td>
						   <td align="center" nowrap class="titlebg">卡子类型</td>
						   <td align="center" nowrap class="titlebg">积分下限</td>
						   <td align="center" nowrap class="titlebg">积分上限</td>
						   <td align="center" nowrap class="titlebg">会员级别</td>
						</tr>
						<tr id="idDetail_1">
							<td align="center" class="u_half">1</td>
							<td align="center" id="idMerNoTd_1">
								<s:hidden id="idMerNo_1" name="merNo"/>
								<s:textfield id="idMerNo_Sel_1" name="merNo_Sel_1"/>
							</td>
							<td align="center" class="u_half">
								<s:hidden id="idCardBinScope_1" name="cardBinScope"/>
								<s:textfield id="idCardBinScope_Sel_1" name="cardBinScope_Sel_1" />
							</td>
							<td align="center" class="u_half">
								<s:hidden id="idCardSubclass_1" name="cardSubclass"/>
								<s:textfield id="idCardSubclass_Sel_1" name="cardSubclass_Sel_1" />
							</td>
							<td align="center" class="u_half">
								<s:textfield name="ptLlimit" />
							</td>
							<td align="center" class="u_half">
								<s:textfield name="ptUlimit" />
							</td>
							<td align="center" class="u_half">
								<s:textfield name="membLevel" />
							</td>
						</tr>
						<tr id="idLink" style="line-height: 30px;">
							<td align="left" colspan="4" style="padding-left: 20px;">
								<span class="redlink"><a href="javascript:void(0);" onclick="javascript:addItem();">增加一项</a><a class="ml30" href="javascript:void(0);" onclick="javascript:deleteItem();">删除一项</a></span>
							</td>
						</tr>
					</table>
					<table class="form_grid" width="60%" border="0" cellspacing="3" cellpadding="0">
						<tr style="margin-top: 30px;">
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="下一步" id="input_btn2"  name="ok" onclick="submitForm();" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/promotions/promtDef/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:hidden />
					<s:token name="_TOKEN_PROMT_DEF_ADD"/>
				</s:form>
				
				<span class="note_div">注释</span>
				<ul class="showli_div">
					<li class="showli_div">卡Bin范围：可选。不选的话为通用Bin，最多支持多选30个卡Bin。 </li>
					<!-- 
					<li class="showli_div">如果选择使用时间段，需输入时间段，24小时制，格式：HHmm 。例如：1400表示下午2点。 </li>
					 -->
					<li class="showli_div">附加范围：可选。如果添加多个附加范围，范围类型也只能选一个，要么全部为排除范围，要么全部为新增范围。附加范围的6个条件可以只填几项，未填的条件不生效。</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>