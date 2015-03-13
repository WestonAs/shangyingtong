$(document).ready(function(){
	$('#input_btn2').attr('disabled', 'true');
	
	$("#id_strCardId").focus();
	
	$(':radio[name="timesDepositType"]').click(function(){
		var value = $(this).val();
		if (value == '0'){
			$('#idAmtLable1').html('单张充值次数');
		} else if (value == '1'){
			$('#idAmtLable1').html('单张充值金额');
		}
		var cardId = $('#id_strCardId').val();
		if (isEmpty(cardId)){
			return;
		}
		calRealAmt();
	});
	
	$('#id_strCardId').change(findCardInfo);
	$('#id_CardCount').change(findCardInfo);
	$('#id_Amt').change(calRealAmt);
	
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
			$('#realAmt').removeClass('readonly').removeAttr('readonly');
			
			if (isDisplay('rebateType') && $('#rebateType').val() == '0') {
				$('#rebateAmt').addClass('readonly').attr('readonly', 'true');
			} else {
				$('#rebateAmt').removeClass('readonly').removeAttr('readonly');
			}
		} else {
			calRealAmt();
			$('#realAmt').addClass('readonly').attr('readonly', 'true');
			$('#rebateAmt').addClass('readonly').attr('readonly', 'true');
		}
	});
	
	$('#idRebateCountId').blur(loadDefaultRebateTable);
})

function findCardInfo(){
	try {
		// 起始卡号
		var cardId = $('#id_strCardId').val();
		// 卡数量
		var cardCount = $('#id_CardCount').val();
		var cardType = $('#id_cardType').val();
	
		if (!checkCardNum(cardId)){
			showMsg("请输入至少18位的数字");
			return;
		}
		if(isEmpty(cardType)) {
			return;
		}
		
		if(isEmpty(cardCount)){
			return;
		}
		
		if(!validator.isDigit(cardCount)){
			return;
		}
		
		hideMsg();
		// 加载其他信息
		$.post(CONTEXT_PATH + '/depositReg/searchCardInfo.do', {'depositReg.cardId':cardId, 'cardType':cardType, 'cardCount':cardCount, 'callId':callId()}, 
		  function(data){
			$('#cardBranchName').val(data.resultBranchName);
			$('#cardSubClass').val(data.resultCardSubClass);
			$('#cardSubClassName').val(data.resultCardSubClassName);
			$('#cardCustomerId').val(data.resultCardCustomerId);
			$('#groupCardID').val(data.resultGroupCardID);
			$('#cardCustomerName').val(data.resultCardCustomerName);
			$("#cardClass").val(data.resultCardClass);
			$("#cardClassName").val(data.resultCardClassName);
			$("#cardBin").val(data.resultCardBin);
			$("#id_endCardId").val(data.resultEndCardId);
			
			// 如果是客户选择返利的则需要显示下拉框
			if (data.resultRebateType == 1){
				$('#idRebateTypeTr').show();
				$('td[id^="idRebateTypeTd"]').show();
				$('#rebateType').removeAttr('disabled');
			} else {
				$('#idRebateTypeTr').show();
				$('td[id^="idRebateTypeTd"]').hide();
				$('#rebateType').attr('disabled', 'true');
			}
			
			var resultMessage = data.resultMessage;
			$('#rebateAmt').val("");
			$('#realAmt').val("");

			if(resultMessage.length > 0){
				showMsg(resultMessage);
			} else {
				hideMsg();
				// 加载返利规则
				$jload('idRebateRulePage', '/depositReg/findRebateRule.do', 
					{'cardId':cardId, 'cardCustomer.cardCustomerId':data.resultCardCustomerId, 'fromMap.ablePeriod':false, 'callId':callId()});
			}
		}, 'json');
	} catch (e){}
}

/**
 *  根据返利卡的张数，得到返利卡号的输入框(对于多张卡返利)
 */
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

/*
 * 检查返利数据(对于多张卡返利)
 */
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

