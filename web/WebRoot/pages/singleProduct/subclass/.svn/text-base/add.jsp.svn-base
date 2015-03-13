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
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<script type="text/javascript">
			$(function(){
				$("#membClassid").removeAttr('disabled');
				$('td[id^="membClassid_td"]').show();
				$("#ptClassId").removeAttr('disabled');
			
				var card_class_Id = $('#cardClassId').val();
				
				$('#cardClassId').change(function(){
					var cardClass = $("#cardClassId").val();
					if(cardClass == "05") {// 若为赠券卡
						$("#membClassid").attr("disabled", "true");
						$("#membLevelId").attr("disabled", "true");
						$("#ptOpencardId").attr("disabled", "true");
						$("#ptClassId").attr("disabled", "true");
						$('td[id^="membClassid_td"]').hide();
						$('#ptClassId_tr').hide();
						$('#faceValueId').attr('readonly', 'true').addClass('readonly');
					} else if(cardClass == ""){ 
						$("#membClassid").attr("disabled", "true");
						$("#membLevelId").attr("disabled", "true");
						$("#ptOpencardId").attr("disabled", "true");
						$("#ptClassId").attr("disabled", "true");
						$('td[id^="membClassid_td"]').hide();
						$('#ptClassId_tr').hide();
						$('#faceValueId').removeAttr('readonly').removeClass('readonly');
					} else {
						$("#membClassid").removeClass('{required:true}');
						$("#ptClassId").removeClass('{required:true}');
						
						$("#membClassid").removeAttr('disabled');
						$("#ptClassId").removeAttr('disabled');
						$('td[id^="membClassid_td"]').show();
						$('#ptClassId_tr').show();
						$('#faceValueId').removeAttr('readonly').removeClass('readonly');
						if(cardClass == '03'){ // 为折扣卡时，必选折扣子类型
							$("#membClassid").removeClass('{required:true}');
						}
						if(cardClass == '04'){ // 为会员卡时，必选会员子类型
							$("#membClassid").addClass('{required:true}');
						}
						if(cardClass == '06'){ // 为次卡时，必选次卡子类型
							$("#membClassid").removeClass('{required:true}');
						}
						SysStyle.addRequiredStyle();
						SysStyle.removeUnRequiredStyle();
					}
				});
				
				$('#chkPwdId').change(function(){
					var value = $(this).val();
					if(value == '1'){
						$("#pwdTypeId").removeAttr('disabled');
						$("#isChgPwdId").removeAttr('disabled');
						$('#usePwd_tr').show();
					} else {
						$('#pwdTypeId').attr('disabled', 'true');
						$('#isChgPwdId').attr('disabled', 'true');
						$('#usePwd_tr').hide();
					}
				});
				$('#pwdTypeId').change(function(){
					var value = $(this).val();
					$('#pwdId').attr('disabled', 'true');
					$('td[id^="pwdId_td"]').hide();
				});
				$('#membClassid').change(function(){
					var value = $(this).val();
					if(!isEmpty(value)){
						$('#membLevelId_tr').show();
						$('#membLevelId').removeAttr('disabled');
					}else {
						$('#membLevelId_tr').hide();
						$('#membLevelId').attr('disabled', 'true');
					}
				});
				$('#ptClassId').change(function(){
					var value = $(this).val();
					if(isEmpty(value)){
						$('td[id^="ptOpencardId_td"]').hide();
						$('#ptOpencardId').attr('disabled', 'true');
					}else{
						$('td[id^="ptOpencardId_td"]').show();
						$('#ptOpencardId').removeAttr('disabled');
					}
				});
				$('#id_expirMthd').change(function(){
					var value = $(this).val();
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
						$('td[id^="id_effPeriod_td"]').hide();
						$('#id_effPeriod').attr('disabled', 'true');
					}
				});
				selectDefaultValue();
			});
			
			function selectDefaultValue(){
			 	$('#chkPwdId').each(function(){
					try {
						var headerValue = $(this).find('option:eq(0)').val();
						var secondValue = $(this).find('option:eq(1)').val();
						// 默认选择是
						if (secondValue == '1'){
							$(this).val(secondValue);
						}
					} catch(e){}
				});

			 	$('#pwdTypeId').each(function(){
					try {
						var headerValue = $(this).find('option:eq(0)').val();
						var secondValue = $(this).find('option:eq(1)').val();
						// 默认选择固定密码
						if (secondValue == '0'){
							$(this).val(secondValue);
						}
					} catch(e){}
				});

			 	$('#id_expirMthd').each(function(){
					try {
						var headerValue = $(this).find('option:eq(0)').val();
						var secondValue = $(this).find('option:eq(1)').val();
						// 默认选择指定日期
						if (secondValue == '1'){
							$(this).val(secondValue);
						}
					} catch(e){}
				});

			 	$('#id_chkPfcard').each(function(){
					try {
						var headerValue = $(this).find('option:eq(0)').val();
						var secondValue = $(this).find('option:eq(1)').val();
						// 默认选择否
						if (secondValue == '0'){
							$(this).val(secondValue);
						}
						
					} catch(e){}
				});

			 	$('#id_autoCancelFlag').each(function(){
					try {
						var headerValue = $(this).find('option:eq(0)').val();
						var secondValue = $(this).find('option:eq(1)').val();
						// 默认选择否
						if (secondValue == '0'){
							$(this).val(secondValue);
						}
					} catch(e){}
				});
			 }
			
			
			function check(){
				var value = $('#membClassid').val();// 会员子类型
				if($('#inputForm').validate().form()){
					$('#inputForm').submit();
					$('#input_btn2').attr('disabled', 'true');
					$("#loadingBarDiv").css("display","inline");
					$("#contentDiv").css("display","none");
				}
			}
			
		</script>
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
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">卡类型名称</td>
							<td>
								<s:textfield name="cardSubClassTemp.cardSubclassName" cssClass="{required:true}" maxlength="40"/>
								<span class="field_tipinfo">请输入卡类型</span>
							</td>
							<td width="80" height="30" align="right">卡种</td>
							<td>
							<!-- 
								<s:select id="cardClassId" list="cardTypeList" listKey="cardTypeCode" listValue="cardTypeName" 
								 cssClass="readonly"></s:select>
								  -->
								  <s:textfield value="储值卡" cssClass="readonly" readonly="true"/>
								  <input type="hidden" value="01" name="cardSubClassTemp.cardClass" />
							</td>							
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">密码标志</td>
							<td colspan="3">
								<s:select id="chkPwdId" name="cardSubClassTemp.chkPwd" list="yesOrNoList" listKey="value" listValue="name" 
								 cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">是否使用密码？</span>
							</td>
						</tr>
						<tr id="usePwd_tr">
							<td width="80" height="30" align="right" >密码类型</td>
							<td >
								<s:select id="pwdTypeId" name="cardSubClassTemp.pwdType" list="pwdTypeList" listKey="value" listValue="name" 
								 cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">选择密码类型</span>
							</td>
							<td width="80" height="30" align="right">是否强制修改密码</td>
							<td>
								<s:select id="isChgPwdId" name="cardSubClassTemp.isChgPwd" list="yesOrNoList" listKey="value" listValue="name" 
								 cssClass="{required:true}" value="1"></s:select>
								<span class="field_tipinfo">是否修改密码？</span>
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
						<tr>
							<td width="80" height="30" align="right">卡片抵用金</td>
							<td>
								<s:textfield name="cardSubClassTemp.cardPrice" cssClass="{required:true, num:true, decimal:'12,2'}" value="0.00"/>
								<span class="field_tipinfo">卡片的工本费</span>
								<span class="error_tipinfo">输入金额</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">是否能充值</td>
							<td>
								<s:select id="id_DepositFlag" name="cardSubClassTemp.depositFlag" list="isYesList" listKey="value" listValue="name" 
								 cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择是或否</span>
							</td>
							
							<td width="80" height="30" align="right">售卡面值能否修改</td>
							<td>
								<s:select id="id_changeFaceValue" name="cardSubClassTemp.changeFaceValue" list="isYesList" listKey="value" listValue="name" 
								 cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择是或否</span>
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
						<tr style="display: none;">
							<td width="80" height="30" align="right">卡片类型</td>
							<td>
							<!-- 
								<s:select id="id_icType" name="cardSubClassTemp.icType" list="cardFlagList" listKey="value" listValue="name" 
								 cssClass="{required:true}" ></s:select>
								  -->
								 <s:textfield value="磁条卡" cssClass="readonly" readonly="true"/>
								  <input type="hidden" value="00" name="cardSubClassTemp.icType" />
								 <span class="field_tipinfo"></span>
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
								<input type="button" value="提交" id="input_btn2" name="ok" onclick="return check();" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/singleProduct/subclass/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_SINGLEPRODUCT_SUBCLASS_ADD"/>
				</s:form>
			</div>
			
			<div class="contentb">
				<span class="note_div">注释</span>
				<ul class="showli_div">
					<li class="showli_div">卡类型名称：最大长度为128个字符，汉字算两个字符，英文或数字算一个字符。系统对外使用该名称来显示卡类型。例如，广州沃尔玛会员卡。</li>
					<li class="showli_div">卡种：表示该卡类型的主要功能，一个卡种除了有主功能(例如储值卡)，它也可以有其他次要功能，例如积分功能、折扣功能。赠券卡比较特殊，它只能做主功能，而且不能同时拥有其他次要功能。</li>
					<li class="showli_div">参考面值：默认为0，在售卡的时候也作为默认开卡金额使用，默认为0。此外，参考面值也写进制卡文件中，可能在制卡的时候有用。</li>
					<li class="showli_div">卡片抵用金：卡片的工本费，退卡的时候需要退卡片抵用金给持卡人。默认为0。</li>
					<li class="showli_div">密码类型：如果选择固定密码，那么初始密码为六个1</li>
					<li class="showli_div">预制卡检查：如果设置此标志，那么该种卡开卡时需要检查预制卡资料表，对于由本系统产生制卡资料的卡，应做制卡资料验证检查。</li>
					<li class="showli_div">自动销卡标志：如果设置此标志，余额为0的时候系统自动销卡。 </li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>