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
		
		<f:js src="/js/biz/gift/addGift.js"/>
		<script>
			function getExpirDate() {
				WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'});
			}
		</script>
		
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
							<td width="80" height="30" align="right">礼品名称</td>
							<td>
							<s:textfield name="giftDef.giftName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入名称</span>
							</td>
							<td width="80" height="30" align="right">礼品简称</td>
							<td>
							<s:textfield name="giftDef.giftChain" cssClass="{required:true}" maxlength="6"/>
								<span class="field_tipinfo">最大输入6个字符</span>
							</td>
						</tr>	
						<tr>	
							<td width="80" height="30" align="right">清算方法</td>
							<td>
								<s:select id="Id_SettMthd" name="giftDef.settMthd" list="settMthdTypeList" headerKey="" headerValue="--请选择--" 
								listKey="value" listValue="name" onchange="GiftDef.settMthdChange()"></s:select>
								<span class="field_tipinfo">请选择方法</span>
							</td>
							<td id="id_settAmt_1" width="80" height="30" align="right" style="display:none">清算金额</td>
							<td id="id_settAmt_2" style="display:none">
							<s:textfield id="id_settAmt_3" name="giftDef.settAmt" disabled="true" cssClass="{decimal:'12,2'}" />
								<span class="field_tipinfo">请输入金额</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right" >积分类型</td>
							<td>
							<s:select name="giftDef.ptClass" list="pointClassDefList" headerKey="" headerValue="--请选择--" 
								listKey="ptClass" listValue="className" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择类型</span>
							</td>
							<td width="80" height="30" align="right">兑换分值</td>
							<td>
							<s:textfield name="giftDef.ptValue" cssClass="{required:true, decimal:'10,2', digit:true}"/>
								<span class="field_tipinfo">请输入分值</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">生效日期</td>
							<td>
								<s:textfield id="startDate" name="giftDef.effDate" onclick="WdatePicker({minDate:'%y-%M-%d'})" cssStyle="width:68px;" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入日期</span>
							</td>
							<td width="80" height="30" align="right">失效日期</td>
							<td>
							<s:textfield name="giftDef.expirDate" onclick="getExpirDate();" cssStyle="width:68px;" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入日期</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/gift/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_GIFT_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>