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
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="80" height="30" align="right">购卡客户：</td>
							<td>
								${customerRebateReg.cardCustomerName}
								<s:hidden name="customerRebateReg.cardCustomerId"/>								
							</td>
							
							<td width="80" height="30" align="right">卡BIN：</td>
							<td>
								${customerRebateReg.binName}
								<s:hidden name="customerRebateReg.binNo"/>								
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">状态：</td>
							<td>
								${customerRebateReg.statusName}
								<s:hidden name="customerRebateReg.status"/>								
							</td>	
							<td></td>	
							<td></td>													
						</tr>
						<tr>
							<td width="80" height="30" align="right">售卡返利ID</td>
							<td>
								<s:select  name="customerRebateReg.saleRebateId" list="rebateRuleList"  listKey="rebateId" listValue="rebateName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请输入售卡返利ID</span>
							</td>
							
							<td width="80" height="30" align="right">充值返利ID</td>
							<td>
								<s:select  name="customerRebateReg.depositRebateId" list="rebateRuleList" listKey="rebateId" listValue="rebateName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请输入充值返利ID</span>
							</td>							
														
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/customerRebateMgr/customerRebateReg/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CUSTOMERREBATEREG_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>