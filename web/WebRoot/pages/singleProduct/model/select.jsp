<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<!-- 查询功能区 -->
<div class="userbox" style="margin: 0px; padding:0px;">
	<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
	<div class="contentb">
		<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
			<caption>单机产品套餐查询</caption>
			<tr>
				<td align="right">套餐编号</td>
				<td><s:textfield id="idSelectPlanId"/></td>
				
				<td align="right">套餐名称</td>
				<td><s:textfield id="idSelectPlanName"/></td>
				
				<td height="30">
					<input type="button" value="查询" id="input_btn2" onclick="searchModel(1)"  name="ok" />
					<s:hidden id="idRadio" name="radio" />
					<s:hidden id="idHiddenModelBranchCode" name="hiddenBranchCode" />
					<s:hidden id="idHiddenIsBunding" name="isBunding" />
				</td>
			</tr>
		</table>
	</div>
	<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
</div>

<!-- 数据列表区 -->
<div class="tablebox" id="idSelectData">
</div>
