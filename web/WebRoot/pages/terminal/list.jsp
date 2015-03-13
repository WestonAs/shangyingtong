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
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
			$(function(){
				Selector.selectBranch('id_FenzhiBranchName', 'id_FenzhiBranchCode', true, '01');// 选择分支机构
				Selector.selectBranch('idMaintenance_sel', 'idMaintenance', true, '00,01,32');
				if('${loginRoleType}' == '00'){
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '20');
				} else if('${loginRoleType}' == '01'){
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '20', '', '', '${loginBranchCode}');
				} else if('${loginRoleType}' == '11'){ // 运营代理
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '20');
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
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<s:if test="showCardBranch">
							<tr>
								<td align="right">终端编号</td>
								<td><s:textfield name="terminal.termId" cssClass="{digitOrLetter:true}"/></td>
								
								<td align="right">商户</td>
								<td><s:textfield name="terminal.merchName"/></td>
	
								<td align="right">更新时间</td>
								<td>
									<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
									<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
								</td>
							</tr>
							<tr>
								<td align="right">输入方式</td>
								<td>
									<s:select name="terminal.entryMode" list="entryModeList" headerKey="" headerValue="---全部---" listKey="value" listValue="name"></s:select>
								</td>
	
								<td align="right">是否单机产品</td>
								<td>
									<s:select name="terminal.singleProduct" list="singleProductList" headerKey="" headerValue="---全部---" listKey="value" listValue="name"></s:select>
								</td>
								
								<td align="right">发卡机构</td>
								<td><s:hidden id="idBranchCode" name="cardBranchCode"/>
								<s:textfield id="idBranchName" name="cardBranchName" /></td>
							</tr>
							<tr>
							    <s:if test="showCenter">
									<td align="right">分支机构</td>
									<td>
									    <s:hidden  id="id_FenzhiBranchCode" name="fenzhiBranchCode" />
										<s:textfield  id="id_FenzhiBranchName" name="fenzhiBranchName" />
									</td>
									
									<td align="right">维护机构</td>
									<td>
										<s:hidden id="idMaintenance" name="terminal.maintenance" />
										<s:textfield id="idMaintenance_sel" name="maintenance_sel" />
									</td>
								</s:if>
								<td align="right">创建时间</td>
								<td>
									<s:textfield id="checkStartDate" name="checkStartDate" onclick="getCheckStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
									<s:textfield id="checkEndDate" name="checkEndDate" onclick="getCheckEndDate();"  cssStyle="width:68px;"/>
								</td>
							</tr>
							<tr>
								<td height="30" align="right"></td>
								<td height="30" colspan="5">
									<input type="submit" value="查询" id="input_btn2"  name="ok" />
									<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm');$('#idBranchCode').val('');$('#idBranchName').val('');$('#id_FenzhiBranchCode').val('');$('#id_FenzhiBranchName').val('');$('#idMaintenance').val('');$('#idMaintenance_sel').val('');" name="escape" />
									<f:pspan pid="terminalmgr_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/pages/terminal/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
									<f:pspan pid="terminalmgr_add"><input style="margin-left:30px;" type="button" value="批量新增" onclick="javascript:gotoUrl('/pages/terminal/showAddBatch.do');" id="input_btn3"  name="escape" /></f:pspan>
									<s:if test="centerOrCenterDeptRoleLogined">
										<f:pspan pid="terminalmgr_add"><input style="margin-left:30px;" type="button" value="文件方式批量新增" onclick="javascript:gotoUrl('/pages/terminal/showAddBatchFile.do');" id="input_btn3"  name="escape" /></f:pspan>
									</s:if>
									<f:pspan pid="terminalmgr_add"><input style="margin-left:30px;" type="button" value="录入旧系统终端" onclick="javascript:gotoUrl('/pages/terminal/showAddOldTerminal.do');" id="input_btn3"  name="escape" /></f:pspan>
								</td>
							</tr>
						</s:if>
						<s:else>
							<tr>
								<td align="right">终端编号</td>
								<td><s:textfield name="terminal.termId" cssClass="{digitOrLetter:true}"/></td>
								
								<td align="right">商户</td>
								<td><s:textfield name="terminal.merchName"/></td>
								
								<td align="right">更新时间</td>
								<td>
									<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
									<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
								</td>
							</tr>
							<tr>
								<td align="right">输入方式</td>
								<td>
									<s:select name="terminal.entryMode" list="entryModeList" headerKey="" headerValue="---全部---" listKey="value" listValue="name"></s:select>
								</td>
								
								<td align="right">是否单机产品</td>
								<td>
									<s:select name="terminal.singleProduct" list="singleProductList" headerKey="" headerValue="---全部---" listKey="value" listValue="name"></s:select>
								</td>
								
								<td height="30" colspan="2">
									<input type="submit" value="查询" id="input_btn2"  name="ok" />
									<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm');" name="escape" />
								</td>
							</tr>
						</s:else>
					</table>
					<s:token name="_TOKEN_TERMINAL_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">终端编号</td>
			   <td align="center" nowrap class="titlebg">商户</td>
			   <td align="center" nowrap class="titlebg">输入方式</td>
			   <td align="center" nowrap class="titlebg">是否单机产品</td>
			   <td align="center" nowrap class="titlebg">更新时间</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap>${termId}</td>
			  <td nowrap>${merchId}-${merchName}</td>
			  <td align="center" nowrap>${entryModeName}</td>
			  <td align="center" nowrap>${singleProductName}</td>
			  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			  <td align="center" nowrap>${posStatusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/terminal/detail.do?terminal.termId=${termId}">查看</f:link>
			  		<f:pspan pid="terminalmgr_modify"><f:link href="/pages/terminal/showModify.do?terminal.termId=${termId}">编辑</f:link></f:pspan>
			  		<s:if test="posStatus==0">
			  			<f:pspan pid="terminalmgr_stop">
			  				<a href="javascript:submitUrl('searchForm', '/pages/terminal/stop.do?termId=${termId}', '确定要停用吗？');" />停用</a>
			  			</f:pspan>
			  		</s:if>
			  		<s:else>
			  			<f:pspan pid="terminalmgr_start">
				  			<a href="javascript:submitUrl('searchForm', '/pages/terminal/start.do?termId=${termId}', '确定要启用吗？');" />启用</a>
				  		</f:pspan>
			  		</s:else>
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