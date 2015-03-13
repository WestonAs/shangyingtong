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
				$('#id_reportType').change(function(){
					var value = $(this).val();
					if(value == 'cardBranch'){
						$('[id^="id_card_Td"]').show();
						$('#id_cardBranch').removeAttr('disabled');
						$('[id^="id_merch_Td"]').hide();
						$('#id_merchant').attr('disabled', 'true');
					} else if(value == 'merch'){
						$('[id^="id_card_Td"]').hide();
						$('#id_cardBranch').attr('disabled', 'true');
						$('[id^="id_merch_Td"]').show();
						$('#id_merchant').removeAttr('disabled');
					} else {
						$('[id^="id_card_Td"]').hide();
						$('#id_cardBranch').attr('disabled', 'true');
						$('[id^="id_merch_Td"]').hide();
						$('#id_merchant').attr('disabled', 'true');
					}
				});
			});
			function downLoadReportFile(path){
		      location.href = CONTEXT_PATH + "/pages/report/generate/monthReportDownload.do?path=" + path;
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
				<s:form action="monthList.do" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">统计月份</td>
							<td>
								<s:textfield id="id_reportDate" name="busiReport.reportDate" onclick="WdatePicker({dateFmt:'yyyyMM'})"/>
							</td>
							<td id="id_card_Td1" align="right">发卡机构</td>
							<td id="id_card_Td2">
								<s:select id="id_cardBranch" name="busiReport.merchantNo" headerKey="" headerValue="--请选择--" list="cardBranchList" 
								  listKey="branchCode" listValue="branchName" ></s:select>
							</td>
							<td height="30" >
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">统计月份</td>
			   <td align="center" nowrap class="titlebg">机构编号</td>
			   <td align="center" nowrap class="titlebg">机构名称</td>
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
			  <td align="center" nowrap>${merchantNo}</td>
			  <td nowrap>${fn:branch(merchantNo)}</td>
			  <td align="center" nowrap>${reportName}</td>
			  <s:if test="centerOrCenterDeptRoleLogined">
			  	<td align="center" nowrap><s:date name="insertTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			  </s:if>
			  <td align="center" nowrap>
				  <f:pspan pid="download_month_report_download">
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