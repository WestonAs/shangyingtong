$(document).ready(function(){
	$('#input_btn2').attr('disabled', 'true');
	$('#defaultAmt').attr('readonly', 'true').addClass('readonly');
	
	$('#cardId').change(function(){
		$('#id_EndCardId').val('');
		$('#realAmt').val('');
		$('#rebateAmt').val('');
		$('#id_FeeAmt').val('');
		
		var cardId = $('#cardId').val();
		if (cardId == null || cardId == undefined || cardId.length < 18){
			$('#idCardCustomerId_sel').bind('focus', customerIdBlur);
			showMsg('起始卡号不得少于18位');
			return;
		}
		if(!isEmpty($('#id_CardCount').val())){
			findEndCardInfo();
		}
	});
	
	$('#id_CardCount').change(function(){
		$('#id_EndCardId').val('');
		$('#realAmt').val('');
		$('#rebateAmt').val('');
		$('#id_FeeAmt').val('');
		
		var cardCount = $('#id_CardCount').val();
		if(isEmpty(cardCount) || parseInt(cardCount) <= 0){
			showMsg('卡连续数必须大于0');
			return;
		}
		var cardId = $('#cardId').val();
		if (cardId == null || cardId == undefined || cardId.length < 18){
			$('#idCardCustomerId_sel').bind('focus', customerIdBlur);
			return;
		}
		findEndCardInfo();
	});
	
	$('#idCardCustomerId_sel').focus(customerIdBlur);
	
	$('#rebateType').change(function(){
		var value = $(this).val();
		if (value == '2'){	// 返利指定卡
			$('#idRebateCardTr').show();
			$('td[id^="idRebateCardTd"]').show();
			$('#idRebateCardId').removeAttr('disabled');
			
			$('td[id^="idRebateCountTd"]').hide();
			$('#idRebateCountId').attr('disabled', 'true');
			$('tr[id^="idRebateDetail_"]').remove();
			$('#idTip').hide();
			$('#idRebateTitle').hide();
		} else if (value == '4'){	// 返利多张卡
			$('#idRebateCardTr').show();
			$('td[id^="idRebateCardTd"]').hide();
			$('#idRebateCardId').attr('disabled', 'true');
			
			$('td[id^="idRebateCountTd"]').show();
			$('#idRebateCountId').removeAttr('disabled').val('');
			
			$('#rebateAmt').addClass('readonly').attr('readonly', 'true');
		} else if (value == '0'){	// 手工
			$('#idRebateCardTr').hide();
			$('td[id^="idRebateCardTd"]').hide();
			$('#idRebateCardId').attr('disabled', 'true');
			
			$('td[id^="idRebateCountTd"]').hide();
			$('#idRebateCountId').attr('disabled', 'true');
			$('tr[id^="idRebateDetail_"]').remove();
			$('#idTip').hide();
			$('#idRebateTitle').hide();
			
			$('#rebateAmt').addClass('readonly').attr('readonly', 'true');
		} else {
			$('#idRebateCardTr').hide();
			$('td[id^="idRebateCardTd"]').hide();
			$('#idRebateCardId').attr('disabled', 'true');
			
			$('td[id^="idRebateCountTd"]').hide();
			$('#idRebateCountId').attr('disabled', 'true');

			$('tr[id^="idRebateDetail_"]').remove();
			$('#idTip').hide();
			$('#idRebateTitle').hide();
		}
		
		if (FormUtils.getRadioedValue('calcMode') == '1' && value != '0') {
			$('#rebateAmt').removeClass('readonly').removeAttr('readonly');
		}
	});
	
	$(':radio[name="calcMode"]').click(function(){
		var value = FormUtils.getRadioedValue('calcMode');
		if (value == '1'){
			$('#expenses').removeClass('readonly').removeAttr('readonly');
			$('#realAmt').removeClass('readonly').removeAttr('readonly');
			if (isDisplay('rebateType') && $('#rebateType').val() == '0') {
				$('#rebateAmt').addClass('readonly').addClass('readonly');
			} else {
				$('#rebateAmt').removeClass('readonly').removeAttr('readonly');
			}
		} else {
			calRealAmt();
			$('#expenses').addClass('readonly').attr('readonly', 'true');
			$('#realAmt').addClass('readonly').attr('readonly', 'true');
			$('#rebateAmt').addClass('readonly').attr('readonly', 'true');
		}
	});
	// 按次充值可编辑金额
	if($("#depositTypeIsTimes").val()=='true'){
		$('#realAmt').removeClass('readonly').removeAttr('readonly');
	}
	SysStyle.setFormGridStyle();
	
	$('#idRebateCountId').blur(loadDefaultRebateTable);
	
	// 点击赠送赠券调用
	$('#isDepositCoupon').click(changeIsDepositCoupon);
	
	changeIsDepositCoupon(); // 默认初始化
});

