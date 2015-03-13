<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="flink.util.SpringContext"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="gnete.card.web.report.ICardReportLoad"%>
<%@page import="gnete.card.dao.BranchInfoDAO"%>
<%@page import="gnete.card.entity.BranchInfo"%>
<%@page import="flink.util.DateUtil"%>
<%@taglib uri="/WEB-INF/runqianReport4.tld" prefix="report"%>

<%@ include file="/common/taglibs.jsp" %>

<%response.setHeader("Cache-Control", "no-cache");%>

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
		
		<script>
		function generateReport(){
			$('#searchForm').attr('action', 'agentFeeShare.jsp').submit();
		}
		function generateExcel() {
			var feeMonth = $('#id_feeDate').val();
			var agentCode = $('#id_payCode').val();
			if(isEmpty(feeMonth) || isEmpty(agentCode)){
				alert("请先输入统计月份和营运代理商。");
				//showMsg("请先输入统计月份和营运代理商。");
			}
			else {
				if (!confirm('是否生成数据源？如果数据太大，请先耐心等待！')) {
					return;
				}
				else {
					$('#searchForm').attr('action', CONTEXT_PATH + '/pages/report/term/agentFeeShareTransSrc.do').submit();
				}
			}
		}
		</script>
	</head>
	<%
	 	ICardReportLoad shfrReportLoad = (ICardReportLoad)SpringContext.getService("agentFeeShareImpl");
	    shfrReportLoad.loadReportParams(request);
	    BranchInfoDAO branchInfoDAO = (BranchInfoDAO) SpringContext.getService("branchInfoDAOImpl"); 
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
			<span id="_msg_content" style="float: left">${errMsg}</span>
			<a id="_msg_close" href="javascript:hideMsg();" style="float: right;color: red">关闭 X</a>
		</div>
		</c:when>
		<c:otherwise>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<form id="searchForm" action="agentFeeShare.jsp" method="post" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3  cellpadding="0">
						<caption>运营代理商分润汇总报表</caption>
						<tr>
							<td align="right">统计月份</td>
							<td>
								<input type="text" id="id_feeDate" name="feeMonth" onclick="WdatePicker({dateFmt:'yyyyMM'})" class="{required:true}" value="${param.feeMonth}" />
							</td>							
							<c:if test="${showCardList}">
							<td align="right" id="id_CardList_td1">运营代理商</td>
							<td id="id_CardList_td2">
								<select id="id_payCode" name="agentCode" class="{required:true}" onmouseover="FixWidth(this)">
									<option value="">--请选择--</option>
								<c:forEach items="${cardBranchList}" var="u">
									<option value="${u.branchCode}" <c:if test="${param.agentCode eq u.branchCode}">selected</c:if>><c:out value="${u.branchName }"/></option>	
								</c:forEach>
								</select>
							</td>
							</c:if>
						</tr>
						<tr>
							<td height="30"></td>
							<td height="30" colspan="3">
							<input type="button" value="生成报表" id="input_btn2"  name="ok" onclick="generateReport();"/>
							<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							<!-- input style="margin-left:30px;" type="button" value="导出交易数据源" onclick="generateExcel();" id="input_btn3" /-->
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
			String feeMonth = request.getParameter("feeMonth");

			// 计费月份
			if(StringUtils.isNotBlank(feeMonth)){
				if(feeMonth.substring(4,feeMonth.length()).equals("12")){
					params.append("feeMonthAdd=" + feeMonth.substring(0,4) + "13;");
				} else {
					params.append("feeMonthAdd=;");
				}
				params.append("feeMonth=" + feeMonth + ";");
				params.append("currDate=" + DateUtil.formatDate("yyyyMMdd") + ";");
			}
			
			// 运营代理商 
			String agentCode = request.getParameter("agentCode");
			if(StringUtils.isNotBlank(agentCode)){
				params.append("agentCode=" + agentCode + ";");
				BranchInfo card = (BranchInfo) branchInfoDAO.findByPk(agentCode);
				params.append("agentName=" + card.getBranchName() + ";");
			}

			System.out.println("params:" + params.toString());
		 %>

		<% if(StringUtils.isNotBlank(feeMonth)){ %>
		 <report:html name="shfrReport" reportFileName="/term/agentFeeShare.raq"
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