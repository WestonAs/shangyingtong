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
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
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
				if('${loginRoleType}' == '00'){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20');
					Selector.selectMerch('idMerchId_sel', 'idMerchId', true);
				} else if('${loginRoleType}' == '01'){
					Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '20', '', '', '${loginBranchCode}');
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
						<tr>
							<s:if test="centerOrCenterDeptRoleLogined || fenzhiRoleLogined">
								<td align="right">发卡机构</td>
								<td>
									<s:hidden id="idBranchCode" name="transLimitTerm.cardIssuer" cssClass=""/>
									<s:textfield id="idBranchCode_sel" name="formMap.branchName" cssClass=""/>
								</td>
							</s:if>
							<s:else>
								<td width="80" height="30" align="right">发卡机构编号</td>
								<td><s:textfield name="transLimitTerm.cardIssuer"/></td>
							</s:else>
							
							<s:if test="centerOrCenterDeptRoleLogined">
								<td align="right">商户</td>
								<td>
									<s:hidden id="idMerchId" name="transLimitTerm.merNo" />
									<s:textfield id="idMerchId_sel" name="formMap.merchName"/>
								</td>
							</s:if>
							<s:else>
								<td width="80" height="30" align="right">商户编号</td>
								<td><s:textfield name="transLimitTerm.merNo"/></td>
							</s:else>
							
							<td width="80" height="30" align="right">卡BIN</td>
							<td><s:textfield name="transLimitTerm.cardBin"/></td>
							<td width="80" height="30" align="right">终端号</td>
							<td><s:textfield name="transLimitTerm.termNo"/></td>
						</tr>
						<tr>
							<td width="80" align="right">状态</td>
							<td>
								<s:select id="Id_status" name="transLimitTerm.status" list="statusList" 
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name" >
								</s:select>
							</td>
							<td height="30" colspan="4" >
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" 
									onclick="$('#idBranchCode_sel').val('');$('#idBranchCode').val('');$('#idMerchId_sel').val('');$('#idMerchId').val('');FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="transLimitTerm_add">
									<input style="margin-left:30px;" type="button" value="批量添加/更新" onclick="javascript:gotoUrl('/transLimitTerm/showAdd.do');" id="input_btn3"  name="escape" />
								</f:pspan>
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
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">商户</td>
			   <td align="center" nowrap class="titlebg">终端号</td>
			   <td align="center" nowrap class="titlebg">卡BIN</td>
			   <td align="center" nowrap class="titlebg">状态</td>			   
			   <td align="center" nowrap class="titlebg">更新时间</td>			   
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>		
			  <td align="left" nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
			  <td align="left" nowrap>${merNo}-${fn:merch(merNo)}</td>
			  <td align="center" nowrap>${termNo}</td>
			  <td align="center" nowrap>${cardBin}</td>
			  <td align="center" nowrap>
			  	<s:if test="status=='00'"><!-- 注销 -->
			  		<font color="red">
			  			${statusName}
			  		</font>
			  	</s:if>
			  	<s:else>
			  		${statusName}
			  	</s:else>
			  </td>
			  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss"/> </td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<s:form id="operateForm" />
					<a href="javascript:openContextDialog('/transLimitTerm/detail.do?transLimitTerm.cardIssuer=${cardIssuer}&transLimitTerm.merNo=${merNo}&transLimitTerm.cardBin=${cardBin}&transLimitTerm.termNo=${termNo}');">
						查看
					</a>
			  		&nbsp;
			  		<f:pspan pid="transLimitTerm_modify">	
				  		<f:link href="/transLimitTerm/showModify.do?transLimitTerm.cardIssuer=${cardIssuer}&transLimitTerm.merNo=${merNo}&transLimitTerm.cardBin=${cardBin}&transLimitTerm.termNo=${termNo}">
				  			编辑
				  		</f:link>&nbsp;
				  		<s:if test='status=="00"' >
				  			<a href="javascript:submitUrl('operateForm','/transLimitTerm/modify.do?transLimitTerm.cardIssuer=${cardIssuer}&transLimitTerm.merNo=${merNo}&transLimitTerm.cardBin=${cardBin}&transLimitTerm.termNo=${termNo}&formMap.setNormal=true','确定要设置为正常吗?');">
				  				设置为正常
				  			</a>
				  		</s:if>
				  		<s:else>
				  			<a href="javascript:submitUrl('operateForm','/transLimitTerm/modify.do?transLimitTerm.cardIssuer=${cardIssuer}&transLimitTerm.merNo=${merNo}&transLimitTerm.cardBin=${cardBin}&transLimitTerm.termNo=${termNo}&formMap.setCancel=true','确定要设置为注销吗?');">
				  				设置为注销
				  			</a>
				  		</s:else>
				  	</f:pspan>&nbsp;
				  	<f:pspan pid="transLimitTerm_delete">
			  			<a href="javascript:submitUrl('operateForm', '/transLimitTerm/delete.do?transLimitTerm.cardIssuer=${cardIssuer}&transLimitTerm.merNo=${merNo}&transLimitTerm.cardBin=${cardBin}&transLimitTerm.termNo=${termNo}', '确定要删除吗？');" >
			  				删除
			  			</a>
			  		</f:pspan>	
			  	</span>
			  </td>
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
				<li class="showli_div">如果没有设置POS终端交易控制，默认为禁止。</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>