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
							<td align="right">卡号</td>
							<td><s:textfield name="pointChgReg.cardId"/></td>	
							<td align="right">积分类型编号</td>
							<td><s:textfield name="pointChgReg.ptClass"/></td>	
							<td align="right">发卡机构编号</td>
							<td><s:textfield name="pointChgReg.cardBranch"/></td>	
						</tr>
						<tr>
							<td align="right">状态</td>
							<td>
								<s:select name="pointChgReg.status" list="statusList" 
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>	
							<td></td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="pointchange_add"><input style="margin-left:30px;" type="button" value="调整登记" onclick="javascript:gotoUrl('/pointBus/pointChange/prepareAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
								<f:pspan pid="pointchange_add"><input style="margin-left:30px;" type="button" value="上传文件" onclick="javascript:gotoUrl('/pointBus/pointChange/showFilePointChgReg.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_POINTCHANGE_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">调整编号</td>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <td align="center" nowrap class="titlebg">账号</td>
			   <td align="center" nowrap class="titlebg">发卡机构编号-名称</td>
			   <td align="center" nowrap class="titlebg">积分类型编号-名称</td>
			   <td align="center" nowrap class="titlebg">原积分</td>
			   <td align="center" nowrap class="titlebg">调整额</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			   
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>	
			  <td align="left" nowrap>${pointChgId}</td>	
			  <td align="center" nowrap>${cardId}</td>	
			  <td align="center" nowrap>${acctId}</td>
			  <td align="center" nowrap>${cardBranch}-${fn:branch(cardBranch)}</td>
			  <td align="center" nowrap>${ptClass}-${ptClassName}</td>
			  <td align="right" nowrap>${fn:amount(ptAvlb)}</td>
			  <td align="right" nowrap>${fn:amount(ptChg)}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pointBus/pointChange/detail.do?pointChgReg.pointChgId=${pointChgId}">查看</f:link>
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