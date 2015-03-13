// 服务端查询卡相关信息
function searchCardInfo(){
	var cardId = $('#cardId').val();
	if (isEmpty(cardId)){return;}
	//cardId = trim(cardId);
	$.post(CONTEXT_PATH + '/depositReg/searchCardInfo.do', 
		{'depositReg.cardId':cardId}, 
		function(data){					
			$('#acctId').val(data.resultAcctId);
			$('#cardClass').val(data.resultCardClass);
			$('#cardClassName').val(data.resultCardClassName);
			$('#cardSubClass').val(data.resultCardSubClass);
			$('#cardSubClassName').val(data.resultCardSubClassName);
			$('#cardCustomerId').val(data.resultCardCustomerId);
			$('#cardCustomerName').val(data.resultCardCustomerName);
			$('#resultRebateType').val(data.resultRebateType);
			$('#rebateTypeName').val(data.resultRebateTypeName);
			$('#groupCardID').val(data.resultGroupCardID);
			$('#rebateId').val(data.resultRebateId);
			$('#rebateName').val(data.resultRebateName);
			$('#calType').val(data.resultCalType);
			$('#calTypeName').val(data.resultCalTypeName);
			$('#custName').val(data.resultCustName);
			$('#credTypeName').val(data.resultCredTypeName);
			$('#credNo').val(data.resultCredNo);
			$('#address').val(data.resultAddress);
			$('#telNo').val(data.resultTelNo);
			$('#mobileNo').val(data.resultMobileNo);
			$('#email').val(data.resultEmail);
			$('#avblBal').val(fix(data.resultAvblBal));
			
			$('#depositTypeIsTimes').val(data.resultDepositTypeIsTimes);
			if($("#depositTypeIsTimes").val()=='true'){//是否次卡
				//$("#depositType").val("按次充值");
				$("#rebateAmt").removeAttr("readonly");
				$("#realAmt").removeAttr("readonly");
				$("#rebateAmt").val("0.00");
				$("#realAmt").val("0.00");
				
				
				$("#depositTypeTr").show();
				$('#depositTypeAmtLabel').show();
				if($("#isNeedDepositRebateAcct").val()=="1"){
					$('#depositTypeRebateAmtLabel').show();
				}else{
					$('#depositTypeRebateAmtLabel').hide();
				}
				$('#depositTypeTimesLabel').show();
				$('#depositTypeTimes').attr("checked","checked");//默认次卡子账户充值
			} else {
				//$("#depositType").val("按金额充值");
				$("#rebateAmt").attr("readonly", "true");
				$("#realAmt").attr("readonly", "true");
				$("#rebateAmt").val("");
				$("#realAmt").val("");
				
				if($("#isNeedDepositRebateAcct").val()=="1"){
					$("#depositTypeTr").show();
					$('#depositTypeAmtLabel').show();
					$('#depositTypeRebateAmtLabel').show();
				}else{
					$("#depositTypeTr").hide();
					$('#depositTypeAmtLabel').hide();
					$('#depositTypeRebateAmtLabel').hide();
				}
				$('#depositTypeTimesLabel').hide();
				$('#depositTypeAmt').attr("checked","checked");//默认 充值资金子账户充值
			}
			changeDepositType();
			
			
			// 如果是客户选择返利的则需要显示下拉框
			if (data.resultRebateType == 1){
				$('#idRebateTypeTr').show();
				$('#rebateType').removeAttr('disabled');
			} else {
				$('#idRebateTypeTr').hide();
				$('#rebateType').attr('disabled', 'true');
			}
			
			var resultMessage = data.resultMessage;

			$('#rebateAmt').val("");
			$('#realAmt').val("");

			if(resultMessage.length > 0){
				showMsg(resultMessage);
				$("#cardId").focus();
			} else {
				// 加载返利规则
				$jload('idRebateRulePage', '/depositReg/findRebateRule.do', 
					{'cardId':cardId, 'cardCustomer.cardCustomerId':data.resultCardCustomerId, 'formMap.ablePeriod': true, 'callId':callId()});
			}
		},'json'
	);
}

