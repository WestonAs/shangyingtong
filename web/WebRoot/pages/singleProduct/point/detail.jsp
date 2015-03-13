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
		<f:js src="/js/biz/singleProduct/pointClassTemp.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			$(function(){
				PointClassTemp.ptUsageForDetail();
			});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
		<caption>积分类型模板详细信息<span class="caption_title"> | <f:link href="/pages/singleProduct/point/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<input type="hidden" id="Id_ptUsage" value="${pointClassTemp.ptUsage}"/>
				<td width="80" height="30" align="right">积分名称</td>
				<td>
					${pointClassTemp.className}
				</td>
				<td width="80" height="30" align="right">积分类型简称</td>
				<td>
					${pointClassTemp.classShortName}
				</td>
				<td width="80" height="30" align="right">积分兑换规则类型</td>
				<td>
					${pointClassTemp.ptExchgRuleTypeName}
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">参考积分</td>
				<td>
					${fn:amount(pointClassTemp.ptRef)}
				</td>
				
				
				<td width="80" height="30" align="right">机构</td>
				<td>
					${fn:branch(pointClassTemp.branchCode)}
				</td>
				<td width="80" height="30" align="right">可用最新期数</td>
				<td>
					${pointClassTemp.ptLatestCyc}
				</td>
				
			</tr>
			<tr>
				<td width="80" height="30" align="right">金额类型</td>
				<td>
					${pointClassTemp.amtTypeName}
				</td>
				<td width="80" height="30" align="right">积分使用方法</td>
				<td>
					${pointClassTemp.ptUsageName}
				</td>
				<td width="80" height="30" align="right">积分兑换率</td>
				<td>
					${pointClassTemp.ptDiscntRate}
				</td>
			</tr>
			<tr id="usage_tr" style="display:none">
				<td id="td_usage1_1" width="80" height="30" align="right" style="display:none">积分有效期数</td>
				<td id="td_usage1_2" style="display:none">
					${pointClassTemp.ptValidityCyc}
				</td>
				<td id="td_usage2_1" width="80" height="30" align="right" style="display:none">兑换期(月数)</td>
				<td id="td_usage2_2" style="display:none">
					${pointClassTemp.ptClassParam1}
				</td>
				<td id="td_usage3_1" width="80" height="30" align="right" style="display:none">折旧分期数(月数)</td>
				<td id="td_usage3_2" style="display:none">
					${pointClassTemp.ptClassParam1}
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">积分分期方法</td>
				<td>
					${pointClassTemp.ptInstmMthdName}
				</td>
				<td width="80" height="30" align="right">积分分期数(月数)</td>
				<td>
					${pointClassTemp.instmPeriod}
				</td>
				<td width="80" height="30" align="right">积分折旧率</td>
				<td>
					${pointClassTemp.ptDeprecRate}
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">积分用途</td>
				<td colspan="5">
					${pointClassTemp.ptUseLimit}
				</td>
			</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>