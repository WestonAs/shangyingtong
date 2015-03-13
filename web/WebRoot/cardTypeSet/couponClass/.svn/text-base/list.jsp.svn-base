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
							<td align="right">赠券名称</td>
							<td><s:textfield name="couponClassDef.className" /></td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="coupnclass_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/cardTypeSet/couponClass/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_COUPONCLASS_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">赠券类型</td>
			   <td align="center" nowrap class="titlebg">赠券名称</td>
			   <td align="center" nowrap class="titlebg">发行机构编号</td>
			   <td align="center" nowrap class="titlebg">发行机构名称</td>
			   <td align="center" nowrap class="titlebg">联名机构编号</td>
			   <td align="center" nowrap class="titlebg">联名机构名称</td>
			   <td align="center" nowrap class="titlebg">初始面值</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td>${coupnClass}</td>
			  <td align="left" nowrap>${className}</td>
			  <td align="center" nowrap>${issId}</td>
			  <td align="center" nowrap>${fn:branch(issId)}</td>
			  <td align="center" nowrap>${jinstId}</td>
			  <td align="center" nowrap>${jinstName}</td>
			  <td align="right" nowrap>${fn:amount(faceValue)}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/cardTypeSet/couponClass/detail.do?couponClassDef.coupnClass=${coupnClass}">查看</f:link>
			  		<s:if test="issId==loginBranchCode && jinstType !=1">
			  		<f:pspan pid="coupnclass_modify"><f:link href="/cardTypeSet/couponClass/showModify.do?couponClassDef.coupnClass=${coupnClass}">修改</f:link></f:pspan>
			  		<f:pspan pid="coupnclass_delete">	
				  			<a href="javascript:submitUrl('searchForm','/cardTypeSet/couponClass/delete.do?couponClassDef.coupnClass=${coupnClass}','确定要删除吗?');">删除</a>
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