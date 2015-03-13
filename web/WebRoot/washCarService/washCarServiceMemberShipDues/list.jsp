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
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
		     $(function(){
		       Selector.selectBranch('idBranchName', 'idBranchNo', true,'20');
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
							<td align="right">卡号</td>
							<td>
							   <s:textfield name="washCarSvcMbShipDues.cardId" />
							</td>
							<td align="right">发卡机构</td>
							<td>
							   <s:hidden id="idBranchNo" name="washCarSvcMbShipDues.cardIssuer" />
							   <s:textfield id="idBranchName" name="branchName" />
							</td>
							<td align="right">缴费状态</td>
							<td>
							    <s:select name="washCarSvcMbShipDues.status" list="washCarSvcMemberShipList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
						    <td align="right">审核状态</td>
							<td>
							    <s:select name="washCarSvcMbShipDues.checkStatus" list="washCarSvcCheckList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td height="30" colspan="4">
							<input type="submit" value="查询" id="input_btn2"  name="ok" />
							<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm');
							$('#idBranchName').val('');$('#idBranchNo').val('');" name="escape" />
							<f:pspan pid="washCarSvcMbShipDues_adds"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/washCarService/washCarServiceMemberShipDues/showAdd.do');" id="input_btn2"  name="escape" /></f:pspan>
							<f:pspan pid="washCarSvcMbShipDues_import">
							  <input style="margin-left:30px;" type="button" value="缴交会费导入" onclick="javascript:gotoUrl('/washCarService/washCarServiceMemberShipDues/showAddFile.do');" id="input_btn3"  name="escape" />
							</f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_PAYMENTOFDUES_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap="nowrap" class="titlebg">卡号</td>
			   <td align="center" nowrap="nowrap" class="titlebg">外部卡号</td>
			   <td align="center" nowrap="nowrap" class="titlebg">发卡机构</td>
			   <td align="center" nowrap="nowrap" class="titlebg">状态</td>
			   <td align="center" nowrap="nowrap" class="titlebg">缴费金额</td>
			   <td align="center" nowrap="nowrap" class="titlebg">有效开始时间</td>
			   <td align="center" nowrap="nowrap" class="titlebg">有效结束时间</td>
			   <td align="center" nowrap="nowrap" class="titlebg">pos缴费时间</td>
			   <td align="center" nowrap="nowrap" class="titlebg">审核</td>
			   <td align="center" nowrap="nowrap" class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td align="center" nowrap="nowrap">${cardId}</td>
			  <td align="center" nowrap="nowrap">${externalCardId}</td>
			  <td align="center" nowrap="nowrap">${cardIssuer}-${fn:branch(cardIssuer)}</td>
			  <td align="center" nowrap="nowrap">${statusName}</td>
			  <td align="center" nowrap="nowrap">${tollAmt}</td>
			  <td align="center" nowrap="nowrap">${tollStartDate}</td>
			  <td align="center" nowrap="nowrap">${tollEndDate}</td>
			  <td align="center" nowrap="nowrap">${posTollDate}</td>
			  <td align="center" nowrap="nowrap">${checkStatusName}</td>
			  <td align="center" nowrap="nowrap">
			  	<span class="redlink">
			  		<f:link href="/washCarService/washCarServiceMemberShipDues/detail.do?washCarSvcMbShipDues.id=${id}">查看</f:link>
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