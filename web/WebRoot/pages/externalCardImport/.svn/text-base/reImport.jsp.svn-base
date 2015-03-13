<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<s:if test='externalCardImportReg.externalNumMakeCard'>
			<s:set var="pageTitle" value='"外部号码开卡"'/>
			<s:set var="listActionDo" value='"listExternalNumMakeCard.do"'/>
			<s:set var="externalImportNotice" value='"开卡上传文件名格式：kk_发卡机构编号_原文件日期_原批次.txt ，如：kk_10005850_20120828_001.txt"'/>
		</s:if>
		<s:else>
			<s:set var="pageTitle" value='"外部卡导入"'/>
			<s:set var="listActionDo" value='"list.do"'/>
			<s:set var="externalImportNotice" value='"上传文件名格式规范：发卡机构编号_原文件日期_原批次.txt ，如：10005850_20120828_001.txt"'/>
		</s:else>
		<title>重新 ${pageTitle}</title>
		
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

		<script type="text/javascript" src="externalCardImport.js"></script>
		
	</head>
    
	<body>
		<div class="location">您当前所在位置： <span class="redlink"><a href="<%=_contextPath%>/home.jsp">首页</a></span> > 综合业务  > ${pageTitle}</div>
		<div class="msg" style="display: none; float: left">
			<span id="_msg_content" style="float: left"></span>
			<a id="_msg_close" href="javascript:hideMsg();" style="float: right;color: red">关闭 X</a>
		</div>
				
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="reImport.do" id="inputForm" enctype="multipart/form-data" cssClass="validate">
					<s:hidden name="externalCardImportReg.id"/>
					<s:hidden name="externalCardImportReg.uptype" />
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>重新 ${pageTitle}</caption>
						<tr>
							<td width="80" height="30" align="right">卡BIN</td>
							<td>
								<s:textfield name="externalCardImportReg.binNo" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo"></span>
							</td>
							<td width="80" height="30" align="right">卡数量</td>
							<td>
								<s:textfield name="externalCardImportReg.totalCount" cssClass="{required:true, digits:true, min:1}" maxlength="5"/>
								<span class="field_tipinfo">请输入正整数</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡子类型</td>
							<td>
								<s:textfield name="externalCardImportReg.cardSubclass" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo"></span>
							</td>
							<td width="80" height="30" align="right">通用积分类型</td>
							<td>
								<s:textfield name="externalCardImportReg.ptClass" cssClass="readonly" readonly="true"/>
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">总金额</td>
							<td>
								<s:textfield name="externalCardImportReg.totalAmt" cssClass="{required:true, num:true}" maxlength="10"/>
								<span class="field_tipinfo">元</span>
								<span class="error_tipinfo">请输入总金额</span>
							</td>
							
							<td width="80" height="30" align="right">总积分</td>
							<td>
								<s:textfield id="totalPoint" name="externalCardImportReg.totalPoint" cssClass="{required:true, num:true}" maxlength="10"/>
								<span class="field_tipinfo">请输入总通用积分</span>
							</td>
						
						</tr>
						<tr>
							<td width="80" height="30" align="right">（新）卡导入文件</td>
							<td >
								<s:file name="upload" cssClass="{required:true}" contenteditable="false"></s:file>
								<span class="field_tipinfo">请选择文件（必须与旧文件同名）</span>
							</td>
							
							<td width="80" height="30" align="right">备注</td>
							<td >
								<s:textfield name="externalCardImportReg.remark" maxlength="255"/>
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" name="ok" onclick="return validateForm();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/externalCardImport/${listActionDo}?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_EXTERNAL_CARD_IMPORT_ADD"/>
				</s:form>
				
				<span class="note_div">注释</span>
				<ul class="showli_div">
					<li class="showli_div">${externalImportNotice }</li>
					<li class="showli_div">上传文件格式为：第一行固定为标题行：“卡号|金额|通用积分|初始密码”；第二行如：“2089999787878787|200|20|123456”</li>
					<li class="showli_div">字段值不能有空格；</li>
					<li class="showli_div">导入文件最大明细数为50000；</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>