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
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
		<table class="detail_grid" width="98%" border="1" cellspacing="0" cellpadding="1">
			<caption>会员登记资料详细信息<span class="caption_title"> | <f:link href="/vipCard/membInfoReg/list.do?goBack=goBack">返回列表</f:link></span></caption>
			<tr>
			    <td>会员登记信息ID</td>
			    <td>${membInfoReg.membInfoRegId}</td>
				<td>会员录入批次ID</td>
                <td>${membInfoReg.membInfoId}</td>
			</tr>
			<tr>
				<td>会员类型</td>
				<td>${membInfoReg.membClass}</td>
				<td>会员级别</td>
                <td>${membInfoReg.membLevel}</td>
			</tr>
			<tr>
				<td>发卡机构</td>
				<td>${membInfoReg.cardIssuer}-${fn:branch(membInfoReg.cardIssuer)}</td>
				<td>会员名称</td>
                <td>${membInfoReg.custName}</td>
			</tr>
			<tr>
				<td>证件类型</td>
				<td>${membInfoReg.credTypeName}</td>
				<td>证件号</td>
				<td>${membInfoReg.credNo}</td>
			</tr>
			<tr>
				<td>地址</td>
				<td>${membInfoReg.address}</td>
				<td>性别</td>
				<td>${membInfoReg.sexName}</td>
			</tr>
			<tr>
				<td>生日</td>
				<td>${membInfoReg.birthday}</td>
				<td>年龄</td>
				<td>${membInfoReg.age}</td>
			</tr>
			<tr>
				<td>手机号</td>
				<td>${membInfoReg.mobileNo}</td>
				<td>座机号</td>
				<td>${membInfoReg.telNo}</td>
			</tr>
			<tr>
				<td>电子邮件</td>
				<td>${membInfoReg.email}</td>
				<td>工作</td>
				<td>${membInfoReg.job}</td>
			</tr>
			<tr>
				<td>薪水</td>
				<td>${membInfoReg.salary}</td>
				<td>教育程度</td>
				<td>${membInfoReg.educationName}</td>
			</tr>
			<tr>
				<td>会员持卡数</td>
				<td>${membInfoReg.membCardNum}</td>
				<td>指定卡</td>
				<td>${membInfoReg.appointCard}</td>
			</tr>
			<tr>
				<td>更新时间</td>
				<td><s:date name="membInfoReg.updateTime"  format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>更新人</td>
				<td>${membInfoReg.updateBy}</td>
			</tr>
			<tr>
				<td>入库时间</td>
				<td><s:date name="membInfoReg.insertTime"  format="yyyy-MM-dd HH:mm:ss" /></td>
				<td>备注</td>
				<td>${membInfoReg.remark}</td>
			</tr>
		</table>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>