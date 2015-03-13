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
							<td align="right">商圈代码</td>
							<td><s:textfield name="merchGroup.groupId" cssClass="{digitOrLetter:true}"/></td>
							<td align="right">商圈名称</td>
							<td><s:textfield name="merchGroup.groupName" /></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="merchantgroupmgr_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/pages/merchGroup/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MERCH_GROUP_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
				<tr>
					<td align="center" nowrap class="titlebg">商圈代码</td>
					<td align="center" nowrap class="titlebg">商圈名称</td>
					<td align="center" nowrap class="titlebg">管理机构（运营机构）</td>
					<td align="center" nowrap class="titlebg">发卡机构</td>
					<td align="center" nowrap class="titlebg">状态</td>
					<td align="center" nowrap class="titlebg">操作</td>
				</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${groupId}</td>
			  <td align="center" nowrap>${groupName}</td>
			  <td align="center" nowrap>${manageBranch}-${fn:branch(manageBranch)}</td>
              <td align="center" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
              <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/merchGroup/detail.do?merchGroup.groupId=${groupId}">查看</f:link>
			  		<f:pspan pid="merchantgroupmgr_modify"><f:link href="/pages/merchGroup/showModify.do?merchGroup.groupId=${groupId}">编辑</f:link></f:pspan>
			  		<s:if test='"00".equals(status)'>
				  		<f:pspan pid="merchantgroupmgr_modify">
			  				<a href="javascript:submitUrl('searchForm', '/pages/merchGroup/cancel.do?groupId=${groupId}', '确定要注销吗？');" />注销</a>
			  			</f:pspan>
			  		</s:if>
			  		<s:if test='"10".equals(status)'>
				  		<f:pspan pid="merchantgroupmgr_modify">
			  				<a href="javascript:submitUrl('searchForm', '/pages/merchGroup/enable.do?groupId=${groupId}', '确定要启用吗？');" />启用</a>
			  			</f:pspan>
			  		</s:if>
<%--			  		<f:pspan pid="merchantgroupmgr_delete">--%>
<%--		  				<a href="javascript:submitUrl('searchForm', '/pages/merchGroup/delete.do?groupId=${groupId}', '确定要删除吗？');" />删除</a>--%>
<%--		  			</f:pspan>--%>
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