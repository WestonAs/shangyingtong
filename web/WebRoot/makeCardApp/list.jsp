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
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<script>
			$(function(){
				if('${loginRoleTypeCode}' == '00'){
					$('#id_branchCode').val('');
					Selector.selectBranch('id_branchCodeName', 'id_branchCode', true, '20');
				} else if('${loginRoleTypeCode}' == '01'){
					$('#id_branchCode').val('');
					Selector.selectBranch('id_branchCodeName', 'id_branchCode', true, '20', '', '', '${loginBranchCode}');
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
				<s:form action="list.do" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">制卡申请ID</td>
							<td>
								<s:textfield name="makeCardApp.appId" cssClass="{digit:true}"/>
								<span class="error_tipinfo">制卡申请ID只能数字</span>
							</td>
							<td align="right">卡样名称</td>
							<td>
								<s:textfield name="makeCardApp.makeName" title="模糊查询"/>
							</td>
							<td align="right">状态</td>
							<td>
								<s:select name="makeCardApp.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<s:if test="centerOrCenterDeptRoleLogined || fenzhiRoleLogined">
								<td align="right">发卡机构</td>
								<td>
									<s:hidden id="id_branchCode" name="makeCardApp.branchCode"/>
									<s:textfield id="id_branchCodeName" name="makeCardApp.branchName"/>
								</td>
							</s:if>
							<s:elseif test="cardOrCardDeptRoleLogined">
								<td align="right">发卡机构</td>
								<td>
									<s:select name="makeCardApp.branchCode" list="myCardBranch"
										 headerKey="" headerValue="--请选择--" 
										 listKey="branchCode" listValue="branchName" 
										 onmouseover="FixWidth(this);" />
								</td>
							</s:elseif>
							
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="FormUtils.clearFormFields('searchForm')" name="escape" />
								<f:pspan pid="makecardmgr_addcard_add"><input style="margin-left:10px;" type="button" value="新增" onclick="javascript:gotoUrl('/makeCardApp/preShowAdd.do');" id="input_btn3"  name="escape" />
								</f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MAKECARD_APP_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">制卡申请ID</td>
			   <td align="center" nowrap class="titlebg">卡样名称</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">卡BIN号</td>
			   <td align="center" nowrap class="titlebg">卡起始号</td>
			   <td align="center" nowrap class="titlebg">卡数量</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data">
			<tr>
			  <td align="center" nowrap>${appId}</td>
			  <td align="center" nowrap>${makeName}</td>
			  <td align="center" nowrap>${branchCode}-${fn:branch(branchCode) }</td>
			  <td align="center" nowrap>${binNo}</td>
			  <td align="center" nowrap>${strNo}</td>
			  <td align="center" nowrap>${cardNum}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/makeCardApp/detail.do?makeCardApp.id=${id}">明细</f:link>
			  		<s:if test="status==01">
			  		<f:pspan pid="makecardmgr_addcard_appcancel">
			  			<f:link href="/makeCardApp/showCancel.do?makeCardApp.id=${id}">撤销制卡</f:link>
			  		</f:pspan>
			  		</s:if>
			  		<s:elseif test="status==06 || status==12">
			  		<f:pspan pid="makecardmgr_addcard_addcancel">
			  			<f:link href="/makeCardApp/showRevoke.do?makeCardApp.id=${id}">撤销建档</f:link>
			  		</f:pspan>
			  		</s:elseif>
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