// 服务端计算返利金额、实收金额
function calRealAmt(){
	$('#input_btn2').attr('disabled', 'true');
	var depositTypeIsTimes = $("#depositTypeIsTimes").val();
	var rebateId = FormUtils.getRadioedValue('rebateIdRadio');
	$('#rebateId').val(rebateId);
	
	// 检查数据
	if(!checkField(false)) return false;
	
	$('#idDepositTbl').show();
				
	var cardCustomerId = $("#cardCustomerId").val();
	var rebateType = $("#rebateType").val();
	var calType = $("#calType").val();
	var binNo = $("#cardBin").val();
	var cardId = $('#id_strCardId').val();
	
	var feeRate = $('#id_FeeRate').val(); //手续费比例
	
	var type = 1;	// type默认指按金额充值，为0时按次充值
	if($("#depositTypeIsTimes").val()=='true'){
		type = FormUtils.getRadioedValue('timesDepositType');
	}
	
	var startCardId = new Array(1);
	var cardCount = new Array(1);
	var amt = new Array(1);

	try{
		startCardId[0] = $('#id_strCardId').val();
		cardCount[0] = $('#id_CardCount').val();
		amt[0] = $('#id_Amt').val();

		$('#idHiddenElementTd').load(CONTEXT_PATH + '/depositBatReg/calRealAmt.do',
			{'depositReg.cardCustomerId':cardCustomerId, 'depositReg.rebateId':rebateId, 
			'depositReg.rebateType':rebateType, 'depositReg.calType':calType, 'binNo':binNo, 'depositReg.feeRate' : feeRate,
			'startCardId':startCardId, 'cardCount':cardCount, 'amt':amt, 'type': type, 'cardId': cardId, 'callId':callId()},
			function(){
				var resultErrorMsg = $('#resultErrorMsg').val();
				if(resultErrorMsg.length > 0){
					showMsg(resultErrorMsg);
					return false;
				}
				$('#amt').val(fix($('#resultSumAmt').val()));
				$('#realAmt').val(fix($('#resultSumRealAmt').val()));
				$('#rebateAmt').val(fix($('#resultSumRebateAmt').val()));
				$('#id_feeAmt').val(fix($('#resultFeeAmt').val())); // 手续费
				$('#input_btn2').removeAttr('disabled');
				//showMsg($('#idHiddenElementTd').html());
				
				hideMsg();
				if(depositTypeIsTimes=='true'){
					$('#realAmt').removeClass('readonly').removeAttr('readonly');
				}
		});
	}catch(err){
		txt="本页中存在错误。\n\n 错误描述：" + err.description + "\n\n"
		showMsg(txt)
	}
}

/*
 * 检查录入数据完整性
 */
