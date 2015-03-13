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
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/validate.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<f:js src="/js/biz/gift/addGift.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			// 检查调整金额
			$('#id_ptChg').blur(function(){
				var ptAvlb = $('#id_ptAvlb').val();
				var ptChg = $('#id_ptChg').val();
				if(isEmpty(ptChg)){
					return;
				}
				/*if(!checkIsInteger(ptChg)){
					showMsg("请输入整数的调整积分。");
					$(':submit').attr('disabled', 'true');
					return false;
				}*/
				if(parseInt(ptAvlb)+parseInt(ptChg)<0){
					showMsg("调整积分减少额度不能小于可用积分。");
					$(':submit').attr('disabled', 'true');
					return false;
				}
				else{
					$(':submit').removeAttr('disabled');
				}
				
			});
			
		});

		
		/** 表单域校验 */
		function validateForm() {
			var signatureReg = $('#needSignatureReg').val();
			if (signatureReg == 'true' && !CheckUSBKey()) { // 证书签名失败
				return false;
			}
			return true;
		}
		
		function CheckUSBKey() {
			// 检查飞天的key
			var isOnline = document.all.FTUSBKEYCTRL.IsOnline;
			if (isOnline == 0) {
				if (FTDoSign()) { // 调用FT的签名函数
					return true;
				} else {
					return false;
				}
			} else {
				showMsg("请检查USB Key是否插入或USB Key是否正确！");
				return false;
			}
			return true;
		}

		/* 飞天的Key的签名函数 */
		function FTDoSign() {
			var SetCertResultRet = document.all.FTUSBKEYCTRL.SetCertResult; // 选择证书
			if (SetCertResultRet == 0) {
				var serialNumber = document.all.FTUSBKEYCTRL.GetCertSerial;
				$('#serialNo').val(serialNumber);
			} else {
				showMsg("选择证书失败");
				return false;
			}
			return true;
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<s:hidden name="pointChgReg.acctId"></s:hidden>
					<s:hidden name="pointChgReg.ptClass"></s:hidden>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
							<s:textfield name="pointChgReg.cardId" cssClass="readonly" readonly="true" />
							</td>
						</tr>	
						<tr>
							<td width="80" height="30" align="right">账户</td>
							<td>
							<s:textfield name="pointBal.acctId" cssClass="readonly" readonly="true" ></s:textfield>
							</td>
						</tr>	
						<tr>
							<td width="80" height="30" align="right">积分类型</td>
							<td>
							<s:textfield name="pointBal.ptClassName" cssClass="readonly" readonly="true"></s:textfield>
							</td>
						</tr>
						<!--<tr>
						-->
						<tr>
							<td width="80" height="30" align="right">可用积分</td>
							<td>
								<s:textfield name="pointBal.ptAvlb" id="id_ptAvlb" cssClass="readonly" readonly="true"></s:textfield>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">调整额</td>
							<td>
								<s:textfield name="pointChgReg.ptChg" id="id_ptChg" cssClass="{required:true, decimal:'12,2'}" maxlength="13"></s:textfield>
								<span class="field_tipinfo" id="">最大输入10位整数，2位小数</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return validateForm()"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pointBus/pointChange/prepareAdd.do')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_POINTCHANGE_ADD"/>
					
					<s:hidden id='needSignatureReg' name="formMap.needSignatureReg" />
					<s:hidden id="serialNo" name="formMap.serialNo"/>
					<jsp:include flush="true" page="/common/certJsIncluded.jsp"></jsp:include>
				</s:form>
			</div>
			<div id="pointBal_div">
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>