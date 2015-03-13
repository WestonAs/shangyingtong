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
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/validate.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			var parent =  $('#idParent').val();
			Selector.selectBranch('id_branchName', 'id_BranchCode', true, '20', '', '', parent);
		});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<s:hidden id="idParent" name="parent" />
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">

						<tr>
						<td width="80" height="30" align="right">发卡机构</td>
						<td id="id_branch_2">
							<s:hidden id="id_BranchCode" name="cardRiskReg.branchCode" cssClass="{required:true}" ></s:hidden>
							<s:textfield  id="id_branchName" cssClass="{required:true}" ></s:textfield>
						</td>
						</tr>
						<%--
						<tr>
							<td width="80" height="30" align="right">机构代码</td>
							<td >
							<s:if test="showAll">
								<s:select name="cardRiskReg.branchCode" list="branchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName" cssClass="{required:true}"></s:select>
							</s:if>
							<s:else>
								<s:select name="cardRiskReg.branchCode" list="branchList" listKey="branchCode" listValue="branchName" cssClass="{required:true}"></s:select>
							</s:else>
								<span class="field_tipinfo">请选择机构编号</span>
							</td>
						</tr>	
						--%>
						<tr>	
							<td width="80" height="30" align="right" >调整类型 </td>
							<td>
								<s:select name="cardRiskReg.adjType" list="adjTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">相关金额</td>
							<td>
								<s:textfield name="cardRiskReg.amt" cssClass="{required:true, num:true, decimal:'13,2'}" maxlength="13"/>
								<span class="field_tipinfo">最大11位整数，2位小数</span>
							</td>
						</tr>

						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="申请" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/cardRisk/cardRiskReg/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARD_RISK_REG_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>