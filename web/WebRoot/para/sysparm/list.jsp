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
							<td align="right">参数代码</td>
							<td><s:textfield name="sysparm.paraCode" cssClass="{digitOrLetter:true}"/></td>
							
							<td align="right">参数名称</td>
							<td><s:textfield name="sysparm.paraName"/></td>
							
							<td align="right">是否显示</td>
							<td><s:select list="#{'':'--请选择--','1':'是', '0':'否'}" name="sysparm.showFlag"/></td>
						</tr>
	
						<tr>
							<td align="right">允许修改</td>
							
							<td><s:select list="#{'':'--请选择--','1':'是', '0':'否'}" name="sysparm.modifyFlag"/></td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_SYSPARM_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">参数代码</td>
			   <td align="center" nowrap class="titlebg">参数名称</td>
			   <td align="center" nowrap class="titlebg">参数值</td>
			   <td align="center" nowrap class="titlebg">是否显示</td>
			   <td align="center" nowrap class="titlebg">允许修改</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${paraCode}</td>
			  <td align="center" nowrap>${paraName}</td>
			  <td align="center" nowrap>${paraValue}</td>
			  <td align="center" nowrap><s:if test="showFlag == 1">是</s:if><s:else>否</s:else></td>
			  <td align="center" nowrap><s:if test="modifyFlag == 1">是</s:if><s:else>否</s:else></td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/para/sysparm/detail.do?sysparm.paraCode=${paraCode}">查看</f:link>
			  		<s:if test="modifyFlag == 1">
			  			<f:pspan pid="syspara_modify"><f:link href="/para/sysparm/showModify.do?sysparm.paraCode=${paraCode}">编辑</f:link></f:pspan>
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