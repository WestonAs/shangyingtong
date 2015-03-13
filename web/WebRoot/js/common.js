var ZERO = 0.0001;
var LOAD_IMAGE = "<img src='" + CONTEXT_PATH + "/images/ajax_loading.gif' style='margin-left:20px;'/>";
var SAVE_IMAGE = "<img src='" + CONTEXT_PATH + "/images/ajax_saving.gif'/>";

var _tracer = null;
var _times = 0;
var _MAX_TIMES = 10; // 系统参数
var _INTERVAL = 2000; // 系统参数

var _INVALID_CHAR = "'<>/&";

/**
 * 是否含有无效字符.
 */
function containsInvalid(s) {
	if (s == null) {
		return false;
	}
	
	for (var i = 0; i < s.length; i++) {
		if (_INVALID_CHAR.indexOf(s.charAt(i)) > -1) {
			return true;
		}
	}
	
	return false;
}

/**
 * 定时执行查询结果
 * 
 */
function startTracker(func) {
	_tracer = setInterval(func, _INTERVAL);
	
	return _tracer;
}

/**
 * 显示成功的图片
 *
 */
function showRightImg(targetId){
	$('#' + targetId).html('<img src="' + CONTEXT_PATH + '/images/pages/right.gif" height="12" width="16" vspace="4"/>');
}

/**
 * 显示错误的图片
 *
 */
function showFaultImg(targetId){
	$('#' + targetId).html('<img src="' + CONTEXT_PATH + '/images/pages/fault.gif"  height="22" width="17" vspace="0"/>');
}

/**
 * 得到当前页面验证结果
 * 
 */
function isValidate(){
	var bValidate = true;
	$('div[id^="resultImg_"]').each(function(){
		if ($(this).html().indexOf('fault.gif') != -1){
			bValidate = false;
		}
	});
	return bValidate;
}

function callId() {
	var random = Math.floor(Math.random() * 10001);
  	return (random + "_" + new Date().getTime()).toString();
}

/**
 * 表单token.
 */
YToken = {
	/**
	 * 生成随机数.
	 */
	callId: function() {
		var random = Math.floor(Math.random() * 10001);
	  	return (random + "_" + new Date().getTime()).toString();
	},
	
	/**
	 * 在表单内部创建随机数token.
	 */
	create: function(jqueryForm) {
		if (jqueryForm.find('input[name="YLINK-KEY"]').length == 0) {
			jqueryForm.append('<input type="hidden" name="YLINK-KEY">');
		}
		
		jqueryForm.find('input[name="YLINK-KEY"]').val(YToken.callId());
	},
	
	/**
	 * 获取表单内部随机数token.
	 */
	get: function(jqueryForm) {
		return jqueryForm.find('input[name="YLINK-KEY"]').val();
	},
	
	/**
	 * 清空表单内部随机数token.
	 */
	clear: function(jqueryForm) {
		jqueryForm.find('input[name="YLINK-KEY"]').val('');
	}
}

/**
 * 提交第一个加入令牌的form.
 */
function submitFirstTokenForm() {
	var form = $("form:first");
	form.find(':button').attr('disabled', true);
	form.find(':submit').attr('disabled', true);
	YToken.create(form);
	form.submit();
}

/**
 * 页面是否有返回结果

 */
function hasResult(){
	var result = $('input[name="result"]').val();
	if (result == null || result == "" || result == undefined){
		return false;
	}
	return true;
}

/**
 * 得到返回消息
 */
function getMessage(){
	return $('input[name="message"]').val();
}

/**
 * 得到返回结果
 */
function getResult(){
	return $('input[name="result"]').val();
}

/**
 * 显示TIP
 * @param result 成功 OR 失败
 * @param message 返回结果
 * @param isHide 成功的信息是否隐藏

 * @param isReset 是否重设页面上的表单
 */
