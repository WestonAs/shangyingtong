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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		/*
		===========================================
		//对字符串进行Html编码
		===========================================
		*/
		function toHtmlEncode(str){
		    str=str.replace(/&/g,"&amp;");
		    str=str.replace(/</g,"&lt;");
		    str=str.replace(/>/g,"&gt;");
		    str=str.replace(/\'/g,"&apos;");
		    str=str.replace(/\"/g,"&quot;");
		    str=str.replace(/\n/g,"<br>");
		    str=str.replace(/\ /g,"&nbsp;");
		    str=str.replace(/\t/g,"&nbsp;&nbsp;&nbsp;&nbsp;");
		    return str;
		}
		
		function submitNotice(){
			$('#idContent').val(toHtmlEncode($('#idContent').val()));
			$('#inputForm').submit();
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
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="80" height="30" align="right">标题</td>
							<td>
								<s:textfield name="publishNotice.title" cssClass="{required:true}" maxlength="30"/>
								<span class="field_tipinfo">请输入标题</span>
							</td>
						</tr>
						<tr>
							<td valign="middle" height="30" align="right" class="nes formlabel">内容</td>
							<td>
								<s:textarea id="idContent" rows="10" cols="40" theme="simple" name="publishNotice.content" cssClass="{required:true}" maxlength="1000" cssStyle=""/>
								<span class="field_tipinfo">请输入通知内容</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="提交" id="input_btn2" name="ok" onclick="submitNotice();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/publishNotice/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_PUBLISH_NOTICE_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>