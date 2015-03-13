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
			if('${loginRoleType}' == '00'){// 运营中心登录时
				Selector.selectBranch('idGroupId_sel', 'idGroupId', true, '12');
				Selector.selectBranch('idCardBranchNames', 'idCardBranches', false, '20');
			} else if('${loginRoleType}' == '01'){
				Selector.selectBranch('idGroupId_sel', 'idGroupId', true, '12', '', '', '${loginBranchCode}');
				Selector.selectBranch('idCardBranchNames', 'idCardBranches', false, '20', '', '', '${loginBranchCode}');
			}
		});
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
							<td width="80" height="30" align="right">集团</td>
							<td>
								<s:hidden id="idGroupId" name="cardGroup.groupId"/>
								<s:textfield id="idGroupId_sel" name="groupNames" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择集团</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:hidden id="idCardBranches" name="cardBranches"/>
								<s:textfield id="idCardBranchNames" name="branchNames" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择发卡机构</span>
							</td>
						</tr>
						
						<tr>	
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/cardGroup/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARD_GROUP_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>