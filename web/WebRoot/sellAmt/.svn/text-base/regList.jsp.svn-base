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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#idSellType').change(function(){
				var value = $(this).val();
				if (value == '0') {
					$('td[id^="idDept_"]').show();
					$('td[id^="idSell_"]').hide();
				} else if (value == '1'){
					$('td[id^="idDept_"]').hide();
					$('td[id^="idSell_"]').show();
				}
			});
		})
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="regList.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">发卡机构代码</td>
							<s:if test="showAll">
							<td><s:select name="branchSellReg.cardBranch" list="branchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName"></s:select></td>
							</s:if>
							<s:else>
							<td><s:select name="branchSellReg.cardBranch" list="branchList" listKey="branchCode" listValue="branchName"></s:select></td>
							</s:else>
							
							<s:if test="showType">
							<td align="right">售卡机构类型</td>
							<td>
								<s:select id="idSellType" name="branchSellReg.sellType" list="sellTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							</s:if>
							
							<s:if test="showSell">
							<td align="right" id="idSell_1" >售卡机构代码</td>
							<s:if test="showAllDept">
							<td id="idSell_1" ><s:select name="branchSellReg.sellBranch" list="sellBranchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName"></s:select></td>
							</s:if>
							<s:else>
							<td id="idSell_1" ><s:select name="branchSellReg.sellBranch" list="sellBranchList" listKey="branchCode" listValue="branchName"></s:select></td>
							</s:else>
							</s:if>
							
							<s:if test="showDept">
							<td align="right" id="idDept_1" style="display:none;">售卡网点</td>
							<s:if test="showAllDept">
							<td id="idDept_2" style="display:none;"><s:select name="deptId" list="deptList" headerKey="" headerValue="--请选择--" listKey="deptId" listValue="deptName"></s:select></td>
							</s:if>
							<s:else>
							<td id="idDept_2" style="display:none;"><s:select name="deptId" list="deptList" listKey="deptId" listValue="deptName"></s:select></td>
							</s:else>
							</s:if>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="sellamtmgr_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/sellAmt/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_SELL_AMT_REG"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">售卡机构</td>
			   <td align="center" nowrap class="titlebg">售卡机构类型</td>
			   <td align="center" nowrap class="titlebg">调整类型</td>
			   <td align="center" nowrap class="titlebg">相关金额</td>
			   <td align="center" nowrap class="titlebg">原金额</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td nowrap>${cardBranch}-${fn:branch(cardBranch)}</td>
			  <td nowrap>${sellBranch}-${fn:branch(sellBranch)}${fn:dept(sellBranch)}</td>
			  <td align="center" nowrap>${sellTypeName}</td>
			  <td align="center" nowrap>${adjTypeName}</td>
			  <td align="center" nowrap>${fn:amount(amt)}</td>
			  <td align="center" nowrap>${fn:amount(orgAmt)}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/sellAmt/regDetail.do?branchSellReg.id=${id}">明细</f:link>
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