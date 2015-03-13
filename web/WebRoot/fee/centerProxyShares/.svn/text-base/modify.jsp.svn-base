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
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
			<s:form action="modify.do" id="inputForm" cssClass="validate">
				<s:hidden name="centerProxyShares.proxyId"></s:hidden>
				<s:hidden name="centerProxyShares.branchCode"></s:hidden>
				<s:hidden name="centerProxyShares.ulMoney"></s:hidden>
				<s:hidden name="centerProxyShares.costCycle"></s:hidden>
				<s:hidden name="centerProxyShares.feeType"></s:hidden>
				<table class="form_grid detail_tbl" width="100%" border="0" cellspacing="3" cellpadding="0">
					<caption>${ACT.name}</caption>
					<tr>
						<td width="80" height="30" align="right">分支机构</td>
						<td>
							${fn:branch(centerProxyShares.branchCode)}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">代理机构</td>
						<td>
							${fn:branch(centerProxyShares.proxyId)}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费方式</td>
						<td>
							${centerProxyShares.feeTypeName}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费周期</td>
						<td>
							${centerProxyShares.costCycleName}
						</td>
					</tr>
					<s:if test="centerProxyShares.feeType==2 || centerProxyShares.feeType==3">
					<tr>
						<td width="80" height="30" align="right">金额上限</td>
						<td>
							<s:if test="updateUlmoney == 999999999999.99 ">
								<s:textfield value="999,999,999,999.99" readonly="true" cssClass="readonly"></s:textfield>
								<s:hidden name="updateUlmoney" cssClass="{required:true}"></s:hidden>
							</s:if>
							<s:else>
								<s:textfield id="id_updateUlmoney" name="updateUlmoney" onblur="formatCurrency(this)" cssClass="{required:true}"></s:textfield>
								<span class="field_tipinfo">请输入金额上限</span>
							</s:else>
						</td>
					</tr>
					</s:if>
					<tr>
						<td width="80" height="30" align="right">手续费分润比率</td>
						<td>
							<s:textfield id="id_feeRateComma" name="feeRateComma" onblur="" cssClass="{required:true, decimal:'14,4'}"></s:textfield>%
							<span class="field_tipinfo">最大为10位整数，4位小数</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30">
							<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return checkField();"/>
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/fee/centerProxyShares/list.do?goBack=goBack')" class="ml30" />
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