<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%
	response.setHeader("Cache-Control", "no-cache");
%>
<%@ include file="/common/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/sys.jsp"%>
<title>${ACT.name}</title>

<f:css href="/css/page.css" />
<f:js src="/js/jquery.js" />
<f:js src="/js/plugin/jquery.metadata.js" />
<f:js src="/js/plugin/jquery.validate.js" />

<f:js src="/js/date/WdatePicker.js" defer="defer" />
<f:js src="/js/sys.js" />
<f:js src="/js/common.js" />

<script type="text/javascript">
	$(function() {
		$("#membClassid").removeAttr('disabled');
		$('td[id^="membClassid_td"]').show();
		$("#discntClassId").removeAttr('disabled');
		$("#accuClassId").removeAttr('disabled');
		$("#ptClassId").removeAttr('disabled');

		$('#cardClassId').change(changeCardClass);//改变卡种
		// 密码标志
		$('#chkPwdId').change(function() {
			var value = $(this).val();
			if (value == '1') { // 密码标志为1，使用密码
				$("#pwdTypeId").removeAttr('disabled');
				$("#isChgPwdId").removeAttr('disabled');
				$('#usePwd_tr').fadeIn();
			} else {
				$('#pwdTypeId').attr('disabled', 'true');
				$('#isChgPwdId').attr('disabled', 'true');
				$('#usePwd_tr').hide();
			}
		});
		// 会员类型
		$('#membClassid').change(function() {
			var value = $(this).val();
			if (!isEmpty(value)) {
				$('#membLevelId_tr').fadeIn();
				$('#membLevelId').removeAttr('disabled');
			} else {
				$('#membLevelId_tr').hide();
				$('#membLevelId').attr('disabled', 'true');
			}
		});
		// 积分类型
		$('#ptClassId').change(function() {
			var value = $(this).val();
			if (isEmpty(value)) {
				$('td[id^="ptOpencardId_td"]').hide();
				$('#ptOpencardId').attr('disabled', 'true');
			} else {
				$('td[id^="ptOpencardId_td"]').fadeIn();
				$('#ptOpencardId').removeAttr('disabled');
			}
		});
		// 赠券类型
		$('#coupnClassId').change(function() {
			var value = $(this).val();
			if (isEmpty(value)) {
				$('#faceValueId').val('');
				return;
			}
			$.post(CONTEXT_PATH + '/cardSubClass/getFaceValue.do', {
				'coupnClassId' : value,
				'callId' : callId()
			}, function(data) {
				if (data.success) {
					$('#faceValueId').val(fix(data.faceValue));
					$('#input_btn2').removeAttr('disabled');
				} else {
					showMsg(data.errorMsg);
					$('#faceValueId').val('');
					$('#input_btn2').attr('disabled', 'true');
				}
			}, 'json');
		});
		// 失效方式
		$('#id_expirMthd').change(function() {
			var value = $(this).val();
			if (value == '1') {// 指定失效日期
				$('td[id^="id_expirDate_td"]').fadeIn();
				$('#id_expirDate').removeAttr('disabled');
				$('td[id^="id_effPeriod_td"]').hide();
				$('#id_effPeriod').attr('disabled', 'true');
			} else if (value == '2') { //指定失效月数
				$('td[id^="id_expirDate_td"]').hide();
				$('#id_expirDate').attr('disabled', 'true');
				$('td[id^="id_effPeriod_td"]').fadeIn();
				$('#id_effPeriod').removeAttr('disabled');
			} else {
				$('td[id^="id_effPeriod_td"]').hide();
				$('#id_effPeriod').attr('disabled', 'true');
			}
		});
		// 卡片类型
		$('#id_icType').change(function() {
			var value = $(this).val();
			if (value == '01' || value == '02' || value == '03') {// 01 纯IC卡；02 复合卡；03 M1卡
				$('tr[id^="id_IC_Config_tr"]').fadeIn();
				$('td[id^="id_icModelNo_td"]').fadeIn();
				$('#id_icModelNo').removeAttr('disabled')
				$('#id_ecashbalance').removeAttr('disabled')
				$('#id_balanceLimit').removeAttr('disabled')
				$('#id_amountLimit').removeAttr('disabled')
				$('#id_onlineCheck').removeAttr('disabled')
				$('#id_autoDepositFlag').removeAttr('disabled')
				$('#id_autoDepositAmt').removeAttr('disabled')
			} else { // 00 磁条卡；
				$('tr[id^="id_IC_Config_tr"]').hide();
				$('td[id^="id_icModelNo_td"]').hide();
				$('#id_icModelNo').attr('disabled', 'true');
				$('#id_ecashbalance').attr('disabled', 'true');
				$('#id_balanceLimit').attr('disabled', 'true');
				$('#id_amountLimit').attr('disabled', 'true');
				$('#id_onlineCheck').attr('disabled', 'true');
				$('#id_autoDepositFlag').attr('disabled', 'true');
				$('#id_autoDepositAmt').attr('disabled', 'true');
			}
		});

		changeCardClass(); // 初始化相关显示
	});

	/** 改变 卡种 或 发卡机构 时，页面相应改动 */
	function changeCardClass() {
		var cardClass = $("#cardClassId").val();

		loadBinNoList($("#cardIssuer").val(), cardClass);//加载卡bin下拉框

		if (cardClass == "05") {// 若为赠券卡
			$("#coupnClassId").removeAttr('disabled');
			$("#membClassid").attr("disabled", "true");
			$("#membLevelId").attr("disabled", "true");
			$("#ptOpencardId").attr("disabled", "true");
			$("#discntClassId").attr("disabled", "true");
			$("#accuClassId").attr("disabled", "true");
			$("#ptClassId").attr("disabled", "true");
			$('td[id^="coupnClassId_td"]').fadeIn();
			<!--赠券卡类型替换-->
			$('td[id^="membClassid_td"]').hide();
			<!--会员类型-->
			$('#ptClassId_tr').hide();
			$('#discntClassId_tr').hide();
			$('#faceValueId').attr('readonly', 'true').addClass('readonly');
			$('td[id^="ecouponTypeTd"]').hide();
		} else if (cardClass == "") {
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
			$('td[id^="ecouponTypeTd"]').hide();
		} else {
			$("#membClassid").removeClass('{required:true}');
			$("#accuClassId").removeClass('{required:true}');
			$("#discntClassId").removeClass('{required:true}');
			$("#ptClassId").removeClass('{required:true}');

			$("#membClassid").removeAttr('disabled');
			$("#discntClassId").removeAttr('disabled');
			$("#accuClassId").removeAttr('disabled');
			$("#ptClassId").removeAttr('disabled');
			$("#coupnClassId").attr("disabled", "true");
			$('td[id^="coupnClassId_td"]').hide();
			$('td[id^="membClassid_td"]').fadeIn();
			$('#ptClassId_tr').fadeIn();
			$('#discntClassId_tr').fadeIn();
			$('#faceValueId').removeAttr('readonly').removeClass('readonly');
			$('td[id^="ecouponTypeTd"]').hide();
			if (cardClass == '03') { // 为折扣卡时，必选折扣子类型
				$("#discntClassId").addClass('{required:true}');
				$("#membClassid").removeClass('{required:true}');
				$("#accuClassId").removeClass('{required:true}');
			}
			if (cardClass == '04') { // 为会员卡时，必选会员子类型
				$("#discntClassId").removeClass('{required:true}');
				$("#membClassid").addClass('{required:true}');
				$("#accuClassId").removeClass('{required:true}');
			}
			if (cardClass == '06') { // 为次卡时，必选次卡子类型
				$("#discntClassId").removeClass('{required:true}');
				$("#membClassid").removeClass('{required:true}');
				$("#accuClassId").addClass('{required:true}');
			}
			if (cardClass == '01') {//
				$('td[id^="ecouponTypeTd"]').fadeIn();
				$("#ecouponSel").removeAttr("disabled");
			}
			SysStyle.addRequiredStyle();
			SysStyle.removeUnRequiredStyle();
		}
	}

	/** 根据 发卡机构 和 卡种 加载binNoList页面 */
	function loadBinNoList(cardIssuer, cardType) {
		if (!isEmpty(cardIssuer) && !isEmpty(cardType)) {
			var url = CONTEXT_PATH + "/cardSubClass/binNoList.do";
			var params = {
				'cardBin.cardIssuer' : cardIssuer,
				'cardBin.cardType' : cardType,
				'callId' : callId()
			}
			$("#cardBin_Td").load(url, params); //加载binNoList页面
		}
	}

	function check() {
		var value = $('#membClassid').val();// 会员子类型
		var accuClass = $('#accuClassId').val();// 次卡子类型
		var discntClass = $('#discntClassId').val(); // 折扣子类型
		/*if(!isEmpty(value) && isEmpty(ptClass)){
			showMsg('积分子类型不能为空');
			return false;
		}*/
		var icType = $('#id_icType').val();
		var expMthd = $('#id_expirMthd').val();
		if ((icType == '01' || icType == '02' || icType == '03')
				&& expMthd != '1') {
			showMsg("注意：“纯IC卡”或“复合卡”或“M1卡”必须指定“失效日期”");
			return false;
		}
		if ($('#inputForm').validate().form()) {
			$('#inputForm').submit();
			$('#input_btn2').attr('disabled', 'true');
			//window.parent.showWaiter();
			$("#loadingBarDiv").css("display", "inline");
			$("#contentDiv").css("display", "none");
		}
	}
