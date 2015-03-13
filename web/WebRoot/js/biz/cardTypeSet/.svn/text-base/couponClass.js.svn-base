CouponClass = {
	coupnUsageChange: function () {
		var coupnUsage = $('#Id_coupnUsage').val();
		var Id_ruleParam1 = $('#Id_ruleParam1');
		var Id_ruleParam2 = $('#Id_ruleParam2');
		var Id_ruleParam3 = $('#Id_ruleParam3');
		if(coupnUsage=='1'||coupnUsage=='2'){
			$('#td_ruleParam3_1').hide();
			$('#td_ruleParam3_2').hide();
			Id_ruleParam3.attr('disabled',true);
			Id_ruleParam1.removeAttr('disabled');
			Id_ruleParam2.removeAttr('disabled');
			$('#usage_1').show();
			$('#td_ruleParam1_1').show();
			$('#td_ruleParam1_2').show();
			$('#td_ruleParam2_1').show();
			$('#td_ruleParam2_2').show();

		}else if(coupnUsage=='3'){
			$('#td_ruleParam2_1').hide();
			$('#td_ruleParam2_2').hide();
			Id_ruleParam2.attr('disabled',true);
			Id_ruleParam1.removeAttr('disabled');
			$('#td_ruleParam1_1').show();
			$('#td_ruleParam1_2').show();
			Id_ruleParam3.removeAttr('disabled');
			$('#td_ruleParam3_1').show();
			$('#td_ruleParam3_2').show();
		}else{
			$('#td_ruleParam1_1').hide();
			$('#td_ruleParam1_2').hide();
			$('#td_ruleParam2_1').hide();
			$('#td_ruleParam2_2').hide();
			$('#td_ruleParam3_1').hide();
			$('#td_ruleParam3_2').hide();
			Id_ruleParam1.attr('disabled',true);
			Id_ruleParam2.attr('disabled',true);
			Id_ruleParam3.attr('disabled',true);
		}
	},
	coupnUsageForDetail: function () {
		var coupnUsage = $('#Id_coupnUsage').val();
		//alert(coupnUsage);
		if(coupnUsage=='1'){
			$('#td_ruleParam3_1').hide();
			$('#td_ruleParam3_2').hide();
			$('#usage_1').show();
			$('#td_ruleParam1_1').show();
			$('#td_ruleParam1_2').show();
			$('#td_ruleParam2_1').show();
			$('#td_ruleParam2_2').show();
			$('#td_ruleParam4_1').show();
			$('#td_ruleParam4_2').show();
		}else if(coupnUsage=='2'){
			$('#td_ruleParam3_1').hide();
			$('#td_ruleParam3_2').hide();
			//$('#td_ruleParam1_1').hide();
			//$('#td_ruleParam1_2').hide();
			$('#usage_1').hide();
			$('#usage_2').show();
			$('#td_ruleParam1_1').show();
			$('#td_ruleParam1_2').show();
			$('#td_ruleParam2_1').show();
			$('#td_ruleParam2_2').show();
			$('#td_ruleParam4_1').show();
			$('#td_ruleParam4_2').show();
		}else if(coupnUsage=='3'){
			//$('#td_ruleParam1_1').hide();
			//$('#td_ruleParam1_2').hide();
			$('#td_ruleParam1_1').show();
			$('#td_ruleParam1_2').show();
			$('#td_ruleParam2_1').hide();
			$('#td_ruleParam2_2').hide();
			$('#td_ruleParam3_1').show();
			$('#td_ruleParam3_2').show();
			$('#td_ruleParam4_1').show();
			$('#td_ruleParam4_2').show();
		}else{
			$('#td_ruleParam1_1').hide();
			$('#td_ruleParam1_2').hide();
			$('#td_ruleParam2_1').hide();
			$('#td_ruleParam2_2').hide();
			$('#td_ruleParam3_1').hide();
			$('#td_ruleParam3_2').hide();
			$('#td_ruleParam4_1').hide();
			$('#td_ruleParam4_2').hide();
		}
	}
	
};