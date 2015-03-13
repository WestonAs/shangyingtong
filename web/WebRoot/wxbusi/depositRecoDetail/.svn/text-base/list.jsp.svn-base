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
							<td align="right">对账明细ID</td>
							<td>
								<s:textfield name="wxReconciliationDetail.id" />
							</td>
							<td align="right">银联商户号</td>
							<td>
								<s:textfield name="wxReconciliationDetail.merchNo"/>
							</td>
							<td align="right">差错类型</td>
							<td>
								<s:select name="wxReconciliationDetail.errorType" list="typeList" 
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name" />
							</td>		
						</tr>
						<tr>					
							<td align="right">处理状态</td>
							<td>
								<s:select name="wxReconciliationDetail.proStatus" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" />
							</td>
							<td align="right">处理时间</td>
							<td>
								<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
							</td>
							<td height="30" colspan="2">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_WX_RETRANSCARDREG_DETAIL_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">对账明细ID</td>
			   <td align="center" nowrap class="titlebg">银联商户号</td>
			   <td align="center" nowrap class="titlebg">交易清算日期</td>
			   <td align="center" nowrap class="titlebg">差错类型</td>
			   <td align="center" nowrap class="titlebg">处理状态</td>
			   <td align="center" nowrap class="titlebg">处理时间</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="left" nowrap>${id}</td>
			  <td align="center" nowrap>${merchNo}</td>
			  <td align="center" nowrap>${settDate}</td>
			  <td align="center" nowrap>${errorTypeName}</td>
			  <td align="center" nowrap>${proStatusName}</td>
			  <td align="center" nowrap><s:date name="proTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/wxbusi/depositRecoDetail/detail.do?wxReconciliationDetail.id=${id}">查看</f:link>
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