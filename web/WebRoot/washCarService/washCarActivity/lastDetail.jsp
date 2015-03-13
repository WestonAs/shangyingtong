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
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		    $(function(){
		       if('${loginRoleType}' == '00'){
		       
			   }
			});
				
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="lastDetail.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
					   <tr>
						    <td align="right">活动ID</td>
							<td>
							   <s:textfield name="washCarActivityRecord.activityId"/>
						    </td>
						    <td align="right">卡号</td>
							<td>
							    <s:textfield name="washCarActivityRecord.cardId"/>
							</td>
						</tr>
				
						<tr>
							<td></td>
							<td height="30" colspan="4">
							<input type="submit" value="查询" id="input_btn2"  name="ok" />
							<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm');" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_WASHCARACTIVITYRECORD_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap="nowrap" class="titlebg">活动ID</td>
			   <td align="center" nowrap="nowrap" class="titlebg">卡号</td>
			   <td align="center" nowrap="nowrap" class="titlebg">周期</td>
			   <td align="center" nowrap="nowrap" class="titlebg">可用数量</td>
			   <td align="center" nowrap="nowrap" class="titlebg">更新时间</td>
			   <td align="center" nowrap="nowrap" class="titlebg">更新人</td>
			   <td align="center" nowrap="nowrap" class="titlebg">录入时间</td>
			   <td align="center" nowrap="nowrap" class="titlebg">备注</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td align="center" nowrap="nowrap">${activityId}</td>
			  <td align="center" nowrap="nowrap">${cardId}</td>
			  <td align="center" nowrap="nowrap">${washCarCycle}</td>
			  <td align="center" nowrap="nowrap">${availNum}</td>
			  <td align="center" nowrap="nowrap"><s:date name="updateTime"/></td>
			  <td align="center" nowrap="nowrap">${updateUser}</td>
			  <td align="center" nowrap="nowrap"><s:date name="insertTime"/></td>
			  <td align="center" nowrap="nowrap">${remark}</td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>