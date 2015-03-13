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
		<f:js src="/js/paginater.js"/>
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<script>	
			$(function(){
				if('${loginRoleType}' == '00'){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20');
				} else if('${loginRoleType}' == '01'){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20', '', '', '${loginBranchCode}');
				}
			});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:hidden name="centerProxyShares.branchCode"></s:hidden>
				<s:form action="list.do" id="searchForm" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<s:if test="centerOrCenterDeptRoleLogined || fenzhiRoleLogined">
								<td align="right">发卡机构</td>
								<td>
									<s:hidden id="idBranchCode" name="cardMembFee.branchCode" cssClass=""/>
									<s:textfield id="idBranchCode_sel" name="formMap.branchName" cssClass=""/>
								</td>
							</s:if>
							<s:elseif test="cardOrCardDeptRoleLogined">
								<td align="right">发卡机构</td>
								<td>
									<s:select name="cardMembFee.branchCode" headerKey="" headerValue="--请选择--" 
										list="myCardBranch" listKey="branchCode" listValue="branchName" 
										onmouseover="FixWidth(this)" cssClass=""></s:select>
								</td>
							</s:elseif>
							<td align="right">会员卡号</td>
							<td>
								<s:textfield name="cardMembFee.cardId" cssClass="{digitOrLetter:true}"/>
							</td>
							<td align="right">交易类型</td>
							<td>
								<s:select name="cardMembFee.transType" headerKey="" headerValue="--请选择--" 
									list="transTypeList" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">计费方式</td>
							<td>
								<s:select name="cardMembFee.feeType" headerKey="" headerValue="--请选择--" 
									list="feeTypeList" listKey="value" listValue="name"
									onmouseover="FixWidth(this)" ></s:select>
							</td>
							
							<td align="right">计费规则ID</td>
							<td>
								<s:textfield name="cardMembFee.feeRuleId" cssClass="{digit:true}" />
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="$('#idBranchCode_sel').val('');$('#idBranchCode').val('');FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="cardMembFee_add">
									<input style="margin-left:30px;" type="button" value="新增" 
										onclick="javascript:gotoUrl('/fee/cardMembFee/showAdd.do');" id="input_btn3"  name="escape" />
								</f:pspan>
							</td>
						</tr>
					</table>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
					   <td align="center" nowrap class="titlebg">计费规则ID</td>
					   <td align="center" nowrap class="titlebg">发卡机构</td>
					   <td align="center" nowrap class="titlebg">会员卡号</td>
					   <td align="center" nowrap class="titlebg">交易类型</td>
					   <td align="center" nowrap class="titlebg">计费方式</td>
					   <td align="center" nowrap class="titlebg">费率</td>
					   <td align="center" nowrap class="titlebg">计费周期</td>
					   <td align="center" nowrap class="titlebg">更新时间</td>
					   <td align="center" nowrap class="titlebg">操作</td>
					</tr>
				</thead>
				<s:iterator value="page.data"> 
					<tr>
					  <td align="center" nowrap>${feeRuleId}</td>
					  <td align="left" nowrap>${branchCode}-${fn:branch(branchCode)}</td>
					  <td align="center" nowrap>${cardId}</td>
					  <td align="center" nowrap>${transTypeName}</td>
					  <td align="center" nowrap>${feeTypeName}</td>
					  <td align="right" nowrap>
					  	<s:if test="feeType == 0">${feeRate} 元/笔</s:if>
					  	<s:else>${fn:percentPre(feeRate, 4)}</s:else>
					  </td>
					  <td align="center" nowrap>${costCycleName}</td>
					  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					  <td align="center" nowrap>
					  	<span class="redlink">
					  		<f:link href="/fee/cardMembFee/detail.do?cardMembFee.feeRuleId=${feeRuleId}">明细</f:link>
						  		<f:pspan pid="cardMembFee_modify">
						  			<f:link href="/fee/cardMembFee/showModify.do?cardMembFee.feeRuleId=${feeRuleId}">修改</f:link>
						  		</f:pspan>
						  		<f:pspan pid="cardMembFee_delete">
						  			<a href="javascript:gotoUrlWithConfirm('/fee/cardMembFee/delete.do?cardMembFee.feeRuleId=${feeRuleId}','确定要删除吗?');">删除</a>
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