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
			function getExpirDate(){
				WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'});
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<s:hidden name="pointConsmRuleDef.branchCode"></s:hidden>
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">积分兑换规则编号</td>
							<td class="pl20">${pointConsmRuleDef.ptExchgRuleId}
							<s:hidden name="pointConsmRuleDef.ptExchgRuleId"/>
							</td>
							<td width="80" height="30" align="right">兑换类型</td>
							<td>
								<s:select id="" name="pointConsmRuleDef.ptExchgRuleType" list="PtExchgRuleTypeList" 
								headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select>
								<span class="field_tipinfo">请选择兑换类型</span>
							</td>
						</tr>
						<tr>	
							<td width="80" height="30" align="right">积分类型</td>
							<td>
								<s:select id="" name="pointConsmRuleDef.ptClass" list="pointClassDefList" headerKey="" 
								headerValue="--请选择--" listKey="ptClass" listValue="ptClass" ></s:select>
								<span class="field_tipinfo">请选择积分类型</span>
							</td>
							<td width="80" height="30" align="right">积分参数</td>
							<td>
							<s:textfield name="pointConsmRuleDef.ptParam" />
							<span class="field_tipinfo">请输入积分参数</span>
							</td>
						</tr>
						<tr>	
							<td width="80" height="30" align="right">赠券类型</td>
							<td>
								<s:select id="" name="pointConsmRuleDef.couponClass" list="couponClassDefList" headerKey="" 
								headerValue="--请选择--" listKey="couponClass" listValue="couponClass" ></s:select>
								<span class="field_tipinfo">请选择赠券类型</span>
							</td>
							<td width="80" height="30" align="right">金额参数</td>
							<td>
							<s:textfield name="pointConsmRuleDef.ruleParam1" />
							<span class="field_tipinfo">请输入金额参数</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">子类型参数</td>
							<td>
							<s:textfield name="pointConsmRuleDef.ruleParam5" />
								<span class="field_tipinfo">请输入子类型参数</span>
							</td>
							<td width="80" height="30" align="right">规则状态</td>
							<td>
							<s:textfield name="pointConsmRuleDef.ruleStatus" />
								<span class="field_tipinfo">请输入规则状态</span>
							</td>
						<tr>
							<td width="80" height="30" align="right">生效日期</td>
							<td>
								<s:textfield id="startDate" name="pointConsmRuleDef.effDate" onclick="WdatePicker({minDate:'%y-%M-%d'})" cssStyle="width:68px;" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入生效日期</span>
							</td>
							<td width="80" height="30" align="right">失效日期</td>
							<td>
							<s:textfield name="pointConsmRuleDef.expirDate" onclick="getExpirDate();" cssStyle="width:68px;" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入失效日期</span>
							</td>
						</tr>
							
															
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" style="margin-left:30px;" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/gift/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_POINTCONSMRULE_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>