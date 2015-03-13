/* ****************************************************************
 *		外部卡导入/外部号码开卡 模块相关js 
 * ****************************************************************/

/** 下载原文件 */
function downloadOrigFile(id, fileName) {
	location.href = CONTEXT_PATH
			+ "/pages/externalCardImport/downloadOrigFile.do?externalCardImportReg.id="
			+ id + "&externalCardImportReg.fileName=" + fileName;
}

/**
 * 根据卡bin，加载相关的卡子类型
 * 
 * @return
 */
function loadSubClass() {
	var binNo = $('#binNo').val();
	if (isEmpty(binNo))
		return;
	$("#cardSubclass").load(
			CONTEXT_PATH + "/pages/externalCardImport/getCardSubClassList.do",
			{
				'binNo' : binNo,
				'callId' : callId()
			}, function() {
				$("#cardSubclass").change();
			});
}

/** 根据卡子类型，查询对应的积分类型 */
function findPointClassByCardSubclass() {
	$.getJSON(CONTEXT_PATH
			+ "/pages/externalCardImport/findPointClassByCardSubclass.do", {
		'externalCardImportReg.cardSubclass' : $("#cardSubclass").val(),
		'callId' : callId()
	}, function(json) {
		if (json.success) {
			$("#ptClass").val(json.ptClass);
			$("#ptClassName").val(json.className);
		} else {
			$("#ptClass").val("");
			$("#ptClassName").val("");
		}
	});
}

/** 表单域校验 */
function validateForm() {
	var signatureReg = $('#needSignatureReg').val();
	if (signatureReg == 'true' && !CheckUSBKey()) { // 证书签名失败
		return false;
	}

	if(isEmpty($("#cardSubclass").val())){
		showMsg("卡类型不能为空");
		return false;
	}
	
	if ($("#ptClass").val() == "" && $("#totalPoint").val() != 0) {
		showMsg("由于没有找到通用积分类型，所以总积分必须为0，并且导入文件中的积分字段也必须全部为0");
		return false;
	} else {
		hideMsg();
		return true;
	}
}

function CheckUSBKey() {
	// 检查飞天的key
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
	return true;
}

/* 飞天的Key的签名函数 */
function FTDoSign() {
	var SetCertResultRet = document.all.FTUSBKEYCTRL.SetCertResult; // 选择证书
	if (SetCertResultRet == 0) {
		var serialNumber = document.all.FTUSBKEYCTRL.GetCertSerial;
		$('#serialNo').val(serialNumber);
	} else {
		showMsg("选择证书失败");
		return false;
	}
	return true;
}