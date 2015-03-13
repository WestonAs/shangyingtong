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
			$(function(){
				show($('#derateTypeId').val());
				$('#derateTypeId').change(function(){
					var value = $(this).val();
					show(value);
				});
			});
			function show(value){
				if(value == '0'){
					//按次减免
					$('td[id^="derateCountId_td"]').show();
					$('td[id^="derateAmtId_td"]').hide();
					$('#derateAmtId').attr('disabled', 'true');
					$('#derateCountId').removeAttr('disabled');
				} else if(value == '1') {
					//按金额减免
					$('td[id^="derateCountId_td"]').hide();
					$('td[id^="derateAmtId_td"]').show();
					$('#derateAmtId').removeAttr('disabled');
					$('#derateCountId').attr('disabled', 'true');
				} else {
					// 不收费
					$('td[id^="derateCountId_td"]').hide();
					$('td[id^="derateAmtId_td"]').hide();
					$('#derateAmtId').attr('disabled', 'true');
					$('#derateCountId').attr('disabled', 'true');
				}
			}
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
							<td width="80" height="30" align="right">签单规则名</td>
							<td>								
								<s:textfield name="signRuleReg.signRuleName"/>	
								<s:hidden name="signRuleReg.signRuleId"/>
								<span class="field_tipinfo">请输入签单规则名</span>							
							</td>
							
							<td width="80" height="30" align="right">状态</td>
							<td>
								<s:textfield name="signRuleReg.statusName" cssClass="readonly" readonly="true"/>							
							</td>	
						</tr>
						<tr>
							<td width="80" height="30" align="right">签单客户</td>
							<td>
								<s:select name="signRuleReg.signCustId" list="signCustList" headerKey="" headerValue="--请选择--" listKey="signCustId" listValue="signCustName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择签单客户</span>								
							</td>							
							<td width="80" height="30" align="right">透支金额</td>
							<td>
								<s:textfield name="signRuleReg.overdraft" cssClass="{required:true, num:true}"/>
								<span class="field_tipinfo">请输入透支金额</span>
							</td>							
						</tr>
						<tr>
							<td width="80" height="30" align="right">年费</td>
							<td>
								<s:textfield name="signRuleReg.annuity" cssClass="{required:true, num:true}"/>
								<span class="field_tipinfo">请输入年费</span>
							</td>							
							<td width="80" height="30" align="right">年费减免方式</td>
							<td>
								<s:select id="derateTypeId" name="signRuleReg.derateType" list="derateTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>	
							</td>							
						</tr>
						<tr>
							<td id="derateCountId_td1" width="80" height="30" align="right" style="display: none;">减免次数</td>
							<td id="derateCountId_td2" style="display: none;">
								<s:textfield id="derateCountId" name="signRuleReg.derateCount" cssClass="{required:true, digit:true, min:1}"/>
								<span class="field_tipinfo">请输入减免次数</span>
							</td>							
							
							<td id="derateAmtId_td1" width="80" height="30" align="right" style="display: none;">减免金额</td>
							<td id="derateAmtId_td2" style="display: none;">
								<s:textfield id="derateAmtId" name="signRuleReg.derateAmt" cssClass="{required:true,num:true}"/>
								<span class="field_tipinfo">请输入减免金额</span>
							</td>							
												
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="signRuleReg.remark" />								
							</td>														
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/signCardMgr/signRuleReg/list.do')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_SIGN_RULE_REG_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>