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
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			var parent =  $('#idParent').val();
			var branchCode = $('#id_BranchCode').val();

			Selector.selectBranch('id_branchName', 'id_BranchCode', true, '20', '', '', parent);

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
						<s:hidden id="idParent" name="parent" />
						<tr>
						<td width="80" height="30" align="right">发卡机构</td>
						<s:if test="showFenzhi">
						<td id="id_branch_2">
							<s:hidden id="id_BranchCode" name="rmaTransTypeLimit.insCode" cssClass="{required:true}" ></s:hidden>
							<s:textfield  id="id_branchName" cssClass="{required:true}" ></s:textfield>
						</td>
						</s:if>
						<s:elseif test="showCard">
						<td>
							<s:hidden name="rmaTransTypeLimit.insCode" ></s:hidden>
							<s:textfield name="rmaTransTypeLimit.insName" cssClass="readonly" readonly="true"></s:textfield>
						</td>
						</s:elseif>
						</tr>
						<tr id="id_transType_tr">
						<td width="80" height="30" align="right">交易类型列表</td>
						<td>
							<s:iterator value="transTypeList">
								<span>
								<input type="checkbox" name="transTypes" value="${value}" />${name}<br />
								</span>
							</s:iterator>
						</td>	
						</tr>	
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/rmaTransTypeLimit/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_RMATRANSTYPELIMIT_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>