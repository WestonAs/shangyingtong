$(function(){
	changeRebateType();
	changeIsCommon();
});

/** 修改返利规则类型  */
function changeIsCommon(){
	if($("#isCommon").val()=="A"){ // POS返利
		$("#rebateType").val("0"); // 设置一次性返利
		$("#rebateType option[value!='0']").attr("disabled","disabled"); // 其他返利模式选项禁止
	} else {
		$("#rebateType option[value!='0']").removeAttr("disabled"); // 其他返利模式选项禁止
	}
	$("#rebateType").change();
}

/** 修改返利方式（一次性还是分期） */
function changeRebateType(){
	if($("#rebateType").val()=="0"){
		$("[name^='rebateRule.period']").attr("disabled", "disabled");
		$("[id^=periodInfoTr]").hide();
	}else{
		$("[name^='rebateRule.period']").removeAttr("disabled");
		$("[id^=periodInfoTr]").show();
	}
}