function showTip(result, message, isHide, isReset){
	if (isHide == undefined || isHide == null) {
		isHide = true;
	}
	if (isReset == undefined || isReset == null) {
		isReset = true;
	}
	if (result == "true"){
		$('#tip').slideDown("slow").html('<img src="' + CONTEXT_PATH + '/images/pages/success.gif" align="absmiddle">&nbsp;' + message);
		if (isReset){
			reset();
		}
		if (isHide){
			setTimeout(hideTip, 3000);
		}
	} else if (result == "false") {
		$('#tip').slideDown("slow").html('<img src="' + CONTEXT_PATH + '/images/pages/fault.gif" align="absmiddle">&nbsp;' + message);
	} else if (result == "loading") {
		$('#tip').slideDown("slow").html('<img src="' + CONTEXT_PATH + '/images/pages/loading.gif" align="absmiddle">&nbsp;' + message);
	}
}

/**
 * 隐藏TIP
 */
function hideTip(){
	$('#tip').slideUp("slow");
}

/**
 * 完成验证, 根据是否有效显示提示信息.
 * @tgt jquery对象.
 */
function finishValidate(tgt, isValid) {
	var tipSpan = tgt.find('+ span');

	if (tipSpan.length == 0) {
		return;
	}
	
	if (isValid) {
		tipSpan.addClass('field-tipinfo');
		tipSpan.removeClass('error-input');
	}
	else {
		tipSpan.addClass('error-input');
		tipSpan.removeClass('field-tipinfo');
	}
}

/**
 * 数据类型.
 */
DataType = {
	STRING: '0',
	NUMBER: '1',
	CURRENCY: '2',
	
	/**
	 * 是否为有效数据格式.
	 * 
	 * @param value 待验证数据.
	 * @param dataType 数据类型.
	 * @param length STRING类型为字字串长度, NUMBER类型为整数长度和小数位长度NUMBER(m, n)
	 * CURRENCY 同NUMBER
	 */
	isValid: function(value, dataType, maxLength) {
		if (this.STRING == dataType) {
			return value.length <= maxLength;
		}

		if (this.NUMBER == dataType || this.CURRENCY == dataType ) {
			return Validator.isDecimal(value);
		}
	}
}

FormUtils = {
	/**
	 * 全选全清复选框
	 */
	selectAll: function(target, checkboxName) {
		$(':checkbox[name="' + checkboxName + '"]').each(function() {
			$(this).attr('checked', target.checked);
		});
	},
	
	/**
	 * 获取复选框选中的值. 以","分隔.
	 */
	getCheckedValues: function(checkboxName) {
		return $(':checkbox[name="' + checkboxName + '"][checked]').map(function(){
	  		return $(this).val();
		}).get().join(",");
	},
	
	/**
	 * 获取复选框选中的值. 数组形式.
	 */
	getCheckedArrayValues: function(checkboxName) {
		return $(':checkbox[name="' + checkboxName + '"][checked]').map(function(){
	  		return $(this).val();
		}).get();
	},
	
	/**
	 * 是否有选中复选框
	 */
	hasSelected: function(checkboxName) {
		return $(':checkbox[name="' + checkboxName + '"][checked]').length > 0;
	},
	
	/**
	 * 选中复选框的个数.
	 */
	getSelectedCount: function(checkboxName) {
		return $(':checkbox[name="' + checkboxName + '"][checked]').length;
	},
	
	/**
	 * 获取隐藏域的值. 以","分隔.
	 */
	getHiddenTextValuesStr: function(textName) {
		return $('input:hidden[name="' + textName + '"]').map(function(){return $(this).val();}).get().join(",");
	},
	
	/**
	 * 是否有选中单选框
	 */
	hasRadio: function(radioboxName) {
		return $(':radio[name="' + radioboxName + '"][checked]').length > 0;
	},
	
	/**
	 * 取选中单选框的值
	 */
	getRadioedValue: function(radioboxName) {
		return $(':radio[name="' + radioboxName + '"][checked]').val();
	},
	
	/**
	 * 清空表单域数据（text、password、select、textarea表单域，不包括readonly和disabled标识的）
	 */
	reset: function(formId) {
		this.clearData($('#' + formId + ' input:text'));
		this.clearData($('#' + formId + ' input:password'));
		this.clearData($('#' + formId + ' select'));
		this.clearData($('#' + formId + ' textarea'));
		if($('#' + formId + ' :checkbox:checked').length>0){
			$('#' + formId + ' :checkbox').attr('checked',false);
			$('#' + formId + ' :checkbox').change();
		}
	},
	/**
	 * 清空表单域数据（text、password、select、textarea、hidden表单域）
	 */
	clearFormFields: function(formId) {
		$('#' + formId + ' input:text').val('');
		$('#' + formId + ' input:password').val('');
		$('#' + formId + ' select').val('');
		$('#' + formId + ' textarea').val('');
		$('#' + formId + ' input:hidden').val('');
		if($('#' + formId + ' :checkbox:checked').length>0){
			$('#' + formId + ' :checkbox').attr('checked',false);
			$('#' + formId + ' :checkbox').change();
		}
	},
	/**
	 * 清空对象
	 * @param obj Jquery对象
	 */
	clearData: function(objs) {
		if (objs != null && objs != undefined && objs.length > 0){
			objs.each(function(){
				if($(this).attr('readonly')	|| $(this).attr('disabled')){
					return true;					
				}
				$(this).val('');
			});
		}
	}
};

