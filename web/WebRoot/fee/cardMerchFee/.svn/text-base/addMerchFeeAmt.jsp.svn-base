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
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
			<s:form action="addMerchFeeAmt.do" id="inputForm" cssClass="validate">
				<s:hidden name="merchFeeAmt.branchCode"></s:hidden>
				<s:hidden name="merchFeeAmt.merchId"></s:hidden>
				<s:hidden name="merchFeeAmt.transType"></s:hidden>
				<s:hidden name="merchFeeAmt.cardBin"></s:hidden>
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td width="80" height="30" align="right">分支机构</td>
						<td>
							${merchFeeAmt.branchName }
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">商户</td>
						<td>
							${merchFeeAmt.merchName }
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">金额上限</td>
						<td>
							<s:textfield name="merchFeeAmt.ulMoney" cssClass="{required:true}"></s:textfield>
							<span class="field_tipinfo">请输入金额上限</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">费率</td>
						<td>
							<s:textfield name="merchFeeAmt.feeRate" cssClass="{required:true}"></s:textfield>
							<span class="field_tipinfo">请输入费率</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30">
							<input type="submit" value="提交" id="input_btn2"  name="ok" />
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/fee/cardMerchFee/listMerchFeeAmt.do?branchCode=${merchFeeAmt.branchCode}&merchFeeAmt.merchId=${merchFeeAmt.merchId}&merchFeeAmt.transType=${merchFeeAmt.transType}&merchFeeAmt.cardBin=${merchFeeAmt.cardBin}&goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_CARDMERCHFEE_ADDMERCHFEEAMT"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>