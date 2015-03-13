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
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:css href="/css/jquery.autocomplete.css"/>
		<f:js src="/js/jquery.autocomplete.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script>
			$(function(){
				if('${loginRoleType}' == '00'){
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '20');
				} else if('${loginRoleType}' == '01'){
					Selector.selectBranch('idBranchName', 'idBranchCode', true, '20', '', '', '${loginBranchCode}');
				}
				
				$('#input_btn3').click(function(){
					$('#searchForm').attr('action', 'generate.do').submit();
					$('#searchForm').attr('action', 'list.do');
					$('#searchForm').find(":submit").attr('disabled', false);
					$('#searchForm').find(":button").attr('disabled', false);
				});
			});
			
			function checkCardId() {
				var strCardId = $('#id_strCardId').val();
				var endCardId = $('#id_endCardId').val();
				if(strCardId > endCardId){
					showMsg('起始卡号不能大于结束卡号');
					return false;
				}
			}
			
			$().ready(function() {
				function format(type) {
					return type.value + '|' + type.name;
				}
				
			    $("#id_appOrgId").autocomplete(CONTEXT_PATH + "/cardReceive/stockInfo/appOrgList.do", {
			    	minChars: 0,
			    	matchContains: 'word',
			    	mustMatch: true,
			    	autoFill: true,
					dataType: "json",
					parse: function(data) {
						return $.map(data, function(row) {
							return {
								data: row,
								value: row.value + '|' + row.name,
								result: row.value + '|' + row.name
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
					<input type="hidden" name="linkFromListPage" value="true"/>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">起始卡号</td>
							<td>
								<s:textfield id="id_strCardId" name="strCardId" cssClass="{digit:true}" maxlength="19"/>
							</td>

							<td align="right">结束卡号</td>
							<td>
								<s:textfield id="id_endCardId" name="endCardId" cssClass="{digit:true}" maxlength="19"/>
							</td>
							
							<td align="right">卡批次</td>
							<td>
								<s:textfield name="cardStockInfo.makeId" />
							</td>
						</tr>
	
						<tr>
							<td align="right">发卡机构</td>
							<td>
								<s:if test="showCardBranch">
								<s:hidden id="idBranchCode" name="cardStockInfo.cardIssuer"/><s:textfield id="idBranchName" name="stockBranchName" />
								</s:if>
								<s:else>
								<s:select name="cardStockInfo.cardIssuer" list="cardBranchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName">
								</s:select>
								</s:else>
							</td>

							<td align="right">领卡机构</td>
							<td>
								<s:textfield id="id_appOrgId" name="cardStockInfo.appOrgId" />
							</td>
							
							<td align="right">卡种类</td>
							<td>
								<s:select name="cardStockInfo.cardClass" list="cardClassList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name">
								</s:select>
							</td>
						</tr>
						<tr>
							<td align="right">状态</td>
							<td>
								<s:select name="cardStockInfo.cardStatus" list="cardStatusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name">
								</s:select>
							</td>
							
							<td align="right">卡类型号</td>
							<td>
								<s:textfield name="cardStockInfo.cardSubclass" />
							</td>
							
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" onclick="return checkCardId();"/>
								<input style="margin-left:10px;" type="button" value="清除" onclick="FormUtils.reset('searchForm');$('#idBranchName').val('');$('#idBranchCode').val('');" name="escape" />
								<input style="margin-left:10px;" type="button" value="导出Excel" id="input_btn3"  name="ok" />
							</td>
						</tr>
					</table>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <td align="center" nowrap class="titlebg">卡种类</td>
			   <td align="center" nowrap class="titlebg">卡类型号</td>
			   <td align="center" nowrap class="titlebg">卡批次</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">领卡机构</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td nowrap align="center">${cardId}</td>
			  <td align="center" nowrap>${cardTypeName}</td>
			  <td align="center" nowrap>${cardSubclass}</td>
			  <td align="center" nowrap>${makeId}</td>
			  <td nowrap>${fn:branch(cardIssuer)}-${cardIssuer}</td>
			  <td nowrap>${fn:branch(appOrgId)}${fn:dept(appOrgId)}${fn:merch(appOrgId)}-${appOrgId}</td>
			  <td nowrap>${cardStatusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/cardReceive/stockInfo/detail.do?cardStockInfo.id=${id}">明细</f:link>
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