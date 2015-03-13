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
							<td width="80" align="right">积分子类型</td>
							<td><s:textfield name="pointConsmRuleDef.ptClass"/></td>	
							<td width="80" align="right">赠券子类型</td>
							<td><s:textfield name="pointConsmRuleDef.couponClass" /></td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="3">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="pointconsmruledef_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/pointExchg/pointConsmRule/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_POINTCONSMRULE_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">规则编号</td>
			   <td align="center" nowrap class="titlebg">积分类型</td>
			   <td align="center" nowrap class="titlebg">积分名称</td>
			   <td align="center" nowrap class="titlebg">积分参数</td>
			   <td align="center" nowrap class="titlebg">赠券类型</td>
			   <td align="center" nowrap class="titlebg">赠券名称</td>
			   <td align="center" nowrap class="titlebg">兑换赠券</td>
			   <td align="center" nowrap class="titlebg">规则状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td nowrap>${ptExchgRuleId}</td>
			  <td align="center" nowrap>${ptClass}</td>
			  <td align="center" nowrap>${pointClassName}</td>
			  <td align="right" nowrap>${fn:amount(ptParam)}</td>
			  <td align="center" nowrap>${couponClass}</td>
			  <td align="center" nowrap>${couponClassName}</td>
			  <td align="right" nowrap>${fn:amount(ruleParam1)}</td>
			  <td align="center" nowrap>${ruleStatusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pointExchg/pointConsmRule/detail.do?pointConsmRuleDef.ptExchgRuleId=${ptExchgRuleId}">查看</f:link>
			  		<s:if test="ruleStatus==30">
			  			<f:pspan pid="rulestatus_effect">
			  				<a href="javascript:submitUrl('searchForm', '/pointExchg/pointConsmRule/effect.do?ptExchgRuleId=${ptExchgRuleId}', '确定要生效吗？');"/>生效</a>
			  			</f:pspan>
			  		</s:if>
			  		<s:else>
			  			<f:pspan pid="rulestatus_invalid">
				  			<a href="javascript:submitUrl('searchForm', '/pointExchg/pointConsmRule/invalid.do?ptExchgRuleId=${ptExchgRuleId}', '确定要失效吗？');" />失效</a>
				  		</f:pspan>
			  		</s:else>
			  		<f:pspan pid="pointconsmruledef_delete">
			  			<a href="javascript:submitUrl('searchForm', '/pointExchg/pointConsmRule/delete.do?ptExchgRuleId=${ptExchgRuleId}', '确定要删除吗？');" />删除</a>
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