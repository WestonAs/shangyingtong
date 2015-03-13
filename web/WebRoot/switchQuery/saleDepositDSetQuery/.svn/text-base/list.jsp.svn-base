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
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

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
							<td align="right">付款方</td>
							<td>
							<s:if test="showCardSell">
							<s:select name="merchTransDSet.payCode" headerKey="" headerValue="--请选择--" list="branchList" listKey="branchCode" listValue="branchName"></s:select>
							</s:if>
							<s:else>
							<s:textfield name="merchTransDSet.payName"></s:textfield>
							</s:else>
							</td>
							<td align="right">发卡机构</td>
							<td>
							<s:if test="showCard">
							<s:select name="merchTransDSet.recvCode" headerKey="" headerValue="--请选择--" list="branchList" listKey="branchCode" listValue="branchName"></s:select>
							</s:if>
							<s:else>
							<s:textfield name="merchTransDSet.recvName"></s:textfield>
							</s:else>
							</td>
							<td align="right">交易类型</td>
							<td>
							<s:select name="merchTransDSet.transType" list="transTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">清算日期</td>
							<td>
								<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
							</td>
							<td height="30" colspan="3">
							<input type="submit" value="查询" id="input_btn2"  name="ok" />
							<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MERCHTRANSDSETQUERY_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">清算日期</td>
			   <td align="center" nowrap class="titlebg">付款方编号</td>
			   <td align="center" nowrap class="titlebg">付款方名称</td>
			   <td align="center" nowrap class="titlebg">发卡机构编号</td>
			   <td align="center" nowrap class="titlebg">发卡机构名称</td>
			   <td align="center" nowrap class="titlebg">交易类型</td>
			   <td align="center" nowrap class="titlebg">交易笔数</td>
			   <td align="center" nowrap class="titlebg">交易金额</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td align="center" nowrap>${setDate}</td>
			  <td align="center" nowrap>${payCode}</td>
			  <td align="center" nowrap>${payName}</td>
			  <td align="center" nowrap>${recvCode}</td>
			  <td align="center" nowrap>${recvName}</td>
			  <td align="center" nowrap>${transTypeName}</td>
			  <td align="right" nowrap>${transNum}</td>
			  <td align="right" nowrap>${fn:amount(feeAmt)}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<a href="javascript:openContextDialog('/switchQuery/saleDepositDSetQuery/detail.do?merchTransDSet.payCode=${payCode}&merchTransDSet.recvCode=${recvCode}&merchTransDSet.setDate=${setDate}&merchTransDSet.transType=${transType}&merchTransDSet.curCode=${curCode}');" />明细</a>
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td align="right" nowrap>当前页合计：</td>
			<td align="right" nowrap>${fn:amount(curPageSum)}</td>
			<td></td>
			</tr>
			<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td align="right" nowrap>总计：</td>
			<td align="right" nowrap>${fn:amount(amountTotal)}</td>
			<td></td>
			</tr>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>