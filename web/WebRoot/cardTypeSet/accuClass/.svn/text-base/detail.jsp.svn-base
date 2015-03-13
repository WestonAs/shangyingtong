<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>修改次卡子类型定义</title>
		
		<f:css href="/css/page.css"/>
		<f:css href="/css/multiselctor.css"/>
		
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		
		<f:js src="/js/biz/cardTypeSet/issType.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
		<input type="hidden" id="Id_coupnUsage" value="${couponClassDef.coupnUsage}"/>
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>次卡子类型详细信息<span class="caption_title"> | <f:link href="/cardTypeSet/accuClass/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td width="80" height="30" align="right">次卡类型</td>
				<td>
					${accuClassDef.accuClass}
				</td>
				<td width="80" height="30" align="right">次卡名称</td>
				<td>
					${accuClassDef.className}
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">联名机构类型</td>
				<td>
					${accuClassDef.jinstTypeName}
				</td>
				<td width="80" height="30" align="right">联名机构</td>
				<td>
					${accuClassDef.jinstName}
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">清算方法</td>
				<td>
					${accuClassDef.settMthdName}
				</td>
				<s:if test="showSettAmt">
				<td width="80" height="30" align="right">清算金额</td>
				<td>
					${fn:amount(accuClassDef.settAmt)}
				</td>
				</s:if>
				<s:else>
				<td></td>
				<td></td>
				</s:else>
			</tr>
			<tr>
				<td width="80" height="30" align="right">生效日期</td>
				<td>
					${accuClassDef.effDate}
				</td>
				<td width="80" height="30" align="right">失效日期</td>
				<td>
					${accuClassDef.expirDate}
				</td>
			</tr>
		</table>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>