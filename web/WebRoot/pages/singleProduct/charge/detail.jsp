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
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>单机产品缴费记录详细信息<span class="caption_title"> | <f:link href="/pages/singleProduct/charge/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td width="80" height="30" align="right">id</td>
				<td>
					${costRecord.id }
				</td>
				<td width="80" height="30" align="right">发卡机构</td>
				<td>
					${costRecord.branchCode}-${fn:branch(costRecord.branchCode)}
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">类型</td>
				<td>
					${costRecord.typeName}
				</td>
				<td width="80" height="30" align="right">制卡ID</td>
				<td>
					${costRecord.makeId}
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">应收金额</td>
				<td>	
					${fn:amount(costRecord.amt)}
				</td>
				<td width="80" height="30" align="right">费用产生时间</td>
				<td>
					<s:date name="costRecord.genTime" format="yyyy-MM-dd HH:mm:ss" />
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">状态</td>
				<td>
					${costRecord.statusName}
				</td>
				<td width="80" height="30" align="right">已交金额</td>
				<td>
					${fn:amount(costRecord.paidAmt)}
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">缴费人</td>
				<td>
					${costRecord.payer}
				</td>
				<td width="80" height="30" align="right">缴费时间</td>
				<td>
					<s:date name="costRecord.payTime" format="yyyy-MM-dd HH:mm:ss" />
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">缴费方式</td>
				<td>
					${costRecord.payTypeName}
				</td>
				<td width="80" height="30" align="right">周期</td>
				<td>
					第${costRecord.period}年
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">是否已开发票</td>
				<td>
					${costRecord.invoiceStatusName}
				</td>
				<td width="80" height="30" align="right">开发票时间</td>
				<td>
					<s:date name="costRecord.invoiceTime" format="yyyy-MM-dd HH:mm:ss" />
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">次年缴费时间</td>
				<td>
					<s:date name="costRecord.nextPayTime" format="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td width="80" height="30" align="right">终端编号</td>
				<td>
					${costRecord.termId}
				</td>
			</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>