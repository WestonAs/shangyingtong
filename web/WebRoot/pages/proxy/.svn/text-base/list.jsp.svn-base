<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
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
		<f:js src="/js/paginater.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">代理类型</td>
							<td><s:select name="branchProxy.proxyType" list="types" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select></td>
							
							<td align="right">代理机构</td>
							<td><s:textfield name="branchProxy.proxyName"/></td>
							
							<td align="right">发展机构</td>
							<td><s:textfield name="branchProxy.respName"/></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="branchmgr_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/pages/proxy/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_PROXY_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">代理机构</td>
			   <td align="center" nowrap class="titlebg">发展机构</td>
			   <td align="center" nowrap class="titlebg">代理类型</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${proxyId}-${proxyName}</td>
			  <td nowrap>${respOrg}-${respName}</td>
			  <td align="center" nowrap>${proxyTypeName}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/proxy/detail.do?branchProxy.proxyId=${proxyId}&branchProxy.respOrg=${respOrg}&branchProxy.proxyType=${proxyType}">查看</f:link>
			  		<s:if test="proxyType == 22">
			  		<f:pspan pid="proxymgr_assign">
			  			<f:link href="/pages/proxy/showAssign.do?branchProxy.proxyId=${proxyId}&branchProxy.respOrg=${respOrg}&branchProxy.proxyType=${proxyType}">分配权限</f:link>
			  		</f:pspan>
			  		</s:if>
			  		<s:if test="status == 00">
			  		<f:pspan pid="proxymgr_cancel">
			  			<a href="javascript:submitUrl('searchForm', '/pages/proxy/cancel.do?proxyId=${proxyId}&respOrg=${respOrg}&proxyType=${proxyType}', '确定要注销吗？');" />注销</a>
			  		</f:pspan>
			  		</s:if>
			  		<s:else>
			  		<f:pspan pid="proxymgr_activate">
			  			<a href="javascript:submitUrl('searchForm', '/pages/proxy/activate.do?proxyId=${proxyId}&respOrg=${respOrg}&proxyType=${proxyType}', '确定要生效吗？');" />生效</a>
			  		</f:pspan>
			  		</s:else>
			  		<f:pspan pid="proxymgr_delete">
			  			<a href="javascript:submitUrl('searchForm', '/pages/proxy/delete.do?proxyId=${proxyId}&respOrg=${respOrg}&proxyType=${proxyType}', '确定要删除吗？');" />删除</a>
			  		</f:pspan>
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>