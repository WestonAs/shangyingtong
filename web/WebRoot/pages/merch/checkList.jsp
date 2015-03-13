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
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<jsp:include flush="true" page="/common/loadingBarDiv.jsp"></jsp:include>
		
		<div id="contentDiv" class="userbox">
	
		<!-- 数据列表区 -->
		<div class="tablebox">
			<form id="id_check_merch" method="post" action="checkList.do">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg"><input type="checkbox" onclick="FormUtils.selectAll(this, 'ids')" />全选</td>
			   <td align="center" nowrap class="titlebg">ID</td>
			   <td align="center" nowrap class="titlebg">商户名称</td>
			   <td align="center" nowrap class="titlebg">联系人</td>
			   <td align="center" nowrap class="titlebg">联系电话</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td><input type="checkbox" name="ids" value="${id}"/></td>
			 <td nowrap>${id}</td>
			  <td align="left" nowrap>${merchName}</td>
			  <td align="center" nowrap>${linkMan}</td>
			  <td align="center" nowrap>${telNo}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
		  			<f:link href="/pages/merch/checkDetail.do?merchInfoReg.id=${id}">查看</f:link>
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			</form>
			<f:paginate name="page" formIndex="0"/>
		</div>
		<f:workflow adapter="merchAdapter" returnUrl="/pages/merch/checkList.do"><s:token name="_TOKEN_MERCH_CHECKLIST"/></f:workflow>
		</div>
		
		<div class="userbox" style="padding-top: 10px">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<span class="note_div">注释：</span>
				<ul class="showli_div">
					<li class="showli_div">本审核页面用于 审核 运营中心、分支机构或运营代理添加的商户。</li>
				</ul> 
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>