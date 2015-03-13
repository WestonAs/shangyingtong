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
							<td align="right">发卡机构</td>
							<td>
							<s:if test="showCard">
								<s:select name="cardMerchRemitAccount.branchCode" list="branchList" listKey="branchCode" listValue="branchName"></s:select>
							</s:if>
							<s:else>
								<s:textfield  id="branchName" name="merchFeeDTotal.branchName"></s:textfield>
							</s:else>
							</td>
							<td align="right">商户</td>
							<td>
							<s:if test="showMerch">
							<s:select name="cardMerchRemitAccount.merchId" headerKey="" headerValue="" list="merchList" listKey="merchId" listValue="merchName"></s:select>
							</s:if>
							<s:else>
								<s:textfield id="merchName" name="cardMerchRemitAccount.merchName"></s:textfield>
							</s:else>
							</td>
							<td align="right">划账方式</td>
							<td>
								<s:select name="cardMerchRemitAccount.xferType" list="xferTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid = "remitAccount_add" >
									<input style="margin-left:30px;" type="button" value="新增"  name="escape" id="input_btn3" onclick="javascript:gotoUrl('/cardMerchRemitAcc/showAdd.do');"/>
								</f:pspan> 
							</td>
						</tr>
						
					</table>
					<s:token name="_TOKEN_CARDMERCHREMITACC_LIST" />
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">商户</td>
			   <td align="center" nowrap class="titlebg">划账方式</td>
			   <td align="center" nowrap class="titlebg">周期日期参数</td>
			   <td align="center" nowrap class="titlebg">金额上限参数</td>
			   <td align="center" nowrap class="titlebg">生效日期</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="left" nowrap>${branchCode}-${fn:branch(branchCode)}</td>
			  <td align="left" nowrap>${merchId}-${fn:merch(merchId)}</td>
			  <td align="center" nowrap>${xferTypeName}</td>
			  <td align="center" nowrap>${dayOfCycleName}</td>
			  <td align="right" nowrap>${fn:amount(ulMoney)}</td>
			  <td align="center" nowrap>${effDate}</td>
			  <td align="center" nowrap>
			  <span class="redlink">
		  		<f:link href="/cardMerchRemitAcc/detail.do?cardMerchRemitAccount.branchCode=${branchCode}&cardMerchRemitAccount.merchId=${merchId}&cardMerchRemitAccount.branchName=${branchName}&cardMerchRemitAccount.merchName=${merchName}">查看</f:link>
		  		<f:pspan pid="remitAccount_modify" >
		  			<f:link href="/cardMerchRemitAcc/showModify.do?cardMerchRemitAccount.xferType=${xferType}&cardMerchRemitAccount.branchCode=${branchCode}&cardMerchRemitAccount.merchId=${merchId}&cardMerchRemitAccount.curCode=${curCode}">编辑</f:link>
		  		</f:pspan>
		  		<f:pspan pid="remitAccount_delete" >
		  			<a href="javascript:submitUrl('searchForm', '/cardMerchRemitAcc/delete.do?branchCode=${branchCode}&merchId=${merchId}', '确定要删除吗？');" />删除</a>
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