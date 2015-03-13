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
			var feeRateComma = $('#id_feeRateComma').val();
			if(!isEmpty(feeRateComma)){
				//formatCurrency($('#id_feeRateComma').get(0));
			}
			
			var ulmoney = $('#id_updateUlmoney').val();
			if(!isEmpty(ulmoney)){
				formatCurrency($('#id_updateUlmoney').get(0));
			}
		});
			
		function checkField(){
			var feeType = $('#id_feeType').val();
			
			if(feeType=='2'||feeType=='3'){
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
			}
			
			// 判断输入费率是否正确 
			var feeRateComma = $('#id_feeRateComma').val();
			feeRateComma = feeRateComma.replace(/,/g, '');
			if(feeRateComma == ""||feeRateComma < 0){
				showMsg("费率不能小于 0 ，请重新输入！");
				return false;
			}
			if(!isDecimal(feeRateComma, '14,4')){
				showMsg("费率最大10位整数，4位小数。");
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
				<s:hidden name="posManageShares.posManageId"></s:hidden>
				<s:hidden name="posManageShares.ulMoney"></s:hidden>
				<s:hidden name="posManageShares.costCycle"></s:hidden>
				<s:hidden id="id_feeType" name="posManageShares.feeType"></s:hidden>
				<s:hidden name="posManageShares.branchCode"></s:hidden>
				<table class="form_grid detail_tbl" width="100%" border="0" cellspacing="3" cellpadding="0">
					<caption>${ACT.name}</caption>
					<tr>
						<td width="80" height="30" align="right">分支机构</td>
						<td>
							${fn:branch(posManageShares.branchCode)}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">出机方</td>
						<td>
							${posManageShares.posManageName}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费方式</td>
						<td>
							${posManageShares.feeTypeName}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费周期</td>
						<td>
							${posManageShares.costCycleName}
						</td>
					</tr>
					<s:if test="posManageShares.feeType==2 || posManageShares.feeType==3">
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
					<tr>
						<td width="80" height="30" align="right">费率</td>
						<td>
							<s:textfield id="id_feeRateComma" name="feeRateComma" cssClass="{required:true, decimal:'14,4'}"></s:textfield>%
							<span class="field_tipinfo">费率最大10位整数，4位小数</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30">
							<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return checkField();"/>
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/fee/posManageShares/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_POSMANAGESHARES_MODIFY"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>