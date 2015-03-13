<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="flink.util.SpringContext"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="gnete.card.web.report.ICardReportLoad"%>
<%@page import="flink.util.DateUtil"%>
<%@page import="gnete.card.dao.MerchInfoDAO"%>
<%@page import="gnete.card.entity.MerchInfo"%>

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
			var loginRoleType = $('#id_loginRoleType').val();
			var loginBranchCode = $('#id_loginBranchCode').val();
			if(loginRoleType == '00'){
				Selector.selectMerch('idMerchId_sel', 'idMerchId', true);
			} else if(loginRoleType == '01'){
				Selector.selectMerch('idMerchId_sel', 'idMerchId', true, '', '', '', loginBranchCode);
			}
		});
		</script>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
	
	<%
	 	ICardReportLoad operateFee = (ICardReportLoad)SpringContext.getService("merchTransDSetImpl");
	    operateFee.loadReportParams(request);
	    MerchInfoDAO merchInfoDAO = (MerchInfoDAO) SpringContext.getService("merchInfoDAOImpl");
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
				<form id="searchForm" method="post" action="transDSet.jsp" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">清算日期</td>
							<td>
							    <input type="text" id="startDate" name="startDate" onclick="getStartDate();" class="{required:true}" style="width:68px;" value="${param.startDate}" />&nbsp;-&nbsp;
								<input type="text" id="endDate" name="endDate" onclick="getEndDate();" class="{required:true}" style="width:68px;" value="${param.endDate}" />
								<!-- input type="text" id="id_setDate" name="setDate" onclick="WdatePicker()" class="{required:true}" value="${param.setDate}" /-->
							</td>
							<c:if test="${showMerchList}">
							<td width="80" height="30" align="right">商户</td>
							<td>
								<input id="id_loginRoleType" type="hidden" name="loginRoleType" value="${loginRoleType}" />
								<input id="id_loginBranchCode" type="hidden" name="loginBranchCode" value="${loginBranchCode}"/>
								<input id="idMerchId" type="hidden" name="merchantNo"  value="${param.merchantNo}"/>
								<input id="idMerchId_sel" type="text" name="merchId_sel" class="{required:true}" value="${param.merchId_sel}"/>
							</td>
							</c:if>
							<c:if test="${showMerchCluster}">
							<td width="80" height="30" align="right">商户集群</td>
							<td>
						       <select id="id_merchClusterId" name="merchClusterId" class="{required:true}" onmouseover="FixWidth(this)">
									<option value="">--请选择--</option>
									<c:forEach items="${merchClusterInfos}" var="u">
										<option value="${u.merchClusterId}" <c:if test="${param.merchClusterId eq u.merchClusterId}">selected</c:if>><c:out value="${u.merchClusterName }"/></option>	
									</c:forEach>
								</select>
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
			StringBuffer params = new StringBuffer(128);
			
			// 清算日期
			//String setDate = request.getParameter("setDate");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			
			if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
				//params.append("setDate=" + setDate + ";");
				params.append("startDate=" + startDate + ";");
				params.append("endDate=" + endDate + ";");
				params.append("currDate=" + DateUtil.formatDate("yyyyMMdd") + ";");
			}
			
			boolean showMerchList = (Boolean) request.getAttribute("showMerchList");
			if(showMerchList){
				String merchId = request.getParameter("merchantNo");
				if(StringUtils.isNotBlank(merchId)){
					params.append("merchId=" + merchId + ";");
					MerchInfo merch = (MerchInfo) merchInfoDAO.findByPk(merchId);
					params.append("merchName=" + merch.getMerchName() + ";");
				}
			} else {
				String merchId = (String) request.getAttribute("merchId");
				if(StringUtils.isNotBlank(merchId)){
					params.append("merchId=" + merchId + ";");
					MerchInfo merch = (MerchInfo) merchInfoDAO.findByPk(merchId);
					params.append("merchName=" + merch.getMerchName() + ";");
				}
			}
			
			boolean showMerchCluster = (Boolean) request.getAttribute("showMerchCluster");
			String merchClusterId = request.getParameter("merchClusterId");
			if(showMerchCluster && null != merchClusterId){
				HashMap clusterMerchMap = (HashMap)request.getAttribute("clusterMerchMap");
				if(null != clusterMerchMap && null != clusterMerchMap.get(Long.valueOf(merchClusterId))){
				    params.append("merchId=" + clusterMerchMap.get(Long.valueOf(merchClusterId)) + ";");
				}else{//集群为空
				    params.append("merchId= ;");
				}
			}
			
			System.out.println("params:" + params);
		%>
		<% if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){ %>
			<report:html name="report1" reportFileName="/merch/transDSetMerch.raq"	
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