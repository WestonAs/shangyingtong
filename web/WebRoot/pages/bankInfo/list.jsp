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
							<td align="right">银行代码</td>
							<td><s:textfield name="bankInfo.bankNo" cssClass="{digitOrLetter:true}"/></td>
							
							<td align="right">银行名称</td>
							<td><s:textfield name="bankInfo.bankName"/></td>
							
							<td align="right">状态</td>
							<td>
								<s:select name="bankInfo.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">省份</td>
							<td><s:select name="bankInfo.parent" id="idSelectProvince" onchange="loadCityForSelectBank();" list="parentList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select></td>
							<td align="right">城市</td>
							<td><s:select name="bankInfo.cityCode" id="idSelectCityCode" list="cityList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select></td>
							<td align="right">银行行别</td>
							<td><s:select name="bankInfo.bankType" list="bankTypeList" headerKey="" headerValue="--请选择--" listKey="bankType" listValue="bankTypeName"></s:select></td>

						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="bankmgr_add">
									<input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/pages/bankInfo/showAdd.do');" id="input_btn3"  name="escape" />
								</f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_BANK_INFO_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">银行行别</td>
			   <td align="center" nowrap class="titlebg">银行行号</td>
			   <td align="center" nowrap class="titlebg">银行名称</td>
			   <td align="center" nowrap class="titlebg">地区码</td>
			   <td align="center" nowrap class="titlebg">地区名</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${bankTypeName}</td>
			  <td align="center" nowrap>${bankNo}</td>
			  <td nowrap>${bankName}</td>
			  <td align="center" nowrap>${addrNo}</td>
			  <td align="center" nowrap>${areaName}</td>
			  <td align="center" nowrap <s:if test=" '10'.equals(status)">class="redfont"</s:if>>${statusName}</td>
			  <td align="center" nowrap>	
				<span class="redlink">
			  		<f:pspan pid="bankmgr_modify">
			  			<f:link href="/pages/bankInfo/showModify.do?bankInfo.bankNo=${bankNo}">编辑</f:link>
			  		</f:pspan>
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