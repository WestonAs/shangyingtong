<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>修改赠券子类型定义</title>
		
		<f:css href="/css/page.css"/>
		<f:css href="/css/multiselctor.css"/>
		
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		
		<f:js src="/js/biz/cardTypeSet/issType.js"/>
		<f:js src="/js/biz/cardTypeSet/couponClass.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
			$(function(){
				//IssType.issTypeChange('jinstName','Id_jinstId','Id_jinstType');
				CouponClass.coupnUsageChange();
			});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
			<s:form action="modify.do" id="inputForm" cssClass="validate">
				<s:hidden name="couponClassDef.coupnClass"></s:hidden>
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td width="80" height="30" align="right">赠券名称</td>
						<td>
							<s:textfield name="couponClassDef.className" cssClass="{required:true}"></s:textfield>
							<span class="field_tipinfo">请输入赠券名称</span>
						</td>
						<td width="80" height="30" align="right">赠券类型简称</td>
						<td>
							<s:textfield name="couponClassDef.classShortName" cssClass="{required:true}" maxlength="6"></s:textfield>
							<span class="field_tipinfo">请输入简称</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">联名机构类型</td>
						<td>
							<s:select id="Id_jinstType" name="couponClassDef.jinstType" list="jinstTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}" disabled="true"></s:select>
							<span class="field_tipinfo"></span>
							<s:hidden name="couponClassDef.jinstType" cssClass="{required:true}"></s:hidden>
						</td>
						<td width="80" height="30" align="right">联名机构</td>
						<td>
							<s:textfield id="jinstName" name="couponClassDef.jinstName" disabled="true" cssClass="{required:true}"></s:textfield>
							<s:hidden name="couponClassDef.jinstName" cssClass="{required:true}"></s:hidden>
							<s:hidden id="Id_jinstId" name="couponClassDef.jinstId" cssClass="{required:true}"></s:hidden>
							<span class="field_tipinfo"></span>
						</td>
					</tr>
					<s:if test="loginRoleType == 40">
						<tr>
							<td width="80" height="30" align="right">委托发卡机构</td>
							<td>
								<s:select name="couponClassDef.issId" list="branchList" headerKey="" headerValue="--请选择--" 
								listKey="branchCode" listValue="branchName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">委托发卡机构</span>
							</td>
						</tr>
						
					</s:if>
					<tr>
						<td width="80" height="30" align="right">赠券使用方法</td>
						<td>
							<s:select id="Id_coupnUsage" name="couponClassDef.coupnUsage" list="couponUsageList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}" onchange="CouponClass.coupnUsageChange()"></s:select>
							<span class="field_tipinfo">请选择使用方法</span>
						</td>
						<td width="80" height="30" align="right">初始面值</td>
						<td>
							<s:textfield name="couponClassDef.faceValue" cssClass="{required:true, digit:true}" maxlength="10" ></s:textfield>
							<span class="field_tipinfo">请输入初始面值</span>
						</td>
					</tr>
					<tr>
						<td id="td_ruleParam1_1" width="80" height="30" align="right" style="display:none"><span>满M元</span></td>
						<td id="td_ruleParam1_2" style="display:none">
							<s:textfield id="Id_ruleParam1"  name="couponClassDef.ruleParam1" cssClass="{required:true, digit:true}" maxlength="10"></s:textfield>
						</td>
						<td id="td_ruleParam2_1" width="80" height="30" align="right" style="display:none">
							<span id="usage_1" style="display:none">消费N元</span>
							<span id="usage_2" style="display:none">每次使用L元</span>
						</td>
						<td id="td_ruleParam2_2" style="display:none">	
							<s:textfield id="Id_ruleParam2" name="couponClassDef.ruleParam2" cssClass="{required:true,digit:true}" maxlength="10"></s:textfield>
						</td>
						<td id="td_ruleParam3_1" width="80" height="30" align="right" style="display:none">使用赠券比例</td>
						<td id="td_ruleParam3_2" style="display:none">	
							<s:textfield id="Id_ruleParam3" name="couponClassDef.ruleParam3" cssClass="{required:true}" ></s:textfield>
						</td>
						
					</tr>
					<tr>
						<td width="80" height="30" align="right">生效日期</td>
						<td>
							<s:textfield id="Id_effDate"  name="couponClassDef.effDate" cssClass="{required:true}" onclick="WdatePicker()"></s:textfield>
						</td>
						<td width="80" height="30" align="right">失效日期</td>
						<td>	
							<s:textfield id="Id_expirDate" name="couponClassDef.expirDate" cssClass="{required:true}" onclick="WdatePicker()"></s:textfield>
						<td>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30" colspan="3">
							<input type="submit" value="提交" id="input_btn2"  name="ok" />
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');PointClass.ptUsageChange()" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/cardTypeSet/couponClass/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_COUPONCLASS_MODIFY"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>