<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<!-- 查询功能区 -->
<div class="userbox" style="margin: 0px; padding:0px;">
	<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
	<div class="contentb">
		<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
			<caption>商户查询</caption>
			<tr>
				<td align="right">商户编号</td>
				<td><s:textfield id="idSelectMerchId" cssClass="u_half"/></td>
				
				<td align="right">商户名称</td>
				<td><s:textfield id="idSelectMerchName" cssClass="u_half"/></td>
				
				<td align="right">币种</td>
				<td><s:select id="id_currCode_Sel" onchange="searchMerch(1)" name="_currCode_Sel" list="currCodeList" listKey="currCode" listValue="currName" cssClass="u_half"></s:select></td>
				
				
				<td height="30">
					<input type="button" value="查询" id="input_btn2" onclick="searchMerch(1)"  name="ok" />
					<s:hidden id="idRadio" name="radio" />
					<s:hidden id="idSelectBranchCode"  name="card_BranchNo" />
					<s:hidden id="idProxyId"  name="proxyId" />
					<s:hidden id="idGroup_CardBranch"  name="group_CardBranch" />
					<s:hidden id="_id_ManageBranch"  name="_manageBranch" />
					<s:hidden id="id_cardBranch"  name="cardBranch" />
					<s:hidden id="_id_cardBranchNotLimit"  name="_cardBranchNotLimit" />
				</td>
			</tr>
		</table>
	</div>
	<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
</div>

<!-- 数据列表区 -->
<div class="tablebox" id="idSelectData">
</div>
