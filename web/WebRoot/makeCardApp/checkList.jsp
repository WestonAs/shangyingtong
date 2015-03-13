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
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div id="loadingBarDiv"	style="display: none; width: 100%; height: 100%">
			<table width=100% height=100%>
				<tr>
					<td align=center >
						<center>
							<img src="${CONTEXT_PATH}/images/ajax_saving.gif" align="middle">
							<div id="processInfoDiv"
								style="font-size: 15px; font-weight: bold">
								正在处理中，请稍候...
							</div>
							<br>
							<br>
							<br>
						</center>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="contentDiv" class="userbox">
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<form id="id_checkForm" method="post" action="checkList.do">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg"><input type="checkbox" onclick="FormUtils.selectAll(this, 'ids')" />全选</td>
			   <td align="center" nowrap class="titlebg">制卡申请ID</td>
			   <td align="center" nowrap class="titlebg">卡样名称</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">卡BIN号</td>
			   <td align="center" nowrap class="titlebg">卡起始号</td>
			   <td align="center" nowrap class="titlebg">卡数量</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data">
			<tr>
			  <td><input type="checkbox" name="ids" value="${id}"/></td>	
			  <td nowrap>${appId}</td>
			  <td align="center" nowrap>${makeName}</td>
			  <td align="center" nowrap>${branchCode}-${fn:branch(branchCode) }</td>
			  <td align="center" nowrap>${binNo}</td>
			  <td align="center" nowrap>${strNo}</td>
			  <td align="center" nowrap>${cardNum}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/makeCardApp/checkDetail.do?makeCardApp.id=${id}">明细</f:link>
			  	</span>
			</tr>
			</s:iterator>
			</table>
			</form>
			<f:paginate name="page" formIndex="0"/>
		</div>
		<f:workflow adapter="makeCardAppAdapter" returnUrl="/makeCardApp/checkList.do"><s:token name="_TOKEN_MAKECARD_APP_CHECKLIST"/></f:workflow>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>