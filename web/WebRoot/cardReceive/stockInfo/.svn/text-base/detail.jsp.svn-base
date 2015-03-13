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
			<caption>卡库存明细查询<span class="caption_title"> | <f:link href="/cardReceive/stockInfo/list.do?goBack=goBack">返回列表</f:link></span></caption>
						<tr>
							<td width="80" height="30" align="right">ID</td>
							<td>
								${cardStockInfo.id}
							</td>
							<td width="80" height="30" align="right">卡号</td>
							<td>
								${cardStockInfo.cardId}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡种类</td>
							<td>
								${cardStockInfo.cardTypeName}
							</td>
							<td width="80" height="30" align="right">卡子类</td>
							<td>
								${cardStockInfo.cardSubclass}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡BIN</td>
							<td>
								${cardStockInfo.cardBin}
							</td>
							<td width="80" height="30" align="right">制卡申请ID</td>
							<td>
								${cardStockInfo.makeId}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								${cardStockInfo.cardIssuer}-${fn:branch(cardStockInfo.cardIssuer)}
							</td>
							<td width="80" height="30" align="right">领卡机构</td>
							<td>
								${cardStockInfo.appOrgId}-${fn:branch(cardStockInfo.appOrgId)}${fn:dept(cardStockInfo.appOrgId)}${fn:merch(cardStockInfo.appOrgId)}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">领卡日期</td>
							<td>
								${cardStockInfo.appDate}
							</td>
							<td width="80" height="30" align="right">状态</td>
							<td>
								${cardStockInfo.cardStatusName}
							</td>
						</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>