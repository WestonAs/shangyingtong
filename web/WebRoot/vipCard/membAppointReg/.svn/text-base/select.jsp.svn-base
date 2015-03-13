<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<!-- 查询功能区 -->
<div class="userbox" style="margin: 0px; padding:0px;">
	<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
	<div class="contentb">
		<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
			<caption>会员查询</caption>
			<tr>
				<td align="right">会员ID</td>
				<td><s:textfield id="id_membInfoRegId" cssClass="u_half"/></td>
				
				<td align="right">会员名称</td>
				<td><s:textfield id="id_custName" cssClass="u_half"/></td>
				
				<td align="right">会员登记批次</td>
				<td><s:select id="id_membId" onchange="searchMemb(1)"  headerKey=""  headerValue="--请选择--"  list="membInfoIdList" listKey="membInfoId" listValue="membInfoId" cssClass="u_half"></s:select></td>
				
				<td height="30">
					<input type="button" value="查询" id="input_btn2" onclick="searchMemb(1)"  name="ok" />
					<s:hidden id="idRadio" name="radio" />
					<s:hidden id="id_hiddenCardIssuer" name="cardIssuer" />
				</td>
			</tr>
		</table>
	</div>
	<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
</div>

<!-- 数据列表区 -->
<div class="tablebox" id="idSelectData">
</div>
