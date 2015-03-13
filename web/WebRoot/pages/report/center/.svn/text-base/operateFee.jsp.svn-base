<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="flink.util.CommonHelper"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="flink.util.SpringContext"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="gnete.card.web.report.ICardReportLoad"%>
<%@page import="flink.util.DateUtil"%>

<%@ taglib uri="/WEB-INF/runqianReport4.tld" prefix="report"%>

<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

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
		
		<script>

		$(function(){
			// 选择分支机构
			Selector.selectBranch('id_clusterBranchName', 'id_clusterBranchCode', true, '01');
			
			var reportType = $('#id_reportType').val();
			if(isEmpty(reportType)){
				$('#input_btn3').hide();
				$('#input_btn4').hide();
				$('#input_btn5').hide();
			}
			//查询类型显示处理
			searchChg();
			//计费月份显示处理
			reportChg();
			
			$('#id_reportType').change(reportChg);		
			
			//按查询类型处理显示
			$('#id_reportSearchType').change(searchChg);
		});
		
		function dateSelect(){
		    var reportType = $('#id_reportType').val();
		    if(reportType == '2' || reportType == '3' ){
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
			if(reportType == '1'){
				$('#id_feeMonth').removeAttr('readonly').removeClass('readonly');
				if(feeMonthEnd == '13'){
				    $('#id_feeMonth').val('');
			    }
				$('#input_btn3').show();
				$('#input_btn4').show();
				$('#input_btn5').show();
			}else if(reportType == '2'){//年调整
			    if('' == feeMonth || feeMonthEnd != '13'){
				     $('#id_feeMonth').val(yearAdjustDate);
			    }
			    $('#id_feeMonth').attr('readonly', 'true').addClass('readonly');
			    $('#input_btn3').show();
				$('#input_btn4').show();
				$('#input_btn5').show();
			}else if(reportType == '0') {
				$('#id_feeMonth').removeAttr('readonly').removeClass('readonly');
				if(feeMonthEnd == '13'){
			        $('#id_feeMonth').val('');
			    }
				$('#input_btn3').hide();
				$('#input_btn4').hide();
				$('#input_btn5').hide();
			}else if(reportType == '3') {
				if('' == feeMonth || feeMonthEnd != '13'){
				     $('#id_feeMonth').val(yearAdjustDate);
			    }
			    $('#id_feeMonth').attr('readonly', 'true').addClass('readonly');
				$('#input_btn3').hide();
				$('#input_btn4').hide();
				$('#input_btn5').hide();
			}
		}
		
		function searchChg(){
			var reportSearchType = $('#id_reportSearchType').val();
			// 分支机构
			if(reportSearchType == '1'){
				$('#id_clusterBranch_td1').show();
				$('#id_clusterBranch_td2').show();
				$('#id_clusterBranchName').removeAttr('disabled');
				$('#id_clusterBranchCode').removeAttr('disabled');
				$('#id_clusterId').attr('disabled', 'true');
				$('#id_clusterId_td1').hide();
				$('#id_clusterId_td2').hide();
			}
			// 集群
			else if(reportSearchType == '0'){
				$('#id_clusterId_td1').show();
				$('#id_clusterId_td2').show();
				$('#id_clusterId').removeAttr('disabled');
				$('#id_clusterBranchName').attr('disabled', 'true');
				$('#id_clusterBranchCode').attr('disabled', 'true');
				$('#id_clusterBranch_td1').hide();
				$('#id_clusterBranch_td2').hide();
			}
			else if(reportSearchType == ''){
				$('#id_clusterBranch_td1').hide();
				$('#id_clusterBranch_td2').hide();
				$('#id_clusterId_td1').hide();
				$('#id_clusterId_td2').hide();
				$('#id_clusterId').attr('disabled', 'true');
				$('#id_clusterBranchName').attr('disabled', 'true');
				$('#id_clusterBranchCode').attr('disabled', 'true');
			}
		}
		
		function check(){
		    var reportType = $('#id_reportType').val();
		    if(reportType == '2'){
		        var feeMonth = $('#id_feeMonth').val();
		        var sub = feeMonth.substring(feeMonth.length-2,feeMonth.length);
		        if(sub != '13'){
		            alert("年终调整报表的月份必须为13");
		        }
		    }
	    }
		
		function generateReport(){
		    //check();
			$('#searchForm').attr('action', 'operateFee.jsp').submit();
		}

		// 生成交易数据源
		function generateExcel() {
			window.parent.unblock();
			var chlCode = $('#id_clusterBranchCode').val();
			if(isEmpty(chlCode)){
				alert("请先选择分支机构再生成数据源。");
			}
			else {
				/*if($('#searchForm').validate().form()){
					if (!confirm('是否生成数据源？如果数据太大，请先耐心等待！')) {
						return;
					}
					$('#searchForm').attr('action', CONTEXT_PATH + '/pages/report/center/operateFeeTransSrc.do').submit();
					$('#input_btn3').attr('disabled', 'true');
					$("#loadingBarDiv").css("display","inline");
					//$("#userbox").css("display","none");
				}*/
				if (!confirm('是否生成数据源？如果数据太大，请先耐心等待！')) {
					return;
				}
				else {
					$('#searchForm').attr('action', CONTEXT_PATH + '/pages/report/center/operateFeeTransSrc.do').submit();
				}
			}
		}

		// 生成会员数
		function generateMemb() {
			var chlCode = $('#id_clusterBranchCode').val();
			if(isEmpty(chlCode)){
				alert("请先选择分支机构再生成数据源。");
			}
			else {
				if (!confirm('是否生成数据源？如果数据太大，请先耐心等待！')) {
					return;
				}
				else {
					$('#searchForm').attr('action', CONTEXT_PATH + '/pages/report/center/operateFeeMembSrc.do').submit();
				}
			}
			
		}

		// 生成折扣卡会员数
		function generateDiscntMemb() {
			var chlCode = $('#id_clusterBranchCode').val();
			if(isEmpty(chlCode)){
				alert("请先选择分支机构再生成数据源。");
			}
			else {
				if (!confirm('是否生成数据源？如果数据太大，请先耐心等待！')) {
					return;
				}
				else {
					$('#searchForm').attr('action', CONTEXT_PATH + '/pages/report/center/operateFeeDiscntMembSrc.do').submit();
				}
			}
		}
		</script>
	</head>
	<%
	 	ICardReportLoad operateFee = (ICardReportLoad)SpringContext.getService("centerOperateFee");
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
		
		<div id="loadingBarDiv"	style="display: none; width: 100%; height: 100%">
			<table width=100% height=100%>
				<tr>
					<td align=center >
						<center>
							<img src="../../../images/ajax_saving.gif" align="middle">
							<div id="processInfoDiv"
								style="font-size: 15px; font-weight: bold">
								正在处理中，请稍候...
							</div>
							<br>
							<br>
							<br>
						</center>
					</td>
				</tr>
			</table>
		</div>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<form id="searchForm" action="operateFee.jsp" method="post" class="validate-tip">
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
							<td align="right" >查询类型</td>
							<td>
								<select id="id_reportSearchType" name="reportSearchType" >
									<option value="">--请选择--</option>
									<c:forEach items="${reportSearchTypeList}" var="u">
										<option value="${u.value}" <c:if test="${param.reportSearchType eq u.value}">selected</c:if>><c:out value="${u.name }"/></option>	
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right">计费月份</td>
							<td>
								<input type="text"  id="id_feeMonth" name="feeMonth" onclick="dateSelect();" class="{required:true}" value="${param.feeMonth}" />
							</td>
							<td id="id_clusterBranch_td1"  align="right"  style="display: none" >分支机构</td>
							<td id="id_clusterBranch_td2"  style="display: none">
								<input  type="hidden"  id="id_clusterBranchCode" name="clusterBranchCode" value="${param.clusterBranchCode}" class="{required:true}" disabled="true"/>
								<input  type="text"  id="id_clusterBranchName" name="clusterBranchName" value="${param.clusterBranchName}" class="{required:true}" disabled="true"/>
							</td>
							<td id="id_clusterId_td1"  align="right" style="display: none" >集群</td>
							<td id="id_clusterId_td2"  style="display: none">
								<select id="id_clusterId" name="clusterId" class="{required:true}"   onmouseover="FixWidth(this)" disabled="true">
									<option value="">--请选择--</option>
									<c:forEach items="${clusterInfoList}" var="t">
										<option value="${t.clusterid}" <c:if test="${param.clusterId eq t.clusterid}">selected</c:if>><c:out value="${t.clustername }"/></option>	
									</c:forEach>
								</select>
							</td>
							</tr>
							<tr>
							<td height="30">
							</td>
							<td height="30" colspan="3">
							<input type="button" value="生成报表" id="input_btn2" name="ok" onclick="generateReport();"/>
							<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							<input style="margin-left:30px;" type="button" value="导出交易数据源" onclick="generateExcel();" id="input_btn3" />						
							<input style="margin-left:30px;" type="button" value="导出会员数" onclick="generateMemb();" id="input_btn4" />						
							<input style="margin-left:30px;" type="button" value="导出折扣卡会员数" onclick="generateDiscntMemb();" id="input_btn5" />						
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
			String reportSearchType =request.getParameter("reportSearchType");//查询类型
			String feeMonth = request.getParameter("feeMonth");
			//String transType = request.getParameter("transType");
			
			// 计费月份
			StringBuffer params = new StringBuffer(128);
			if(StringUtils.isNotBlank(feeMonth)){
				if(feeMonth.substring(4,feeMonth.length()).equals("12")){
					params.append("feeMonthAdd=" + feeMonth.substring(0,4) + "13;");
				} else {
					params.append("feeMonthAdd=;");
				}
				params.append("currDate=" + DateUtil.formatDate("yyyyMMdd") + ";");
				params.append("feeMonth=" + feeMonth + ";");
				// 交易类型
				//params.append("transType=" + transType + ";");
			}
			
			// 分支机构号
			String clusterBranchCode = request.getParameter("clusterBranchCode");
			String clusterId = request.getParameter("clusterId");
			if(StringUtils.isNotBlank(reportSearchType) && reportSearchType.equals("1")){//单个机构
				params.append("chlCode=" + clusterBranchCode + ";");
			}else if(StringUtils.isNotBlank(reportSearchType) && reportSearchType.equals("0")){//集群
				HashMap clusterBranchMap = (HashMap)request.getAttribute("clusterBranchMap");
				if(null != clusterBranchMap && null != clusterBranchMap.get(Long.valueOf(clusterId))){
				    params.append("chlCode=" + clusterBranchMap.get(Long.valueOf(clusterId)) + ";");
				}else{//集群为空
				    params.append("chlCode= ;");
				}
			}else{//所有集群机构
			    String clusterBranchList = (String)request.getAttribute("clusterBranchListStr");
			    if(StringUtils.isNotBlank(clusterBranchList)){
			        params.append("chlCode=" + clusterBranchList + ";");
			    }
			}

			System.out.println("params:" + params.toString());
		%>
		<% if(StringUtils.isNotBlank(type) && type.equals("0")){ %>
			<report:html name="report1" reportFileName="/center/optFeeSum.raq"	
			    params="<%=params.toString() %>"
			    funcBarFontFace="宋体"               
				funcBarFontSize="14px"   	
				needSaveAsExcel="yes"
				needSaveAsPdf="yes"
				needSaveAsWord="yes"
				pdfExportStyle="text,0"
				excelPageStyle="1"
				needPrint="yes"
				funcBarLocation="bottom"
				width="-1"
				useCache="false"
			/>
		<% } else if(StringUtils.isNotBlank(type) && type.equals("1")){ %>
		<report:html name="report2" reportFileName="/center/optFeeDetail.raq"	
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
			<report:html name="report2" reportFileName="/center/optFeeDetailAdjust.raq"	
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
			<report:html name="report2" reportFileName="/center/optFeeSumAdjust.raq"	
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