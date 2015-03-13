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
		<f:js src="/js/date/WdatePicker.js"  defer="defer"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#generateExcelBtn').click(generateExcel);
		});
		
		/** 下载 产生 明细Excel文件 */
		function generateExcel(){
			if(!validateForm()){
				return false;
			}
			$.getJSON(CONTEXT_PATH + "/pages/nonrealtimemonitor/histrans/ajaxIsGeneratingExcel.do",{'callId':callId()}, 
				function(json){
					if(json.success){
						alert("正在生产 Excel文件，请耐心等待...");
					}else{
						// 提交产生excel的请求
						$('#searchForm').attr('action', 'generate.do').submit();
						$('#searchForm').attr('action', 'list.do');
						$('#searchForm').find(":submit").attr('disabled', false);
						$('#searchForm').find(":button").attr('disabled', false);
					}
				}
			);
		}
		
		//检查卡号
		function validateForm() {
			//var days = DateDiff($('#startDate').val(), $('#endDate').val());
			//if(days > 31){
			//	showMsg("清算日期跨度最大为31天。");
			//	return false;
			//}
			
			return true;
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">风险商户号</td>
							<td><s:textfield name="trans.merNo" /></td>
							<td align="right">卡号</td>
							<td><s:textfield name="trans.cardId" /></td>
							<td align="right">清算日期</td>
							<td>
								<s:textfield id="startDate" name="settStartDate" onclick='WdatePicker({ maxDate:"#F{$dp.$D(\'endDate\')}" });' cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="endDate" name="settEndDate" onclick='WdatePicker({minDate:"#F{$dp.$D(\'startDate\')}" });' cssStyle="width:68px;"/>
							</td>

						</tr>
						<tr>

							<td>&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="查询" id="input_btn2"  name="ok"  onclick="return validateForm();"/>
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<input type="button" value="导出Excel" id="generateExcelBtn" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_TRANS_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<!-- 数据列表区 -->
		<div class="tablebox">
		<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
		<thead>
		<tr>
      <td align="center" nowrap class="titlebg">清算日期</td>
      <td align="center" nowrap class="titlebg">商户号</td>
      <td align="center" nowrap class="titlebg">商户名称</td>
      <td align="center" nowrap class="titlebg">卡号</td>
      <td align="center" nowrap class="titlebg">流水号</td>
      <td align="center" nowrap class="titlebg">交易日期</td>
      <td align="center" nowrap class="titlebg">交易响应码</td>
      <td align="center" nowrap class="titlebg">风险类型</td>
      <td align="center" nowrap class="titlebg">风险标志</td>
      <td align="center" nowrap class="titlebg">交易金额</td>
      <td align="center" nowrap class="titlebg">累计交易金额</td>
      <td align="center" nowrap class="titlebg">累计交易笔数</td>
		</tr>
		</thead>
		<s:iterator value="page.data"> 
		<tr>		
      <td align="center" nowrap>${settDate}</td>
      <td align="center" nowrap>${merNo}</td>
      <td align="center" nowrap>${fn:merch(merNo)}</td>
      <td align="center" nowrap>${cardId}</td>
      <td align="center" nowrap>${transSn}</td>
      <td align="center" nowrap><s:date name="rcvTime" format="yyyy-MM-dd HH:mm:ss" /></td>
      <td align="center" nowrap>${respCode}</td>
      <td align="center" nowrap>${riskTypeName}</td>
      <td align="center" nowrap>${riskFlagName}</td>
      <td align="center" nowrap>${transAmt}</td>
      <td align="center" nowrap>${amount}</td>
      <td align="center" nowrap>${tradeCnt}</td>
		</tr>
		</s:iterator>
		</table>
		<f:paginate name="page"/>
	    </div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>