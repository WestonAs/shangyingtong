<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>

<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
			
		<title>${ACT.name}</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<f:js src="/js/cert/AC_ActiveX.js"/>
		<f:js src="/js/cert/AC_RunActiveContent.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
		$(function(){
			$("#id_CardId").focus();
			$('#input_btn2').attr('disabled', 'true');
			
			$('#id_CardId').change(function(){
				var couponClass = $('#id_CouponClass').val();
				var cardId = $('#id_CardId').val();
				var point = $('#id_CouponAmt').val();
				if(isEmpty(cardId)){
					showMsg('卡号不能为空');
					return false;
				}
				
				$.post(CONTEXT_PATH + '/pages/couponDeposit/checkCard.do', {'cardId':cardId, 'callId':callId()},
					function(json){
						if (json.success){
							$("#id_CouponClass").load(CONTEXT_PATH + "/pages/couponDeposit/loadBranch.do",{'cardIssuer':json.cardIssuer, 'callId':callId()});
						} else {
							showMsg(json.errorMsg);
							$('#input_btn2').attr('disabled', 'true');
						}
					}, 'json');
					
				if(!isEmpty(point) && !isEmpty(couponClass)){
					calcRefAmt();
				}
			});
			
			// 赠券类型变更时，得到可用赠券
			$('#id_CouponClass').change(function(){
				var couponClass = $('#id_CouponClass').val();
				var cardId = $('#id_CardId').val();
				var point = $('#id_CouponAmt').val();
				$('#id_AvlPoint').val('');
				
				if(isEmpty(cardId)){
					return false;
				}
				
				if(isEmpty(couponClass)){
					return false;
				}
				
				$.post(CONTEXT_PATH + '/pages/couponDeposit/calcCardOther.do', {'couponClass':couponClass, 'cardId':cardId, 'callId':callId()},
					function(json){
						if (json.success){
							$('#id_AvlPoint').val(json.avlPoint);
						} else {
							showMsg(json.errorMsg);
							$('#input_btn2').attr('disabled', 'true');
						}
					}, 'json');
				
				if(!isEmpty(point) && !isEmpty(couponClass)){
					calcRefAmt();
				}
			});
			// 赠送赠券变更时，得到赠券折算金额
			$('#id_CouponAmt').change(calcRefAmt);
		});
		
		// 根据卡号，赠券类型号，赠送赠券得到赠券折算金额
		function calcRefAmt() {
			var couponClass = $('#id_CouponClass').val();
			var cardId = $('#id_CardId').val();
			var point = $('#id_CouponAmt').val();
					
			$('#id_RefAmt').val('');
			$('#input_btn2').attr('disabled', 'true');
			
			if(isEmpty(cardId)
				|| isEmpty(couponClass)
				|| isEmpty(point)){
				return false;
			}
			$('#id_RefAmt').val(this.value);
			$('#input_btn2').removeAttr('disabled');
			//$.post(CONTEXT_PATH + '/pages/couponDeposit/calRealAmt.do', {'couponClass':couponClass, 'cardId':cardId, 'point':point, 'callId':callId()},
			//	function(json){
			//		if (json.success){
			//			$('#id_RefAmt').val(json.refAmt);
			//			$('#input_btn2').removeAttr('disabled');
			//		} else {
			//			showMsg(json.errorMsg);
			//			$('#input_btn2').attr('disabled', 'true');
			//		}
			//	}, 'json');
		}
		
		function submitDepositForm(){
			try{
				if(!checkField()){
					return false;
				}
				if(!checkSubmitForm()){
					return false;
				}
				
				hideMsg();
				if($('#inputForm').validate().form()){
					$('#inputForm').submit();
					$('#input_btn2').attr('disabled', 'true');
					//window.parent.showWaiter();
					$("#loadingBarDiv").css("display","inline");
					$("#contentDiv").css("display","none");
				}
			}catch(err){
				txt="本页中存在错误。\n\n 错误描述：" + err.description + "\n\n"
				showMsg(txt)
			}
		}
		
		// 检查录入数据完整性
		function checkField(){
			// 卡号有效性检查
			var cardId = $("#id_CardId").val();
			var couponClass = $('#id_CouponClass').val();
			var point = $("#id_CouponAmt").val();
			var refAmt = $("#id_RefAmt").val();
			
			if(isEmpty(cardId)){
				showMsg("卡号不能为空！");
				return false;
			}
			if(isEmpty(couponClass)){
				showMsg("赠券类型不能为空！");
				return false;
			}
			if(isEmpty(point)){
				showMsg("赠送赠券不能为空！");
				return false;
			}
			if(isEmpty(refAmt)){
				showMsg("赠券折算金额不能为空！");
				return false;
			}
			hideMsg();
			return true;
		}
		
		function checkSubmitForm(){
			try{
				var signatureReg = $('#id_signatureReg').val();
				if(signatureReg == 'true'){
					if(!CheckUSBKey()){
						return false;
					}
				}
				return true;
				
			}catch(err){
				txt="本页中存在错误。\n\n"
				txt+="错误描述：" + err.description + "\n\n"
				showMsg(txt)
			}
		}
		
		function CheckUSBKey() {
		    try{
				// 检查key
				var isOnline = document.all.FTUSBKEYCTRL.IsOnline;
				if (isOnline == 0) {
					FTDoSign(); //调用FT的签名函数
				} else {
					showMsg("请检查USB Key是否插入或USB Key是否正确！");
					return false;
			    }
			} catch(ex) {
				showMsg("ex");
			}
			return true;
		}
		
		/*飞天的Key的签名函数*/
		function FTDoSign(){
			var SetCertResultRet = document.all.FTUSBKEYCTRL.SetCertResult; //选择证书
			if (SetCertResultRet == 0) {
		   		var randomNum = $('#id_randomSessionId').val();
				var cardId = $('#id_CardId').val();
				var realAmt = $('#id_RefAmt').val();
				
				var serialNumber = document.all.FTUSBKEYCTRL.GetCertSerial;
				$('#id_serialNo').val(serialNumber);
		      
		        var signResultRet = document.all.FTUSBKEYCTRL.SignResult(cardId + realAmt + randomNum);
				if (signResultRet == 0) {
					var signData = document.all.FTUSBKEYCTRL.GetSignature;
					//alert('signData:'+ signData);
					$('#id_signature').val(signData);
				} else {
					showMsg("签名失败");
					return false;
				}
			} else {
				showMsg("选择证书失败");
				return false;
			}
		}
		
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div id="loadingBarDiv"	style="display: none; width: 100%; height: 100%">
			<table width=100% height=100%>
				<tr>
					<td align=center >
						<center>
							<img src="${CONTEXT_PATH}/images/ajax_saving.gif" align="middle">
							<div id="processInfoDiv"
								style="font-size: 15px; font-weight: bold">
								正在处理中，请稍候...
							</div>
							<br>
							<br>
							<br>
						</center>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="contentDiv" class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate-tip">
					<s:hidden id="id_signatureReg" name="signatureReg" />
					<s:hidden id="id_serialNo" name="serialNo"/>
					<s:hidden id="id_randomSessionId" name="depositCouponReg.randomSessionid" />
					<s:hidden id="id_signature" name="depositCouponReg.signature" />
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
								<s:textfield id="id_CardId" name="depositCouponReg.cardId" cssClass="{required:true, digit:true}" maxlength="19"/>
								<span class="field_tipinfo">请输入卡号</span>
							</td>
							<td width="80" height="30" align="right">赠券类型</td>
							<td>
								<select id="id_CouponClass" name="depositCouponReg.couponClass" class="{required:true}"></select>
								<span class="field_tipinfo">请选择赠券类型</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">可用赠券</td>
							<td>
								<s:textfield id="id_AvlPoint" name="avlPoint" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo">不能为空</span>
							</td>
							<td width="80" height="30" align="right">赠送赠券</td>
							<td>
								<s:textfield id="id_CouponAmt" name="depositCouponReg.couponAmt" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入赠券数</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">折算金额</td>
							<td>
								<s:textfield id="id_RefAmt" name="depositCouponReg.refAmt" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo">不能为空</span>
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="depositCouponReg.remark" />
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="提交" id="input_btn2"  name="ok"  onclick="return submitDepositForm();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/couponDeposit/list.do?goBack=goBack')" class="ml30" />
							</td>
							<td></td><td></td>
						</tr>
					</table>
					<s:token name="_TOKEN_DEPOSIT_COUPON_REG_ADD"/>
				</s:form>
				 <script type="text/javascript">
					AC_AX_RunContent( 'classid','clsid:B494F2C1-57A7-49F7-A7AF-0570CA22AF80','id','FTUSBKEYCTRL','style','DISPLAY:none' ); //end AC code
				</script>
				<noscript><OBJECT classid="clsid:B494F2C1-57A7-49F7-A7AF-0570CA22AF80" id="FTUSBKEYCTRL" style="DISPLAY:none"/></OBJECT></noscript>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>