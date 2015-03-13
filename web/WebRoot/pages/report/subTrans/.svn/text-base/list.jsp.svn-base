<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="flink.util.SpringContext"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="gnete.card.web.report.ICardReportLoad"%>
<%@page import="flink.util.DateUtil"%>

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
		<script>
			$(function(){
				var fenzhiCode = $('#id_fenzhiList').val();
				if(!isEmpty(fenzhiCode)){ 
					$('#id_branchList').load(CONTEXT_PATH + '/pages/branch/loadBranch.do', {'callId':callId(), 'fenzhiCode':fenzhiCode}
						,function(){
								setTimeout('SysStyle.initSelect()', 100);
								var branchCode = "${param.branchCode}";//发卡机构url参数
								if(!isEmpty(branchCode)){
									$('#id_branchList').val(branchCode);
								}
						}
					);
				}
				
				
				$('#id_fenzhiList').change(function(){
					var value = $('#id_fenzhiList').val();
					if(isEmpty(value)){ 
						return false;
					}
					$('#id_branchList').load(CONTEXT_PATH + '/pages/branch/loadBranch.do', {'callId':callId(), 'fenzhiCode':value}
						, function(){setTimeout('SysStyle.initSelect()', 100);}
					);
				});
			});
		</script>
		
	</head>
    <%
	 	ICardReportLoad operateFee = (ICardReportLoad)SpringContext.getService("subTransShare");
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
				<form id="searchForm" action="list.jsp" method="post" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td id="id_fenzhi_td1" align="right">分支机构</td>
						    <c:if test="${showFenzhi}">
								<td id="id_fenzhi_td2" >
									<select id="id_fenzhiList" name="fenzhiCode" class="{required:true}" style="width: auto;">
										<option value="">--请选择--</option>
										<c:forEach items="${fenzhiList}" var="t">
											<option value="${t.branchCode}" <c:if test="${param.fenzhiCode eq t.branchCode}">selected</c:if>><c:out value="${t.branchName }"/></option>	
										</c:forEach>
									</select>
								</td>
							</c:if>
							<c:if test="${showCard}">
								<td id="id_fenzhi_td3" >
								     <!-- s:hidden id="id_fenzhiCode" name="fenzhiCode"/-->
								     <!-- s:textfield  id="id_fenzhiName" name="fenzhiName"  cssClass="readonly" readonly="true"  /-->
								     <input id="id_fenzhiCode" type="hidden" name="fenzhiCode" value="${fenzhiCode}" />
								     <input type="text"  id="id_fenzhiName" name="fenzhiName"  cssClass="readonly" readonly="true"  value="${fenzhiName}" />
								</td>
							</c:if>
						</tr>
						<tr>
							<td id="id_cardBranch_td1" align="right">发卡机构</td>
							<c:if test="${showFenzhi}">
								<td id="id_cardBranch_td2" >
									<select id="id_branchList" name="branchCode" style="width: auto;">
										<option value="">--请选择--</option>
									</select>
								</td>
							</c:if>
							<c:if test="${showCard}">
								<td id="id_cardBranch_td3" >
								     <!-- s:hidden id="id_branchCode" name="branchCode"/-->
								     <!-- s:textfield  id="id_cardBranchName" name="branchName"  cssClass="readonly" readonly="true"  /-->
								     <input id="id_branchCode" type="hidden" name="branchCode" value="${branchCode}" />
								     <input type="text"  id="id_cardBranchName" name="branchName"  cssClass="readonly" readonly="true" value="${branchName}"  />
								</td>
							</c:if>
						</tr>
						<tr>
							<td id="id_startEnd_td1"  align="right" >日期范围</td>
							<td id="id_startEnd_td2" >
								<input type="text" id="startDate" name="startDate" onclick="getStartDate();" class="{required:true}" style="width:68px;" value="${param.startDate}" />&nbsp;-&nbsp;
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
			// 分支机构号
			String fenzhiCode = request.getParameter("fenzhiCode");
			if(StringUtils.isNotBlank(fenzhiCode)){
				params.append("chlCode=" + fenzhiCode + ";");
			}
			// 发卡机构号
			String branchCode = request.getParameter("branchCode");
			if(StringUtils.isNotBlank(branchCode)){
				params.append("cardIssuer=" + branchCode + ";");
			}
			//时间范围
			String settDateStart = request.getParameter("startDate");
			String settDateEnd = request.getParameter("endDate");
			params.append("settDateStart=" + settDateStart + ";");
			params.append("settDateEnd=" + settDateEnd + ";");
			
			params.append("currDate=" + DateUtil.formatDate("yyyyMMdd") + ";");
						
			System.out.println("params:" + params.toString());
		%>
		<% if(StringUtils.isNotBlank(fenzhiCode) && StringUtils.isNotBlank(settDateStart) && StringUtils.isNotBlank(settDateEnd) ){ %>
			<report:html name="report1" reportFileName="/subTrans/subTransDSum.raq"	
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