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
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>消费积分协议[${promtRuleDef.promtId}]的规则列表</caption>
						<tr>
							<td height="30"  colspan="4">
								<f:pspan pid="protocol_def_add">
									<input style="margin-left:30px;" type="button" value="新增规则" onclick="javascript:gotoUrl('/promotions/protocolDef/showRuleAdd.do?promtRuleDef.promtId=${promtRuleDef.promtId }');" id="input_btn3"  name="escape" />
								</f:pspan>
								<f:pspan pid="protocol_def_commit">
									<input style="margin-left:30px;" type="button" value="提交审核" onclick="javascript:submitUrl('searchForm', '/promotions/protocolDef/commitCheck.do?promtId=${promtRuleDef.promtId}', '确定要提交审核吗？');" id="input_btn3"  name="escape" />
								</f:pspan>
								<input style="margin-left:30px;" type="button" value="返回上级" onclick="javascript:gotoUrl('/promotions/protocolDef/showModify.do?promtDef.promtId=${promtRuleDef.promtId }');" id="input_btn3"  name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_PROTOCOL_DEF_RULELIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">促销规则ID</td>
			   <td align="center" nowrap class="titlebg">促销活动ID</td>
			   <td align="center" nowrap class="titlebg">金额类型</td>
			   <td align="center" nowrap class="titlebg">参考金额</td>
			   <td align="center" nowrap class="titlebg">促销规则类型</td>
			   <td align="center" nowrap class="titlebg">规则状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${promtRuleId}</td>
			  <td align="center" nowrap>${promtId}</td>
			  <td align="center" nowrap>${amtTypeName}</td>
			  <td align="center" nowrap>${fn:amount(amtRef)}</td>
			  <td align="center" nowrap>${promtRuleTypeName}</td>
			  <td align="center" nowrap>${ruleStatusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/promotions/protocolDef/ruleDetail.do?promtRuleDef.promtRuleId=${promtRuleId}">明细</f:link>
			  		<s:if test="ruleStatus == 10">
				  		<f:pspan pid="protocol_def_modify">
				  			<f:link href="/promotions/protocolDef/showRuleModify.do?promtRuleDef.promtRuleId=${promtRuleId}">编辑</f:link>
				  		</f:pspan>
				  		<f:pspan pid="protocol_def_delete">
				  			<a href="javascript:submitUrl('searchForm', '/promotions/protocolDef/ruleDelete.do?promtId=${promtId}&promtRuleId=${promtRuleId}', '确定要删除吗？');" />删除</a>
				  		</f:pspan>
			  		</s:if>
			  		<s:if test="ruleStatus == 00">
			  			<f:pspan pid="protocol_def_cancel">
			  				<a href="javascript:submitUrl('searchForm', '/promotions/protocolDef/cancelRule.do?promtId=${promtId}&promtRuleId=${promtRuleId}', '确定要注销吗？');" />注销</a>
			  			</f:pspan>
			  		</s:if>
			  		<s:if test="ruleStatus == 30">
			  			<f:pspan pid="protocol_def_start">
			  				<a href="javascript:submitUrl('searchForm', '/promotions/protocolDef/startRule.do?promtId=${promtId}&promtRuleId=${promtRuleId}', '确定要启用吗？');" />启用</a>
			  			</f:pspan>
			  		</s:if>
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