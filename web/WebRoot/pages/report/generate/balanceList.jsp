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
				if(${centerOrCenterDeptRoleLogined}){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20');
				}
			});
			
			function downLoadReportFile(merchantNo, reportDate){
		      location.href = CONTEXT_PATH + "/pages/report/generate/balanceReportDownload.do?formMap.merchantNo="+merchantNo+"&formMap.reportDate=" + reportDate;
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
				<s:form action="balanceList.do" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td id="id_card_Td1" align="right">发卡机构</td>
							<td>
								<s:if test="centerOrCenterDeptRoleLogined">
										<s:hidden id="idBranchCode" name="formMap.merchantNo"/>
										<s:textfield id="idBranchCode_sel" name="formMap.branchName" />
								</s:if>
								<s:elseif test="cardRoleLogined">
									<s:select id="id_cardBranch" name="formMap.merchantNo" 
										list="cardBranchList" listKey="branchCode" listValue="branchName" onmouseover="FixWidth(this)"
										cssClass="{required:true}"> </s:select>
								</s:elseif>
							</td>
							<td align="right">统计日期</td>
							<td>
								<s:textfield id="id_reportDate" name="formMap.reportDate" onclick="WdatePicker({dateFmt:'yyyyMMdd'})" />
							</td>
							<td height="30" >
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="$('#idBranchCode_sel').val('');$('#idBranchCode').val('');FormUtils.reset('searchForm')" name="escape" />
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
			   <td align="center" nowrap class="titlebg">统计日期</td>
			   <td align="center" nowrap class="titlebg">机构编号</td>
			   <td align="center" nowrap class="titlebg">机构名称</td>
			   <td align="center" nowrap class="titlebg">报表名称</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data">
			<tr>
			  <td align="center" nowrap>${reportDate}</td>
			  <td align="center" nowrap>${merchantNo}</td>
			  <td nowrap>${fn:branch(merchantNo)}</td>
			  <td align="center" nowrap>${reportName}</td>
			  <td align="center" nowrap>
				  <f:pspan pid="card_balance_report_down">
				  	<span class="redlink">
				  		<a href="javascript:downLoadReportFile('${merchantNo}', '${reportDate}');">下载</a>
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