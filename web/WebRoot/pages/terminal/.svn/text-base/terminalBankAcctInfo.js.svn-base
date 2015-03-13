$(function(){
	changeNeedBankAcctInfo(); //初始化显示
	Selector.selectBank('bankName', 'bankNo', true, function(){
			loadAreaCode($('#bankNo').val())
	});
});

function changeNeedBankAcctInfo(){
	if($("#needBankAcctInfo").val()=="0"){//不需要
		$("[name^='terminalAddi.']").attr("disabled", "disabled");
		$("[id^=terminalAddiTr]").hide();
	}else{
		$("[name^='terminalAddi.']").removeAttr("disabled");
		$("[id^=terminalAddiTr]").show();
	}
}

/** 根据银行的行号来取得地区码，作为开户行地区码 */
function loadAreaCode(bankNo){
	$.post(CONTEXT_PATH + '/pages/branch/loadAccAreaCode.do', {'bankNo':bankNo, 'callId':callId()}, 
		function(data){
			$('#accAreaName').val(data.accAreaName);
			$('#accAreaCode').val(data.accAreaCode);
		}, 'json');
}