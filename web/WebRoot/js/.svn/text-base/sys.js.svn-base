try{
	document.execCommand("BackgroundImageCache", false, true);
} catch(e){}

String.prototype.trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, "");
};

/**
 * 打开模态对话框.
 * @param isRefresh 是否刷新
 * @param refreshHandle 刷新触发id
 */
function openContextDialog(url, isRefresh, refreshHandle, width, height) {
	if (!width) {
		width = 900;
	}
	
	if (!height) {
		height = 600;
	}
	
	var option = 'dialogWidth=' + width + 'px;dialogHeight=' + height + 'px;resizable=yes';
	var rlt = window.showModalDialog(CONTEXT_PATH + url, null, option)
	
	// 默认设置刷新
	if (!isRefresh) {
		isRefresh = false;
	}
	
	var refreshId = refreshHandle ? refreshHandle : '_refresh';
	var _refresh = document.getElementById(refreshId);
	
	if (!_refresh) {
		_refresh = document.getElementById('_refresh_0');
	}

	if (_refresh && isRefresh) {
		setTimeout(function() {_refresh.click();}, 1);
	}

	if(!isEmpty(rlt)){
		return rlt;
	}
	
}

function gotoUrl(url){
	window.location.href= CONTEXT_PATH + url;
}

function gotoUrlWithConfirm(url, msg){
	if (!confirm(msg)) {
		return;
	}
	window.location.href= CONTEXT_PATH + url;
}

function stopevent(evt){
	var e = evt ? evt : window.event;
	if (window.event) {
		e.cancelBubble=true;
	} else {
		e.stopPropagation();
	}
}

/**
 * 设置页面元素样式.
 */
$(function() {
	SysStyle.setDataGridStyle();
	SysStyle.setFormGridStyle();
	SysStyle.setSearchGridStyle();
	SysStyle.setDetailGridStyle();
	SysStyle.setPageNavStyle();
	SysStyle.setButtonStyle();
	SysStyle.setNoPrivilegeStyle();
	SysStyle.addFormValidate();
	SysStyle.addRequiredStyle();
	// SysStyle.initSelect();
});

/**
 * 系统样式.
 */
