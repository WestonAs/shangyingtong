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
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script type="text/javascript">		
		$(function(){
			$('#searchForm')[0].action = "list" + $('#searchForm_actionSubName')[0].value + ".do";	
			if('${loginRoleType}' == '00'){
				Selector.selectBranch('id_branchCodeName', 'id_branchCode', true, '20');
			} else if('${loginRoleType}' == '01'){
				Selector.selectBranch('id_branchCodeName', 'id_branchCode', true, '20', '', '', '${loginBranchCode}');
			}				
		})
		function clearBranch(){
			$('#id_branchCode').val('');
			$('#id_branchCodeName').val('');
		}
		</script>
		
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">售卡批次</td>
							<td><s:textfield name="saleCardBatReg.saleBatchId" cssClass="{digitOrLetter:true}"/></td>							
							<td align="right">卡号</td>
							<td>
								<s:textfield name="saleCardBatReg.cardId" cssClass="{digitOrLetter:true}"/>
							</td>
							<td align="right">状态</td>
							<td>
								<s:select name="saleCardBatReg.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">发卡机构</td>
							<td>
								<s:if test="showCardBranch">
									<s:hidden id="id_branchCode" name="saleCardReg.cardBranch"/>
									<s:textfield id="id_branchCodeName" name="cardBranchName"/>
								</s:if>
								<s:else>
									<s:select name="saleCardReg.cardBranch" list="cardBranchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName" onmouseover="FixWidth(this)"/>
								</s:else>
							</td>							
							<td align="right">购卡客户</td>
							<td>
								<s:textfield name="saleCardReg.cardCustomerName" />
							</td>
							<td align="right">返利方式</td>
							<td>
								<s:select name="saleCardReg.rebateType" list="rebateTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">售卡日期</td>
							<td>
								<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
							</td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm');clearBranch();" name="escape" />
								<f:pspan pid="saleCard${actionSubName}_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/saleCardBatReg/showAdd${actionSubName}.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>							
							<td><s:hidden name="actionSubName" ></s:hidden></td>
						</tr>
					</table>
					<s:token name="_TOKEN_SALE_CARD_BAT_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">售卡批次</td>
			   <!-- 
			   <td align="center" nowrap class="titlebg">卡类</td>
			    -->
			   <td align="center" nowrap class="titlebg">售卡日期</td>
			   <td align="center" nowrap class="titlebg">购卡客户</td>
			   <td align="center" nowrap class="titlebg">起始卡号</td>
			   <td align="center" nowrap class="titlebg">结束卡号</td>
			   <td align="center" nowrap class="titlebg">返利方式</td>
			   <td align="center" nowrap class="titlebg">实收金额</td>
			   <td align="center" nowrap class="titlebg">返利金额</td>
			   <td align="center" nowrap class="titlebg">手续费</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${saleBatchId}</td>
			  <!-- 
			  <td align="center" nowrap>${cardClassName}</td>
			   -->
			  <td align="center" nowrap>${saleDate}</td>
			  <td align="left" nowrap>${cardCustomerId}-${cardCustomerName}</td>
			  <td align="center" nowrap>${strCardId}</td>
			  <td align="center" nowrap>${endCardId}</td>
			  <td align="center" nowrap>${rebateTypeName}</td>
			  <td align="right" nowrap>${fn:amount(realAmt)}</td>
			  <td align="right" nowrap>${fn:amount(rebateAmt)}</td>
			  <td align="right" nowrap>${fn:amount(feeAmt)}</td>
			  <td align="center" nowrap <s:if test=" !'01'.equals(status)">class="redfont"</s:if>>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/saleCardBatReg/detail.do?saleCardBatReg.saleBatchId=${saleBatchId}">查看</f:link>			  		
			  	</span>
			  	<s:if test="isPresell==true" > <s:if test="unActivatedLabel.equals(status)" >
				  	<f:pspan pid="saleCardBatPre_activate"><f:link href="/saleCardBatReg/showActivate.do?saleCardBatReg.saleBatchId=${saleBatchId}">激活</f:link></f:pspan>
			  	</s:if></s:if>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>