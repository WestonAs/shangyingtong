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
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
			$(function(){
				if('${loginRoleType}' == '00'){
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '');
					Selector.selectMerch('idMerchId_sel', 'idMerchId', true);
				} else if('${loginRoleType}' == '01'){
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '', '', '', '${loginBranchCode}');
					Selector.selectMerch('idMerchId_sel', 'idMerchId', true, '', '', '', '${loginBranchCode}');
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
				<s:form action="list.do" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">日志类型</td>
							<td><s:select name="userLog.logType" list="logTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select></td>
							<td align="right">日志时间</td>
							<td>
								<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
							</td>
							
							<td align="right">用户编号</td>
							<td><s:textfield name="userLog.userId" /></td>
						</tr>
	
						<tr>
							<s:if test="showBranch">
							<td align="right">机构编号</td>
							<td>
								<s:hidden id="idBranchCode" name="userLog.branchNo"/>
								<s:textfield id="idBranchName" name="cardBranchName" /></td>
							</s:if>
							
							<s:if test="showMerch">
							<td align="right">商户编号</td>
							<td>
								<s:hidden id="idMerchId" name="userLog.merchantNo" />
								<s:textfield id="idMerchId_sel" name="merchId_sel"/>
							</td>
							</s:if>
							<s:if test="!showBranch && (!showMerch)">
								<td align="right">&nbsp;</td>
							</s:if>
							<td height="30" colspan="2">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
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
					   <td align="center" nowrap class="titlebg">用户编号</td>
					   <td align="center" nowrap class="titlebg">日志类型</td>
					   <td align="center" nowrap class="titlebg">日志内容</td>
					   <td align="center" nowrap class="titlebg">登录IP</td>
					   <td align="center" nowrap class="titlebg">日志时间</td>
					   <td align="center" nowrap class="titlebg">操作</td>
					</tr>
				</thead>
				<s:iterator value="page.data">
					<tr>
					  <td nowrap>${userId}</td>
					  <td align="center" nowrap>${logTypeName}</td>
					  <td align="left" nowrap title="${content}">
				  		<s:if test="content.length()>70">
					  		<s:property value="content.substring(0,70)" /> ...
					  	</s:if>
					  	<s:else>
					  		<s:property value="content" />
					  	</s:else>
					  </td>
					  <td align="center" nowrap>${loginIp}</td>
					  <td align="center" nowrap><s:date name="logDate" format="yyyy-MM-dd HH:mm:ss" /></td>
					  <td align="center" nowrap>
					  	<span class="redlink">
					  		<f:link href="/log/userlog/detail.do?userLog.id=${id}">查看</f:link>
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