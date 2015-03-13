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
		
		<script>
			$(function(){
				Selector.selectArea('id_areaName', 'id_areaCode', true);
			});
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
							<td width="80" align="right">发卡机构</td>
							<td><s:textfield name="cardToMerch.branchCode"/></td>
							
							<td width="80" align="right">商户</td>
							<td><s:textfield name="cardToMerch.merchId"/></td>
							
							<td width="80" align="right">代理机构</td>
							<td><s:textfield name="cardToMerch.proxyId" /></td>
						</tr>
						<tr>
							<td width="80" align="right">发卡机构地区</td>
							<td><s:hidden id="id_areaCode" name="areaCode" /><s:textfield id="id_areaName" name="areaName" /></td>
							<td width="80" align="right">状态</td>
							<td><s:select name="cardToMerch.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select></td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm');$('#id_areaCode').val('');$('#id_areaName').val('');" name="escape" />
								<f:pspan pid="cardmerchmgr_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/pages/cardMerch/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARD_MERCH_LIST"/>
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
			   <td align="center" nowrap class="titlebg">代理机构</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${branchCode}-${branchName}</td>
			  <td nowrap>${merchId}-${merchName}</td>
			  <td nowrap>${proxyId}-${proxyName}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/cardMerch/detail.do?cardToMerch.branchCode=${branchCode}&cardToMerch.merchId=${merchId}">查看</f:link>
			  		<s:if test="status == 00">
				  		<f:pspan pid="cardmerchmgr_cancel">
				  			<a href="javascript:submitUrl('searchForm', '/pages/cardMerch/cancel.do?merchId=${merchId}&branchCode=${branchCode}', '确定要注销吗？');" />注销</a>
				  		</f:pspan>
			  		</s:if>
			  		<s:else>
				  		<f:pspan pid="cardmerchmgr_activate">
				  			<a href="javascript:submitUrl('searchForm', '/pages/cardMerch/activate.do?merchId=${merchId}&branchCode=${branchCode}', '确定要生效吗？');" />生效</a>
				  		</f:pspan>
			  		</s:else>
			  		<f:pspan pid="cardmerchmgr_delete">
			  			<a href="javascript:submitUrl('searchForm', '/pages/cardMerch/delete.do?merchId=${merchId}&branchCode=${branchCode}', '确定要删除吗？');" />删除</a>
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