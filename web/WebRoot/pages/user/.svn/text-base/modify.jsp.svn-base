<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>修改用户</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="80" height="30" align="right">用户编号</td>
							<td style="padding-left: 20px;">
								${userInfo.userId}
								<s:hidden name="userInfo.userId" />
							</td>
							<td width="80" height="30" align="right">用户名称</td>
							<td>
								<s:textfield name="userInfo.userName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入用户名称</span>
							</td>
						</tr>
						<s:if test="showBranch">
						<tr>
							<td width="80" height="30" align="right">所属机构</td>
							<td>
								<s:select name="userInfo.branchNo" list="branchs" listKey="branchCode" listValue="branchName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择所属机构</span>
							</td>
						</tr>
						</s:if>
						<s:if test="showDept">
						<tr>
							<td width="80" height="30" align="right">所属部门</td>
							<td>
								<s:select name="userInfo.deptId" list="depts" listKey="deptId" listValue="deptName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择所属机构</span>
							</td>
						</tr>
						</s:if>
						<s:if test="showMerch">
						<tr>
							<td width="80" height="30" align="right">所属商户</td>
							<td>
								<s:select name="userInfo.merchantNo" list="merchs" listKey="merchId" listValue="merchName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择所属商户</span>
							</td>
						</tr>
						</s:if>
						<tr>
							<td width="80" height="30" align="right">联系电话</td>
							<td>
								<s:textfield name="userInfo.phone" cssClass="{digit:true}"/>
								<span class="field_tipinfo">请输入联系电话</span>
							</td>	
							<td width="80" height="30" align="right">手机号码</td>
							<td>
								<s:textfield name="userInfo.mobile" cssClass="{digit:true}"/>
								<span class="field_tipinfo">请输入手机号码</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">Email</td>
							<td>
								<s:textfield name="userInfo.email" cssClass="{email:true}"/>
								<span class="field_tipinfo">请输入Email</span>
							</td>	
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" style="margin-left:30px;" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/user/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_USER_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>