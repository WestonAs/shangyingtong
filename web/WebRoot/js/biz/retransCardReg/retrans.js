Retrans = {
	couponUsageChange: function () {
		var couponUsage = $('#Id_couponUsage_td2').val();
		var Id_ruleParam1 = $('#Id_ruleParam1');
		//alert(couponUsage);
		// 先用赠券账户
		if(couponUsage=='0'){ 
			$('#depReb_tr').hide();
			$('#deposit_td_1').hide();
			$('#deposit_td_2').hide();
			$('#rebate_td_1').hide();
			$('#rebate_td_2').hide();
			$('#accu_td_1').hide();
			$('#accu_td_2').hide();
			$('#couAccu_tr').show();
			$('#coupon_td_1').show();
			$('#coupon_td_2').show();

		}
		// 先不用赠券账户
		else if(couponUsage=='1'){
			$('#depReb_tr').show();
			$('#deposit_td_1').show();
			$('#deposit_td_2').show();
			$('#rebate_td_1').show();
			$('#rebate_td_2').show();
			$('#couAccu_tr').hide();
			$('#coupon_td_1').hide();
			$('#coupon_td_2').hide();
			$('#accu_td_1').hide();
			$('#accu_td_2').hide();
		}else{
			$('#depReb_tr').hide();
			$('#deposit_td_1').hide();
			$('#deposit_td_2').hide();
			$('#rebate_td_1').hide();
			$('#rebate_td_2').hide();
			$('#couAccu_tr').hide();
			$('#coupon_td_1').hide();
			$('#coupon_td_2').hide();
			$('#accu_td_1').hide();
			$('#accu_td_2').hide();
		}
	}
	
};
		
