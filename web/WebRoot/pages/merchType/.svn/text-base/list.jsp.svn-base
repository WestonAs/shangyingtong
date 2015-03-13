<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ page import="flink.util.Paginater"%>
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
							<td align="right">类型代码</td>
							<td><s:textfield name="merchType.merchType" cssClass="{digitOrLetter:true}"/></td>
							
							<td align="right">类型名称</td>
							<td><s:textfield name="merchType.typeName"/></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="branchmgr_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/pages/merchType/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MERCH_TYPE_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">商户类型代码</td>
			   <td align="center" nowrap class="titlebg">类型名称</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${merchType}</td>
			  <td align="center" nowrap>${typeName}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/merchType/detail.do?merchType.merchType=${merchType}">查看</f:link>
			  		<f:pspan pid="merchanttypemgr_modify"><f:link href="/pages/merchType/showModify.do?merchType.merchType=${merchType}">编辑</f:link></f:pspan>
			  		<s:if test="status==00">
			  			<f:pspan pid="merchanttypemgr_cancel">
			  				<a href="javascript:submitUrl('searchForm', '/pages/merchType/cancel.do?merchTypeCode=${merchType}', '确定要注销吗？');" />注销</a>
			  			</f:pspan>
			  		</s:if>
			  		<s:else>
			  			<f:pspan pid="merchanttypemgr_activate">
				  			<a href="javascript:submitUrl('searchForm', '/pages/merchType/activate.do?merchTypeCode=${merchType}', '确定要生效吗？');" />生效</a>
				  		</f:pspan>
			  		</s:else>
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