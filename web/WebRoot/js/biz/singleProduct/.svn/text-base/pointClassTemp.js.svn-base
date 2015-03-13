PointClassTemp = {
	ptUsageChange: function () { 
		/*
		 * 1:永久有效
		 * 2:限时分期积分
		 * 3:分期积分
		 * 4:分期积分折旧积分
		 * 5:保德分期积分
		 */
		var ptUsage = $('#Id_ptUsage').val();
		var Id_ptValidityCyc1 = $('#Id_ptValidityCyc1');
		var Id_ptValidityCyc2 = $('#Id_ptValidityCyc2');
		if(ptUsage=='1'||ptUsage=='2'||ptUsage=='5'){
			$('#id_baode_tr').hide();
			$('#td_usage1_1').hide();
			$('#td_usage1_2').hide();
			Id_ptValidityCyc1.attr('disabled',true);
			Id_ptValidityCyc2.attr('disabled',true);
			$('#td_usage3_1').hide();
			$('#td_usage3_2').hide();
			$('#Id_usage_4').attr('disabled',true);
			
			if(ptUsage=='1' || ptUsage=='5'){
				$('#usage_tr2').hide();
				$('#td_usage2_1').hide();
				$('#td_usage2_2').hide();
				$('#Id_usage_2').attr('disabled',true);
				$('#ptDeRate').hide();
				$('#Id_ptDeprecRate').attr('disabled',true);
				
				$('#td_ptInsMthd_1').hide();
				$('#td_ptInsMthd_2').hide();
				$('#Id_ptInstmMthd').attr('disabled',true);
				$('#td_insPeriod_1').hide();
				$('#td_insPeriod_2').hide();
				$('#Id_instmPeriod').attr('disabled',true);
				if(ptUsage=='5'){
					$('#id_baode_tr').show();
				}
			}
			if(ptUsage=='2'){
				$('#Id_ptInstmMthd').removeAttr('disabled');
				$('#td_ptInsMthd_1').show();
				$('#td_ptInsMthd_2').show();
				$('#Id_instmPeriod').removeAttr('disabled');
				$('#td_insPeriod_1').show();
				$('#td_insPeriod_2').show();
				$('#usage_tr2').show();
				
				$('#Id_usage_2').removeAttr('disabled');
				$('#td_usage2_1').show();
				$('#td_usage2_2').show();
				$('#usage_tr').show();
			}
			
		}else if(ptUsage=='3'||ptUsage=='4'){
			JError.clearError(Id_ptValidityCyc1);
			JError.clearError(Id_ptValidityCyc2);
			$('#id_baode_tr').hide();
			$('#td_usage1_1').show();
			$('#td_usage1_2').show();
			if(ptUsage=='3'){
				Id_ptValidityCyc2.hide();
				Id_ptValidityCyc2.attr('disabled',true);
				Id_ptValidityCyc1.removeAttr('disabled');
				Id_ptValidityCyc1.show();
				$('#td_usage2_1').hide();
				$('#td_usage2_2').hide();
				$('#td_usage3_1').hide();
				$('#td_usage3_2').hide();
				$('#Id_usage_2').attr('disabled',true);
				$('#Id_usage_4').attr('disabled',true);
				JError.clearError(Id_ptValidityCyc1);
				
				$('#Id_ptInstmMthd').removeAttr('disabled');
				$('#td_ptInsMthd_1').show();
				$('#td_ptInsMthd_2').show();
				$('#Id_instmPeriod').removeAttr('disabled');
				$('#td_insPeriod_1').show();
				$('#td_insPeriod_2').show();
				$('#usage_tr2').show();
				/*$('#usage_tr2').show();*/
			}
			if(ptUsage=='4'){
				Id_ptValidityCyc1.hide();
				Id_ptValidityCyc1.attr('disabled',true);
				Id_ptValidityCyc2.removeAttr('disabled');
				Id_ptValidityCyc2.show();
				$('#td_usage2_1').hide();
				$('#td_usage2_2').hide();
				$('#Id_usage_2').attr('disabled',true);
				$('#Id_usage_4').removeAttr('disabled');
				$('#td_usage3_1').show();
				$('#td_usage3_2').show();
				$('#usage_tr').show();
				JError.clearError(Id_ptValidityCyc1);
				
				$('#td_usage1_1').hide();
				$('#td_usage1_2').hide();
				Id_ptValidityCyc1.attr('disabled',true);
				Id_ptValidityCyc2.attr('disabled',true);
				
				$('#Id_ptInstmMthd').removeAttr('disabled');
				$('#td_ptInsMthd_1').show();
				$('#td_ptInsMthd_2').show();
				$('#Id_instmPeriod').removeAttr('disabled');
				$('#td_insPeriod_1').show();
				$('#td_insPeriod_2').show();
				$('#usage_tr2').show();
			}
			$('#usage_tr').show();
		} else{
			
			$('#usage_tr').hide();
			Id_ptValidityCyc1.attr('disabled',true);
			Id_ptValidityCyc2.attr('disabled',true);
			$('#Id_usage_2').attr('disabled',true);
			$('#Id_usage_4').attr('disabled',true);
			
			$('#usage_tr2').hide();
			$('#Id_ptInstmMthd').attr('disabled',true);
			$('#Id_instmPeriod').attr('disabled',true);
			
			$('#ptDeprecRate_tr').hide();
			$('#Id_ptDeprecRate').attr('disabled',true);
			
			$('#id_baode_tr').hide();
			$('#tr_usage_2').hide();
			$('#tr_usage_4').hide();
			$('#td_usage3_1').hide();
			$('#td_usage3_2').hide();
		}
		
		if(ptUsage=='4'){
			$('#Id_ptDeprecRate').removeAttr('disabled');
			$('#ptDeRate').show();
			$('#ptDeprecRate_tr').show();
		}else{
			$('#ptDeprecRate_tr').hide();
			$('#Id_ptDeprecRate').attr('disabled',true);
		}
	},
	ptUsageForDetail: function () {
		var ptUsage = $('#Id_ptUsage').val();
		if(ptUsage=='1'||ptUsage=='2'){
			$('#td_usage1_1').hide();
			$('#td_usage1_2').hide();
			if(ptUsage=='2'){
				$('#td_usage3_1').hide();
				$('#td_usage3_2').hide();
				$('#td_usage2_1').show();
				$('#td_usage2_2').show();
				$('#usage_tr').show();
			}
		}else if(ptUsage=='3'||ptUsage=='4'){
			$('#td_usage1_1').show();
			$('#td_usage1_2').show();
			if(ptUsage=='3'){
				$('#td_usage2_1').hide();
				$('#td_usage2_2').hide();
				$('#td_usage3_1').hide();
				$('#td_usage3_2').hide();
			}
			if(ptUsage=='4'){
				$('#td_usage2_1').hide();
				$('#td_usage2_2').hide();
				$('#td_usage3_1').show();
				$('#td_usage3_2').show();
			}
			$('#usage_tr').show();
		}else{
			$('#tr_usage_2').hide();
			$('#tr_usage_4').hide();
			$('#td_usage3_1').hide();
			$('#td_usage3_2').hide();
		}
	}
};