function changeIsDepositCoupon() {
	var hasChecked = $("#isDepositCoupon").attr('checked');
	if (hasChecked) {
		$("#depositCouponTr").show();
		$("#depositCouponTr input").removeAttr("disabled");
		ajaxLoadCouponClass();
	} else {
		$("#depositCouponTr").hide();
		$("#depositCouponTr input").attr("disabled","disabled");
	}
}


function ajaxLoadCouponClass(){
	$("#couponClassSel").val("").empty(); //清空赠券类型
	if (isEmpty($("#cardId").val())) {
		return;
	}
	$.getJSON(CONTEXT_PATH + "/ajax/ajaxFindCouponClass.do",{'formMap.cardId':$("#cardId").val(), 'callId':callId()}, 
		function(json){
			$("#couponClassSel").append("<option value=''>--请选择--</option>");
			var couponClassList = json.couponClassList;
			if(couponClassList!=null && couponClassList.length>0){
				
				for(i in couponClassList){
					var option = "<option value='"+couponClassList[i].coupnClass +"'> "
						+couponClassList[i].className+"</option>";
					$("#couponClassSel").append(option);
				}
			}
		}
	);
}

/** 加载返利卡列表 */
function loadDefaultRebateTable(){
	var defaultLevel = $('#idRebateCountId').val();
	if(isEmpty(defaultLevel)){
		return false;
	}
	// 级别数大于等于1，小于等于10
	if(defaultLevel < 2 || defaultLevel > 20){
		showMsg("返利卡张数应大于等于2，小于等于20。");
		return false;
	}
	
	// 根据级别数显示相应数目的输入框
	$('#idWorkflowDefineTbl').show();
	$('#idTip').show();
	$('#idRebateTitle').show();
	$('tr[id^="idRebateDetail_"]').remove();
	var trCount = Math.floor((parseInt(defaultLevel) + 1)/2);
	for(var j = 1; j <= trCount; j++) {
		var trStart = '<tr id="idRebateDetail_'+ (j) +'">';
		var content = ""; 
		var nums = (j - 1)*2;
		if(j == trCount && (parseInt(defaultLevel) % 2 == 1)){
			content = '<td align="center">'+ (nums + 1) +'</td>' 
						+ '<td align="left"><input type="text" name="rebateCardIds" maxlength="19"/></td>' 
						+ '<td align="left"><input type="text" name="rebateAmts" /></td>'
						+ '<td align="center"></td>'
						+ '<td align="left"></td>' 
						+ '<td align="left"></td>';
		} else {
			content = '<td align="center">'+ (nums + 1) +'</td>' 
						+ '<td align="left"><input type="text" name="rebateCardIds" maxlength="19"/></td>' 
						+ '<td align="left"><input type="text" name="rebateAmts" /></td>'
						+ '<td align="center">'+ (nums + 2) +'</td>'
						+ '<td align="left"><input type="text" name="rebateCardIds" maxlength="19"/></td>' 
						+ '<td align="left"><input type="text" name="rebateAmts" /></td>';
		}
		var trEnd = "</tr>";
		$('#idWorkflowDefineTbl').append(trStart + content + trEnd);
	}

	// 设置样式
	SysStyle.setFormGridStyle();
	
	$('tr[id^="idRebateDetail_"]').find(':text[name="rebateAmts"]').addClass('u_half');
}

