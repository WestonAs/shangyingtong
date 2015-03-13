<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
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
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<f:js src="/js/biz/rebateRule/modify.js"/>

		<script type="text/javascript" src="rebateRule.js"></script>
		<script>
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<s:hidden name="rebateRule.rebateId"/>
						<s:hidden id="isCommon" name="rebateRule.isCommon"/>
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td>
								<s:textfield name="rebateRule.cardBranch" cssClass="{required:true} readonly" readonly="readonly"/>
							</td>
							<td>发卡机构名称</td>
							<td>
								<input value="${fn:branch(rebateRule.cardBranch) }" class="{required:true} readonly" readonly="readonly" />
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">返利规则名称</td>
							<td>
								<s:textfield name="rebateRule.rebateName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入规则名</span>
							</td>
							
							<td width="80" height="30" align="right">计算方式</td>
							<td>
								<s:select name="rebateRule.calType" list="calTypeList" 
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name"  cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>	
						<tr>
							<td height="30" align="right">返利方式</td>
							<td>
								<s:select id="rebateType" name="rebateRule.rebateType" list='#{"0":"一次性返利", "1":"周期返利"}' 
									onchange="changeRebateType()" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>
						
						<tr id="periodInfoTr1" style="display: none;">
							<td height="30" align="right">返利次数（期数）</td>
							<td>
								<s:textfield name="rebateRule.periodNum" cssClass="{required:true, digit:true, min:1}"/>
								<span class="field_tipinfo">请输入大于0的整数</span>
							</td>
							<td height="30" align="right">周期类型</td>
							<td>
								<s:select name="rebateRule.periodType" list='#{"0":"按月返利"}' cssClass="{required:true}" >
								</s:select>
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>
						<tr id="periodInfoTr2" style="display: none;">
							<td height="30" align="right">分期返利生效时间</td>
							<td>
								<s:textfield name="rebateRule.periodStartTime" onclick="WdatePicker();" cssClass="{required:true, date:true}" />
								<span class="field_tipinfo"></span>
							</td>
							<td height="30" align="right">分期返利失效时间</td>
							<td>
								<s:textfield name="rebateRule.periodEndTime" onclick="WdatePicker();" cssClass="{required:true, date:true}" />
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						<tr id="periodInfoTr3" style="display: none;">
							<td height="30" align="right">首次是否立即返利</td>
							<td>
								<s:select name="rebateRule.periodImmedRebate" list='#{"0":"否", "1":"是"}' cssClass="{required:true}" >	</s:select>
								<span class="field_tipinfo"></span>
							</td>
							<td height="30" align="right">上期最低交易金额</td>
							<td>
								<s:textfield name="rebateRule.periodTransAmt" cssClass="{required:true, num:true}"/>
								<span class="field_tipinfo">请输入整数或小数金额</span>
							</td>
						</tr>					
					</table>
					
					<hr style="width: 98%; margin: 10px 0px 10px 0px;"/>
					<table class="form_grid" width="60%" border="0" cellspacing="3" cellpadding="0">
						<tr>
						   <td align="center" nowrap class="titlebg">返利分段号</td>
						   <td align="center" nowrap class="titlebg">分段金额上限</td>
						   <td align="center" nowrap class="titlebg">每张卡返利比%/每张卡返利金额</td>
						</tr>
						
						<s:iterator value="rebateRuleDetailList"> 
						<tr id='idDetail_${rebateSect}'>
							<td align="center">${rebateSect}</td>
							<td align="center">
								<s:textfield name="rebateUlimit" />
							</td>
							<td align="center" >
								<s:textfield name="rebateRate"/>
							</td>
						</tr>
						</s:iterator>
						
						<!-- 
						
						<tr id="idDetail_1">
							<td align="center">1</td>
							<td align="center">
								<s:textfield name="rebateUlimitArray"/>
							</td>
							<td align="center" >
								<s:textfield name="rebateRateArray"/>
							</td>
						</tr>
						 -->
						 
						<tr id="idLink" style="line-height: 30px;">
							<td align="left" colspan="4" style="padding-left: 20px;">
								<span class="redlink"><a href="javascript:void(0);" onclick="javascript:addItem();">增加一项</a><a class="ml30" href="javascript:void(0);" onclick="javascript:deleteItem();">删除一项</a></span>
							</td>
						</tr>
						<tr style="margin-top: 30px;">
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="提交" id="input_btn2"  name="ok" onclick="submitForm();" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/customerRebateMgr/rebateRule/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_REBATERULE_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>