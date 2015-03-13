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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="80" height="30" align="right">换卡ID</td>
							<td>
								${renewCardReg.renewCardId}
								<s:hidden name="renewCardReg.renewCardId"/>
							</td>

							<td width="80" height="30" align="right">旧卡ID</td>
							<td>
								${renewCardReg.cardId}
								<s:hidden name="renewCardReg.cardId"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">新卡ID</td>
							<td>
								${renewCardReg.newCardId}
								<s:hidden name="renewCardReg.newCardId"/>
							</td>
							<td width="80" height="30" align="right">账户ID</td>
							<td>
								<s:textfield name="renewCardReg.acctId" cssClass="{required:true}"></s:textfield>
								<span class="field_tipinfo">请输入账户ID</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">持卡人姓名</td>
							<td>
								<s:textfield name="renewCardReg.custName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入持卡人姓名</span>
							</td>
							<td width="80" height="30" align="right">证件类型</td>
							<td>
								<s:select id="idCertType" name="renewCardReg.certType" list="certTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择证件类型</span>
							</td>
						</tr>
						
						<tr>	
							<td width="80" height="30" align="right">证件号码</td>
							<td>
								<s:textfield name="renewCardReg.certNo" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入联名证件号码</span>
							</td>
							<td width="80" height="30" align="right">换卡类型</td>
							<td>
								<s:select id="idRenewType" name="renewCardReg.renewType" list="renewTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择换卡类型</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td>
							<s:textfield name="renewCardReg.remark" />
								<span class="field_tipinfo">请输入备注</span>
							</td>
						</tr>
															
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<s:hidden name="renewCardReg.renewCardCode"/>
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" style="margin-left:30px;" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/intgratedService/renewcard/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_RENEWCARD_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>