//服务端查询签单卡相关信息
function searchSignCardInfo(){
	var cardId = $('#cardId').val();
	$.post(CONTEXT_PATH + '/depositRegSign/searchSignCardInfo.do', 
		{'depositReg.cardId':cardId}, 
		function(data){					
			$('#acctId').val(data.resultAcctId);
			$('#cardClass').val(data.resultCardClass);
			$('#cardClassName').val(data.resultCardClassName);
			$('#cardSubClass').val(data.resultCardSubClass);
			$('#cardSubClassName').val(data.resultCardSubClassName);
			$('#cardCustomerId').val(data.resultCardCustomerId);
			$('#cardCustomerName').val(data.resultCardCustomerName);
			$('#resultRebateType').val(data.resultRebateType);
			$('#rebateTypeName').val(data.resultRebateTypeName);
			$('#groupCardID').val(data.resultGroupCardID);
			$('#rebateId').val(data.resultRebateId);
			$('#rebateName').val(data.resultRebateName);
			$('#calType').val(data.resultCalType);
			$('#calTypeName').val(data.resultCalTypeName);
			$('#custName').val(data.resultCustName);
			$('#credTypeName').val(data.resultCredTypeName);
			$('#credNo').val(data.resultCredNo);
			$('#address').val(data.resultAddress);
			$('#telNo').val(data.resultTelNo);
			$('#mobileNo').val(data.resultMobileNo);
			$('#email').val(data.resultEmail);
			$('#avblBal').val(fix(data.resultAvblBal));
			
			$('#depositTypeIsTimes').val(data.resultDepositTypeIsTimes);
			if($("#depositTypeIsTimes").val()=='true'){
				$("#rebateAmt").attr("readOnly", "");
				$("#realAmt").attr("readOnly", "");
				$("#rebateAmt").attr("value", "0.00");
				$("#realAmt").attr("value", "0.00");				
			}else{
				$("#rebateAmt").attr("readOnly", "readOnly");
				$("#realAmt").attr("readOnly", "readOnly");
				$("#rebateAmt").attr("value", "");
				$("#realAmt").attr("value", "");
			}
			
			var resultMessage = data.resultMessage;

			$('#rebateAmt').val("");
			$('#realAmt').val("");

			if(resultMessage.length > 0){
				showMsg(resultMessage);
			}
		},'json'
	);
}

// 服务端计算返利金额、实收金额
function calRealAmt(){
	var rebateId = FormUtils.getRadioedValue('rebateIdRadio');
	$('#rebateId').val(rebateId);
	
	if($('#rebateRuleRebateType'+rebateId).val()==1){//分期
		$('#periodLabel').show();
	}else{
		$('#periodLabel').hide();
	}
	
	// 检查数据
	if(!checkHiddenField()) return false;
	
	$('#idDepositTbl').show();
	
	// 按次充值
	var type = 1;	// type默认指按金额充值，为0时按次充值
	var cardId = $('#cardId').val();
	if($("#depositTypeIsTimes").val()=='true'){
		$("#rebateAmt").removeClass('readonly').removeAttr('readonly');
		$("#realAmt").removeClass('readonly').removeAttr('readonly');
		type = FormUtils.getRadioedValue('timesDepositType');
	}
	var rebateType = $('#rebateType').val();
	//var rebateId = FormUtils.getRadioedValue('rebateIdRadio');
	var cardCustomerId = $('#cardCustomerId').val();
	//$('#rebateId').val(rebateId);
	var amt = $('#amt').val();
	var feeRate = $('#id_FeeRate').val();
	
	if(rebateId == undefined || rebateId=="")return;
	if(amt == undefined || amt=="")return;
	
	$('#submitBtn').attr('disabled','disabled');
	$('#realAmt').val("");
	$('#rebateAmt').val("");
	$('#id_FeeAmt').val("");
	$.post(CONTEXT_PATH + '/depositReg/calRealAmt.do', 
		{'depositReg.cardCustomerId':cardCustomerId, 'rebateType':rebateType, 'depositReg.rebateId':rebateId, 
			'depositReg.amt':amt, 'depositReg.feeRate':feeRate, 'type': type, 'cardId': cardId, 'callId':callId()}, 
		function(data){
			if(data.isUsedPeriodRule){
				$("#isUsedPeriodRuleTip").show();
			}else{
				$("#isUsedPeriodRuleTip").hide();
			}
			$('#realAmt').val(fix(data.realAmt));
			$('#rebateAmt').val(fix(data.rebateAmt));
			$('#id_FeeAmt').val(fix(data.feeAmt));
			$('#submitBtn').removeAttr('disabled');
		},'json');
}


