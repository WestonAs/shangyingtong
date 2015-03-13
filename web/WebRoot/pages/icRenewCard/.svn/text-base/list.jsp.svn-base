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
		
		<script type="text/javascript">		
		$(function(){
			//$('#searchForm')[0].action = "list" + $('#searchForm_actionSubName')[0].value + ".do";			
		})
		</script>
		
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
							<td align="right">换卡批次</td>
							<td><s:textfield name="icRenewCardReg.id" cssClass="{digit:true}"/></td>							
							<td align="right">旧卡卡号</td>
							<td><s:textfield name="icRenewCardReg.oldCardId" cssClass="{digit:true}"/></td>							
							<td align="right">新卡卡号</td>
							<td><s:textfield name="icRenewCardReg.newCardId" cssClass="{digit:true}"/></td>							
						</tr>
						<tr>
							<td align="right">状态</td>
							<td>
								<s:select name="icRenewCardReg.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="icRenewCardmgr_add">
									<input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/pages/icRenewCard/showAdd.do');" id="input_btn3"  name="escape" />
								</f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_IC_REWCARD_REG_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">换卡批次</td>
			   <td align="center" nowrap class="titlebg">旧卡卡号</td>
			   <td align="center" nowrap class="titlebg">新卡卡号</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">换卡类型</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">冲正标志</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${id}</td>
			  <td align="center" nowrap>${oldCardId}</td>
			  <td align="center" nowrap>${newCardId}</td>
			  <td align="center" nowrap>${cardBranch}-${fn:branch(cardBranch)}</td>
			  <td align="center" nowrap>${renewTypeName}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>${reversalFlagName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/icRenewCard/detail.do?icRenewCardReg.id=${id}">明细</f:link>			  		
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