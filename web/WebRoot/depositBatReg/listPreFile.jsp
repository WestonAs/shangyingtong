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
		<f:js src="/js/datePicker/WdatePicker.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script type="text/javascript">		
		</script>
		
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="listPreFile.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td height="30"  align="right">充值批次</td>
							<td height="30" >
								<s:textfield name="depositBatReg.depositBatchId" cssClass="{digit:true}"/>
							</td>							
							<td height="30"  align="right">卡类型</td>
							<td height="30" >
								<s:select name="depositBatReg.cardClass" list="cardTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td height="30"  align="right">状态</td>
							<td height="30" >
								<s:select name="depositBatReg.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">发卡机构</td>
							<td>
								<s:if test="showCardBranch">
									<s:hidden id="id_branchCode" name="depositReg.cardBranch"/>
									<s:textfield id="id_branchCodeName" name="cardBranchName"/>
								</s:if>
								<s:else>
									<s:select name="depositReg.cardBranch" list="cardBranchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName" />
								</s:else>
							</td>							
							<td align="right">购卡客户</td>
							<td>
								<s:textfield name="depositReg.cardCustomerName" />
							</td>
							<td align="right">返利方式</td>
							<td>
								<s:select name="depositReg.rebateType" list="rebateTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">状态</td>
							<td>
								<s:select name="depositReg.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td align="right">充值日期</td>
							<td>
								<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td height="30" ></td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="depositCardBatPreFile_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/depositBatRegFile/showPreAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_DEPOSIT_BAT_PRE_FILE_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">充值批次</td>
			   <td align="center" nowrap class="titlebg">充值日期</td>
			   <td align="center" nowrap class="titlebg">卡类</td>
			   <td align="center" nowrap class="titlebg">购卡客户</td>
			   <td align="center" nowrap class="titlebg">充值金额</td>
			   <td align="center" nowrap class="titlebg">返利金额</td>
			   <td align="center" nowrap class="titlebg">手续费</td>
			   <td align="center" nowrap class="titlebg">实收金额</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">返利方式</td>
			   <td align="center" nowrap class="titlebg">返利规则</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${depositBatchId}</td>
			  <td align="center" nowrap>${depositDate}</td>
			  <td align="center" nowrap>${cardClassName}</td>
			  <td align="left" nowrap>${cardCustomerId}-${cardCustomerName}</td>
			  <td align="right" nowrap>${fn:amount(amt)}</td>
			  <td align="right" nowrap>${fn:amount(rebateAmt)}</td>
			  <td align="right" nowrap>${fn:amount(feeAmt)}</td>
			  <td align="right" nowrap>${fn:amount(realAmt)}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>${rebateTypeName}</td>
			  <td align="center" nowrap>${rebateName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/depositBatRegFile/detail.do?depositBatReg.depositBatchId=${depositBatchId}">明细</f:link>			  		
			  	</span>
			  	<s:if test="preDeposit==1 && status==04">
				  	<f:pspan pid="depositCardBat_activate">
				  		<f:link href="/depositBatReg/showActivate.do?depositBatReg.depositBatchId=${depositBatchId}">激活</f:link>
				  	</f:pspan>
			  	</s:if>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>