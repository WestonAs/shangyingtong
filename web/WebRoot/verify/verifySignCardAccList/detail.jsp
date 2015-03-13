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
		
		<f:js src="/js/biz/cardTypeSet/pointClass.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>签单卡账单详细信息<span class="caption_title"> | <f:link href="/verify/verifySignCardAccList/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td width="80" height="30" align="right">卡号</td>
				<td>
					${card.cardId}
				</td>
				<td width="80" height="30" align="right">账单月份</td>
				<td>
					${card.accMonth}
				</td>
				<td width="80" height="30" align="right">账单生成日期</td>
				<td>
					${card.accDate}
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">签单客户ID</td>
				<td>
					${card.signCustId}
				</td>
				<td width="80" height="30" align="right">本期账单额</td>
				<td>
					${fn:amount(card.curAmt)}
				</td>
				<td width="80" height="30" align="right">年费</td>
				<td>
					${fn:amount(card.yearAmt)}
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">可用余额</td>
				<td>
					${fn:amount(card.useAmt)}
				</td>
				<td width="80" height="30" align="right">罚息</td>
				<td>
					${fn:amount(card.feeAmt)}
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">账单开始日期</td>
				<td>
					${card.strDate}
				</td>
				<td width="80" height="30" align="right">账单结束日期</td>
				<td>
					${card.endDate}
				</td>
				<td width="80" height="30" align="right">还款到期日期</td>
				<td>
					${card.expDate}
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">还款日期</td>
				<td>
					${card.payDate}
				</td>
				<td width="80" height="30" align="right">还款金额</td>
				<td>
					${card.payAmt}
				</td>
				<td width="80" height="30" align="right">状态</td>
				<td>
					${card.stateName}
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">操作员</td>
				<td>
					${card.updateUser}
				</td>
				<td width="80" height="30" align="right">操作时间</td>
				<td>
					${card.updateTime}
				</td>
				<td width="80" height="30" align="right">备注</td>
				<td>
					${card.remark}
				</td>
			</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>