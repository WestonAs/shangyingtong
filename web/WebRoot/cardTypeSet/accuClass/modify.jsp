<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>修改次卡子类型定义</title>
		
		<f:css href="/css/page.css"/>
		<f:css href="/css/multiselctor.css"/>
		
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		
		<f:js src="/js/biz/cardTypeSet/issType.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			$(function(){
				IssType.issTypeChange();
				settMthdChange();
			});
			function settMthdChange(){
				var settMthd = $('#Id_settMthd').val();
				if(settMthd=='1'){
					$('#td_settAmt_1').show();
					$('#td_settAmt_2').show();
					$('#Id_settAmt').removeAttr('disabled');
				}else{
					$('#td_settAmt_1').hide();
					$('#td_settAmt_2').hide();
					$('#Id_settAmt').attr('disabled',true);
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
				<s:hidden name="accuClassDef.accuClass" ></s:hidden>
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td width="80" height="30" align="right">次卡类型</td>
						<td>
							<s:textfield name="accuClassDef.accuClass" readonly="true" disabled="true"> </s:textfield>
						</td>
						<td width="80" height="30" align="right">次卡名称</td>
						<td>
							<s:textfield name="accuClassDef.className" cssClass="{required:true}"></s:textfield>
							<span class="field_tipinfo">请输入次卡名称</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">联名机构类型</td>
						<td>
							<s:select id="Id_jinstType" name="accuClassDef.jinstType" list="jinstTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" disabled="true" cssClass="{required:true}" ></s:select>
							<span class="field_tipinfo">请选择联名机构类型</span>
						</td>
						<td width="80" height="30" align="right">联名机构</td>
						<td>
							<s:textfield id="jinstName" name="accuClassDef.jinstName" disabled="true" cssClass="{required:true}"></s:textfield>
							<span class="field_tipinfo">请选择联名机构</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">清算方法</td>
						<td>
							<s:select id="Id_settMthd" name="accuClassDef.settMthd" list="settMthdList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}" onchange="settMthdChange()"></s:select>
							<span class="field_tipinfo">请选择清算方法</span>
						</td>
						<td id="td_settAmt_1" width="80" height="30" align="right" style="display:none">清算金额</td>
						<td id="td_settAmt_2" style="display:none">
							<s:textfield id="Id_settAmt" name="accuClassDef.settAmt" cssClass="{required:true,num:true}" disabled="true"></s:textfield>
							<span class="field_tipinfo">请选择清算金额</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">生效日期</td>
						<td>
							<s:textfield id="Id_effDate"  name="accuClassDef.effDate" cssClass="{required:true}" onclick="WdatePicker()"></s:textfield>
							<span class="field_tipinfo">请输入生效日期</span>
						</td>
						<td width="80" height="30" align="right">失效日期</td>
						<td>	
							<s:textfield id="Id_expirDate" name="accuClassDef.expirDate" cssClass="{required:true}" onclick="WdatePicker()"></s:textfield>
							<span class="field_tipinfo">请输入失效日期</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30" colspan="3">
							<input type="submit" value="提交" id="input_btn2"  name="ok" />
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');PointClass.ptUsageChange()" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/cardTypeSet/accuClass/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_ACCUCLASS_MODIFY"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>