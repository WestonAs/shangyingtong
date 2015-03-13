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
							<td align="right">奖券号</td>
							<td><s:textfield name="awardReg.awdTicketNo" /></td>
							<td align="right">流水号</td>
							<td><s:textfield name="awardReg.transSn" /></td>
							<td align="right">卡号</td>
							<td><s:textfield name="awardReg.cardId"/></td>		
						</tr>
						<tr>
							<td align="right">中奖人</td>
							<td><s:textfield name="awardReg.custName" /></td>
							<td align="right">证件号码</td>
							<td><s:textfield name="awardReg.credNo"/></td>		
						</tr>	
							<td></td>				
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_PRIZEAWARD_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">奖券号</td>
			   <td align="center" nowrap class="titlebg">抽奖活动编号</td>
			   <td align="center" nowrap class="titlebg">中奖人</td>
			   <td align="center" nowrap class="titlebg">证件号码</td>
			   <td align="center" nowrap class="titlebg">中奖状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td nowrap>${awdTicketNo}</td>
			  <td align="center" nowrap>${drawId}</td>
			  <td align="center" nowrap>${custName}</td>
			  <td align="center" nowrap>${credNo}</td>
			  <td align="center" nowrap>${awdStatusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/prizeAward/detail.do?awardReg.awdTicketNo=${awdTicketNo}&awardReg.drawId=${drawId}">查看</f:link>
			  		<s:if test="awdStatus==01">
			  		<f:pspan pid="prizeaward_modify"><f:link href="/prizeAward/showModify.do?awardReg.awdTicketNo=${awdTicketNo}&awardReg.drawId=${drawId}">领奖</f:link></f:pspan>
			  		</s:if>
			  		<f:pspan pid="prizeaward_delete">
			  			<a href="javascript:submitUrl('searchForm', '/prizeAward/delete.do?awardReg.awdTicketNo=${awdTicketNo}&awardReg.drawId=${drawId}', '确定要删除吗？');" />删除</a>
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