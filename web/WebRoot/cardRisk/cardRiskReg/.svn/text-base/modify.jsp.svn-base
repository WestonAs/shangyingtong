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
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

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
							<td width="80" height="30" align="right">调整申请编号: </td>
							<td>
								${cardRiskReg.id}
								<s:hidden name="cardRiskReg.id"/>
								<s:hidden name="cardRiskReg.branchCode"/>
								<s:hidden name="cardRiskReg.effectiveDate"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">审核状态: </td>
							<td>
								${cardRiskReg.statusName}
								<s:hidden name="cardRiskReg.status"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">原金额: </td>
							<td>
								${fn:amount(cardRiskReg.orgAmt)}
								<s:hidden name="cardRiskReg.orgAmt"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">调整类型: </td>
							<td>
								<s:select name="cardRiskReg.adjType" list="adjTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
 									cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择调整类型</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">相关金额: </td>
							<td>
								<s:textfield name="cardRiskReg.amt" cssClass="{required:true, num:true, decimal:'13,2'}"/>
								<span class="field_tipinfo">最大11位整数，2位小数</span>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" style="margin-left:30px;" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/cardRisk/cardRiskReg/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARD_RISK_REG_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>