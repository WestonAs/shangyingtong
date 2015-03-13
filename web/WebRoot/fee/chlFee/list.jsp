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
				<s:form action="list.do" id="searchForm" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">分支机构名称</td>
							<td>
								<%--<s:if test="showProxy">
									<s:select name="chlFee.branchCode" list="branchList" listKey="branchCode" listValue="branchName"></s:select>
								</s:if>
								<s:else>
									<s:select name="chlFee.branchCode" headerKey="" headerValue="--请选择--" list="branchList" listKey="branchCode" listValue="branchName"></s:select>
								</s:else>--%>
								<s:textfield name="chlFee.branchName" />
							</td>
							<td align="right">计费类型</td>
							<td>
								<s:select name="chlFee.feeType" headerKey="" headerValue="--请选择--" list="feeTypeList" listKey="value" listValue="name"></s:select>
							</td>
							<td align="right">计费内容</td>
							<td>
								<s:select name="chlFee.feeContent" headerKey="" headerValue="--请选择--" list="feeContentList" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="chlfee_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/fee/chlFee/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CHLFEE_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">分支机构</td>
			   <td align="center" nowrap class="titlebg">计费内容</td>
			   <td align="center" nowrap class="titlebg">金额笔数标志</td>
			   <td align="center" nowrap class="titlebg">计费类型</td>
			   <td align="center" nowrap class="titlebg">计费周期</td>
			   <td align="center" nowrap class="titlebg">金额上限</td>
			   <td align="center" nowrap class="titlebg">收费金额/费率</td>
			   <td align="center" nowrap class="titlebg">更新时间</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="left" nowrap>${branchCode}-${fn:branch(branchCode)}</td>
			  <td align="center" nowrap>${feeContentName}</td>
			  <td align="center" nowrap>${amtOrCntName}</td>
			  <td align="center" nowrap>${feeTypeName}</td>
			  <td align="center" nowrap>${costCycleName}</td>
			  <td align="right" nowrap>${fn:amount(ulMoney)}</td>
			  <td align="right" nowrap>
			  <s:if test="feeType==1||feeType==3">${feeRate}</s:if><s:else>${fn:percentPre(feeRate, 4)}</s:else>
			  <td align="center" nowrap="nowrap"><s:date name="updateTime"/></td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<!--<f:link href="/fee/chlFee/showModify.do?chlFee.branchCode=${branchCode}&chlFee.feeContent=${feeContent}&chlFee.ulMoney=${ulMoney}">明细</f:link>-->
			  		<f:pspan pid="chlfee_modify"><f:link href="/fee/chlFee/showModify.do?chlFee.branchCode=${branchCode}&chlFee.feeContent=${feeContent}&chlFee.ulMoney=${ulMoney}">修改</f:link></f:pspan>
			  		<f:pspan pid="chlfee_delete">
			  			<a href="javascript:submitUrl('searchForm','/fee/chlFee/delete.do?branchCode=${branchCode}&ulMoney=${ulMoney}&feeContent=${feeContent}','确定要删除吗?');">删除</a>
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