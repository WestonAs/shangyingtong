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
						    <td width="80" height="30" align="right">抽奖活动</td>
							<td>
								<s:select name="prizeDef.drawId" list="drawList" headerKey="" headerValue="--请选择--" listKey="drawId" listValue="drawName" ></s:select>
							</td>
							<td align="right">奖项名称</td>
							<td><s:textfield name="prizeDef.prizeName"/></td>
							<td align="right">参与机构</td>
							<td><s:textfield name="prizeDef.jinstId"/></td>
							<td align="right">奖品类型</td>
							<td>
								<s:select name="prizeDef.awdType" list="awdTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
	
						<tr>
						    <td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="prize_def_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/lottery/prizeDef/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_LOTTER_PRIZE_DEF_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">奖项编号</td>
			   <td align="center" nowrap class="titlebg">奖项名称</td>
			   <td align="center" nowrap class="titlebg">抽奖活动ID</td>
               <td align="center" nowrap class="titlebg">参与机构类型</td>
			   <td align="center" nowrap class="titlebg">参与机构</td>
			   <td align="center" nowrap class="titlebg">奖品类型</td>
			   <td align="center" nowrap class="titlebg">奖品子类型</td>
			   <td align="center" nowrap class="titlebg">奖品总数</td>
			   <td align="center" nowrap class="titlebg">剩余奖品总数</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${prizeNo}</td>
			  <td align="center" nowrap>${prizeName}</td>
			  <td align="center" nowrap>${drawId}</td>
			  <td align="center" nowrap>${jinstTypeName}</td>
			  <td align="center" nowrap>${jinstId}-${fn:branch(jinstId)}${fn:merch(jinstId)}</td>
			  <td align="center" nowrap>${awdTypeName}</td>
			  <td align="center" nowrap>${classId}</td>
			  <td align="center" nowrap>${totalAwdCnt}</td>
			  <td align="center" nowrap>${leftAwdCnt}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/lottery/prizeDef/detail.do?prizeDef.drawId=${drawId}&prizeDef.prizeNo=${prizeNo}">明细</f:link>
			  		<f:pspan pid="prize_def_modify"><f:link href="/lottery/prizeDef/showModify.do?prizeDef.drawId=${drawId}&prizeDef.prizeNo=${prizeNo}">编辑</f:link></f:pspan>
		  			<f:pspan pid="prize_def_delete">
		  				<a href="javascript:submitUrl('searchForm', '/lottery/prizeDef/delete.do?drawId=${drawId}&prizeNo=${prizeNo}', '确定要删除吗？');" />删除</a>
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