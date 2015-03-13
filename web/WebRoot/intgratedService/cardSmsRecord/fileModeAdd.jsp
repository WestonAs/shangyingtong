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
				<s:form action="fileModeAdd.do" id="inputForm" enctype="multipart/form-data" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">卡导入文件</td>
							<td >
								<s:file name="upload" cssClass="{required:true}" contenteditable="false"></s:file>
								<span class="field_tipinfo">请选择文件</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" onclick="return validateForm()" name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/intgratedService/cardSmsRecord/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARD_SMS_RECORD_ADD"/>
				</s:form>
				<div>
					<span class="note_div">注释</span>
					<ul class="showli_div">
						<li class="showli_div">上传文件为txt文本文件，下载 <b><a href="smsRecordTmpl.txt">手机短信营销模板</a></b></li>
						<li class="showli_div">上传文件格式为：第一行固定为标题行：“手机号|短信内容”；第二行开始为明细行：如：“13511112222|xxxx”</li>
						<li class="showli_div">明细行数不能大于10000！</li>
						<li class="showli_div">短信内容 不能超过75个中文字符 或 150个英文字符！</li>
					</ul>
				</div>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>