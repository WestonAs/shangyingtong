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
		<f:css href="/css/multiselctor.css"/>
		
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		
		<f:js src="/js/biz/cardTypeSet/issType.js"/>
		<f:js src="/js/biz/cardTypeSet/couponClass.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
			$(function(){
				CouponClass.coupnUsageForDetail();
			});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
		<input type="hidden" id="Id_coupnUsage" value="${couponClassDef.coupnUsage}"/>
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>${ACT.name}<span class="caption_title"> | <f:link href="/cardTypeSet/couponClass/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td width="80" height="30" align="right">赠券类型</td>
				<td>
					${couponClassDef.coupnClass}
				</td>
				<td width="80" height="30" align="right">赠券名称</td>
				<td>
					${couponClassDef.className}
				</td>
				<td width="80" height="30" align="right">赠券类型简称</td>
				<td>
					${couponClassDef.classShortName}
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">发行机构编号</td>
				<td>
					${couponClassDef.issId}
				</td>
				<td width="80" height="30" align="right">发行机构类型</td>
				<td>
					${couponClassDef.issTypeName}
				</td>
				<td width="80" height="30" align="right">发行机构名称</td>
				<td>
					${fn:branch(couponClassDef.issId)}
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">联名机构编号</td>
				<td>
					${couponClassDef.jinstId}
				</td>
				<td width="80" height="30" align="right">联名机构类型</td>
				<td>
					${couponClassDef.jinstTypeName}
				</td>
				<td width="80" height="30" align="right">联名机构名称</td>
				<td>
					${couponClassDef.jinstName}
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">初始面值</td>
				<td>
					${fn:amount(couponClassDef.faceValue)}
				</td>
				<td width="80" height="30" align="right">赠券使用方法</td>
				<td>
					${couponClassDef.coupnUsageName}
				</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td id="td_ruleParam1_1" width="80" height="30" align="right" style="display:none"><span>满M元</span></td>
				<td id="td_ruleParam1_2" style="display:none">
					${couponClassDef.ruleParam1}
				</td>
				<td id="td_ruleParam2_1" width="80" height="30" align="right" style="display:none">
					<span id="usage_1" style="display:none">消费N元</span>
					<span id="usage_2" style="display:none">消费N元</span>
				</td>
				<td id="td_ruleParam2_2" style="display:none">	
					${couponClassDef.ruleParam2}
				</td>
				<td id="td_ruleParam3_1" width="80" height="30" align="right" style="display:none">使用赠券比例</td>
				<td id="td_ruleParam3_2" style="display:none">	
					${couponClassDef.ruleParam3}
				</td>
				<td id="td_ruleParam4_1" width="80" height="30" align="right" style="display:none"></td>
				<td id="td_ruleParam4_2" style="display:none">
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">生效日期</td>
				<td>
					${couponClassDef.effDate}
				</td>
				<td width="80" height="30" align="right">失效日期</td>
				<td>	
					${couponClassDef.expirDate}
				</td>
				<td></td>
				<td></td>
			</tr>
		</table>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>