function checkField(b){
	// 后台加载的购卡客户ID不能为空
	var cardCustomerId = $("#cardCustomerId").val();
	if(cardCustomerId==""){
		return false;
	}
	
	// 返利规则ID不能为空 
	var rebateId = $("#rebateId").val();
	if(rebateId==""){
		return false;
	}
	
	// 起始卡号不能为空
	var strCardId = $('#id_strCardId').val();
	if(!checkCardNum(strCardId)){
		if(b){
			showMsg('"起始卡号"为空或格式错误！');
			$('#id_strCardId').focus();
		}
		return false;
	}
	
	// 卡连续数
	var cardCount = $('#id_CardCount').val();
	if(isEmpty(cardCount)){
		if(b) {
			showMsg('"卡连续张数"不能为空！');
			$('#id_CardCount').focus();
		}
		return false;
	}
	
	var depositAmt = $('#id_Amt').val();
	if(isEmpty(depositAmt)){
		if(b) {
			showMsg('"单张充值金额"不能为空！');
			$('#id_Amt').focus();		
		}
		return false;
	} else {
		if(!is142Num(depositAmt)){
			if(b) {
				showMsg('"单张充值金额"必须为Number(14,2)格式！');
				$('#id_Amt').focus();		
			}
			return false;
		}
	}
	
	hideMsg();
	return true;
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

function submitDepositForm(){
	try{
		if(!checkField(true)){
			return false;
		}
		if(!checkRebateField()){
			return false;
		}
		if (isEmpty($('#realAmt').val())){
			showMsg('实收金额不能为空，请重新计算费用');
			return false;
		}
		if (isEmpty($('#rebateAmt').val())){
			showMsg('返利金额不能为空，请重新计算费用');
			return false;
		}
		if (isEmpty($('#amt').val())){
			showMsg('充值合计金额不能为空，请重新计算费用');
			return false;
		}
		var signatureReg = $('#id_signatureReg').val();
		if(signatureReg == 'true'){
			if(!CheckUSBKey()){
				return false;
			}
		}
		
		if($('#inputForm').validate().form()){
			$('#inputForm').submit();
			$('#input_btn2').attr('disabled', 'true');
			//window.parent.showWaiter();
			$("#loadingBarDiv").css("display","inline");
			$("#contentDiv").css("display","none");
		}
	}catch(err){
		txt="本页中存在错误。\n\n"
		txt+="错误描述：" + err.description + "\n\n"
		showMsg(txt)
	}	
}

//---------------- USBKey签名 start ------------------------------------------
function CheckUSBKey() {
	var USBKeyFlag = 0; //USBKeyFlag＝0：科友的key；USBKeyFlag＝1：非科友的key

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
	return true;
}

/*飞天的Key的签名函数*/
function FTDoSign()	{
	var SetCertResultRet = document.all.FTUSBKEYCTRL.SetCertResult; //选择证书
	if (SetCertResultRet == 0) {
   		var randomNum = $('#id_RandomSessionId').val();
		var cardId = $('#id_strCardId').val();
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
//---------------- USBKey签名 end  --------------------------------------------

/* 旧的充值方法，现在要去掉
function addItem(){
	var count = $('tr[id^="idDetail_"]').size();
	//var content = '<tr id="idDetail_'+ (count+1) +'"><td align="center">'+ (count+1) +'</td><td align="center"><input type="text" name="rebateUlimit" value="" id="inputForm_rebateUlimit"/></td><td align="center" ><input type="text" name="rebateRate" value="" id="inputForm_rebateRate"/></td></tr>';
	var content = '<tr id="idDetail_'+ (count+1) +'"><td align="center">'+ (count+1) +'</td>' 
				+ '<td align="center"><input type="text" name="cardId" value="" id="inputForm_cardId" /></td>' 
				+ '<td align="center"><input type="text" name="cardCount" value="" id="inputForm_cardCount" /></td>' 
				+ '<td align="center"><input type="text" name="amt" value="" id="inputForm_amt" onchange="calRealAmt()"/></td>' 
				+ '</tr>';
	$('#idLink').before(content);

	// 设置样式
	SysStyle.setFormGridStyle();
}

function deleteItem(){
	var count = $('tr[id^="idDetail_"]').size();
	if (count == 1) {
		showMsg('必须设置一项');
		return;
	}
	$('tr[id^="idDetail_"]:last').remove();
}

function checkCardBin(cardId, binNo){
	var cardIdSize = cardId.length;
	var binNoSize = binNo.length;
	if(cardIdSize < 1 || binNoSize < 1 || !(binNo == cardId.substr(3,binNoSize))){
		return false;
	}
	return true;					
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

// 服务端计算返利金额、实收金额
function calRealAmt(){
	$('#input_btn2').attr('disabled', 'true');
	var depositTypeIsTimes = $("#depositTypeIsTimes").val();
	var rebateId = FormUtils.getRadioedValue('rebateIdRadio');
	$('#rebateId').val(rebateId);
	
	// 检查数据
	if(!checkField()) return false;
	
	// 检查返利数据
	if(!checkRebateField()){
		return false;
	}
	$('#idDepositTbl').show();
				
	var cardCustomerId = $("#cardCustomerId").val();
	var rebateId = $("#rebateId").val();
	var rebateType = $("#rebateType").val();
	var calType = $("#calType").val();
	var binNo = $("#cardBin").val();
	var count = $('tr[id^="idDetail_"]').size();
	var cardId = $('#cardId').val();
	
	var feeRate = $('#id_FeeRate').val(); //手续费比例
	
	var type = 1;	// type默认指按金额充值，为0时按次充值
	if($("#depositTypeIsTimes").val()=='true'){
		type = FormUtils.getRadioedValue('timesDepositType');
	}
	
	var startCardId = new Array(count);
	var cardCount = new Array(count);
	var amt = new Array(count);
	var expenses = new Array(count);
	try{
		for(i = 0; i < count; i++) {
			var $obj = $('tr[id^="idDetail_"]').eq(i);
			var $objStartCardId = $obj.children().eq(1).children();
			var $objCardCount = $objStartCardId.parent().next().children();
			var $objAmt = $objCardCount.parent().next().children();
			//var $objExpenses = $objAmt.parent().next().children();
			
			startCardId[i] = $objStartCardId.val();
			cardCount[i] = $objCardCount.val();
			amt[i] = $objAmt.val();
			//expenses[i] = $objExpenses.val();
		}

		$('#idHiddenElementTd').load(CONTEXT_PATH + '/depositBatReg/calRealAmt.do',
			{'depositReg.cardCustomerId':cardCustomerId, 'depositReg.rebateId':rebateId, 
			'depositReg.rebateType':rebateType, 'depositReg.calType':calType, 'binNo':binNo, 'depositReg.feeRate' : feeRate,
			'startCardId':startCardId, 'cardCount':cardCount, 'amt':amt, 'type': type, 'cardId': cardId},
			function(){
				var resultErrorMsg = $('#resultErrorMsg').val();
				if(resultErrorMsg.length > 0){
					showMsg(resultErrorMsg);
					return false;
				}
				$('#amt').val(fix($('#resultSumAmt').val()));
				$('#realAmt').val(fix($('#resultSumRealAmt').val()));
				$('#rebateAmt').val(fix($('#resultSumRebateAmt').val()));
				$('#id_feeAmt').val(fix($('#resultFeeAmt').val())); // 手续费
				$('#input_btn2').removeAttr('disabled');
				//showMsg($('#idHiddenElementTd').html());
				
				hideMsg();
				if(depositTypeIsTimes=='true'){
					$('#realAmt').removeClass('readonly').removeAttr('readonly');
				}
		});
	}catch(err){
		txt="本页中存在错误。\n\n 错误描述：" + err.description + "\n\n"
		showMsg(txt)
	}
}

// 检查录入数据完整性
function checkField(){
	var cardCustomerId = $("#cardCustomerId").val();
	if(cardCustomerId==""){
		return false;
	}
	var rebateId = $("#rebateId").val();
	if(rebateId==""){
		return false;
	}
	
	var rebateType = $("#rebateType").val();
	if(rebateType == '2'){ //返利指定卡
		var rebateCard = $('#idRebateCardId').val();
		if(!checkCardNum(rebateCard)){
			showMsg("指定的返利卡号为空或格式错误");
			return false;
		}
	} else if(rebateType == '4'){
		var rebateCount = $('#idRebateCountId').val();
		if(isEmpty(rebateCount)){
			showMsg("返利卡张数不能为空");
			return false;
		}
	}
	
	var count = $('tr[id^="idDetail_"]').size();
	var binNo = $('#cardBin').val();
	for(i=0; i<count; i++){
		var ii = i + 1;
		var $obj = $('tr[id^="idDetail_"]').eq(i);
		var $objStartCardId = $obj.children().eq(1).children();
		var $objCardCount = $objStartCardId.parent().next().children();
		var $objAmt = $objCardCount.parent().next().children();
		
		if($objStartCardId.val()==""){
			//showMsg('第 ' + ii + ' 段"起始卡号"不能为空，请重新输入！');
			showMsg('"起始卡号"不能为空，请重新输入！');
			$objStartCardId.select().focus();
			return false;
		}
		if(isNaN($objStartCardId.val())){
			//showMsg('第 ' + ii + ' 段"起始卡号"不是数字字符，请重新输入！');	
			showMsg('"起始卡号"不是数字字符，请重新输入！');	
			$objStartCardId.select().focus();			
			return false;
		}
		if ($objStartCardId.val().length != 19){
			if (b) showMsg('第 ' + ii + ' 段"起始卡号"必须为19位，请重新输入！');
			return false;
		}
		if(!checkCardBin($objStartCardId.val(), binNo)){
			showMsg('第 ' + ii + ' 段"起始卡号"的第 4 位起应包含卡BIN串，请重新输入！"');
			return false;
		}
		if($objCardCount.val()==""){
			//showMsg('第 ' + ii + ' 段"卡连续数"不能为空，请重新输入！');
			showMsg('"卡连续数"不能为空，请重新输入！');
			$objCardCount.select().focus();		
			return false;
		}
		if(isNaN($objCardCount.val())){
			//showMsg('第 ' + ii + ' 段"卡连续数"不是数字，请重新输入！');				
			showMsg('"卡连续数"不是数字，请重新输入！');				
			$objCardCount.select().focus();		
			return false;
		}
		if($objAmt.val()==""){
			//showMsg('第 ' + ii + ' 段"单张充值金额"不能为空，请重新输入！');
			showMsg('"单张充值金额"不能为空，请重新输入！');
			$objAmt.select().focus();		
			return false;
		}
		if($objAmt.val()!="" && !is142Num($objAmt.val())){
			//showMsg('请按照Number(14,2)格式输入第 ' + ii + ' 段"单张充值金额"！');
			showMsg('请按照Number(14,2)格式输入"单张充值金额"！');
			$objAmt.select().focus();		
			return false;
		}
	}
	hideMsg();
	return true;
}
*/