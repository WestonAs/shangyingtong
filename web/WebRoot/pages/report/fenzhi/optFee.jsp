<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="gnete.card.entity.BranchInfo"%>
<%@page import="gnete.card.dao.BranchInfoDAO"%>
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
				//var reType = '<%=request.getParameter("reportType")%>';
				//if(!isEmpty(reType)){
				//	showBranch(reType);
				//}
				//$('#id_reportType').change(function(){
				//	var value = $(this).val();
				//	showBranch(value);
				//});
				
				//计费月份显示处理
				reportChg();
				
				$('#id_reportType').change(reportChg);	
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
			
			function dateSelect(){
			    var reportType = $('#id_reportType').val();
			    if(reportType == '2'){
			        javascript:WdatePicker({dateFmt:'yyyy13'});
			    }else{
			        javascript:WdatePicker({dateFmt:'yyyyMM'});
			    }
		    }
		    
		    function reportChg(){
			    var date = new Date();
			    var year = date.getFullYear();
			    var yearAdjustDate = year+'13';
			    
				var reportType = $('#id_reportType').val();
				var feeMonth = $('#id_feeMonth').val();
				var feeMonthEnd = feeMonth.substring(feeMonth.length-2,feeMonth.length);
				// 积分卡业务平台运营手续费收入明细报表
				if(reportType == '2'){//年调整
				    if('' == feeMonth || feeMonthEnd != '13'){
				        $('#id_feeMonth').val(yearAdjustDate);
				    }
				    $('#id_feeMonth').attr('readonly', 'true').addClass('readonly');
				}else {
					$('#id_feeMonth').removeAttr('readonly').removeClass('readonly');
					if(feeMonthEnd == '13'){
				        $('#id_feeMonth').val('');
				    }
				}
			}
		</script>
	</head>
	<%
	 	ICardReportLoad operateFee = (ICardReportLoad)SpringContext.getService("fenzhiOperateFeeImpl");
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
				<form id="searchForm" action="optFee.jsp" method="post" class="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">报表类型</td>
							<td>
								<select id="id_reportType" name="reportType" class="{required:true}" style="width: auto;">
									<option value="">--请选择--</option>
									<c:forEach items="${reportTypeList}" var="u">
										<option value="${u.value}" <c:if test="${param.reportType eq u.value}">selected</c:if>><c:out value="${u.name }"/></option>	
									</c:forEach>
								</select>
							</td>
					    </tr>
						<tr>
							<td align="right">计费月份</td>
							<td>
								<input type="text"  id="id_feeMonth" name="feeMonth" onclick="dateSelect();" class="{required:true}" value="${param.feeMonth}" />
							</td>
							<c:if test="${showFenzhi}">
							<td align="right">分支机构</td>
							<td>
								<select id="id_fenzhiList" name="fenzhiCode" class="{required:true}" onmouseover="FixWidth(this)">
									<option value="">--请选择--</option>
								<c:forEach items="${fenzhiList}" var="t">
									<option value="${t.branchCode}" <c:if test="${param.fenzhiCode eq t.branchCode}">selected</c:if>><c:out value="${t.branchName }" /></option>	
								</c:forEach>
								</select>
							</td>
							</c:if>
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
			boolean showFenzhi = (Boolean)request.getAttribute("showFenzhi");
			String feeMonth = request.getParameter("feeMonth");
			
			StringBuffer params = new StringBuffer(128);
			// 计费月份
			if(StringUtils.isNotBlank(feeMonth)){
				if(feeMonth.substring(4,feeMonth.length()).equals("12")){
					params.append("feeMonthAdd=" + feeMonth.substring(0,4) + "13;");
				} else {
					params.append("feeMonthAdd=;");
				}
				params.append("feeMonth=" + feeMonth + ";");
				params.append("currDate=" + DateUtil.formatDate("yyyyMMdd") + ";");
			}
			
			// 如果显示分支机构列表，则分支机构代理由页面传入
			if(showFenzhi){
				// 分支机构号
				String fenzhiCode = request.getParameter("fenzhiCode");
				if(StringUtils.isNotBlank(fenzhiCode)){
					params.append("chlCode=" + fenzhiCode + ";");
					BranchInfo fenzhi = (BranchInfo) branchInfoDAO.findByPk(fenzhiCode);
					params.append("chlName=" + fenzhi.getBranchName() + ";");
				}
			} else {
				BranchInfo fenzhi = (BranchInfo)request.getAttribute("fenzhi");
				params.append("chlCode=" + fenzhi.getBranchCode() + ";");
				params.append("chlName=" + fenzhi.getBranchName() + ";");
			}
			
			System.out.println("params:" + params.toString());
		%>
		<%  if(StringUtils.isNotBlank(type) && type.equals("1")){ %>
			<report:html name="report2" reportFileName="/fenzhi/chlFeeMSum.raq"	
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
			<report:html name="report2" reportFileName="/fenzhi/chlFeeMSumAdjust.raq"	
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