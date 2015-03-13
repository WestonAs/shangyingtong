<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 transRevocationitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transRevocationitional.dtd">
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
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<s:hidden name="transRevocation.revcSn"></s:hidden>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">系统跟踪号</td>
							<td><s:textfield name="transRevocation.sysTraceNum" /></td>
							<td align="right">清算日期</td>
							<td>
								<s:textfield id="startDate" name="settStartDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="endDate" name="settEndDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
							</td>
							<td align="right">交易类型</td>
							<td>
							<s:select name="transRevocation.revcType" list="transTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">卡号</td>
							<td><s:textfield name="transRevocation.cardId" cssClass="{digitOrLetter:true}"/></td>
							<td align="right">发卡机构号</td>
							<td><s:textfield name="transRevocation.cardIssuer" cssClass="{digitOrLetter:true}"/></td>
							<td align="right">发起方</td>
							<td><s:textfield name="transRevocation.initrNo" cssClass="{digitOrLetter:true}"/></td>
						</tr>
						<tr>
							<td align="right">处理状态</td>
							<td>
							<s:select name="transRevocation.revcStatus" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_TRANSREVOACTIONQUERY_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">系统跟踪号</td>
			   <td align="center" nowrap class="titlebg">原系统跟踪号</td>
			   <td align="center" nowrap class="titlebg">清算日期</td>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <td align="center" nowrap class="titlebg">交易类型</td>
			   <td align="center" nowrap class="titlebg">原交易类型</td>
			   <td align="center" nowrap class="titlebg">相关金额</td>
			   <td align="center" nowrap class="titlebg">处理状态</td>
			   <s:if test="#session.SESSION_USER.role.roleType!=40">
			   <td align="center" nowrap class="titlebg">操作</td>
			   </s:if>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td style="display: none">${revcSn}</td>
			  <td align="center" nowrap>${sysTraceNum}</td>
			  <td align="center" nowrap>${origSysTraceNum}</td>
			  <td align="center" nowrap>${settDate}</td>
			  <td align="center" nowrap>${cardId}</td>
			  <td align="center" nowrap>${revcTypeName}</td>
			  <td align="center" nowrap>${origTransTypeName}</td>
			  <td align="right" nowrap>${fn:amount(revcAmt)}</td>	
			  <td align="center" nowrap>${revcStatusName}</td>		  
			  <s:if test="#session.SESSION_USER.role.roleType!=40">
			  <td align="center" nowrap>
			    <span class="redlink">
			  		<a href="javascript:openContextDialog('/transRevocationQuery/detail.do?transRevocation.revcSn=${revcSn}');" />明细</a>
			  	</span>
			  </td>
			  </s:if>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>