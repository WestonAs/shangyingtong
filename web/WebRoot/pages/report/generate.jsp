<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>手动生成报表</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(document).ready(function(){
				//window.parent.unblock();
			});
		$(function(){
			//window.parent.unblock();
			$('#input_btn2').click(function(){
				if($('#inputForm').validate().form()){
					if (!confirm('是否生成报表？重新生成报表将覆盖旧的报表文件，请先做好备份！')) {
						return;
					}
					$('#inputForm').submit();
					$('#input_btn2').attr('disabled', 'true');
					//window.parent.showWaiter();
					$("#loadingBarDiv").css("display","inline");
					$("#contentDiv").css("display","none");
				}
			});
			$('#id_reportType').change(function(){
				var value = $(this).val();
				if(value == '0'){// 如果是生成日报表
					$('td[id^="id_date_td"]').show();
					$('#id_reportDate').removeAttr('disabled');
					$('td[id^="id_month_td"]').hide();
					$('#id_reportMonth').attr('disabled', 'true');
					
					$('#dayReportType_tr').show();
					$('#id_dayReportType').removeAttr('disabled');
					$('#monthReportType_tr').hide();
					$('#id_monthReportType').attr('disabled', 'true');
				} else if(value == '1'){ //如果生成的是月报表
					$('td[id^="id_date_td"]').hide();
					$('#id_reportDate').attr('disabled', 'true');
					$('td[id^="id_month_td"]').show();
					$('#id_reportMonth').removeAttr('disabled');
					
					$('#dayReportType_tr').hide();
					$('#id_dayReportType').attr('disabled', 'true');
					$('#monthReportType_tr').show();
					$('#id_monthReportType').removeAttr('disabled');
				} else {
					$('td[id^="id_date_td"]').hide();
					$('#id_reportDate').attr('disabled', 'true');
					$('td[id^="id_month_td"]').hide();
					$('#id_reportMonth').attr('disabled', 'true');
					
					$('#dayReportType_tr').hide();
					$('#id_dayReportType').attr('disabled', 'true');
					$('#monthReportType_tr').hide();
					$('#id_monthReportType').attr('disabled', 'true');
				}
			});
		});
		function getStartDate(){
			WdatePicker({dateFmt:'yyyyMMdd', minDate:'#F{$dp.$D(\'id_starDate\')}', maxDate:'#F{$dp.$D(\'id_endDate\')}'});
		}
		function getStartDateImg(){
			WdatePicker({el:'id_reportDate', minDate:'#F{$dp.$D(\'id_starDate\')}', maxDate:'#F{$dp.$D(\'id_endDate\')}'});
		}
		function getStartMonth(){
			WdatePicker({dateFmt:'yyyyMM', minDate:'#F{$dp.$D(\'id_starDate\')}', maxDate:'#F{$dp.$D(\'id_endDate\')}'});
		}
		
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<jsp:include flush="true" page="/common/loadingBarDiv.jsp"></jsp:include>
		
		<div id="contentDiv" class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			
			<div class="contentb">
				<s:form action="generate.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="150" height="30" align="right">报表类型</td>
							<td>
								<s:select id="id_reportType" name="reportType" list="reportTypeList" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name"  cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择要生成的报表类型</span>
							</td>
						</tr>
						<tr>
							<td id="id_date_td1" width="150" height="30" align="right" style="display: none;">统计日期</td>
							<td id="id_date_td2" style="display: none;">
								<s:hidden id="id_starDate" name="starDate"/>
								<s:hidden id="id_endDate" name="endDate"/>
								<s:textfield id="id_reportDate" onclick="getStartDate();" name="reportDate" cssClass="{required:true}"
								 disabled="true" />
								 <img id="id_report_img" onclick="getStartDateImg();" alt="点击选择日期" src="../../js/date/skin/datePicker.gif" width="16" height="22" align="middle" />
								<span id="error_tip_Id" class="field_tipinfo">报表统计日期不能为空</span>
							</td>
						</tr>
						<tr>
							<td id="id_month_td1" width="150" height="30" align="right" style="display: none;">统计月份</td>
							<td id="id_month_td2" style="display: none;">
								<s:textfield id="id_reportMonth" onclick="getStartMonth();" name="reportDate" cssClass="{required:true}"
								  disabled="true" />
								<span id="error_tip_Id" class="field_tipinfo">报表统计月份不能为空</span>
							</td>
						</tr>
						<tr  id="dayReportType_tr" style="display:none">
							<td width="100" height="30" align="center">指定日报表  </td>
							<td>
								<s:select id="id_dayReportType" name="report" list="dayReportTypeList" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name"  disabled="true"></s:select>
								<span class="field_tipinfo">请选择报表</span>
							</td>
						</tr>
						<tr  id="monthReportType_tr" style="display:none">
							<td width="100" height="30" align="center">指定月报表  </td>
							<td>
								<s:select id="id_monthReportType" name="report" list="monthReportTypeList" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name"  disabled="true"></s:select>
								<span class="field_tipinfo">请选择报表</span>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="生成报表" id="input_btn2"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MANUAL_GENERATE_REPORT"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>