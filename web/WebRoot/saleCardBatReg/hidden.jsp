<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

	<input type="hidden" name="resultErrorMsg" id="resultErrorMsg" value="${resultErrorMsg}" />
	<input type="hidden" name="resultSumAmt" id="resultSumAmt" value="${resultSumAmt}" />
	<input type="hidden" name="resultSumExpenses" id="resultSumExpenses" value="${resultSumExpenses}" />
	<input type="hidden" name="resultSumRealAmt" id="resultSumRealAmt" value="${resultSumRealAmt}" />
	<input type="hidden" name="resultSumRebateAmt" id="resultSumRebateAmt" value="${resultSumRebateAmt}" />
	<input type="hidden" name="resultFeeAmt" id="resultFeeAmt" value="${resultFeeAmt}" />

	<s:hidden name="saleCardBatReg.amt" />
	<s:hidden name="saleCardBatReg.expenses" />
	<s:hidden name="saleCardBatReg.realAmt" />
	<s:hidden name="saleCardBatReg.rebateAmt" />