function checkRebateField(){
	var count = $('tr[id^="idRebateDetail_"]').size();
	var defaultLevel = parseInt($('#idRebateCountId').val());
	for(i=0; i < count; i++){
		var $obj = $('tr[id^="idRebateDetail_"]').eq(i);
		
		if(i == (count-1) && (parseInt(defaultLevel) % 2 == 1)){
			var $rebateNum1 = $obj.children().eq(0);
			var $rebateCard1 = $obj.children().eq(1).children();
			var $rebateAmt1 = $obj.children().eq(2).children();
			if(isEmpty($rebateCard1.val())){
				showMsg("第[" + $rebateNum1.html() + "]张返利卡号不能为空。");
				return false;
			}
			if(isEmpty($rebateAmt1.val())){
				showMsg("第[" + $rebateNum1.html() + "]张返利金额不能为空。");
				return false;
			}
		} else {
			var $rebateNum1 = $obj.children().eq(0);
			var $rebateCard1 = $obj.children().eq(1).children();
			var $rebateAmt1 = $obj.children().eq(2).children();
			
			var $rebateNum2 = $obj.children().eq(3);
			var $rebateCard2 = $obj.children().eq(4).children();
			var $rebateAmt2 = $obj.children().eq(5).children();
			if(isEmpty($rebateCard1.val())){
				showMsg("第[" + $rebateNum1.html() + "]张返利卡号不能为空。");
				return false;
			}
			if(!checkCardNum($rebateCard1.val())){
				showMsg("第[" + $rebateNum1.html() + "]张返利卡号错误。");
				return false;
			}
			if(isEmpty($rebateAmt1.val()) || !is142Num($rebateAmt1.val())){
				showMsg("请按照Number(14,2)格式输入第[" + $rebateNum1.html() + "]张卡返利金额。");
				return false;
			}
			if(isEmpty($rebateCard2.val())){
				showMsg("第[" + $rebateNum2.html() + "]张返利卡号不能为空。");
				return false;
			}
			if(!checkCardNum($rebateCard2.val())){
				showMsg("第[" + $rebateNum2.html() + "]张返利卡号错误。");
				return false;
			}
			if(isEmpty($rebateAmt2.val()) || !is142Num($rebateAmt2.val())){
				showMsg("请按照Number(14,2)格式第[" + $rebateNum2.html() + "]张卡的返利金额。");
				return false;
			}
		}
	}
	return true;
}

/** 
 * ajax查找第一张待售的卡号，并充填到开始卡号文本框 
 */
function ajaxFindFirstCardToSold(){
	$('#input_btn2').attr('disabled', 'true');
	$('#id_EndCardId').val('');
	$("#firstCardWaiterImg").show();
	$.post(CONTEXT_PATH + '/saleCardBatReg/ajaxFindFirstCardToSold.do', 
			{'callId':callId(),'cardType':$('#cardType').val()}, 
				function(json){
					if(json.firstCardToSold!=null && json.firstCardToSold!=""){
						$('#cardId').val(json.firstCardToSold);
						$('#cardId').focus();
						$('#cardId').change();
					}else{
						showMsg("未找到第一张待售的卡号，请手动填写！");
					}
					$("#firstCardWaiterImg").hide();
				}
			, 'json');
}

/**
 * 根据起始卡号和卡数量取得结束卡号
 */
function findEndCardInfo() {
	$('#input_btn2').attr('disabled', 'true');
	
	var cardId = $('#cardId').val();
	var cardCount = $('#id_CardCount').val();
	try {
		if(cardId.length == 19) {
			$('#defaultAmt').removeAttr('readonly').removeClass('readonly');
		} else {
			$('#defaultAmt').attr('readonly', 'true').addClass('readonly');
		}
		var cardType = $('#cardType').val();
		
		$('#idCardCustomerId_sel').removeMulitselector();
		$('#idCardCustomerId_sel').unbind();
		
		// 加载其他信息
		$("#endCardIdWaiterImg").show();
		$.ajax({
			url: CONTEXT_PATH + '/saleCardReg/calcCardOther.do',
			data: {'cardId':cardId, 'cardCount':cardCount, 'isBatFlag':'Y', 'cardType': cardType, 'callId':callId()},
			type: 'POST',
			dataType: 'json',
			success: function(json){
				if(json.success){
					if(json.isModifyFaceValue){
						$('#defaultAmt').removeAttr('readonly').removeClass('readonly');
					} else {
						$('#defaultAmt').attr('readonly', 'true').addClass('readonly');
					}
					$('#defaultAmt').val(fix(json.amt));
					$('#defaultExpense').val(fix(json.expenses));
					$('#cardBin').val(json.cardBin);
					$('#id_EndCardId').val(json.endCardId);
					$('#cardBinName').val(json.cardBinName);
					$('#cardBranchName').val(json.cardBranchName);
					$('#idCardCustomerId').val(json.cardCustomerId);
					$('#idCardCustomerId_sel').val(json.cardCustomerName);
					loadRebateRule();
					Selector.selectCardCustomer('idCardCustomerId_sel', 'idCardCustomerId', true, $('#cardType').val(), json.cardBin, loadRebateRule);
					
					ajaxLoadCouponClass();
					
				} else {
					$('#input_btn2').attr('disabled', 'true');
					showMsg(json.errorMsg);
				}
				$("#endCardIdWaiterImg").hide();
			},
			error: function(){
				showMsg("访问异常");
				$("#endCardIdWaiterImg").hide();
			}
		});
	} catch (e){}
}

