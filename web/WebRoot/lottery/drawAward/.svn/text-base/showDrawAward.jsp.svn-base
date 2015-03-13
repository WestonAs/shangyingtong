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
				<s:form id="searchForm" action="showDrawAward.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
						    <td width="80" height="30" align="right">抽奖活动ID</td>
							<td>	<s:textfield name="awardReg.drawId"  cssClass="required:true  readonly" readonly="true"/></td>
							<td align="right">用户标识</td>
							<td><s:textfield name="awardReg.awdTicketNo" /></td>
						</tr>
						<tr>	
							<td></td>				
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							    <input type="button" value="返回" name="escape" onclick="gotoUrl('/lottery/drawAward/list.do?goback=goback')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_DRAWAWARD_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">用户标识</td>
			   <td align="center" nowrap class="titlebg">抽奖活动ID</td>
			   <td align="center" nowrap class="titlebg">礼品号</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">中奖操作员</td>
			   <td align="center" nowrap class="titlebg">中奖时间</td>
			   <td align="center" nowrap class="titlebg">兑奖操作员</td>
			   <td align="center" nowrap class="titlebg">兑奖时间</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td nowrap>${awdTicketNo}</td>
			  <td align="center" nowrap>${drawId}</td>
			  <td align="center" nowrap>${prizeNo}</td>
			  <td align="center" nowrap>${awdStatusName}</td>
			  <td align="center" nowrap>${awdOptr}</td>
			  <td align="center" nowrap><s:date name="awdTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			  <td align="center" nowrap>${exchgOptr}</td>
			  <td align="center" nowrap><s:date name="exchgTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<s:if test="awdStatus==01">
			  		    <f:pspan pid="draw_award_modify">
			  		        <a href="javascript:submitUrl('searchForm', '/lottery/drawAward/acceptDrawAward.do?awdTicketNo=${awdTicketNo}&drawId=${drawId}', '确定要领奖吗？');" />领奖</a>
			  		    </f:pspan>
			  		</s:if>
			  		<f:pspan pid="draw_award_delete">
			  			<a href="javascript:submitUrl('searchForm', '/lottery/drawAward/deleteDrawAward.do?awdTicketNo=${awdTicketNo}&drawId=${drawId}', '确定要删除吗？');" />删除</a>
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