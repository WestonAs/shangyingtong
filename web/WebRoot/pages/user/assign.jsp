<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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

		<script type="text/javascript">
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
				<s:form action="assign.do" id="inputForm" cssClass="validate">
					<table class="form_grid detail_tbl" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>给用户分配角色</caption>
						
						<tr>
							<td width="130" height="20" align="right">用户编号</td>
							<td width="200">${userInfo.userId}</td>
						</tr>
						
						<tr>
							<td width="130" height="20" align="right">用户名称</td>
							<td width="200">${userInfo.userName}</td>
						</tr>
						
						<tr>
							<td width="130" height="30" align="right">可分配角色信息</td>
							<td>
								<s:iterator value="roleList">
									<span>
									<input type="checkbox" name="roles" value="${roleId}" 
										<s:if test='hasRole.indexOf("," + roleId + ",") != -1'>checked</s:if>/>${roleName}<br />
									</span>
								</s:iterator>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<s:hidden name="userInfo.userId"/>
								<input type="submit" value="提交" id="input_btn2" name="ok" onclick="return validateForm()"/>
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/user/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_USER_ASSIGN"/>
					
					<s:hidden id='needSignatureReg' name="formMap.needSignatureReg" />
					<s:hidden id="serialNo" name="formMap.serialNo"/>
					<jsp:include flush="true" page="/common/certJsIncluded.jsp"></jsp:include>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>