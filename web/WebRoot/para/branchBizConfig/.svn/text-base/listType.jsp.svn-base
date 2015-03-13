<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>机构业务配置-类型</title>
		
		<f:css href="/css/page.css"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>

		<script>	
		</script>
		
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="listType.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>
							<span class="caption_title"><f:link href="/para/branchBizConfig/list.do">机构业务配置</f:link></span> | 机构业务配置-类型
						</caption>
						<tr>
							<td width="80" height="30" align="right">类型编号</td>
							<td><s:textfield  name="branchBizConfigType.configType"/></td>
							<td width="80" height="30" align="right">类型名称</td>
							<td><s:textfield name="branchBizConfigType.configTypeName"/></td>
							<td width="80" height="30" align="right">备注</td>
							<td><s:textfield name="branchBizConfigType.remark"/></td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="4" >
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" 
									onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="branchBizConfig_add">
									<input style="margin-left:30px;" type="button" value="添加" onclick="javascript:gotoUrl('/para/branchBizConfig/showAddType.do');" id="input_btn3"  name="escape" />
								</f:pspan>
								
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/para/branchBizConfig/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
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
			   <td align="center" nowrap class="titlebg">类型编号</td>
			   <td align="center" nowrap class="titlebg">类型名称</td>
			   <td align="center" nowrap class="titlebg">备注</td>
			   <td align="center" nowrap class="titlebg">更新人</td>
			   <td align="center" nowrap class="titlebg">更新时间</td>			   
			   <td align="center" nowrap class="titlebg">插入时间</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td align="left" nowrap>${configType}</td>
			  <td align="left" nowrap>${configTypeName}</td>
			  <td align="left" nowrap>${remark}</td>
			  <td align="center" nowrap>${updateUser }</td>
			  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			  <td align="center" nowrap><s:date name="insertTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<s:form id="operateForm" />
			  		<f:pspan pid="branchBizConfig_modify">
			  			<f:link href="/para/branchBizConfig/showModifyType.do?branchBizConfigType.configType=${configType}">
			  				编辑
			  			</f:link>
				  	</f:pspan>
				  	&nbsp;
				  	<f:pspan pid="branchBizConfig_delete">
			  			<a href="javascript:submitUrl('operateForm', '/para/branchBizConfig/deleteType.do?branchBizConfigType.configType=${configType}', '确定要删除吗？');" >
			  				删除
			  			</a>
			  		</f:pspan>	
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		
		<%-- 
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
				<span class="note_div">注释</span>
				<ul class="showli_div">
					<li class="showli_div"></li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		--%>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>