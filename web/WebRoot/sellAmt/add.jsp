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
			$('#idSellType').change(function(){
				var value = $(this).val();
				if (value == '0'){
					$('td[id^="idDept_td"]').show();
					$('td[id^="idSell_td"]').hide();
					$('#idDept').removeAttr('disabled');
				} else if (value == '1') {
					$('td[id^="idDept_td"]').hide();
					$('td[id^="idSell_td"]').show();
					$('#idDept').attr('disabled', 'true');
				}
			});
			Selector.selectBranch('idCardBranch_sel', 'idCardBranch', true, '20', loadOpt);
			Selector.selectBranch('idSellBranch_sel', 'idSellBranch', true, '22');
			
			$('#idCardBranch').change(function(){
				loadOpt();
			});
		});
		
		function loadOpt(){
			var cardBranch = $('#idCardBranch').val();
			$('#idDept').load(CONTEXT_PATH + '/sellAmt/loadDept.do', 
					{cardBranch:cardBranch, 'callId':callId()});
		}
		</script>
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
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:select id="idCardBranch" name="branchSellReg.cardBranch" list="branchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo" id="">请选择发卡机构</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">调整类型</td>
							<td>
								<s:select name="branchSellReg.adjType" list="adjTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择调整类型</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right" >售卡机构类型</td>
							<td>
								<s:select id="idSellType" name="branchSellReg.sellType" list="sellTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择售卡机构类型</span>
							</td>
						</tr>
						<tr>
							<td id="idSell_td1" width="80" height="30" align="right" style="display:none;">售卡机构</td>
							<td id="idSell_td2" style="display:none;">
								<s:select name="branchSellReg.sellBranch" list="sellBranchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName"></s:select>
								<span class="field_tipinfo" id="">请选择售卡机构</span>
							</td>
							<td id="idDept_td1" width="80" height="30" align="right" style="display:none;">售卡部门</td>
							<td id="idDept_td2" style="display:none;">
								<select id="idDept" name="department" class="{required:true}"></select>
								<span class="field_tipinfo" id="">请选择售卡部门</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">金额</td>
							<td>
								<s:textfield name="branchSellReg.amt" cssClass="{required:true, num:true, decimal:'14,2'}"/>
								<span class="field_tipinfo">请输入相关金额</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="申请" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/sellAmt/regList.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_SELL_AMT_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>