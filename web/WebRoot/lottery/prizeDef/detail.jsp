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
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			var value = '${prizeDef.awdType}';
			if(value == '01' || value == '02') {
				$('#prizeCnt_tr').show();
			} else {
				$('#prizeCnt_tr').hide();
			}
		});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>奖项详细信息<span class="caption_title"> | <f:link href="/lottery/prizeDef/list.do">返回列表</f:link></span></caption>
			<tr>
				<td width="80" height="30" align="right">抽奖活动</td>
				<td>${prizeDef.drawId}</td>
				
				<td width="80" height="30" align="right"></td>
			    <td></td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">奖项名称</td>
				<td>${prizeDef.prizeName}</td>
				
				<td width="80" height="30" align="right">奖项简称</td>
				<td>${prizeDef.shortName}</td>
			</tr>
			<tr>
			    <td width="80" height="30" align="right">奖项编号</td>
				<td>${prizeDef.prizeNo}</td>
			    <td width="80" height="30" align="right">奖品类型</td>
				<td>${prizeDef.awdTypeName}</td>
			</tr>
			<tr>
			    <td width="80" height="30" align="right">奖品总数</td>
				<td>${brushSet.totalAwdCnt}</td>
				<td width="80" height="30" align="right">剩余奖品数</td>
				<td>${brushSet.leftAwdCnt}</td>
			</tr>
			<tr>
				<td id="pInstType_td1" width="80" height="30" align="right" >参与机构类型</td>
				<td id="pInstType_td2" >${prizeDef.jinstTypeName}</td>
				<td id="pinstId_td1" width="80" height="30" align="right" >参与机构编号</td>
				<td id="pinstId_td2" >${prizeDef.jinstId}-${fn:branch(prizeDef.jinstId)}${fn:merch(prizeDef.jinstId)}</td>
			</tr>
			<tr id="prizeCnt_tr"  style="display: none;">
			    <td width="80" height="30" align="right">奖品数量值</td>
				<td>${prizeDef.prizeCnt}</td>
				<td width="80" height="30" align="right">奖品子类型</td>
				<td>${prizeDef.classId}</td>
			</tr>
		  	<tr>
				<td>更新人</td>
				<td>${prizeDef.defOptr}</td>
				<td>更新时间</td>
				<td><s:date name="prizeDef.defTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
			<tr>
			    <td width="80" height="30" align="right">备注</td>
			    <td>${prizeDef.remark}</td>
			    <td width="80" height="30" align="right"></td>
			    <td></td>
			</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>