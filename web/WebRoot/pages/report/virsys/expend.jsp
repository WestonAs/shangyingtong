<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="flink.util.SpringContext"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="gnete.card.web.report.ICardReportLoad"%>
<%@page import="gnete.card.entity.BranchInfo"%>
<%@page import="gnete.card.dao.BranchInfoDAO"%>
<%@page import="gnete.card.entity.MerchInfo"%>
<%@page import="gnete.card.dao.MerchInfoDAO"%>
<%@page import="flink.util.DateUtil"%>
<%@page import="gnete.card.entity.UserInfo"%>
<%@page import="gnete.etc.Constants"%>
<%@page import="gnete.card.entity.UserInfo"%>
<%@page import="gnete.etc.Constants"%>
<%@page import="org.springframework.web.util.WebUtils"%>
<%@page import="gnete.card.entity.type.RoleType"%>

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
			$(function(){
			});
			
		</script>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
	
	<%
		UserInfo userInfo = (UserInfo) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
		BranchInfoDAO branchInfoDAO = (BranchInfoDAO) SpringContext.getService("branchInfoDAOImpl");
		MerchInfoDAO merchInfoDAO = (MerchInfoDAO) SpringContext.getService("merchInfoDAOImpl");
		String roleType = userInfo.getRole().getRoleType();
		
		//如果是运营中心或运营中心部门时,可查看所有发卡机构的报表
		if (StringUtils.equals(roleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue())) {
			BranchInfo branchInfo = (BranchInfo) branchInfoDAO.findByPk(userInfo.getBranchNo());
			request.setAttribute("branchCode", branchInfo.getBranchCode());
			request.setAttribute("branchName", branchInfo.getBranchName());
		}
		// 运营分支机构登录时,可查看自己管理的发卡机构的报表
		else if (StringUtils.equals(roleType, RoleType.FENZHI.getValue())) {
			BranchInfo branchInfo = (BranchInfo) branchInfoDAO.findByPk(userInfo.getBranchNo());
			request.setAttribute("branchCode", branchInfo.getBranchCode());
			request.setAttribute("branchName", branchInfo.getBranchName());
		}
		// 集团机构角色登录时，可查看自己集团下的发卡机构的报表
		else if (StringUtils.equals(roleType, RoleType.GROUP.getValue())) {
			BranchInfo branchInfo = (BranchInfo) branchInfoDAO.findByPk(userInfo.getBranchNo());
			request.setAttribute("branchCode", branchInfo.getBranchCode());
			request.setAttribute("branchName", branchInfo.getBranchName());
		}
		// 发卡机构角色登录时，可查看自己的报表
		else if (StringUtils.equals(roleType, RoleType.CARD.getValue())
				|| StringUtils.equals(roleType, RoleType.CARD_DEPT.getValue())) {
			BranchInfo branchInfo = (BranchInfo) branchInfoDAO.findByPk(userInfo.getBranchNo());
			request.setAttribute("branchCode", branchInfo.getBranchCode());
			request.setAttribute("branchName", branchInfo.getBranchName());
		}
		// 商户角色登录时，可查看自己的报表
		else if (StringUtils.equals(roleType, RoleType.MERCHANT.getValue())) {
			MerchInfo merchInfo = (MerchInfo) merchInfoDAO.findByPk(userInfo.getMerchantNo());
			request.setAttribute("branchCode", merchInfo.getMerchId());
			request.setAttribute("branchName", merchInfo.getMerchName());
		}
		// 其他角色没有权限查看报表
		else {
			request.setAttribute("errMsg", "没有权限查看。");
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
				<form id="searchForm" method="post" action="expend.jsp" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td id="id_startEnd_td1"  align="right" >日期范围</td>
							<td id="id_startEnd_td2" >
								<input type="text" id="startDate" name="startDate" onclick="getStartDate();" class="{required:true}" style="width:68px;"  value="${param.startDate}" />&nbsp;-&nbsp;
								<input type="text" id="endDate" name="endDate" onclick="getEndDate();" class="{required:true}" style="width:68px;"  value="${param.endDate}" />
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
			StringBuffer params = new StringBuffer(128);
			
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			
			if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
				params.append("startDate=" + startDate + ";");
				params.append("endDate=" + endDate + ";");
				params.append("curDate=" + DateUtil.formatDate("yyyyMMdd") + ";");
			}
			
			String branchCode = (String)request.getAttribute("branchCode");
			String branchName = (String)request.getAttribute("branchName");
			if(StringUtils.isNotBlank(branchCode)){
				params.append("branchCode=" + branchCode + ";");
				params.append("branchName=" + branchName + ";");
			}
			System.out.println("params:" + params);
		%>
		<% if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){ %>
		<report:html name="report1" reportFileName="/virsys/virSysExpendDayDetail.raq"	
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