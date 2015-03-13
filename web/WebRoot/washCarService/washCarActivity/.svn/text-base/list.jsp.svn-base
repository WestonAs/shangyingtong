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
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		    $(function(){
		       if('${loginRoleType}' == '00'){
				  Selector.selectMerch('idMerchId_sel', 'idMerchId',true);
				  Selector.selectBranch('idBranch_sel', 'idBranchId', true,'20');
			   }else if('${loginRoleType}' == '01'){
			      Selector.selectMerch('idMerchId_sel', 'idMerchId', true, '','','${loginBranchCode}');
				  Selector.selectBranch('idBranch_sel', 'idBranchId', true, '20','','${loginBranchCode}');
			   }else if('${loginRoleType}' == '20'){
			      Selector.selectMerch('idMerchId_sel', 'idMerchId', true, '','${loginBranchCode}');
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
						<s:if test="cardRoleLogined">
						   <tr>
							    <td align="right">活动ID</td>
								<td>
								   <s:textfield name="washCarActivity.activityId"/>
							    </td>
							    <td align="right">商户</td>
								<td>
								    <s:hidden id="idMerchId" name="washCarActivity.merchId" />
								    <s:textfield id="idMerchId_sel" name="" />
								</td>
								<td align="right">洗车周期</td>
								<td>
								    <s:select name="washCarActivity.washCarCycle" list="washCarCycleList" headerKey="" headerValue="--请选择--" 
								    listKey="value" listValue="name"></s:select>
								</td>
							</tr>
						</s:if>
						<s:elseif test="merchantRoleLogined">
						    <tr>
							    <td align="right">活动ID</td>
								<td>
								   <s:textfield name="washCarActivity.activityId"/>
							    </td>
								<td align="right">洗车周期</td>
								<td>
								    <s:select name="washCarActivity.washCarCycle" list="washCarCycleList" headerKey="" headerValue="--请选择--" 
								    listKey="value" listValue="name"></s:select>
								</td>
							</tr>
						</s:elseif>
						<s:else>
							<tr>
							    <td align="right">活动ID</td>
								<td>
								   <s:textfield name="washCarActivity.activityId"/>
							    </td>
							    <td align="right">发卡机构</td>
								<td>
								    <s:hidden id="idBranchId" name="washCarActivity.cardIssuer" />
								    <s:textfield id="idBranch_sel" name="cardIssuerName"/>
								</td>
							    <td align="right">商户</td>
								<td>
								    <s:hidden id="idMerchId" name="washCarActivity.merchId" />
								    <s:textfield id="idMerchId_sel" name="merchName" />
								</td>
							</tr>
							<tr>
								<td align="right">洗车周期</td>
								<td>
								    <s:select name="washCarActivity.washCarCycle" list="washCarCycleList" headerKey="" headerValue="--请选择--" 
								    listKey="value" listValue="name"></s:select>
								</td>
							</tr>
						</s:else>
						<tr>
							<td></td>
							<td height="30" colspan="4">
							<input type="submit" value="查询" id="input_btn2"  name="ok" />
							<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm');
							$('#idMerchId').val('');$('#idBranchId').val('');$('#idBranch_sel').val('');$('#idMerchId_sel').val('')" name="escape" />
							<f:pspan pid="washCarActivity_add">
							  <input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/washCarService/washCarActivity/showAdd.do');" id="input_btn3"  name="escape" />
							</f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_WASHCARACTIVITY_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap="nowrap" class="titlebg">活动ID</td>
			   <td align="center" nowrap="nowrap" class="titlebg">活动名称</td>
			   <s:if test="centerRoleLogined||fenzhiRoleLogined">
			   <td align="center" nowrap="nowrap" class="titlebg">发卡机构</td>
			   </s:if>
			   <s:if test="merchantRoleLogined">
			   </s:if>
			   <s:else>
			   <td align="center" nowrap="nowrap" class="titlebg">商户</td>
			   </s:else>
			   <td align="center" nowrap="nowrap" class="titlebg">洗车周期</td>
			   <td align="center" nowrap="nowrap" class="titlebg">总次数</td>
			   <td align="center" nowrap="nowrap" class="titlebg">每月限次</td>
			   <td align="center" nowrap="nowrap" class="titlebg">当月不洗是否作废</td>
			   <td align="center" nowrap="nowrap" class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td align="center" nowrap="nowrap">${activityId}</td>
			  <td align="center" nowrap="nowrap">${activityName}</td>
			  <s:if test="centerRoleLogined||fenzhiRoleLogined">
			  <td align="center" nowrap="nowrap">${cardIssuer}-${fn:branch(cardIssuer)}</td>
			  </s:if>
			  <s:if test="merchantRoleLogined">
			  </s:if>
			  <s:else>
			  <td align="center" nowrap="nowrap">${merchId}-${fn:merch(merchId)}</td>
			  </s:else>
			  <td align="center" nowrap="nowrap">${washCarCycleName}</td>
			  <td align="center" nowrap="nowrap">${totalNum}</td>
			  <td align="center" nowrap="nowrap">${limitMonthNum}</td>
			  <td align="center" nowrap="nowrap">${washTherInvalName}</td>
			  <td align="center" nowrap="nowrap">
			  	<span class="redlink">
			  		<f:link href="/washCarService/washCarActivity/detail.do?washCarActivity.activityId=${activityId}">查看</f:link>
			  		<f:link href="/washCarService/washCarActivity/showModify.do?washCarActivity.activityId=${activityId}">编辑</f:link>
			  		<f:link href="/washCarService/washCarActivity/lastDetail.do?washCarActivityRecord.activityId=${activityId}">可用明细</f:link>
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