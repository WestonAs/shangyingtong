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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			var insType = $('#id_insCode').val();

			// 发卡机构-0
			// 商户-1
			if(insType=='0'){
				$('#id_card').show();
				$('#id_merch').hide();
			}
			else {
				$('#id_card').hide();
				$('#id_merch').show();
			}

			var cycleType = $('#id_cycleType').val(); 
			
			// 1按日
			// 2按周
			// 3按月
			if(cycleType=='1'){
				$('#tr_cycleOfMonth').hide();
				$('#id_cycleOfMonth').attr('disabled',true);
				$('#tr_cycleOfWeek').hide();
				$('#id_cycleOfWeek').attr('disabled',true);
			}
			else if(cycleType=='2'){
				$('#tr_cycleOfMonth').hide();
				$('#id_cycleOfMonth').attr('disabled',true);
				$('#tr_cycleOfWeek').show();
				$('#id_cycleOfWeek').removeAttr('disabled');
			}
			else if(cycleType=='3'){
				$('#tr_cycleOfMonth').show();
				$('#id_cycleOfMonth').removeAttr('disabled');
				$('#tr_cycleOfWeek').hide();
				$('#id_cycleOfWeek').attr('disabled', true);
			}
			
		});

		function bindCycle(){
			var cycleType = $('#id_cycleType').val(); 
			
			// 1按日
			// 2按周
			// 3按月
			if(cycleType=='1'){
				$('#tr_cycleOfMonth').hide();
				$('#id_cycleOfMonth').attr('disabled',true);
				$('#tr_cycleOfWeek').hide();
				$('#id_cycleOfWeek').attr('disabled',true);
			}
			else if(cycleType=='2'){
				$('#tr_cycleOfMonth').hide();
				$('#id_cycleOfMonth').attr('disabled',true);
				$('#tr_cycleOfWeek').show();
				$('#id_cycleOfWeek').removeAttr('disabled');
			}
			else if(cycleType=='3'){
				$('#tr_cycleOfMonth').show();
				$('#id_cycleOfMonth').removeAttr('disabled');
				$('#tr_cycleOfWeek').hide();
				$('#id_cycleOfWeek').attr('disabled', true);
			}
			else {
				$('#tr_cycleOfMonth').hide();
				$('#tr_cycleOfWeek').hide();
			}
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="100" height="30" align="right">机构类型</td>
							<td>
								<s:hidden id="id_insCode" name="reportConfigPara.insType"/>
								<s:textfield id="id_insType" name="reportConfigPara.insTypeName" cssClass="{required:true} readonly" readonly="true"></s:textfield>
							</td>
						</tr>
						<tr>
							<td id="id_card" width="100" height="30" align="right">发卡机构</td>
							<td id="id_merch" width="100" height="30" align="right">商户</td>
							<td>
								<s:hidden id="id_insCode" name="reportConfigPara.insId"/>
								<s:textfield name="reportConfigPara.insName" cssClass="{required:true} readonly" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">报表类型</td>
							<td>
								<s:hidden id="id_insCode" name="reportConfigPara.reportType"/>
								<s:textfield name="reportConfigPara.reportTypeName" cssClass="{required:true} readonly" readonly="true"/> 
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">周期类型</td>
							<td>
								<s:select id="id_cycleType" name="reportConfigPara.cycleType" onchange="bindCycle();" list="cycleTypeList" 
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							</td>
						</tr>
						<tr id="tr_cycleOfMonth" style="display: none">
							<td id="td_cycleOfMonth_1" width="80" height="30" align="right">指定月日期</td>
							<td id="td_cycleOfMonth_2">
							<s:select name="reportConfigPara.cycleOfMonth" list="cycleOfMonthTypeList" headerKey="" headerValue="--请选择--" 
								listKey="value" listValue="name" id="id_cycleOfMonth" cssClass="{required:true}" disabled="true"></s:select>
							<span class="field_tipinfo"></span>
							<br /></td>
						</tr>
						<tr id="tr_cycleOfWeek" style="display: none">
							<td id="td_cycleOfWeek_1" width="80" height="30" align="right">指定周日期</td>
							<td id="td_cycleOfWeek_2">
							<s:select name="reportConfigPara.cycleOfWeek" list="cycleOfWeekTypeList" headerKey="" headerValue="--请选择--" 
								listKey="value" listValue="name" id="id_cycleOfWeek" cssClass="{required:true}" disabled="true"></s:select>
							<span class="field_tipinfo"></span>
							<br /></td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" name="ok" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/para/reportConfigPara/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_REPORT_CONFIG_PARA_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>