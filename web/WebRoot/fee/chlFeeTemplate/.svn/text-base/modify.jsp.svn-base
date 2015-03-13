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
			var updateUlmoney = $('#id_updateUlmoney').val();
			if(!isEmpty(updateUlmoney)){
				formatCurrency($('#id_updateUlmoney').get(0));
			}
			//formatCurrency($('#id_feeRate').get(0));
			
			var value = $('#idFeeType').val();
			
			if (value == '2' || value == '4' || value == '6') {
				$('#idFeeRateTr_').hide();
				$('#idFeeRateTip').html('%');
			} else { // 固定金额、分段金额
				$('#idFeeRateTr_').show();
				getChinese();
				$('#idFeeRateTip').html('元');
			}
			
		});
		
		function feeTypeChange() {
			var value = $('#idFeeType').val();
			if (value == '2' || value == '4' || value == '6') {
				$('#idFeeRateTr_').hide();
				$('#idFeeRateTip').html('%');
			} else { // 固定金额、分段金额
				$('#idFeeRateTr_').show();
				getChinese();
				$('#idFeeRateTip').html('元');
			}
		}
		
		function checkField(){
			// 判断金额上限输入是否正确 			
			var ulmoney = $('#id_updateUlmoney').val();
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

		// 根据金额获得中文大写
		function getChinese(){
			var feeRate = $('#id_feeRate').val(); 
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
			<s:form action="modify.do" id="inputForm" cssClass="validate">
				<s:hidden name="chlFeeTemplate.branchCode"></s:hidden>
				<s:hidden name="chlFeeTemplate.feeContent"></s:hidden>
				<s:hidden name="chlFeeTemplate.ulMoney"></s:hidden>
				<table class="form_grid detail_tbl" width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td width="80" height="30" align="right">模板ID</td>
						<td>
							${chlFeeTemplate.templateId}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">模板名称</td>
						<td>
							${chlFeeTemplate.templateName}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费内容</td>
						<td>
							${chlFeeTemplate.feeContentName}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费周期</td>
						<td>
							${chlFeeTemplate.costCycleName}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费类型</td>
						<td>
							<s:select id="idFeeType" name="chlFeeTemplate.feeType" onchange="feeTypeChange();" list="feeTypeList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
						</td>
					</tr>
					<s:if test="chlFeeTemplate.feeType==3 || chlFeeTemplate.feeType==4 || chlFeeTemplate.feeType==6">
					<tr>
						<td width="80" height="30" align="right">金额上限</td>
						<td>
							<s:if test="updateUlmoney == 999999999999.99">
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
					<tr>
						<td width="80" height="30" align="right">收费金额/费率</td>
						<td>
							<s:textfield id="id_feeRate" name="feeRateComma" onkeyup="getChinese()" onblur="getChinese()" cssClass="{required:true, decimal:'14,4'}" ></s:textfield><span id="idFeeRateTip"></span>
							<span class="field_tipinfo">最大10位整数，4位小数</span>
						</td>
					</tr>
					<!-- 1:固定金额；3:分段金额 -->
					<tr id="idFeeRateTr_" style="display: none" >
						<td width="80" height="30" align="right">大写：</td>
						<td colspan="3">
							<span id="id_feeRateToChinese" style="font-size: 16px;"></span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30">
							<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return checkField();"/>
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/fee/chlFeeTemplate/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_CHLFEETEMPLATE_MODIFY"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>