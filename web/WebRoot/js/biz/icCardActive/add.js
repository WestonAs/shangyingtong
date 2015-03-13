IcCardActive ={
	
	//1001 上电 输入参数10010000
	powerOn: function(touchType){
		var a = "LK31U" + touchType + "02B10010000";
		try{
			var lState = HiddenCOM.PBOCCommand(a);
	
			var str2=lState.substr(4,4);
			if(str2 == "9999") {
				alert("上电失败，请将卡片正确的放入读卡器内！");
				return false;
			}
		} catch(err) {
			alert("请插入读卡器并装好读卡器驱动程序！" );
			return false;
		}
		return true;
	},
	
	// 1002 下电 输入参数10020000
	powerOff: function(touchType){
		var a = "LK31U" + touchType + "02B10020000";
		var lState = HiddenCOM.PBOCCommand(a);
		
		var str2=lState.substr(4,4);		
		if(str2 == "9999") {
			showMsg('下电失败，请将卡片正确的放入读卡器内！' + lState);
			return "-1";
		}
		return "0";
	},

	// 1003 选择金融环境 输入参数10030000
	onSelect: function(touchType){
		var a = "LK31U" + touchType + "02B100300010";
		var lState = HiddenCOM.PBOCCommand(a);
		
		var str2 = lState.substr(4,4);	
		if(str2 == "9999") {
			showMsg("选择金融环境失败，请将卡片正确的放入读卡器内！" + lState);
			return false;
		} 
		else {
			// 取得PDOL的长度
			$('#id_dataLength').val(str2);
			
			var pdol = lState.substr(8, lState.length - 8);
			$('#id_pdol').val(pdol);
		} 
		return true;
	},
	
	// 1004 开始交易 
	startTrans: function(touchType){
		var pdol = $('#id_pdol').val();
		var dataLength = $('#id_dataLength').val();
		
		var length = parseInt(dataLength, 10) + 12;
		var newDataLength = IcCardActive.leftPad(length, 4, '0');
		
		// 授权金额
		var amt = "100000000000";
		var a = "LK31U" + touchType + "02B1004" + newDataLength + amt + pdol;
		
		var lState = HiddenCOM.PBOCCommand(a);

		var str2=lState.substr(4,4);		
		if(str2 == "9999") {
			alert('1004处理失败！' + lState);
			return false;
		} else {
			// 取得AIP和AFL
			var AIP = lState.substr(8, lState.indexOf("&") - 8);
			$('#id_AIP').val(AIP);;
			var AFL = lState.substr(lState.indexOf("&") + 1, lState.length - lState.indexOf("&"));
			$('#id_AFL').val(AFL);;
		}
		return true;
	},

	// 1008 获取卡片信息 输入参数10080000
	getCardInfo: function(touchType){
		var a = "LK31U" + touchType + "02B10080000";
		var lState = HiddenCOM.PBOCCommand(a);
		
		var str2 = lState.substr(4,4);		
		if(str2 == "9999") {
			alert('获取卡片信息失败！' + lState);
			return false;
		}
		// 处理成功
		else {
			var cardId = lState.substr(8,19);
			
			var info = lState.split("&");//-- 国家代码 + 卡片序列号 + 生效日期 + 失效日期
			$('#id_cardSn').val(info[2]) ; // 卡片序列号
			
			$('#id_cardId').val(trim(cardId));
			$.post(CONTEXT_PATH + '/pages/icEcashDeposit/searchCardId.do', 
				{'cardId':cardId}, 
				function(data){
					// 获取信息成功的话
					if(data.result) {
						$('#id_cardId').val(data.cardId);
						$('#id_cardClass').val(data.cardClass);
						$('#id_cardClassName').val(data.cardClassName);
						$('#id_cardBin').val(data.cardBin);
						$('#id_cardSubClass').val(data.cardSubClass);
						$('#id_cardSubClassName').val(data.cardSubClassName);
						$('#id_cardBranch').val(data.cardBranch);
						$('#id_cardBranchName').val(data.cardBranchName);
						$('#id_balanceLimit').val(fix(data.balanceLimit));
						$('#id_randomSessionId').val(data.randomSessionid);
						$('#id_signatureReg').val(data.signatureReg);
						$('#id_avlBal').val(fix(data.avlBal));
					} else {
						showMsg(data.msg);
						return false;
					}
				}, 
			'json');
		}
		return true;
	},

	// 1011 获取电子现金余额 输入参数10110000
	getCardBanlance: function(touchType){
		var a = "LK31U" + touchType + "02B10110000";
		var lState = HiddenCOM.PBOCCommand(a);
		
		var str2 = lState.substr(4,4);		
		if(str2 == "9999") {
			alert('获取电子现金余额失败！' + lState);
			return false;
		}
		// 处理成功
		else {
			var cardBanlance = lState.substr(8,12);
			
			$('#id_lastBalance').val(fix(new Number(cardBanlance)/100));
		}
		return true;
	},
	
	// 1012获取终端验证结果 输入参数10120000
	getTerminalCheck: function(touchType) {
		var a = "LK31U" + touchType + "02B10120000";
		var lState = HiddenCOM.PBOCCommand(a);
		
		var str2 = lState.substr(4,4);		
		if(str2 == "9999") {
			alert('获取终端验证结果失败！' + lState);
			return false;
		}
		// 处理成功
		else {
			//alert(lState);
		}
		return true;
	},

	/*
	 * 1017获取交易密文ARQC 
	 *	输入参数: 1017 + 数据长度+ I_EnsType+ I_GnType + I_AuthAmt + I_OtherAmt + I_AIP + I_MercName + I_AFL
	 * I_EnsType 密文类型0x80 ARQC,0x90 ARQC+CDA，传入十进制整型数128或144，3位
	 * I_GnType 功能代码,2位 01- 现金圈存
	 * I_AuthAmt 授权金额，12位无小数点，精确到分
	 * I_OtherAmt 其它金额，12位无小数点，精确到分
	 * I_AIP 应用交互特征，4位
	 * I_MercName 机构号（终端号）， 20位（英文和数字）
	 * I_ AFL 应用文件定位，变长
	 *
	 *	输入参数：接触类型，充值金额(12位，精确到分)
	 *  输出数据：1017 + 数据长度+
	 *	O_EnsData+ & + O_IssueAppData+ & +O_CDOL1+ & +O_CDOL1Data + & + O_CDOL2 + & + O_AQDT + & + O_EnsType
	 *   O_EnsData 16字节的ARQC或TC密文数据
	 *   O_IssueAppData 发卡行应用数据
	 *   O_CDOL1 卡片风险管理数据对象列表1
	 *   O_CDOL1Data 对应CDOL1的数据
	 *   O_ CDOL2 卡片风险管理数据对象列表2
	 *   O_AQDT 参与校验ARQC的数据
	 *   O_EnsType 密文类型2字节
	 */ 
	getTransARQC: function(touchType, authAmt) {
		var a = "LK31U" + touchType + "02B1017";
		var ensType = "128"; // 密文类型 128- ARQC
		var gnType = "01";  // 功能代码 01- 现金圈存
		var otherAmt = "000000000000"; //其他金额
		var merchName = "00000000000000000000"; // 机构号（终端号）
		var AIP = $('#id_AIP').val();
		var AFL = $('#id_AFL').val();
		
		//alert('getTransARQC');
		var inputLast = ensType + gnType + authAmt + otherAmt + AIP + merchName + AFL;
		
		var input = a + IcCardActive.leftPad(inputLast.length, 4, '0') + inputLast;
		//alert("1017input:" + input);
		
		var lState = HiddenCOM.PBOCCommand(input);
		
		var str2 = lState.substr(4,4);		
		if(str2 == "9999") {
			alert('获取终端验证结果失败！' + lState);
			return false;
		}
		// 处理成功
		else {
			// 授权请求密文 16字节的ARQC
			var arqc = lState.substr(8, lState.indexOf("&") - 8);
			$('#id_ARQC').val(arqc);

			var reuslt_arqc = lState.split("&");// 数组的类容为: 1,O_IssueAppData; 2,O_CDOL1; 3,O_CDOL1Data;4,O_CDOL2;5,O_AQDT;6,O_EnsType
			//arqc源数据 aqdt, O_AQDT 参与校验ARQC的数据
			$('#id_AQDT').val(reuslt_arqc[5]);
			$('#id_CDOL1Data').val(reuslt_arqc[3]); // 3, O_CDOL1Data
			$('#id_CDOL2').val(reuslt_arqc[4]); // 4,O_CDOL2
		}
		return true;
	},
	
	/*
	 * 1018
	 * 获得交易确认密文TC或获得交易拒绝密文AAC
	 * 输入参数: 1018 +数据长度+ 
	 * I_EnsType+ I_GnType + I_AuthAmt + I_OtherAmt + I_AuthRespCode + I_CDOL1Data_Len + I_CDOL1Data + I_CDOL2_Len + I_CDOL2
	 * 
	 * I_EnsType: 密文类型0x00 AAC, 0x40 TC,0x50 TC+CDA，传入十进制整型数00、64或80，2位
	 * I_GnType: 功能代码,2位 09- 卡收回 02-预制卡启用 03-现金圈存 04-非指定圈存 05-指定圈存
	 * I_AuthAmt: 授权金额，12位无小数点，精确到分。即充值金额
	 * I_OtherAmt: 其它金额，12位无小数点，精确到分
	 * I_AuthRespCode： 授权响应码4位(当获得密文AAC时，不能为0x30 0x30) 这里填：3030
	 * I_CDOL1Data_Len： O_CDOL1Data的长度，4位，前补0
	 * I_CDOL1Data： 获得交易密文中返回的O_CDOL1Data
	 * I_CDOL2_Len： O_ CDOL2的长度，4位，前补0
	 * I_CDOL2： 获得交易密文终返回的CDOL2
	 *
	 * 输出数据：1018+ 长度+ O_EnsData + & + O_CDOL2Data + & + O_EnsType
	 * O_EnsData 16字节的TC或AAC密文数据
	 * O_CDOL2Data 参与TC或AAC运算的CDOL2Data
	 * O_EnsType 密文类型2字节
	 *
	 * 函数的参数：卡接触类型，充值金额
	 */
	 getTransConfirm: function(touchType, authAmt){
	 	var a = "LK31U" + touchType + "02B1018";
	 	var ensType = "64"; // 密文类型 64- TC
	 	var gnType = "03";  // 功能代码 03-现金圈存
	 	var otherAmt = "000000000000"; //其他金额
	 	var authRespCode = "3030"; // 授权响应码 这里填：3030
	 	var CDOL1Data = $('#id_CDOL1Data').val();
	 	var CDOL2 = $('#id_CDOL2').val();
	 	
	 	// I_CDOL1Data_Len + I_CDOL1Data + I_CDOL2_Len + I_CDOL2
	 	var suffix = IcCardActive.leftPad(CDOL1Data.length, 4, '0') + CDOL1Data + IcCardActive.leftPad(CDOL2.length, 4, '0') + CDOL2;
	 	var suff = ensType + gnType + authAmt + otherAmt + authRespCode + suffix;
	 	
	 	var input = a + IcCardActive.leftPad(suff.length, 4, '0') + suff;
	 	//alert('CDOL1Data:[' + CDOL1Data + '],CDOL2:[' +CDOL2+ '], suff:['+(ensType + gnType + authAmt + otherAmt + authRespCode)+']');
	 	//alert("1018:" + input);
	 	
	 	var lState = HiddenCOM.PBOCCommand(input);
		
		var str2 = lState.substr(4,4);		
		if(str2 == "9999") {
			showMsg('获得交易确认密文TC或获得交易拒绝密文AAC失败！' + lState);
			return "-1";
		}
		// 获得交易确认密文TC或获得交易拒绝密文AAC成功
		else {
			//alert('1018返回：' + lState);
		}
		return "0";
	 },
	 
	 /*
	  * 1020 发卡行认证
	  * 输入参数： 10200020+I_AthData 
	  *		I_AthData发卡行认证数据，20位，为ARPC(16字节)+授权响应码(4字节)
	  * 输出数据： 10200000 成功
	  */
	getCardBrancAuth: function(touchType, arpc){
		var a = "LK31U" + touchType + "02B1020";
		var authRespCode = "3030"; // 授权响应码 这里填：3030
		var input = a + "0020" + arpc + authRespCode;

		var lState = HiddenCOM.PBOCCommand(input);
		
		var str2 = lState.substr(4,4);		
		if(str2 == "9999") {
			alert('获得发卡行认证失败！' + lState);
			return "-1";
		}
		// 获得发卡行认证成功
		else {
			alert("执行命令1020，获得发卡行认证成功！");
			return "0";
		}
	},
	
	/*
	  * 1021 运行发卡行命令脚本。就是执行写卡脚本
	  * 输入参数： 1021+ I_DataLen + I_ScriptData 
	  *		I_DataLen 脚本长度，左补0
	  *		I_ScriptData 脚本数据的组合数据串
	  * 输出数据： 10210012 + O_Flag + O_ScriptSeq + O_ScriptMark
	  *		O_Flag 执行标志，2位; 00-成功; 01-脚本错; 02-读卡器错误
	  *		O_ScriptSeq 执行失败脚本序号，2位
	  *		O_ScriptMark 发卡行脚本标识符，8位
	  */
	runWriteScript: function(touchType, scriptData){
		var a = "LK31U" + touchType + "02B1021";
		var dataLen = IcCardActive.leftPad(scriptData.length, 4, '0');
		var input = a + dataLen + scriptData;
		
		//alert('1021:' + input);
		var lState = HiddenCOM.PBOCCommand(input);
		
		var str2 = lState.substr(4,4);		
		if(str2 == "9999") {
			alert('写卡失败！' + lState);
			return "-1";
		}
		
		var flag = lState.substr(8,2);		
		//  写卡成功
		if(flag == "00") {
			alert("写卡成功");
			//return true;
			return flag;
		}
		// 写卡失败，执行冲正命令
		else {
			alert('运行发卡行命令脚本失败：' + lState);
			//$.post(CONTEXT_PATH + '/pages/icEcashDeposit/reversal.do', 
				//{'icEcashReversal':cardId});
			//return false;
			return flag;
		}
	},

	//根据返回码判断后台处理成功，则进行写卡操作 -1：失败
	writeCard: function(touchType, arpc, authAmt, scriptData){
		// 1018 获得交易确认密文TC或获得交易拒绝密文AAC
		if(IcCardActive.getTransConfirm(touchType, authAmt) == "-1"){
			return "-1";
		}
		// 1021运行写卡脚本
		return IcCardActive.runWriteScript(touchType, scriptData);
	},
	
	// 读取卡片信息
	readCardInfo: function(){
		IcCardActive.clearAllInput();
		var touchType = FormUtils.getRadioedValue('touchType');
		// 1001 上电
		if(!IcCardActive.powerOn(touchType)){
			return false;
		}
		// 1003 选择金融环境
		if(!IcCardActive.onSelect(touchType)) {
			return false;
		}
		// 1004 开始交易
		if(!IcCardActive.startTrans(touchType)) {
			return false;
		}
		//1008 获取卡片信息
		if(!IcCardActive.getCardInfo(touchType)) {
			return false;
		}
		// 1011 取得卡片余额
		if(!IcCardActive.getCardBanlance(touchType)) {
			return false;
		}
	},
	
	// 校验数据有效性
	checkForm: function(){
		var touchType = FormUtils.getRadioedValue('touchType');
	
		if(!IcCardActive.checkActive()) {
			return false;
		}
	
		var signatureReg = $('#id_signatureReg').val();
		if(signatureReg == 'true'){
			if(!IcCardActive.checkUSBKey()){
				return false;
			}
		}
		
		IcCardActive.loadDeal();
		
		var totalAmt;
		var avlBal = $('#id_avlBal').val();
		var lastBalance = $('#id_lastBalance').val();
		var balanceLimit = $('#id_balanceLimit').val();
		// 补登账户金额 + 余额 不得超过卡内余额上限。如果超过，则授权金额为卡内余额上限
		totalAmt = parseFloat(avlBal) + parseFloat(lastBalance);
		if( totalAmt > parseFloat(balanceLimit)) {
			totalAmt = parseFloat(balanceLimit); 
		}
		
		// 变动金额
		var newBal = new Array("","");
		var newBal = (new String(totalAmt * 100)).split(".");
		//alert('newBal:' + newBal[0]);
		var authAmt = IcCardActive.leftPad(newBal[0], 12, '0'); //授权金额即充值金额
		
		// 1017 获得交易密文ARQC
		if(!IcCardActive.getTransARQC(touchType, authAmt)) {
			return false;
		}
		
		// 1012 获取终端验证结果
		if(!IcCardActive.getTerminalCheck(touchType)) {
			return false;
		}
		
		var params = "{";
		$('#inputForm').find('input').each(function (){
			if($(this).attr('type') == 'submit' 
				|| $(this).attr('type') == 'button'
				|| isEmpty($(this).attr('name'))){
				return true;
			}
			if(isEmpty($(this).val())){
				return true;
			}
			if(params.length > 1){
				params += ","
			}
			params += "'" + $(this).attr('name') +"':" + "'"+$(this).val()+"'";
		});
		params += "}"; 
		//alert(params);
		var jsonObj = eval('(' + params + ')');
		
		// 用ajax提交到后台来获取arpc和写卡脚本
		$.post(CONTEXT_PATH + '/pages/icCardActive/add.do', 
				jsonObj, 
				function(data){
					// 获取信息成功的话
					if(data.result) {
						var chkRespCode = data.chkRespCode;
						var arpc = data.arpc;
						var writeRespCode = data.writeRespCode;
						var scriptData = data.writeScript;
						var cardId = data.cardId;
						var activeBatchId = data.activeBatchId;
						//alert("arpc:" + arpc);
						
						var revesalURL = '/pages/icCardActive/reversal.do?icEcashReversal.amt=' + authAmt 
									+ '&icEcashReversal.cardId=' + cardId
									+ '&icEcashReversal.depositBatchId=' + activeBatchId;
						//var noticeURL = 
						
						// 1020 进行发卡行认证
						var cardAuth = IcCardActive.getCardBrancAuth(touchType, arpc);
						if(cardAuth == "-1"){
							//$('#inputForm').attr('action', 'reversal.do').submit();
							gotoUrl(revesalURL);
							return false;
						}
						var writeRuslt;
						// 如果响应码都为00，则写卡
						if(chkRespCode == '00' && writeRespCode == '00'){
							// 写卡 
							writeRuslt = IcCardActive.writeCard(touchType, arpc, authAmt, scriptData);
						}
						if(writeRuslt == "-1"){
							//$('#inputForm').attr('action', 'reversal.do').submit();
							gotoUrl(revesalURL);
							return false;
						}
						// 1002 下电
						var power = IcCardActive.powerOff(touchType);
						if(power == "-1"){
							//$('#inputForm').attr('action', 'reversal.do').submit();
							gotoUrl(revesalURL);
							return false;
						}

						$('#id_reversal_depositBatchId').val(activeBatchId);
						$('#id_reversal_amt').val(authAmt);
						$('#id_reversal_cardId').val(cardId);

						//alert("cardId:" + cardId);

						if(!isEmpty(writeRuslt)) {
							if(writeRuslt == '01' || writeRuslt == '02'){
								// 发送冲正
								//$('#inputForm').attr('action', 'reversal.do').submit();
								gotoUrl(revesalURL);
								return false;
								//$.post(CONTEXT_PATH + '/pages/icEcashDeposit/reversal.do', 
									//{'icEcashReversal.amt':authAmt, 'icEcashReversal.cardId':cardId, 'icEcashReversal.depositBatchId':depositBatchId});
							} 
							// 写卡成功的时候发通知
							else if(writeRuslt == '00'){
								//$('#inputForm').attr('action', 'notice.do').submit();
								gotoUrl('/pages/icCardActive/notice.do?icEcashReversal.depositBatchId=' + activeBatchId);
								//$.post(CONTEXT_PATH + '/pages/icEcashDeposit/notice.do', 
									//{'icEcashReversal.depositBatchId':depositBatchId});
							}
						}
					} else {
						//showMsg(data.msg);
						$('#id_error_msg').val(data.msg);
						//$('#inputForm').attr('action', 'noticeError.do').submit();
						gotoUrl('/pages/icCardActive/noticeError.do?errorMsg=' + data.msg)
						return false;
					}
				}, 
			'json');
	},
	
	loadDeal: function(){
		$('#input_btn2').attr('disabled', 'true');
		//window.parent.showWaiter();
		$("#loadingBarDiv").css("display","inline");
		$("#contentDiv").css("display","none");
	},
	
	checkActive: function(){
		var lastBalance = $('#id_lastBalance').val();
		if(isEmpty(lastBalance)){
			showMsg("余额不能为空！");
			return false;
		}
		var cardId = $('#id_cardId').val();
		if(isEmpty(cardId)){
			showMsg("卡号不能为空！");
			return false;
		
		}
		return true;
	},
	
	clearAllInput: function(){
		$('#inputForm').find('input').each(function (){
			if($(this).attr('type') == 'text' 
				|| $(this).attr('type') == 'hidden'){
				$(this).val('');
			}
			return true;
		});
	},
	
	checkUSBKey: function() {
		var USBKeyFlag = 0; //USBKeyFlag＝0：科友的key；USBKeyFlag＝1：非科友的key
	
	    try{
			//捷德的key不在线，检查飞天的key
			var isOnline = document.all.FTUSBKEYCTRL.IsOnline;
			if (isOnline == 0) {
				if(IcCardActive.FTDoSign()){ //调用FT的签名函数
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
	   		var randomNum = $('#id_randomSessionId').val();
	   		//alert('randomNum:' + randomNum);
	   		
	   		var serialNumber = document.all.FTUSBKEYCTRL.GetCertSerial;
			$('#id_serialNo').val(serialNumber);
	   		
	   		var cardId = $('#id_cardId').val(); //卡号
	   		var amt = fix($('#id_lastBalance').val()); //余额 
			
			// 取得要验签的内容(卡号+ 充值金额)
	        var signResultRet = document.all.FTUSBKEYCTRL.SignResult(cardId + amt + randomNum);
			if (signResultRet == 0) {
				var signedData = document.all.FTUSBKEYCTRL.GetSignature;
				//alert('signedData:' + signedData);
				$('#id_signature').val(signedData);
			} else {
				showMsg("签名失败");
				return false;
			}
		} else {
			showMsg("选择证书失败");
			return false;
		}
		return true;
	},
	
	// 字符串填充 
	leftPad: function(value, size, padStr){
		var val = new String(value);
		var str = "";
		if(val.length < size) {
			var leng = parseInt(size - val.length);
			for(i = 0; i<leng; i++ ) {
				 str = padStr + str;
			}
		}
		return str + val;
	}
}