function customerIdBlur(){
	var cardId = $("#cardId").val();
	if (cardId == null || cardId == undefined || cardId.length < 18){
		showMsg('请先输入正确的卡号再选择购卡客户');
		return;
	}
}

function loadRebateRule(){
	var cardId = $("#cardId").val();
	var cardCustomerId = $('#idCardCustomerId').val();
	if(isEmpty(cardCustomerId)){
		return;
	}
	$.post(CONTEXT_PATH + '/saleCardReg/findCustomerRebateType.do', 
		{'cardCustomer.cardCustomerId':cardCustomerId, 'callId':callId()}, function(json){
		if (json.choose){
			$('#idRebateTypeTr').show();
			$('#rebateType').removeAttr('disabled').val(json.rebateType);
		} else {
			$('#idRebateTypeTr').hide();
			$('#rebateType').attr('disabled', 'true');
		}
	}, 'json');
	
	if(cardId == undefined || cardId=="") {return;}
	if(cardCustomerId == undefined || cardCustomerId==""){return;}
	
	$jload('idRebateRulePage', '/saleCardReg/findRebateRule.do', 
		{'cardId':cardId, 'cardCustomer.cardCustomerId':cardCustomerId, 'formMap.ablePeriod':false, 'callId':callId()});
	$('#idCardInfoTbl').show();
}

//格式：Number(14,2)
function is142Num(strNum){
	var result1 =  strNum.match(/^\d{0,12}\.\d{0,2}$/);
	var result2 =  strNum.match(/^\d{0,14}$/);
	if(result1 == null && result2 == null){
		return false;
	}else{
		return true;
	}
}

// 校验数据有效性
function checkForm(){
	
	if(!checkRebateField()){
		return false;
	}
	
	if ($("#isDepositCoupon").attr('checked')) {// 选择了 赠送赠券
		if (isEmpty($("#couponClassSel").val())
				|| isEmpty($('#couponAmt').val())) {
			showMsg('选择了赠送赠券，赠券类型与赠券金额都不能为空');
			return false;
		} else if (!is142Num($('#couponAmt').val())) {
			showMsg('赠券金额格式错误');
			return false;
		}
	}
	
	// 数据有效性检查
	//if(!checkCard()) return false;
	if (isEmpty($('#realAmt').val())){
		showMsg('实收金额不能为空，请重新计算费用');
		return false;
	}
	if (isEmpty($('#rebateAmt').val())){
		showMsg('返利金额不能为空，请重新计算费用');
		return false;
	}
	if (isEmpty($('#amt').val())){
		showMsg('售卡金额合计不能为空，请重新计算费用');
		return false;
	}
	var signatureReg = $('#id_signatureReg').val();
	if(signatureReg == 'true'){
		if(!CheckUSBKey()){
			return false;
		}
	}
	//if($('#inputForm').validate().form()){
		$('#inputForm').submit();
		$('#input_btn2').attr('disabled', 'true');
		//window.parent.showWaiter();
		$("#loadingBarDiv").css("display","inline");
		$("#contentDiv").css("display","none");
	//}
}

function CheckUSBKey() {
	var USBKeyFlag = 0; //USBKeyFlag＝0：科友的key；USBKeyFlag＝1：非科友的key

    try{
		//捷德的key不在线，检查飞天的key
		var isOnline = document.all.FTUSBKEYCTRL.IsOnline;
		if (isOnline == 0) {
			if(FTDoSign()){ //调用FT的签名函数
				return true;
			} else {
				return false;
			}
		} else {
			showMsg("请检查USB Key是否插入或USB Key是否正确！");
			return false;
	    }
	} catch(ex) {
		alert(ex);
	}
	return true;
}

/*飞天的Key的签名函数*/
function FTDoSign()	{
	var SetCertResultRet = document.all.FTUSBKEYCTRL.SetCertResult; //选择证书
	if (SetCertResultRet == 0) {
   		var randomNum = $('#id_RandomSessionId').val();
		var cardId = $('#cardId').val();
		var realAmt = $('#realAmt').val();
		
		var serialNumber = document.all.FTUSBKEYCTRL.GetCertSerial;
		$('#id_serialNo').val(serialNumber);
      
        var signResultRet = document.all.FTUSBKEYCTRL.SignResult(realAmt + randomNum);
		if (signResultRet == 0) {
			var signData = document.all.FTUSBKEYCTRL.GetSignature;
			$('#id_Signature').val(signData);
		} else {
			showMsg("签名失败");
			return false;
		}
	} else {
		showMsg("选择证书失败");
		return false;
	}
	return true;
}

