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
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td width="80" height="30" align="right">卡号</td>
							<td>
							<s:textfield name="" cssClass="{required:true, digit:true}" maxlength="19"/>
								<span class="field_tipinfo">请输入卡号</span>
							</td>
							<td width="80" height="30" align="right">缴费金额</td>
							<td>
							<s:textfield name="" cssClass="{required:true, decimal:'14,2'}" onchange="formatCurrency(this)" />
								<span class="field_tipinfo">请输入缴费金额,小数最大保留两位</span>
							</td>
						</tr>
						
						<tr>
						    <td width="80" height="30" align="right">有效开始时间</td>
							<td>
							    <s:textfield name="" cssClass="{required:true,digit:true}" maxlength="8"/>
								<span class="field_tipinfo">请输入开始时间 格式：YYYYMMDD</span>
							</td>
							<td width="80" height="30" align="right">有效结束时间</td>
							<td>
							    <s:textfield name="" cssClass="{required:true,digit:true}" maxlength="8"/>
							    <span class="field_tipinfo">请输入结束时间 格式：YYYYMMDD</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">状态</td>
							<td>
							     <s:select name="" list="washCarSvcStateList" headerKey="" 
							        headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}">
							     </s:select>
						    </td>
							<td width="80" height="30" align="right">备注</td>
							<td>
							    <s:textfield name="" />
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/washCarService/washCarServiceMemberShipDues/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_PAYMENTOFDUES_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>