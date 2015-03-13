<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="flink.util.SpringContext"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="gnete.card.web.report.ICardReportLoad"%>
<%@page import="gnete.card.entity.BranchInfo"%>
<%@page import="gnete.card.dao.BranchInfoDAO"%>
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

		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:css href="/css/multiselctor.css"/>
		
        <script>
			$(function(){
				if ('${SESSION_USER.role.roleType}'=='00'||'${SESSION_USER.role.roleType}'=='02' ){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20');
				} else if ('${SESSION_USER.role.roleType}'=='01' ){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20', '', '', '${SESSION_USER.branchNo}');
				}
				
				var reportType = $('#id_reportType').val();
				if(reportType == '0'){
					$('td[id^="id_setDate_Td"]').hide();
					$('#id_setDate').attr('disabled', 'true')

					$('td[id^="id_startEnd_td"]').show();
					$('#startDate').removeAttr('disabled');
					$('#endDate').removeAttr('disabled');
				} else {
					$('td[id^="id_setDate_Td"]').show();
					$('#id_setDate').removeAttr('disabled');
					
					$('td[id^="id_startEnd_td"]').hide();
					$('#startDate').attr('disabled', 'true');
					$('#endDate').attr('disabled', 'true');
				}
	
				$('#id_reportType').change(changeReportType);
			});
			
			
			function changeReportType(){
				var reportType = $('#id_reportType').val();
				if(reportType == '0'){
					$('td[id^="id_setDate_Td"]').hide();
					$('#id_setDate').attr('disabled', 'true')

					$('td[id^="id_startEnd_td"]').show();
					$('#startDate').removeAttr('disabled');
					$('#endDate').removeAttr('disabled');
				} else {
					$('td[id^="id_setDate_Td"]').show();
					$('#id_setDate').removeAttr('disabled');
					
					$('td[id^="id_startEnd_td"]').hide();
					$('#startDate').attr('disabled', 'true');
					$('#endDate').attr('disabled', 'true');
				}
			}
		</script>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
	
	<%
	 	ICardReportLoad operateFee = (ICardReportLoad)SpringContext.getService("cardSellDepositFeeDayImpl");
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
				<form id="searchForm" method="post" action="dayFee.jsp" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">报表类型</td>
							<td colspan="3">
								<select id="id_reportType" name="reportType" class="{required:true}" onmouseover="FixWidth(this)">
									<option value="">--请选择--</option>
								<c:forEach items="${reportTypeList}" var="u">
									<option value="${u.value}" <c:if test="${param.reportType eq u.value}">selected</c:if>><c:out value="${u.name }"/></option>	
								</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
						    <td  id="id_setDate_Td2"  align="right">清算日期</td>
							<td id="id_setDate_Td1" >
								<input type="text" id="id_setDate" name="setDate" onclick="WdatePicker()" class="{required:true}" value="${param.setDate}" />
							</td>
							<td id="id_startEnd_td1"  align="right" style="display: none;">日期范围</td>
							<td id="id_startEnd_td2" style="display: none;">
								<input type="text" id="startDate" name="startDate" onclick="getStartDate();" class="{required:true}" style="width:68px;" disabled="disabled" value="${param.startDate}" />&nbsp;-&nbsp;
								<input type="text" id="endDate" name="endDate" onclick="getEndDate();" class="{required:true}" style="width:68px;" disabled="disabled" value="${param.endDate}" />
							</td>
						    <!-- 
							<td align="right">统计日期</td>
							<td>
								<input type="text" id="id_setDate" name="setDate" onclick="WdatePicker({dateFmt:'yyyyMMdd'})" class="{required:true}" value="${param.setDate}" />
							</td> 
							<td align="right">日期范围</td>
							<td>
								<input type="text" id="startDate" name="startDate" onclick="getStartDate();" class="{required:true}" style="width:68px;" value="${param.startDate}" />&nbsp;-&nbsp;
								<input type="text" id="endDate" name="endDate" onclick="getEndDate();" class="{required:true}" style="width:68px;" value="${param.endDate}" />
							</td>-->
							<c:if test="${showCardList}">
								<td align="right" id="id_CardList_td1">发卡/操作机构</td>
								<td id="id_CardList_td2">
									<c:choose>
									  <c:when test='${SESSION_USER.role.roleType eq "00" || SESSION_USER.role.roleType eq "01" || SESSION_USER.role.roleType eq "02"}'>
											<input type="hidden" id="idBranchCode" name="payCode" value="${param.payCode}"/>
											<input type="text" id="idBranchCode_sel" name="branchName" value="${param.branchName}" class="{required:true}"/>
									  </c:when>
									  <c:otherwise>
											<select id="id_payCode" name="payCode" class="{required:true}" onmouseover="FixWidth(this)">
												<option value="">--请选择--</option>
												<c:forEach items="${cardBranchList}" var="u">
													<option value="${u.branchCode}" <c:if test="${param.payCode eq u.branchCode}">selected</c:if>><c:out value="${u.branchName }"/></option>	
												</c:forEach>
											</select>
									  </c:otherwise>
									</c:choose>
								</td>
							</c:if>
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
			StringBuilder params = new StringBuilder(128);
			
			String type =request.getParameter("reportType");
			
			if (StringUtils.equals(type, "0")) {
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				
				if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
					params.append("startDate=" + startDate + ";");
					params.append("endDate=" + endDate + ";");
					params.append("currDate=" + DateUtil.formatDate("yyyyMMdd") + ";");
				}
			} else {
				// 清算日期
				String setDate = request.getParameter("setDate");
				if(StringUtils.isNotBlank(setDate)){
					params.append("setDate=" + setDate + ";");
					params.append("currDate=" + DateUtil.formatDate("yyyyMMdd") + ";");
				}
			}
			
			boolean showCardList = (Boolean) request.getAttribute("showCardList");
			
			if(showCardList) {
				String cardBranchCode = request.getParameter("payCode");
				if(StringUtils.isNotBlank(cardBranchCode)){
					params.append("cardBranchCode=" + cardBranchCode + ";");
					params.append("cardBranchName=" + NameTag.getBranchName(cardBranchCode) + ";");
				}
			} else {
				String cardBranch = (String) request.getAttribute("cardBranch");
				if(StringUtils.isNotBlank(cardBranch)){
					params.append("cardBranchCode=" + cardBranch + ";");
					params.append("cardBranchName=" + NameTag.getBranchName(cardBranch) + ";");
				}
			}
			
			System.out.println("params:" + params);
		%>
		<% if(StringUtils.isNotBlank(type) && type.equals("1")){ %>
			<report:html name="report1" reportFileName="/card/sellDepositFeeDSet.raq"	
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
		<% } else if(StringUtils.isNotBlank(type) && type.equals("0")){ %>
			<report:html name="report1" reportFileName="/card/sellDepositFeeDSetByBranch.raq"	
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