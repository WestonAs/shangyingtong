SaleCardCancel ={
	// 校验数据有效性
	checkForm: function(){
	
		if(!this.checkSaleCancel()) {
			return false;
		}
	
		var signatureReg = $('#id_signatureReg').val();
		if(signatureReg == 'true'){
			if(!SaleCardCancel.CheckUSBKey()){
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
	},
	
	//给隐藏的实收金额，售卡批次id设值
	setHiddenValue: function(saleBatchId, realAmt) {
		$('#id_saleBatchId').val(saleBatchId);
		$('#id_realAmt').val(realAmt);
	},
	
	checkSaleCancel: function(){
		if (!FormUtils.hasRadio('saleBatchId')){
			alert('请选择要撤销的售卡记录！');
			return false;
		}
		if (!confirm('是否要撤销售卡？')) {
			return false;
		}
		return true;
	},
	
	CheckUSBKey: function() {
		var USBKeyFlag = 0; //USBKeyFlag＝0：科友的key；USBKeyFlag＝1：非科友的key
	
	    try{
			//捷德的key不在线，检查飞天的key
			var isOnline = document.all.FTUSBKEYCTRL.IsOnline;
			if (isOnline == 0) {
				if(SaleCardCancel.FTDoSign()){ //调用FT的签名函数
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
	},
	
	/*飞天的Key的签名函数*/
	FTDoSign: function() {
		var SetCertResultRet = document.all.FTUSBKEYCTRL.SetCertResult; //选择证书
		if (SetCertResultRet == 0) {
	   		var randomNum = $('#id_RandomSessionId').val();
	   		
	   		var serialNumber = document.all.FTUSBKEYCTRL.GetCertSerial;
			$('#id_serialNo').val(serialNumber);
	   		
	   		var saleBatchId = $('#id_saleBatchId').val();
	   		var realAmt = $('#id_realAmt').val();
			
			// 取得要验签的内容(原售卡批次号+ 实收金额)
			//var unsignData = FormUtils.getRadioedValue('saleCardReg.saleBatchId');
	      	//alert(saleBatchId + fix(realAmt) + randomNum);
	        var signResultRet = document.all.FTUSBKEYCTRL.SignResult(saleBatchId +  fix(realAmt) + randomNum);
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
}