SysStyle = {
	/**
	 * 设置数据表格头背景, 奇偶行颜色, 鼠标移动颜色.
	 */
	setDataGridStyle: function() {
		$('table.data_grid').each(function() {
			var $t = $(this);
			var thead = $t.find('thead');
			if (thead.hasClass('bg-1')) {
				return;
			}
			
			thead.addClass('bg-1');
			$t.find('tbody tr:odd').addClass('eee');
			$t.find('tbody tr:even').addClass('ddd');
			
			$trs = $t.find('tbody tr');
			$trs.mouseover(function() {if (!$(this).hasClass('click')) {$(this).addClass('on')}});
			$trs.mouseout(function() {if (!$(this).hasClass('click')) {$(this).removeClass('on')}});
			//$trs.click(function() {$(this).toggleClass('click')});
			$trs.click(function() {
				removeClassAll($(this));//remove所有样式，不包括自己
				$(this).toggleClass('click');
				//var radio = $(this).find("td [type='radio']:eq(0)"); //找到radio，存在则自动选择
				//radio.attr("checked", true);
			});
		});
		
		var removeClassAll = function($self){
			var $trs = $self.siblings();
			$trs.each(function(){
				var $tr = $(this);
				$tr.removeClass("click");
				$tr.removeClass("on");
			});
		};
		
	},
	
	/**
	 * 设置表单表格样式.
	 */
	setFormGridStyle: function() {
		// 设置表单表格样式.
		/*
		$('table.form_grid').find('td').each(function(i) {
			// form表格为两列, 第一列为标签, 第二列为录入框.
			if (i % 2 == 0) {
				$(this).addClass('form-label-td');
			}
		});
		*/
		
		// 设置表单输入框样式.
		$('table.form_grid :text, table.form_grid :password')
			.addClass('userbox_bt')
			.focus(function() {$(this).addClass('sffocus');})
			.blur(function() {$(this).removeClass('sffocus');});
		$('table.form_grid select').addClass('select_width');
		$('table.form_grid label').mouseover(function(){$(this).addClass('lihover');})
			.mouseout(function(){$(this).removeClass('lihover');});
	},
	
	/**
	 * 设置表单表格样式.
	 */
	setDetailGridStyle: function() {
		// 设置明细页面样式.
		$('table.detail_grid td:even').addClass('headcell').attr('width', '100px');
		$('table.detail_grid td:odd').addClass('valuecell');
	},
	
	/**
	 * 设置按钮样式.
	 */
	setButtonStyle: function() {
		$btn = $(':button, :submit, :reset')
		$btn.addClass('inp_L3');
		
		$btn.mouseover(function() {$(this).addClass('inp_L4'); $(this).removeClass('inp_L3')});
		$btn.mouseout(function() {$(this).addClass('inp_L3'); $(this).removeClass('inp_L4')});
	},
	
	/**
	 * 设置查询表格样式.
	 */
	setSearchGridStyle: function() {
		$('table.search_grid :text').addClass('form-text');
		$('table.search_grid :password').addClass('form-text');
	},
	
	/**
	 * 禁用无权限链接.
	 */
	setNoPrivilegeStyle: function() {
		$('span.no-privilege a').removeAttr('href').removeAttr('onclick');
	},
	
	/**
	 * 设置分页栏样式.
	 */
	setPageNavStyle: function() {
		$btn = $(':button.pagenavbtn')
		$btn.addClass('inp_L1');
		
		$btn.mouseover(function() {$(this).addClass('inp_L2'); $(this).removeClass('inp_L1')});
		$btn.mouseout(function() {$(this).addClass('inp_L1'); $(this).removeClass('inp_L2')});
	},
	
	/**
	 * 添加表单验证.
	 */
	addFormValidate: function() {
		$('form').each(function(){
			var f = $(this);
			if (f.hasClass('validate')) {
				f.validate({
					showErrors: JError.showErrors, 
					clearError: JError.clearError,
					submitHandler: function(form) {
						$(form).find(":submit").attr('disabled', true);
						$(form).find(":button").attr('disabled', true);
					}
				});
			}
			if (f.hasClass('validate-tip')) {
				f.validate({
					showErrors: JError.tipErrors, 
					clearError: JError.clearTipError,
					submitHandler: function(form) {
						$(form).find(":submit").attr('disabled', true);
						$(form).find(":button").attr('disabled', true);
					}
				});
			}
		});
	},
	
	addRequiredStyle: function(){
		$(':text, select, :file').each(function(){
			var cssName = $(this).attr('class');
			if (cssName != undefined && cssName.indexOf('required') > -1) {
				$(this).parent().prev().addClass('nes formlabel');
			}
		});
	}, 

	removeUnRequiredStyle: function(){
		$(':text, select, :file').each(function(){
			var cssName = $(this).attr('class');
			if (cssName != undefined && cssName.indexOf('required') == -1) { //没有必选的样式时，去掉*的样式
				$(this).parent().prev().removeClass('nes formlabel');
			}
		});
	}, 
	
	// 当页面下拉框只有一个选项时(除开--请选择--)，默认选中该option
	initSelect: function(){
		// 列表页面不做初始化
		if ($('.paginaterLayer').size() > 0){
			return;
		}
		$('select').each(function(){
			var cssName = $(this).attr('class');
			if (cssName != undefined && cssName.indexOf('required') > -1) {
				var size = $(this).find('option').size();
				if (size <= 1){
					return;
				}
				try {
					var headerValue = $(this).find('option:eq(0)').val();
					var secondValue = $(this).find('option:eq(1)').val();
					// 币种的当做特殊处理
					if (secondValue == 'CNY'){
						$(this).val(secondValue);
					} else if (size > 2) {
						return;
					}
					
					if (isEmpty(headerValue)){
						$(this).val(secondValue);
					} else {
						$(this).val(headerValue);
					}
				} catch(e){}
			}
		});
	}
};

/**
 * 表单验证错误提示.
 */
JError = {
	/**
	 * 显示错误信息
	 * @param {} errorMap
	 * @param {} errorList
	 */
	showErrors: function(errorMap, errorList) {
		for ( var i = 0; errorList[i]; i++ ) {
			var error = errorList[i];
			var tipObj = JError.getTipElement(error.element)
			var errObj = JError.getErrElement(error.element)

			// 没有提示信息, 退出.
			if (tipObj == null) {
				return;
			}

			// 有错误信息, 显示错误信息, 隐藏提示信息.
			if (errObj != null) {
				tipObj.hide();
				errObj.show();
			}
			
			// 没有错误信息, 则用错误样式显示提示信息.
			else {
				tipObj.addClass('error_tipinfo');
				tipObj.show();
			}
		}
	},
	
	/**
	 * 清除错误信息
	 */
	clearError: function(element) {
		var tipObj = JError.getTipElement(element);
		var errObj = JError.getErrElement(element);
		
		// 有错误信息对象, 则隐藏错误信息, 显示提示信息.
		if (errObj) {
			errObj.hide();
			tipObj.show();
		}
		
		// 去除提示信息的错误样式.
		if (tipObj != null) {
			tipObj.removeClass('error_tipinfo');
		}
	},
	
	/**
	 * 取得提示信息对象
	 * @param {} element
	 * @return {}
	 */
	getTipElement: function(element) {
		var obj = $(element).next();
		
		if (obj.length == 0) {
			return null;
		}
		
		if (obj.hasClass('field_tipinfo')) {
			return obj;
		}
		
		// 查找下一个.
		obj = obj.next();
		return obj.hasClass('field_tipinfo') ? obj : null;
	},
		
	/**
	 * 取得错误信息对象, 一般为录入对象后的第二个, text->infospan->errspan
	 * @param {} element
	 * @return {}
	 */
	getErrElement: function(element) {
		var tipObj = JError.getTipElement(element);
		if (tipObj == null) {
			return null;
		}
		
		var errObj = tipObj.next();
		return errObj.hasClass('error_tipinfo') ? errObj : null;
	},

	/**
	 * 显示错误信息
	 */
	showError: function(element) {
		var tipe = getTipElement(element);
		
		if (tipe) {
			tipe.removeClass('field_tipinfo').addClass('error_tipinfo');
		}
	},
	
	hasFormError: function() {
		return $('.error_tipinfo').length > 0
	},
	
	/**
	 * 以tip方式显示错误信息
	 * @param {} errorMap
	 * @param {} errorList
	 */
	tipErrors: function(errorMap, errorList) {
		for ( var i = 0; errorList[i]; i++ ) {
			var error = errorList[i];
			var id = $(error.element).attr('id');
			var field = $(error.element).parent().prev().text();
			ZZtips.attachTip(id, field + error.message);
			return;
		}
	}, 
	
	/**
	 * 清除TIP错误信息
	 * @param {} errorMap
	 * @param {} errorList
	 */
	clearTipError: function(element) {
		ZZtips.hide();
	}
}

