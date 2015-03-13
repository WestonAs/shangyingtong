		$(function(){
			$('#idMembClass').blur(function(){
				$('#idMembClass_field').html('请输入8位数字会员模板类型');
				var value = $(this).val();
				if (value == undefined || value.length < 8){
					return;
				}
				if (!validator.isDigit(value)) {
					return;
				}
				$.post(CONTEXT_PATH + '/vipCard/isExistMembClass.do', {'membClassDef.membClass':value}, function(json){
					if (json.isExist){
						$('#idMembClass_field').html('该会员类型模板已存在，请更换').addClass('error_tipinfo').show();
						$('#input_btn2').attr('disabled', 'true');
					} else {
						clearMembClassError();
					}
				}, 'json');
			});
		});
		function clearMembClassError(){
			$('#idMembClass_field').removeClass('error_tipinfo').html('请输入8位数字会员模板类型');
			$('#input_btn2').removeAttr('disabled');
		}