// 服务端计算返利金额、实收金额
function calRealAmt(){
	$('#input_btn2').attr('disabled', 'true');
	var depositTypeIsTimes = $("#depositTypeIsTimes").val();
	$('#idCardInfoTbl').show();
	// 数据有效性检查
	if(!checkCard()) {
		return false;
	}

	var cardCustomerId = $('#idCardCustomerId').val();
	var saleRebateId = FormUtils.getRadioedValue('rebateIdRadio');
	$('#idRebateId').val(saleRebateId);
	var rebateType = $('#rebateType').val();
	if(isEmpty(rebateType)){
		showMsg('请选择返利方式');
		return false;
	}
	
	var startCardId = $('#cardId').val(); // 起始卡号
	var cardCount = $('#id_CardCount').val(); //卡连续数
	var amt = $('#defaultAmt').val(); //单张售卡金额
	var expenses = $('#defaultExpense').val(); //工本费
	var feeRate = $('#id_FeeRate').val(); //手续费比例
	
	var type = 1;	// type默认指按金额充值，为0时按次充值
	if(depositTypeIsTimes=='true'){
		type = 0;
	}
	if(isEmpty(feeRate)){
		showMsg('手续费比例不能为空');
		return;
	}
	try{
		$('#idHiddenElementTd').load(CONTEXT_PATH + '/saleCardBatReg/calRealAmt.do', 
			{'saleCardReg.cardCustomerId':cardCustomerId, 'saleCardReg.rebateId':saleRebateId, 
			'startCardId':startCardId, 'cardCount':cardCount, 'amt':amt, 'expenses':expenses, 'saleCardReg.feeRate' : feeRate,
			'rebateType':rebateType, 'depositTypeIsTimes':depositTypeIsTimes, 'type':type, 'callId':callId()}, 
			function(data){
				$('#amt').val(fix($('#resultSumAmt').val()));
				$('#expenses').val(fix($('#resultSumExpenses').val()));
				$('#realAmt').val(fix($('#resultSumRealAmt').val()));
				$('#rebateAmt').val(fix($('#resultSumRebateAmt').val()));
				$('#id_FeeAmt').val(fix($('#resultFeeAmt').val()));// 清算金额*手续费费率
				
				$('#input_btn2').removeAttr('disabled');
				
				if(depositTypeIsTimes=='true'){
					$('#realAmt').removeClass('readonly').removeAttr('readonly');
				}
		});
	}catch(err){
		txt="本页中存在错误。\n\n 错误描述：" + err.description + "\n\n"
		showMsg(txt)
	}
}

// 校验卡数据
function checkCard(){
	try{
		if(isEmpty($('#cardId').val())){
			showMsg('"起始卡号"不能为空，请重新输入！');
			return false;
		}
		if(isEmpty($('#id_CardCount').val())){
			showMsg('"卡连续数"不能为空，请重新输入！');
			return false;
		}
		if(isEmpty($('#id_EndCardId').val())){
			showMsg('"结束卡号"不能为空，请重新匹配！');
			return false;
		}
		var sellAmt = $('#defaultAmt').val(); //单张售卡金额
		if(isEmpty(sellAmt)){
			showMsg('"单张售卡金额"不能为空，请重新输入！');
			return false;
		} else {
			if(!is142Num(sellAmt)){
				showMsg('请按照Number(14,2)格式输入"单张售卡金额"！');
				return false;
			}
		}
		var expenses = $('#defaultExpense').val(); //工本费
		if(isEmpty(expenses)){
			showMsg('"单张工本费"不能为空，请重新输入！');
			return false;
		} else {
			if(!is142Num(expenses)){
				showMsg('请按照Number(14,2)格式输入"单张工本费"！');
				return false;
			}
		}
		if(isEmpty($('#idCardCustomerId').val())){
			showMsg('请选择"购卡客户"！');
			return false;
		}
		var saleRebateId = FormUtils.getRadioedValue('rebateIdRadio');
		if(isEmpty(saleRebateId)){
			showMsg('请选择"返利规则"！');
			return false;
		}
		
		return true;
	}catch(err){
		txt="本页中存在错误。\n\n";
		txt+="错误描述：" + err.description + "\n\n";	
		showMsg(txt);
	}	
}