/**
 * 时间对象的格式化。将日期对象转换为指定格式的字符串
 */
Date.prototype.format = function(format) {
	/*
	 * eg:format="yyyy-MM-dd hh:mm:ss";
	 */
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
		// millisecond
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}

	for (var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1
							? o[k]
							: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

/**
 * 加载页面.
 * @param boxid 容器id
 * @param url 地址 不含CONTEXT_PATH
 * @param param 参数列表,json形式.
 */
function $jload(boxid, url, param) {
	if ($('#' + boxid).length == 0) { 
		return;
	}
	
	// LOAD_IMAGE 位于common.js 中.
	$('#' + boxid).show().html(LOAD_IMAGE).load(CONTEXT_PATH + url, param, function() {SysStyle.setDataGridStyle();});
}

function $loadTree(url, treeId, generateCheckbox, openItem){
	// 树形初始化
	$.post(CONTEXT_PATH + url, function(data){
		// 先清空原加载的html，再加载xml文档
		$('#' + treeId).html('');
		
		tree = new dhtmlXTreeObject(treeId, "100%", "100%", 0);
		tree.setImagePath("../../js/tree/imgs/csh_dhx_skyblue/");
		if (generateCheckbox) {
			tree.enableCheckBoxes(1);
			tree.enableThreeStateCheckboxes(true);
		}
		tree.loadXMLString(data);
		
		// 展开树形
		if (openItem) {
			for (var i = 1; i <= 9; i++) {
				tree.openAllItems('0' + i);
			}
			tree.openAllItems('10');
			tree.openAllItems('11');
			tree.openAllItems('12');
		}
	}, 'xml');
}

/**
 * 通过form提交相应链接
 */
function submitUrl(formId, action, msg){
	if (!confirm(msg)) {
		return;
	}
	var oldAction = $('#' + formId).attr('action');
	$('#' + formId).attr('action', CONTEXT_PATH + action).submit();
}
/**
 * 通过模态框打开
 */
function cancel(url, msg, id){
	if (!confirm(msg)) {
		return false;
	}
	openContextDialog(url, true, id, '450px', '240px');
}

/**
 * 公用的选择器
 */
Selector = {
	/**
	 * 选择机构
	 */
	selectBranch: function(id, hidden, radio, branchTypes, func, setMode, parent){
		$("#" + id).mulitselector({
			title:'请选择机构',
			hidden: hidden,
			radio: radio,
			page: '/pages/branch/showSelect.do',
			param: {'branchTypes': branchTypes, 'radio': radio, 'callId': callId(), 'setMode':setMode, 'parent':parent},
			callback: function(){
				searchBranch(1);
			},
			exitFunc: function(){
				if (typeof func == 'function') {
					func();
				}
			}
		});
	},
	
	/**
	 * 选择代理
	 */
	selectProxy: function(id, hidden, radio, cardBranch, proxyType) {
		$("#" + id).mulitselector({
			title:'请选择代理',
			hidden: hidden,
			radio: radio,
			page: '/pages/proxy/showSelect.do',
			param: {'cardBranch':cardBranch, 'proxyType': proxyType, 'radio': radio, 'callId': callId()},
			callback: function(){
				searchProxy(1);
			}
		});
	},
	
	/**
	 * 选择商户
	 */
	selectMerch: function(id, hidden, radio, card_BranchNo, proxyId, group_CardBranch, _manageBranch, func, cardBranch, _cardBranchNotLimit){
		if (isEmpty(proxyId)){
			proxyId = "";
		}
		$("#" + id).mulitselector({
			title:'请选择商户',
			hidden: hidden,
			radio: radio,
			page: '/pages/merch/showSelect.do',
			param: {'radio': radio, 'callId': callId(), 'card_BranchNo':card_BranchNo, 'proxyId':proxyId, 
				'group_CardBranch':group_CardBranch, '_manageBranch':_manageBranch, 'cardBranch':cardBranch,
				'_cardBranchNotLimit':_cardBranchNotLimit},
			callback: function(){searchMerch(1);},
			exitFunc: function(){
				if (typeof func == 'function') {
					func();
				}
			}
		});
	},
	
	/**
	 * 选择终端
	 */
	selectTerminal: function(id, hidden, radio, cardBranch, merchIds, func){
		$("#" + id).mulitselector({
			title:'请选择终端',
			hidden: hidden,
			radio: radio,
			page: '/pages/terminal/showSelect.do',
			param: {'radio': radio, 'callId': callId(), 'formMap.cardBranch':cardBranch, 'formMap.merchIds':merchIds},
			callback: function(){searchTerminal(1);},
			exitFunc: function(){
				if (typeof func == 'function') {
					func();
				}
			}
		});
	},
	
	/**
	 * 选择商户组
	 */
	selectMerchGroup: function(id, hidden, radio){
		$("#" + id).mulitselector({
			title:'请选择商户组',
			hidden: hidden,
			radio: radio,
			page: '/pages/merchGroup/showSelect.do',
			param: {'radio': radio, 'callId': callId()},
			callback: function(){searchMerchGroup(1);}
		});
	},
	
	/**
	 * 选择卡号ID
	 */
	selectCard: function(id, hidden, radio, func, branchCode){
		$("#" + id).mulitselector({
			title:'请选择卡号ID',
			hidden: hidden,
			radio: radio,
			page: '/freeze/showSelect.do',
			param: {'radio': radio, 'branchCode':branchCode, 'callId': callId()},
			callback: function(){searchCard(1);},
			exitFunc: function(){
				if (typeof func == 'function') {
					func();
				}
			}
		});
	},
	
	/**
	 * 选择卡BIN
	 */
	selectCardBin: function(id, hidden, radio, cardIssuerBin, merchIdBin, func){
		$("#" + id).mulitselector({
			title:'请选择卡BIN',
			hidden: hidden,
			radio: radio,
			page: '/addCardBin/showSelect.do',
			param: {'radio': radio, 'cardIssuerBin':cardIssuerBin, 'merchIdBin':merchIdBin,'callId': callId()},
			callback: function(){searchCardBin(1);},
			exitFunc: function(){
				if (typeof func == 'function') {
					func();
				}
			}
		});
	},
	
	/**
	 * 选择卡子类型
	 */
	selectCardSubclass: function(id, hidden, radio, merchantNo, cardIssuerNo, sellBranch, cardType, func){
		$("#" + id).mulitselector({
			title:'请选择卡子类型',
			hidden: hidden,
			radio: radio,
			page: '/cardSubClass/showSelect.do',
			param: {'radio': radio,'merchantNo':merchantNo, 'cardIssuerNo':cardIssuerNo,'sellBranch':sellBranch, 'cardType':cardType, 'callId': callId()},
			callback: function(){searchCardSubclass(1);},
			exitFunc: function(){
				if (typeof func == 'function') {
					func();
				}
			}
		});

	},

	/**
	 * 选择购卡客户
	 */
	selectCardCustomer: function(id, hidden, radio, cardType, binNo, func){
		$("#" + id).mulitselector({
			title:'请选择购卡客户',
			hidden: hidden,
			radio: radio,
			page: '/customerRebateMgr/customerRebateReg/showSelect.do',
			param: {'cardType': cardType, 'cardBin.binNo':binNo, 'radio': radio, 'callId': callId()},
			callback: function(){searchCardCustomer(1);},
			exitFunc: function(){
				if (typeof func == 'function') {
					func();
				}
			}
		});
	},

	/**
	 * 通用购卡客户选择器
	 */
	selectCardCustomerOther: function(id, hidden, radio, func){
		$("#" + id).mulitselector({
			title:'请选择购卡客户',
			hidden: hidden,
			radio: radio,
			page: '/customerRebateMgr/cardCustomer/showSelect.do',
			param: {'radio': radio, 'callId': callId()},
			callback: function(){searchCardCustomerOther(1);},
			exitFunc: function(){
				if (typeof func == 'function') {
					func();
				}
			}
		});
	},

	/**
	 * 选择地区
	 */
	selectArea: function(id, hidden, radio, func){
		$("#" + id).mulitselector({
			title:'请选择地区',
			hidden: hidden,
			radio: radio,
			page: '/area/showSelect.do',
			param: {'radio': radio, 'callId': callId()},
			callback: function(){searchArea(1);},
			exitFunc: function(){
				if (typeof func == 'function') {
					func();
				}
			}
		});
	},

	/**
	 * 单机产品卡样
	 * 参数：hiddenBranchCode：分支机构号 plandNo：套餐编号
	 */
	selectStyle: function(id, hidden, radio, hiddenBranchCode, planNo, hiddenCardBranch, func){
		$("#" + id).mulitselector({
			title:'请选择单机产品卡样',
			hidden: hidden,
			radio: radio,
			page: '/pages/singleProduct/style/showSelect.do',
			param: {'radio': radio, 'hiddenBranchCode': hiddenBranchCode, 'planNo': planNo, 'hiddenCardBranch': hiddenCardBranch, 'callId': callId()},
			callback: function(){searchStyle(1);},
			exitFunc: function(){
				if (typeof func == 'function') {
					func();
				}
			}
		});
	},

	/**
	 * 单机产品套餐
	 */
	selectModel: function(id, hidden, radio, hiddenBranchCode, func){
		$("#" + id).mulitselector({
			title:'请选择单机产品套餐',
			hidden: hidden,
			radio: radio,
			page: '/pages/singleProduct/model/showSelect.do',
			param: {'radio': radio, 'hiddenBranchCode': hiddenBranchCode, 'callId': callId()},
			callback: function(){searchModel(1);},
			exitFunc: function(){
				if (typeof func == 'function') {
					func();
				}
			}
		});
	},
	
	/**
	 * 会员
	 */
	selectMemb: function(id, hidden, radio, hiddenCardIssuer, func){
		$("#" + id).mulitselector({
			title:'请选择会员',
			hidden: hidden,
			radio: radio,
			page: '/vipCard/membAppointReg/showSelect.do',
			param: {'radio': radio, 'hiddenCardIssuer': hiddenCardIssuer, 'callId': callId()},
			callback: function(){searchMemb(1);},
			exitFunc: function(){
				if (typeof func == 'function') {
					func();
				}
			}
		});
	},
	
	/**
	 * 选择银行
	 */
	selectBank: function(id, hidden, radio, func){
		$("#" + id).mulitselector({
			title:'请选择银行行号',
			hidden: hidden,
			radio: radio,
			page: '/pages/bankInfo/showSelect.do',
			param: {'radio': radio, 'callId': callId()},
			callback: function(){searchBank(1);},
			exitFunc: function(){
				if (typeof func == 'function') {
					func();
				}
			}
		});
	}
}

/**
 * 查询选择机构的通用方法
 */
function searchBranch(pageNum){
	if (!pageNum) {
		pageNum = 1;
	}
	var branchCode = $('#idSelectBranchCode').val();
    var branchName = $('#idSelectBranchName').val();
    var radio = $('#idRadio').val();
    var branchTypes = $('#idBranchTypes').val();
    var setMode = $('#idSetMode').val();
    var parent = $('#idParents').val();
    if (isEmpty(setMode) || setMode == 'undefined'){
    	setMode = "";
    }
    if (isEmpty(parent) || parent == 'undefined'){
    	parent = "";
    }
	
	$('#idSelectData').html(LOAD_IMAGE).load(CONTEXT_PATH + '/pages/branch/select.do', {
		'branch.branchCode':branchCode, 'branch.branchName':branchName, 'radio':radio,
		'branchTypes':branchTypes, 'setMode':setMode, 'parent':parent,
		'pageNumber':pageNum, "callId":callId()}, function(){
			Popupselector.initPageSelect();
	});
}

/**
 * 查询选择机构的通用方法
 */
function searchProxy(pageNum){
	if (!pageNum) {
		pageNum = 1;
	}
	var branchCode = $('#idSelectBranchCode').val();
    var branchName = $('#idSelectBranchName').val();
    var radio = $('#idRadio').val();
    var cardBranch = $('#cardBranch').val();
    var proxyType = $('#proxyType').val();
    
	
	$('#idSelectData').html(LOAD_IMAGE).load(CONTEXT_PATH + '/pages/proxy/select.do', {
		'branchInfo.branchCode':branchCode, 'branchInfo.branchName':branchName, 'radio':radio,
		'cardBranch':cardBranch, 'proxyType': proxyType, 'pageNumber':pageNum, "callId":callId()}, function(){
			Popupselector.initPageSelect();
	});
}

/**
 * 查询选择商户的通用方法
 */
function searchMerch(pageNum){
	if (!pageNum) {
		pageNum = 1;
	}
	var merchId = $('#idSelectMerchId').val();
    var merchName = $('#idSelectMerchName').val();
    var branchCode = $('#idSelectBranchCode').val();
    var proxyId = $('#idProxyId').val();
    var _currCode_Sel = $('#id_currCode_Sel').val();
    var group_CardBranch = $('#idGroup_CardBranch').val();
    var _manageBranch = $('#_id_ManageBranch').val();
    var cardBranch = $('#id_cardBranch').val();
    var _cardBranchNotLimit = $('#_id_cardBranchNotLimit').val();
    if (isEmpty(branchCode) || branchCode == 'undefined'){
    	branchCode = "";
    }
    if (isEmpty(proxyId) || proxyId == 'undefined'){
    	proxyId = "";
    }
    if (isEmpty(_currCode_Sel) || _currCode_Sel == 'undefined'){
    	_currCode_Sel = "";
    }
    if (isEmpty(group_CardBranch) || group_CardBranch == 'undefined'){
    	group_CardBranch = "";
    }
    if (isEmpty(_manageBranch) || _manageBranch == 'undefined'){
    	_manageBranch = "";
    }
    if (isEmpty(cardBranch) || cardBranch == 'undefined'){
    	cardBranch = "";
    }
    if (isEmpty(_cardBranchNotLimit) || _cardBranchNotLimit == 'undefined'){
    	_cardBranchNotLimit = "";
    }
    var radio = $('#idRadio').val();
	$('#idSelectData').html(LOAD_IMAGE).load(CONTEXT_PATH + '/pages/merch/select.do', {
		'merchInfo.merchId':merchId, 'merchInfo.merchName':merchName, 'card_BranchNo':branchCode, 'proxyId':proxyId, 
		'_currCode_Sel':_currCode_Sel,'radio':radio, 'group_CardBranch':group_CardBranch, '_manageBranch':_manageBranch,
		'cardBranch':cardBranch, '_cardBranchNotLimit':_cardBranchNotLimit, 'pageNumber':pageNum, "callId":callId()}, function(){
			Popupselector.initPageSelect();
	});
}
/**
 * 查询选择终端的通用方法
 */
function searchTerminal(pageNum){
	if (!pageNum) {
		pageNum = 1;
	}
	var cardBranch = $('#cardBranch').val();
	var merchIds = $('#merchIds').val();
	var termId = $('#termId').val();
	var merchName = $('#merchName').val();
	if (isEmpty(cardBranch) || cardBranch == 'undefined'){
		cardBranch = "";
	}
	if (isEmpty(merchIds) || merchIds == 'undefined'){
		merchIds = "";
	}
	if (isEmpty(termId) || termId == 'undefined'){
		termId = "";
	}
	if (isEmpty(merchName) || merchName == 'undefined'){
		merchName = "";
	}
	var radio = $('#idRadio').val();
	
	$('#idSelectData').html(LOAD_IMAGE).load(CONTEXT_PATH + '/pages/terminal/select.do', {
		'formMap.cardBranch':cardBranch, 'formMap.merchIds':merchIds, 'formMap.termId':termId, 
		'formMap.merchName':merchName, 
		'radio':radio, 'pageNumber':pageNum, "callId":callId()}, function(){
			Popupselector.initPageSelect();
		});
}

/**
 * 查询选择商户组的通用方法
 */
function searchMerchGroup(pageNum){
	if (!pageNum) {
		pageNum = 1;
	}
	var groupId = $('#idSelectGroupId').val();
    var groupName = $('#idSelectGroupName').val();
    var radio = $('#idRadio').val();
	$('#idSelectData').html(LOAD_IMAGE).load(CONTEXT_PATH + '/pages/merchGroup/select.do', {
		'merchGroup.groupId':groupId, 'merchGroup.groupName':groupName, 'radio':true,
		 'pageNumber':pageNum, "callId":callId()}, function(){
			Popupselector.initPageSelect();
	});
}

/**
 * 查询选择卡号的通用方法
 */
function searchCard(pageNum){
	if (!pageNum) {
		pageNum = 1;
	}
	var cardId = $('#idSelectCardId').val();
    var acctId = $('#idSelectAcctId').val();
    var radio = $('#idRadio').val();
    var branchCode = $('#idBranchCode').val();
    if (branchCode == 'undefined' || branchCode == undefined) {
    	branchCode = "";
    }
	$('#idSelectData').html(LOAD_IMAGE).load(CONTEXT_PATH + '/freeze/select.do', {
		'freezeReg.acctId':acctId, 'freezeReg.cardId':cardId, 'branchCode':branchCode, 'radio':radio,
		 'pageNumber':pageNum, "callId":callId()}, function(){
			Popupselector.initPageSelect();
	});
}

/**
 * 查询选择卡BIN通用方法
 */
function searchCardBin(pageNum){
	if (!pageNum) {
		pageNum = 1;
	}
	var binNo = $('#idSelectCardBinId').val();
    var binName = $('#idSelectCardBinName').val();
    var cardIssuerBin = $('#idCardIssuerBin').val();
    var merchIdBin = $('#idMerchIdBin').val();
    var radio = $('#idRadio').val();
    if (cardIssuerBin == 'undefined' || cardIssuerBin == undefined ){
    	cardIssuerBin = "";
    }
    if (merchIdBin == 'undefined' || merchIdBin == undefined ){
    	merchIdBin = "";
    }
	$('#idSelectData').html(LOAD_IMAGE).load(CONTEXT_PATH + '/addCardBin/select.do', {
		'cardBin.binNo':binNo, 'cardBin.binName':binName, 'cardIssuerBin':cardIssuerBin, 'radio':radio,
		'merchIdBin':merchIdBin, 'pageNumber':pageNum, "callId":callId()}, function(){
			Popupselector.initPageSelect();
	});
}

/**
 * 查询不同类型卡的客户返利的购卡客户的通用方法
 */
function searchCardCustomer(pageNum){
	if (!pageNum) {
		pageNum = 1;
	}
	var cardCustomerId = $('#idSelectCardCustomerId').val();
    var cardCustomerName = $('#idSelectCardCustomerName').val();
    var binNo = $('#idCardBin').val();
    var radio = $('#idRadio').val();
    var cardType = $('#idCardType').val();
	$('#idSelectData').html(LOAD_IMAGE).load(CONTEXT_PATH + '/customerRebateMgr/customerRebateReg/select.do', {
		'cardType':cardType, 'cardCustomer.cardCustomerId':cardCustomerId,
		'cardCustomer.cardCustomerName':cardCustomerName, 'cardBin.binNo':binNo, 'radio':radio,
		'pageNumber':pageNum, "callId":callId()}, function(){
			Popupselector.initPageSelect();
	});
}

/**
 * 购卡客户选择器
 */
function searchCardCustomerOther(pageNum){
	if (!pageNum) {
		pageNum = 1;
	}
	var cardCustomerId = $('#idSelectCardCustomerId').val();
    var cardCustomerName = $('#idSelectCardCustomerName').val();
    var radio = $('#idRadio').val();
    var cardBranch = $('#idCardBranch').val();
    if (cardBranch == 'undefined' || cardBranch == undefined ){
    	cardBranch = "";
    }
	$('#idSelectData').html(LOAD_IMAGE).load(CONTEXT_PATH + '/customerRebateMgr/cardCustomer/select.do', {
		'cardCustomer.cardCustomerId':cardCustomerId, 'cardCustomer.cardCustomerName':cardCustomerName, 
		'cardCustomer.cardBranch':cardBranch, 'radio':radio,
		'pageNumber':pageNum, "callId":callId()}, function(){
			Popupselector.initPageSelect();
	});
}

/**
 * 查询选择卡子类型通用方法
 */
function searchCardSubclass(pageNum){
	if (!pageNum) {
		pageNum = 1;
	}
	var cardSubclass = $('#idSelectCardSubclass').val();
    var cardSubclassName = $('#idSelectCardSubclassName').val();
    var radio = $('#idRadio').val();
    var merchantNo = $('#idMerchantNo').val();
    var cardIssuerNo = $('#idCardIssuerNo').val();
    var sellBranch = $('#idSellBranch').val();
    var cardType = $('#idCardType').val();
    if (merchantNo == 'undefined' || merchantNo == undefined ){
    	merchantNo = "";
    }
    if (cardIssuerNo == 'undefined' || cardIssuerNo == undefined ){
    	cardIssuerNo = "";
    }
    if (sellBranch == 'undefined' || sellBranch == undefined ){
    	sellBranch = "";
    }
    if (cardType == 'undefined' || cardType == undefined ){
    	cardType = "";
    }
	$('#idSelectData').html(LOAD_IMAGE).load(CONTEXT_PATH + '/cardSubClass/select.do', {
		'cardSubClassDef.cardSubclass':cardSubclass, 'cardSubClassDef.cardSubclassName':cardSubclassName, 
		'radio':radio, 'merchantNo':merchantNo, 'cardIssuerNo':cardIssuerNo,'sellBranch':sellBranch,
		'cardType':cardType, 'pageNumber':pageNum, "callId":callId()}, function(){
			Popupselector.initPageSelect();
	});
}

/**
 * 查询选择地区通用方法
 */
function searchArea(pageNum){
	if (!pageNum) {
		pageNum = 1;
	}
    var areaParent = $('#idSelectAreaParent').val();
	var areaCityCode = $('#idSelectAreaCityCode').val();
    var areaName = $('#idSelectAreaName').val();
    var radio = $('#idRadio').val();
	$('#idSelectData').html(LOAD_IMAGE).load(CONTEXT_PATH + '/area/select.do', {
		'area.cityCode':areaCityCode, 'area.areaName':areaName, 'area.parent':areaParent, 
		'radio':radio, 'pageNumber':pageNum, "callId":callId()}, function(){
			Popupselector.initPageSelect();
	});
}

function loadCityForSelect() {
	var provinceName = $('#idSelectAreaParent').val();
	$('#idSelectAreaCityCode').load(CONTEXT_PATH + '/area/loadCity.do',{'provinceName':provinceName, 'callId':callId()});
}

function loadCityForSelectBank() {
	var provinceName = $('#idSelectProvince').val();
	$('#idSelectCityCode').load(CONTEXT_PATH + '/area/loadCity.do',{'provinceName':provinceName, 'callId':callId()});
}

/**
 * 查询选择银行通用方法
 */
function searchBank(pageNum){
	if (!pageNum) {
		pageNum = 1;
	}
	var bankType = $('#idSelectBankType').val();
    var areaParent = $('#idSelectProvince').val();
    var bankName = $('#idSelectBankName').val();
    var cityCode = $('#idSelectCityCode').val();
    var radio = $('#idRadio').val();
    if (bankType == 'undefined' || bankType == undefined ){
    	bankType = "";
    }
    if (areaParent == 'undefined' || areaParent == undefined ){
    	areaParent = "";
    }
    if (bankName == 'undefined' || bankName == undefined ){
    	bankName = "";
    }
    if (cityCode == 'undefined' || cityCode == undefined ){
    	cityCode = "";
    }
	$('#idSelectData').html(LOAD_IMAGE).load(CONTEXT_PATH + '/pages/bankInfo/select.do', {
		'bankInfo.bankType':bankType, 'bankInfo.parent':areaParent, 'bankInfo.bankName':bankName, 
		'bankInfo.addrNo':cityCode, 'radio':radio, 'pageNumber':pageNum, "callId":callId()}, function(){
			Popupselector.initPageSelect();
	});
}

/**
 * 查询选择单机产品卡样通用方法
 */
function searchStyle(pageNum){
	if (!pageNum) {
		pageNum = 1;
	}
    var cardExampleId = $('#idSelectCardExampleId').val();
	var cardExampleName = $('#idSelectcardExampleName').val();
    var branchCode = $('#idHiddenStyleBranchCode').val();
    var planNo = $('#idHiddenPlanNo').val();
    var hiddenCardBranch = $('#idHiddenCardBranch').val();
    if (branchCode == 'undefined' || branchCode == undefined ){
    	branchCode = "";
    }
    if (planNo == 'undefined' || planNo == undefined ){
    	planNo = "";
    }
    if (hiddenCardBranch == 'undefined' || hiddenCardBranch == undefined ){
    	hiddenCardBranch = "";
    }
    
    var radio = $('#idRadio').val();
	$('#idSelectData').html(LOAD_IMAGE).load(CONTEXT_PATH + '/pages/singleProduct/style/select.do', {
		'cardExampleDef.cardExampleId':cardExampleId, 'cardExampleDef.cardExampleName':cardExampleName, 
		'hiddenBranchCode':branchCode, 'planNo':planNo, 'hiddenCardBranch':hiddenCardBranch,
		'radio':radio, 'pageNumber':pageNum, "callId":callId()}, function(){
			Popupselector.initPageSelect();
	});
}

/**
 * 查询选择单机产品套餐通用方法
 */
function searchModel(pageNum){
	if (!pageNum) {
		pageNum = 1;
	}
    var planId = $('#idSelectPlanId').val();
	var planName = $('#idSelectPlanName').val();
    var branchCode = $('#idHiddenModelBranchCode').val();
    if (branchCode == 'undefined' || branchCode == undefined ){
    	branchCode = "";
    }
    
    var radio = $('#idRadio').val();
	$('#idSelectData').html(LOAD_IMAGE).load(CONTEXT_PATH + '/pages/singleProduct/model/select.do', {
		'planDef.planId':planId, 'planDef.planName':planName, 'hiddenBranchCode':branchCode, 
		'radio':radio, 'pageNumber':pageNum, "callId":callId()}, function(){
			Popupselector.initPageSelect();
	});
}

/**
 * 查询会员通用方法
 */
function searchMemb(pageNum){
	if (!pageNum) {
		pageNum = 1;
	}
    var membInfoRegId = $('#id_membInfoRegId').val();
    var custName = $('#id_custName').val();
    var membInfoId = $('#id_membId').val();
    var cardIssuer = $('#id_hiddenCardIssuer').val();
    if (cardIssuer == 'undefined' || cardIssuer == undefined ){
    	cardIssuer = "";
    }
    
    var radio = $('#idRadio').val();
	$('#idSelectData').html(LOAD_IMAGE).load(CONTEXT_PATH + '/vipCard/membAppointReg/select.do', {
		'membInfoReg.membInfoRegId':membInfoRegId, 'membInfoReg.custName':custName,'membInfoReg.membInfoId':membInfoId, 'membInfoReg.cardIssuer':cardIssuer, 
		'radio':radio, 'pageNumber':pageNum, "callId":callId()}, function(){
			Popupselector.initPageSelect();
	});
}


function openPrint() {
    window.open(CONTEXT_PATH + '/common/print.jsp', 'print');
}
