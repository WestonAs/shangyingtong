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
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<script type="text/javascript">		
			$(function(){
				if('${loginRoleType}' == '00'){
					Selector.selectBranch('id_branchCodeName', 'id_branchCode', true, '20');
				} else if('${loginRoleType}' == '01'){
					Selector.selectBranch('id_branchCodeName', 'id_branchCode', true, '20', '', '', '${loginBranchCode}');
				}				
			});
			function clearBranch(){
				$('#id_branchCode').val('');
				$('#id_branchCodeName').val('');
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="list.do" id="searchForm" cssClass="validate-tip">
					<table id="form_grid" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">终端编号</td>
							<td>
								<s:textfield name="cardissuerFreeNum.termId" maxlength="8"/>
							</td>
							<td align="right">发卡机构</td>
							<td>
								<s:if test="showBranchList">
									<s:select name="cardissuerFreeNum.issId" list="cardBranchList" headerKey="" headerValue="--请选择--" 
										listKey="branchCode" listValue="branchName" onmouseover="FixWidth(this)" />
								</s:if>
								<s:else>
									<s:hidden id="id_branchCode" name="cardissuerFreeNum.issId"/>
									<s:textfield id="id_branchCodeName" name="cardBranchName"/>
								</s:else>
							</td>
							<td align="right">状态</td>
							<td>
								<s:select name="cardissuerFreeNum.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select>
							</td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="FormUtils.reset('searchForm');clearBranch();" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CardFreeNum_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">终端编号</td>
			   <td align="center" nowrap class="titlebg">周期</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">每台终端赠送卡数量</td>
			   <td align="center" nowrap class="titlebg">已用免费制卡数量</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data">
			<tr>
			  <td nowrap>${termId}</td>
			  <td align="center" nowrap>${period}</td>
			  <td nowrap>${issId}-${fn:branch(issId)}</td>
			  <td align="right" nowrap>${sendNum}</td>
			  <td align="right" nowrap>${usedNum}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/singleProduct/freeNum/detail.do?cardissuerFreeNum.termId=${termId}&cardissuerFreeNum.period=${period}">明细</f:link>
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