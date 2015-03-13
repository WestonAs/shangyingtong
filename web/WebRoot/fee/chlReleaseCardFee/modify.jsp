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
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
			<s:form action="modify.do" id="inputForm" cssClass="validate">
				<s:hidden name="releaseCardFee.branchCode"></s:hidden>
				<s:hidden name="releaseCardFee.transType"></s:hidden>
				<s:hidden name="releaseCardFee.merchId"></s:hidden>
				<s:hidden name="releaseCardFee.cardBin"></s:hidden>
				<s:hidden name="releaseCardFee.ulMoney"></s:hidden>
				<table class="form_grid detail_tbl" width="100%" border="0" cellspacing="3" cellpadding="0">
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
						<td width="80" height="30" align="right">计费内容</td>
						<td>
							${releaseCardFee.transTypeName}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费类型</td>
						<td>
							${releaseCardFee.feeTypeName}
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">计费周期</td>
						<td>
							${releaseCardFee.costCycleName}
						</td>
					</tr>
					<s:if test="releaseCardFee.feeType==3 || releaseCardFee.feeType==4 || releaseCardFee.feeType==6">
					<tr>
						<td width="80" height="30" align="right">金额上限</td>
						<td>
							<s:if test="updateUlmoney == 9999999999.99 ">
								<s:textfield value="999,999,999,999.99" readonly="true" cssClass="readonly"></s:textfield>
								<s:hidden name="updateUlmoney" cssClass="{required:true}"></s:hidden>
							</s:if>
							<s:else>
								<s:textfield name="updateUlmoney" onblur="formatCurrency(this)" cssClass="{required:true}"></s:textfield>
								<span class="field_tipinfo">请输入金额上限</span>
							</s:else>
						</td>
					</tr>
					</s:if>
					<tr>
						<td width="80" height="30" align="right">收费金额/费率</td>
						<td>
							<s:textfield name="releaseCardFee.feeRate" onblur="formatCurrency(this)" cssClass="{required:true, decimal:'14,4'}"></s:textfield><s:if test="releaseCardFee.feeType == 2 || releaseCardFee.feeType == 4 || releaseCardFee.feeType == 6">%</s:if><s:else>元</s:else>
							<span class="field_tipinfo">请输入收费金额/费率</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30">
							<input type="submit" value="提交" id="input_btn2"  name="ok" />
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/fee/chlReleaseCardFee/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_CHLRELEASECARDFEE_MODIFY"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>