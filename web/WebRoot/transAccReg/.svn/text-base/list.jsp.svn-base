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
							<td align="right">转出卡卡号</td>
							<td><s:textfield name="transAccReg.outCardId" cssClass="{digitOrLetter:true}"/></td>
							<td align="right">转入卡卡号</td>
							<td><s:textfield name="transAccReg.inCardId" cssClass="{digitOrLetter:true}"/></td>
						
							<td align="right">转出子账户类型</td>
							<td><s:select name="transAccReg.subacctType" list="subacctTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select></td>
						</tr>	
						<tr>
							<td align="right">状态</td>
							<td><s:select name="transAccReg.status" list="registerStateList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select></td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="transAccReg_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/transAccReg/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_TRANSACCREG_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">转账编号</td>
			   <td align="center" nowrap class="titlebg">转出卡卡号</td>
			   <td align="center" nowrap class="titlebg">转出卡账户</td>
			   <td align="center" nowrap class="titlebg">转出子账户类型</td>
			   <td align="center" nowrap class="titlebg">转入卡卡号</td>
			   <td align="center" nowrap class="titlebg">转入卡账户</td>
			   <td align="center" nowrap class="titlebg">转账金额</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">发起方</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="left" nowrap>${transAccId}</td>
			  <td align="center" nowrap>${outCardId}</td>
			  <td align="center" nowrap>${outAccId}</td>
			  <td align="center" nowrap>${subacctTypeName}</td>
			  <td align="center" nowrap>${inCardId}</td>
			  <td align="center" nowrap>${inAccId}</td>
			  <td align="right" nowrap>${fn:amount(amt)}</td>
			  <td align="right" nowrap>${cardBranch}</td>
			  <td align="right" nowrap>${initiator}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/transAccReg/detail.do?transAccReg.transAccId=${transAccId}">查看</f:link>
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