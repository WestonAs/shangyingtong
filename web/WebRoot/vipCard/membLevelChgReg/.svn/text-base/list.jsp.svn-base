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
				    <s:hidden id="id_cardIssuer" name="cardIssuer"></s:hidden>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>会员级别变更 | <span class="caption_title"><f:link href="/vipCard/vipUpgrade/list.do">升级换卡</f:link></span></caption>
						<tr>
							<td align="right">卡号</td>
							<td><s:textfield name="membLevelChgReg.cardId"/></td>
							<td align="right">会员类型</td>
							<td>
								<s:select name="membLevelChgReg.membClass"  list="membClassDefList" headerKey="" headerValue="--请选择--" listKey="membClass" listValue="className" ></s:select>
							</td>		
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="memblevelchgreg_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/vipCard/membLevelChgReg/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MEMBLEVELCHGREG_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
					    <td align="center" nowrap class="titlebg">ID</td>
						<td align="center" nowrap class="titlebg">卡号</td>
						<td align="center" nowrap class="titlebg">会员类型</td>
						<td align="center" nowrap class="titlebg">旧级别</td>
						<td align="center" nowrap class="titlebg">新级别</td>
						<td align="center" nowrap class="titlebg">发卡机构</td>
						<td align="center" nowrap class="titlebg">状态</td>
						<td align="center" nowrap class="titlebg">操作</td>
					</tr>
				</thead>
				<s:iterator value="page.data"> 
					<tr>	
					    <td align="center" nowrap>${id}</td>
						<td align="center" nowrap>${cardId}</td>
						<td align="center" nowrap>${membClass}</td>
						<td align="center" nowrap>${origLevel}</td>
						<td align="center" nowrap>${curLevel}</td>
						<td align="center" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
						<td align="center" nowrap>${procStatusName}</td>
					    <td align="center" nowrap>
					  	<span class="redlink">
					  		<f:link href="/vipCard/membLevelChgReg/detail.do?membLevelChgReg.id=${id}">查看</f:link>
					  		<f:pspan pid="memblevelchgreg_delete">
					  			<a href="javascript:submitUrl('searchForm', '/vipCard/membLevelChgReg/delete.do?membLevelChgReg.id=${id}', '确定要删除吗？');" />删除</a>
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