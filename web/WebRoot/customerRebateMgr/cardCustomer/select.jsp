<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<!-- 查询功能区 -->
<div class="userbox" style="margin: 0px; padding:0px;">
	<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
	<div class="contentb">
		<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
			<caption>购卡客户查询</caption>
			<tr>
				<td align="right">客户编号</td>
				<td><s:textfield id="idSelectCardCustomerId" name="cardCustomer.cardCustomerId" cssStyle="width:120px;"/></td>
				
				<td align="right">客户名称</td>
				<td><s:textfield id="idSelectCardCustomerName" name="cardCustomer.cardCustomerName" cssStyle="width:120px;"/></td>
				
				<td align="right">发卡机构</td>
				<td><s:select onchange="searchCardCustomerOther(1)" list="cardBranchList" id="idCardBranch" name="cardCustomer.cardBranch"  
					listKey="branchCode" listValue="branchName" cssClass="u_half" cssStyle="width:120px;"/></td>
				
				<td height="30">
					<input type="button" value="查询" id="input_btn2" onclick="searchCardCustomerOther(1)"  name="ok" />
					<s:hidden id="idRadio" name="radio" />
				</td>
			</tr>
		</table>
	</div>
	<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
</div>

<!-- 数据列表区 -->
<div class="tablebox" id="idSelectData">
</div>
