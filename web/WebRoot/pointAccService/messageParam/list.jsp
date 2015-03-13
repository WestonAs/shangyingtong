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
							<td align="right">发卡机构</td>
							<s:if test="showCard">
							<td><s:select name="messageParam.cardIssuer" list="branchList" listKey="branchCode" listValue="branchName"></s:select>
							</s:if>
							<s:else>
							<td><s:textfield name="messageParam.branchName"/></td>
							</s:else>
							
							<td align="right">业务代码</td>
							<td>
							<td><s:textfield name="messageParam.eventCode"/></td>
							</td>
							<td align="right">操作员名称</td>
							<td><s:textfield name="messageParam.operator"/></td>
							</td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="messageparam_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/pointAccService/messageParam/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MESSAGEPARAM_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">业务代码</td>
			   <td align="center" nowrap class="titlebg">操作员名称</td>
			   <td align="center" nowrap class="titlebg">手机号码</td>
			   <td align="center" nowrap class="titlebg">更新用户名</td>
			   <td align="center" nowrap class="titlebg">更新时间</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="left" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
			  <td align="center" nowrap>${eventCode}</td>
			  <td align="center" nowrap>${operator}</td>
			  <td align="center" nowrap>${mobileNo}</td>
			  <td align="center" nowrap>${updateBy}</td>
			  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:pspan pid="messageparam_modify" >
			  			<f:link href="/pointAccService/messageParam/showModify.do?messageParam.cardIssuer=${cardIssuer}&messageParam.eventCode=${eventCode}">编辑</f:link></f:pspan>
			  		<f:pspan pid="messageparam_delete">
			  			<a href="javascript:submitUrl('searchForm', '/pointAccService/messageParam/delete.do?cardIssuer=${cardIssuer}&eventCode=${eventCode}', '确定要删除吗？');" />删除</a>
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