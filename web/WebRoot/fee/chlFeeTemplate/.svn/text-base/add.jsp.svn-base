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
				// 选择分支机构
				//Selector.selectBranch('id_branchName', 'id_templateName', true, '01');
			
				$('#idFeeType').change(function(){
					$('#id_feeRateToChinese').html('');
					$('#idFeeRate').val('');
					var value = $(this).val();
					if (value == '1' || value == '2') {
						$('#idFeeRateTr').show();
						$('#idFeeRate').removeAttr('disabled');
						$('#idSectTbl').hide();
						if(value == '1'){
							$('#idFeeRateTd_1').hide();
							$('#idFeeRateTd_2').show();
							$('#idFeeRateTr_').show();
						} else{
							$('#idFeeRateTd_1').show();
							$('#idFeeRateTd_2').hide();
							$('#idFeeRateTr_').hide();
						}
					} else {
						$('#idFeeRateTr').hide();
						$('#idFeeRateTr_').hide();
						$('#idFeeRate').attr('disabled', 'true');
						$('#idSectTbl').show();
					}
					
					if (value == '1') {
						$('#idFeeRateTip').html('元');
					} else if (value == '2') {
						$('#idFeeRateTip').html('%');
					} 

					// 分段金额
					if(value == '3'){
						$('#id_AmtOrFeeRate').html('收费金额(元)');
					}
					// 分段比例、累进比例
					else if(value == '4' || value == '6'){
						$('#id_AmtOrFeeRate').html('费率(%)');
					}
				});

				$('#id_feeContent').change(function(){
					var value = $(this).val();

					// 预付资金、通用积分资金、 赠券资金
					if(value == '1' || value == '2' || value == '3'){
						$('#id_Segment').html('分段金额上限');
					}
					// 专属积分交易笔数
					else if(value == '4'){
						$('#id_Segment').html('分段交易笔数');
					}
					// 次卡可用次数
					else if(value == '5'){
						$('#id_Segment').html('分段次数');
					}
					// 会员数、折扣卡会员数
					else if(value == '6' || value == '7'){
						$('#id_Segment').html('分段会员数');
					}
				});
			});
			
			function addItem() {
				var count = $('tr[id^="idDetail_"]').size();
					var content = '<tr id="idDetail_'+ (count+1) +'"><td align="center">'+ (count+1) +'</td><td align="center"><input type="text" name="ulimit" value="" onblur="formatCurrency(this)" id="inputForm_ulimit" /></td><td align="center" ><input type="text" name="feeRate" value="" id="inputForm_feeRate" /></td></tr>';
				
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
					var objUValue = $objU.val().toString().replace(/\$|\,/g, '');
					
					var $objR = $('tr[id^="idDetail"]').eq(i).children().eq(2).children()
					var objRValue = $objR.val().toString().replace(/\$|\,/g, '');
					
					if((isNaN(objUValue))||(isNaN(objRValue))){
						showMsg("“分段金额上限”、“费率”请输入数字");
						return false;
					}
					if((objUValue=="")||(objUValue < 0 )){
						showMsg("分段金额上限不能小于 0 ，请重新输入！");
						return false;
					}
					if(!isDecimal(objUValue, '14,2')){
						showMsg("分段金额最大为12位整数，2位小数，必须为Num(14,2)");
						return false;
					}
					if((objRValue=="")||(objRValue < 0 )){
						showMsg("收费金额/费率不能小于 0 ，请重新输入！");
						return false;
					}
					if(!isDecimal(objRValue, '14,4')){
						showMsg("收费金额/费率最大为10位整数，4位小数，必须为Num(14,4)");
						return false;
					}
				}
				for(i=1; i<count; i++){
					var $preObj = $('tr[id^="idDetail"]').eq(i-1).children().eq(1).children()
					var $nextObj = $('tr[id^="idDetail"]').eq(i).children().eq(1).children()
					var preValue = $preObj.val();
					preValue = preValue.replace(/,/g, '');
					
					var nextValue = $nextObj.val();
					nextValue = nextValue.replace(/,/g, '');
					
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
			
			function queryCostCycleList(){
				var feeContent = $('#id_feeContent').val(); 
				$("#id_costCycle").load(CONTEXT_PATH + "/fee/chlFee/queryCostCycleList.do",{'chlFee.feeContent':feeContent, 'callId':callId()});
			}

			// 根据金额获得中文大写
			function getChinese(){
				var feeRate = $('#idFeeRate').val(); 
				var chinese = AmountInWords(feeRate);
				$("#id_feeRateToChinese").html(chinese);
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
						<td width="80" height="30" align="right">模板名称</td>
						<td>
							<s:textfield id="id_templateName" name="chlFeeTemplate.templateName" cssClass="{required:true}"></s:textfield>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费内容</td>
						<td>
							<s:select id="id_feeContent" name="chlFeeTemplate.feeContent" onchange="queryCostCycleList()" list="feeContentList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">请选择计费内容</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费周期</td>
						<td>
							<select name="chlFeeTemplate.costCycle" id="id_costCycle"
								class="{required:true}">
							</select>
							<span class="field_tipinfo">预付资金、通用积分资金、赠券资金默认为"按年"，其他为"按月"</span>	
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费类型</td>
						<td>
							<s:select id="idFeeType" name="chlFeeTemplate.feeType" list="feeTypeList" headerKey="" 
								headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}">
							</s:select>
							<span class="field_tipinfo">请选择计费类型</span>
						</td>
					</tr>
					<tr id="idFeeRateTr" style="display: none;">
						<td id="idFeeRateTd_1" width="80" height="30" align="right">费率</td>
						<td id="idFeeRateTd_2" width="80" height="30" align="right">金额</td>
						<td>
							<s:textfield id="idFeeRate" name="feeRateComma" onkeyup="getChinese()" onblur="getChinese()" 
								cssClass="{required:true, decimal:'14,4'}" maxlength="15" disabled="disabeld">
							</s:textfield><span id="idFeeRateTip"></span>
							<span class="field_tipinfo">最大为10位整数，4位小数</span>
						</td>
					</tr>
					<tr id="idFeeRateTr_" style="display:none">
						<td width="80" height="30" align="right">大写：</td>
						<td colspan="3">
							<span id="id_feeRateToChinese" style="font-size: 16px;"></span>
						</td>
					</tr>
				</table>
				<table class="form_grid" width="80%" border="0" cellspacing="3" cellpadding="0" id="idSectTbl" style="display:none;">
					<tr>
					   <td align="center" nowrap class="titlebg">序号</td>
					   <td align="center" nowrap class="titlebg" id="id_Segment"></td>
					   <td align="center" nowrap class="titlebg" id="id_AmtOrFeeRate"></td>
					</tr>
					<tr id="idDetailMax">
						<td align="center" id="idXh">1</td>
						<td align="center">
							<s:textfield name="ulimit" value="999999999999.99" readonly="true" cssClass="readonly"/>
						</td>
						<td align="center" >
							<s:textfield name="feeRate" value=""/>
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
						<td width="80" height="30" align="right"></td>
						<td height="30" colspan="3"> 
							<input type="button" value="提交" id="input_btn2"  name="ok" onclick="return submitForm()"/>
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/fee/chlFeeTemplate/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_CHLFEETEMPLATE_ADD"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>