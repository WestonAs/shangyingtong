MainPage = {
	/**
	 * 加载模块顶部按钮
	 */
	loadTopButton: function(parentCode, privilegeCode) {
		var param = {parentCode: parentCode, privilegeCode: privilegeCode};
		$('#topbutton').load(CONTEXT_PATH + "/topbutton.do", param, function(){
			var url = $('#topbutton a:first').attr('href');
			$('#main_area').attr('src', url);
		});
	}
}

function showWaiter(){
	$.blockUI({message: "<div style='float:left;text-align:right;width:40%'><img src='" + CONTEXT_PATH + "/images/ajax_loader.gif' /></div><div style='height:40px;line-height:40px;text-align:left;padding-left:5px;'>正在处理中，请稍候...</div>"});
}

function unblock(){
	$.unblockUI();
}

/**
 * --显示隐藏左侧菜单代码--
 */
function toggleMenu(obj,noid){
	var currObj = $(obj);
	var menuObj = $('#' + noid);
	var display = menuObj.css('display');
	if (menuObj.hasClass('no')) {
		menuObj.removeClass('no');
	} else {
		menuObj.addClass('no');
	}
	menuObj.find("span").addClass("no");
	
	var value = currObj.html();
	if (display == 'none'){
		value = value.replace("+", "-");
		currObj.html(value);
		if (noid.indexOf('menu2') > -1) {
			currObj.addClass('redfont');
		}
		menuObj.show();
	} else {
		value = value.replace("-", "+");
		currObj.html(value);
		if (noid.indexOf('menu2') > -1) {
			currObj.removeClass('redfont');
		}
		menuObj.hide();
	}
}
var fg = true;
function menu_xsyc(){
   if(fg){
       $('#xs').hide();
       $('#yc').attr('src', 'images/xs_icon.gif');
   }else{
       $('#xs').show();
       $('#yc').attr('src', 'images/yc_icon.gif');
   }
   fg = !fg;
}

function logout(){
	window.location.href = CONTEXT_PATH + "/logout.do";
}

function switchRole(roleId) {
	//window.location.replace(CONTEXT_PATH + "/switchRole.do?switchRoleId=" + roleId);
	$.post(CONTEXT_PATH + '/switchRole.do', {'switchRoleId':roleId}, function(){
	});
}

function highColor(id){
	$('#menu a').css('color', '');
	$('#' + id).css('color', '#ff0000');
}
