<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="flink.util.SpringContext"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="gnete.card.web.report.ICardReportLoad"%>
<%@page import="flink.util.DateUtil"%>
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
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<script>
			function checkDateTime(){
				var starTime = $('#id_startDate').val();
				var endTime = $('#id_endDate').val();
				
				if(starTime > endTime){
					alert("开始时间不能大于结束时间");
					return false;
				}
				
				return true;
			}	
		</script>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
	
	<%
	 	ICardReportLoad operateFee = (ICardReportLoad)SpringContext.getService("reportShuanghuiChangeDetail");
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
				<form id="searchForm" method="post" action="shuanghuiChgDetail.jsp" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">开始时间</td>
							<td>
								<input id="id_startDate" type="text" name="startDate" onclick="WdatePicker({dateFmt:'yyyyMMddHHmmss'})" class="{required:true}" value="${param.startDate}" />
							</td>
							<td align="right">结束时间</td>
							<td>
								<input id="id_endDate" type="text" name="endDate" onclick="WdatePicker({dateFmt:'yyyyMMddHHmmss'})" class="{required:true}" value="${param.endDate}" />
							</td>
						</tr>
						<c:if test="${showCardBranch}">
						<tr>
							<td align="right" id="id_CardList_td1">发卡机构</td>
							<td id="id_CardList_td2" colspan="3">
								<select id="id_cardBranchCode" name="cardBranchCode" class="{required:true}" style="width: auto;">
									<option value="">--请选择--</option>
								<c:forEach items="${cardBranchList}" var="u">
									<option value="${u.branchCode}" <c:if test="${param.cardBranchCode eq u.branchCode}">selected</c:if>><c:out value="${u.branchName }"/></option>	
								</c:forEach>
								</select>
							</td>
						</tr>
						</c:if>
						<tr>
							<td></td>
							<td colspan="3">
								<input type="submit" value="生成报表" id="input_btn2"  name="ok" onclick="return checkDateTime();" />
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
				
				// 时间范围
			    String startDate = request.getParameter("startDate");
			    String endDate = request.getParameter("endDate");
			    String cardBranchCode = request.getParameter("cardBranchCode");
			    System.out.println("startDate:" + startDate);
			    System.out.println("endDate:" + endDate);
			    System.out.println("cardBranchCode:" + cardBranchCode);
			    if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate) && StringUtils.isNotBlank(cardBranchCode)){
					params.append("startDate=" + startDate + ";");
					params.append("endDate=" + endDate + ";");
					params.append("cardBranchCode=" + cardBranchCode + ";");
					params.append("cardBranchName=" + NameTag.getBranchName(cardBranchCode) + ";");
					params.append("currDate=" + DateUtil.formatDate("yyyyMMdd") + ";");
				}
				
				System.out.println("params:" + params);
			%>
			<% if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate) && StringUtils.isNotBlank(cardBranchCode)){ %>
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