</script>
</head>

<body>
	<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>

	<div id="loadingBarDiv"
		style="display: none; width: 100%; height: 100%">
		<table width=100% height=100%>
			<tr>
				<td align=center>
					<center>
						<img src="${CONTEXT_PATH}/images/ajax_saving.gif" align="middle" />
						<div id="processInfoDiv"
							style="font-size: 15px; font-weight: bold">正在处理中，请稍候...</div>
						<br /> <br /> <br />
					</center>
				</td>
			</tr>
		</table>
	</div>

	<div id="contentDiv" class="userbox">
		<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
		<div class="contentb">
		<s:debug></s:debug>
			<s:form action="add.do" id="inputForm" cssClass="validate">
				<table class="form_grid" width="100%" border="0" cellspacing="3"
					cellpadding="0">
					<caption>${ACT.name}</caption>
					<tr>
						<td width="80" height="30" align="right">发卡机构</td>
						<td><s:hidden id="cardIssuer"
								name="cardSubClassDef.cardIssuer" /> <input type="text"
							value="${fn:branch(cardSubClassDef.cardIssuer)}"
							class="{required:true} readonly" readonly="readonly"
							maxlength="40" /></td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">卡类型名称</td>
						<td><s:textfield name="cardSubClassDef.cardSubclassName"
								cssClass="{required:true}" maxlength="40" /> <span
							class="field_tipinfo">请输入名称</span></td>

						<td width="80" height="30" align="right">是否能充值</td>
						<td><s:select id="id_DepositFlag"
								name="cardSubClassDef.depositFlag" list="yesOrNoList"
								listKey="value" listValue="name" cssClass="{required:true}" />
							<span class="field_tipinfo">请选择是或否</span></td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">卡种</td>
						<td><s:select id="cardClassId"
								name="cardSubClassDef.cardClass" list="cardTypeList"
								listKey="cardTypeCode" listValue="cardTypeName"
								cssClass="{required:true}" /> <span class="field_tipinfo">请选择卡种</span>
						</td>
						<td width="80" height="30" align="right">卡BIN</td>
						<td id="cardBin_Td"><select name="cardSubClassDef.binNo"
							style="width: 165px" class="{required:true}"
							onmouseover="FixWidth(this);" /> <span class="field_tipinfo">请选择卡BIN号</span>
						</td>
					</tr>

					<tr>
						<td width="40" height="30" align="right">密码标志</td>
						<td colspan="3"><s:select id="chkPwdId"
								name="cardSubClassDef.chkPwd" list="trueOrFalseList"
								listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">是否使用密码？</span></td>

					</tr>
					<tr id="usePwd_tr">
						<td width="80" height="30" align="right">密码类型</td>
						<td><s:select id="pwdTypeId" name="cardSubClassDef.pwdType"
								list="pwdTypeList" listKey="value" listValue="name"
								cssClass="{required:true}"></s:select> <span
							class="field_tipinfo">选择密码类型</span></td>
						<td width="80" height="30" align="right">是否强制修改密码</td>
						<td><s:select id="isChgPwdId" name="cardSubClassDef.isChgPwd"
								list="trueOrFalseList" listKey="value" listValue="name"
								cssClass="{required:true}" value="0"></s:select> <span
							class="field_tipinfo">是否修改密码？</span></td>
					</tr>

					<tr>
						<td width="80" height="30" align="right">失效方式</td>
						<td><s:select id="id_expirMthd"
								name="cardSubClassDef.expirMthd" list="expirMthdList"
								listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">请选择</span></td>
						<td id="id_effPeriod_td1" width="80" height="30" align="right"
							style="display: none;">指定月数</td>
						<td id="id_effPeriod_td2" style="display: none;"><s:textfield
								id="id_effPeriod" name="cardSubClassDef.effPeriod"
								cssClass="{required:true,digit:true}" maxlength="4"
								disabled="true" /> <span class="field_tipinfo">售卡经该月数失效</span> <span
							class="error_tipinfo">请输入正整数</span></td>
						<td id="id_expirDate_td1" width="80" height="30" align="right">指定日期</td>
						<td id="id_expirDate_td2"><s:textfield id="id_expirDate"
								name="cardSubClassDef.expirDate"
								onclick="WdatePicker({startDate:'{%y+2}1231', minDate:'%y-%M-%d'})"
								cssClass="{required:true}" /> <span class="field_tipinfo">到该日期失效</span>
							<span class="error_tipinfo">请选择日期</span></td>
					</tr>

					<tr>
						<td id="membClassid_td1" width="80" height="30" align="right"
							style="display: none;">会员类型</td>
						<td id="membClassid_td2" style="display: none;"><s:select
								id="membClassid" name="cardSubClassDef.membClass" headerKey=""
								headerValue="--请选择--" list="membClassList" listKey="membClass"
								listValue="className" disabled="true"></s:select> <span
							class="field_tipinfo">请选择会员类型</span></td>
						<td id="coupnClassId_td1" width="80" height="30" align="right"
							style="display: none;">赠券类型</td>
						<td id="coupnClassId_td2" style="display: none;"><s:select
								id="coupnClassId" name="cardSubClassDef.couponClass"
								headerKey="" headerValue="--请选择--" list="coupnClassList"
								listKey="coupnClass" listValue="className" disabled="true"
								cssClass="{required:true}"></s:select> <span
							class="field_tipinfo">请选择</span></td>
						<td id="faceValueId_td1" width="80" height="30" align="right">参考面值</td>
						<td id="faceValueId_td2"><s:textfield id="faceValueId"
								name="cardSubClassDef.faceValue"
								cssClass="{required:true, num:true, decimal:'12,2'} "
								maxlength="15" value="0.00" /> <span class="field_tipinfo">默认为0</span>
						</td>
					</tr>
					<tr id="membLevelId_tr" style="display: none;">
						<td id="membLevelId_td1" width="80" height="30" align="right">初始会员级别</td>
						<td id="membLevelId_td2"><s:textfield id="membLevelId"
								name="cardSubClassDef.membLevel"
								cssClass="{required:true, digit:true, minlength:2, maxlength:2}"
								disabled="true" /> <span class="field_tipinfo">请输入2位数字</span></td>
						<td></td>
						<td></td>
					</tr>
					<tr id="ptClassId_tr">
						<td width="80" height="30" align="right">积分类型</td>
						<td><s:select id="ptClassId" name="cardSubClassDef.ptClass"
								headerKey="" headerValue="--请选择--" list="ponitClassList"
								listKey="ptClass" listValue="className" disabled="true"></s:select>
							<span class="field_tipinfo">请选择</span></td>
						<td id="ptOpencardId_td1" width="80" height="30" align="right"
							style="display: none;">开卡积分</td>
						<td id="ptOpencardId_td2" style="display: none;"><s:textfield
								id="ptOpencardId" name="cardSubClassDef.ptOpencard"
								cssClass="{required:true, digit:true}" disabled="true" /> <span
							class="field_tipinfo">输入非空数字</span></td>
					</tr>
					<tr id="discntClassId_tr">
						<td width="80" height="30" align="right">折扣类型</td>
						<td><s:select id="discntClassId"
								name="cardSubClassDef.discntClass" headerKey=""
								headerValue="--请选择--" list="discntClassList"
								listKey="discntClass" listValue="className" disabled="true"></s:select>
							<span class="field_tipinfo">请选择</span></td>
						<td width="80" height="30" align="right">次数卡类型</td>
						<td><s:select id="accuClassId"
								name="cardSubClassDef.frequencyClass" headerKey=""
								headerValue="--请选择--" list="accuClassList" listKey="accuClass"
								listValue="className" disabled="true"></s:select> <span
							class="field_tipinfo">请选择</span></td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">卡片抵用金</td>
						<td><s:textfield name="cardSubClassDef.cardPrice"
								cssClass="{required:true, num:true, decimal:'12,2'}"
								value="0.00" /> <span class="field_tipinfo">卡的工本费</span> <span
							class="error_tipinfo">输入金额</span></td>

						<td width="80" height="30" align="right">售卡面值能否修改</td>
						<td><s:select id="id_changeFaceValue"
								name="cardSubClassDef.changeFaceValue" list="yesOrNoList"
								listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">请选择是或否</span></td>
						<!-- 
							<td width="80" height="30" align="right">最高购买价格</td>
							<td>
								<s:textfield name="cardSubClassDef.buyPriceMax" cssClass="{num:true}"/>
								<span class="field_tipinfo">请输入购买价格最高值</span>
								<span class="error_tipinfo">必须为数字</span>
							</td>-->
					</tr>
					<!-- 
						<tr>
							<td width="80" height="30" align="right">最低购买价格</td>
							<td>
								<s:textfield name="cardSubClassDef.buyPriceMin" cssClass="{num:true}"/>
								<span class="field_tipinfo">请输入购买价格最低值</span>
								<span class="error_tipinfo">该项必须为数字</span>
							</td>
						</tr>
						<tr id="chkPwdId_tr" style="display: none;">
							
							<td id="pwdId_td1" width="80" height="30" align="right" style="display: none;">密码</td>
							<td id="pwdId_td2" style="display: none;">
								<s:textfield id="pwdId" name="cardSubClassDef.pwd" cssClass="{required:true, digit:true,minlength:6}" maxlength="6" disabled="true"/>
								<span class="field_tipinfo">请输入6位数字</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">续卡最高值</td>
							<td>
								<s:textfield name="cardSubClassDef.extenUlimit" cssClass="{num:true}"/>
								<span class="field_tipinfo">请输入续卡费用最高值</span>
								<span class="error_tipinfo">该项必须为数字</span>
							</td>
							<td width="80" height="30" align="right">续卡最低值</td>
							<td>
								<s:textfield name="cardSubClassDef.extenLlimit" cssClass="{num:true}"/>
								<span class="field_tipinfo">请输入续卡费用最低值</span>
								<span class="error_tipinfo">该项必须为数字</span>
							</td>
						</tr>
						 -->
					<tr>
						<td width="80" height="30" align="right">预制卡检查</td>
						<td><s:select id="id_chkPfcard"
								name="cardSubClassDef.chkPfcard" list="trueOrFalseList"
								listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">是否检查？</span> <span
							class="error_tipinfo">该项非空</span></td>
						<td width="80" height="30" align="right">自动销卡标志</td>
						<td><s:select id="id_autoCancelFlag"
								name="cardSubClassDef.autoCancelFlag" list="trueOrFalseList"
								listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">是否自动销卡？</span> <span
							class="error_tipinfo">该项不能为空</span></td>
					</tr>

					<tr>
						<td width="80" height="30" align="right">卡内额度上限</td>
						<td><s:textfield name="cardSubClassDef.creditUlimit"
								cssClass="{num:true, decimal:'12,2'}" value="0.00" /> <span
							class="field_tipinfo">卡内额度上限</span> <span class="error_tipinfo">该项为(12,2)</span>
						</td>
						<td width="80" height="30" align="right">单笔充值上限</td>
						<td><s:textfield name="cardSubClassDef.chargeUlimit"
								cssClass="{num:true, decimal:'12,2'}" value="0.00" /> <span
							class="field_tipinfo">单笔充值上限</span> <span class="error_tipinfo">该项为(12,2)</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">单笔消费上限</td>
						<td><s:textfield name="cardSubClassDef.consmUlimit"
								cssClass="{num:true, decimal:'12,2'}" value="0.00" /> <span
							class="field_tipinfo">单笔消费上限</span> <span class="error_tipinfo">该项为(12,2)</span>
						</td>
						<td width="80" height="30" align="right">绝对失效日期</td>
						<td><s:textfield name="cardSubClassDef.mustExpirDate"
								onclick="WdatePicker({startDate:'{%y+20}1231',minDate:'%y-%M-%d'})"
								cssStyle="width:68px;" value="20991231" /> <span
							class="field_tipinfo">为空时默认为20991231</span></td>
					</tr>

					<tr>
						<td width="80" height="30" align="right">卡延期(月数)</td>
						<td><s:textfield name="cardSubClassDef.extenPeriod"
								cssClass="{digit:true, required:true}" maxlength="4" value="12" />
							<span class="field_tipinfo">卡延期(月数)</span> <span
							class="error_tipinfo">必须为数字</span></td>
						<td width="80" height="30" align="right">最大延期次数</td>
						<td><s:textfield name="cardSubClassDef.extenMaxcnt"
								cssClass="{digit:true, required:true}" maxlength="4" value="12" />
							<span class="field_tipinfo">最大延期次数？</span> <span
							class="error_tipinfo">必须为数字</span></td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">卡片类型</td>
						<td><s:select id="id_icType" name="cardSubClassDef.icType"
								list="cardFlagList" listKey="value" listValue="name"
								cssClass="{required:true}"></s:select> <span
							class="field_tipinfo">请选择卡片类型</span></td>
						<td id="id_icModelNo_td1" style="display: none;">IC卡模型</td>
						<td id="id_icModelNo_td2" style="display: none;"><s:select
								id="id_icModelNo" name="cardSubClassDef.icModelNo"
								list="modelList" listKey="modelNo" listValue="modelDescribe"
								cssClass="{required:true}" disabled="true"></s:select></td>
					</tr>
					<tr id="id_IC_Config_tr1" style="display: none;">
						<td width="80" height="30" align="right">电子现金余额</td>
						<td><s:textfield id="id_ecashbalance"
								name="icTempPara.ecashbalance"
								cssClass="{num:true, decimal:'12,2', required:true}"
								value="0.00" disabled="true" /> <span class="field_tipinfo">请输入金额</span>
							<span class="error_tipinfo">该项为(12,2)</span></td>
						<td width="80" height="30" align="right">电子现金余额上限</td>
						<td><s:textfield id="id_balanceLimit"
								name="icTempPara.balanceLimit"
								cssClass="{num:true, decimal:'12,2', required:true}"
								value="0.00" disabled="true" /> <span class="field_tipinfo">请输入金额</span>
							<span class="error_tipinfo">该项为(12,2)</span></td>
					</tr>
					<tr id="id_IC_Config_tr2" style="display: none;">
						<td width="80" height="30" align="right">电子现金单笔交易限额</td>
						<td><s:textfield id="id_amountLimit"
								name="icTempPara.amountLimit"
								cssClass="{num:true, decimal:'12,2', required:true}"
								value="0.00" disabled="true" /> <span class="field_tipinfo">单笔交易限额</span>
							<span class="error_tipinfo">该项为(12,2)</span></td>
						<!-- 
							<td width="80" height="30" align="right">是否开卡检测</td>
							<td>
								<s:select id="id_onlineCheck" name="icTempPara.onlineCheck" list="trueOrFalseList" listKey="value" listValue="name" 
								 cssClass="{required:true}" disabled="true"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
							 -->
					</tr>
					<tr id="id_IC_Config_tr3" style="display: none;">
						<td width="80" height="30" align="right">是否自动圈存</td>
						<td><s:select id="id_autoDepositFlag"
								name="icTempPara.autoDepositFlag" list="yesOrNoList"
								listKey="value" listValue="name" cssClass="{required:true}"
								disabled="true"></s:select> <span class="field_tipinfo">请选择</span>
						</td>
						<td width="80" height="30" align="right">自动圈存金额</td>
						<td><s:textfield id="id_autoDepositAmt"
								name="icTempPara.autoDepositAmt"
								cssClass="{num:true, decimal:'12,2', required:true}"
								value="0.00" disabled="true" /> <span class="field_tipinfo">自动圈存金额</span>
							<span class="error_tipinfo">该项为(12,2)</span></td>
					</tr>
					<tr>
						<td id="ecouponTypeTd1" width="40" height="30" align="right">电子消费券</td>
						<td id="ecouponTypeTd2"><s:select id="ecouponSel"
								headerKey="" headerValue="不使用电子消费券"
								name="cardSubClassDef.ecouponType" list="ecouponList"
								listKey="value" listValue="name" ></s:select>
							<span class="field_tipinfo">是否使用电子消费券及类型？</span></td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30" colspan="3"><input type="button" value="上一步"
							onclick="gotoUrl('/cardSubClass/preShowAdd.do?cardSubClassDef.cardIssuer=${cardSubClassDef.cardIssuer}')" />
							<input type="button" value="提交" id="input_btn2" name="ok"
							onclick="return check();" class="ml30" /> <input type="button"
							value="清除" name="escape" onclick="FormUtils.reset('inputForm')"
							class="ml30" /> <input type="button" value="返回" name="escape"
							onclick="gotoUrl('/cardSubClass/list.do?goBack=goBack')"
							class="ml30" /></td>
					</tr>

				</table>
				<s:token name="_TOKEN_CARDSUBCLASS_ADD" />
			</s:form>
		</div>

		<div class="contentb">
			<span class="note_div">注释</span>
			<ul class="showli_div">
				<!-- 
					<li class="showli_div">卡子类型号：由数字和英文字母组成，最长8位，系统内部使用它来标记卡类型。例如：广州沃尔玛会员卡可以使用GZWEMHYK作为卡类型号。</li>
					 -->
				<li class="showli_div">卡子类型名称：最大长度为128个字符，汉字算两个字符，英文或数字算一个字符。系统对外使用该名称来显示卡类型。例如，广州沃尔玛会员卡。</li>
				<li class="showli_div">卡类型：表示该卡类型的主要功能，一个卡类型除了有主功能(例如储值卡)，它也可以有其他次要功能，例如积分功能、折扣功能。赠券卡比较特殊，它只能做主功能，而且不能同时拥有其他次要功能。</li>
				<li class="showli_div">参考面值：默认为0，在售卡的时候也作为默认开卡金额使用，默认为0。此外，参考面值也写进制卡文件中，可能在制卡的时候有用。</li>
				<li class="showli_div">卡片抵用金：卡片的工本费，退卡的时候需要退卡片抵用金给持卡人。默认为0。</li>
				<li class="showli_div">密码类型：如果选择固定密码，那么初始密码为六个1</li>
				<li class="showli_div">预制卡检查：如果设置此标志，那么该种卡开卡时需要检查预制卡资料表，对于由本系统产生制卡资料的卡，应做制卡资料验证检查。</li>
				<li class="showli_div">自动销卡标志：如果设置此标志，余额为0的时候系统自动销卡。</li>
			</ul>
		</div>
		<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
	</div>

	<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
</body>
</html>