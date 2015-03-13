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
		<f:js src="/js/paginater.js"/>
		

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="list.do" id="searchForm" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td height="30" colspan="4">
								<f:pspan pid="branchshares_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/fee/cardMerchFee/showAddMerchFeeAmt.do?merchFeeAmt.branchCode=${branchCode}&merchFeeAmt.merchId=${merchFeeAmt.merchId}&merchFeeAmt.transType=${merchFeeAmt.transType}&merchFeeAmt.cardBin=${merchFeeAmt.cardBin}');" id="input_btn3"  name="escape" /></f:pspan>
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/fee/cardMerchFee/showModify.do?cardMerchFee.branchCode=${branchCode}&cardMerchFee.merchId=${merchFeeAmt.merchId}&cardMerchFee.transType=${merchFeeAmt.transType}&cardMerchFee.cardBin=${merchFeeAmt.cardBin}&goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARDMERCHFEE_LISTMERCHFEEAMT"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">金额段</td>
			   <td align="center" nowrap class="titlebg">费率</td>
			   <td align="center" nowrap class="titlebg">更新时间</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="merchFeeAmtPage.data"> 
			<tr>
			  <td align="center" nowrap>
			  	${fn:amount(ulMoney)}
			  </td>
			  <td align="center" nowrap>
			  	${fn:percent(feeRate)}
			  </td>
			  <td align="center" nowrap><s:date name="updateTime"/></td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:pspan pid="merchfeeamtmgr"><f:link href="/fee/cardMerchFee/showModifyMerchFeeAmt.do?merchFeeAmt.branchCode=${branchCode}&merchFeeAmt.merchId=${merchId}&merchFeeAmt.transType=${transType}&merchFeeAmt.cardBin=${cardBin}&merchFeeAmt.ulMoney=${ulMoney}">修改</f:link></f:pspan>
			  		<f:pspan pid="merchfeeamtmgr">
			  			<a href="javascript:submitUrl('searchForm','/fee/cardMerchFee/deleteMerchFeeAmt.do?branchCode=${branchCode}&merchFeeAmt.merchId=${merchId}&merchFeeAmt.transType=${transType}&merchFeeAmt.cardBin=${cardBin}&merchFeeAmt.ulMoney=${ulMoney}','确定要删除吗?');">删除</a>
			  		</f:pspan>
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>