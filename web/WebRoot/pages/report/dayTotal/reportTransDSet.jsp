<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="flink.util.SpringContext"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="gnete.card.web.report.ICardReportLoad"%>

<%@ taglib uri="/WEB-INF/runqianReport4.tld" prefix="report"%>

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
	
	<%
	 	ICardReportLoad operateFee = (ICardReportLoad)SpringContext.getService("reportTransSet");
	    operateFee.loadReportParams(request);
	 %>
    
	<body>
		<div class="location">您当前所在位置： <span class="redlink"><f:link href="/home.jsp">首页</f:link></span>
			<c:choose>
				<c:when test="${PRIVILEGE_PATH !=null}">
					<c:forEach items="${PRIVILEGE_PATH}" var="menu">
					&gt; ${menu.name}
					</c:forEach>
				</c:when>
			</c:choose>
		</div>
		<c:choose>
		<c:when test="${not empty errMsg}">
		<div class="msg">
			<span id="_msg_content" style="float: left">${errMsg }</span>
			<a id="_msg_close" href="javascript:hideMsg();" style="float: right;color: red">关闭 X</a>
		</div>
		</c:when>
		<c:otherwise>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<form id="searchForm" method="post" action="reportTransDSet.jsp" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">清算日期</td>
							<td>
							<c:choose>
								<c:when test="${showDateScope}">
									<input type="text" id="id_startDate" name="startDate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\\\'endDate\\\')}'})" style="width:68px;" class="{required:true}" value="${param.startDate}"/>&nbsp;-&nbsp;
									<input type="text" id="id_endDate" name="endDate" onclick="WdatePicker({minDate:'#F{$dp.$D(\\\'startDate\\\')}'})"  style="width:68px;" class="{required:true}" value="${param.endDate}"/>
								</c:when>
								<c:otherwise>
									<input type="text" id="id_setDate" name="setDate" onclick="WdatePicker()" class="{required:true}" value="${param.setDate}" />
								</c:otherwise>
							</c:choose>
							<c:if test="${showPayCode}">
							<td align="right" id="id_payCode_td1">付款方</td>
							<td id="id_payCode_td2">
								<select id="id_payCode" name="payCode">
									<option value="">--全部--</option>
								<c:forEach items="${payCodeList}" var="u">
									<option value="${u.branchCode}" <c:if test="${param.payCode eq u.branchCode}">selected</c:if>><c:out value="${u.branchName }"/></option>	
								</c:forEach>
								</select>
							</td>
							</c:if>
							<c:if test="${showRecvCode}">
							<td align="right" id="id_payCode_td1">收款方</td>
							<td id="id_payCode_td2">
								<select id="id_payCode" name="recvCode">
									<option value="">--全部--</option>
								<c:forEach items="${recvCodeList}" var="t">
									<option value="${t.merchId}" <c:if test="${param.recvCode eq t.merchId}">selected</c:if>><c:out value="${t.merchName }"/></option>	
								</c:forEach>
								</select>
							</td>
							</c:if>
						</tr>
						<tr>
							<td align="right">交易类型</td>
							<td>
								<select id="id_transTpye" name="transType">
									<option value="">--全部--</option>
								<c:forEach items="${transTypeList}" var="t">
									<option value="${t.value}" <c:if test="${param.transType eq t.value}">selected</c:if>><c:out value="${t.name }"/></option>	
								</c:forEach>
								</select>
							</td>
							<td height="30" colspan="3">
							<input type="submit" value="生成报表" id="input_btn2"  name="ok" />
							<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<%
			StringBuffer params = new StringBuffer(128);
			
			String payBranch = (String) request.getAttribute("payBranch");
			if(StringUtils.isNotBlank(payBranch)){
				params.append(payBranch);
			}
			
			// 交易类型
			String transType = request.getParameter("transType");
			params.append("transType=" + transType + ";");
			
			//清算日期范围
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if(StringUtils.isNotBlank(startDate)){
				params.append("startDate=" + startDate + ";");
				params.append("endDate=" + endDate + ";");
			}
			
			// 清算日期
			String setDate = request.getParameter("setDate");
			if(StringUtils.isNotBlank(setDate)){
				params.append("setDate=" + setDate + ";");
			}
			
			// 付款方
			String payCode = request.getParameter("payCode");
			if(StringUtils.isNotBlank(payCode)){
				params.append("payCode=" + payCode + ";");
			}
			
			// 收款方
			String recvCode = request.getParameter("recvCode");
			if(StringUtils.isNotBlank(recvCode)){
				params.append("recvCode=" + recvCode + ";");
			}
			System.out.println("params:" + params);
		%>
		<% if(StringUtils.isNotBlank(startDate) || StringUtils.isNotBlank(setDate)){ %>
		<report:html name="report1" reportFileName="${reportFile}"	
		    params="<%=params.toString() %>"
		    funcBarFontFace="宋体"               
			funcBarFontSize="14px"   	
			needSaveAsExcel="yes"
			needSaveAsPdf="yes"
			needSaveAsWord="yes"
			needPrint="yes"
			funcBarLocation="bottom"
			width="-1"
			useCache="false"
		/>
		<%} %>
	  </c:otherwise>
	  </c:choose>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>