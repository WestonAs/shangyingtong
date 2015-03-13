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
		
		<script type="text/javascript">		
		
		</script>
		
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
							<td align="right">卡号</td>
							<td><s:textfield name="saleSignCardReg.cardId" cssClass="{digitOrLetter:true}"/></td>							
							<td align="right">签单客户ID</td>
							<td><s:textfield name="saleSignCardReg.signCustId" cssClass="{digitOrLetter:true}"/></td>
						</tr>						
						<tr>
							<td align="right">签单客户名称</td>
							<td><s:textfield name="saleSignCardReg.signCustName" /></td>
							<td align="right">签单规则ID</td>
							<td>
								<s:textfield name="saleSignCardReg.signRuleId" cssClass="{digitOrLetter:true}"/>
							</td>
						</tr>						
						<tr>
							<td align="right">签单规则名称</td>
							<td><s:textfield name="saleSignCardReg.signRuleName" /></td>
							<td align="right">持卡人</td>
							<td><s:textfield name="saleSignCardReg.custName" /></td>														
						</tr>						
						<tr>
							<td align="right">状态</td>
							<td>
								<s:select name="saleSignCardReg.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="saleSignCardReg_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/signCardMgr/saleSignCardReg/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
							<td><s:hidden name="actionSubName" ></s:hidden></td>
						</tr>
					</table>
					<s:token name="_TOKEN_SALE_SIGN_CARD_REG_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <td align="center" nowrap class="titlebg">签单客户</td>
			   <td align="center" nowrap class="titlebg">签单规则</td>
			   <td align="center" nowrap class="titlebg">透支金额</td>
			   <td align="center" nowrap class="titlebg">持卡人</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${cardId}</td>
			  <td align="center" nowrap>${signCustName}</td>
			  <td align="center" nowrap>${signRuleName}</td>
			  <td align="right" nowrap>${fn:amount(overdraft)}</td>
			  <td align="center" nowrap>${custName}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/signCardMgr/saleSignCardReg/detail.do?saleSignCardReg.saleSignCardId=${saleSignCardId}">明细</f:link>			  		
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