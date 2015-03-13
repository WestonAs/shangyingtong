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
		
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:css href="/css/multiselctor.css"/>
		
		<script>
			$(function(){
				if('${loginRoleType}' == '00'){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '');
				} else if('${loginRoleType}' == '01'){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '', '', '', '${loginBranchCode}');
				}
			});
		</script>

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
							<td align="right">发卡机构</td>
							<td>
								<s:hidden id="idBranchCode" name="logoConfig.branchNo"/>
								<s:textfield id="idBranchCode_sel" name="branchName" />
							</td>
							
							<td align="right">更新日期</td>
							<td>
								<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:66px;"/>&nbsp;-&nbsp;
								<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:66px;"/>
							</td>
							
							<td>
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="$('#idBranchCode').val('');$('#idBranchCode_sel').val('');FormUtils.reset('searchForm');" name="escape" />
								<f:pspan pid="logoConfig_add"><input style="margin-left:10px;" type="button" value="新增" onclick="javascript:gotoUrl('/para/logoConfig/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					
					<s:token name="_TOKEN_LOGO_CONFIG_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">首页大图名</td>
			   <td align="center" nowrap class="titlebg">首页小图名</td>
			   <td align="center" nowrap class="titlebg">登录后小图名</td>
			   <td align="center" nowrap class="titlebg">更新人</td>			   
			   <td align="center" nowrap class="titlebg">更新时间</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td align="left" nowrap>${branchNo}-${fn:branch(branchNo)}</td>
			  <td align="center" nowrap>${homeBig}</td>
			  <td align="center" nowrap>${homeSmall}</td>
			  <td align="center" nowrap>${loginSmall}</td>
			  <td align="center" nowrap>${updateBy}</td>
			  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			  <td align="center" nowrap>
			  	<span class="redlink">
				  	<f:pspan pid="logoConfig_delete">
			  			<a href="javascript:submitUrl('searchForm', '/para/logoConfig/delete.do?branchCode=${branchNo}', '确定要删除吗？');" />删除</a>
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