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
		<f:js src="/js/date/WdatePicker.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		function getNewExpirDate(){
			WdatePicker({minDate:'#F{$dp.$D(\'expirDateId\')}'});
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
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="80" height="30" align="right">卡延期ID</td>
							<td>
								${cardDeferReg.cardDeferId}
								<s:hidden name="cardDeferReg.cardDeferId"/>
							</td>

							<td width="80" height="30" align="right">卡号</td>
							<td>
								<s:textfield name="cardDeferReg.cardId" readonly="true"/>
								<!-- 
								${cardDeferReg.cardId}
								<s:textfield name="cardDeferReg.cardId" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入卡号</span>
								 -->
							</td>
						</tr>
						<tr>
							<td align="right">原生效日期</td>
							<td>
								<s:textfield name="cardDeferReg.effDate" readonly="true"/>
							<!-- 
								${cardDeferReg.effDate}
								<s:textfield name="cardDeferReg.acctId" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入账号ID</span>
								 -->
							</td>
						
							<td align="right">原失效日期</td>
							<td>
								<s:textfield name="cardDeferReg.expirDate" id="expirDate" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">延期日期</td>
							<td>
								<s:textfield name="cardDeferReg.newExpirDate" id="newExpirDate" onclick="getNewExpirDate();" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入延期日期</span>
							</td>
							<td width="80" height="30" align="right">备注</td>
							<td>
							<s:textfield name="cardDeferReg.remark" />
								<span class="field_tipinfo">请输入备注</span>
							</td>
						</tr>
						
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<s:hidden name="cardDeferReg.cardDeferCode"/>
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" style="margin-left:30px;" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/carddDefer/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARDDDEFER_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>