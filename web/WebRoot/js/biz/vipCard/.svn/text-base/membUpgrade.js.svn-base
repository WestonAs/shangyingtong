		$(function(){
			$('#idMembUpgrade').blur(function(){
				$('#idMembUpgrade_field').html('请输入19位卡号');
				var value = $(this).val();
				if (value == undefined || value.length < 19){
					return;
				}
				if (!validator.isDigit(value)) {
					return;
				}
				
				$.post(CONTEXT_PATH + '/vipUpgrade/isMeetUpgradeRule.do', {'renewCardReg.cardId':value}, function(json){
					if (json.isMeet){
						clearMembUpgradeError();
					} else {
						$('#idMembUpgrade_field').html('该卡号不符合升级规则，不予升级换卡').addClass('error_tipinfo').show();
						$('#input_btn2').attr('disabled', 'true');
						
					}
				}, 'json');
			});
		});
		function clearMembUpgradeError(){
			$('#idMembUpgrade_field').removeClass('error_tipinfo').html('请输入19位数字卡号');
			$('#input_btn2').removeAttr('disabled');
		}