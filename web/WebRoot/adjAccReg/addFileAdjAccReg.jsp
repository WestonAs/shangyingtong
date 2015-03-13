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
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		
		<script type="text/javascript" src="adjAccReg.js"></script>
		<script>
			function checkSubmit(){
				var needSignatureReg = $('#needSignatureReg').val();
				if (needSignatureReg == 'true' && !CheckUSBKey()) { // 证书签名失败
					return false;
				}
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="addFileAdjAccReg" id="inputForm" enctype="multipart/form-data" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">导入文件</td>
							<td>
								<s:file name="upload" cssClass="{required:true}" contenteditable="false" ></s:file>
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>
						<tr>	
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return checkSubmit();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/adjAccReg/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_FILEADJACCREG_ADD"/>
					
					<s:hidden id="needSignatureReg" name="formMap.needSignatureReg" />
					<s:hidden id="serialNo" name="formMap.serialNo"/>
					<s:hidden id="randomSessionId" name="formMap.randomSessionId" />
					<s:hidden id="signature" name="formMap.signature" />
				</s:form>
				
				<jsp:include flush="true" page="/common/certJsIncluded.jsp"></jsp:include>
				
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<span class="note_div">注释</span>
		<ul class="showli_div">
			<li class="showli_div">上传文件必须是文本格式，下载 <b><a href="adjAcctTmpl.txt">调账退货模板</a></b></li>
			<li class="showli_div">第一行为标题行，固定为：交易流水号</li>
			<li class="showli_div">第二行开始为数据行，比如：12345678</li>
		</ul>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>