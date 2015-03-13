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
							<td width="80" height="30" align="right">申请登记编号: </td>
							<td>
								${riskMarginReg.earnestRegId}
								<s:hidden name="riskMarginReg.earnestRegId"/>
							</td>
							<td width="80" height="30" align="right">审核状态: </td>
							<td>
								${riskMarginReg.statusName}
								<s:hidden name="riskMarginReg.status"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">机构编号: </td>
							<td>
								${riskMarginReg.branchCode}
								<s:hidden name="riskMarginReg.branchCode"/>
							</td>
							<td width="80" height="30" align="right">机构名称: </td>
							<td>
								${riskMarginReg.branchName}
								<s:hidden name="riskMarginReg.branchName"/>
							</td>
						</tr>
						<tr>
							<td align="right">保证金余额: </td>
							<td>
								${riskMarginReg.remainRiskAmt}
								<s:hidden name="riskMarginReg.remainRiskAmt"/>
							</td>
							<td></td>
							<td></td>
						<tr>
						<tr>	
							<td width="80" height="30" align="right">调整方向</td>
							<td>
								<s:select name="riskMarginReg.adjDirection" list="adjDirectionList" listKey="value" listValue="name" ></s:select>
								<span class="field_tipinfo">请选择调整方向</span>
							</td>
							<td width="80" height="30" align="right">调整金额</td>
							<td>
								<s:textfield name="riskMarginReg.adjAmt" />
								<span class="field_tipinfo">请输入调整金额</span>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" style="margin-left:30px;" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/earnestMoney/earnestMoneyReg/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>