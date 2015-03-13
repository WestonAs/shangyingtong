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
			var feeType = $('#id_feeType').val();
			var feeRateComma;

			$('#id_templateName').blur(function(){
				var value = $(this).val();
				if(isEmpty(value)){
					showMsg('模板名称不能为空！');
					$('#id_templateName').val('');
				}
			});	
			
			if(feeType=='0'){
				getChinese($('#id_feeRateComma_1').get(0), 'id_feeRateToChinese');
				feeRateComma = $('#id_feeRateComma_1').val();
				if(!isEmpty(feeRateComma)){
					//formatCurrency($('#id_feeRateComma_1').get(0));
				}
			} else {
				feeRateComma = $('#id_feeRateComma_2').val();
				if(!isEmpty(feeRateComma)){
					//formatCurrency($('#id_feeRateComma_2').get(0));
				}
			}
			
			// 金额固定比例
			if(feeType=='1'){
				var maxAmtComma = $('#idMaxAmt').val();	
				var minAmtComma = $('#idMinAmt').val();	
				
				getChinese($('#idMaxAmt').get(0), 'id_maxAmtToChinese');
				getChinese($('#idMinAmt').get(0), 'id_minAmtToChinese');
				
				if(!isEmpty(maxAmtComma)){
					formatCurrency($('#idMaxAmt').get(0));
				}
				if(!isEmpty(minAmtComma)){
					formatCurrency($('#idMinAmt').get(0));
				}
			}

			var ulmoney = $('#id_updateUlmoney').val();
			if(!isEmpty(ulmoney)){
				formatCurrency($('#id_updateUlmoney').get(0));
			}
		});
		
		function checkField(){
			// 判断最小金额和最大金额
			if (isDisplay('idMaxAmt')){
				var maxAmt = $('#idMaxAmt').val();
				maxAmt = maxAmt.replace(/,/g, '');
				var minAmt = $('#idMinAmt').val();
				minAmt = minAmt.replace(/,/g, '');
				
				if (parseFloat(maxAmt) < parseFloat(minAmt)){
					showMsg('最小手续费不能大于最大手续费');
					return false;
				}
			}

			// 判断金额上限输入是否正确 			
			var ulmoney = $('#id_updateUlmoney').val();
			ulmoney = ulmoney.replace(/,/g, '');
			
			if(ulmoney == ""||ulmoney < 0){
				showMsg("金额上限不能小于 0 ，请重新输入！");
				return false;
			}
			if(!isDecimal(ulmoney, '14,2')){
				showMsg("金额上限最大12位整数，2位小数。");
				return false;
			}
			
			return true;
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
			<s:form action="modify.do" id="inputForm" cssClass="validate">
				<s:hidden name="merchFeeTemplate.templateId"></s:hidden>
				<s:hidden name="merchFeeTemplate.branchCode"></s:hidden>
				<s:hidden name="merchFeeTemplateDetail.transType"></s:hidden>
				<s:hidden name="merchFeeTemplateDetail.cardBin"></s:hidden>
				<s:hidden name="merchFeeTemplateDetail.ulMoney"></s:hidden>
				<s:hidden id="id_feeType" name="merchFeeTemplateDetail.feeType"></s:hidden>
				<table class="form_grid detail_tbl" width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td width="80" height="30" align="right">模板编号</td>
						<td>
							${merchFeeTemplate.templateId}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">发卡机构</td>
						<td>
							${merchFeeTemplate.branchCode}-${merchFeeTemplate.branchName}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">交易类型</td>
						<td>
							${merchFeeTemplateDetail.transTypeName}
						</td>	
					</tr>
					<tr>
						<td width="80" height="30" align="right">卡Bin</td>
						<td>
							${merchFeeTemplateDetail.cardBin}
						</td>	
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费方式</td>
						<td>
							${merchFeeTemplateDetail.feeTypeName}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费周期</td>
						<td>
							${merchFeeTemplateDetail.costCycleName}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">模板名称</td>
						<td>
							<s:textfield id="id_templateName" name="merchFeeTemplate.templateName" cssClass="{required:true}"></s:textfield>
							<span class="field_tipinfo">不超过20个字符</span>
						</td>
					</tr>
					
					<s:if test="merchFeeTemplateDetail.feeType==2 || merchFeeTemplateDetail.feeType==3">
					<tr>
						<td width="80" height="30" align="right">金额上限</td>
						<td>
							<s:if test="updateUlmoney == 999999999999.99 ">
								<s:textfield value="999,999,999,999.99" readonly="true" cssClass="readonly"></s:textfield>
								<s:hidden name="updateUlmoney" cssClass="{required:true}"></s:hidden>
							</s:if>
							<s:else>
								<s:textfield id="id_updateUlmoney" name="updateUlmoney" onblur="formatCurrency(this)" cssClass="{required:true, decimal:'14,2'}"></s:textfield>
								<span class="field_tipinfo">请输入金额上限</span>
							</s:else>
						</td>
					</tr>
					</s:if>
					<s:if test="merchFeeTemplateDetail.feeType==0">
						<tr>
							<td width="80" height="30" align="right">收费金额</td>
							<td>
								<s:textfield id="id_feeRateComma_1" name="feeRateComma" onkeyup="getChinese(this, 'id_feeRateToChinese')" 
									onblur="getChinese(this, 'id_feeRateToChinese')"
									cssClass="{required:true,num:true, decimal:'14,4'}"></s:textfield>
								<span class="field_tipinfo">正确格式：最大为10位整数，小于等于4位小数</span>
							</td>
						</tr>
						<%--<tr id="id_feeRateTr" >
							<td width="80" height="30" align="right">大写：</td>
							<td colspan="3">
								<span id="id_feeRateToChinese" style="font-size: 16px;"></span>
							</td>
						</tr>
						--%>
					</s:if>
					<s:else>
						<tr>
							<td width="80" height="30" align="right">费率</td>
							<td>
								<s:textfield id="id_feeRateComma_2" name="feeRateComma" cssClass="{required:true,num:true, decimal:'14,4'}"></s:textfield>%
								<span class="field_tipinfo">正确格式：最大为10位整数，小于等于4位小数</span>
							</td>
						</tr>
					</s:else>
					<s:if test="merchFeeTemplateDetail.feeType==1">
						<tr>
							<td width="80" height="30" align="right">最高手续费</td>
							<td>
								<s:textfield id="idMaxAmt" name="maxAmtComma" onkeyup="getChinese(this, 'id_maxAmtToChinese')" 
									onblur="formatCurrency(this), getChinese(this, 'id_maxAmtToChinese')" 
									cssClass="{required:true, decimal:'14,2'}"></s:textfield>
								<span class="field_tipinfo">最大为12位整数，小于等于2位小数</span>
							</td>
						</tr>
						<%--<tr id="id_maxAmtTr" >
							<td width="80" height="30" align="right">大写：</td>
							<td colspan="3">
								<span id="id_maxAmtToChinese" style="font-size: 16px;"></span>
							</td>
						</tr>
						--%>
						<tr>
							<td width="80" height="30" align="right">最小手续费</td>
							<td>
								<s:textfield id="idMinAmt" name="minAmtComma" onkeyup="getChinese(this, 'id_minAmtToChinese')" 
									onblur="formatCurrency(this), getChinese(this, 'id_minAmtToChinese')"
									cssClass="{required:true, decimal:'14,2'}"></s:textfield>
								<span class="field_tipinfo">最大为12位整数，小于等于2位小数</span>
							</td>
						</tr>
						<%--<tr id="id_minAmtTr" >
							<td width="80" height="30" align="right">大写：</td>
							<td colspan="3">
							<span id="id_minAmtToChinese" style="font-size: 16px;"></span>
							</td>
						</tr>
						--%>
						</s:if>
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30">
							<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return checkField();"/>
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/fee/merchFeeTemplate/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_MERCH_FEE_TEMPLATE_MODIFY"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>