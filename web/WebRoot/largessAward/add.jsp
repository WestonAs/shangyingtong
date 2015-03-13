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
							<td width="80" height="30" align="right">赠品</td>
							<td>
							<s:select name="largessReg.largessId" list="largessDefList" headerKey="" headerValue="--请选择--" 
								listKey="largessId" listValue="largessName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择赠品</span>
							</td>
							<td width="80" height="30" align="right">赠品数量</td>
							<td>
							<s:textfield name="largessReg.largessNum" cssClass="{required:true, digit:true}" maxlength="8"/>
								<span class="field_tipinfo">请输入赠品数量</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">小票号</td>
							<td>
							<s:textfield name="largessReg.ticketNo" />
								<span class="field_tipinfo">请输入小票号</span>
							</td>
							<td width="80" height="30" align="right">交易金额</td>
							<td>
							<s:textfield name="largessReg.transAmt" 
							cssClass="{num:true, decimal:'10,2'}" />
								<span class="field_tipinfo">请输入交易金额</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">客户姓名</td>
							<td>
							<s:textfield name="largessReg.custName" />
								<span class="field_tipinfo">请输入客户姓名</span>
							</td>
							<td width="80" height="30" align="right">联系地址</td>
							<td>
							<s:textfield name="largessReg.address" />
								<span class="field_tipinfo">请输入联系地址</span>
							</td>
						</tr>
						<tr>	
							<td width="80" height="30" align="right">联系电话</td>
							<td>
							<s:textfield name="largessReg.mobileNo" />
								<span class="field_tipinfo">请输入联系电话</span>
							</td>
						</tr>	
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="登记" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/largessAward/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_LARGESSAWARD_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>