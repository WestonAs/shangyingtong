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
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<script>
		    $(function(){
		        $('#input_btn3').click(function(){
					gotoUrl("/washCarService/washCarServiceMemberShipDues/export.do");
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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="addFile" id="inputForm" enctype="multipart/form-data" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">导入文件</td>
							<td>
								<s:file name="upload" cssClass="{required:true}" contenteditable="false" ></s:file>
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>
						<tr>	
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return validateForm()"/>
								<input type="button" value="生成Excel模版" id="input_btn3"  name="ok" class="ml30"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/washCarService/washCarServiceMemberShipDues/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_FILEPOINTCHGREG_ADD"/>
					<jsp:include flush="true" page="/common/certJsIncluded.jsp"></jsp:include>
				</s:form>
				
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>