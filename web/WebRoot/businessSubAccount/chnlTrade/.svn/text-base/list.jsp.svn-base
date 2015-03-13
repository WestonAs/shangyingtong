<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			function setResult(id, result){
				var tip = "";
				if(result == 'S'){
					tip = "置结果前确认渠道交易成功，确认操作?";
				}else{
					tip = "置结果前确认渠道交易失败，确认操作?";
				}
				if(confirm(tip)){
					gotoUrl('/businessSubAccount/chnlTrade/setResult.do?channelTrade.id='+id+'&channelTrade.result='+result);
				}
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">创建日期</td>
							<td>
								<s:textfield name="channelTrade.startCreateDate" onfocus="WdatePicker()" cssStyle="width:70px"></s:textfield> - <s:textfield name="channelTrade.endCreateDate" onfocus="WdatePicker()" cssStyle="width:70px"></s:textfield>
							</td>
							<td align="right">金额</td>
							<td><s:textfield name="channelTrade.amount" id="amount" cssClass="{number:true}"/></td> 
							<td align="right">交易状态</td>
							<td>
								<s:select list="returnStates" listKey="value" listValue="name" name="channelTrade.returnState" headerKey="" headerValue="--请选择--"></s:select>
							</td> 
						</tr>
						<tr>
							<td align="right">交易类型</td>
							<td>
								<s:select list="tradeTypes" listKey="value" listValue="name" name="channelTrade.tradeType" headerKey="" headerValue="--请选择--"></s:select>
							</td>
							<td align="right">银行账号</td>
							<td><s:textfield name="channelTrade.acctNo" cssClass="{digitOrLetter:true}" maxlength="32"/></td>
							<td align="right">银行户名</td>
							<td><s:textfield name="channelTrade.acctName" maxlength="32"/></td>
						</tr>
						<tr> 
						<td></td>
							<td height="30" colspan="3">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<s:form action="check.do" id="checkForm">
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">渠道交易编号</td>
			   <td align="center" nowrap class="titlebg">交易类型</td>
			   <td align="center" nowrap class="titlebg">交易单号</td>
			   <td align="center" nowrap class="titlebg">交易金额</td>
			   <td align="center" nowrap class="titlebg">行别</td>
			   <td align="center" nowrap class="titlebg">银行账号</td> 
			   <td align="center" nowrap class="titlebg">银行户名</td>			   
			   <td align="center" nowrap class="titlebg">创建时间</td>
			   <td align="center" nowrap class="titlebg">回盘时间</td>
			   <td align="center" nowrap class="titlebg">交易状态</td>
			   <td align="center" nowrap class="titlebg">交易结果</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${id}</td>
			  <td align="center" nowrap>${tradeTypeName}</td>
			  <td align="center" nowrap>${refId}</td>
			  <td align="center" nowrap>${fn:amount(amount)}</td>
			  <td align="center" nowrap>${bankTypeName}</td>
			  <td align="center" nowrap>${acctNo}</td>
			  <td align="center" nowrap>${acctName}</td>
			  <td align="center" nowrap><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td align="center" nowrap><s:date name="returnTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td align="center" nowrap>${returnStateName}</td>
			  <td align="center" nowrap>${resultName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/businessSubAccount/chnlTrade/detail.do?channelTrade.id=${id}">查看</f:link>
			  		<c:if test="${returnState eq '10'}">
				  		<a href href="javascript:void(0)" onclick="setResult(${id}, 'S')">手工置成功</a>
				  		<a href href="javascript:void(0)" onclick="setResult(${id}, 'F')">手工置失败</a>
			  		</c:if>
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		<s:token name="_TOKEN_TRANS_CHECK" />
		</s:form>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>