/**
 * 判断数组元素是否相同.
 */
function equalArray(array1, array2) {
	if (!$.isArray(array1) || !$.isArray(array2)) {
		return false;
	}

	if (array1.length != array2.length) {
		return false;
	}

	var eq = true;
	
	$.each(array1, function(i, n) {
		if ($.inArray(n, array2) < 0) {
			eq = false;
			return false;
		}
	});
	
	return eq;
}

function check(btn, pass) {
	if (!FormUtils.hasSelected('ids')) {
		alert('请选择需要审核的记录');
		return;
	}
	
	if(!pass){
		var desc = $('#id_Desc').val();
		if(isEmpty(desc)){
			alert('请输入审核意见');
			return;
		}
	}
	
	$('#pass').val(pass);
	$('#ids').val(FormUtils.getCheckedValues('ids'));
	$('#workflowForm').submit();
	
	$("#loadingBarDiv").css("display","block");
	$("#contentDiv").css("display","none");
}

function checkParam(btn, pass) {
	var ids = $('#ids').val();
	if (ids == undefined || ids == null || ids == ''){
		alert('没有需要审核的记录');
	}
	
	if(!pass){
		var desc = $('#id_Desc').val();
		if(isEmpty(desc)){
			alert('请输入审核意见');
			return;
		}
	}
	
	var idParamNames = $('#idParamNames').val();
	var paramArray = idParamNames.split(',');
	var paramValue = '';
	for (var i = 0; i < paramArray.length; i++){
		paramValue += $('#' + paramArray[i]).val() + ',';
	}
	$('#pass').val(pass);
	$('#idParamValue').val(paramValue);
	if($('#workflowForm').validate().form()){
		$('#workflowForm').submit();
		
		$("#loadingBarDiv").css("display","inline");
		$("#contentDiv").css("display","none");
	}
}

function isDisplay(domId) {
	var display = $('#' + domId).css('display');
	return (display == 'block' || display == 'inline' || display == 'inline-block' || display == '');
}

