IssType = {
	issTypeChange: function (id_issName,id_issId,id_issType) {
		$('#'+id_issName).removeMulitselector();
		var issType = $('#'+id_issType).val();
		//发卡机构
		if(issType=='0'){ 
			$('#jinst_td1').hide();
			$('#jinst_td2').hide();
			$('#jinstName').attr('disabled',true);
			$('#Id_jinstId').attr('disabled',true);
		}else if(issType=='1'||issType=='2'){
			$('#jinstName').removeAttr('disabled');
			$('#Id_jinstId').removeAttr('disabled');
			$('#jinstName').show();
			$('#Id_jinstId').show();
			$('#jinst_td1').show();
			$('#jinst_td2').show();
		
			if(issType=='1'){
				Selector.selectMerch(id_issName, id_issId, true);
			}
			if(issType=='2'){
				Selector.selectMerchGroup(id_issName, id_issId, true);
			}
		}else{
			$('#jinst_td1').hide();
			$('#jinst_td2').hide();
			$('#jinstName').attr('disabled',true);
			$('#Id_jinstId').attr('disabled',true);
		}
	}
	
};