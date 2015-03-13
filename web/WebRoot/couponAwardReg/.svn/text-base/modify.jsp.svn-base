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
							<td width="80" height="30" align="right">赠券派赠编号</td>
							<td>
							<s:textfield name="couponReg.couponRegId" readonly="true"/>
								<span class="field_tipinfo"></span>
							</td>
							
							<td width="80" height="30" align="right">卡号</td>
							<td>
							<s:textfield name="couponReg.cardId" readonly="true"/>
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">小票号</td>
							<td>
								<s:textfield name="couponReg.ticketNo" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入小票号</span>
							</td>
							<td width="80" height="30" align="right">交易金额</td>
							<td>
							<s:textfield name="couponReg.transAmt" />
								<span class="field_tipinfo">请输入交易金额</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">返利金额</td>
							<td>
							<s:textfield name="couponReg.backAmt" />
								<span class="field_tipinfo">请输入返利金额</span>
							</td>
							<td width="80" height="30" align="right">客户姓名</td>
							<td>
								<s:textfield name="couponReg.custName" cssClass="{required:true}" />
								<span class="field_tipinfo">请输入客户姓名</span>
							</td>
						<tr>
						<tr>	
							<td width="80" height="30" align="right">联系地址</td>
							<td>
								<s:textfield name="couponReg.jinstId" cssClass="{required:true}" readonly="true"/>
								<span class="field_tipinfo">请输入联系地址</span>
							</td>
							<td width="80" height="30" align="right">客户姓名</td>
							<td>
								<s:textfield name="couponReg.jinstId" cssClass="{required:true}" readonly="true"/>
								<span class="field_tipinfo">请输入联系电话</span>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" style="margin-left:30px;" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/couponAward/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_COUPONAWARD_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>