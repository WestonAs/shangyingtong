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
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '20');
				} else if('${loginRoleType}' == '01'){
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '20', '', '', '${loginBranchCode}');
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
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">购卡客户ID</td>
							<td><s:textfield name="cardCustomer.cardCustomerId" cssClass="{digitOrLetter:true}"/></td>
							
							<td align="right">购卡客户名称</td>
							<td><s:textfield name="cardCustomer.cardCustomerName"/></td>
							
							<td align="right">返利方式</td>
							<td>
								<s:select name="cardCustomer.rebateType" list="rebateTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
	
						<tr>
							<td align="right">状态</td>
							<td>
								<s:select name="cardCustomer.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td align="right">发卡机构</td>
							<td>
							<s:if test="cardBranchList.size() > 0">
								<s:select id="id_CardBranch" name="cardCustomer.cardBranch" list="cardBranchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName"></s:select>
							</s:if>
							<s:else>
								<s:hidden id="idBranchCode" name="cardCustomer.cardBranch"/><s:textfield id="idBranchName" name="cardBranchName" />
							</s:else>
							</td>
							<td align="right">是否通用</td>
							<td>
								<s:select name="cardCustomer.isCommon" list="isCommonList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">风险等级</td>
							<td>
								<s:select name="cardCustomer.riskLevel" list="@gnete.card.entity.type.RiskLevelType@getAll()"  
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name" />
							</td>
							<td></td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm');$('#idBranchCode').val('');$('#idBranchName').val('');" name="escape" />
								<f:pspan pid="cardCustomer_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/customerRebateMgr/cardCustomer/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARDCUSTOMER_LIST"/>
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
			   <td align="center" nowrap class="titlebg">购卡客户名</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">录入机构</td>
			   <td align="center" nowrap class="titlebg">是否通用</td>
			   <td align="center" nowrap class="titlebg">返利方式</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${cardCustomerId}</td>
			  <td align="center" nowrap>${cardCustomerName}</td>
			  <td nowrap>${cardBranch}-${fn:branch(cardBranch)}</td>
			  <td nowrap>${inputBranch}-${fn:branch(inputBranch)}</td>
			  <td align="center" nowrap>${isCommonName}</td>
			  <td align="center" nowrap>${rebateTypeName}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/customerRebateMgr/cardCustomer/detail.do?cardCustomer.cardCustomerId=${cardCustomerId}">明细</f:link>
			  		<s:if test='"N".equals(isCommon) && !usedLabel.equals(status)' >
				  		<f:pspan pid="cardCustomer_modify">
				  			<f:link href="/customerRebateMgr/cardCustomer/showModify.do?cardCustomer.cardCustomerId=${cardCustomerId}">编辑</f:link>
				  		</f:pspan>
				  		<f:pspan pid="cardCustomer_delete">
				  			<a href="javascript:submitUrl('searchForm', '/customerRebateMgr/cardCustomer/delete.do?cardCustomerId=${cardCustomerId}', '确定要删除吗？');" />删除</a>
				  		</f:pspan>
			  		</s:if>
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