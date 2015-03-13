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
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			$(function(){
				//PointClass.ptUsageForDetail();
			});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
		
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>会员类型模板详细信息<span class="caption_title"> | <f:link href="/pages/singleProduct/memb/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
				<td width="80" height="30" align="right">会员类型</td>
				<td>
					${membClassTemp.membClass }
				</td>
				<td width="80" height="30" align="right">类型名称</td>
				<td>
					${membClassTemp.className }
				</td>
				<td width="80" height="30" align="right">机构</td>
				<td>
					${membClassTemp.branchCode }
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">会员级别</td>
				<td>
					${membClassTemp.membLevel }
				</td>
				<td width="80" height="30" align="right">会员级别名称</td>
				<td>
					${membClassTemp.membClassName }
				</td>
				<td width="80" height="30" align="right">会员升级方式</td>
				<td>
					${membClassTemp.membUpgradeMthdName }
				</td>
			</tr>
			
			<tr>
				<td width="80" height="30" align="right">会员升级条件</td>
				<td>
					${membClassTemp.membUpgradeThrhd }
				</td>
				<td width="80" height="30" align="right">会员保级方式</td>
				<td>
					${membClassTemp.membDegradeMthdName }
				</td>
				<td width="80" height="30" align="right">会员保级条件</td>
				<td>
					${membClassTemp.membDegradeThrhd }
				</td>
			</tr>
			<tr>
				<td width="80" height="30" align="right">绝对失效日期</td>
				<td>
					${membClassTemp.mustExpirDate }
				</td>
				<td width="80" height="30" align="right">更新用户名</td>
				<td>
					${membClassTemp.updateBy }
				</td>
				<td width="80" height="30" align="right">更新时间</td>
				<td>
					<s:date name="membClassTemp.updateTime" format="yyyy-MM-dd HH:mm:ss" />
				</td>
			</tr>
			<!-- 
			<tr>
				<td width="80" height="30" align="right">状态</td>
				<td colspan="5">${membClassTemp.status }</td>
			</tr>
			 -->
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>