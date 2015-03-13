<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="flink.util.SpringContext"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="gnete.card.web.report.ICardReportLoad"%>
<%@page import="flink.util.DateUtil"%>

<%@ taglib uri="/WEB-INF/runqianReport4.tld" prefix="report"%>

<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<%
    ICardReportLoad operateFee = (ICardReportLoad)SpringContext.getService("fenzhiOperateFeeShare");
    operateFee.loadReportParams(request);
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
				var showfenzhi = '<%=request.getAttribute("showfenzhi")%>';
				if(showfenzhi == 'true'){
					var fenzhiCode = '<%=request.getAttribute("fenzhicode")%>';
					var fenzhiName = '<%=request.getAttribute("fenzhiname")%>';
					$('#id_fenzhiName').val(fenzhiName);
					$('#id_fenzhiCode').val(fenzhiCode);
					$('#id_fenzhiName').attr('readonly', 'true').addClass('readonly');
				} else {
					Selector.selectBranch('id_fenzhiName', 'id_fenzhiCode', true, '01', changeFenzhiCode);
				}

				if (!isEmpty($('#id_fenzhiCode').val()) ){
				    Selector.selectBranch('id_branchCodeName', 'id_branchCode', true, '20', '', '', $('#id_fenzhiCode').val());
				}
				
				//报表类型
				changeReportType();
				$('#id_reportType').change(changeReportType);
			});

			function changeFenzhiCode(){
				var fenzhiCode = $('#id_fenzhiCode').val();
				if(!isEmpty(fenzhiCode)){
					$('#id_branchCodeName').removeMulitselector();//移除选择器
					$('#id_branchCodeName').val('');
					$('#id_branchCode').val('');
				    Selector.selectBranch('id_branchCodeName', 'id_branchCode', true, '20', '', '', fenzhiCode);
				}
			}
			
			function changeReportType(){
				var reportType = $('#id_reportType').val();
				if(reportType == '2' || reportType == '3' ){
				    $('#id_feeMonthDate_td1').show();
					$('#id_feeMonthDate_td2').show();
					$('#id_feeMonth').removeAttr('disabled');
            
					$('#id_feeMonthStartEnd_td1').hide();
					$('#id_feeMonthStartEnd_td2').hide();
					$('#feeMonthStart').attr('disabled', 'true');
					$('#feeMonthEnd').attr('disabled', 'true');
				}else{
					$('#id_feeMonthDate_td1').hide();
					$('#id_feeMonthDate_td2').hide();
					$('#id_feeMonth').attr('disabled', 'true');
            
					$('#id_feeMonthStartEnd_td1').show();
					$('#id_feeMonthStartEnd_td2').show();
					$('#feeMonthStart').removeAttr('disabled');
					$('#feeMonthEnd').removeAttr('disabled');
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
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<form id="searchForm" action="feeShare.jsp" method="post" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
						    <td align="right">报表类型</td>
							<td >
								<select id="id_reportType" name="reportType" class="{required:true}" style="width: auto;">
									<option value="">--请选择--</option>
								<c:forEach items="${reportTypeList}" var="u">
									<option value="${u.value}" <c:if test="${param.reportType eq u.value}">selected</c:if>><c:out value="${u.name }"/></option>	
								</c:forEach>
								</select>
							</td>
							<td id="id_fenzhi_td1"  width="80" height="30" align="right" >分支机构</td>
							<td id="id_fenzhi_td2">
								<input  type="hidden" id="id_fenzhiCode" name="fenzhiCode" value="${param.fenzhiCode }" class="{required:true}"/>
								<input  type="text"  id="id_fenzhiName" name="fenzhiName" value="${param.fenzhiName }" class="{required:true}"/>
							</td>
						</tr>
						<tr>
							<!-- td align="right">计费月份</td>
							<td>
								<input type="text" id="id_feeMonth" name="feeMonth" onclick="WdatePicker({dateFmt:'yyyyMM'})" class="{required:true}" value="${param.feeMonth}" />
							</td -->
							<td  id="id_feeMonthDate_td1"  align="right">计费月份</td>
							<td id="id_feeMonthDate_td2" >
								<input type="text" id="id_feeMonth" name="feeMonth" onclick="WdatePicker({dateFmt:'yyyyMM'})" class="{required:true}" value="${param.feeMonth}" />
							</td>
							<td id="id_feeMonthStartEnd_td1"  align="right" style="display: none;">日期范围</td>
							<td id="id_feeMonthStartEnd_td2" style="display: none;">
								<input type="text" id="feeMonthStart" name="feeMonthStart" onclick="WdatePicker({dateFmt:'yyyyMM'})" class="{required:true}" style="width:68px;" disabled="true" value="${param.feeMonthStart}" />&nbsp;-&nbsp;
								<input type="text" id="feeMonthEnd" name="feeMonthEnd" onclick="WdatePicker({dateFmt:'yyyyMM'})" class="{required:true}" style="width:68px;" disabled="true" value="${param.feeMonthEnd}" />
							</td>
							<td id="id_cardBranch_td1" align="right">发卡机构</td>
							<td id="id_cardBranch_td2" >
							    <input  type="hidden"  id="id_branchCode" name="branchCode" value="${param.branchCode }" />
								<input  type="text"   id="id_branchCodeName" name="branchCodeName" value="${param.branchCodeName }"/>
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
			String type =request.getParameter("reportType");
			String feeMonth = request.getParameter("feeMonth");
			String feeMonthStart = request.getParameter("feeMonthStart");
			String feeMonthEnd = request.getParameter("feeMonthEnd");
			
			// 计费月份
			StringBuffer params = new StringBuffer(128);
			if(StringUtils.isNotBlank(type) && ( type.equals("2") || type.equals("3") )){
				if(StringUtils.isNotBlank(feeMonth)){
					if(feeMonth.substring(4,feeMonth.length()).equals("12")){
						params.append("feeMonthAdd=" + feeMonth.substring(0,4) + "13;");
					} else {
						params.append("feeMonthAdd=;");
					}
					params.append("feeMonth=" + feeMonth + ";");
					params.append("currDate=" + DateUtil.formatDate("yyyyMMdd") + ";");
				}
			}else {
			    if(StringUtils.isNotBlank(feeMonthStart) && StringUtils.isNotBlank(feeMonthEnd)){
					params.append("feeMonthStart=" + feeMonthStart + ";");
					params.append("feeMonthEnd=" + feeMonthEnd + ";");
					params.append("currDate=" + DateUtil.formatDate("yyyyMMdd") + ";");
				}
			}
			
			// 发卡机构号
			String branchCode = request.getParameter("branchCode");
			if(StringUtils.isNotBlank(branchCode)){
				params.append("cardBranchCode=" + branchCode + ";");
			}
			
			// 分支机构号
			String fenzhiCode = request.getParameter("fenzhiCode");
			if(StringUtils.isNotBlank(fenzhiCode)){
				params.append("chlCode=" + fenzhiCode + ";");
			}

			System.out.println("params:" + params.toString());
		%>
		<% if(StringUtils.isNotBlank(type) && type.equals("0")){ %>
			<report:html name="report1" reportFileName="/fenzhi/optFeeMSum.raq"	
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
			<report:html name="report2" reportFileName="/fenzhi/optFeeMDetail.raq"	
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
			<report:html name="report2" reportFileName="/fenzhi/optFeeMDetailAdjust.raq"	
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
			<report:html name="report3" reportFileName="/fenzhi/costFeeMSum.raq"	
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
			<report:html name="report4" reportFileName="/fenzhi/costFeeMDetail.raq"	
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