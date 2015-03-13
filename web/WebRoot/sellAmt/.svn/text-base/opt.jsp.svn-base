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
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/validate.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			var branchCode = $('#idBranchCode').val();
			if (branchCode == '') {
				return;
			}
			$('#idUserId').change(function(){
				var userId = $(this).val();
				if (userId == '') {
					return;
				}
				$.post(CONTEXT_PATH + '/sellAmt/getUserOpt.do', 
					{branchCode:branchCode, userId:userId, 'callId':callId()}, function(json){
					if (json.success) {
						$('tr[id^="idOpt_"]').show();
						//$('#idOrgAmt_update').html(json.amt);
						//$('#idUsedAmt_update').html(json.availableAmt);
						$('#idOrgAmt').val(fix(json.amt));
						$('#idUsedAmt').val(fix(json.availableAmt));
					} else {
						alert(json.error);
					}
				}, 'json');
			});
		});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="setOpt.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td width="80" height="30" align="right">机构编号</td>
							<td>
								<s:if test="loginRoleType == 20">
								<s:select id="idBranchCode" name="userSellChg.branchCode" list="branchList" listKey="branchCode" listValue="branchName" cssClass="{required:true}"></s:select>
								</s:if>
								<s:else>
								<s:select id="idBranchCode" name="userSellChg.branchCode" list="sellBranchList" listKey="branchCode" listValue="branchName" cssClass="{required:true}"></s:select>
								</s:else>
								<span class="field_tipinfo" id="">请选择机构</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">用户编号</td>
							<td>
								<s:if test="userList != null && userList.size > 0">
								<s:select id="idUserId" name="userSellChg.userId" list="userList" headerKey="" headerValue="--请选择--" listKey="userId" listValue="userName" cssClass="{required:true}"></s:select>
								</s:if>
								<s:else>
								<input type="text" name="userSellChg.userId" readonly="readonly" value="该机构下没有用户" class="{digitOrLetter:true}" />
								</s:else>
								<span class="field_tipinfo" id="">请输入用户编号</span>
							</td>
						</tr>
						<tr id="idOpt_1" style="display:none;">
							<td width="80" height="30" align="right">原配额</td>
							<td id="idOrgAmt_update">
								<s:textfield id="idOrgAmt" cssClass="readonly" readonly="true"/>
							</td>
						</tr>
						<tr id="idOpt_2" style="display:none;">
							<td width="80" height="30" align="right">可用配额</td>
							<td id="idUsedAmt_update">
								<s:textfield id="idUsedAmt" cssClass="readonly" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">新配额</td>
							<td>
								<s:textfield name="userSellChg.amt" cssClass="{required:true, num:true, decimal:'14,2'}"/>
								<span class="field_tipinfo">请输入新配额</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="重置" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/sellAmt/optList.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_SELL_AMT_OPT"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>