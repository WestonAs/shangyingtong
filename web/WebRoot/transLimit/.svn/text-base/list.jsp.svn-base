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
							<td width="80" height="30" align="right">发卡机构</td>
							<td><s:textfield name="transLimit.branchName"/></td>
							<td width="80" height="30" align="right">商户</td>
							<td><s:textfield name="transLimit.merchName"/></td>
							<td width="80" height="30" align="right">卡BIN</td>
							<td><s:textfield name="transLimit.cardBin"/></td>
						</tr>
						<tr>
							<td width="80" align="right">状态</td>
							<td><s:select id="Id_status" name="transLimit.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select></td>
							<td height="30" colspan="4" >
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="translimit_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/transLimit/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_TRANSLIMIT_LIST"/>
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
			   <td align="center" nowrap class="titlebg">卡BIN</td>
			   <td align="center" nowrap class="titlebg">状态</td>			   
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td align="left" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
			  <td align="left" nowrap>${merNo}-${fn:merch(merNo)}</td>
			  <td align="center" nowrap>${cardBin}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/transLimit/detail.do?transLimit.cardIssuer=${cardIssuer}&transLimit.merNo=${merNo}&transLimit.cardBin=${cardBin}&transLimit.transType=${transType}">查看</f:link>
			  		<f:pspan pid="translimit_modify">	
				  		<s:if test="status==10" >
				  			<a href="javascript:submitUrl('searchForm','/transLimit/modify.do?cardIssuer=${cardIssuer}&merNo=${merNo}&cardBin=${cardBin}&transType=${transType}','确定要设置为正常吗?');">设置为正常</a>
				  		</s:if>
				  		<s:else>
				  			<a href="javascript:submitUrl('searchForm','/transLimit/modify.do?cardIssuer=${cardIssuer}&merNo=${merNo}&cardBin=${cardBin}&transType=${transType}','确定要设置为注销吗?');">设置为注销</a>
				  		</s:else>
				  	</f:pspan>
				  	<f:pspan pid="translimit_delete">
			  			<a href="javascript:submitUrl('searchForm', '/transLimit/delete.do?cardIssuer=${cardIssuer}&merNo=${merNo}&cardBin=${cardBin}&transType=${transType}', '确定要删除吗？');" />删除</a>
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