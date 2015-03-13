<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>${ACT.name}</title>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>	
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<script type="text/javascript" src="cardMembFee.js"></script>
		
		<script>
			$(function(){
				$('#feeType').change(feeTypeChanged);

				$('#feeType').change();//执行一次初始化
			});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
			<s:form action="modify.do" id="inputForm" cssClass="validate">
				<s:hidden name="cardMembFee.feeRuleId"></s:hidden>
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<caption>${ACT.name}</caption>
					<tr>
						<td align="right">发卡机构</td>
						<td>
							<s:hidden id="idBranchCode" name="cardMembFee.branchCode" cssClass="{required:true}"/>
							<input type="text" id="idBranchCode_sel" value="${fn:branch(cardMembFee.branchCode)}" class="readonly {required:true}" readonly="readonly"/>
							<span class="field_tipinfo"></span>
						</td>

						<td align="right">会员卡号</td>
						<td>
							<s:textfield name="cardMembFee.cardId" cssClass="{digitOrLetter:true, required:true} readonly" readonly="true"/>
							<span class="field_tipinfo"></span>
						</td>
					</tr>
					<tr>
						<td align="right">交易类型</td>
						<td>
							<s:select name="cardMembFee.transType" headerKey="" headerValue="--请选择--" 
								list="transTypeList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo"></span>
						</td>
						<td align="right">计费方式</td>
						<td>
							<s:select id="feeType" name="cardMembFee.feeType" headerKey="" headerValue="--请选择--" 
								list="feeTypeList" listKey="value" listValue="name"
								onmouseover="FixWidth(this)" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo"></span>
						</td>
					</tr>
					<tr>
						<td align="right">计费周期</td>
						<td>
							<s:select name="cardMembFee.costCycle"  
								list="costCycleTypeList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo"></span>
						</td>
						<td align="right">费率</td>
						<td colspan="3">
							<s:textfield name="cardMembFee.feeRate" cssClass="{required:true, decimal:'14,4'}"></s:textfield>
							<span id="idFeeRateTip" class="redfont"></span>
							<span class="field_tipinfo">正确格式：最大10位整数，4位小数</span>
						</td>
					</tr>
					<tr id="minMaxAmtTr" style="display:none">
						<td align="right">单笔保底手续费</td>
						<td ><!-- releaseCardFee.minAmt  -->
							<s:textfield id="minAmt" name="cardMembFee.minAmt"  
								cssClass="{required:true, decimal:'14,4', num:true}" disabled="true"></s:textfield><span> 元</span>
							<span class="field_tipinfo">最大为10位整数，小于等于4位小数</span>
						</td>
						<td align="right">单笔封顶手续费</td>
						<td ><!-- releaseCardFee.maxAmt  -->
							<s:textfield id="maxAmt" name="cardMembFee.maxAmt"  
								cssClass="{required:true, decimal:'14,4', num:true}" disabled="true"></s:textfield><span> 元</span>
							<span class="field_tipinfo">最大为10位整数，小于等于4位小数</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30">
							<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return checkField();"/>
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/fee/cardMembFee/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_CENTERPROXYSHARES_MODIFY"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>