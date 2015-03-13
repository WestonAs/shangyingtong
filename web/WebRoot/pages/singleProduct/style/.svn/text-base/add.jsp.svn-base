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
			$(function(){
				Selector.selectModel('id_cardStyleNames', 'id_cardStyleIds', false, '${loginBranchCode}');
				Selector.selectBranch('id_brancheCodeName', 'id_brancheCode', true, '20', '', '', '${loginBranchCode}');
			});
		</script>
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
							<td width="80" height="30" align="right">卡样名称</td>
							<td>
								<s:textfield name="cardExampleDef.cardExampleName" cssClass="{required:true}" maxlength="40"/>
								<span class="field_tipinfo">请输入卡样名称</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡样</td>
							<td>
								<s:file name="upload" cssClass="{required:true}" contenteditable="false" ></s:file>
								<span class="field_tipinfo">请上传一个压缩包（包含卡样分成和合成文件）</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">套餐</td>
							<td>
								<s:hidden id="id_cardStyleIds" name="planId"/>
								<s:textfield id="id_cardStyleNames" name="planIdNames" />
								<span class="field_tipinfo">请选择套餐（选填项，没有可不选）</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:hidden id="id_brancheCode" name="cardExampleDef.cardIssuer"/>
								<s:textfield id="id_brancheCodeName" name="brancheCodeName"/>
								<span class="field_tipinfo">请选择发卡机构（选填，若填则为该发卡机构定制卡样，其它发卡机构不能用）</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/singleProduct/style/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_PRODUCT_STYLEFIX_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>