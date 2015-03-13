<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
<%@ include file="/common/taglibs.jsp" %>
<head>
<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>${ACT.name}</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	bgcolor="#ffffff">
	<tr>
		<td align="center" valign="top">
		<table width="657" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="2" height="40" align="left">当前位置：余额查询</td>
			</tr>
			<tr>
				<td colspan="2">
				<table width="657" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="background-image: url('/yintong/site/images/mc_14.gif');" height="8"></td>
					</tr>
					<tr>
						<td style="background-image: url('/yintong/site/images/mc_17.gif');" align="center">
						<table width="96%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="3" class="border2">
								<!-- 子账户数据列表区 -->
								<div class="tablebox">
									<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
									<caption>相关子账户详细信息</caption>	
									<thead>
									<tr>
									   <td align="center" nowrap class="titlebg">子账户类型</td>
									   <td align="center" nowrap class="titlebg">可用余额</td>
									   <td align="center" nowrap class="titlebg">冻结金额</td>
									   <td align="center" nowrap class="titlebg">生效日期</td>
									   <td align="center" nowrap class="titlebg">失效日期</td>
									</tr>
									</thead>
								
									<s:iterator value="page.data"> 
									<tr>		
									  <td align="center" nowrap>${subacctTypeName}</td>
									  <td align="right" nowrap>${fn:amount(avlbBal)}</td>
									  <td align="right" nowrap>${fn:amount(fznAmt)}</td>
									  <td align="center" nowrap>${effDate}</td>
									  <td align="center" nowrap>${expirDate}</td>			  
									</tr>
									</s:iterator>
									</table>
									<f:paginate name="page"/>
								</div>
								
								<!-- 积分账户数据列表区 -->
								<div class="tablebox">
									<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
									<caption>相关积分账户详细信息</caption>	
									<thead>
									<tr>
									   <td align="center" nowrap class="titlebg">积分类型</td>
									   <td align="center" nowrap class="titlebg">积分名称</td>
									   <td align="center" nowrap class="titlebg">联名机构编号</td>
									   <td align="center" nowrap class="titlebg">联名机构名称</td>
									   <td align="center" nowrap class="titlebg">新增积分</td>
									   <td align="center" nowrap class="titlebg">可用积分</td>
									</tr>
									</thead>
								
									<s:iterator value="pointPage.data"> 
									<tr>		
									  <td align="center" nowrap>${ptClass}</td>
									  <td align="center" nowrap>${ptClassName}</td>
									  <td align="center" nowrap>${jinstId}</td>
									  <td align="center" nowrap>${fn:branch(jinstId)}${fn:merch(jinstId)}</td>
									  <td align="right" nowrap>${fn:amount(ptInc)}</td>
									  <td align="right" nowrap>${fn:amount(ptAvlb)}</td>			  
									</tr>
									</s:iterator>
									</table>
									<f:paginate name="pointPage"/>
								</div>
								
								<!-- 赠券账户数据列表区 -->
								<div class="tablebox">
									<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
									<caption>相关赠券账户详细信息</caption>	
									<thead>
									<tr>
									   <td align="center" nowrap class="titlebg">赠券类型</td>
									   <td align="center" nowrap class="titlebg">赠券名称</td>
									   <td align="center" nowrap class="titlebg">联名机构编号</td>
									   <td align="center" nowrap class="titlebg">联名机构名称</td>
									   <td align="center" nowrap class="titlebg">生效日期</td>
									   <td align="center" nowrap class="titlebg">失效日期</td>
									   <td align="center" nowrap class="titlebg">余额</td>
									</tr>
									</thead>
								
									<s:iterator value="CouponPage.data"> 
									<tr>		
									  <td align="center" nowrap>${couponClass}</td>
									  <td align="center" nowrap>${className}</td>
									  <td align="center" nowrap>${jinstId}</td>
									  <td align="center" nowrap>${fn:branch(jinstId)}${fn:merch(jinstId)}</td>
									  <td align="center" nowrap>${effDate}</td>
									  <td align="center" nowrap>${expirDate}</td>			  
									  <td align="right" nowrap>${fn:amount(balance)}</td>			  
									</tr>
									</s:iterator>
									</table>
									<f:paginate name="CouponPage"/>
								</div>
								</td>
							</tr>

						</table>
						</td>
					</tr>
					<tr>
						<td style="background-image: url('/yintong/site/images/mc_19.gif');" height="26"></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>