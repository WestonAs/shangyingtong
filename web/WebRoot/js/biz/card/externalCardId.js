/* *****************************************************************************
 * 本js用于 卡信息（cardInfo）的外部号码（externalCardId）字段 在 页面查询方面 的使用；
 * *****************************************************************************/

$(function(){
	clickUseExternalCardSearch(); //根据外部号码checkbox，初始化externalCardId查询框
});


/** 点击外部号码checkbox的触发函数 */
function clickUseExternalCardSearch(){
	if($('#useExternalCardSearch:checked').length>0){//选中使用外部号码checkbox
		$("#externalCardId").show();
		enableOrDisableElmt($("#externalCardId"), true);
		$("[name^=externalCardCol]").show();
	}else{
		$("#externalCardId").hide();
		enableOrDisableElmt($("#externalCardId"), false);
		$("[name^=externalCardCol]").hide();
	}
}