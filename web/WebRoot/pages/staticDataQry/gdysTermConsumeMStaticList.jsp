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
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

		<script>	
		   $(function(){
		       $('#exportCsvBtn').click(function (){generateFile()});
		       if('${loginRoleType}' == '00'){
		           Selector.selectBranch('idBranchName', 'idBranchNo', true,'20');
			       Selector.selectMerch('idMerchName', 'idMerchNo', true);
		       }else if('${loginRoleType}' == '01'){
		           Selector.selectBranch('idBranchName', 'idBranchNo', true,'20','','','${loginBranchCode}');
			       Selector.selectMerch('idMerchName', 'idMerchNo', true,'','','','${loginBranchCode}');
		       }
		    });
		    
		   
			function generateFile(){
				var count = $('#id_maxRowCount').val();
				if (count == 0) {
					showMsg('请先查询或查询出来的记录为空！');
					return false;
				}
				
				// 提交产生excel的请求
				$('#searchForm').attr('action', 'gdysTermConsumeMStaticExport.do').submit();
				$('#searchForm').attr('action', 'gdysTermConsumeMStaticList.do');
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
				<s:form id="searchForm" action="gdysTermConsumeMStaticList.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">统计月份</td>
							<td><s:textfield name="gdysIssuerTermStatic.month" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'});" /></td>
			                <td width="80" height="30" align="right">发卡机构</td>
							<td>
							    <s:hidden id="idBranchNo" name="gdysIssuerTermStatic.cardIssuer"/>
							    <s:textfield id="idBranchName" name="branchName"/>
							</td>
							<td width="80" height="30" align="right">商户</td>
							<td>
							   <s:hidden id="idMerchNo" name="gdysIssuerTermStatic.merNo"/>
							   <s:textfield id="idMerchName" name="merchName"/>
							 </td>
						</tr>
						<tr>
			                <td width="80" height="30" align="right">终端号</td>
							<td>
							    <s:textfield name="gdysIssuerTermStatic.termlId"/>
							</td>
						</tr>
						<tr>
						    <td></td>
							<td height="30" colspan="4" >
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" 
									onclick="FormUtils.reset('searchForm');$('#idMerchNo').val('');$('#idMerchName').val('');
									$('#idBranchName').val('');$('#idBranchNo').val('');" name="escape" />
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
			   <td align="center" nowrap="nowrap" class="titlebg">统计月份</td>
			   <td align="center" nowrap="nowrap" class="titlebg">发卡机构</td>
			   <td align="center" nowrap="nowrap" class="titlebg">商户</td>	
			   <td align="center" nowrap="nowrap" class="titlebg">终端号</td>	   
			   <td align="center" nowrap="nowrap" class="titlebg">总数量</td>			   
			   <td align="center" nowrap="nowrap" class="titlebg">总金额</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td align="center" nowrap="nowrap">${month }</td>
			  <td align="center" nowrap="nowrap">${cardIssuer}-${fn:branch(cardIssuer)}</td>
			  <td align="center" nowrap="nowrap">${merNo}-${fn:merch(merNo)}</td>
			  <td align="center" nowrap="nowrap">${termlId }</td>
			  <td align="center" nowrap="nowrap">${totalnum }</td>
			  <td align="right" nowrap="nowrap">${fn:amount(totalamt)}</td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>