/** 选择计算方式：0，自动技术；1，手工指定 */
function selectCalcMode(){
	var value = FormUtils.getRadioedValue('calcMode');
	if (value == '1'){ //1，手工指定
		$('#realAmt').removeClass('readonly').removeAttr('readonly');
		if (isDisplay('rebateType') && $('#rebateType').val() == '0') {// 折现，则返利不能改
			$('#rebateAmt').addClass('readonly').attr('readonly', 'true');
		} else { 
			$('#rebateAmt').removeClass('readonly').removeAttr('readonly');
		}
	} else {
		calRealAmt();
		$('#realAmt').addClass('readonly').attr('readonly', 'true');
		$('#rebateAmt').addClass('readonly').attr('readonly', 'true');
	}
}

function checkHiddenField(){
	var cardCustomerId = $("#cardCustomerId").val();
	if(cardCustomerId==""){
		return false;
	}
	var rebateId = $("#rebateId").val();
	if(rebateId==""){
		return false;
	}
	hideMsg();
	return true;
}

// 检查录入数据完整性
function checkField(){
	// 卡号有效性检查
	var cardId = $("#cardId").val();
	var cardCustomerId = $("#cardCustomerId").val();
	var rebateId = $("#rebateId").val();
	var calType = $("#calType").val();
	var amt = $("#amt").val();
	var rebateAmt = $("#rebateAmt").val();
	var realAmt = $("#realAmt").val();
	
	if(cardId==null || cardId==""){
		showMsg("卡号不能为空！");
		return false;
	}
	if(cardCustomerId==null || cardCustomerId==""){
		showMsg("购卡客户不能为空！");
		return false;
	}
	if(amt==null || amt==""){
		showMsg("充值金额不能为空！");
		return false;
	}
	if(rebateAmt==null || rebateAmt==""){
		showMsg("返利金额不能为空！");
		return false;
	}
	if(realAmt==null || realAmt==""){
		showMsg("实收金额不能为空！");
		return false;
	}
	hideMsg();
	return true;
}

function submitDepositForm(){
	try{
		if(!checkField()){
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
			showMsg('充值金额不能为空，请重新计算费用');
			return false;
		}
		if(!checkSubmitForm()){
			return false;
		}
		/*
		if (!isDisplay('idTimesTr')){
			$(':radio[name="timesDepositType"]').removeAttr('checked');
		}
		*/
		
		hideMsg();
		if($('#inputForm').validate().form()){
			$('#inputForm').submit();
			$('#submitBtn').attr('disabled', 'true');
			//window.parent.showWaiter();
			$("#loadingBarDiv").css("display","inline");
			$("#contentDiv").css("display","none");
		}
	}catch(err){
		txt="本页中存在错误。\n\n 错误描述：" + err.description + "\n\n"
		showMsg(txt)
	}
}


function checkSubmitForm(){
	try{
		var signatureReg = $('#id_signatureReg').val();
		if(signatureReg == 'true'){
			if(!CheckUSBKey()){
				return false;
			}
		}
		return true;
		
	}catch(err){
		txt="本页中存在错误。\n\n"
		txt+="错误描述：" + err.description + "\n\n"
		showMsg(txt)
	}
}

function CheckUSBKey() {
    try{
		// 检查key
		var isOnline = document.all.FTUSBKEYCTRL.IsOnline;
		if (isOnline == 0) {
			FTDoSign(); //调用FT的签名函数
		} else {
			showMsg("请检查USB Key是否插入或USB Key是否正确！");
			return false;
	    }
	} catch(ex) {
		showMsg("ex");
	}
	return true;
}

/*飞天的Key的签名函数*/
function FTDoSign(){
	var SetCertResultRet = document.all.FTUSBKEYCTRL.SetCertResult; //选择证书
	if (SetCertResultRet == 0) {
   		var randomNum = $('#id_randomSessionId').val();
		var cardId = $('#cardId').val();
		var realAmt = $('#realAmt').val();
		
		var serialNumber = document.all.FTUSBKEYCTRL.GetCertSerial;
		$('#id_serialNo').val(serialNumber);
      
        var signResultRet = document.all.FTUSBKEYCTRL.SignResult(cardId + realAmt + randomNum);
		if (signResultRet == 0) {
			var signData = document.all.FTUSBKEYCTRL.GetSignature;
			//alert('signData:'+ signData);
			$('#id_signature').val(signData);
		} else {
			showMsg("签名失败");
			return false;
		}
	} else {
		showMsg("选择证书失败");
		return false;
	}
}

/** 读取ic卡卡号 */
function readIcCardId(){
	var oldCardId = $("#cardId").val();
	var touchType = $('input:radio[name="touchType"]:checked').val();
	var cardId = IcReader.readIcCardId(touchType);
	$("#cardId").val(cardId);
	if(cardId != oldCardId){
		$("#cardId").change();
	}
}