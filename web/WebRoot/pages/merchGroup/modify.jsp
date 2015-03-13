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
		$(function(){
			if('${loginRoleType}' == '01'){// 分支机构登录时
				Selector.selectBranch('id_branchName', 'id_cardIssuer', true, '20', loadMerchGroup, '', '${loginBranchCode}');
			} else {
			    Selector.selectBranch('id_branchName', 'id_cardIssuer', true, '20', loadMerchGroup);
			}
			
			//
			var branchCode = $('#id_cardIssuer').val();
			if (!isEmpty(branchCode)){
				Selector.selectMerch('idMerchNames', 'idMerchs', false, branchCode);
			}
		});
		function loadMerchGroup() {
			var branchCode = $('#id_cardIssuer').val();
				
			if (!isEmpty(branchCode)){
				$('#idMerchNames').unbind().removeMulitselector();
				$('#idMerchs').val('');
				$('#idMerchNames').val('');
				Selector.selectMerch('idMerchNames', 'idMerchs', false, branchCode);
			} else {
				$('#idMerchNames').focus(function(){
					if (isEmpty(branchCode)){
						showMsg('请先选择发卡机构再选择商户');
					}
				});
			}
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="80" height="30" align="right">商圈代码</td>
							<td>
								<s:textfield name="merchGroup.groupId" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo">请输入商圈代码</span>
								<span class="error_tipinfo">请输入商圈代码</span>
							</td>
							<td width="80" height="30" align="right">商圈名称</td>
							<td>
								<s:textfield name="merchGroup.groupName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入商圈名称</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
							    <s:hidden  id="id_cardIssuer"  name="merchGroup.cardIssuer" />
							    <s:textfield  id="id_branchName"  name="branchName" cssClass="{required:true}" />
								<span class="field_tipinfo">请输入发卡机构</span>
							</td>
							<td width="80" height="30" align="right">选择商户</td>
							<td>
							    <s:hidden id="idMerchs" name="merchs"/>
								<s:textfield id="idMerchNames"  name="merchNames"  cssClass="{required:true}" />
								<span class="field_tipinfo">请选择商户</span>
							</td>
						</tr>
						<tr>	
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/merchGroup/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MERCH_GROUP_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>