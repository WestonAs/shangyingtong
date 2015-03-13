/**
 *  ajax查询发卡机构的商户集群信息 
 */
function ajaxFindMerchClusterInfos(cardIssuer){
	$("#merchClusterId").val("").empty();//清空商户集群
	
	$.getJSON(CONTEXT_PATH + "/ajax/ajaxFindMerchClusterInfos.do",{'formMap.cardIssuer':cardIssuer, 'callId':callId()}, 
		function(json){
			$("#merchClusterId").append("<option value=''>--请选择--</option>");
			if(json.merchClusterInfos!=null && json.merchClusterInfos.length>0){
				for(i in json.merchClusterInfos){
					var option = "<option value='"+json.merchClusterInfos[i].merchClusterId +"'> "
						+json.merchClusterInfos[i].merchClusterName+"</option>";
					$("#merchClusterId").append(option);
				}
			}
		}
	);
}

/**
 *  下载 产生Excel文件 
 *  @param isTransSummary 是否是产生交易汇总excel
 */
function generateExcel(isTransSummary){
	if(!validateForm()){
		return false;
	}
	$.getJSON(CONTEXT_PATH + "/transQuery/merchClusterHisTrans/ajaxIsGeneratingExcel.do",{'callId':callId()}, 
		function(json){
			if(json.success){
				alert("正在生成商户集群历史交易 明细/汇总 Excel文件，请耐心等待...");
			}else{
				// 提交产生excel的请求
				if(isTransSummary){
					$('#searchForm').attr('action', 'generateSummary.do').submit();
					$('#searchForm').attr('action', 'listSummary.do');
				}else{
					$('#searchForm').attr('action', 'generate.do').submit();
					$('#searchForm').attr('action', 'list.do');
				}
				$('#searchForm').find(":submit").attr('disabled', false);
				$('#searchForm').find(":button").attr('disabled', false);
			}
		}
	);
}

function validateForm() {
	if($("#merchClusterId").val()==""){
		showMsg("请先选择发卡机构商户集群！");
		return false;
	}
	var days = DateDiff($('#startDate').val(), $('#endDate').val());
	if(days > 90){
		showMsg("清算日期跨度最大为90天。");
		return false;
	}
	
	return true;
}