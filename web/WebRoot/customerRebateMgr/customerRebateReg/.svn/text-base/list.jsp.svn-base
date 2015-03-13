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
							<td align="right">购卡客户ID</td>
							<td><s:textfield name="customerRebateReg.cardCustomerId" cssClass="{digitOrLetter:true}"/></td>
							
							<td align="right">购卡客户名称</td>
							<td><s:textfield name="customerRebateReg.cardCustomerName"/></td>
							
							<td align="right">卡BIN</td>
							<td><s:textfield name="customerRebateReg.binNo" cssClass="{digitOrLetter:true}"/></td>
						</tr>
						<tr>							
							<td align="right">卡BIN名称</td>
							<td><s:textfield name="customerRebateReg.binName"/></td>
							
							<td align="right">售卡返利规则ID</td>
							<td><s:textfield name="customerRebateReg.saleRebateId" cssClass="{digitOrLetter:true}"/></td>
							
							<td align="right">售卡返利规则名</td>
							<td><s:textfield name="customerRebateReg.saleRebateName"/></td>
													
						</tr>
						<tr>
							<td align="right">充值返利规则ID</td>
							<td><s:textfield name="customerRebateReg.depositRebateId" cssClass="{digitOrLetter:true}"/></td>
							
							<td align="right">充值返利规则名</td>
							<td><s:textfield name="customerRebateReg.depositRebateName"/></td>							
							<td height="30" colspan="2">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<f:pspan pid="customerRebateReg_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/customerRebateMgr/customerRebateReg/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CUSTOMERREBATEREG_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">购卡客户ID</td>
			   <td align="center" nowrap class="titlebg">购卡客户名称</td>
			   <td align="center" nowrap class="titlebg">卡BIN</td>
			   <td align="center" nowrap class="titlebg">卡BIN名称</td>
			   <td align="center" nowrap class="titlebg">售卡返利名称</td>
			   <td align="center" nowrap class="titlebg">充值返利名称</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${cardCustomerId}</td>
			  <td align="center" nowrap>${cardCustomerName}</td>
			  <td align="center" nowrap>${binNo}</td>
			  <td align="center" nowrap>${binName}</td>
			  <td align="center" nowrap>${saleRebateName}</td>
			  <td align="center" nowrap>${depositRebateName}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<!--  测试页面直接传 CardCustomerId			  		  		
			  		<f:link href="/customerRebateReg/detail.do?customerRebateId=${customerRebateId}">查看</f:link>
			  		 -->
			  		<f:link href="/customerRebateMgr/customerRebateReg/detail.do?customerRebateReg.customerRebateRegId=${customerRebateRegId}">明细</f:link>
			  		<f:pspan pid="customerRebate_modify"><f:link href="/customerRebateMgr/customerRebateReg/showModify.do?customerRebateReg.cardCustomerId=${cardCustomerId}&customerRebateReg.binNo=${binNo}">编辑</f:link></f:pspan>
			  		<f:pspan pid="customerRebate_delete">
			  			<a href="javascript:submitUrl('searchForm', '/customerRebateMgr/customerRebateReg/delete.do?customerRebateReg.cardCustomerId=${cardCustomerId}&customerRebateReg.binNo=${binNo}', '确定要删除吗？');" />删除</a>
			  		</f:pspan>	
			  		<f:pspan pid="customerRebateReg_add">
			  		<s:if test='"10".equals(status)'>
			  			<a href="javascript:submitUrl('searchForm', '/customerRebateMgr/customerRebateReg/stop.do?customerRebateRegId=${customerRebateRegId}', '确定要注销吗？');" />注销</a>
			  		</s:if>
			  		<s:elseif test='"02".equals(status)'>
			  			<a href="javascript:submitUrl('searchForm', '/customerRebateMgr/customerRebateReg/start.do?customerRebateRegId=${customerRebateRegId}', '确定要生效吗？');" />生效</a>
			  		</s:elseif>
			  		</f:pspan>		  		
			  		<!-- 
			  		<f:link href="/customerRebateReg/delete.do?customerRebateReg.cardCustomerId=${cardCustomerId}&customerRebateReg.binNo=${binNo}">删除</f:link>
			  		 -->
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		
		<div class="userbox" style="padding-top: 10px">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<span class="note_div">注释：</span> 适用于管理系统充值、售卡返利。
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>