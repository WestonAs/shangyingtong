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

		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="list.do" id="searchForm" cssClass="validate-tip">
					<table id="form_grid" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">发卡机构</td>
							<td>
								<s:textfield name="cardissuerPlanFee.issId"/>
							</td>
							<td align="right">套餐编号</td>
							<td>
								<s:textfield name="cardissuerPlanFee.planId"/>
							</td>
							<td align="right">状态</td>
							<td>
								<s:select name="cardissuerPlanFee.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">更新时间</td>
							<td>
								<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
							</td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARD_PLANFEE_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">发卡机构编号</td>
			   <td align="center" nowrap class="titlebg">发卡机构名称</td>
			   <td align="center" nowrap class="titlebg">套餐编号</td>
			   <td align="center" nowrap class="titlebg">收费标准</td>
			   <td align="center" nowrap class="titlebg">标准制卡单价</td>
			   <td align="center" nowrap class="titlebg">定制制卡单价</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data">
			<tr>
			  <td nowrap>${issId}</td>
			  <td nowrap>${fn:branch(issId)}</td>
			  <td align="center" nowrap>${planId}</td>
			  <td align="center" nowrap>${chargeTypeName}</td>
			  <td align="right" nowrap>${fn:amount(defauleAmt)}</td>
			  <td align="right" nowrap>${fn:amount(customAmt)}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/singleProduct/planfee/detail.do?cardissuerPlanFee.issId=${issId}">明细</f:link>
			  		<f:pspan pid="single_product_planfee_modify">
			  			<f:link href="/pages/singleProduct/planfee/showModify.do?cardissuerPlanFee.issId=${issId}">编辑</f:link>
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