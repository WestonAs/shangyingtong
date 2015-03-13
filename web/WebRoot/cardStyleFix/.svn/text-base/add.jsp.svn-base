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
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

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
				<s:form action="add.do" id="inputForm" enctype="multipart/form-data" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:hidden name="makeCardReg.branchCode"/>
								<input type="text" value="${fn:branch(makeCardReg.branchCode)}" class="{required:true} readonly" readonly="readonly" maxlength="40"/>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡子类型</td>
							<td>
								<s:select name="makeCardReg.cardSubtype" list="cardSubTypeList" headerKey="" headerValue="--请选择--" listKey="cardSubclass" listValue="cardSubclassName" 
								cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择卡子类型</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡样名称</td>
							<td>
								<s:textfield name="makeCardReg.makeName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入卡样名称</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">制卡厂商</td>
							<td>
								<s:select name="makeCardReg.makeUser" list="makeBranchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName" 
									cssClass="{required:true}" ></s:select>
								<span class="field_tipinfo">请选择制卡厂商</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">初始卡样</td>
							<td>
								<s:file name="upload" cssClass="{required:true}" contenteditable="false" ></s:file>
								<span class="field_tipinfo">请上传一个压缩包（包含卡样分成和合成文件）</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="上一步"
									onclick="gotoUrl('/cardStyleFix/preShowAdd.do?makeCardReg.branchCode=${makeCardReg.branchCode}')" />
								<input type="submit" value="提交" id="input_btn2" name="ok" class="ml30" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/cardStyleFix/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARDSTYLEFIX_ADD"/>
					<s:hidden id="privileges" name="privileges" />
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>