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
				<s:form id="searchForm" action="optList.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">用户编码</td>
							<td><s:textfield name="userSellAmt.userId" cssClass="{digitOrLetter:true}"/></td>
							<td align="right">机构编码</td>
							<td>
								<s:if test="showSell">
									<s:select name="userSellAmt.branchCode" list="sellBranchList" listKey="branchCode" listValue="branchName" cssClass="{digitOrLetter:true}"></s:select>
								</s:if>
								<s:else>
									<s:select name="userSellAmt.branchCode" list="branchList" listKey="branchCode" listValue="branchName" cssClass="{digitOrLetter:true}"></s:select>
								</s:else>
							
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="sellamtmgr_opt"><input style="margin-left:30px;" type="button" value="重置用户配额" onclick="javascript:gotoUrl('/sellAmt/showOpt.do');" id="input_btn3" name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_SELL_AMT_OPT_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">用户编码</td>
			   <td align="center" nowrap class="titlebg">机构</td>
			   <td align="center" nowrap class="titlebg">配额</td>
			   <td align="center" nowrap class="titlebg">已用配额</td>
			   <td align="center" nowrap class="titlebg">当前可用配额</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td align="center" nowrap>${userId}</td>
			  <td nowrap>${branchCode}-${fn:branch(branchCode)}</td>
			  <td align="center" nowrap>${fn:amount(amt)}</td>
			  <td align="center" nowrap>${fn:amount(usedAmt)}</td>
			  <td align="center" nowrap>${fn:amount(availableAmt)}</td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>