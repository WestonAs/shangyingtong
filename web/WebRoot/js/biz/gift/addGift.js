		$(function(){
			$('#idGift').blur(function(){
				$('#idGift_field').html('请输入礼品代码');
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
		
		GiftDef = {
				settMthdChange: function () {
					var Id_SettMthd = $('#Id_SettMthd').val();
					
					if(Id_SettMthd=='0'){
						$('#id_settAmt_1').hide();
						$('#id_settAmt_2').hide();
						$('#id_settAmt_3').attr('disabled',true);
					}else if(Id_SettMthd=='1'||Id_SettMthd=='2'){
						$('#id_settAmt_3').removeAttr('disabled');
						$('#id_settAmt_1').show();
						$('#id_settAmt_2').show();
					}else{
						$('#id_settAmt_1').hide();
						$('#id_settAmt_2').hide();
						$('#id_settAmt_3').attr('disabled',true);
					}
				}
			};