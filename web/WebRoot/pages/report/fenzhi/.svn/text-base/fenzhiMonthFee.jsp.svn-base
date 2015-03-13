<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="flink.util.SpringContext"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="gnete.card.web.report.ICardReportLoad"%>
<%@page import="flink.util.DateUtil"%>
<%@page import="gnete.card.entity.UserInfo"%>
<%@page import="gnete.card.tag.NameTag"%>

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

		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
			$(function(){
				var loginRoleType = '<%= ((UserInfo) request.getSession().getAttribute("SESSION_USER")).getRole().getRoleType() %>';
				var loginBranchCode = '<%= ((UserInfo) request.getSession().getAttribute("SESSION_USER")).getBranchNo() %>';
				if(loginRoleType == '00'){
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '01');
				} else if(loginRoleType == '01'){
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '01', '', '', loginBranchCode);
				}
			});
		</script>
	</head>
	
	<%
	 	ICardReportLoad operateFee = (ICardReportLoad)SpringContext.getService("fenzhiSellDepositFeeMonthImpl");
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
				<form id="searchForm" method="post" action="fenzhiMonthFee.jsp" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">统计月份</td>
							<td>
								<input id="id_feeMonth" type="text" name="feeMonth" onclick="WdatePicker({dateFmt:'yyyyMM'})" class="{required:true}" value="${param.feeMonth}" />
							</td>
							<td align="right">运营机构</td>
							<td>
								<input type="hidden" id="idBranchCode" name="branchCode" value="${param.branchCode}"/>
								<input type="text" id="idBranchName" name="branchName" value="${param.branchName}" class="{required:true}" />
							</td>
							<td>
								<input type="submit" value="生成报表" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm');$('#idBranchCode').val('');$('#idBranchName').val('');" name="escape" />
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
			
			String chlCode = request.getParameter("branchCode");
			
			// 计费日期
			String setMonth = request.getParameter("feeMonth");
			if(StringUtils.isNotBlank(setMonth)){
				params.append("chlCode=" + chlCode + ";");
				params.append("chlName=" + NameTag.getBranchName(chlCode) + ";");
				params.append("setMonth=" + setMonth + ";");
				params.append("currDate=" + DateUtil.formatDate("yyyyMMdd") + ";");
			}
			
			System.out.println("params:" + params);
		%>
		<% if(StringUtils.isNotBlank(setMonth) ){ %>
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