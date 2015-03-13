/* ****************************************************************
 *		R6/W8  非接触  IC 卡读写器（深圳市明华澳汉科技股份有限公司）（M1卡）
 *		ActiveX  控件函数调用
 * ****************************************************************/

MwReader = {
	/** 读取卡号 */
	readM1CardId : function (btn) {
		$(btn).attr('disabled', 'true');
		var cardId = "";
		try{
			MWRFATL.openReader(0);
			MWRFATL.readerBeep(1, 1, 1); // 读写器鸣响
			MWRFATL.openMifareCard(1); // 打开mifare卡片
			MWRFATL.authenticationKey(0, 13, "1234567890AB"); // 验证密码
			var data = MWRFATL.mifareRead(52) + MWRFATL.mifareRead(53) + MWRFATL.mifareRead(54) + " "; // 读数据
			cardId = data.substring(0, data.indexOf("="));
			MWRFATL.closeMifareCard(); // 关闭mifare卡片
			MWRFATL.closeReader(); // 断开设备
		}catch (e) {
			alert("读卡失败……\n" + e);
		}
		$(btn).removeAttr('disabled');
		return cardId;
	}
}