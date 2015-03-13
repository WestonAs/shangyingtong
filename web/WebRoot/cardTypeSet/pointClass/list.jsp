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
		
		<f:js src="/js/biz/cardTypeSet/pointClass.js"/>

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
							<td width="80" height="30" align="right">积分名称</td>
							<td><s:textfield name="pointClassDef.className" /></td>
							
							<td width="80" height="30" align="right">积分使用方法</td>
							<td>
								<s:select id="" name="pointClassDef.ptUsage" list="ptUsageList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select>
							</td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="pointclass_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/cardTypeSet/pointClass/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_POINTCLASS_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">积分类型</td>
			   <td align="center" nowrap class="titlebg">积分名称</td>
			   <td align="center" nowrap class="titlebg">积分使用方法</td>
			   <td align="center" nowrap class="titlebg">联名机构类型</td>
			   <td align="center" nowrap class="titlebg">联名机构编号</td>
			   <td align="center" nowrap class="titlebg">联名机构名称</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td>${ptClass}</td>
			  <td align="left" nowrap>${className}</td>
			  <td align="center" nowrap>${ptUsageName}</td>
			  <td align="center" nowrap>${jinstTypeName}</td>
			  <td align="center" nowrap>${jinstId}</td>
			  <td align="center" nowrap>${fn:branch(jinstId)}${fn:merch(jinstId)}${groupName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/cardTypeSet/pointClass/detail.do?pointClassDef.ptClass=${ptClass}">查看</f:link>
			  		<s:if test="cardIssuer==loginBranchCode">
			  		<f:pspan pid="pointclass_modify"><f:link href="/cardTypeSet/pointClass/showModify.do?pointClassDef.ptClass=${ptClass}">修改</f:link></f:pspan>
			  		<f:pspan pid="pointclass_delete">	
				  			<a href="javascript:submitUrl('searchForm','/cardTypeSet/pointClass/delete.do?pointClassDef.ptClass=${ptClass}','确定要删除吗?');">删除</a>
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