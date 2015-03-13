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
	 	ICardReportLoad operateFee = (ICardReportLoad)SpringContext.getService("reportCommonAcctRma");
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
				<form id="searchForm" method="post" action="commonAcctRma.jsp" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">清算日期</td>
							<td>
								<input id="id_settDate" type="text" name="settDate" onclick="WdatePicker({dateFmt:'yyyyMMdd'})" 
									class="{required:true}" value="${param.settDate}" />
							</td>
							<c:if test="${showCardIssuerList}">
								<td align="right">商户</td>
								<td>
									<select id="id_cardIssuer" name="cardIssuer" onmouseover="FixWidth(this)">
										<option value="">--全部--</option>
										<c:forEach items="${cardIssuerList}" var="t">
											<option value="${t.branchCode}" <c:if test="${param.cardIssuer eq t.branchCode}">selected</c:if>>
												<c:out value="${t.branchName }"/>
											</option>	
										</c:forEach>
									</select>
								</td>
							</c:if>
							</tr>
							<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
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
		<div class="clear">
		<%
			StringBuffer params = new StringBuffer(128);
			
			String loginBranchCode = (String) request.getAttribute("loginBranchCode");
			boolean showCardIssuerList = (Boolean) request.getAttribute("showCardIssuerList");
			
			// 营运中心、分支机构登录
			if(showCardIssuerList){
				String cardIssuer = request.getParameter("cardIssuer");
				if(StringUtils.isNotBlank(cardIssuer)){
					params.append("cardIssuer=" + cardIssuer + ";");
				}
			}
			// 发卡机构登录
			else{
				System.out.println("loginBranchCode=" + loginBranchCode);
				if(StringUtils.isNotBlank(loginBranchCode)){
					params.append("cardIssuer=" + loginBranchCode + ";");
				}
			}
			
			// 清算日期
			String settDate = request.getParameter("settDate");
			if(StringUtils.isNotBlank(settDate)){
				params.append("settDate=" + settDate + ";");
			}
			
		
			System.out.println("params:" + params);
		%>
		<% if(StringUtils.isNotBlank(settDate)){ %>
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
		</div>
	  </c:otherwise>
	  </c:choose>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>