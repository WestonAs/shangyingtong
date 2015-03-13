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
							<td width="80" height="30" align="right">解付编号</td>
							<td>
								${UnfreezeReg.unfreezeId}
								<s:hidden name="unfreezeReg.unfreezeId"/>
							</td>

							<td width="80" height="30" align="right">卡号</td>
							<td>
								<!-- 
								${UnfreezeReg.cardId}
								<s:hidden name="unfreezeReg.cardId"/>
								<span class="field_tipinfo">请输入卡号</span>
								 -->
								<s:textfield name="unfreezeReg.cardId" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td align="right">账号ID</td>
							<td>
								<s:textfield name="unfreezeReg.acctId" readonly="true"/>
							<!-- 
								<s:hidden name="unfreezeReg.acctId"/>
								${UnfreezeReg.acctId}
								<span class="field_tipinfo">请输入账号ID</span>
								 -->
							</td>
						
							<td align="right">子账户类型</td>
							<td>
								<s:select id="idSubAcctType" name="unfreezeReg.subacctType" list="subAcctTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择子账户类型</span>
							<!-- 
								<s:hidden name="unfreezeReg.subacctType"/>
							${UnfreezeReg.subacctTypeName}
								 -->
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">可用金额</td>
							<td>
							<!-- 
								${freezeReg.avlbBal}
								<s:hidden name="unfreezeReg.avlbBal"/>
							 -->
							 	<s:textfield name="unfreezeReg.avlbBal" readonly="true" />
							</td>
							<td width="80" height="30" align="right">冻结金额</td>
							<td>
							 	<s:textfield name="unfreezeReg.fznAmt" readonly="true" />
							 	<!-- 
								${freezeReg.fznAmt}
								<s:hidden name="unfreezeReg.fznAmt"/>
								-->
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">解付金额</td>
							<td>
								<s:textfield name="unfreezeReg.unfznAmt" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入解付金额</span>
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
							<s:textfield name="unfreezeReg.remark" />
								<span class="field_tipinfo">请输入备注</span>
							</td>
						</tr>
						
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<s:hidden name="unfreezeReg.unfreezeCode"/>
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" style="margin-left:30px;" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/unfreeze/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_UNFREEZE_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>