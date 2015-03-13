RenewCard = {

	// 查询指定卡号在数据库的信息
	loadCardInfo: function() {
		var cardId = $('#id_cardId').val();
		if(!checkCardNum(cardId)){
			return false;
		}
		
		$.post(CONTEXT_PATH + '/pages/icEcashDeposit/searchCardId.do', {'cardId':cardId}, 
				function(data){
					// 获取信息成功的话
					if(data.result) {
						$('#id_lastBalance').val(fix(data.lastBalance));
						
						$('#id_randomSessionId').val(data.randomSessionid);
						$('#id_signatureReg').val(data.signatureReg);
						
						
						$('#id_CustName_Hidden').val(data.custName);
						$('#id_CertType_Hidden').val(data.credType);
						$('#id_CertNo_Hidden').val(data.credNo);
					} else {
						showMsg(data.msg);
						return false;
					}
				}, 
			'json');
			
		return true;
	},
	
	checkSubmitForm: function(){
		var newCardId = $('#id_newCardId').val();
		if(isEmpty(newCardId)) {
			showMsg("新卡卡号不能为空！");
			return false;
		}
		var oldCardId = $('#id_cardId').val();
		if(isEmpty(oldCardId)) {
			showMsg("旧卡卡号不能为空！");
			return false;
		}
		if(newCardId == oldCardId){
			showMsg("新旧卡号不能相同！");
			return false;
		}
		var lastBalance = $('#id_lastBalance').val();
		if(isEmpty(lastBalance)){
			showMsg("旧卡余额不能为空！");
			return false;
		}
		var renewType = $('#idRenewType').val();
		if(isEmpty(renewType)){
			showMsg("换卡类型不能为空！");
			return false;
		}
		
		var custName = $('#idCustName').val();
		var certType = $('#idCertType').val();
		var certNo = $('#idCertNo').val();
		
		var custNameHidden = $('#id_CustName_Hidden').val();
		var certTypeHidden = $('#id_CertType_Hidden').val();
		var certNoHidden = $('#id_CertNo_Hidden').val();
		
		if(isEmpty(custNameHidden) && isEmpty(certTypeHidden) && isEmpty(certNoHidden)){
			return confirm("卡号持卡人未保存姓名、证件信息，是否继续？");
		} else {
			if(!isEmpty(custNameHidden) && custName != custNameHidden){
				return confirm('持卡人姓名不符，是否继续？');
			}
			if(!isEmpty(certTypeHidden) && certType != certTypeHidden){
				return confirm('证件类型不符，是否继续？');
			}
			if(!isEmpty(certNoHidden) && certNo != certNoHidden){
				return confirm('证件号码不符，是否继续？');
			}
		}
		
		return true;
	},
	
	// 校验数据有效性
	checkForm: function(){
		if(!RenewCard.checkSubmitForm()) {
			return false;
		}
	
		var signatureReg = $('#id_signatureReg').val();
		//alert('signatureReg:' + signatureReg);
		if(signatureReg == 'true'){
			if(!IcRenewCard.CheckUSBKey()){
				return false;
			}
		}
		
		if(!$('#inputForm').validate().form()){
			return false;
		}
		
		IcRenewCard.loadDeal();
		
		/**
		var params = "{";
		$('#inputForm').find('input').each(function (){
			if($(this).attr('type') == 'submit' 
				|| $(this).attr('type') == 'button'
				|| isEmpty($(this).attr('name'))){
				return true;
			}
			if(params.length > 1){
				params += ","
			}
			params += "'" + $(this).attr('name') + "':" + "'" + $(this).val() + "'";
		});
		params += "}"; */
		
		var params = $('#inputForm input, select').serialize();
		//alert("sssss" + params);
		//var jsonObj = eval('(' + params + ')');
		
		// 用ajax提交到后台
		$.post(CONTEXT_PATH + '/pages/icRenewCard/unreadRenew.do', 
				params, 
				function(data){
					// 获取信息成功的话
					if(data.result) {
						var refId = data.batchId; // 换卡批次号
						
						$('#id_reversal_refId').val(refId);
						
						gotoUrl('/pages/icRenewCard/notice.do?icRenewCardReg.id=' + refId);

						return true;
					} else {
						$('#id_error_msg').val(data.msg);
						gotoUrl('/pages/icRenewCard/noticeError.do?errorMsg=' + data.msg);
					}
				}, 
			'json');
	}
	
}