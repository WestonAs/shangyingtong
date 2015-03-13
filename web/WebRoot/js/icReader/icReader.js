/* ****************************************************************
 *		IC卡读写器 简单封装
 * ****************************************************************/
IcReader = {
	/** 读取ic卡卡号 */
	readIcCardId : function(touchType) {
		IcReaderBase.powerOn(touchType);
		IcReaderBase.onSelect(touchType);
		var cardId = IcReaderBase.getCardInfo(touchType).substr(8,19);
		return cardId;
	}
}



/* ****************************************************************
 *		IC卡读写器 基础方法
 * ****************************************************************/
/**
 * 设备信息域为6 位，格式为**##$?，
 * 1、**，两位，代表厂商名称。编码如下：LK 莱克；NT 南天；ST 升腾；SD 实达。
 * 2、##，两位，代表产品型号。厂商不同型号的读卡器，可能驱动不同。
 * 3、$，一位，代表通讯方式。U USB 通讯方式；C COM 串口通讯方式。
 * 4、?，一位，代表接触或非接方式。0 接触式；1 非接触式。
 */
IcReaderBase = {
	inputPrex : "LK31U",

	/** 1001 上电 输入参数10010000 */
	powerOn : function(touchType) {
		var a = this.inputPrex + touchType + "02B10010000";
		try {
			var lState = HiddenCOM.PBOCCommand(a);
			if (lState.substr(4, 4) == "9999") {
				alert("上电失败，请将卡片正确的放入读卡器内！");
				return false;
			}
		} catch (err) {
			alert("请插入读卡器并装好读卡器驱动程序！");
			return false;
		}
		return true;
	},

	/** 1003 选择金融环境 输入参数10030000 */
	onSelect : function(touchType) {
		var a = this.inputPrex + touchType + "02B100300010";
		var lState = HiddenCOM.PBOCCommand(a);
		if (lState.substr(4, 4) == "9999") {
			showMsg("选择金融环境失败，请将卡片正确的放入读卡器内！" + lState);
			return false;
		}
		return true;
	},

	/** 1008 获取卡片信息 输入参数10080000 */
	getCardInfo : function(touchType) {
		var a = this.inputPrex + touchType + "02B10080000";
		var lState = HiddenCOM.PBOCCommand(a);
		if (lState.substr(4, 4) == "9999") {
			showMsg('获取卡片信息失败！' + lState);
			return "";
		} else {// 处理成功
			return lState;
		}
	}
}