<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			$(function(){
				var cardClass = $("#cardClassId").val();
				showClass(cardClass);
				
				var chkPwd = $('#chkPwdId').val();
				showPass(chkPwd);
				
				var passType = $('#pwdTypeId').val();
				showPassType(passType);
				
				var mem = $('#membClassid').val();
				showMem(mem);
				
				var ptClassId = $('#ptClassId').val()
				showPtId(ptClassId);
				
				var expirMthd = $('#id_expirMthd').val();
				showExpirMthd(expirMthd);
				
				$('#chkPwdId').change(function(){
					var value = $(this).val();
					showPass(value);
				});
				$('#cardClassId').change(function(){
					var cardClass = $("#cardClassId").val();
					//$("#binNoId").load(CONTEXT_PATH + "/cardSubClass/binNoList.do",{'cardTypeNo':cardClass, 'callId':callId()});
					showClass(cardClass);
				});
				$('#pwdTypeId').change(function(){
					var value = $(this).val();
					showPassType(value);
				});
				$('#membClassid').change(function(){
					var value = $(this).val();
					showMem(value);
				});
				$('#ptClassId').change(function(){
					var value = $(this).val();
					showPtId(value);
				});
				$('#id_expirMthd').change(function(){
					var value = $(this).val();
					showExpirMthd(value);
				});
				$('#coupnClassId').change(function(){
					var value = $(this).val();
					if(isEmpty(value)){
						$('#faceValueId').val('');
						return;
					}
					$.post(CONTEXT_PATH + '/pages/singleProduct/subclass/getFaceValue.do', {'coupnClassId':value, 'callId':callId()}, 
						function(data){
							if(data.success){
								$('#faceValueId').val(fix(data.faceValue));
								$('#input_btn2').removeAttr('disabled');
							} else {
								showMsg(data.errorMsg);
								$('#faceValueId').val('');
								$('#input_btn2').attr('disabled', 'true');
							}
						}, 'json');
				});
			});
			function showClass(cardClass){
				if(cardClass == "05") {// 若为赠券卡
						$("#coupnClassId").removeAttr('disabled');
						$("#membClassid").attr("disabled", "true");
						$("#membLevelId").attr("disabled", "true");
						$("#ptOpencardId").attr("disabled", "true");
						$("#discntClassId").attr("disabled", "true");
						$("#accuClassId").attr("disabled", "true");
						$("#ptClassId").attr("disabled", "true");
						$('td[id^="coupnClassId_td"]').show();
						$('td[id^="membClassid_td"]').hide();
						$('#ptClassId_tr').hide();
						$('#discntClassId_tr').hide();
						$('#faceValueId').attr('readonly', 'true').addClass('readonly');
					} else if(cardClass == ""){ 
						$("#coupnClassId").attr("disabled", "true");
						$("#membClassid").attr("disabled", "true");
						$("#membLevelId").attr("disabled", "true");
						$("#ptOpencardId").attr("disabled", "true");
						$("#discntClassId").attr("disabled", "true");
						$("#accuClassId").attr("disabled", "true");
						$("#ptClassId").attr("disabled", "true");
						$('td[id^="coupnClassId_td"]').hide();
						$('td[id^="membClassid_td"]').hide();
						$('#ptClassId_tr').hide();
						$('#discntClassId_tr').hide();
						$('#faceValueId').removeAttr('readonly').removeClass('readonly');
					} else {
						$("#membClassid").removeAttr('disabled');
						$("#discntClassId").removeAttr('disabled');
						$("#accuClassId").removeAttr('disabled');
						$("#ptClassId").removeAttr('disabled');
						$("#coupnClassId").attr("disabled", "true");
						$('td[id^="coupnClassId_td"]').hide();
						$('td[id^="membClassid_td"]').show();
						$('#ptClassId_tr').show();
						$('#discntClassId_tr').show();
						$('#faceValueId').removeAttr('readonly').removeClass('readonly');
					}
			}
			function showPass(chkPwd){
				if(chkPwd == '1'){
					$("#pwdTypeId").removeAttr('disabled');
					$('#chkPwdId_tr').show();
				} else {
					$('#pwdTypeId').attr('disabled', 'true');
					$('#chkPwdId_tr').hide();
				}
			}
			function showPassType(value){
				if(value == '0'){
					$("#pwdId").removeAttr('disabled');
					$('td[id^="pwdId_td"]').show();
				} else {
					$('#pwdId').attr('disabled', 'true');
					$('td[id^="pwdId_td"]').hide();
				}
			}
			function showMem(value){
				if(!isEmpty(value)){
					$('#membLevelId_tr').show();
					$('#membLevelId').removeAttr('disabled');
				}else {
					$('#membLevelId_tr').hide();
					$('#membLevelId').attr('disabled', 'true');
				}
			}
			function showPtId(value){
				if(isEmpty(value)){
					$('td[id^="ptOpencardId_td"]').hide();
					$('#ptOpencardId').attr('disabled', 'true');
				}else{
					$('td[id^="ptOpencardId_td"]').show();
					$('#ptOpencardId').removeAttr('disabled');
				}
			}
			function check(){
				var value = $('#membClassid').val();// 会员子类型
				var accuClass = $('#accuClassId').val();// 次卡类型
				var discntClass = $('#discntClassId').val(); // 折扣子类型
				if(!isEmpty(value) && isEmpty(ptClass)){
					showMsg('积分子类型不能为空');
					return false;
				}
				var cardClass = $("#cardClassId").val();
				if(cardClass == '03' && isEmpty(discntClass)){// 为折扣卡时，必选折扣子类型
					showMsg('折扣子类型不能为空');
					return false;
				}
				if(cardClass == '04' && isEmpty(value)){// 为会员卡时，必选会员子类型
					showMsg('会员子类型不能为空');
					return false;
				}
				if(cardClass == '06' && isEmpty(accuClass)){// 为次卡时，必选次卡子类型
					showMsg('次卡子类型不能为空');
					return false;
				}
				return true;
			}
			function showExpirMthd(value) {
				if(value == '1'){
					$('td[id^="id_expirDate_td"]').show();
					$('#id_expirDate').removeAttr('disabled');
					$('td[id^="id_effPeriod_td"]').hide();
					$('#id_effPeriod').attr('disabled', 'true');
				} else if(value == '2') {
					$('td[id^="id_expirDate_td"]').hide();
					$('#id_expirDate').attr('disabled', 'true');
					$('td[id^="id_effPeriod_td"]').show();
					$('#id_effPeriod').removeAttr('disabled');
				} else {
					$('td[id^="id_expirDate_td"]').hide();
					$('#id_expirDate').attr('disabled', 'true');
					$('td[id^="id_effPeriod_td"]').hide();
					$('#id_effPeriod').attr('disabled', 'true');
				}
			}
		</script>
	</head>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">卡类型名称</td>
							<td colspan="3">
								<s:textfield name="cardSubClassTemp.cardSubclassName" cssClass="{required:true}" maxlength="40"/>
								<span class="field_tipinfo">请输入卡类型名称，名称可为中文，字母，数字</span>
							</td>
						</tr>
						<tr>				
						<td width="80" height="30" align="right">卡类型号</td>
							<td>
								<s:textfield name="cardSubClassTemp.cardSubclass" readonly="true" cssClass="readonly"/>
								<span class="field_tipinfo">输入数字字母</span>
							</td>	
							<td width="80" height="30" align="right">卡种</td>
							<td>
								<s:select id="cardClassId" name="cardSubClassTemp.cardClass" list="cardTypeList" listKey="cardTypeCode" listValue="cardTypeName" 
								 cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择卡种</span>
							</td>

							<!-- 
							<td width="80" height="30" align="right">卡BIN</td>
							<td id="binNoId">
								<select name="cardSubClassTemp.binNo" style="width: 165px" class="{required:true}">
								</select>
								<span class="field_tipinfo">请选择卡BIN号</span>
							</td>
							 -->
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">密码标志</td>
							<td>
								<s:select id="chkPwdId" name="cardSubClassTemp.chkPwd" list="yesOrNoList" listKey="value" listValue="name" 
								 cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">是否使用密码?</span>
							</td>
							
							<td width="80" height="30" align="right" id="pwdTypeId_td1" >密码类型</td>
							<td id="pwdTypeId_td2">
								<s:select id="pwdTypeId" name="cardSubClassTemp.pwdType" list="pwdTypeList" listKey="value" listValue="name" 
								 cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择密码类型</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">失效方式</td>
							<td>
								<s:select id="id_expirMthd" name="cardSubClassTemp.expirMthd" list="expirMthdList" listKey="value" listValue="name" 
								 cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
							<td id="id_effPeriod_td1" width="80" height="30" align="right" style="display: none;">指定月数</td>
							<td id="id_effPeriod_td2" style="display: none;">
								<s:textfield id="id_effPeriod" name="cardSubClassTemp.effPeriod" cssClass="{required:true,digit:true}" maxlength="4" disabled="true"/>
								<span class="field_tipinfo">经该月数失效</span>
							</td>
							<td id="id_expirDate_td1" width="80" height="30" align="right">指定日期</td>
							<td id="id_expirDate_td2" >
								<s:textfield id="id_expirDate" name="cardSubClassTemp.expirDate" onclick="WdatePicker({startDate:'{%y+2}1231', minDate:'%y-%M-%d'})" cssClass="{required:true}"/>
								<span class="field_tipinfo">到该日期失效</span>
							</td>
						</tr>
						
						<tr>
							<td id="membClassid_td1" width="80" height="30" align="right" style="display: none;">会员类型</td>
							<td id="membClassid_td2" style="display: none;">
								<s:select id="membClassid" name="cardSubClassTemp.membClass" headerKey="" headerValue="--请选择--" list="membClassList" listKey="membClass" listValue="className" 
								 disabled="true"></s:select>
								<span class="field_tipinfo">请选择会员类型</span>
							</td>
							<td id="coupnClassId_td1" width="80" height="30" align="right" style="display: none;">赠券类型</td>
							<td id="coupnClassId_td2" style="display: none;">
								<s:select id="coupnClassId" name="cardSubClassTemp.couponClass" headerKey="" headerValue="--请选择--" list="coupnClassList" listKey="coupnClass" listValue="className" 
								 disabled="true" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
							<td id="faceValueId_td1" width="80" height="30" align="right" >参考面值</td>
							<td id="faceValueId_td2">
								<s:textfield id="faceValueId" name="cardSubClassTemp.faceValue" cssClass="{required:true, num:true, decimal:'12,2'} " maxlength="15" value="0.00"/>
								<span class="field_tipinfo">默认为0</span>
							</td>
						</tr>
						<tr id="membLevelId_tr" style="display: none;">
							<td id="membLevelId_td1" width="80" height="30" align="right">初始会员级别</td>
							<td id="membLevelId_td2">
								<s:textfield id="membLevelId" name="cardSubClassTemp.membLevel" cssClass="{required:true, digit:true, minlength:2, maxlength:2}"
								disabled="true" />
								<span class="field_tipinfo">请输入2位数字</span>
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr id="ptClassId_tr"">
							<td width="80" height="30" align="right" >积分类型</td>
							<td>
								<s:select id="ptClassId" name="cardSubClassTemp.ptClass" headerKey="" headerValue="--请选择--" list="ponitClassList" listKey="ptClass" listValue="className"
								disabled="true" ></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
							<td id="ptOpencardId_td1" width="80" height="30" align="right" style="display: none;">开卡积分</td>
							<td id="ptOpencardId_td2" style="display: none;">
								<s:textfield id="ptOpencardId" name="cardSubClassTemp.ptOpencard" cssClass="{required:true, digit:true}" 
								disabled="true" />
								<span class="field_tipinfo">输入非空数字</span>
							</td>
						</tr>
						<tr id="discntClassId_tr" >
							<td width="80" height="30" align="right">折扣类型</td>
							<td>
								<s:select id="discntClassId" name="cardSubClassTemp.discntClass" headerKey="" headerValue="--请选择--" list="discntClassList" listKey="discntClass" listValue="className" 
								 disabled="true" ></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
							<td width="80" height="30" align="right">次数卡类型</td>
							<td>
								<s:select id="accuClassId" name="cardSubClassTemp.frequencyClass" headerKey="" headerValue="--请选择--" list="accuClassList" listKey="accuClass" listValue="className" 
								 disabled="true" ></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>
						<tr>

						<td width="80" height="30" align="right">最低购买价格</td>
							<td>
								<s:textfield name="cardSubClassTemp.buyPriceMin" cssClass="{num:true}"/>
								<span class="field_tipinfo">请输入最低值</span>
								<span class="error_tipinfo">该项必须为数字</span>
							</td>
							<td width="80" height="30" align="right">最高购买价格</td>
							<td>
								<s:textfield name="cardSubClassTemp.buyPriceMax" cssClass="{num:true}"/>
								<span class="field_tipinfo">请输入最高值</span>
								<span class="error_tipinfo">必须为数字</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡片抵用金</td>
							<td>
								<s:textfield name="cardSubClassTemp.cardPrice" cssClass="{required:true, num:true, decimal:'12,2'}" value="0.00"/>
								<span class="field_tipinfo">卡片的工本费</span>
								<span class="error_tipinfo">输入金额</span>
							</td>
						</tr>
						<!-- 
						<tr id="chkPwdId_tr" style="display: none;">
							
							<td id="pwdId_td1" width="80" height="30" align="right" style="display: none;">密码</td>
							<td id="pwdId_td2" style="display: none;">
								<s:textfield id="pwdId" name="cardSubClassTemp.pwd" cssClass="{required:true, digit:true,minlength:6}" maxlength="6" disabled="true"/>
								<span class="field_tipinfo">请输入6位数字</span>
							</td>
						</tr>
							 -->
						<tr>
							<td width="80" height="30" align="right">续卡最高值</td>
							<td>
								<s:textfield name="cardSubClassTemp.extenUlimit" cssClass="{num:true}"/>
								<span class="field_tipinfo">请输费用最高值</span>
								<span class="error_tipinfo">该项必须为数字</span>
							</td>
							<td width="80" height="30" align="right">续卡最低值</td>
							<td>
								<s:textfield name="cardSubClassTemp.extenLlimit" cssClass="{num:true}"/>
								<span class="field_tipinfo">请输费用最低值</span>
								<span class="error_tipinfo">该项必须为数字</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">预制卡检查</td>
							<td>
								<s:select id="id_chkPfcard" name="cardSubClassTemp.chkPfcard" list="yesOrNoList" listKey="value" listValue="name" 
								 cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">是否检查？</span>
								<span class="error_tipinfo">该项非空</span>
							</td>
							<td width="80" height="30" align="right">自动销卡标志</td>
							<td>
								<s:select id="id_autoCancelFlag" name="cardSubClassTemp.autoCancelFlag" list="yesOrNoList" listKey="value" listValue="name" 
								 cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">是否自动销卡？</span>
								<span class="error_tipinfo">该项不能为空</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">卡内额度上限</td>
							<td>
								<s:textfield name="cardSubClassTemp.creditUlimit" cssClass="{num:true, decimal:'12,2'}" value="0.00"/>
								<span class="field_tipinfo">卡内额度上限</span>
								<span class="error_tipinfo">该项为(12,2)</span>
							</td>
							<td width="80" height="30" align="right">单笔充值上限</td>
							<td>
								<s:textfield name="cardSubClassTemp.chargeUlimit" cssClass="{num:true, decimal:'12,2'}" value="0.00"/>
								<span class="field_tipinfo">单笔充值上限</span>
								<span class="error_tipinfo">该项为(12,2)</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">单笔消费上限</td>
							<td>
								<s:textfield name="cardSubClassTemp.consmUlimit" cssClass="{num:true, decimal:'12,2'}" value="0.00"/>
								<span class="field_tipinfo">单笔消费上限</span>
								<span class="error_tipinfo">该项为(12,2)</span>
							</td>
							<td width="80" height="30" align="right">绝对失效日期</td>
							<td>
								<s:textfield name="cardSubClassTemp.mustExpirDate" onclick="WdatePicker({startDate:'{%y+20}1231',minDate:'%y-%M-%d'})" cssStyle="width:68px;" value="20991231"/>
								<span class="field_tipinfo">为空时默认为20991231</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">卡延期(月数)</td>
							<td>
								<s:textfield name="cardSubClassTemp.extenPeriod" cssClass="{digit:true}" maxlength="4" value="0"/>
								<span class="field_tipinfo">卡延期(月数)</span>
								<span class="error_tipinfo">必须为数字</span>
							</td>
							<td width="80" height="30" align="right">最大延期次数</td>
							<td>
								<s:textfield name="cardSubClassTemp.extenMaxcnt" cssClass="{digit:true}" maxlength="4" value="0"/>
								<span class="field_tipinfo">最大延期次数？</span>
								<span class="error_tipinfo">必须为数字</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡片类型</td>
							<td>
								<s:select id="id_icType" name="cardSubClassTemp.icType" list="cardFlagList" listKey="value" listValue="name" 
								 cssClass="{required:true}" ></s:select>
								 <span class="field_tipinfo">请选择卡片类型</span>
							</td>
							<td id="id_icModelNo_td1" style="display: none;">IC卡模型</td>
							<td id="id_icModelNo_td2" style="display: none;">
								<s:select id="id_icModelNo" name="cardSubClassTemp.icModelNo" list="modelList" listKey="modelNo" listValue="modelDescribe" 
								 cssClass="{required:true}" disabled="true"></s:select>
							</td>
						</tr>
						<tr id="id_IC_Config_tr1" style="display: none;">
							<td width="80" height="30" align="right">电子现金余额</td>
							<td>
								<s:textfield id="id_ecashbalance" name="icTempPara.ecashbalance" cssClass="{num:true, decimal:'12,2', required:true}" value="0.00" disabled="true"/>
								<span class="field_tipinfo">电子现金余额</span>
								<span class="error_tipinfo">该项为(12,2)</span>
							</td>
							<td width="80" height="30" align="right">IC卡余额上限</td>
							<td>
								<s:textfield id="id_balanceLimit" name="icTempPara.balanceLimit" cssClass="{num:true, decimal:'12,2', required:true}" value="0.00" disabled="true"/>
								<span class="field_tipinfo">IC卡余额上限</span>
								<span class="error_tipinfo">该项为(12,2)</span>
							</td>
						</tr>
						<tr id="id_IC_Config_tr2" style="display: none;">
							<td width="80" height="30" align="right">IC卡单笔交易限额</td>
							<td>
								<s:textfield id="id_amountLimit" name="icTempPara.amountLimit" cssClass="{num:true, decimal:'12,2', required:true}" value="0.00" disabled="true"/>
								<span class="field_tipinfo">单笔交易限额</span>
								<span class="error_tipinfo">该项为(12,2)</span>
							</td>
							<td width="80" height="30" align="right">是否开卡检测</td>
							<td>
								<s:select id="id_onlineCheck" name="icTempPara.onlineCheck" list="yesOrNoList" listKey="value" listValue="name" 
								 cssClass="{required:true}" disabled="true"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" name="ok" onclick="return check();" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/singleProduct/subclass/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_SINGLEPRODUCT_SUBCLASS_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>