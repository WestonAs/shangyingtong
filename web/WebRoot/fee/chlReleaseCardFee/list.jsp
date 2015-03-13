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
							<td align="right">分支机构</td>
							<td>
								<s:if test="showCenter">
									<s:textfield name="releaseCardFee.chlName"></s:textfield>
								</s:if>
								<s:else>
									<s:select name="releaseCardFee.chlCode" headerKey="" headerValue="--请选择--" list="chlList" listKey="branchCode" listValue="branchName"></s:select>
								</s:else>
							</td>
							<td align="right">发卡机构</td>
							<td>
								<s:if test="showCard">
									<s:select name="releaseCardFee.branchCode" headerKey="" headerValue="--请选择--" list="branchList" listKey="branchCode" listValue="branchName"></s:select>
								</s:if>
								<s:else>
									<s:textfield name="releaseCardFee.branchName"></s:textfield>
								</s:else>
							</td>
							<td align="right">计费内容</td>
							<td>
								<s:select name="releaseCardFee.transType" headerKey="" headerValue="--请选择--" list="transTypeList" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">计费方式</td>
							<td>
								<s:select name="releaseCardFee.feeType" headerKey="" headerValue="--请选择--" list="feeTypeList" listKey="value" listValue="name"></s:select>
							</td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="releasecardfeechl_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/fee/chlReleaseCardFee/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CHLRELEASECARDFEE_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">分支机构</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">计费内容</td>
			   <td align="center" nowrap class="titlebg">计费方式</td>
			   <td align="center" nowrap class="titlebg">计费周期</td>
			   <td align="center" nowrap class="titlebg">金额段</td>
			   <td align="center" nowrap class="titlebg">收费金额/费率</td>
			   <td align="center" nowrap class="titlebg">更新时间</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td style="display: none">${cardBin}</td>
			  <td align="center" nowrap>${chlName}</td>
			  <td align="center" nowrap>${branchName}</td>
			  <td align="center" nowrap>${transTypeName}</td>
			  <td align="center" nowrap>${feeTypeName}</td>
			  <td align="center" nowrap>${costCycleName}</td>
			  <td align="right" nowrap>
			  	${fn:amount(ulMoney)}
			  </td>
			  <td align="right" nowrap>
			  	<s:if test="feeType == 1||feeType == 3||feeType== A">${fn:amount(feeRate)}</s:if><s:else>${fn:percentPre(feeRate, 4)}</s:else>
			  </td>
			  <td align="center" nowrap><s:date name="updateTime"/></td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<s:if test="chlCode==loginBranchCode">
			  		<f:pspan pid="releasecardfeechl_modify"><f:link href="/fee/chlReleaseCardFee/showModify.do?key.branchCode=${branchCode}&key.cardBin=${cardBin}&key.transType=${transType}&key.merchId=${merchId}&key.ulMoney=${ulMoney}">修改</f:link></f:pspan>
			  		<f:pspan pid="releasecardfeechl_delete">
			  			<a href="javascript:submitUrl('searchForm','/fee/chlReleaseCardFee/delete.do?branchCode=${branchCode}&cardBin=${cardBin}&transType=${transType}&merchId=${merchId}&ulMoney=${ulMoney}','确定要删除吗?');">删除</a>
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