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
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
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
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="list.do">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<s:if test="loginRoleType == 00 || loginRoleType == 01">
							<tr>
								<s:if test="showBranch">
									<td align="right">所属机构</td>
									<td>
										<s:hidden id="idBranchCode" name="userInfo.branchNo"/><s:textfield id="idBranchName" name="userBranchName" />
									</td>
								</s:if>
								<s:if test="showDept">
									<td align="right">所属部门</td>
									<td>
										<s:select name="userInfo.deptId" headerKey="" headerValue="--请选择--" list="depts" listKey="deptId" listValue="deptName" onmouseover="FixWidth(this)"></s:select>
									</td>
								</s:if>
								<s:if test="showMerch">
									<td align="right">所属商户</td>
									<td>
										<s:hidden id="idMerchId" name="userInfo.merchantNo"/><s:textfield id="idMerchName" name="userMerchName" />
									</td>
								</s:if>
							</tr>
							<tr>
								<td align="right">用户编号</td>
								<td><s:textfield name="userInfo.userId"/></td>
								<td align="right">用户名</td>
								<td><s:textfield name="userInfo.userName"/></td>
								<td height="30" colspan="2">
									<input type="submit" value="查询" id="input_btn2"  name="ok" />
									<input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/pages/user/showAdd.do');" id="input_btn3"  name="escape" />
								</td>
							</tr>
						</s:if>
						<s:elseif test="loginRoleType == 20">
							<tr>
								<s:if test="showBranch">
									<td align="right">所属机构</td>
									<td>
										<s:select name="userInfo.branchNo" list="branchs" listKey="branchCode" listValue="branchName" headerKey="" headerValue="--请选择--" onmouseover="FixWidth(this)"></s:select>
									</td>
								</s:if>
								<s:if test="showDept">
									<td align="right">所属部门</td>
									<td>
										<s:select name="userInfo.deptId" list="depts" headerKey="" headerValue="--请选择--" listKey="deptId" listValue="deptName" onmouseover="FixWidth(this)"></s:select>
									</td>
								</s:if>
								<td align="right">用户编号</td>
								<td><s:textfield name="userInfo.userId"/></td>
							</tr>
							<tr>
								<td align="right">用户名</td>
								<td><s:textfield name="userInfo.userName"/></td>
								<td height="30" colspan="5">
									<input type="submit" value="查询" id="input_btn2"  name="ok" />
									<input class="ml30" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
									<input class="ml30" type="button" value="新增" onclick="javascript:gotoUrl('/pages/user/showAdd.do');" id="input_btn3"  name="escape" />
								</td>
							</tr>
						</s:elseif>
						<s:else>
							<tr>
								<s:if test="showBranch">
									<td align="right">所属机构</td>
									<td>
										<s:select name="userInfo.branchNo" list="branchs" listKey="branchCode" listValue="branchName" onmouseover="FixWidth(this)"></s:select>
									</td>
								</s:if>
								<s:if test="showDept">
									<td align="right">所属部门</td>
									<td>
										<s:select name="userInfo.deptId" list="depts" headerKey="" headerValue="--请选择--" listKey="deptId" listValue="deptName" onmouseover="FixWidth(this)"></s:select>
									</td>
								</s:if>
								<s:if test="showMerch">
									<td align="right">所属商户</td>
									<td>
										<s:select name="userInfo.merchantNo" list="merchs" listKey="merchId" listValue="merchName" onmouseover="FixWidth(this)"></s:select>
									</td>
								</s:if>
								<td align="right">用户编号</td>
								<td><s:textfield name="userInfo.userId"/></td>
								<td align="right">用户名</td>
								<td><s:textfield name="userInfo.userName"/></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td height="30" colspan="3">
									<input class="ml30" type="submit" value="查询" id="input_btn2"  name="ok" />
									<input class="ml30" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
									<input class="ml30" type="button" value="新增" onclick="javascript:gotoUrl('/pages/user/showAdd.do');" id="input_btn3"  name="escape" />
								</td>
							</tr>
						</s:else>
					</table>
					<s:token name="_TOKEN_USER_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">用户编号</td>
			   <td align="center" nowrap class="titlebg">用户名</td>
			   <td align="center" nowrap class="titlebg">手机</td>
			   <td align="center" nowrap class="titlebg">E-Mail</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${userId}</td>
			  <td align="left" nowrap>${userName}</td>
			  <td align="center" nowrap>${mobile}</td>
			  <td align="center" nowrap>${email}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/user/detail.do?userInfo.userId=${userId}">查看</f:link>
			  		<f:pspan pid="usermgr_assign"><f:link href="/pages/user/showAssign.do?userInfo.userId=${userId}">分配角色</f:link></f:pspan>
			  		<f:pspan pid="usermgr_modify"><f:link href="/pages/user/showModify.do?userInfo.userId=${userId}">编辑</f:link></f:pspan>
			  		<s:if test="state==00">
			  			<f:pspan pid="usermgr_cancel">
			  				<a href="javascript:submitUrl('searchForm', '/pages/user/cancel.do?userId=${userId}', '确定要注销吗？');" />注销</a>
			  			</f:pspan>
			  		</s:if>
			  		<s:elseif test="state==02">
			  			<f:pspan pid="usermgr_activate">
				  			<a href="javascript:submitUrl('searchForm', '/pages/user/activate.do?userId=${userId}', '确定要生效吗？');" />生效</a>
				  		</f:pspan>
			  		</s:elseif>
			  		<s:if test="centerOrCenterDeptRoleLogined || fenzhiRoleLogined">
				  		<f:pspan pid="usermgr_reset">
				  			<a href="javascript:submitUrl('searchForm', '/pages/user/resetPass.do?userId=${userId}', '确定要重置密码吗？');" />重置密码</a>
				  		</f:pspan>
			  		</s:if>
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