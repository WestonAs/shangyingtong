<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="gnete.card.entity.UserInfo"%>
<%@page import="org.springframework.web.util.WebUtils"%>
<%@page import="gnete.etc.Constants"%>
<%@page import="gnete.card.entity.type.RoleType"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="flink.util.DateUtil"%>
<%@page import="gnete.card.entity.type.report.SingleProductReportType"%>

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
    	boolean showReport = false;
    	String errMsg = "";
    	List<SingleProductReportType> reportTypeList = SingleProductReportType.getAll();
    	
    	request.setAttribute("reportTypeList", reportTypeList);
    	
    	UserInfo userInfo = (UserInfo) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
    	String roleType = userInfo.getRole().getRoleType();
    	
    	StringBuilder params = new StringBuilder(128);
    	
    	// 营运中心或者营运中心部门
    	if(RoleType.CENTER.getValue().equals(roleType)
    		|| RoleType.CENTER_DEPT.getValue().equals(roleType)){
		} else {
			errMsg = "没有权限查看。";
		}
		
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
		<%if(StringUtils.isNotBlank(errMsg)) {%>
			<div class="msg">
				<span id="_msg_content" style="float: left"><%=errMsg %></span>
				<a id="_msg_close" href="javascript:hideMsg();" style="float: right;color: red">关闭 X</a>
			</div>
		<%} else { %>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<form id="searchForm" action="wytPay.jsp" method="post" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">划付日期</td>
							<td>
								<input id="id_rmaDate" type="text" name="rmaDate" onclick="WdatePicker({dateFmt:'yyyyMMdd'})" class="{required:true}" value="${param.rmaDate}" />
							</td>
							
							<td align="right">报表类型</td>
							<td>
								<select id="id_reportType" name="reportType" class="{required:true}" onmouseover="FixWidth(this)">
									<option value="">--请选择--</option>
								<c:forEach items="${reportTypeList}" var="u">
									<option value="${u.value}" <c:if test="${param.reportType eq u.value}">selected</c:if>><c:out value="${u.name}"/></option>	
								</c:forEach>
								</select>
							</td>
						</tr>
						
						<tr>
							<td></td>
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
			String rmaDate = request.getParameter("rmaDate");
			String reportType = request.getParameter("reportType");
			
			String currDate = DateUtil.formatDate("yyyyMMdd");
			
			// 判断查询条件是否输入完整。如果不完整，不显示报表
			if (StringUtils.isNotEmpty(rmaDate) && StringUtils.isNotEmpty(reportType)) {
				showReport = true;
			}
			
			params.append("rmaDate=" + rmaDate + ";");
			params.append("currDate=" + currDate + ";");
			
			if(SingleProductReportType.SINGLE_MAKE_FEE.getValue().equals(reportType)){
				params.append("reportNo=" + ( "JKFMK" + rmaDate) + ";");
			} else {
				params.append("reportNo=" + ( "JFKPF" + rmaDate) + ";");
			}
			
			// 发卡机构或机构部门
			if(roleType.equals(RoleType.CARD.getValue())
				|| roleType.equals(RoleType.CARD_DEPT.getValue())){
			}
			
			System.out.println("params = " + params.toString());
		%>
		<%if(showReport){ %>
			<% if(StringUtils.isNotBlank(reportType) && "0".equals(reportType)){ %>
				<report:html name="report1" reportFileName="/fenzhi/wytPlanPay.raq"	
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
			<% } if(StringUtils.isNotBlank(reportType) && "1".equals(reportType)){ %>
				<report:html name="report2" reportFileName="/fenzhi/wytMakeCardPay.raq"	
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
	  	<%} %>
	  	</div>
	<%} %>
	<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>