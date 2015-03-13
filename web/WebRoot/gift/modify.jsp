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
		
		<script>
			function getExpirDate() {
				WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'});
			}
		</script>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="80" height="30" align="right">礼品名称</td>
							<td>
								<s:textfield name="giftDef.giftName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入名称</span>
							</td>
							<td width="80" height="30" align="right">礼品简称</td>
							<td>
								<s:textfield name="giftDef.giftChain" cssClass="{required:true}"  maxlength="6"/>
								<span class="field_tipinfo">最大输入6个字符</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">礼品代码</td>
							<td>
								<s:textfield name="giftDef.giftId" cssClass="{required:true}"
								disabled="true" readonly="true"/>
								<s:hidden name="giftDef.giftId"/>
							</td>
							<td width="80" height="30" align="right">审核状态</td>
							<td>
								<s:textfield name="giftDef.statusName" cssClass="{required:true}"
								disabled="true" readonly="true"/>
								<s:hidden name="giftDef.status"/>
							</td>
						</tr>
						<tr>
							<td align="right">联名机构类型</td>
							<td>
								<s:select name="giftDef.jinstType" list="jinstTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"
								disabled="true"></s:select>
								<s:hidden name="giftDef.jinstType"/>
							</td>
							<td width="80" height="30" align="right">联名机构编号</td>
							<td>
								<s:textfield name="giftDef.jinstId" cssClass="{required:true}" readonly="true"
								disabled="true"/>
								<s:hidden name="giftDef.jinstId"/>
							</td>
						<tr>
						
						<tr>	
							<td width="80" height="30" align="right">清算方法</td>
							<td>
								<s:select name="giftDef.settMthd" list="settMthdTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select>
								<span class="field_tipinfo">请选择方法</span>
							</td>

							<td width="80" height="30" align="right">清算金额</td>
							<td>
								<s:textfield name="giftDef.settAmt" />
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
							<s:textfield name="giftDef.ptValue" />
								<span class="field_tipinfo">请输入分值</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">生效日期</td>
							<td>
								<s:textfield id="startDate" name="giftDef.effDate" onclick="WdatePicker({minDate:'%y-%M-%d'})" cssStyle="width:68px;" />
								<span class="field_tipinfo">请输入日期</span>
							</td>
							
							<td width="80" height="30" align="right">失效日期</td>
							<td>
							<s:textfield name="giftDef.expirDate" onclick="getExpirDate();" cssStyle="width:68px;" />
								<span class="field_tipinfo">请输入日期</span>
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
					<s:token name="_TOKEN_GIFT_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>