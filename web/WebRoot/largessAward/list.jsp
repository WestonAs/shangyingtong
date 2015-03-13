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
		<f:js src="/js/datePicker/WdatePicker.js"/>

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
							<td align="right">小票号</td>
							<td><s:textfield name="largessReg.ticketNo" cssClass="{digitOrLetter:true}"/></td>
							<td align="right">赠品名称</td>
							<td><s:textfield name="largessReg.largessName" /></td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="4">
							<input type="submit" value="查询" id="input_btn2"  name="ok" />
							<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							<f:pspan pid="largessaward_add"><input style="margin-left:30px;" type="button" value="派赠登记" onclick="javascript:gotoUrl('/largessAward/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_LARGESSAWARD_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">派赠编号</td>
			   <td align="center" nowrap class="titlebg">赠品号码</td>
			   <td align="center" nowrap class="titlebg">赠品名称</td>
			   <td align="center" nowrap class="titlebg">赠品数量</td>
			   <td align="center" nowrap class="titlebg">小票号</td>
			   <td align="center" nowrap class="titlebg">客户姓名</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td align="left" nowrap>${largessRegId}</td>
			  <td align="center" nowrap>${largessId}</td>
			  <td align="center" nowrap>${largessName}</td>
			  <td align="center" nowrap>${largessNum}</td>
			  <td align="center" nowrap>${ticketNo}</td>
			  <td align="center" nowrap>${custName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/largessAward/detail.do?largessReg.largessRegId=${largessRegId}&largessReg.largessId=${largessId}">查看</f:link>
			  		<!--<f:pspan pid="largessaward_modify"><f:link href="/largessAward/showModify.do?largessReg.largessRegId=${largessRegId}">编辑</f:link></f:pspan>-->
			  		
			  		<f:pspan pid="largessaward_delete">
			  			<a href="javascript:submitUrl('searchForm', '/largessAward/delete.do?largessRegId=${largessRegId}', '确定要删除吗？');" />删除</a>
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