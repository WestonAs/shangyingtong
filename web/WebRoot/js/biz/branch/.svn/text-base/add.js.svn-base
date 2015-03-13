$(function(){
	//SysStyle.initSelect();
	
	// 此方法已经无效，现在添加机构必须添加管理员
	$(':radio[name="addAdmin"]').click(function(){
		var value = $(this).val();
		if (value == 2) {
			$('td[id^="idAddAdmin_"]').hide();
			$('#branchAdmin').attr('disabled', 'true');
		} else {
			$('td[id^="idAddAdmin_"]').show();
			$('#branchAdmin').removeAttr('disabled');
		}
	});
	
	// 输入完机构简称，自动生成管理员用户
	$('#id_branchAbbname').blur(function(){
		var value = $(this).val();
		if(!isEmpty(value)){
			$.post(CONTEXT_PATH + '/pages/branch/getAdminUserId.do', {'branchAbbname':value, 'fromMerch':'N', 'callId':callId()}, function(data){
				$('#id_branchAdmin').val(data.userId);
			}, 'json');
		}
	});
	
	// 检查用户名是否已经存在
	$('#id_branchAdmin').blur(function(){
		$('#idbranchAdmin_field').html('请输入数字字符');
		checkUserId();
	});
	
	// 类型切换时显示
	$('#idType').change(function(){
		var value = $(this).val();
		$('#idManageBranchList').html('');
		$('#id_card_develop').html('');
		
		if (value == '20' || value == '12'){
			// 发卡机构需要选择清算方式
			$('#id_SetMode').removeAttr('disabled');
			$('td[id^="td_setMode"]').show();
			selectSetMode();
		} 
		else{
			$('td[id^="td_setMode"]').hide();
			$('#id_SetMode').attr('disabled', 'true');
		}
		
		// 售卡代理需要选择是否独立清算
		if (value == '22'){
			$('#id_sale').show();
			$('#id_sale_setle').removeAttr('disabled');
		} else {
			$('#id_sale').hide();
			$('#id_sale_setle').attr('disabled', 'true');
		}
		
		// 发卡机构可以同时添加商户，要设置发展方
		if (value == '20'){
			$('#id_card').show();
			$('#id_card_develop').removeAttr('disabled');
			$('#id_trustAmt').removeAttr('disabled');
			$('#idAsMerchTr').show();
			
			$('td[id^="td_superBranchCode_"]').show();
			$('#idBranchName').removeAttr('disabled');
			$('#idBranchCode').removeAttr('disabled');
			var loginRoleType = $('#id_loginRoleType').val();
			var loginBranchCode = $('#id_loginBranchCode').val();
			if(loginRoleType == '00'){
				Selector.selectBranch('idBranchName', 'idBranchCode', true, '20');
			} else if(loginRoleType == '01'){
				Selector.selectBranch('idBranchName', 'idBranchCode', true, '20', '', '', loginBranchCode);
			} else {
				Selector.selectBranch('idBranchName', 'idBranchCode', true, '20');
			}
		} else {
			$('#id_card').hide();
			$('#id_card_develop').attr('disabled', 'true');
			$('#id_trustAmt').attr('disabled', 'true');
			$('#idAsMerchTr').hide();
			
			$('td[id^="td_superBranchCode_"]').hide();
			$('#idBranchCode').attr('disabled', 'true');
			$('#idBranchName').attr('disabled', 'true');
		}
		
		// 如果是分支机构或售卡代理的话，需要设置级别 || value == '22'
		if(value == '01'){
			$('td[id^="td_branchLevel_"]').show();
			$('#id_branchLevel').removeAttr('disabled');
			$('#id_branchLevel').load(CONTEXT_PATH + '/pages/branch/loadBranchLevel.do', {'type':value, 'callId':callId});
		} else {
			$('td[id^="td_branchLevel_"]').hide();
			$('#id_branchLevel').attr('disabled', 'true');
		}
		
		if (value == '20'){
			// 绑定事件：根据管理机构得到发展机构列表
			$('#idManageBranchList').change(loadDevelopsByManager);
			
			$('#idManageBranchList').load(CONTEXT_PATH + '/pages/branch/getManageBranch.do', {type:value, 'callId':callId()});
			
			$('#id_branchLevel').unbind();
		}
		// 如果是分支机构的话，需要设置级别
		else if(value == '01'){
			$('#id_branchLevel').bind('change', changeLevel);
		} else {
		 	$('#id_branchLevel').unbind();
		 	$('#idManageBranchList').load(CONTEXT_PATH + '/pages/branch/getManageBranch.do', {type:value, 'callId':callId()});
			/*$('#id_card').hide();
			$('#id_card_develop').attr('disabled', 'true');
			$('#id_trustAmt').attr('disabled', 'true');
			
			$('#idAsMerchTr').hide();*/
		}
		
		/*
		if (value == '21' || value == '22') {
			// 发卡机构代理业需要设置发展方
			$('#id_proxy').show();
			$('#id_card_proxy').removeAttr('disabled');
		} else {
			$('#id_proxy').hide();
			$('#id_card_proxy').attr('disabled', 'true');
		}
		*/
		
		/*
		if (value == '22'){
			// 售卡代理允许自定义权限
			$('#idSaleProxyTr').show();
		} else {
			$('#idSaleProxyTr').hide();
		}
		
		$(':radio[name="definePrivilege"]').click(function(){
			var value = $(this).val();
			if (value == 'N') {
				$('#idSaleProxyTreeTr').hide();
			} else {
				$('#idSaleProxyTreeTr').show();
				loadTree();
				
			}
		});*/
		
		/*
		
		// 售卡代理需要选择是否独立清算
		if (value == '22'){
			$('#id_sale').show();
			$('#id_sale_setle').removeAttr('disabled');
		} else {
			$('#id_sale').hide();
			$('#id_sale_setle').attr('disabled', 'true');
		}
		
		//$('#idManageBranchList').load(CONTEXT_PATH + '/pages/branch/getManageBranch.do', {type:value, 'callId':callId()});
		
		if (value == '20' || value == '12'){
			// 营运分支机构需要选择清算方式
			$('#id_SetMode').removeAttr('disabled');
			$('td[id^="td_setMode"]').show();
			selectSetMode();
		} 
		else{
			$('td[id^="td_setMode"]').hide();
			$('#id_SetMode').attr('disabled', 'true');
		}
		*/
	});
	
	Selector.selectArea('idArea_sel', 'idArea', true);
	//Selector.selectArea('idAccAreaCode_sel', 'idAccAreaCode', true);
	Selector.selectBank('idBank_sel', 'idBank', true, function(){
		loadAreaCode($('#idBank').val());
	});
});


