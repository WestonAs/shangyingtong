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
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/validate.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" enctype="multipart/form-data" cssClass="validate">
					<div>
						<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
							<caption>${ACT.name}</caption>
							<tr>
								<td width="80" height="30" align="right">发卡机构</td>
								<td>
									<s:textfield name="membImportReg.branchName" readonly="true" cssClass="readonly" ></s:textfield>
								</td>
							</tr>
							<tr>
								<td width="80" height="30" align="right">会员注册文件导入</td>
								<td >
									<s:file name="upload" cssClass="{required:true}" contenteditable="false"></s:file>
									<span class="field_tipinfo">请选择文件</span>
								</td>
							</tr>
							<tr>
								<td width="80" height="30" align="right">&nbsp;</td>
								<td height="30" colspan="3">
									<input type="submit" value="提交" id="input_btn2" name="ok"/>
									<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
									<input type="button" value="返回" name="escape" onclick="gotoUrl('/cps/membImport/list.do?goBack=goBack')" class="ml30" />
								</td>
							</tr>
						</table>
						<s:token name="_TOKEN_MEMB_IMPORT_ADD"/>
					</div>
				</s:form>
				<span class="note_div">会员注册文件格式</span>
				<ul class="showli_div">
					<li class="showli_div">下载 <b><a href="membImportTmpl.txt">会员注册文件模板</a></b></li>
					<li class="showli_div">卡号|姓名|手机号|身份证号</li>
					<li class="showli_div">2088882990100000155|李XX|12345678902|999899999999</li>
					<li class="showli_div">注意：每次上传的文件名必须不一样</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>