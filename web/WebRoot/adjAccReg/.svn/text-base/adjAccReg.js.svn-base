/* ****************************************************************
 *		调账退货 模块相关js 
 * ****************************************************************/

function CheckUSBKey() {
	var USBKeyFlag = 0; // USBKeyFlag＝0：科友的key；USBKeyFlag＝1：非科友的key
	try {
		// 捷德的key不在线，检查飞天的key
		var isOnline = document.all.FTUSBKEYCTRL.IsOnline;
		if (isOnline == 0) {
			if (FTDoSign()) { // 调用FT的签名函数
				return true;
			} else {
				return false;
			}
		} else {
			showMsg("请检查USB Key是否插入或USB Key是否正确！");
			return false;
		}
	} catch (ex) {
		alert(ex);
	}
	return true;
}

/* 飞天的Key的签名函数 */
function FTDoSign() {
	var SetCertResultRet = document.all.FTUSBKEYCTRL.SetCertResult; // 选择证书
	if (SetCertResultRet == 0) {
		var randomNum = $('#randomSessionId').val();
		var serialNumber = document.all.FTUSBKEYCTRL.GetCertSerial;
		$('#serialNo').val(serialNumber);
		var signResultRet = document.all.FTUSBKEYCTRL.SignResult(randomNum);
		if (signResultRet == 0) {
			var signData = document.all.FTUSBKEYCTRL.GetSignature;
			$('#signature').val(signData);
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