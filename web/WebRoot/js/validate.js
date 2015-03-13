
/***********************************************************
	Validator: 数据验证类
 ***********************************************************/
/**
 * 数据验证类.
 * 
 * @constructor
 * @version 1.0
 * @author haichen.ma
 * @date 2008-01-28
 */
Validator = function() {
	this.isEmpty = function(value){
		if ( value == "" ) return true;
		var regu = "^[ ]+$";
		var re = new RegExp(regu);
		return re.test(value);
	};
	/**
	 * 验证是否为全数字.
	 * 
	 * @param value 待验证值
	 * @return 是否为全数字
	 * @type boolean
	 *
	 * @author haichen.ma
	 * @date 2008-01-28
	 */
	this.isDigit = function(value) {
		return /^([0-9]*)$/.test(value);
	};

	/**
	 * 验证是否为数字和大小写字母组成.
	 * 
	 * @param value 待验证值
	 * @return 是否为数字和大小写字母组成
	 * @type boolean
	 * 
	 * @author haichen.ma
	 * @date 2008-01-28
	 */
	this.isDigitOrLetter = function(value) {
		return /^([0-9a-zA-Z]*)$/.test(value);
	};

	/**
	 * 验证是否为整数或小数.
	 * 
	 * @param value 待验证值
	 * @return 是否为整数或小数
	 * @type boolean
	 * 
	 * @author haichen.ma
	 * @date 2008-01-28
	 */
	this.isDecimal = function(value) {
		return /^\d*(\.\d+)?$/.test(value);
	};
	
	/**
	 * 验证是否为24小时时间格式HHmm.
	 * @param value 待验证值
	 */
	this.isHHmm = function(value) {
		var reg = /^(0\d|1\d|2[0123])[012345]\d$/
		return reg.test(value);
	};

	/**
	 * 验证是否为日期类型.
	 * 接受以下三种格式并且是1000年以后的日期:
	 * <ul>
	 *	<li><code>yyyyMMdd</code></li>
	 *	<li><code>yyyy/MM/dd</code></li>
	 *	<li><code>yyyy-MM-dd</code></li>
	 * </ul>
	 * 
	 * @param value 待验证值
	 * @return 是否为日期类型
	 * @type boolean
	 * 
	 * @author haichen.ma
	 * @date 2008-01-28
	 */
	this.isDate = function(value) {
		/* 判断格式 */
		if (!/^\d{4}([-]?\d{2}){2}|\d{4}([\/]?\d{2}){2}$/.test(value)) {
			return false;
		}
		
		var year = null;
		var month = null;
		var day = null;

		/* 提取年月日部分 */
		if (value.length == 8) {
			year = value.substr(0, 4);
			month = value.substr(4, 2);
			day = value.substr(6, 2);
		}
		else if (value.length == 10) {
			year = value.substr(0, 4);
			month = value.substr(5, 2);
			day = value.substr(8, 2);
		}
		else {
			return false;
		}

		/* 判断日期范围 */
		if (year < 1000 
			|| (month < 1 || month > 12) 
			|| (day < 1 || day > 31)) {
			return false;
		}

		/* 判断是否能正确转换为日期 */
		var date = new Date(year, month - 1, day);

		if (date.getFullYear() != year
			|| date.getMonth() != month - 1
			|| date.getDate() != day) {
			return false;
		}

		return true;
	};
};

var validator = new Validator();