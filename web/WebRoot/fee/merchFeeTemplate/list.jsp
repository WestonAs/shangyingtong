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
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

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
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:if test="showCard">
									<s:textfield  id="branchName" name="merchFeeTemplateDetail.branchName"></s:textfield>
								</s:if>
								<s:else>
									<s:select name="merchFeeTemplateDetail.branchCode" headerKey="" headerValue="--请选择--" list="branchList" listKey="branchCode" listValue="branchName"></s:select>
								</s:else>
							</td>
							<td width="80" height="30" align="right">模板名称</td>
							<td>
								<s:textfield name="merchFeeTemplateDetail.templateName"></s:textfield>
							</td>
							<td align="right">卡BIN</td>
							<td>
								<s:textfield name="merchFeeTemplateDetail.cardBin" cssClass="{digitOrLetter:true}"></s:textfield>
							</td>
						</tr>
						<tr>
							<td align="right">计费方式</td>
							<td>
								<s:select name="merchFeeTemplateDetail.feeType" headerKey="" headerValue="--请选择--" list="feeTypeList" listKey="value" listValue="name"></s:select>
							</td>
							<td align="right">交易类型</td>
							<td>
								<s:select name="merchFeeTemplateDetail.transType" headerKey="" headerValue="--请选择--" list="transTypeList" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td>&nbsp</td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input class="ml30" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="merchfeetemplate_add"><input class="ml30" type="button" value="新增" onclick="javascript:gotoUrl('/fee/merchFeeTemplate/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MERCH_FEE_TEMPLATE_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap="nowrap" class="titlebg">模板编号</td>
			   <td align="center" nowrap="nowrap" class="titlebg">模板名称</td>
			   <td align="center" nowrap="nowrap" class="titlebg">发卡机构</td>
			   <td align="center" nowrap="nowrap" class="titlebg">交易类型</td>
			   <td align="center" nowrap="nowrap" class="titlebg">卡Bin</td>
			   <td align="center" nowrap="nowrap" class="titlebg">计费方式</td>
			   <td align="center" nowrap="nowrap" class="titlebg">金额段</td>
			   <td align="center" nowrap="nowrap" class="titlebg">费率</td>
			   <td align="center" nowrap="nowrap" class="titlebg">更新时间</td>
			   <td align="center" nowrap="nowrap" class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="left" nowrap="nowrap">${templateId}</td>
			  <td align="left" nowrap="nowrap">${templateName}</td>
			  <td nowrap="nowrap">${branchCode}-${fn:branch(branchCode)}</td>
			  <td align="center" nowrap="nowrap">${transTypeName}</td>
			  <td align="center" nowrap="nowrap">${cardBin}</td>
			  <td align="center" nowrap="nowrap">${feeTypeName}</td>
			  <td align="right" nowrap="nowrap">${fn:amount(ulMoney)}</td>
			  <td align="right" nowrap="nowrap"><s:if test="feeType == 0">${feeRate}</s:if><s:else>${fn:percentPre(feeRate, 4)}</s:else></td>
			  <td align="center" nowrap="nowrap"><s:date name="updateTime"/></td>
			  <td align="center" nowrap="nowrap">
			  	<span class="redlink">
			  		<f:pspan pid="merchfeetemplate_modify"><f:link href="/fee/merchFeeTemplate/showModify.do?merchFeeTemplateDetail.templateId=${templateId}&merchFeeTemplateDetail.branchCode=${branchCode}&merchFeeTemplateDetail.cardBin=${cardBin}&merchFeeTemplateDetail.transType=${transType}&merchFeeTemplateDetail.ulMoney=${ulMoney}">修改</f:link></f:pspan>
			  		<f:pspan pid="merchfeetemplate_delete">
			  			<a href="javascript:submitUrl('searchForm','/fee/merchFeeTemplate/delete.do?branchCode=${branchCode}&templateId=${templateId}&cardBin=${cardBin}&transType=${transType}&ulMoney=${ulMoney}','确定要删除吗?');">删除</a>
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