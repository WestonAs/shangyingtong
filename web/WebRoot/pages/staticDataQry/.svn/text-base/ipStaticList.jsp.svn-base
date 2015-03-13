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
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/date/WdatePicker.js"/>
		<f:js src="/js/paginater.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

		<script>	
		    $(function(){
		       $('#exportCsvBtn').click(function (){generateFile()});
		       Selector.selectBranch('idBranchName', 'idBranchNo', true,'');
		    });
		    
		   
			function generateFile(){
				var count = $('#id_maxRowCount').val();
				if (count == 0) {
					showMsg('请先查询或查询出来的记录为空！');
					return false;
				}
				
				// 提交产生excel的请求
				$('#searchForm').attr('action', 'ipStaticExport.do').submit();
				$('#searchForm').attr('action', 'ipStaticList.do');
				$('#searchForm').find(":submit").attr('disabled', false);
				$('#searchForm').find(":button").attr('disabled', false);
			}
		</script>
		
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="ipStaticList.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
						    <td width="80" height="30" align="right">统计日期</td>
							<td><s:textfield id="startDate" name="startDate" onclick="getStartDate()" cssStyle="width:70px;"/>
							    - <s:textfield id="endDate" name="endDate" onclick="getEndDate()" cssStyle="width:70px;"/>
							</td>
						
							<td width="80" height="30" align="right">管理机构</td>
							<td>
							    <s:hidden id="idBranchNo" name="logIpGuard.branchNo"/>
							    <s:textfield id="idBranchName" name="logIpGuard.branchName"/>
							</td>
						    <td width="80" height="30" align="right">商户名称</td>
						    <td><s:textfield name="logIpGuard.merchName"/></td>
							
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="4" >
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" 
									onclick="FormUtils.reset('searchForm');$('#idBranchName').val('');$('#idBranchNo').val('');" name="escape" />
								<input style="margin-left:30px;" type="button" value="导出Excel文档" id="exportCsvBtn"  name="ok" />
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
			<s:hidden id="id_maxRowCount" name="page.maxRowCount"></s:hidden>
				<thead>
				<tr>
				   <td align="center" nowrap="nowrap" class="titlebg">统计日期</td>
				   <td align="center" nowrap="nowrap" class="titlebg">IP</td>
				   <td align="center" nowrap="nowrap" class="titlebg">管理机构</td>
				   <td align="center" nowrap="nowrap" class="titlebg">商户名称</td>			   
				   <td align="center" nowrap="nowrap" class="titlebg">登录次数</td>			   
				</tr>
				</thead>
				<s:iterator value="page.data"> 
				<tr>		
				  <td align="center" nowrap="nowrap">${statDate}</td>
				  <td align="center" nowrap="nowrap">${loginIp}</td>
				  <td align="center" nowrap="nowrap">${branchNo}-${branchName}</td>
				  <td align="center" nowrap="nowrap">${merchName}</td>
				  <td align="center" nowrap="nowrap">${logcnt}</td>
				</tr>
				</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>