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
    
	<body  >
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">返利规则ID</td>
							<td><s:textfield name="rebateRule.rebateId" cssClass="{digitOrLetter:true}"/></td>
							
							<td align="right">返利规则名称</td>
							<td><s:textfield name="rebateRule.rebateName"/></td>
							
							<td align="right">计算方式</td>
							<td>
								<s:select name="rebateRule.calType" list="calTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
	
						<tr>
							<td align="right">发卡机构</td>
							<td>
							<s:if test="cardBranchList.size() > 0">
								<s:select id="id_CardBranch" name="rebateRule.cardBranch" list="cardBranchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName"></s:select>
							</s:if>
							<s:else>
								<s:hidden id="idBranchCode" name="rebateRule.cardBranch"/><s:textfield id="idBranchName" name="cardBranchName" />
							</s:else>
							</td>
							<td align="right">返利类型</td>
							<td>
								<s:select name="rebateRule.isCommon" list="isCommonList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td align="right">状态</td>
							<td>
								<s:select name="rebateRule.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm');$('#idBranchCode').val('');$('#idBranchName').val('');" name="escape" />
								<f:pspan pid="rebateRule_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/customerRebateMgr/rebateRule/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_REBATERULE_LIST"/>
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
			   <td align="center" nowrap class="titlebg">录入机构</td>
			   <td align="center" nowrap class="titlebg">返利规则名称</td>
			   <td align="center" nowrap class="titlebg">返利类型</td>
			   <td align="center" nowrap class="titlebg">计算方式</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${rebateId}</td>
			  <td nowrap>${cardBranch}-${fn:branch(cardBranch)}</td>
			  <td nowrap>${inputBranch}-${fn:branch(inputBranch)}</td>
			  <td align="center" nowrap>${rebateName}</td>
			  <td align="center" nowrap>${isCommonName}</td>
			  <td align="center" nowrap>${calTypeName}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/customerRebateMgr/rebateRule/detail.do?rebateRule.rebateId=${rebateId}">明细</f:link>
			  		<s:if test='normalStatus && (notCommonRebateType || posRebateType)'>
				  		<f:pspan pid="rebateRule_modify">
				  			<f:link href="/customerRebateMgr/rebateRule/showModify.do?rebateRule.rebateId=${rebateId}">编辑</f:link>
				  		</f:pspan>
				  		<f:pspan pid="rebateRule_delete">
				  			<a href="javascript:submitUrl('searchForm', '/customerRebateMgr/rebateRule/delete.do?rebateId=${rebateId}', '确定要删除吗？');" >删除</a>
				  		</f:pspan>
			  		</s:if>
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		
		<div class="userbox" style="padding-top: 10px">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<span class="note_div">注释：</span>
				<ul class="showli_div">
					<li class="showli_div">“POS返利”类型的返利规则，直接作用于POS充值返利。</li>
					<li class="showli_div">“非通用”类型的返利规则，需要通过客户返利申请，作用于管理系统售卡、充值返利。</li>
					<li class="showli_div">“通用”类型的返利规则，可以直接作用于管理系统售卡、充值返利。</li>
				</ul> 
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>