//去除字符串空格
function trim(str) { 
   return str.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 是否为空
 * value 必须为字符串
 * return:true 是，false 否
 */
function isEmpty(value) {
	if (value == undefined){return true;}
	if (typeof value != "string"){return false;}
	return value == null || value == undefined || $.trim(value) === "";
}

/**
 * 检查卡号格式是否正确.
 */
function checkCardId(cardId) {
	return !(cardId == null || cardId == undefined || cardId.length < 19 || cardId == "");
}

/**
 * 检查新旧卡号格式是否正确.
 */
function checkCardNum(cardId) {
	return !(cardId == null || cardId == undefined || cardId.length < 18 || cardId == "");
}

function showMsg(content){
	$('.msg').fadeIn();
	$('#_msg_content').html(content);
	$('#_msg_close').focus();
}

function hideMsg(){
	$('.msg').fadeOut(100);
	$('#_msg_content').html('');
}

/** 
 * 使用（显示）或禁用（禁用并且不可编辑）某个Element 
 * @param jqueryObj jquery对象
 * @param enable 使用/禁用
 * 
 */
function enableOrDisableElmt(jqueryObj, enable){
	if(enable){
		jqueryObj.removeAttr("disabled");
		jqueryObj.removeAttr("readonly");
		jqueryObj.removeClass("readonly");
	}else{
		jqueryObj.attr("disabled","disabled");
		jqueryObj.attr("readonly","readonly");
		jqueryObj.addClass("readonly");
	}
}

function fix(value){
	if (isEmpty(value)) {return "";}
	if (value == undefined) {return "";}
	return parseFloat(value).toFixed(2);
}

/**
 * 验证是否decimal
 * @param value 待验证的值
 * @param param '14,2'
 */
function isDecimal(value, param){
	var len = parseInt(param.split(',')[0]);
	var lenScale = parseInt(param.split(',')[1]);
	value = value.replace(/,/g, '');
	var re = new RegExp('^\\d{1,' + (len-lenScale) + '}(\\.\\d{1,' + lenScale + '})?$', 'ig');
	return value.match(re);
}


function checkAllTree(id, tree){
	if ($('#' + id).attr('checked')){
		for (var i = 1; i <= 9; i++) {
			tree.setCheck('0' + i,true);
		}
		tree.setCheck('10',true);
		tree.setCheck('11',true);
		tree.setCheck('12',true);
		tree.setCheck('13',true);
	} else {
		for (var i = 1; i <= 9; i++) {
			tree.setCheck('0' + i,false);
		}
		tree.setCheck('10',false);
		tree.setCheck('11',false);
		tree.setCheck('12',false);
		tree.setCheck('13',false);
	}
}

function checkIsInteger(str){
	var regu = /^[-]{0,1}[0-9]{1,}$/;
	return regu.test(str);
}

function formatCurrency(money) {
	
	var num = money.value;
	var result;
	num = num.toString().replace(/\$|\,/g, '');
	if (isNaN(num)) {
		result = "";
	} else {
		var st = num.indexOf(".");
		if (st != -1) {
			num = num.replace(/,/g, '');
			var x;
			if (num.length >= st + 3) {
				x = num.substring(st + 1, st + 3)
			} else {
				x = num.substring(st + 1, num.length)
			}
			z = num.substring(0, st);
			for (var i = 0; i < Math.floor((z.length - (1 + i)) / 3); i++) {
				z = z.substring(0, z.length - (4 * i + 3)) + ','
						+ z.substring(z.length - (4 * i + 3));
			}
			result = z + "." + x;

		} else {
			
			num = num.replace(/,/g, '');
			for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
				num = num.substring(0, num.length - (4 * i + 3)) + ','
						+ num.substring(num.length - (4 * i + 3));
			result = num;
		}

	}
	
	if(isEmpty(result)) {
		money.value = result;
		return;
	}
	
	if(result.indexOf('.') == -1){
		result += '.00';
	}else if(result.indexOf('.') == result.length -2){
		result += '0';
	}
	if (result != money.value) {
		money.value = result;
	}
}

/**
 * 金额格式化
 * 
 * @param 
 *            money 金额
 * @param 
 *            t 大写金额现实的位置的ID
 * @param t1 隐藏INPUT内容,用于页面往后传递,如果没有传空字符串
 */
function formatToCurrency(money, t, t1) {
	var num = money.value;
	var result;
	num = num.toString().replace(/\$|\,/g, '');
	if (isNaN(num) || num.indexOf(' ') != -1 || num < 0) {
		result='';
		$('#'+t).html('');
		$('#'+t1).html('');
	} else {
		if(num.length>=2){
			if(num.substring(0,2)=='00'){
				num='';
			}
		}
		if(num.length>1 && num.substr(0,1)=='0'&&num.substr(1,1)!='.'){
			num=num.substr(1,num.length-1);
		}
		var amount = new Number(num);
		if(amount>=1000000000000){
			//如果金额过大，取取前12位
			num=num.substr(0,12);
			/*
			money.select();
			money.focus();
			$(money).parent().find('span.error_tipinfo').text('请输入正确格式的金额').show();
			$(money).parent().find('span.field_tipinfo').hide();
			$('#'+t).html('');
			$('#'+t1).html('');
			return;*/
		}else{
			$(money).parent().find('span.error_tipinfo').hide();
		}
		var st = num.indexOf(".")
		if (st != -1) {
			num = num.replace(/,/g, '');
			var x;
			if (num.length >= st + 3) {
				x = num.substring(st + 1, st + 3)
			} else {
				x = num.substring(st + 1, num.length)
			}
			z = num.substring(0, st);
			for (var i = 0; i < Math.floor((z.length - (1 + i)) / 3); i++) {
				z = z.substring(0, z.length - (4 * i + 3)) 
				+ ','//不要逗号
				+ z.substring(z.length - (4 * i + 3));
			}
			result = z + "." + x;

		} else {

			num = num.replace(/,/g, '');
			
			for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
				num = num.substring(0, num.length - (4 * i + 3)) + ','
						+ num.substring(num.length - (4 * i + 3));
			
			//不要金额逗号
			/*for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
				num = num.substring(0, num.length - (4 * i + 3)) + ''
						+ num.substring(num.length - (4 * i + 3));*/
			result = num;
		}
		$('#'+t1).text(result);
		result = result.replace(/,/g, '');
	}
	if (result != money.value) {
		money.value = result;
	}
	
	var sda=arabia_to_chinese(result);
	var ida="#"+t;
	
	//$(ida).val(sda);
	$(ida).text(sda);
}
/**
 * 金额小写转汉字大写js方法
 * 
 * @author jcj
 * @param {}
 *            数字金额
 * @return {}中文金额
 */
function arabia_to_chinese(num) {

	
	for (i = num.length - 1; i >= 0; i--) {
		num = num.replace(",", "")// 替换tomoney()中的“,”
		num = num.replace(" ", "")// 替换tomoney()中的空格
	}
	num = num.replace("￥", "")// 替换掉可能出现的￥字符

	// ---字符处理完毕，开始转换，转换采用前后两部分分别转换---/
	part = num.split(".");
	newchar = "";
	// 小数点前进行转化
	for (i = part[0].length - 1; i >= 0; i--) {
		if (part[0].length > 14) {
			//alert("请输入正确的金额");
			return "";
		}// 若数量超过拾亿单位，提示
		tmpnewchar = ""
		perchar = part[0].charAt(i);
		switch (perchar) {
			case "0" :
				tmpnewchar = "零" + tmpnewchar;
				break;
			case "1" :
				tmpnewchar = "壹" + tmpnewchar;
				break;
			case "2" :
				tmpnewchar = "贰" + tmpnewchar;
				break;

			case "3" :
				tmpnewchar = "叁" + tmpnewchar;
				break;
			case "4" :
				tmpnewchar = "肆" + tmpnewchar;
				break;
			case "5" :
				tmpnewchar = "伍" + tmpnewchar;
				break;
			case "6" :
				tmpnewchar = "陆" + tmpnewchar;
				break;
			case "7" :
				tmpnewchar = "柒" + tmpnewchar;
				break;
			case "8" :
				tmpnewchar = "捌" + tmpnewchar;
				break;
			case "9" :
				tmpnewchar = "玖" + tmpnewchar;
				break;
		}
		switch (part[0].length - i - 1) {
			case 0 :
				tmpnewchar = tmpnewchar + "元";
				break;
			case 1 :
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "拾";
				break;
			case 2 :
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "佰";
				break;
			case 3 :
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "仟";
				break;
			case 4 :
				var intLen = part[0].length;
				if(!(intLen >= 9 && part[0].substring(intLen-8, intLen-4) == '0000'))	
					tmpnewchar = tmpnewchar + "万";
				break;
			case 5 :
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "拾";
				break;
			case 6 :
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "佰";
				break;
			case 7 :
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "仟";
				break;
			case 8 :
				tmpnewchar = tmpnewchar + "亿";
				break;
			case 9 :
				tmpnewchar = tmpnewchar + "拾";
				break;
			case 10 :
				tmpnewchar = tmpnewchar + "佰";
				break;
			case 11 :
				tmpnewchar = tmpnewchar + "仟";
				break;
			case 12 :
				tmpnewchar = tmpnewchar + "万";
				break;
		}
		newchar = tmpnewchar + newchar;
		
	}
	
	// 小数点之后进行转化
	if (num.indexOf(".") != -1) {
		if (part[1].length > 2) {
			part[1] = part[1].substr(0, 2)
		}
		for (i = 0; i < part[1].length; i++) {
			tmpnewchar = ""
			perchar = part[1].charAt(i)
			switch (perchar) {
				case "0" :
					tmpnewchar = "零" + tmpnewchar;
					break;
				case "1" :
					tmpnewchar = "壹" + tmpnewchar;
					break;
				case "2" :
					tmpnewchar = "贰" + tmpnewchar;
					break;
				case "3" :
					tmpnewchar = "叁" + tmpnewchar;
					break;
				case "4" :
					tmpnewchar = "肆" + tmpnewchar;
					break;
				case "5" :
					tmpnewchar = "伍" + tmpnewchar;
					break;
				case "6" :
					tmpnewchar = "陆" + tmpnewchar;
					break;
				case "7" :
					tmpnewchar = "柒" + tmpnewchar;
					break;
				case "8" :
					tmpnewchar = "捌" + tmpnewchar;
					break;
				case "9" :
					tmpnewchar = "玖" + tmpnewchar;
					break;
			}
			if (i == 0)
				tmpnewchar = tmpnewchar + "角";
			if (i == 1)
				tmpnewchar = tmpnewchar + "分";
			newchar = newchar + tmpnewchar;
		}
	}
	
	// 替换所有无用汉字
	while (newchar.search("零零") != -1)
		newchar = newchar.replace("零零", "零");
	newchar = newchar.replace("零亿", "亿");
	newchar = newchar.replace("亿万", "亿");
	newchar = newchar.replace("零万", "万");
	newchar = newchar.replace("零元", "元");
	newchar = newchar.replace("零仟", "");
	newchar = newchar.replace("零佰", "");
	newchar = newchar.replace("零拾", "");
	newchar = newchar.replace("零角", "");
	newchar = newchar.replace("零分", "");
	
	if (newchar.charAt(newchar.length - 1) == "元"
			|| newchar.charAt(newchar.length - 1) == "角")
		newchar = newchar + "整"
	if(newchar.substr(0,1)=='元'){
		newchar.substring(1,newchar.length-1);
	}
	if(newchar=='元整'){
		newchar='零元整';
	}
	
	if(newchar.substr(0,1)=='元'){
		newchar=newchar.substr(1,newchar.length-1);
	}
	if(newchar=='元整'){
		newchar='零元整';
	}
	return newchar;
	
}
function formtMoney(amount,t) {
	var val = amount.value;
	
	//var val = $('#'+t).text();
	var len = val.length;
	//如果输入数据以小数点结尾，将其去掉
	if(val.substr(len-1,1)=='.'){
		val = val.substr(0,len-1);
		var amountVal = $(amount).val();
		$(amount).val(amountVal.substr(0,amountVal.length-1));
	}
	var result;
	if (val == "") {

	} else {
		
		var	num = val.replace(/\$|\,/g, '');
		if(num>=10000000000000){
			return;
		}
		var st = val.indexOf(".")

		if (st == -1) {
			result = val + ".00";
		} else {
			var l = val.length - (st + 1)
			if (l == 0) {
				result = val + ".00";
			} else if (l == 1) {
				result = val + "0";
			} else {
				result = val;
			}
		}
		//$('#'+t).text(result);
		$(amount).val(result);
	}
}

/**
 *选择起始日期
 */
function getStartDate(){
	WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'});
}

/**
 *选择结束日期
 */
function getEndDate(){
	WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'});
}

/**
 *选择起始日期
 */
function getCheckStartDate(){
	WdatePicker({maxDate:'#F{$dp.$D(\'checkEndDate\')}'});
}

/**
 *选择结束日期
 */
function getCheckEndDate(){
	WdatePicker({minDate:'#F{$dp.$D(\'checkStartDate\')}'});
}

/**
 *选择售卡起始日期
 */
function getSaleStartDate(){
	WdatePicker({maxDate:'#F{$dp.$D(\'cardSaleEndDate\')}'});
}

/**
 *选择售卡结束日期
 */
function getSaleEndDate(){
	WdatePicker({minDate:'#F{$dp.$D(\'cardSaleStartDate\')}'});
}

/**
 *选择消费起始日期
 */
function getConsumeStartDate(){
	WdatePicker({maxDate:'#F{$dp.$D(\'consumeEndDate\')}'});
}

/**
 *选择消费结束日期
 */
function getConsumeEndDate(){
	WdatePicker({minDate:'#F{$dp.$D(\'consumeStartDate\')}'});
}

/**
 *选择起始月份
 */
function getStartMonth(){
	//WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'});
	WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM',maxDate:'#F{$dp.$D(\'endDate\')}'})
}

/**
 *选择结束月份
 */
function getEndMonth(){
	//WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})
	WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM',minDate:'#F{$dp.$D(\'startDate\')}'})
}

function AmountInWords(dValue) {
    var maxDec = 4;

    // 验证输入金额数值或数值字符串：
    dValue = dValue.toString().replace(/,/g, ""); dValue = dValue.replace(/^0+/, "");      // 金额数值转字符、移除逗号、移除前导零
    if (dValue == "") { return "零元整"; }      // （错误：金额为空！）
    else if (isNaN(dValue)) { return ""; } //{ return "错误：金额不是合法的数值！"; }

    var minus = "";                             // 负数的符号“-”的大写：“负”字。可自定义字符，如“（负）”。
    var CN_SYMBOL = "";                         // 币种名称（如“人民币”，默认空）
    if (dValue.length > 1) {
        if (dValue.indexOf('' - '') == 0) { dValue = dValue.replace("-", ""); minus = "负"; }   // 处理负数符号“-”
        if (dValue.indexOf('' + '') == 0) { dValue = dValue.replace("+", ""); }                 // 处理前导正数符号“+”（无实际意义）
    }

    // 变量定义：
    var vInt = ""; var vDec = "";               // 字符串：金额的整数部分、小数部分
    var resAIW;                                 // 字符串：要输出的结果
    var parts;                                  // 数组（整数部分.小数部分），length=1时则仅为整数。
    var digits, radices, bigRadices, decimals;  // 数组：数字（0~9——零~玖）；基（十进制记数系统中每个数字位的基是10——拾,佰,仟）；大基（万,亿,兆,京,垓,杼,穰,沟,涧,正）；辅币（元以下，角/分/厘/毫/丝）。
    var zeroCount;                              // 零计数
    var i, p, d;                                // 循环因子；前一位数字；当前位数字。
    var quotient, modulus;                      // 整数部分计算用：商数、模数。

    // 金额数值转换为字符，分割整数部分和小数部分：整数、小数分开来搞（小数部分有可能四舍五入后对整数部分有进位）。
    var NoneDecLen = (typeof (maxDec) == "undefined" || maxDec == null || Number(maxDec) < 0 || Number(maxDec) > 5);     // 是否未指定有效小数位（true/false）
    parts = dValue.split('.');                      // 数组赋值：（整数部分.小数部分），Array的length=1则仅为整数。
    if (parts.length > 1) {
        vInt = parts[0]; vDec = parts[1];           // 变量赋值：金额的整数部分、小数部分

        if (NoneDecLen) { maxDec = vDec.length > 5 ? 5 : vDec.length; }                                  // 未指定有效小数位参数值时，自动取实际小数位长但不超5。
        var rDec = Number("0." + vDec);
        rDec *= Math.pow(10, maxDec); rDec = Math.round(Math.abs(rDec)); rDec /= Math.pow(10, maxDec);  // 小数四舍五入
        var aIntDec = rDec.toString().split('.');
        if (Number(aIntDec[0]) == 1) { vInt = (Number(vInt) + 1).toString(); }                           // 小数部分四舍五入后有可能向整数部分的个位进位（值1）
        if (aIntDec.length > 1) { vDec = aIntDec[1]; } else { vDec = ""; }
    }
    else { vInt = dValue; vDec = ""; if (NoneDecLen) { maxDec = 0; } }
    if (vInt.length > 44) { return "错误：金额值太大了！整数位长【" + vInt.length.toString() + "】超过了上限——44位/千正/10^43（注：1正=1万涧=1亿亿亿亿亿，10^40）！"; }

    // 准备各字符数组 Prepare the characters corresponding to the digits:
    digits = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖");         // 零~玖
    radices = new Array("", "拾", "佰", "仟");                                              // 拾,佰,仟
    bigRadices = new Array("", "万", "亿", "兆", "京", "垓", "杼", "穰", "沟", "涧", "正"); // 万,亿,兆,京,垓,杼,穰,沟,涧,正
    decimals = new Array("角", "分", "厘", "毫", "丝");                                     // 角/分/厘/毫/丝

    resAIW = "";  // 开始处理

    // 处理整数部分（如果有）
    if (Number(vInt) > 0) {
        zeroCount = 0;
        for (i = 0; i < vInt.length; i++) {
            p = vInt.length - i - 1; d = vInt.substr(i, 1); quotient = p / 4; modulus = p % 4;
            
            if (d == "0") { zeroCount++; }
            else {
                if (zeroCount > 0) { resAIW += digits[0]; }
                zeroCount = 0; resAIW += digits[Number(d)] + radices[modulus];
            }
            if (modulus == 0 && zeroCount < 4) { resAIW += bigRadices[quotient]; }
        }
        resAIW += "元";
    }

    // 处理小数部分（如果有）
    for (i = 0; i < vDec.length; i++) { d = vDec.substr(i, 1); if (d != "0") { resAIW += digits[Number(d)] + decimals[i]; } }

    // 处理结果
    if (resAIW == "") { resAIW = "零" + "元"; }     // 零元
    if (vDec == "") { resAIW += "整"; }             // ...元整
    resAIW = CN_SYMBOL + minus + resAIW;            // 人民币/负......元角分/整
    return resAIW;

    
}

//根据金额获得中文大写
function getChinese(src, des){
	var chinese = AmountInWords(src.value); // 取得输入的金额
	$('#' + des).html(chinese);  // 将金额转换成中文大写
}

//计算两个日期的间隔天数 
function DateDiff(sDate1, sDate2){ //sDate1和sDate2是20121218格式 
	var oDate1, oDate2, iDays 
	oDate1 = new Date(); 
	oDate2 = new Date(); 
	oDate1.setFullYear(sDate1.substr(0, 4), sDate1.substr(4, 2)-1, sDate1.substr(6, 2));
	oDate2.setFullYear(sDate2.substr(0, 4), sDate2.substr(4, 2)-1, sDate2.substr(6, 2));
	iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 /24) //把相差的毫秒数转换为天数
	return iDays 
} 

