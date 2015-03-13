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
		
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:css href="/css/multiselctor.css"/>
		
		<script>
			$(function(){
				if('${loginRoleType}' == '00'){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20');
				} else if('${loginRoleType}' == '01'){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20', '', '', '${loginBranchCode}');
				}
			});
		
			function downLoadReportFile(reportType, merchantNo, reportDate){
		      location.href = CONTEXT_PATH + "/pages/report/sellProxy/dayReportDownload.do?reportPathSave.reportType="
				+ reportType + "&reportPathSave.merchantNo=" + merchantNo
				+ "&reportPathSave.reportDate=" + reportDate;
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
								<s:textfield id="id_reportDate" name="reportPathSave.reportDate" onclick="WdatePicker()"/>
							</td>
							<s:if test="showCardBranch">
								<td align="right">售卡代理</td>
								<td>
									<s:select  name="reportPathSave.merchantNo" headerKey="" headerValue="--请选择--" list="cardBranchList" 
									  listKey="branchCode" listValue="branchName" onmouseover="FixWidth(this)"></s:select>
								</td>
							</s:if>
							<s:if test="showCardText">
								<td align="right">售卡代理</td>
								<td>
									<s:hidden id="idBranchCode" name="reportPathSave.merchantNo"/>
									<s:textfield id="idBranchCode_sel" name="formMap.branchName" />
								</td>
							</s:if>
							<td height="30">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_REPORT_CARD_DAY_LIST"/>
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
			   <td align="center" nowrap class="titlebg">机构号</td>
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
			  <td align="center" nowrap>${reportTypeName}</td>
			  <td align="center" nowrap>${merchantNo}</td>
			  <td nowrap>${fn:branch(merchantNo)}</td>
			  <td nowrap>${reportName}</td>
			  <s:if test="centerOrCenterDeptRoleLogined">
			  	<td nowrap><s:date name="insertTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			  </s:if>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<a href="javascript:downLoadReportFile('${reportType}','${merchantNo}','${reportDate}');"/>下载</a>
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>