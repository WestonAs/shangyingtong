
function addItem(){
	var count = $('tr[id^="idDetail_"]').size();
	var content = '<tr id="idDetail_'+ (count+1) +'"><td align="center">'+ (count+1) +'</td><td align="center"><input type="text" name="rebateUlimit" value="" id="inputForm_rebateUlimit" /></td><td align="center" ><input type="text" name="rebateRate" value="" id="inputForm_rebateRate" /></td></tr>';
	$('#idLink').before(content);

	// 设置样式
	SysStyle.setFormGridStyle();
}

function deleteItem(){
	var count = $('tr[id^="idDetail_"]').size();
	if (count == 1) {
		alert('必须设置一项');
		return;
	}
	$('tr[id^="idDetail_"]:last').remove();
}

function check(){
	var count = $('tr[id^="idDetail_"]').size();

	for(i=0; i<count; i++){
		var $objU = $('tr[id^="idDetail_"]').eq(i).children().eq(1).children()
		var $objR = $('tr[id^="idDetail_"]').eq(i).children().eq(2).children()
		if((isNaN($objU.val()))||(isNaN($objR.val()))){
			alert("“分段金额上限”、“返利比”请输入数字");
			return false;
		}
		if(($objU.val()=="")||($objU.val() < 0 )){
			alert("分段金额上限不能小于 0 ，请重新输入！");
			return false;
		}
		if(!isDecimal($objU.val(),'14,2')){
			alert("分段金额必须为Num(14,2)");
			return false;
		}
		if(($objR.val()=="")||($objR.val() < 0 )){
			alert("返利比不能小于 0 ，请重新输入！");
			return false;
		}
		if(!isDecimal($objR.val(),'8,3')){
			alert("每张卡返利比%/每张卡返利金额最大为99999.999，且最多有3位小数");
			return false;
		}	
	}
	for(i=1; i<count; i++){
		var $preObj = $('tr[id^="idDetail_"]').eq(i-1).children().eq(1).children()
		var $nextObj = $('tr[id^="idDetail_"]').eq(i).children().eq(1).children()
		var preValue = $preObj.val();
		var nextValue = $nextObj.val();
		if(Number(preValue) > Number(nextValue)){
			alert("后段“分段金额上限”必须大于前段“分段金额上限” ，请重新输入！");
			return false;
		}				
	}
	return true;
}

function submitForm(){
	if(!check()){
		return false;
	}
	
	$("#inputForm").submit();
}