/**
 * 根据select框的内容的长度动态调整select框的大小；（用于解决ie6到ie8对于select中的选项太长时无法显示全的bug）
 */
function FixWidth(selectObj){
	var newSelectObj = selectObj.cloneNode(true);
    newSelectObj.selectedIndex = selectObj.selectedIndex;
    newSelectObj.onmouseover = null;

    var e = selectObj;
    var absTop = e.offsetTop;
    var absLeft = e.offsetLeft;
    while(e = e.offsetParent) {
        absTop += e.offsetTop;
        absLeft += e.offsetLeft;
    }
	with (newSelectObj.style) {
        position = "absolute";
        top = absTop + "px";
        left = absLeft + "px";
        width = "auto";
    }
	document.body.appendChild(newSelectObj);//添加 newSelectObj
	if ($(newSelectObj).width()<=$(selectObj).width()){ //新select的宽度 小于等于 旧select宽度，则不用调整select框的大小
		//移除 newSelectObj
		document.body.removeChild(newSelectObj);
		return;
	}
	selectObj.style.visibility = "hidden";
	newSelectObj.focus();
	
    var rollback = function(){ 
		RollbackWidth(selectObj, newSelectObj); 
	};
	
	if(window.addEventListener) { //firefox
        newSelectObj.addEventListener("blur", rollback, false);
        newSelectObj.addEventListener("change", rollback, false);
    } else { // ie
        newSelectObj.attachEvent("onblur", rollback);
        newSelectObj.attachEvent("onchange", rollback);
    }

}

function RollbackWidth(selectObj, newSelectObj) {
	document.body.removeChild(newSelectObj);
	selectObj.style.visibility = "visible";
	if (selectObj.selectedIndex != newSelectObj.selectedIndex){
		selectObj.selectedIndex = newSelectObj.selectedIndex;
		$(selectObj).change();
	}
}
//******** 根据select框的内容的长度动态调整select框的大小end ********************