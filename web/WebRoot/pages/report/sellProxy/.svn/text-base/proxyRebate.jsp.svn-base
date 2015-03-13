<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="flink.util.SpringContext"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="gnete.card.web.report.ICardReportLoad"%>
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
			$(function(){
				var reType = '<%=request.getParameter("reportType")%>';
				if(!isEmpty(reType)){
					showBranch(reType);
				}
				$('#id_reportType').change(function(){
				    var value = $(this).val();				    
					showBranch(value);
				});
			});
						
		    function showBranch(value){
				if(value == '0'){
				    //显示发卡机构名称及其列表其余则隐藏
				    $("#id_skdl_td1").hide();
				    $("#id_skdl_td2").hide();
				    $("#id_cardBranch_td1").show();
					$("#id_cardBranch_td2").show();					
					$('#id_branchList').removeAttr('disabled');
										
				} else if(value == '1'){
					//显示售卡代理名称及其列表其余则隐藏
					$("#id_cardBranch_td1").hide();
					$("#id_cardBranch_td2").hide();
					$("#id_skdl_td1").show();
					$("#id_skdl_td2").show();					
					$('#id_skdlList').removeAttr('disabled');
					
				} else if(value == '2'){
					//显示售卡代理名称及其列表其余则隐藏					
					$("#id_cardBranch_td1").hide();
					$("#id_cardBranch_td2").hide();
					$("#id_skdl_td1").show();
					$("#id_skdl_td2").show();					
					$('#id_skdlList').removeAttr('disabled');
					
				} else if(value == '3'){
					//显示发卡机构名称及其列表其余则隐藏
					$("#id_skdl_td1").hide();
				    $("#id_skdl_td2").hide();
				    $("#id_cardBranch_td1").show();
					$("#id_cardBranch_td2").show();					
					$('#id_branchList').removeAttr('disabled');
					
				} 
			}
		</script>
	</head>
	<%
	 	ICardReportLoad skflReportLoad = (ICardReportLoad)SpringContext.getService("cardProxyReturnReport");
	    skflReportLoad.loadReportParams(request);	    
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
				<form id="searchForm" action="proxyRebate.jsp" method="post" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3  cellpadding="0">
						<caption>售卡代理售卡返利汇总报表</caption>
						<tr>
							<td align="right">计费月份</td>
							<td>
								<input type="text" id="id_feeDate" name="feeDate" onclick="WdatePicker({dateFmt:'yyyyMM'})" class="{required:true}" value="${param.feeDate}" />
							</td>							
							<td align="right">报表类型</td>
							<td>
								<select id="id_reportType" name="reportType" class="{required:true}" onmouseover="FixWidth(this)">
									<option value="">--请选择--</option>
								<c:forEach items="${reportTypeList}" var="u">
									<option value="${u.value}" <c:if test="${param.reportType eq u.value}">selected</c:if>><c:out value="${u.name }"/></option>	
								</c:forEach>
								</select>
							</td>													
						</tr>
						<tr>
							<td id="id_cardBranch_td1" align="right" style="display: none;">发卡机构</td>
							<td id="id_cardBranch_td2" style="display: none;">
								<select id="id_branchList" name="branchCode" class="{required:true}" disabled="disabled" onmouseover="FixWidth(this)">
									<option value="">--请选择--</option>
								<c:forEach items="${branchList}" var="t">
									<option value="${t.branchCode}" <c:if test="${param.branchCode eq t.branchCode }">selected</c:if>><c:out value="${t.branchName }"/></option>	
								</c:forEach>
								</select>
							</td>
							<td id="id_skdl_td1" align="right" style="display: none;">售卡代理</td>
							<td id="id_skdl_td2" style="display: none;">
								<select id="id_skdlList" name="skdlCode" class="{required:true}" disabled="disabled" onmouseover="FixWidth(this)">
									<option value="">--请选择--</option>
								<c:forEach items="${skdlList}" var="t">
									<option value="${t.branchCode}" <c:if test="${param.skdlCode eq t.branchCode}">selected</c:if>><c:out value="${t.branchName }"/></option>	
								</c:forEach>
								</select>
							</td>
							 <td></td>
							<td height="30" colspan="3">							
							<input type="submit" value="生成报表" id="input_btn2"  name="ok" />
							<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<input type="hidden" id="roleType" name="roleType" value="${roleType}" />
					<input type="hidden" id="branchInfo" name="branchInfo" value="${branchInfo}"/>
				</form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="clear">		
		<%
		    //计费日期(必填)
		    String feeDate = request.getParameter("feeDate");
		    //角色类型(必须)
		    String roleType = request.getParameter("roleType");
		    //报表类型(必须)
		    String reportType = request.getParameter("reportType");	
		    //检查页面的基本提交参数
		    boolean checkSubmit = (StringUtils.isEmpty(feeDate) || StringUtils.isEmpty(roleType) || StringUtils.isEmpty(reportType)); 
		    //System.out.println("submitParams==={" + feeDate + "," + roleType + "," + reportType + "}");	
		%>
		<% if(!checkSubmit) { %>
			 <%
			    //可能提交的页面信息	     
			    String branchCode = request.getParameter("branchCode");	
			    String skdlCode = request.getParameter("skdlCode");	
			    String branchInfo = request.getParameter("branchInfo");    
			    request.setAttribute("roleType",roleType);
			    //模板文件查询参数
			    String[] queryParams = null;
			    //如果是中心或部门角色
			    if(StringUtils.isEmpty(branchInfo)) {
	               //当选择发卡机构时
			       if(StringUtils.isNotEmpty(branchCode))  {
			         queryParams=  skflReportLoad.getReportQueryParams(roleType,reportType,new String[] {feeDate,branchCode});
			       } 
			       //当选择售卡代理时
			       else if(StringUtils.isNotEmpty(skdlCode)) {
			         queryParams = skflReportLoad.getReportQueryParams(roleType,reportType,new String[] {feeDate,skdlCode});
			       }		      
			    } 
			    //否则是发卡机构或售卡代理的选择
			    else {
			       if(StringUtils.isNotEmpty(branchCode)) {
			         queryParams = skflReportLoad.getReportQueryParams(roleType,reportType,new String[] {feeDate,branchInfo,branchCode});
			       } else if(StringUtils.isNotEmpty(skdlCode)) {
			         queryParams = skflReportLoad.getReportQueryParams(roleType,reportType,new String[] {feeDate,branchInfo,skdlCode});
			       }		       		      
			    }
			    boolean checkParams = ArrayUtils.isEmpty(queryParams);		    
			 %>
			 <% if(!checkParams){ %> 
				 <report:html name="skflReport" reportFileName="<%=queryParams[0]%>"	
				    params="<%=queryParams[1] %>"
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
	  </c:otherwise>
	  </c:choose>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>