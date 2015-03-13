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
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<script>
			$(function(){
				if('${loginRoleType}' == '00'){
					$('#id_branchCode').val('');
					Selector.selectBranch('id_branchCodeName', 'id_branchCode', true, '20');
				} else if('${loginRoleType}' == '01'){
					$('#id_branchCode').val('');
					Selector.selectBranch('id_branchCodeName', 'id_branchCode', true, '20', '', '', '${loginBranchCode}');
				}				
			})

			function downLoadReportFile(branchCode, checkDate, fileName) {
				location.href = CONTEXT_PATH
						+ "/pages/accountCheckFileDownload/download.do?formMap.branchCode="
						+ merchantNo + "&formMap.checkDate=" + checkDate
						+ "&formMap.fileName=" + fileName;
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
				<s:form action="list.do" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
						<s:if test="loginRoleType == '01' || loginRoleType == '00'">
							<td align="right">发卡机构</td>
							<td>
								<s:hidden id="id_branchCode" name="accountCheckFile.branchCode"/>
								<s:textfield id="id_branchCodeName" name="cardBranchName" cssClass="{required:true}"/>
							</td>	
						</s:if>
							<td align="right">对账日期</td>
							<td>
								<s:textfield name="accountCheckFile.checkDate" onclick="WdatePicker({dateFmt:'yyyyMMdd'})" />
							</td>
							<td height="30">
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
			   <td align="center" nowrap class="titlebg">发卡机构号</td>
			   <td align="center" nowrap class="titlebg">发卡机构名</td>
			   <td align="center" nowrap class="titlebg">对账日期</td>
			   <td align="center" nowrap class="titlebg">对账文件名</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data">
			<tr>
			  <td align="center" nowrap>${branchCode}</td>
			  <td align="center" nowrap>${branchName}</td>
			  <td align="center" nowrap>${checkDate}</td>
			  <td align="center" nowrap>${fileName}</td>
			  <td align="center" nowrap>
			  	<f:pspan pid="accountCheckFile_download">
				  	<span class="redlink">
				  		<a href="javascript:downLoadReportFile('${branchCode}','${checkDate}','${fileName}');"/>下载</a>
				  	</span>
			  	</f:pspan>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>