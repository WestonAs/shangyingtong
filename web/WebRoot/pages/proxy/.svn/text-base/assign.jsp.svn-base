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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
		function checkTree(){
			if (!FormUtils.hasSelected('groups')) {
				showMsg("请选择权限点!");
				return false;
			}
			return true;
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="assign.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="100" height="30" align="right">发卡机构</td>
							<td>
								<input type="text" class="readonly" value="${fn:branch(branchProxy.respOrg)}" />
								<s:hidden id="respOrg" name="branchProxy.respOrg" />
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">代理机构</td>
							<td>
								<input type="text" class="readonly" value="${fn:branch(branchProxy.proxyId)}" />
								<s:hidden id="proxyId" name="branchProxy.proxyId" />
							</td>
						</tr>
						<tr>
							<td width="130" height="30" align="right">可分配权限点</td>
							<td>
								<s:iterator value="cardPrivilegeList">
									<span>
									<input type="checkbox" name="groups" value="${id}" 
										<s:if test='hasGroup.indexOf("," + id + ",") != -1'>checked</s:if>/>${name}<br />
									</span>
								</s:iterator>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" onclick="return checkTree()"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/proxy/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_PROXY_ASSIGN"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>