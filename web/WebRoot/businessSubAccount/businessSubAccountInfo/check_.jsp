<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
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
							<td align="right">虚账户体系代码</td>
							<td><s:textfield name="accountSystemInfo.systemId" cssClass="{digitOrLetter:true}"/></td>
							
							<td align="right">开设机构编号</td>
							<td><s:textfield name="accountSystemInfo.custId"/></td> 
							<td align="right">虚账户体系名称</td>
							<td><s:textfield name="accountSystemInfo.systemName"/></td> 
						</tr>
	
						<tr> 
						<td></td>
							<td height="30" colspan="3">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid = "account_system_info_add" >
									<input style="margin-left:30px;" type="button" value="新增"  name="escape" id="input_btn3" onclick="javascript:gotoUrl('/businessSubAccount/accountSystemInfo/showAdd.do');"/>
								</f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_ACCOUNT_SYSTEM_INFO_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg"></td>
			   <td align="center" nowrap class="titlebg"></td> 
			   <td align="center" nowrap class="titlebg"></td>		
			   <td align="center" nowrap class="titlebg"></td>
			   <td align="center" nowrap class="titlebg"></td> 
			   <td align="center" nowrap class="titlebg"></td>			   

			    <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${systemId}</td>
			  <td align="center" nowrap>${systemName}</td>
			  <td align="center" nowrap>${custId}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/businessSubAccount/accountSystemInfo/detail.do?accountSystemInfo.systemId=${systemId}">查看</f:link>
			  		 
			  		<f:pspan pid="account_system_info_modify"><f:link href="/businessSubAccount/accountSystemInfo/showModify.do?accountSystemInfo.systemId=${systemId}">编辑</f:link></f:pspan>
			  		 
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