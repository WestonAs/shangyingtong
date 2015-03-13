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
		<f:js src="/js/date/WdatePicker.js"/>

		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
			$(function(){
				if('${loginRoleType}' == '00'){
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '20');
				} else if('${loginRoleType}' == '01'){
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '20', '', '', '${loginBranchCode}');
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
				<s:form id="searchForm" action="regList.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption><span class="caption_title"><f:link href="/pages/merch/list.do">商户信息列表</f:link></span> | 商户登记信息列表</caption>
						<tr>
							<td align="right">商户编号</td>
							<td><s:textfield name="merchInfoReg.merchId" cssClass="{digitOrLetter:true}"/></td>
							
							<td align="right">商户名称</td>
							<td><s:textfield name="merchInfoReg.merchName"/></td>
							
							<s:if test="!userOfLimitedTransQuery">
								<td align="right">更新时间</td>
								<td>
									<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
									<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
								</td>
							</s:if>
						</tr>
						<tr>
							<s:if test="!userOfLimitedTransQuery">
								<td align="right">状态</td>
								<td>
									<s:select name="merchInfoReg.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
								</td>
								<s:if test="showCardBranch">
									<td align="right">发卡机构</td>
									<td><s:hidden id="idBranchCode" name="cardBranchCode"/><s:textfield id="idBranchName" name="cardBranchName" /></td>
								</s:if>
								
								<td align="right">是否单机产品</td>
								<td>
									<s:select name="merchInfoReg.singleProduct" list="yesOrNoFlagList" headerKey="" headerValue="---全部---" listKey="value" listValue="name"></s:select>
								</td>
							</s:if>
						</tr>
						<tr>
							<td align="right">&nbsp;</td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm');$('#idBranchCode').val('');$('#idBranchName').val('');" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MERCH_Reg_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">ID</td>
			   <td align="center" nowrap class="titlebg">商户编号</td>
			   <td align="center" nowrap class="titlebg">商户名称</td>
			   <td align="center" nowrap class="titlebg">是否单机产品</td>
			   <td align="center" nowrap class="titlebg">联系人</td>
			   <td align="center" nowrap class="titlebg">联系电话</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${id}</td>
			  <td align="center" nowrap>${merchId}</td>
			  <td align="left" nowrap>${merchName}</td>
			  <td align="center" nowrap>${singleProductName}</td>
			  <td align="center" nowrap>${linkMan}</td>
			  <td align="center" nowrap>${telNo}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/merch/regDetail.do?merchInfoReg.id=${id}">查看</f:link>
			  	</span>
			  	<s:if test="status==20">
			  		<span class="redlink">
				  		<f:pspan pid="merchantmgr_modify"><f:link href="/pages/merch/showModifyReg.do?merchInfoReg.id=${id}">编辑</f:link></f:pspan>
			  		</span>
			  	</s:if>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>