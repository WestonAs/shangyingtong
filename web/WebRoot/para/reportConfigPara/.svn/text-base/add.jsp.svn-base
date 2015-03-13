<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
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
		<f:js src="/js/validate.js"/>
		<f:js src="/js/date/WdatePicker.js" />
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>

		$(function(){
			var parent =  $('#idParent').val();

			$('#id_insType').change(function(){
				var insType = $(this).val();

				// 发卡机构
				if(insType == '0'){
					$('#id_merch_1').hide();
					$('#id_merch_2').hide();
					$('#id_MerchId').attr('disabled', 'true');
					$('#id_merchName').attr('disabled', 'true');
					$('#id_MerchId').val('');
					$('#id_merchName').val('');

					$('#id_card_1').hide();
					$('#id_card_2').hide();
					$('#id_BranchCode').attr('disabled', 'true');
					$('#id_branchName').attr('disabled', 'true');
					$('#id_BranchCode').val('');
					$('#id_branchName').val('');

					$('#id_cards_1').show();
					$('#id_cards_2').show();
					$('#id_insId').removeAttr('disabled');
					$('#id_insName').removeAttr('disabled');

					$('#id_insName').unbind().removeMulitselector();
					$('#id_insId').val('');
					$('#id_insName').val('');
					Selector.selectBranch('id_insName', 'id_insId', false, '20', '', '', parent);
					
				}
				// 商户
				else if(insType == '1'){
					$('#id_merch_1').show();
					$('#id_merch_2').show();
					$('#id_MerchId').removeAttr('disabled');
					$('#id_merchName').removeAttr('disabled');
					$('#id_MerchId').val('');
					$('#id_merchName').val('');

					$('#id_card_1').show();
					$('#id_card_2').show();
					$('#id_BranchCode').removeAttr('disabled');
					$('#id_branchName').removeAttr('disabled');
					$('#id_BranchCode').val('');
					$('#id_branchName').val('');

					$('#id_cards_1').hide();
					$('#id_cards_2').hide();
					$('#id_insId').attr('disabled', 'true');
					$('#id_insName').attr('disabled', 'true');
					$('#id_insId').val('');
					$('#id_insName').val('');

					Selector.selectBranch('id_branchName', 'id_BranchCode', true, '20', loadMerch, '', parent);
					
					$('#id_merchName').focus(function(){
						var insType = $('#id_insType').val();
						var branchCode = $('#id_BranchCode').val();

						if (isEmpty(insType)){
							showMsg('请先选择机构类型');
						}
						
						if (isEmpty(branchCode)){
							showMsg('请先选择发卡机构再选择商户');
						} 
						
					});
				}
				else if(insType == ''){
					$('#id_merch_1').hide();
					$('#id_merch_2').hide();
					$('#id_MerchId').attr('disabled', 'true');
					$('#id_merchName').attr('disabled', 'true');
					$('#id_MerchId').val('');
					$('#id_merchName').val('');
					
					$('#id_cards_1').hide();
					$('#id_cards_2').hide();
					$('#id_insId').attr('disabled', 'true');
					$('#id_insName').attr('disabled', 'true');
					$('#id_insId').val('');
					$('#id_insName').val('');

					$('#id_card_1').hide();
					$('#id_card_2').hide();
					$('#id_BranchCode').attr('disabled', 'true');
					$('#id_branchName').attr('disabled', 'true');
					$('#id_BranchCode').val('');
					$('#id_branchName').val('');
				}
			});
			
			$('#id_branchName').focus(function(){
				var insType = $('#id_insType').val();
				
				if (isEmpty(insType)){
					showMsg('请先选择机构类型');
				}
			});

			function loadMerch() {
				var insType = $('#id_insType').val();
				var branchCode = $('#id_BranchCode').val();
				
				if (isEmpty(insType)){
					showMsg('请先选择机构类型');
				}
					
				if (!isEmpty(branchCode)){
					$('#id_merchName').unbind().removeMulitselector();
					
					$('#id_MerchId').val('');
					$('#id_merchName').val('');
					Selector.selectMerch('id_merchName', 'id_MerchId', false, branchCode);
				} else {
					$('#id_merchName').focus(function(){
						if (isEmpty(insType)){
							showMsg('请先选择机构类型');
						}
				
						if (isEmpty(branchCode)){
							showMsg('请先选择发卡机构再选择商户');
						}
					});
				}
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
		}

		function queryCycleTypeList(){
			var reportType = $('#id_reportType').val(); 
			$("#id_cycleType").load(CONTEXT_PATH + "/para/reportConfigPara/queryCycleTypeList.do",{'reportConfigPara.reportType':reportType, 'callId':callId()});
		}
		
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<s:hidden id="idParent" name="parent" />
					<tr>
					<td width="80" height="30" align="right">机构类型</td>
					<td>
						<s:select id="id_insType" name="reportConfigPara.insType" list="insTypeList" 
							headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
					</td>
					</tr>
					<tr>
					<td id="id_cards_1" width="80" height="30" align="right" style="display: none">发卡机构</td>
					<td id="id_cards_2" style="display: none">
						<s:hidden id="id_insId" name="insIds" disabled="true"/>
						<s:textfield id="id_insName" name="insName" cssClass="{required:true}" disabled="true"/>
					</td>
					</tr>
					
					<tr>
						<td id="id_card_1" width="80" height="30" align="right" style="display: none">发卡机构</td>
						<td id="id_card_2" style="display: none">
							<s:hidden id="id_BranchCode" name="insId"
								cssClass="{required:true}" disabled="true"></s:hidden>
							<s:textfield  id="id_branchName" cssClass="{required:true}" disabled="true"></s:textfield>
						</td>
						</tr>
					<tr>
						<td id="id_merch_1" width="80" height="30" align="right" style="display: none">商户</td>
						<td id="id_merch_2" style="display: none">
							<s:hidden id="id_MerchId" name="insIds"
								cssClass="{required:true}" disabled="true"></s:hidden>
							<s:textfield  id="id_merchName" cssClass="{required:true}" disabled="true"></s:textfield>
							<span class="field_tipinfo">请先选择发卡机构</span>
						</td>
					</tr>
						
					<tr>
						<td width="80" height="30" align="right">报表类型</td>
						<td>
							<s:select id="id_reportType" name="reportConfigPara.reportType" onchange="queryCycleTypeList()" list="reportTypeList" 
								headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">周期类型</td>
						<td>
							<select name="reportConfigPara.cycleType" id="id_cycleType" onchange="bindCycle();"
								class="{required:true}">
							</select>
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
					<tr style="margin-top: 30px;">
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30" colspan="3">
							<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return submitForm()" />
						<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
						<input type="button" value="返回" name="escape" onclick="gotoUrl('/para/reportConfigPara/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
					</table>
					<s:token name="_TOKEN_REPORT_CONFIG_PARA_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>