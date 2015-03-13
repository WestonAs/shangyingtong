<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="gnete.etc.WorkflowConstants"%>
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
		
		<div id="printArea" >
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>领卡详细信息<span class="caption_title"> | <f:link href="/cardReceive/receive/list.do?goBack=goBack">返回列表</f:link></span></caption>
						<tr>
							<td width="80" height="30" align="right">ID</td>
							<td>
								${appReg.id}
							</td>
							<td width="80" height="30" align="right">领卡日期</td>
							<td>
								${appReg.appDate}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								${appReg.cardIssuer}-${fn:branch(appReg.cardIssuer)}
							</td>
							<td width="80" height="30" align="right">领卡机构</td>
							<td>
								${appReg.appOrgId}-${fn:branch(appReg.appOrgId)}${fn:dept(appReg.appOrgId)}${fn:merch(appReg.appOrgId)}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">卡种类</td>
							<td>
								${appReg.cardTypeName}
							</td>
							<td width="80" height="30" align="right">卡子类型</td>
							<td>
								${appReg.cardSubClass}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">申请卡起始号</td>
							<td>
								${appReg.strNo}
							</td>
							<td width="80" height="30" align="right">申请卡数量</td>
							<td>
								${appReg.cardNum}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">审核卡起始号</td>
							<td>
								${appReg.checkStrNo}
							</td>
							<td width="80" height="30" align="right">审核卡数量</td>
							<td>
								${appReg.checkCardNum}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">结束卡号</td>
							<td>
								${appReg.endCardId}
							</td>
							<td width="80" height="30" align="right">状态</td>
							<td>
								${appReg.statusName}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">领卡面值</td>
							<td>
								${fn:amount(appFaceValue)}
							</td>
							<td width="80" height="30" align="right">领卡总金额</td>
							<td>
								${fn:amount(appSumAmt)}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">发生方式</td>
							<td>
								${appReg.flagName}
							</td>
							<td width="80" height="30" align="right">联系人</td>
							<td>
								${appReg.linkMan}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">联系方式</td>
							<td>
								${appReg.contact}
							</td>
							<td width="80" height="30" align="right">联系地址</td>
							<td>
								${appReg.address}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">更新用户名</td>
							<td>
								${appReg.updateBy}
							</td>
							<td width="80" height="30" align="right">更新时间</td>
							<td>
								<s:date name="appReg.updateTime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td colspan="3">
								${appReg.memo}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">申购单号</td>
							<td>
								${appReg.orderNo}
							</td>
							<td width="80" height="30" align="right">申购单位</td>
							<td>
								${appReg.orderUnit}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">身份证号</td>
							<td>
								${appReg.identityCard}
							</td>
							<td width="80" height="30" align="right"></td>
							<td>
							</td>
						</tr>
		</table>
		</div>
		</div><!-- end of printArea -->
		
		<div style="padding-left: 30px; margin-bottom: 10px;">
			<input type="button" value="打印" onclick="openPrint();"/>
		</div>
		
		<!-- 流程相关信息 -->
		<jsp:include flush="true" page="/workflow/flow.jsp">
			<jsp:param name="workflowId" value="<%=WorkflowConstants.CARD_RECEIVE%>"/>
			<jsp:param name="refId" value="${appReg.id}"/>
		</jsp:include>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>