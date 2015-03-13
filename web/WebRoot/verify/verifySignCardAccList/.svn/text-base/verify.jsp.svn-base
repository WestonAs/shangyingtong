<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>售卡代理商返利核销</title>
		
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
			<s:form action="verify.do" id="inputForm" cssClass="validate">
				<s:hidden name="card.cardId"></s:hidden>
				<s:hidden name="card.accMonth"></s:hidden>
				<s:hidden id="hidden_curAmt" name="card.curAmt"></s:hidden>
				<s:hidden id="hidden_yearAmt" name="card.yearAmt"></s:hidden>
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<caption>签单卡账单核销</caption>
					<tr>
						<td width="80" height="30" align="right">卡号</td>
						<td>${card.cardId}</td>
						<td width="80" height="30" align="right">账单月份</td>
						<td>${card.accMonth}</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">账单生成日期</td>
						<td>${card.accDate}</td>
						<td width="80" height="30" align="right">账单客户ID</td>
						<td>${card.signCustId}</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">本期账单额</td>
						<td>${fn:amount(card.useAmt)}</td>
						<td width="80" height="30" align="right">年费</td>
						<td>${fn:amount(card.yearAmt)}</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">可用余额</td>
						<td>${fn:amount(card.useAmt)}</td>
						<td width="80" height="30" align="right">罚息</td>
						<td>${fn:amount(card.feeAmt)}</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">账单开始日期</td>
						<td>${card.strDate}</td>
						<td width="80" height="30" align="right">账单结束日期</td>
						<td>${card.endDate}</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">还款到期日期</td>
						<td>${card.expDate}</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">还款金额</td>
						<td>
							<s:textfield name="card.payAmt" id="Id_payAmt" cssClass="{required:true}"></s:textfield>
							<span class="field_tipinfo">请输入还款金额</span>
						</td>
						<td width="80" height="30" align="right">备注</td>
						<td>
							<s:textfield name="card.remark" id="Id_remark" maxlength="200"></s:textfield>
							<span class="field_tipinfo">200字符以内</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30">
							<input type="submit" value="提交" id="input_btn2"  name="ok" />
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/verify/verifySignCardAccList/list.do?goBack=goBack')" class="ml30" />
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