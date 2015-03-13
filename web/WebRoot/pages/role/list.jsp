<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
		<f:js src="/js/paginater.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<script>
			$(function(){
				if('${loginRoleType}' == '00'){
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '00,01,11,12,20,21,22,30,31,32');
					Selector.selectMerch('idMerchName', 'idMerchId', true);
				} else if('${loginRoleType}' == '01'){
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '01,11,12,20,21,22,30,31,32', '', '', '${loginBranchCode}');
					Selector.selectMerch('idMerchName', 'idMerchId', true, '', '', '', '${loginBranchCode}');
				}
			});
		</script>

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
				<s:form action="list.do" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<s:if test="loginRoleType == 00 || loginRoleType == 01">
						<tr>
							<s:if test="showBranch">
							<td align="right">所属机构</td>
							<td>
								<s:hidden id="idBranchCode" name="roleInfo.branchNo"/><s:textfield id="idBranchName" name="userBranchName" />
							</td>
							</s:if>
							<!-- 
							<s:if test="showDept">
							<td align="right">所属部门</td>
							<td>
								<s:select name="roleInfo.deptId" headerKey="" headerValue="--请选择--" list="deptList" listKey="deptId" listValue="deptName"></s:select>
							</td>
							</s:if>
							 -->
							<s:if test="showMerch">
							<td align="right">所属商户</td>
							<td>
								<s:hidden id="idMerchId" name="roleInfo.merchantNo"/><s:textfield id="idMerchName" name="userMerchName" />
							</td>
							</s:if>
							<td align="right">角色类型</td>
							<td>
								<s:select name="roleInfo.roleType" headerKey="" headerValue="--请选择--" list="roleTypeList" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">角色编号</td>
							<td><s:textfield name="roleInfo.roleId"/></td>
							<td align="right">角色名称</td>
							<td><s:textfield name="roleInfo.roleName"/></td>
							<td height="30" colspan="2">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<f:pspan pid="rolemgr_add"><input style="margin-left:10px;" type="button" value="新增" onclick="javascript:gotoUrl('/pages/role/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
						</s:if>
						<s:else>
						<tr>
							<s:if test="showBranch">
							<td align="right">所属机构</td>
							<td>
								<s:select name="roleInfo.branchNo" headerKey="" headerValue="--请选择--" list="branchList" listKey="branchCode" listValue="branchName" onmouseover="FixWidth(this)"></s:select>
							</td>
							</s:if>
							<s:if test="showDept">
							<td align="right">所属部门</td>
							<td>
								<s:select name="roleInfo.deptId" headerKey="" headerValue="--请选择--" list="deptList" listKey="deptId" listValue="deptName" onmouseover="FixWidth(this)"></s:select>
							</td>
							</s:if>
							<s:if test="showMerch">
							<td align="right">所属商户</td>
							<td>
								<s:select name="roleInfo.merchantNo" headerKey="" headerValue="--请选择--" list="merchList" listKey="merchId" listValue="merchName" onmouseover="FixWidth(this)"></s:select>
							</td>
							</s:if>
							<td align="right">角色编号</td>
							<td><s:textfield name="roleInfo.roleId"/></td>
						</tr>
						<tr>
							<td align="right">角色名称</td>
							<td><s:textfield name="roleInfo.roleName"/></td>
							<td height="30" colspan="3">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="rolemgr_add"><input style="margin-left:10px;" type="button" value="新增" onclick="javascript:gotoUrl('/pages/role/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
						</s:else>
					</table>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">角色编号</td>
			   <td align="center" nowrap class="titlebg">角色名称</td>
			   <td align="center" nowrap class="titlebg">角色类型</td>
			   <td align="center" nowrap class="titlebg">所属机构</td>
			   <td align="center" nowrap class="titlebg">所属商户</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data">
			<tr>
			  <td nowrap>${roleId}</td>
			  <td nowrap>${roleName}</td>
			  <td align="center" nowrap>${roleTypeName}</td>
			  <td nowrap>${fn:branch(branchNo)}</td>
			  <td nowrap>${fn:merch(merchantNo)}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/role/detail.do?roleInfo.roleId=${roleId}">查看</f:link>
			  		<f:pspan pid="rolemgr_modify"><f:link href="/pages/role/showModify.do?roleInfo.roleId=${roleId}">编辑</f:link></f:pspan>
			  		<f:pspan pid="rolemgr_delete"><f:link href="/pages/role/delete.do?roleInfo.roleId=${roleId}" onclick="if (!confirm('确定要删除吗？')){return false;}">删除</f:link></f:pspan>
			  	</span>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>