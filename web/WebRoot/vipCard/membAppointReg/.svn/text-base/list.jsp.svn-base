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
				<s:form action="list.do" id="searchForm" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">发卡机构</td>
							<td><s:textfield name="membAppointReg.cardIssuer" /></td>
							
							<td width="80" height="30" align="right">卡号</td>
							<td><s:textfield name="membAppointReg.cardId" /></td>
							
							<td width="80" height="30" align="right">会员登记资料ID</td>
							<td><s:textfield name="membAppointReg.membInfoRegId" /></td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="membappointreg_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/vipCard/membAppointReg/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MEMBAPPOINT_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">登记薄ID</td>
<%--			   <td align="center" nowrap class="titlebg">会员登记资料批次ID</td>--%>
			   <td align="center" nowrap class="titlebg">起始卡号</td>
			   <td align="center" nowrap class="titlebg">结束卡号</td>
<%--			   <td align="center" nowrap class="titlebg">会员登记资料ID</td>--%>
<%--			   <td align="center" nowrap class="titlebg">是否批量</td>--%>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">备注</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
              <td align="center" nowrap>${membAppointRegId}</td>		  
<%--			  <td align="center" nowrap>${saleBatchId}</td>--%>
			  <td align="center" nowrap>${startCardId}</td>
			  <td align="center" nowrap>${endCardId}</td>
<%--              <td align="left" nowrap>${membInfoRegId}</td>		  --%>
<%--			  <td align="center" nowrap>${appointTypeName}</td>--%>
			  <td align="center" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>${remark}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/vipCard/membAppointReg/detail.do?membAppointReg.membAppointRegId=${membAppointRegId}">查看</f:link>
<%--			    <f:pspan pid="membappointreg_modify"><f:link href="/vipCard/membAppointReg/showModify.do?membAppointReg.ptClass=${ptClass}&membAppointReg.cardIssuer=${cardIssuer}&membAppointReg.merNo=${merNo}">修改</f:link></f:pspan> --%>
			  		<f:pspan pid="membappointreg_delete"><a href="javascript:submitUrl('searchForm', '/vipCard/membAppointReg/delete.do?membAppointReg.membAppointRegId=${membAppointRegId}', '确定要删除吗？');" />删除</a></f:pspan>
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