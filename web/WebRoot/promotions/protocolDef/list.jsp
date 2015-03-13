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
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">协议积分名</td>
							<td>
								<s:textfield name="promtDef.promtName"/>
							</td>
							
							<td align="right">参与机构</td>
							<td><s:textfield name="promtDef.pinstId"/></td>
							
							<td align="right">交易类型类型</td>
							<td>
								<s:select name="promtDef.transType" list="transTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select>
							</td>
						</tr>
						<tr>
							<td align="right">状态</td>
							<td>
								<s:select name="promtDef.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select>
							</td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="protocol_def_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/promotions/protocolDef/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_PROTOCOL_DEF_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">消费积分协议名</td>
			   <td align="center" nowrap class="titlebg">参与机构类型</td>
			   <td align="center" nowrap class="titlebg">参与机构</td>
			   <td align="center" nowrap class="titlebg">交易类型</td>
			   <td align="center" nowrap class="titlebg">生效日期</td>
			   <td align="center" nowrap class="titlebg">失效日期</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${promtName}</td>
			  <td align="center" nowrap>${pinstTypeName}</td>
			  <td nowrap>${pinstId}-<s:if test="pinstId.length() == 8">${fn:branch(pinstId)}</s:if><s:else>${fn:merch(pinstId)}</s:else></td>
			  <td align="center" nowrap>${transTypeName}</td>
			  <td align="center" nowrap>${effDate}</td>
			  <td align="center" nowrap>${expirDate}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/promotions/protocolDef/detail.do?promtDef.promtId=${promtId}">明细</f:link>
			  		<s:if test="status == 10">
				  		<f:pspan pid="protocol_def_modify"><f:link href="/promotions/protocolDef/showModify.do?promtDef.promtId=${promtId}">编辑</f:link></f:pspan>
				  		<f:pspan pid="protocol_def_delete">
			  				<a href="javascript:submitUrl('searchForm', '/promotions/protocolDef/delete.do?promtId=${promtId}', '确定要删除吗？');" />删除</a>
				  		</f:pspan>
				  		<f:pspan pid="protocol_def_commit">
				  			<a href="javascript:submitUrl('searchForm', '/promotions/protocolDef/commitCheck.do?promtId=${promtId}', '确定要提交审核吗？');" />提交审核</a>
				  		</f:pspan>
			  		</s:if>
			  		<s:if test="status == 00">
			  			<f:pspan pid="protocol_def_cancel">
			  				<a href="javascript:submitUrl('searchForm', '/promotions/protocolDef/cancel.do?promtId=${promtId}', '确定要注销吗？');" />注销</a>
			  			</f:pspan>
			  		</s:if>
			  		<s:if test="status == 30">
			  			<f:pspan pid="protocol_def_start">
			  				<a href="javascript:submitUrl('searchForm', '/promotions/protocolDef/start.do?promtId=${promtId}', '确定要启用吗？');" />启用</a>
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