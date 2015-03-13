<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="flink.util.SpringContext"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="gnete.card.entity.UserInfo"%>
<%@page import="gnete.etc.Constants"%>
<%@page import="org.springframework.web.util.WebUtils"%>
<%@page import="gnete.card.entity.type.RoleType"%>
<%@page import="flink.util.DateUtil"%>

<%@ taglib uri="/WEB-INF/runqianReport4.tld" prefix="report"%>

<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<%
    UserInfo userInfo = (UserInfo) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
	String roleType = userInfo.getRole().getRoleType();
	
	//如果是运营中心或运营中心部门时
	if (StringUtils.equals(roleType, RoleType.CENTER.getValue())
			|| StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue())) {
	}else {
		request.setAttribute("errMsg", "没有权限查看。");
		return;
	}
%>

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

		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			$(function(){
				// 选择分支机构
			    Selector.selectBranch('id_branchName', 'id_branchCode', true, '01');
			});
		</script>
		
	</head>
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
				<form id="searchForm" action="greateDiscntCenterFee.jsp" method="post" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td id="id_startDateEnd_td1"  align="right" >日期范围</td>
							<td id="id_startDateEnd_td2" >
								<input type="text" id="startDate" name="startDate" onclick="getStartDate();" class="{required:true}" style="width:68px;" value="${param.startDate}" />&nbsp;-&nbsp;
								<input type="text" id="endDate" name="endDate" onclick="getEndDate();" class="{required:true}" style="width:68px;" value="${param.endDate}" />
							</td>
							<td id="id_branch_td1"  align="right" >分支机构</td>
							<td id="id_branch_td2"  >
								<input  type="hidden"  id="id_branchCode" name="branchCode" />
								<input  type="text"  id="id_branchName" />
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
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			
			// 计费月份
			StringBuffer params = new StringBuffer(128);
			if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
				params.append("startDate=" + startDate + ";");
				params.append("endDate=" + endDate + ";");
				params.append("currDate=" + DateUtil.formatDate("yyyyMMdd") + ";");
			}
			
			// 运营机构号
			String branchCode = request.getParameter("branchCode");
			if(StringUtils.isNotBlank(branchCode)){
				params.append("branchCode=" + branchCode + ";");
			}

			System.out.println("params:" + params.toString());
		%>
		<% if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){ %>
			<report:html name="report1" reportFileName="/center/greateDiscntCenterFeeSum.raq"	
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