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
							<td align="right">编号</td>
							<td><s:textfield name="pointAccReg.pointAccId"/></td>
							<td align="right">业务类型</td>
							<td>
							<s:select name="pointAccReg.transType" list="pointAccTransTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td align="right">状态</td>
							<td><s:select name="pointAccReg.status" list="pointAccStatusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<s:if test="!showCard">
						<tr>
							<td align="right">机构编号</td>
							<td><s:textfield name="pointAccReg.cardIssuer"/></td>
							<td align="right">机构名称</td>
							<td><s:textfield name="pointAccReg.branchName"/></td>
						</tr>
						</s:if>
						<tr>
							<td></td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_POINTACCREG_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">编号</td>
			   <td align="center" nowrap class="titlebg">业务类型</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">文件名称</td>
			   <td align="center" nowrap class="titlebg">记录总数</td>
			   <td align="center" nowrap class="titlebg">总金额</td>
			   <td align="center" nowrap class="titlebg">导入时间</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${pointAccId}</td>
			  <td align="center" nowrap>${transTypeName}</td>
			  <td align="center" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
			  <td align="center" nowrap>${fileName}</td>
			  <td align="center" nowrap>${recordNum}</td>
			  <td align="right" nowrap>${fn:amount(amt)}</td>
			  <td align="center" nowrap><s:date name="time" format="yyyy-MM-dd HH:mm:ss" /></td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pointAccService/pointAccReg/detail.do?pointAccReg.pointAccId=${pointAccId}">查看</f:link>
			  		<s:if test="showCard && status==00">
			  		<f:pspan pid="pointpresent_cancel">
			  			<a href="javascript:submitUrl('searchForm', '/pointAccService/pointAccReg/cancel.do?pointAccId=${pointAccId}', '确定要注销吗？');" />注销</a>
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