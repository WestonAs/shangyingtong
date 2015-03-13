		$(function(){
			$('#idPointExcGift').blur(function(){
				$('#idGift_field').html('请输入卡号');
				var value = $(this).val();
				if (!validator.isDigit(value)) {
					return;
				}
				$.post(CONTEXT_PATH + '/gift/isExistGift.do', {'giftDef.giftId':value}, function(json){
					if (json.isExist){
						$('#idGift_field').html('该礼品代码已存在，请更换').addClass('error_tipinfo').show();
						$('#input_btn2').attr('disabled', 'true');
					} else {
						clearGiftError();
					}
				}, 'json');
			});
		});
		function clearGiftError(){
			$('#idGift_field').removeClass('error_tipinfo').html('请输入礼品代码');
			$('#input_btn2').removeAttr('disabled');
		}
		
		$(function(){
			bindSel();
		});
		function bindSel(){
			$('#signCustId').bind('change', function(){
				var obj = $('#signRuleId');
				var type = $(this).val();
				if (type == ''){
					obj.empty();
				}else{
					$jload(obj.attr('id'), '/saleSignCardReg/signRuleRegList.do', {'saleSignCardReg.signCustId':type});
				}
			});
		}