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
		<f:js src="/js/common.js?d=20130508"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			$(function(){
				// 选择分支机构
				Selector.selectBranch('id_FenzhiBranchName', 'id_FenzhiBranchCode', true, '01');
				
				Selector.selectArea('id_areaName', 'id_areaCode', true);
			});
		</script>
		
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
							<td align="right">机构编号</td>
							<td><s:textfield id="idBranchCode" name="branch.branchCode" /></td>
							
							<td align="right">机构名称</td>
							<td><s:textfield name="branch.branchName"/></td>
							<s:if test="!userOfLimitedTransQuery">
								<td align="right">地区代码</td>
								<td><s:hidden id="id_areaCode" name="branch.areaCode" /><s:textfield id="id_areaName" name="areaName" /></td>
							</s:if>
						</tr>
	
						<tr>
							<s:if test="!userOfLimitedTransQuery">
								<td align="right">机构类型</td>
								<td>
									<s:select name="type" list="typeList" 
										headerKey="" headerValue="--请选择--" 
										listKey="value" listValue="name" />
								</td>
								<td align="right">状态</td>
								<td>
									<s:select name="branch.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
								</td>
								<td align="right">更新时间</td>
								<td>
									<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
									<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
								</td>
							</s:if>
						</tr>
						<tr>
						    <s:if test="showCenter">
							<td align="right">分支机构</td>
							<td>
							    <s:hidden  id="id_FenzhiBranchCode" name="parent" />
								<s:textfield  id="id_FenzhiBranchName" name="parentName" />
							</td>
							</s:if>
							<td align="right">是否单机</td>
							<td><s:select name="branch.singleProduct" list="yesOrNoList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select></td>
							<td align="right">审核时间</td>
							<td>
								<s:textfield id="checkStartDate" name="checkStartDate" onclick="getCheckStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="checkEndDate" name="checkEndDate" onclick="getCheckEndDate();"  cssStyle="width:68px;"/>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm'); $('#id_areaCode').val('');$('#id_areaName').val('');$('#id_FenzhiBranchCode').val('');$('#id_FenzhiBranchName').val('');" name="escape" />
								<f:pspan pid="branchmgr_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/pages/branch/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
								<f:pspan pid="branchmgr_add">
									<s:if test='("00").equals(loginRoleType)'>
									<input style="margin-left:30px;" type="button" value="提交审核" onclick="javascript:submitUrl('searchForm', '/pages/branch/submitCheck.do', '将把所有“待提交”状态的机构提交审核，确定要提交审核吗？');" id="input_btn4"  name="escape" />
									</s:if>
								</f:pspan>
							</td>
						</tr>
					</table>
					<s:hidden id="idBranchs" name="branchs"/>
					<s:token name="_TOKEN_BRANCH_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">机构编号</td>
			   <td align="center" nowrap class="titlebg">机构名称</td>
			   <td align="center" nowrap class="titlebg">联系人</td>
			   <td align="center" nowrap class="titlebg">联系电话</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${branchCode}</td>
			  <td nowrap>${branchName}</td>
			  <td align="center" nowrap>${contact}</td>
			  <td align="center" nowrap>${phone}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/branch/detail.do?branch.branchCode=${branchCode}">查看</f:link>
			  	</span>
			  		<s:if test="(centerOrCenterDeptRoleLogined || fenzhiRoleLogined) && !unpassStatus">
				  		<span class="redlink">
				  			<f:pspan pid="branchmgr_modify"><f:link href="/pages/branch/showModify.do?branch.branchCode=${branchCode}">编辑</f:link></f:pspan>
				  		</span>
			  		</s:if>
			  	<s:if test="status==00">
			  		<span class="redlink">
			  			<f:pspan pid="branchmgr_stop">
			  				<a href="javascript:submitUrl('searchForm', '/pages/branch/stop.do?branchCode=${branchCode}', '确定要停用吗？');"/>停用</a>
			  			</f:pspan>
			  		</span>
			  	</s:if>
			  	<s:elseif test="status==10">
			  		<span class="redlink">
			  			<f:pspan pid="branchmgr_start">
				  			<a href="javascript:submitUrl('searchForm', '/pages/branch/start.do?branchCode=${branchCode}', '确定要启用吗？');" />启用</a>
				  		</f:pspan>
			  		</span>
			  	</s:elseif>
			  	<s:elseif test="status==30 || status==40">
			  		<span class="redlink">
			  			<f:pspan pid="branchmgr_delete">
				  			<a href="javascript:submitUrl('searchForm', '/pages/branch/delete.do?branchCode=${branchCode}', '确定要删除吗？');" />删除</a>
				  		</f:pspan>
				  	</span>
			  	</s:elseif>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>