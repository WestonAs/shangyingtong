<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="gnete.card.entity.BranchInfo"%>
<%@page import="gnete.card.dao.BranchInfoDAO"%>
<%@page import="flink.util.SpringContext"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="gnete.card.web.report.ICardReportLoad"%>

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
				if(value == '0' || value==''){
					$('td[id^="id_cardBranch_td"]').hide();
					$('#id_branchList').attr('disabled', 'true');
					$('td[id^="id_fenzhi_td"]').hide();
					$('#id_fenzhiList').attr('disabled', 'true');
					$('td[id^="id_agent_td"]').hide();
					$('#id_proxyList').attr('disabled', 'true');
					$('td[id^="id_posProv_td"]').hide();
					$('#id_provList').attr('disabled', 'true');
					$('td[id^="id_posManage_td"]').hide();
					$('#id_manageList').attr('disabled', 'true');
				} else if(value == '1'){
					$('td[id^="id_cardBranch_td"]').show();
					$('#id_branchList').removeAttr('disabled');
					$('td[id^="id_fenzhi_td"]').hide();
					$('#id_fenzhiList').attr('disabled', 'true');
					$('td[id^="id_agent_td"]').hide();
					$('#id_proxyList').attr('disabled', 'true');
					$('td[id^="id_posProv_td"]').hide();
					$('#id_provList').attr('disabled', 'true');
					$('td[id^="id_posManage_td"]').hide();
					$('#id_manageList').attr('disabled', 'true');
				} else if(value == '2'){
					$('td[id^="id_cardBranch_td"]').hide();
					$('#id_branchList').attr('disabled', 'true');
					$('td[id^="id_fenzhi_td"]').show();
					$('#id_fenzhiList').removeAttr('disabled');
					$('td[id^="id_agent_td"]').hide();
					$('#id_proxyList').attr('disabled', 'true');
					$('td[id^="id_posProv_td"]').hide();
					$('#id_provList').attr('disabled', 'true');
					$('td[id^="id_posManage_td"]').hide();
					$('#id_manageList').attr('disabled', 'true');
				} else if(value == '3'){
					$('td[id^="id_cardBranch_td"]').hide();
					$('#id_branchList').attr('disabled', 'true');
					$('td[id^="id_fenzhi_td"]').hide();
					$('#id_fenzhiList').attr('disabled', 'true');
					$('td[id^="id_agent_td"]').show();
					$('#id_proxyList').removeAttr('disabled');
					$('td[id^="id_posProv_td"]').hide();
					$('#id_provList').attr('disabled', 'true');
					$('td[id^="id_posManage_td"]').hide();
					$('#id_manageList').attr('disabled', 'true');
				} else if(value == '4'){
					$('td[id^="id_cardBranch_td"]').hide();
					$('#id_branchList').attr('disabled', 'true');
					$('td[id^="id_fenzhi_td"]').hide();
					$('#id_fenzhiList').attr('disabled', 'true');
					$('td[id^="id_agent_td"]').hide();
					$('#id_proxyList').attr('disabled', 'true');
					$('td[id^="id_posProv_td"]').show();
					$('#id_provList').removeAttr('disabled');
					$('td[id^="id_posManage_td"]').hide();
					$('#id_manageList').attr('disabled', 'true');
				} else if(value == '5'){
					$('td[id^="id_cardBranch_td"]').hide();
					$('#id_branchList').attr('disabled', 'true');
					$('td[id^="id_fenzhi_td"]').hide();
					$('#id_fenzhiList').attr('disabled', 'true');
					$('td[id^="id_agent_td"]').hide();
					$('#id_proxyList').attr('disabled', 'true');
					$('td[id^="id_posProv_td"]').hide();
					$('#id_provList').attr('disabled', 'true');
					$('td[id^="id_posManage_td"]').show();
					$('#id_manageList').removeAttr('disabled');
				}
			}
		</script>
	</head>
	<%
	 	ICardReportLoad operateFee = (ICardReportLoad)SpringContext.getService("reportOperateFee");
	    operateFee.loadReportParams(request);
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
			<span id="_msg_content" style="float: left">${errMsg }</span>
			<a id="_msg_close" href="javascript:hideMsg();" style="float: right;color: red">关闭 X</a>
		</div>
		</c:when>
		<c:otherwise>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<form id="searchForm" action="reportOperateFee.jsp" method="post" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">计费月份</td>
							<td>
								<input type="text" id="id_feeMonth" name="feeMonth" onclick="WdatePicker({dateFmt:'yyyyMM'})" class="{required:true}" value="${param.feeMonth}" />
							</td>
							<c:if test="${showReportType=='Y'}">
							<td align="right">报表类型</td>
							<td>
								<select id="id_reportType" name="reportType" class="{required:true}">
									<option value="">--请选择--</option>
								<c:forEach items="${reportTypeList}" var="u">
									<option value="${u.value}" <c:if test="${param.reportType eq u.value}">selected</c:if>><c:out value="${u.name }"/></option>	
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
							<td id="id_cardBranch_td1" align="right" style="display: none;">发卡机构</td>
							<td id="id_cardBranch_td2" style="display: none;">
								<select id="id_branchList" name="branchCode" class="{required:true}" disabled="disabled">
									<option value="">--请选择--</option>
								<c:forEach items="${branchList}" var="t">
									<option value="${t.branchCode}" <c:if test="${param.branchCode eq t.branchCode}">selected</c:if>><c:out value="${t.branchName }"/></option>	
								</c:forEach>
								</select>
							</td>
							<td id="id_fenzhi_td1" align="right" style="display: none;">分支机构</td>
							<td id="id_fenzhi_td2" style="display: none;">
								<select id="id_fenzhiList" name="fenzhiCode" class="{required:true}" disabled="disabled">
									<option value="">--请选择--</option>
								<c:forEach items="${fenzhiList}" var="t">
									<option value="${t.branchCode}" <c:if test="${param.fenzhiCode eq t.branchCode}">selected</c:if>><c:out value="${t.branchName }"/></option>	
								</c:forEach>
								</select>
							</td>
							<td id="id_agent_td1" align="right" style="display: none;">运营代理</td>
							<td id="id_agent_td2" style="display: none;">
								<select id="id_proxyList" name="proxyCode" class="{required:true}" disabled="disabled">
									<option value="">--请选择--</option>
								<c:forEach items="${proxyList}" var="t">
									<option value="${t.branchCode}" <c:if test="${param.proxyCode eq t.branchCode}">selected</c:if>><c:out value="${t.branchName }"/></option>	
								</c:forEach>
								</select>
							</td>
							<td id="id_posProv_td1" align="right" style="display: none;">出机方</td>
							<td id="id_posProv_td2" style="display: none;">
								<select id="id_provList" name="provCode" class="{required:true}" disabled="disabled">
									<option value="">--请选择--</option>
								<c:forEach items="${provList}" var="t">
									<option value="${t.branchCode}" <c:if test="${param.provCode eq t.branchCode}">selected</c:if>><c:out value="${t.branchName }"/></option>	
								</c:forEach>
								</select>
							</td>
							<td id="id_posManage_td1" align="right" style="display: none;">维护方</td>
							<td id="id_posManage_td2" style="display: none;">
								<select id="id_manageList" name="manageCode" class="{required:true}" disabled="disabled">
									<option value="">--请选择--</option>
								<c:forEach items="${manageList}" var="t">
									<option value="${t.branchCode}" <c:if test="${param.manageCode eq t.branchCode}">selected</c:if>><c:out value="${t.branchName }"/></option>	
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
				</form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<%
			String type =request.getParameter("reportType");
			String feeMonth = request.getParameter("feeMonth");
			String transType = request.getParameter("transType");
			
			// 计费月份
			StringBuffer params = new StringBuffer(128);
			if(StringUtils.isNotBlank(feeMonth)){
				if(feeMonth.substring(4,feeMonth.length()).equals("12")){
					params.append("feeMonthAdd=" + feeMonth.substring(0,4) + "13;");
				} else {
					params.append("feeMonthAdd=;");
				}
				params.append("feeMonth=" + feeMonth + ";");
				// 交易类型
				params.append("transType=" + transType + ";");
			}
			
			// 发卡机构号
			String branchCode = request.getParameter("branchCode");
			if(StringUtils.isNotBlank(branchCode)){
				params.append("branchCode=" + branchCode + ";");
				BranchInfo card = (BranchInfo) branchInfoDAO.findByPk(branchCode);
				params.append("branchName=" + card.getBranchName() + ";");
			}
			
			// 分支机构号
			String fenzhiCode = request.getParameter("fenzhiCode");
			if(StringUtils.isNotBlank(fenzhiCode)){
				params.append("fenzhiCode=" + fenzhiCode + ";");
				BranchInfo card = (BranchInfo) branchInfoDAO.findByPk(fenzhiCode);
				params.append("fenzhiName=" + card.getBranchName() + ";");
			}

			// 代理机构号
			String proxyCode = request.getParameter("proxyCode");
			if(StringUtils.isNotBlank(proxyCode)){
				params.append("proxyCode=" + proxyCode + ";");
				BranchInfo card = (BranchInfo) branchInfoDAO.findByPk(proxyCode);
				params.append("proxyName=" + card.getBranchName() + ";");
			}

			// 机具出机方
			String provCode = request.getParameter("provCode");
			if(StringUtils.isNotBlank(provCode)){
				params.append("provCode=" + provCode + ";");
				BranchInfo card = (BranchInfo) branchInfoDAO.findByPk(provCode);
				params.append("provName=" + card.getBranchName() + ";");
			}

			// 机具维护方
			String manageCode = request.getParameter("manageCode");
			if(StringUtils.isNotBlank(manageCode)){
				params.append("manageCode=" + manageCode + ";");
				BranchInfo card = (BranchInfo) branchInfoDAO.findByPk(manageCode);
				params.append("manageName=" + card.getBranchName() + ";");
			}
			
			String branchReport = (String) request.getAttribute("branchReport");
			if(StringUtils.isNotBlank(branchReport)){
				params.append(branchReport);
			}
			System.out.println("params:" + params.toString());
		%>
		<% if(StringUtils.isNotBlank(type) && type.equals("0")){ %>
		<report:html name="report1" reportFileName="/operateFeeSum.raq"	
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
		<% } else if(StringUtils.isNotBlank(type) && type.equals("1")){ %>
		<report:html name="report2" reportFileName="/operateFeeCard.raq"	
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
		<% } else if(StringUtils.isNotBlank(type) && type.equals("2")){ %>
		<report:html name="report3" reportFileName="/operateFeeFenzhi.raq"	
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
		<% } else if(StringUtils.isNotBlank(type) && type.equals("3")){ %>
		<report:html name="report4" reportFileName="/operateFeeProxy.raq"	
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
		<% } else if(StringUtils.isNotBlank(type) && type.equals("4")){ %>
		<report:html name="report5" reportFileName="/operateFeeProv.raq"	
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
		<% } else if(StringUtils.isNotBlank(type) && type.equals("5")){ %>
		<report:html name="report6" reportFileName="/operateFeeManage.raq"	
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
		<% }if(StringUtils.isNotBlank(feeMonth) && StringUtils.isBlank(type)){ %>
		<report:html name="report6" reportFileName="${reportFile}"	
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
	  </c:otherwise>
	  </c:choose>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>