/** 根据选择的管理机构加载相关的发展方 */
function loadDevelopsByManager(){
	if( !($('#id_card_develop').attr('disabled')) ){
		var managerBranch = $(this).val();
		$('#id_card_develop').load(CONTEXT_PATH + '/pages/branch/getDevelopList.do', {'manageBranch':managerBranch, 'callId':callId()});
	}
}

/** 根据银行的行号来取得地区码，作为开户行地区码 */
function loadAreaCode(bankNo){
	$.post(CONTEXT_PATH + '/pages/branch/loadAccAreaCode.do', {'bankNo':bankNo, 'callId':callId()}, 
		function(data){
			$('#idAccAreaCode_sel').val(data.accAreaName);
			$('#idAccAreaCode').val(data.accAreaCode);
		}, 'json');
}

 function selectSetMode(){
 	$('#id_SetMode').each(function(){
		var cssName = $(this).attr('class');
		if (cssName != undefined && cssName.indexOf('required') > -1) {
			var size = $(this).find('option').size();
			if (size <= 1){
				return;
			}
			try {
				var headerValue = $(this).find('option:eq(0)').val();
				var secondValue = $(this).find('option:eq(1)').val();
				// 默认选择成本模式
				if (secondValue == '2'){
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

function loadTree(){
	$loadTree('/pages/role/initTreeByAddSaleProxy.do', 'treebox_tree', true);
}

function checkTree(){
	var value = $('#idType').val();
	if (value != '22') {return true;}
	var define = $(':radio[name="definePrivilege"][checked]').val();
	if (define == 'N') {
		return true;
	}
	var privilege = tree.getAllCheckedBranches();
	if (privilege == null || privilege == undefined || privilege == "") {
		alert("请选择权限!");
		return false;
	}
	$('#privileges').val(privilege);
	return true;
}

function checkUserId() {
	var flag = false;
	var value = $('#id_branchAdmin').val();
	if (isEmpty(value)){
		return;
	}
	$.ajax({
		url: CONTEXT_PATH + '/pages/branch/checkUserId.do',
		data: {'branchAdmin':value},
		cache: false,
		async: false,
		type: 'POST',
		dataType: 'json',
		success: function(result){
			if (result.isExist){
				flag = false;
				$('#idbranchAdmin_field').html('该用户名已存在').addClass('error_tipinfo').show();
				$('#input_btn2').attr('disabled', 'true');
			} else {
				flag = true;
				clearCarBinError();
			}
		}
	});
	return flag;
}
function clearCarBinError(){
	$('#idbranchAdmin_field').removeClass('error_tipinfo').html('请输入数字字符');
	$('#input_btn2').removeAttr('disabled');
}
function checkAllParams() {
	if(isEmpty($('#id_branchAdmin').val())){
		$('#inputForm').submit();
	}
	if(checkTree() && checkUserId()){
		if($('#inputForm').validate().form()){
			$('#inputForm').submit();
			$('#input_btn2').attr('disabled', 'true');
			//window.parent.showWaiter();
			$("#loadingBarDiv").css("display","inline");
			$("#contentDiv").css("display","none");
		}
	}
}

function changeLevel() {
	var branchLevel = $('#id_branchLevel').val();
	var value = $('#idType').val();
	$('#idManageBranchList').load(CONTEXT_PATH + '/pages/branch/getManageBranch.do', {type:value, 'branchLevel':branchLevel, 'callId':callId()});
}