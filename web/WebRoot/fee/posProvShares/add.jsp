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
			
				Selector.selectBranch('id_posProvName', 'id_posProv', true, '31');
				
				$('#idFeeType').change(function(){
					var value = $(this).val();
					if (value == '0' || value == '1') {
						$('#idFeeRateTr').show();
						$('#idFeeRate').removeAttr('disabled');
						//$('#idCostCycle').removeAttr('disabled');
						$('#idSectTbl').hide();
					} else if (value == '2' || value == '3') {
						//$('#idCostCycle').val('2').attr('disabled', 'true');
						$('#idFeeRateTr').hide();
						$('#idFeeRate').attr('disabled', 'true');
						$('#idSectTbl').show();
					} else {
						$('#idFeeRateTr').hide();
						$('#idFeeRate').attr('disabled', 'true');
						//$('#idCostCycle').removeAttr('disabled');
						$('#idSectTbl').hide();
					}
					if (value == '0') {
						$('#idFeeRateTip').html('元');
					} else if (value == '1') {
						$('#idFeeRateTip').html('%');
					} 
				});
			});
			function addItem() {
				var count = $('tr[id^="idDetail_"]').size();
					var content = '<tr id="idDetail_'+ (count+1) +'"><td align="center">'+ (count+1) +'</td><td align="center"><input type="text" name="ulimit" onblur="formatCurrency(this)" value="" id="inputForm_ulimit" /></td><td align="center" ><input type="text" name="feeRate" onblur="formatCurrency(this)" value="" id="inputForm_feeRate" />%</td></tr>';
				
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
				/*var feeRateComma = $('#idFeeRate').val();
				feeRateComma = feeRateComma.replace(/,/g, '');
				if(feeRateComma == ""||feeRateComma < 0){
					showMsg("费率不能小于 0 ，请重新输入！");
					return false;
				}
				if(!isDecimal(feeRateComma, '5,2')){
					showMsg("费率最大3位整数，2位小数。");
					return false;
				}
				*/
 				if(!isDisplay('idSectTbl')){
					return true;
				}
				var count = $('tr[id^="idDetail"]').size();
				for(i=0; i<count; i++){
					var $objU = $('tr[id^="idDetail"]').eq(i).children().eq(1).children()
					var $objR = $('tr[id^="idDetail"]').eq(i).children().eq(2).children()
					if((isNaN($objU.val()))||(isNaN($objR.val()))){
						showMsg("“分段金额上限”、“费率”请输入数字");
						return false;
					}
					if(($objU.val()=="")||($objU.val() < 0 )){
						showMsg("分段金额上限不能小于 0 ，请重新输入！");
						return false;
					}
					if(!isDecimal($objU.val(), '14,2')){
						showMsg("分段金额必须为Num(14,2)");
						return false;
					}
					if(($objR.val()=="")||($objR.val() < 0 )){
						showMsg("费率不能小于 0 ，请重新输入！");
						return false;
					}
					if(!isDecimal($objR.val(), '14,4')){
						showMsg("费率必须为Num(14,4)");
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
					<tr>
						<td width="80" height="30" align="right">分支机构</td>
						<td id="">
							<s:textfield  id="id_branchName" name="branchName" cssClass="readonly" readonly="true"></s:textfield>
						</td>
						</tr>
					<tr>
					<tr>
						<td width="80" height="30" align="right">机具出机方</td>
						<td>
							<s:hidden id="id_posProv" name="posProvShares.posProvId"
								cssClass="{required:true}"></s:hidden>
							<s:textfield  id="id_posProvName" cssClass="{required:true}"></s:textfield>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费方式</td>
						<td>
							<s:select id="idFeeType" name="posProvShares.feeType" list="feeTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费周期</td>
						<td>
							<s:select id="idCostCycle" name="costCycle" list="costCycleList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<s:hidden id="idCostCycleHidden" name="posProvShares.costCycle"></s:hidden>
						</td>
					</tr>
					<tr id="idFeeRateTr" style="display: none;">
						<td width="80" height="30" align="right">费率</td>
						<td>
							<s:textfield id="idFeeRate" name="feeRateComma" cssClass="{required:true, decimal:'14,4'}" disabled="disabeld"></s:textfield><span id="idFeeRateTip">%</span>
							<span class="field_tipinfo">费率最大10位整数，4位小数</span>
						</td>
					</tr>
				</table>
				<table class="form_grid" width="60%" border="0" cellspacing="3" cellpadding="0" id="idSectTbl" style="display:none;">
					<tr>
					   <td align="center" nowrap class="titlebg">序号</td>
					   <td align="center" nowrap class="titlebg">分段金额上限</td>
					   <td align="center" nowrap class="titlebg">费率(百分比)</td>
					</tr>
					<tr id="idDetailMax">
						<td align="center" id="idXh">1</td>
						<td align="center">
							<s:textfield name="ulimit" value="999999999999.99" readonly="true" cssClass="readonly"/>
						</td>
						<td align="center" >
							<s:textfield name="feeRate" value=""/>%
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
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/fee/posProvShares/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_POSPROVSHARES_ADD"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>