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
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>

		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/validate.js"/>
		
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<script type="text/javascript">
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" enctype="multipart/form-data" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right" class="field_tipinfo">积分类型</td>
							<td>
								<s:select name="pointSendApplyReg.ptClass" list="pointClassDefs" 
									headerKey="" headerValue="--请选择--" listKey="ptClass" listValue="className" 
									cssClass="{required:true}" />
								<span class="field_tipinfo"></span>
							</td>
							<td width="80" height="30" align="right">开始日期</td>
							<td>
								<s:textfield name="pointSendApplyReg.beginDate" onclick="WdatePicker();" cssClass="{required:true}" />
								<span class="field_tipinfo"></span>
							</td>
							
							<td width="80" height="30" align="right">结束日期</td>
							<td>
								<s:textfield name="pointSendApplyReg.endDate" onclick="WdatePicker();" cssClass="{required:true}" />
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">阀值</td>
							<td>
								<s:textfield name="pointSendApplyReg.thresholdValue" cssClass="{required:true, num:true}" maxlength="10"/>
								<span class="field_tipinfo"></span>
							</td>
							
							<td width="80" height="30" align="right">赠送积分</td>
							<td>
								<s:textfield id="totalPoint" name="pointSendApplyReg.sendPoint" cssClass="{required:true, num:true}" maxlength="10"/>
								<span class="field_tipinfo"></span>
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="pointSendApplyReg.remark" maxlength="255"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/intgratedService/pointBiz/pointAccumulationSend/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_EXTERNAL_CARD_IMPORT_ADD"/>
				</s:form>
				
				<span class="note_div">注释</span>
				<ul class="showli_div">
					<li class="showli_div">开始日期 和 结束日期 指的是清算日期。</li>
					<li class="showli_div">阀值：用户在 开始日期 到 结束日期 内，累积积分超过该阀值则具有被赠送资格。</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>