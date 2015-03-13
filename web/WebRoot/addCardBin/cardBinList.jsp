<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
			$(function(){
				if('${loginRoleTypeCode}' == '00'){
					Selector.selectBranch('id_branchCodeName', 'id_branchCode', true, '20');
				} else if('${loginRoleTypeCode}' == '01'){
					Selector.selectBranch('id_branchCodeName', 'id_branchCode', true, '20', '', '', '${loginBranchCode}');
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
				<s:form action="cardBinList.do">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption><span class="caption_title"><f:link href="/addCardBin/regList.do">卡BIN申请记录表</f:link></span> | 卡BIN信息列表</caption>
						<tr>
							<td align="right">卡BIN号</td>
							<td>
								<s:textfield name="cardBin.binNo"/>
							</td>
						
							<td align="right">卡BIN名称</td>
							<td>
								<s:textfield name="cardBin.binName"/>
							</td>
							<td align="right">卡种</td>
							<td>
								<s:select name="cardBin.cardType" list="cardTypeList" headerKey="" headerValue="--请选择--" listKey="cardTypeCode" listValue="cardTypeName"></s:select>
							</td>
						</tr>
	
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:if test="centerOrCenterDeptRoleLogined || fenzhiRoleLogined">
									<s:hidden id="id_branchCode" name="cardBin.cardIssuer"/>
									<s:textfield id="id_branchCodeName" name="cardBranchName"/>
								</s:if>
								<s:elseif test="cardOrCardDeptRoleLogined">
									<s:select name="cardBin.cardIssuer" list="myCardBranch"
										 headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName" />
								</s:elseif>
							</td>
							<td align="right">状态</td>
							<td>
								<s:select name="cardBin.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td height="30" colspan="2">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="FormUtils.clearFormFields('searchForm');" name="escape" />
							</td>
						</tr>
					</table>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">卡BIN号码</td>
			   <td align="center" nowrap class="titlebg">卡BIN名称</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">货币代码</td>
			   <td align="center" nowrap class="titlebg">卡类型</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">更新时间</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${binNo}</td>
			  <td nowrap>${binName}</td>
			  <td nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
			  <td align="center" nowrap>${currCode}</td>
			  <td align="center" nowrap>${cardTypeName}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>