<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<!-- 查询功能区 -->
<div class="userbox" style="margin: 0px; padding:0px;">
	<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
	<div class="contentb">
		<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
			<caption>地区查询</caption>
			<tr>
				<td align="right">省份</td>
				<td><s:select onchange="loadCityForSelect();" list="parentList" id="idSelectAreaParent" name="areaSelect" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"
				 	cssStyle="width: 80px;" onkeyup="this.blur();this.focus();"/></td>
				
				<td align="right" >城市</td>
				<td>
					<s:select onchange="searchArea(1);" list="cityList" id="idSelectAreaCityCode" name="cityCode" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"
				 	cssStyle="width: auto;" onkeyup="this.blur();this.focus();"/>
				</td>
				
				<td align="right">地区名称</td>
				<td><s:textfield id="idSelectAreaName" /></td>
				
				
				<td height="30">
					<input type="button" value="查询" id="input_btn2" onclick="searchArea(1)"  name="ok" />
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
