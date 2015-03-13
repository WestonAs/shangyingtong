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
		<f:js src="/js/datePicker/WdatePicker.js"/>

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
							<td align="right">商圈</td>
							<td><s:textfield name="merchGroupPointCouponLimit.groupId" /></td>
							<td align="right">商户编号</td>
							<td><s:textfield name="merchGroupPointCouponLimit.merchId" /></td>

						<tr>
							<td>&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="merchgrouppntcpnlimit_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/cardTypeSet/pointCouponsRule/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MERCHGROUPPOINTCOUPONLIMIT_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
		<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
		<thead>
		<tr>
      <td align="center" nowrap class="titlebg">商圈</td>
      <td align="center" nowrap class="titlebg">限制类型</td>
      <td align="center" nowrap class="titlebg">类型ID</td>
      <td align="center" nowrap class="titlebg">商户编号</td>
      <td align="center" nowrap class="titlebg">状态</td>

      <td align="center" nowrap class="titlebg">操作</td>
		</tr>
		</thead>
		<s:iterator value="page.data"> 
		<tr>		
      <td align="center" nowrap>${groupId}-${groupName}</td>
      <td align="center" nowrap>${limitTypeName}</td>
      <td align="center" nowrap>${limitId}-${coupnName}${ptName}</td>
      <td align="center" nowrap>${merchId}-${fn:merch(merchId)}</td>
      <td align="center" nowrap>${statusName}</td>

		  <td align="center" nowrap>
		  	<span class="redlink">
		  		<f:link href="/cardTypeSet/pointCouponsRule/detail.do?merchGroupPointCouponLimit.limitId=${limitId}&&merchGroupPointCouponLimit.limitType=${limitType}&&merchGroupPointCouponLimit.groupId=${groupId}&&merchGroupPointCouponLimit.merchId=${merchId}">查看</f:link>
			  	<f:pspan pid="merchgrouppntcpnlimit_modify"><f:link href="/cardTypeSet/pointCouponsRule/showModify.do?merchGroupPointCouponLimit.limitId=${limitId}&&merchGroupPointCouponLimit.limitType=${limitType}&&merchGroupPointCouponLimit.groupId=${groupId}&&merchGroupPointCouponLimit.merchId=${merchId}">修改</f:link></f:pspan>
			  	<f:pspan pid="merchgrouppntcpnlimit_delete">
			  		<a href="javascript:submitUrl('searchForm', '/cardTypeSet/pointCouponsRule/delete.do?limitId=${limitId}&&limitType=${limitType}&&groupId=${groupId}&&merchId=${merchId}', '确定要删除吗？');" />删除</a>
			  	</f:pspan>
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