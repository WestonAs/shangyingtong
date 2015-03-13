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
		<script language="javascript">
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
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">ID</td>
							<td><s:textfield name="cardSmsRecord.id" cssClass="{digit:true}"/></td>
							
							<s:if test="cardRoleLogined">
								<td align="right">发卡机构</td>
								<td>
									<s:select name="cardSmsRecord.issNo" list="cardBranchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName"></s:select>
								</td>
							</s:if>
							<s:else>
								<td align="right">发卡机构编号</td>
								<td>
									<s:textfield name="cardSmsRecord.IssNo"/>
								</td>
							</s:else>

							<td align="right">手机号</td>
							<td><s:textfield name="cardSmsRecord.mobile"/></td>

						</tr>
	
						<tr>
							<td align="right">状态</td>
							<s:set name="flagMap" value='#{"1":"待处理", "0":"待发送", "2":"已发送", "3":"发送失败"}' />
							<td><s:select  name="cardSmsRecord.flag" list="flagMap" headerKey="" headerValue="--请选择--" /></td>

							<td height="30" colspan="5">
								<input type="submit" value="查询"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="cardSmsRecord_add">
									<input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/intgratedService/cardSmsRecord/showAdd.do');"  name="escape" />
								</f:pspan>
								<f:pspan pid="cardSmsRecord_add">
									<input style="margin-left:30px;" type="button" value="文件方式新增" onclick="javascript:gotoUrl('/intgratedService/cardSmsRecord/showFileModeAdd.do');"  name="escape" />
								</f:pspan>
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
			   <td align="center" nowrap class="titlebg">ID</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">手机号</td>
			   <td align="center" nowrap class="titlebg">发送时间</td>
			   <td align="center" nowrap class="titlebg">发送内容</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${id}</td>
			  <td nowrap align="center">${issNo}-${fn:branch(issNo)}</td>
			  <td align="center" nowrap>${mobile}</td>
			  <td align="center" nowrap><s:date name="sendTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			  <td align="center" nowrap title="${smsSendContent}">
				  	<s:if test="smsSendContent.length()>30">
				  		<s:property value="smsSendContent.substring(0,30)" /> ...
				  	</s:if>
				  	<s:else>
				  		<s:property value="smsSendContent" />
				  	</s:else>
			  </td>
			  <td align="center" nowrap><s:property value="#flagMap[flag]"/></td>
			  <td align="center" nowrap>
			  	<span class="redlink">
				  	<f:link href="/intgratedService/cardSmsRecord/detail.do?cardSmsRecord.id=${id}">明细</f:link>
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