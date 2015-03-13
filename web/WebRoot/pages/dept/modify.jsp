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
		<f:css href="/js/tree/dhtmlxtree.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/tree/dhtmlxcommon.js"/>
		<f:js src="/js/tree/dhtmlxtree.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script language="javascript">
		$(function(){
			$loadTree('/pages/role/initTreeByUpdateDept.do?deptId=${departmentInfo.deptId}', 'treebox_tree', true);
		});
		function checkTree(){
			var privilege = tree.getAllCheckedBranches();
			if (privilege == null || privilege == undefined || privilege == "") {
				alert("请选择权限!");
				return false;
			}
			$('#privileges').val(privilege);
			return true;
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
							<td width="120" height="30" align="right">部门代码</td>
							<td>
								${departmentInfo.deptId}
							</td>
						</tr>
						<tr>
							<td width="120" height="30" align="right">部门名称</td>
							<td>
								<s:textfield name="departmentInfo.deptName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入部门名称</span>
							</td>
						</tr>
						<tr>
							<td width="120" height="30" align="right" valign="top" style="padding-top: 10px">部门拥有权限</td>
							<td>
								<div id="treebox_tree" style="padding:5px;width:300px; background-color:#f5f5f5;border :1px solid Silver;">
									<img src="../../images/loading.gif" />正在加载...
								</div>
							</td>
						</tr>
						<tr><td>&nbsp;</td><td><input id="selectAll" type="checkbox" onclick="checkAllTree('selectAll', tree)" /><label for="selectAll">全选</label></td></tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" onclick="return checkTree()" name="ok"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/dept/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_DEPT_MODIFY"/>
					<s:hidden id="privileges" name="privileges" />
					<s:hidden name="departmentInfo.deptId" />
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>