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
		
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
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
				<s:form id="searchForm" action="acctTransList.do" cssClass="validate-tip">
					<s:hidden name="formMap.cardId" />
					
					<s:hidden name="formMap.fromPage" />
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>账户交易明细
							<span class="caption_title"> |
								<s:if test='formMap.fromPage=="listDiscontinuousCard"'>
									<f:link href="/cardAcct/listDiscontinuousCard.do?goBack=goBack">返回列表</f:link>
								</s:if>
								<s:else>
									<f:link href="/cardAcct/list.do?goBack=goBack">返回列表</f:link>
								</s:else> 
							</span>
						</caption>
						<tr>					
							<td align="right">清算日期</td>
							<td>
							<s:textfield id="startDate" name="settStartDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
							<s:textfield id="endDate" name="settEndDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
							</td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARDACCT_ACCTTRANSDETAIL"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">账号</td>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <td align="center" nowrap class="titlebg">系统跟踪号</td>
			   <td align="center" nowrap class="titlebg">清算日期</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">交易类型</td>
			   <td align="center" nowrap class="titlebg">发起方</td>
			   <td align="center" nowrap class="titlebg">终端号</td>
			   <td align="center" nowrap class="titlebg">交易金额/次数</td>
			   <td align="center" nowrap class="titlebg">清算金额</td>
			   <td align="center" nowrap class="titlebg">处理状态</td>
			</tr>
			</thead>
			<s:iterator value="acctTransPage.data"> 
			<tr>
			  <td align="center" nowrap>${acctId}</td>
			  <td align="center" nowrap>${cardId}</td>
			  <td align="center" nowrap>${sysTraceNum}</td>
			  <td align="center" nowrap>${settDate}</td>
			  <td align="left" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>		  
			  <td align="center" nowrap>${transTypeName}</td>
			  <td align="left" nowrap>${merNo}-${fn:branch(merNo)}${fn:merch(merNo)}</td>		
			  <td align="center" nowrap>${termlId}</td>	
			  <td align="right" nowrap>${fn:amount(transAmt)}</td>	
			  <td align="right" nowrap><s:if test="procStatus==01">${fn:amount(settAmt)}</s:if><s:else>0.00</s:else> </td>	
			  <td align="center" nowrap>${procStatusName}</td>		
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="acctTransPage"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>