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
		
		<f:js src="/js/date/WdatePicker.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){

			var count = $('td[id^="idUlMoneyTd_"]').size();
			for(i=0; i<count; i++){
				formatCurrency($('#idUlMoney_'+i).get(0));
			}
			
		});
		
		function checkField(){

			if(!isDisplay('idReleaseCardFeeDetailModifyTb')){
				return true;
			}
			
			var count = $('tr[id^="idReleaseCardFee"]').size();
			for(i=0; i<count; i++){
				var $objU = $('tr[id^="idReleaseCardFee"]').eq(i).children().eq(1).children()
				var objUValue = $objU.val().toString().replace(/\$|\,/g, '');
				
				var $objR = $('tr[id^="idReleaseCardFee"]').eq(i).children().eq(2).children()
				var objRValue = $objR.val().toString().replace(/\$|\,/g, '');
				
				if((isNaN(objUValue))||(isNaN(objRValue))){
					showMsg("“分段金额上限”、“费率”请输入数字");
					return false;
				}
				if((objUValue=="")||(objRValue < 0 )){
					showMsg("分段金额上限不能小于 0 ，请重新输入！");
					return false;
				}
				if(!isDecimal(objUValue, '14,2')){
					showMsg("分段金额最大12位整数，2位小数，必须为Num(14,2)");
					return false;
				}
				if((objRValue=="")||(objRValue < 0 )){
					showMsg("费率不能小于 0 ，请重新输入！");
					return false;
				}
				if(!isDecimal(objRValue, '14,4')){
					showMsg("费率最大10位整数，4位小数，必须为Num(14,4)");
					return false;
				}
			}
			for(i=1; i<count; i++){
				var $preObj = $('tr[id^="idReleaseCardFee"]').eq(i-1).children().eq(1).children()
				var $nextObj = $('tr[id^="idReleaseCardFee"]').eq(i).children().eq(1).children()
				
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

		function getExpirDate(){
			WdatePicker({minDate:'#F{$dp.$D(\'id_effDate\')}'});
		} 

		function getEffDate(){
			WdatePicker({maxDate:'#F{$dp.$D(\'id_expirDate\')}'});
		} 
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
			<s:form action="modify.do" id="inputForm" cssClass="validate">
				<s:hidden name="releaseCardFee.branchCode"></s:hidden>
				<s:hidden name="releaseCardFee.ulMoney"></s:hidden>
				<s:hidden name="releaseCardFee.merchId"></s:hidden>
				<s:hidden name="releaseCardFee.cardBin"></s:hidden>
				<s:hidden name="releaseCardFee.transType"></s:hidden>
				<s:hidden name="releaseCardFee.feePrinciple"></s:hidden>
				<s:hidden name="releaseCardFee.adjustCycleType"></s:hidden>
				<s:hidden id = "id_feeType" name="releaseCardFee.feeType"></s:hidden>
				<table class="form_grid detail_tbl" width="100%" border="0" cellspacing="3" cellpadding="0">
					<caption>${ACT.name}</caption>
					<tr>
						<td width="80" height="30" align="right">分支机构</td>
						<td>
							${releaseCardFee.chlCode}-${fn:branch(releaseCardFee.chlCode)}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">发卡机构</td>
						<td>
							${releaseCardFee.branchCode}-${fn:branch(releaseCardFee.branchCode)}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">商户</td>
						<td>
							<s:if test='"0".equals(releaseCardFee.merchId)'>通用商户</s:if><s:else>${releaseCardFee.merchId}-${fn:merch(releaseCardFee.merchId)}</s:else>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">卡BIN</td>
						<td>
							${releaseCardFee.cardBin} 
							<s:if test="releaseCardFee.cardBin == '*'">
							卡BIN为*时，表示通用
							</s:if>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">交易类型</td>
						<td>
							${releaseCardFee.transTypeName}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费方式</td>
						<td>
							${releaseCardFee.feeTypeName}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费周期</td>
						<td>
							${releaseCardFee.feeCycleTypeName}
						</td>
					</tr>
					<s:if test="releaseCardFee.feeCycleType==6">
						<tr>
							<td width="80" height="30" align="right">触发日期</td>
							<td>
								${releaseCardFee.feeDate}
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">调整月数</td>
							<td>
								${releaseCardFee.adjustMonths}
							</td>
						</tr>
					</s:if>
					<tr>
						<td width="80" height="30" align="right">计费原则</td>
						<td>
							${releaseCardFee.feePrincipleName}
						</td>
					</tr>
					<s:if test="releaseCardFee.feePrinciple!=0">
						<tr>
							<td width="80" height="30" align="right">调整周期</td>
							<td>
								${releaseCardFee.adjustCycleTypeName}
							</td>
						</tr>
					</s:if>
					<%--
					<tr>
						<td width="80" height="30" align="right">计费原则</td>
						<td>
						<s:select id="id_feePrinciple" name="releaseCardFee.feePrinciple" list="feePrincipleList" headerKey="" headerValue="--请选择--" 
							listKey="value" listValue="name" cssClass="{required:true}"></s:select>
						</td>
					</tr>
					--%>
					<tr>
						<td width="80" height="30" align="right">生效日期</td>
						<td>
							<s:textfield id="id_effDate" name="releaseCardFee.effDate" onclick="getEffDate();" cssClass="{required:true}" ></s:textfield>
							<span class="field_tipinfo"></span>
						</td>
						<td width="80" height="30" align="right">失效日期</td>
						<td>
							<s:textfield id="id_expirDate" name="releaseCardFee.expirDate" onclick="getExpirDate();" cssClass="{required:true}" ></s:textfield>
							<span class="field_tipinfo"></span>
						</td>
					</tr>
					
					<!-- 1-按交易笔数  5-金额固定比例保底封顶 -->
					<s:if test="releaseCardFee.feeType==1 || releaseCardFee.feeType==5">
						<tr>
							<td width="80" height="30" align="right">费率</td>
							<td>
								<s:textfield id="id_feeRateComma" name="feeRateComma" cssClass="{required:true,num:true, decimal:'14,4'}"></s:textfield><span id="idFeeRateTip"></span>
								<span class="field_tipinfo">正确格式：最大为10位整数，小于等于4位小数</span>
							</td>
						</tr>
						<s:if test="releaseCardFee.feeType == 5">
							<tr>
								<td width="80" height="30" align="right">最高手续费</td>
								<td>
									<s:textfield id="idMaxAmt" name="maxAmtComma" onblur="formatCurrency(this);" 
										cssClass="{required:true, decimal:'14,2'}"></s:textfield>
									<span class="field_tipinfo">最大为12位整数，小于等于2位小数</span>
								</td>
							</tr>
							<tr>
								<td width="80" height="30" align="right">最小手续费</td>
								<td>
									<s:textfield id="idMinAmt" name="minAmtComma" onblur="formatCurrency(this);"
										cssClass="{required:true, decimal:'14,2'}"></s:textfield>
									<span class="field_tipinfo">最大为12位整数，小于等于2位小数</span>
								</td>
							</tr>
						</s:if>
					</s:if>
					<s:else>
						</table>
						<table class="form_grid" id="idReleaseCardFeeDetailModifyTb" 
							width="60%" border="0" cellspacing="3" cellpadding="0">
							<tr>
							   <td align="center" nowrap class="titlebg">序号</td>
							   <td align="center" nowrap class="titlebg">分段金额上限</td>
							   <td align="center" nowrap class="titlebg">费率</td>
							 </tr>
							<s:iterator value="releasecardFeeDtlList" status="mcnStuts">
							    <tr id="idReleaseCardFee_<s:property value="#mcnStuts.index"/>">
								      <td align="center" class="u_half" >
								      	<s:property value="#mcnStuts.index+1"/>
								      </td>
								      <td id="idUlMoneyTd_<s:property value="#mcnStuts.index"/>" align="center" >
									      <s:if test="ulMoney == 999999999999.99">
												<input id="idUlMoney_<s:property value="#mcnStuts.index"/>" onblur="formatCurrency(this);" readonly="true" class="readonly" type="text" name="ulimit" value="${ulMoney}" />
												<input type="hidden" name="originalUlimit" value="${ulMoney}"/>
										  </s:if>
										  <s:else>
											  	<input id="idUlMoney_<s:property value="#mcnStuts.index"/>" onblur="formatCurrency(this);" type="text" name="ulimit" value="${ulMoney}" />
											 	<input type="hidden" name="originalUlimit" value="${ulMoney}"/>
										  </s:else>
									  </td>
								      <td id="Id_feeRate_tds_<s:property value="#mcnStuts.index"/>" align="center" >
								      	<input type="text" name="feeRate" value="${feeRate}"/>
									  </td>
							    </tr>
							</s:iterator>
						</table>
						<table>
					</s:else>
						<tr style="margin-top: 30px;">
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return checkField();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/fee/releaseCardFee/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
				<s:token name="_TOKEN_RELEASECARDFEE_MODIFY"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>