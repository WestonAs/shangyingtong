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
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>查询发卡机构业务审核配置<span class="caption_title"><f:pspan pid="cardBizCheck_modify"> | <f:link href="/pages/cardBizCheck/showModify.do">编辑</f:link></f:pspan></span></caption>
			<tr>
				<td>发卡机构编号</td>
				<td>${checkConfig.cardBranch}</td>
				<td>发卡机构名称</td>
				<td>${fn:branch(checkConfig.cardBranch)}</td>
		  	</tr>
			<tr>
				<td>售卡是否需要审核</td>
				<td>
					<span <s:if test='"Y".equals(checkConfig.sellCheck)'>class="redfont"</s:if>>
					${checkConfig.sellCheckName}
					</span>
				</td>
				<td>充值是否需要审核</td>
				<td>
					<span <s:if test='"Y".equals(checkConfig.depositCheck)'>class="redfont"</s:if>>
					${checkConfig.depositCheckName}
					</span>
				</td>
		  	</tr>
			<tr>
				<td>挂失是否需要审核</td>
				<td>
					<span <s:if test='"Y".equals(checkConfig.lossCardCheck)'>class="redfont"</s:if>>
					${checkConfig.lossCardCheckName}
					</span>
				</td>
				<td>转账是否需要审核</td>
				<td>
					<span <s:if test='"Y".equals(checkConfig.transAccCheck)'>class="redfont"</s:if>>
					${checkConfig.transAccCheckName}
					</span>
				</td>
		  	</tr>
			<tr>
				<td>销户是否需要审核</td>
				<td>
					<span <s:if test='"Y".equals(checkConfig.cancelCardCheck)'>class="redfont"</s:if>>
					${checkConfig.cancelCardCheckName}
					</span>
				</td>
				<td>延期是否需要审核</td>
				<td>
					<span <s:if test='"Y".equals(checkConfig.carddeferCheck)'>class="redfont"</s:if>>
					${checkConfig.carddeferCheckName}
					</span>
				</td>
		  	</tr>
			<tr>
				<td>换卡是否需要审核</td>
				<td colspan="3">
					<span <s:if test='"Y".equals(checkConfig.renewCardCheck)'>class="redfont"</s:if>>
					${checkConfig.renewCardCheckName}
					</span>
				</td>
		  	</tr>
		  	<tr>
				<td>更新人</td>
				<td>${checkConfig.updateBy}</td>
				<td>更新时间</td>
				<td><s:date name="checkConfig.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		  	</tr>
		</table>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>