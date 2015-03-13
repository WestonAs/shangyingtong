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
		<f:js src="/js/datePicker/WdatePicker.js"/>

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
							<td align="right">申请登记编号</td>
							<td><s:textfield name="riskMarginReg.earnestRegId" cssClass="{digitOrLetter:true}"/></td>
							<td align="right">调整方向</td>
							<td>
								<s:select name="riskMarginReg.adjDirection" list="adjDirectionList" headerKey="" 
								headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td height="30" >
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="earnestreg_add"><input style="margin-left:30px;" type="button" value="申请准备金" onclick="javascript:gotoUrl('/earnestMoney/earnestMoneyReg/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token />
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">申请登记编号</td>
			   <td align="center" nowrap class="titlebg">机构编码</td>
			   <td align="center" nowrap class="titlebg">保证金余额</td>			   
			   <td align="center" nowrap class="titlebg">调整方向</td>
			   <td align="center" nowrap class="titlebg">调整金额</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td align="center" nowrap>${earnestRegId}</td>
			  <td align="center" nowrap>${branchCode}</td>
			  <td align="center" nowrap>${remainRiskAmt}</td>
			  <td align="center" nowrap>${adjDirectionName}</td>
			  <td align="center" nowrap>${adjAmt}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/earnestMoney/earnestMoneyReg/detail.do?riskMarginReg.earnestRegId=${earnestRegId}">查看</f:link>
			  		<s:if test="status==00">
			  		<f:pspan pid="earnestreg_modify"><f:link href="/earnestMoney/earnestMoneyReg/showModify.do?riskMarginReg.earnestRegId=${earnestRegId}">编辑</f:link></f:pspan>
					</s:if>
					<f:pspan pid="earnestreg_delete">
			  			<a href="javascript:submitUrl('searchForm', '/earnestMoney/earnestMoneyReg/delete.do?earnestRegId=${earnestRegId}', '确定要删除吗？');" />删除</a>
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