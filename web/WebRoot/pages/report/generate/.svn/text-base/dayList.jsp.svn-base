<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
				var reportType = $('#id_reportType').val();
				showReport(reportType);
				$('#id_reportType').change(function(){
					var value = $(this).val();
					showReport(value);
				});
			});
			
			function showReport(reportType){
				if(!isEmpty(reportType)){
					$('[id^="id_branch_Td"]').show();
					if(reportType == 'cardBranch'){ // 看的是发卡机构报表时
						$('#id_branch_Td1').html('发卡机构');
						$('#id_cardBranch').show();
						$('#id_cardBranch').removeAttr('disabled');
						$('#id_merchant').hide();
						$('#id_merchant').attr('disabled', 'true');
						$('#id_proxyBranch').hide();
						$('#id_proxyBranch').attr('disabled', 'true');
					} else if(reportType == 'merch'){ //看的是商户报表时
						$('#id_branch_Td1').html('商户');
						$('#id_cardBranch').hide();
						$('#id_cardBranch').attr('disabled', 'true');
						$('#id_merchant').show();
						$('#id_merchant').removeAttr('disabled');
						$('#id_proxyBranch').hide();
						$('#id_proxyBranch').attr('disabled', 'true');
					} else {
						$('#id_branch_Td1').html('售卡代理');
						$('#id_cardBranch').hide();
						$('#id_cardBranch').attr('disabled', 'true');
						$('#id_merchant').hide();
						$('#id_merchant').attr('disabled', 'true');
						$('#id_proxyBranch').show();
						$('#id_proxyBranch').removeAttr('disabled');
					}
				} else {
					$('[id^="id_branch_Td"]').hide();
					$('#id_cardBranch').hide();
					$('#id_cardBranch').attr('disabled', 'true');
					$('#id_merchant').hide();
					$('#id_merchant').attr('disabled', 'true');
					$('#id_proxyBranch').hide();
					$('#id_proxyBranch').attr('disabled', 'true');
				}
			}
			
			function downLoadReportFile(path){
		      location.href = CONTEXT_PATH + "/pages/report/generate/dayReportDownload.do?path=" + path;
		   }
		</script>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="dayList.do" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">报表日期</td>
							<td>
								<s:textfield id="id_reportDate" name="preWorkDate" onclick="WdatePicker()"/>
							</td>
							<td align="right">报表类型</td>
							<td>
								<s:select id="id_reportType" name="busiReport.reportType" headerKey="" headerValue="--请选择--" list="reportTypeList" listKey="value" 
									listValue="name"></s:select>
							</td>
							<td id="id_branch_Td1" align="right" style="display: none;">机构（商户）</td>
							<td id="id_branch_Td2" style="display: none;">
								<s:select id="id_cardBranch" name="busiReport.merchantNo" headerKey="" headerValue="--请选择--" list="cardBranchList" 
								  listKey="branchCode" listValue="branchName" cssStyle="display: none;" disabled="true"></s:select>
								<s:select id="id_merchant" name="busiReport.merchantNo" headerKey="" headerValue="--请选择--" list="merchList" 
								  listKey="merchId" listValue="merchName" cssStyle="display: none;" disabled="true"></s:select>
								<s:select id="id_proxyBranch" name="busiReport.merchantNo" headerKey="" headerValue="--请选择--" list="proxyList" 
								  listKey="branchCode" listValue="branchName" cssStyle="display: none;" disabled="true"></s:select>
							</td>
						</tr>
						<tr>
							<td height="30" align="right"></td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_REPORT_DAY_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">报表日期</td>
			   <td align="center" nowrap class="titlebg">报表类型</td>
			   <td align="center" nowrap class="titlebg">机构（商户）号</td>
			   <td align="center" nowrap class="titlebg">机构（商户）名称</td>
			   <td align="center" nowrap class="titlebg">报表名称</td>
			   <s:if test="centerOrCenterDeptRoleLogined">
			   	<td align="center" nowrap class="titlebg">插入时间</td>
			   </s:if>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data">
			<tr>
			  <td align="center" nowrap>${reportDate}</td>
			  <td align="center" nowrap>${reportTypeName}</td>
			  <td align="center" nowrap>${merchantNo}</td>
			  <td nowrap>${fn:branch(merchantNo)}${fn:merch(merchantNo)}</td>
			  <td nowrap>${reportName}</td>
			  <s:if test="centerOrCenterDeptRoleLogined">
			  	<td nowrap><s:date name="insertTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			  </s:if>
			  <td align="center" nowrap>
			  	<f:pspan pid="download_day_report_download">
				  	<span class="redlink">
				  		<a href="javascript:downLoadReportFile('${filePath}');">下载</a>
				  	</span>
			  	</f:pspan>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>