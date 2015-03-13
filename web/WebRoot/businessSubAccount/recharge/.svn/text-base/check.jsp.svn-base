<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%
	response.setHeader("Cache-Control", "no-cache");
%>
<%@ include file="/common/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp"%>
		<%@ include file="/common/sys.jsp"%>
		<title>${ACT.name}</title>

		<f:css href="/css/page.css" />
		<f:css href="/css/multiselctor.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/plugin/jquery.metadata.js" />
		<f:js src="/js/plugin/jquery.validate.js" />
		<f:js src="/js/plugin/jquery.multiselector.js" />
		<f:js src="/js/date/WdatePicker.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />

		<style type="text/css">
html {
	overflow-y: scroll;
}
</style>
	<script type="text/javascript">
		function change(checkResult){
			//失败
			if(checkResult.value == '20'){
				$('tr.toggle').each(function(){
					$(this).hide();
				});
				$('#remitTime').removeClass('{required:true}');
				$('#voucherNo').removeClass('{required:true}');
				$('#remitTime').attr('disabled', true);
				$('#voucherNo').attr('disabled', true);
			}else{
				$('tr.toggle').each(function(){
					$(this).show();
				});
				$('#remitTime').addClass('{required:true}');
				$('#voucherNo').addClass('{required:true}');
				$('#remitTime').attr('disabled', false);
				$('#voucherNo').attr('disabled', false);
			}
		}
	</script>
	</head>

	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>

		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="check.do" id="inputForm" cssClass="validate">
					<s:hidden name="rechargeBill.no"></s:hidden>
					<div>
						<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
							<caption>
								${ACT.name}
							</caption>
							<tr>
								<td class="formlabel">
									审核结果
								</td>
								<td>
									<s:select list="checkResults" name="rechargeBill.state" listKey="key" listValue="value" onchange="change(this)"></s:select>
								</td>
							</tr>
							<tr class="toggle">
								<td class="formlabel">
									到账时间
								</td>
								<td>
									<s:textfield id="remitTime" name="rechargeBill.remitTimeStr" cssClass="{required:true}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></s:textfield>
									<span class="field_tipinfo">请输入到账时间</span>
								</td>
							</tr>
							<tr class="toggle">
								<td class="formlabel">
									凭证号
								</td>
								<td>
									<s:textfield id="voucherNo" name="rechargeBill.voucherNo" cssClass="{required:true}" maxlength="32"></s:textfield>
									<span class="field_tipinfo">请输入凭证号,如网银流水</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									备注
								</td>
								<td>
									<s:textfield id="note" name="rechargeBill.checkNote"  maxlength="32"></s:textfield>
								</td>
							</tr>
							<tr>
								<td width="80" height="30" align="right">
									&nbsp;
								</td>
								<td height="30" colspan="3">
									<input type="submit" value="提交" id="input_btn2" name="ok" />
									<input type="button" value="返回" name="escape" onclick="gotoUrl('/businessSubAccount/recharge/list.do')" class="ml30" />
								</td>
							</tr>
						</table>
						<s:token name="_TOKEN_RECHARGE_ADD" />
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>