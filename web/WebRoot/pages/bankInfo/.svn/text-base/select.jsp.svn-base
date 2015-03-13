<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<!-- 查询功能区 -->
<div class="userbox" style="margin: 0px; padding:0px;">
	<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
	<div class="contentb">
		<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
			<caption>银行行号查询</caption>
			<tr>
				<td align="right">行别</td>
				<td><s:select onchange="searchBank(1)" list="bankTypeList" id="idSelectBankType" headerKey="" headerValue="--请选择--" listKey="bankType" listValue="bankTypeName" cssClass="u_half"/></td>

				<td align="right">省份</td>
				<td><s:select onchange="loadCityForSelectBank();" list="parentList" id="idSelectProvince" name="areaSelect" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
					cssStyle="width: 80px;"/></td>
				
				<td align="right">城市</td>
				<td><s:select onchange="searchBank(1)" list="cityList" id="idSelectCityCode" name="cityCode" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
					cssStyle="width: 80px;"/></td>
				
				<td align="right">银行名称</td>
				<td><s:textfield id="idSelectBankName" cssClass="u_half"/></td>
				
				
				<td height="30">
					<input type="button" value="查询" id="input_btn2" onclick="searchBank(1)"  name="ok" />
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
