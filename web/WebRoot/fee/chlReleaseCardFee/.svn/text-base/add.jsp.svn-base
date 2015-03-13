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
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
			$(function(){
				var setMode = $('#idSetMode').val();
				var parent =  $('#idParent').val();
				
				$('#id_branchType').change(function(){
					var branchType = $(this).val();
					// 集团
					if(branchType == '1'){
						$('#id_branchCode_1').show();
						$('#id_branchCode_2').show();
						$('#id_branchCode_3').hide();
						$('#id_branchCode_4').hide();
						$('#branchName_1').removeAttr('disabled');
						$('#idBranchCode_1').removeAttr('disabled');
						$('#branchName_2').attr('disabled', 'true');
						$('#idBranchCode_2').attr('disabled', 'true');
						Selector.selectBranch('branchName_1', 'idBranchCode_1', true, '12', setMode, parent);
					}
					// 发卡机构
					else if(branchType == '0'){
						$('#id_branchCode_3').show();
						$('#id_branchCode_4').show();
						$('#id_branchCode_1').hide();
						$('#id_branchCode_2').hide();
						$('#branchName_2').removeAttr('disabled');
						$('#idBranchCode_2').removeAttr('disabled');
						$('#branchName_1').attr('disabled', 'true');
						$('#idBranchCode_1').attr('disabled', 'true');
						Selector.selectBranch('branchName_2', 'idBranchCode_2', true, '20', setMode, parent);
					}
					else if(branchType == ''){
						$('#id_branchCode_3').hide();
						$('#id_branchCode_4').hide();
						$('#id_branchCode_1').hide();
						$('#id_branchCode_2').hide();
						$('#branchName_1').attr('disabled', 'true');
						$('#idBranchCode_1').attr('disabled', 'true');
						$('#branchName_2').attr('disabled', 'true');
						$('#idBranchCode_2').attr('disabled', 'true');
					}
				});
				
				$('#id_transType').change(function(){
					var transType = $(this).val();
					// 预付资金、通用积分资金、赠券资金，计费周期默认为"按年"
					if(transType == '1' || transType == '2' || transType == '3'){
						$('#id_costCycle').val("按年");
					}
					// 其他的交易类型，计费周期默认为"按月"
					else {
						$('#id_costCycle').val("按月");
					}
					
				});
				
				
				$('#idFeeType').change(function(){
					var value = $(this).val();
					if (value == '1' || value == '2' || value == 'A') {
						$('#idFeeRateTr').show();
						$('#idFeeRate').removeAttr('disabled');
						$('#idSectTbl').hide();
						if(value == '1' || value == 'A'){
							$('#idFeeRateTd_1').hide();
							$('#idFeeRateTd_2').show();
						} else{
							$('#idFeeRateTd_1').show();
							$('#idFeeRateTd_2').hide();
						}
					} else {
						$('#idFeeRateTr').hide();
						$('#idFeeRate').attr('disabled', 'true');
						$('#idSectTbl').show();
					}
					
					if (value == '1' || value == 'A') {
						$('#idFeeRateTip').html('元');
					} else if (value == '2') {
						$('#idFeeRateTip').html('%');
					} 
				});
			});
			
			function addItem() {
				var count = $('tr[id^="idDetail_"]').size();
					var content = '<tr id="idDetail_'+ (count+1) +'"><td align="center">'+ (count+1) +'</td><td align="center"><input type="text" name="ulimit" value="" onblur="formatCurrency(this)" id="inputForm_ulimit" /></td><td align="center" ><input type="text" name="feeRate" value="" onblur="formatCurrency(this)" id="inputForm_feeRate" />%</td></tr>';
				
				$('#idXh').html(count+2);
				$('#idDetailMax').before(content);
			
				// 设置样式
				SysStyle.setFormGridStyle();
			}
			function deleteItem() {
				var count = $('tr[id^="idDetail_"]').size();
				if (count == 0) {
					$('#idXh').html('1');
					showMsg('必须设置一项');
					return;
				}
				$('#idXh').html(count);
				$('tr[id^="idDetail_"]:last').remove();
			}
			function check(){
				if(!isDisplay('idSectTbl')){
					return true;
				}
				var count = $('tr[id^="idDetail"]').size();
				for(i=0; i<count; i++){
					var $objU = $('tr[id^="idDetail"]').eq(i).children().eq(1).children()
					var $objR = $('tr[id^="idDetail"]').eq(i).children().eq(2).children()
					/*
					if((isNaN($objU.val()))||(isNaN($objR.val()))){
						showMsg("“分段金额上限”、“费率”请输入数字");
						return false;
					}
					if(($objU.val()=="")||($objU.val() < 0 )){
						showMsg("分段金额上限不能小于 0 ，请重新输入！");
						return false;
					}
					*/
					if(!isDecimal($objU.val(), '14,2')){
						showMsg("分段金额必须为Num(14,2)");
						return false;
					}
					if(($objR.val()=="")||($objR.val() < 0 )){
						showMsg("收费金额/费率不能小于 0 ，请重新输入！");
						return false;
					}
					if(!isDecimal($objR.val(), '14,4')){
						showMsg("收费金额/费率必须为Num(14,4)");
						return false;
					}
				}
				for(i=1; i<count; i++){
					var $preObj = $('tr[id^="idDetail"]').eq(i-1).children().eq(1).children()
					var $nextObj = $('tr[id^="idDetail"]').eq(i).children().eq(1).children()
					var preValue = $preObj.val();
					var nextValue = $nextObj.val();
					if(Number(preValue) > Number(nextValue)){
						showMsg("后段“分段金额上限”必须大于前段“分段金额上限” ，请重新输入！");
						return false;
					}				
				}
				return true;
			}
			
			function submitForm(){
				if(!check()){
					return false;
				}
				$('#idCostCycleHidden').val($('#idCostCycle').val());
				$("#inputForm").submit();
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
			<s:form action="add.do" id="inputForm" cssClass="validate">
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<caption>${ACT.name}</caption>
					<s:hidden id="idSetMode" name="setMode" />
					<s:hidden id="idParent" name="parent" />
					<tr>
						<td width="80" height="30" align="right">机构类型</td>
						<td>
							<s:select id="id_branchType" name="releaseCardFee.groupFlag" list="branchTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">请选择集团或发卡机构</span>
						</td>
					</tr>
					<tr>
						<td id="id_branchCode_1" width="80" height="30" align="right" style="display: none">集团</td>
						<td id="id_branchCode_2" style="display: none">
							<s:hidden id="idBranchCode_1" name="releaseCardFee.branchCode"
								cssClass="{required:true}" disabled="true"></s:hidden>
							<s:textfield  id="branchName_1" cssClass="{required:true}" disabled="true" ></s:textfield>
						</td>
					</tr>
					<tr>
						<td id="id_branchCode_3" width="80" height="30" align="right" style="display: none">发卡机构</td>
						<td id="id_branchCode_4" style="display: none">
							<s:hidden id="idBranchCode_2" name="releaseCardFee.branchCode"
								cssClass="{required:true}" disabled="true"></s:hidden>
							<s:textfield  id="branchName_2" cssClass="{required:true}" disabled="true"></s:textfield>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费内容</td>
						<td>
							<s:select id="id_transType" name="releaseCardFee.transType" list="transTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费周期</td>
						<td>
							<s:textfield  id="id_costCycle" name="releaseCardFee.costCycle" cssClass="readonly" readonly="true"></s:textfield>
							<span class="field_tipinfo">预付资金、通用积分资金、赠券资金，计费周期默认为"按年"，其他默认为"按月"</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费类型</td>
						<td>
							<s:select id="idFeeType" name="releaseCardFee.feeType" list="feeTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
						</td>
					</tr>
					<tr id="idFeeRateTr" style="display: none;">
						<td id="idFeeRateTd_1" width="80" height="30" align="right">费率</td>
						<td id="idFeeRateTd_2" width="80" height="30" align="right">金额</td>
						<td>
							<s:textfield id="idFeeRate" name="feeRateComma" onblur="formatCurrency(this)" cssClass="{required:true, decimal:'14,4'}" disabled="disabeld"></s:textfield><span id="idFeeRateTip"></span>
						</td>
					</tr>
				</table>
				<table class="form_grid" width="60%" border="0" cellspacing="3" cellpadding="0" id="idSectTbl" style="display:none;">
					<tr>
					   <td align="center" nowrap class="titlebg">序号</td>
					   <td align="center" nowrap class="titlebg">分段金额上限</td>
					   <td align="center" nowrap class="titlebg">收费金额/费率(百分比)</td>
					</tr>
					<tr id="idDetailMax">
						<td align="center" id="idXh">1</td>
						<td align="center">
							<s:textfield name="ulimit" value="9999999999.99" readonly="true" cssClass="readonly"/>
						</td>
						<td align="center" >
							<s:textfield name="feeRate" onblur="formatCurrency(this)" value=""/>%
						</td>
					</tr>
					<tr id="idLink" style="line-height: 30px;">
						<td align="left" colspan="4" style="padding-left: 20px;">
							<span class="redlink"><a href="javascript:void(0);" onclick="javascript:addItem();">增加一项</a><a class="ml30" href="javascript:void(0);" onclick="javascript:deleteItem();">删除一项</a></span>
						</td>
					</tr>
				</table>
				<table class="form_grid" width="80%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30" colspan="3"> 
							<input type="button" value="提交" id="input_btn2"  name="ok" onclick="return submitForm()"/>
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/fee/chlReleaseCardFee/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_CHLRELEASECARDFEE_ADD"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>