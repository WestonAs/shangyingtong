<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="gnete.card.entity.BranchInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gnete.card.entity.type.ChlFeeContentType"%>
<%@page import="gnete.card.entity.type.ChlFeeType"%>
<%@page import="gnete.card.entity.UserInfo"%>
<%@page import="org.springframework.web.util.WebUtils"%>
<%@page import="gnete.card.dao.BranchInfoDAO"%>
<%@page import="flink.util.SpringContext"%>
<%@page import="gnete.etc.Constants"%>
<%@page import="gnete.card.entity.type.RoleType"%>
<%@page import="org.apache.commons.lang.StringUtils"%>

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
    	boolean showCenter = false;
    	boolean showChl = false;
    	boolean showReport = false;
    	boolean showTimeSpan = false;
    	String reportType = "";
    	String errMsg = "";
    	
    	List<BranchInfo> branchList = new ArrayList<BranchInfo>();
    	
    	List<ChlFeeContentType> transTypeList = ChlFeeContentType.getList();
    	List<ChlFeeType> feeTypeList = ChlFeeType.getForChlFee();
    	
    	UserInfo userInfo = (UserInfo) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
    	BranchInfoDAO branchInfoDAO = (BranchInfoDAO) SpringContext.getService("branchInfoDAOImpl");
    	String roleType = userInfo.getRole().getRoleType();
    	
    	StringBuffer params1 = new StringBuffer(128);
    	StringBuffer params2 = new StringBuffer(128);
    	
    	// 营运中心或者营运中心部门
    	if(RoleType.CENTER.getValue().equals(roleType)
    		|| RoleType.CENTER_DEPT.getValue().equals(roleType)){
    		reportType = "total";
    		showCenter = true;
    		showChl = false;
    		showReport = true;
    		showTimeSpan = true;
    		branchList = branchInfoDAO.findByType(RoleType.FENZHI.getValue());
    	}
    	// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(roleType)) {
			reportType = "chl";
			showChl = true;
			showCenter = false;
			showReport = true;
			showTimeSpan = false;
			branchList.add((BranchInfo)branchInfoDAO.findByPk(userInfo.getBranchNo()));
		}
		else {
			errMsg = "没有权限查看。";
		}
		request.setAttribute("transTypeList", transTypeList);
		request.setAttribute("feeTypeList", feeTypeList);
		request.setAttribute("branchList", branchList);
		request.setAttribute("showCenter", showCenter);
		request.setAttribute("showChl", showChl);
		request.setAttribute("showTimeSpan", showTimeSpan);
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
				<form id="searchForm" action="reportChlFee.jsp" method="post" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">计费月份</td>
							<td>
							<c:choose>
								<c:when test="${showTimeSpan}">
									<input id="id_startMonth" type="text" name="startMonth" onclick="WdatePicker({dateFmt:'yyyyMM', maxDate:'#F{$dp.$D(\\\'endMonth\\\')}'})" style="width:68px;" class="{required:true}" value="${param.startMonth}"/>&nbsp;-&nbsp;
									<input id="id_endMonth" type="text" name="endMonth" onclick="WdatePicker({dateFmt:'yyyyMM', minDate:'#F{$dp.$D(\\\'startMonth\\\')}'})"  style="width:68px;" class="{required:true}" value="${param.endMonth}"/>
								</c:when>
								<c:otherwise>
									<input id="id_feeMonth" type="text" name="feeMonth" onclick="WdatePicker({dateFmt:'yyyyMM'})" class="{required:true}" value="${param.feeMonth}" />
								</c:otherwise>
							</c:choose>
							</td>
							
							<c:if test="${showCenter}">
							<td align="right" >分支机构</td>
							<td>
								<select name="chlCode">
									<option value="">--全部--</option>
								<c:forEach items="${branchList}" var="u">
									<option value="${u.branchCode}" <c:if test="${param.chlCode eq u.branchCode}">selected</c:if>><c:out value="${u.branchName }"/></option>	
								</c:forEach>
								</select>
							</td>
							</c:if>
							
							<td align="right">交易类型</td>
							<td>
								<select id="id_transTpye" name="transType">
									<option value="">--全部--</option>
								<c:forEach items="${transTypeList}" var="t">
									<option value="${t.value}" <c:if test="${param.transType eq t.value}">selected</c:if>><c:out value="${t.name }"/></option>	
								</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right">计费类型</td>
							<td>
								<select id="id_feeTpye" name="feeType">
									<option value="">--全部--</option>
								<c:forEach items="${feeTypeList}" var="f">
									<option value="${f.value}" <c:if test="${param.feeType eq f.value}">selected</c:if>><c:out value="${f.name }"/></option>	
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
			String startMonth = request.getParameter("startMonth");
			String endMonth = request.getParameter("endMonth");
			String feeMonth = request.getParameter("feeMonth");
			String chlCode = request.getParameter("chlCode");
			String transType = request.getParameter("transType");
			String feeType = request.getParameter("feeType");
			
			if(reportType.equals("total")){
				params1.append("startMonth=" + startMonth + ";");
				if(endMonth==null){
					endMonth = "";
				}
				params1.append("endMonth=" + endMonth + ";");	
				params1.append("chlCode=" + chlCode + ";");
				params1.append("transType=" + transType + ";");
				params1.append("feeType=" + feeType + ";");
				System.out.println("params1:" + params1.toString());
			}
			if(reportType.equals("chl")){
				if(feeMonth==null){
					feeMonth = "";
				}
				
				chlCode = userInfo.getBranchNo();
				params2.append("feeMonth=" + feeMonth + ";").append("chlCode=" + chlCode + ";");
				BranchInfo branchInfo = (BranchInfo) branchInfoDAO.findByPk(userInfo.getBranchNo());
				String chlName = branchInfo.getBranchName();
				params2.append("transType=" + transType + ";");
				params2.append("feeType=" + feeType + ";");
				params2.append("chlName=" + chlName + ";");
				System.out.println("params2" + params2.toString());
			}
		%>
		<%if(showReport){ %>
		<% if(StringUtils.isNotBlank(reportType) && reportType == "total"){ %>
		<report:html name="report1" reportFileName="/chlFeeMTotalAll.raq"	
		    params="<%=params1.toString() %>"
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
		<% } if(StringUtils.isNotBlank(reportType) && reportType == "chl"){ %>
		<report:html name="report2" reportFileName="/chlFeeMTotalChl.raq"	
		    params="<%=params2.toString() %>"
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
	<%} %>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>