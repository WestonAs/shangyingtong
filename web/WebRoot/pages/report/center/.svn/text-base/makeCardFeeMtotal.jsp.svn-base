<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="flink.util.CommonHelper"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="flink.util.DateUtil"%>
<%@page import="gnete.etc.Constants"%>
<%@page import="org.springframework.web.util.WebUtils"%>

<%@ taglib uri="/WEB-INF/runqianReport4.tld" prefix="report"%>

<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>
<%@page import="gnete.card.entity.type.RoleType"%>
<%@page import="gnete.card.entity.UserInfo"%>
<html xmlns="http://www.w3.org/1999/xhtml" >
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
		
		<f:js src="/js/validate.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<%
		    UserInfo userInfo = (UserInfo) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
			String roleType = userInfo.getRole().getRoleType();
			
			//如果是运营中心或运营中心部门时
			if (StringUtils.equals(roleType, RoleType.CENTER.getValue())
					|| StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue())) {
			}else {
				request.setAttribute("errMsg", "没有权限查看。");
				//return;
			}
       %>
		<script>

		$(function(){
			// 选择分支机构
			Selector.selectBranch('idBranchName', 'idBranchCode', true, '20');

		});
		
		function dateSelect(){
		    var reportType = $('#id_reportType').val();
		    if(reportType == '2' || reportType == '3' ){
		        javascript:WdatePicker({dateFmt:'yyyy13'});
		    }else{
		        javascript:WdatePicker({dateFmt:'yyyyMM'});
		    }
		}

		
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
		
		<div id="loadingBarDiv"	style="display: none; width: 100%; height: 100%">
			<table width=100% height=100%>
				<tr>
					<td align=center >
						<center>
							<img src="../../../images/ajax_saving.gif" align="middle" />
							<div id="processInfoDiv"
								style="font-size: 15px; font-weight: bold">
								正在处理中，请稍候...
							</div>
							<br/>
							<br/>
							<br/>
						</center>
					</td>
				</tr>
			</table>
		</div>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<form id="searchForm" action="makeCardFeeMtotal.jsp" method="post" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">计费月份</td>
							<td>
								<input type="text"  id="id_feeMonth" name="feeMonth" onclick="dateSelect();" class="{required:true}" value="${param.feeMonth}" />
							</td>
							<td align="right">发卡机构</td>
							<td>
							    <input  type="hidden"  id="idBranchCode" name="branchCode" value="${param.branchCode}"/>
								<input  type="text"  id="idBranchName" name="branchName" value="${param.branchName}" />
							</td>
						</tr>
							<tr>
							<td height="30">
							</td>
							<td height="30" colspan="3">
							<input type="submit" value="生成报表" id="input_btn2" name="ok"/>
							<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm');$('#idBranchName').val('');$('#idBranchCode').val('');" name="escape" />				
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
			String branchCode =request.getParameter("branchCode");
			String feeMonth = request.getParameter("feeMonth");
			
			// 计费月份
			StringBuffer params = new StringBuffer(128);
			if(StringUtils.isNotBlank(feeMonth)){

				params.append("feeMonth=" + feeMonth + ";");
				params.append("currDate=" + DateUtil.formatDate("yyyyMMdd") + ";");
				params.append("branchCode=" + branchCode + ";");
			}
			System.out.println("params:" + params.toString());
		%>
		<% if(StringUtils.isNotBlank(feeMonth)){ %>
			<report:html name="report1" reportFileName="/center/cardMakecardFeeMtotal.raq"	
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