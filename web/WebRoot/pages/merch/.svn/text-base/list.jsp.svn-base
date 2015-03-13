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
		
	    <f:css href="/css/jquery.autocomplete.css"/>
		<f:js src="/js/jquery.autocomplete.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
			$(function(){
				// 选择分支机构
				Selector.selectBranch('id_FenzhiBranchName', 'id_FenzhiBranchCode', true, '01');
				
				if('${loginRoleType}' == '00'){
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '20');
				} else if('${loginRoleType}' == '01'){
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '20', '', '', '${loginBranchCode}');
				}
			});

			$().ready(function() {
				function format(type) {
					return type.merchType + '|' + type.typeName;
				}
				
			    $("#id_merchType").autocomplete(CONTEXT_PATH + "/pages/merch/merchType.do", {
			    	minChars: 0,
			    	matchContains: 'word',
			    	mustMatch: true,
			    	autoFill: true,
					dataType: "json",
					parse: function(data) {
						return $.map(data, function(row) {
							return {
								data: row,
								value: row.merchType + '|' + row.typeName,
								result: row.merchType + '|' + row.typeName
							}
						});
					},
					formatItem: function(item) {
						return format(item);
					}
			    });
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
						<caption>商户信息列表<span class="caption_title"> | <f:link href="/pages/merch/regList.do">商户登记信息列表</f:link></span></caption>
						<tr>
							<td align="right">商户编号</td>
							<td><s:textfield name="merchInfo.merchId" cssClass="{digitOrLetter:true}"/></td>
							
							<td align="right">商户名称</td>
							<td><s:textfield name="merchInfo.merchName"/></td>
							<s:if test="!userOfLimitedTransQuery">
								<td align="right">更新时间</td>
								<td>
									<s:textfield id="startDate" name="startDate" onclick="getStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
									<s:textfield id="endDate" name="endDate" onclick="getEndDate();"  cssStyle="width:68px;"/>
								</td>
							</s:if>
						</tr>
						<tr>
							<s:if test="!userOfLimitedTransQuery">
								<td align="right">状态</td>
								<td>
									<s:select name="merchInfo.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
								</td>
								<s:if test="showCardBranch">
									<td align="right">发卡机构</td>
									<td><s:hidden id="idBranchCode" name="cardBranchCode"/>
									<s:textfield id="idBranchName" name="cardBranchName" /></td>
								</s:if>
								
								<td align="right">是否单机产品</td>
								<td>
									<s:select name="merchInfo.singleProduct" list="yesOrNoFlagList" headerKey="" headerValue="---全部---" listKey="value" listValue="name"></s:select>
								</td>
							</s:if>
						</tr>
						<tr>
						    <s:if test="showCenter">
							<td align="right">分支机构</td>
							<td>
							    <s:hidden  id="id_FenzhiBranchCode" name="merchInfo.manageBranch" />
								<s:textfield  id="id_FenzhiBranchName" name="manageBranchName" />
							</td>
							</s:if>
							<td align="right">商户类型</td>
							<td><s:textfield id="id_merchType"  name="merchInfo.merchType"/></td>
							<td align="right">审核时间</td>
							<td>
								<s:textfield id="checkStartDate" name="checkStartDate" onclick="getCheckStartDate();" cssStyle="width:68px;"/>&nbsp;-&nbsp;
								<s:textfield id="checkEndDate" name="checkEndDate" onclick="getCheckEndDate();"  cssStyle="width:68px;"/>
							</td>
						</tr>
						<tr>
							<td align="right">&nbsp;</td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm');$('#idBranchCode').val('');$('#idBranchName').val('');$('#id_FenzhiBranchCode').val('');$('#id_FenzhiBranchName').val('');" name="escape" />
								<f:pspan pid="merchantmgr_add">
									<input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/pages/merch/showAdd.do');" id="input_btn1"  name="escape" />
								</f:pspan>
								<f:pspan pid="merchantmgr_addOld">
									<input style="margin-left:30px;" type="button" value="录入旧系统商户" onclick="javascript:gotoUrl('/pages/merch/showAddOldMerch.do');" id="input_btn4"  name="escape" />
								</f:pspan>
								<f:pspan pid="merchantmgr_addFile">
									<input style="margin-left:30px;" type="button" value="批量新增商户" onclick="javascript:gotoUrl('/pages/merch/showAddMerchFile.do');" id="input_btn5"  name="escape" />
								</f:pspan>
									<!-- 导入旧商户时用到的
									<s:if test='("00").equals(loginRoleType)'>
										<input style="margin-left:30px;" type="button" value="提交审核" onclick="javascript:submitUrl('searchForm', '/pages/merch/submitCheck.do', '将把所有“待提交”状态的商户提交审核，确定要提交审核吗？');" id="input_btn4"  name="escape" />
									</s:if>
									 -->
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MERCH_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">商户编号</td>
			   <td align="center" nowrap class="titlebg">商户名称</td>
			   <td align="center" nowrap class="titlebg">是否单机产品</td>
			   <td align="center" nowrap class="titlebg">联系人</td>
			   <td align="center" nowrap class="titlebg">联系电话</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="left" nowrap>${merchId}</td>
			  <td align="left" nowrap>${merchName}</td>
			  <td align="center" nowrap>${singleProductName}</td>
			  <td align="center" nowrap>${linkMan}</td>
			  <td align="center" nowrap>${telNo}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/pages/merch/detail.do?merchInfo.merchId=${merchId}">查看</f:link>
			  	</span>
			  	
			  		<%-- 只有运营中心和运营机构能够维护商户信息 --%>
			  		<s:if test='centerOrCenterDeptRoleLogined || fenzhiRoleLogined'>
				  		<s:if test='"10".equals(status) || "00".equals(status)'>
					  		<span class="redlink">
						  		<f:pspan pid="merchantmgr_modify"><f:link href="/pages/merch/showModify.do?merchInfo.merchId=${merchId}">编辑</f:link></f:pspan>
					  		</span>
				  		</s:if>
				  		
				  		<s:if test="status==00">
				  			<f:pspan pid="merchantmgr_cancel">
						  		<span class="redlink">
					  				<a href="javascript:submitUrl('searchForm', '/pages/merch/cancel.do?merchId=${merchId}', '确定要注销吗？');" />注销</a>
					  			</span>
				  			</f:pspan>
				  		</s:if>
				  		<s:elseif test="status == 10">
				  			<f:pspan pid="merchantmgr_activate">
				  				<span class="redlink">
						  			<a href="javascript:submitUrl('searchForm', '/pages/merch/activate.do?merchId=${merchId}', '确定要生效吗？');" />生效</a>
				  				</span>
					  		</f:pspan>
				  		</s:elseif>
			  		</s:if>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>