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
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>

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
					<table id="form_grid" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">用户编号</td>
							<td>
								<s:textfield id="id_WxUserInfo" name="wxUserInfo.userId" cssClass="{digit:true}"  maxlength="15" />
							</td>
							<td align="right">手机号</td>
							<td>
								<s:textfield id="id_ExternalCardId" name="wxUserInfo.externalCardId" cssClass="{digit:true}"  maxlength="32" />
							</td>
							<td align="right">注册渠道</td>
							<td>
								<s:textfield id="id_AccessNo" name="wxUserInfo.accessNo" cssClass="{digit:true}"  maxlength="32" />
							</td>
						</tr>
						<tr>	
							<td align="right">注册日期</td>
							<td>
								<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>
								&nbsp;&nbsp;-&nbsp;&nbsp;
								<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
							</td>
							<td align="right">用户状态</td>
							<td>
								<s:select name="wxUserInfo.userType" list="wxUserTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td align="right">&nbsp;</td>
							<td align="right">&nbsp;</td>
						</tr>
						<tr>
							<td height="30" colspan="6" align = "center">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_EXPIRECARD_LIST" />
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">用户编号</td>
			   <td align="center" nowrap class="titlebg">注册渠道</td>
			   <td align="center" nowrap class="titlebg">手机号</td>
			   <td align="center" nowrap class="titlebg">用户姓名</td>
			   <td align="center" nowrap class="titlebg">注册时间</td>
			   <td align="center" nowrap class="titlebg">用户类型</td>
			   
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data">
			<tr>
			  <td nowrap>${userId}</td>
			  <td align="center" nowrap>${accessNo}</td>
			  <td align="center" nowrap>${externalCardId}</td>
			  <td align="center" nowrap>${userName}</td>
			  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td align="center" nowrap>${userTypeName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/wxbusi/userInfo/detail.do?wxUserInfo.userId=${userId}">
			  		明细</f:link>	
			  	</span>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
				<span class="note_div">注释</span>
				<ul class="showli_div">
				<li class="showli_div">请至少输入用户编号/手机号/注册渠道。</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>
  
