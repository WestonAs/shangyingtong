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

		<script type="text/javascript">
			$(function(){
				selectGeneralMerch();
			});
			
			function selectGeneralMerch(){
				if($('#generalMerch:checked').length>0){//选中通用商户checkbox
					$("#merchName").val("");
					enableOrDisableElmt($("#merchName"), false);
				}else{
					enableOrDisableElmt($("#merchName"), true);
				}
			}
			
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
				<s:form action="list.do" id="searchForm" cssClass="validate-tip">
					<table id="searchForm" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<s:if test="showCenter">
							<td align="right">分支机构名称</td>
							<td>
								<s:textfield  id="chlName" name="releaseCardFee.chlName"></s:textfield>
							</td>
							</s:if>
							<td align="right">发卡机构名称</td>
							<td>
								<s:if test="showCard">
									<s:select name="releaseCardFee.branchCode" headerKey="" headerValue="--请选择--" list="branchList" listKey="branchCode" listValue="branchName"></s:select>
								</s:if>
								<s:else>
									<s:textfield  id="branchName" name="releaseCardFee.branchName"></s:textfield>
								</s:else>
							</td>
							<td align="right">商户(组)名称</td>
							<td>
								<s:textfield id="merchName" name="releaseCardFee.merchName"></s:textfield>
								<label>&nbsp;<s:checkbox id="generalMerch" name="formMap.generalMerch" fieldValue="true" onchange="selectGeneralMerch()"/> 通用商户</label>
							</td>
						<s:if test="centerOrCenterDeptRoleLogined">
							</tr><tr>
						</s:if>
							<td align="right">卡BIN</td>
							<td>
								<s:textfield name="releaseCardFee.cardBin"></s:textfield>
							</td>
						<s:if test="!centerOrCenterDeptRoleLogined">
							</tr><tr>
						</s:if>
							<td align="right">计费方式</td>
							<td>
								<s:select name="releaseCardFee.feeType" headerKey="" headerValue="--请选择--" list="feeTypeList" listKey="value" listValue="name"></s:select>
							</td>
							<td align="right">交易类型</td>
							<td>
								<s:select name="releaseCardFee.transType" headerKey="" headerValue="--请选择--" list="transTypeList" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>							
							<td align="right">状态</td>
							<td>
								<s:select name="releaseCardFee.status" list="@gnete.card.entity.state.FeeStatus@getList()" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select>
							</td>
							<td>&nbsp;</td>
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm'); " name="escape" />
								<f:pspan pid="releasecardfee_add"><input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/fee/releaseCardFee/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					 <s:token name="_TOKEN_RCF_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">分支机构</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">商户</td>
			   <td align="center" nowrap class="titlebg">卡BIN</td>
			   <td align="center" nowrap class="titlebg">交易类型</td>
			   <td align="center" nowrap class="titlebg">计费方式</td>
			   <td align="center" nowrap class="titlebg">计费周期</td>
			   <!-- td align="center" nowrap class="titlebg">金额段</td> -->
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">费率</td>
			   <td align="center" nowrap class="titlebg">更新时间</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="left" nowrap>${chlCode}-${fn:branch(chlCode)}</td>
			  <td align="left" nowrap>${branchCode}-${fn:branch(branchCode)}</td>
			  <td align="left" nowrap><s:if test='"0".equals(merchId)'>通用商户</s:if><s:else>${merchName}</s:else></td>
			  <td align="center" nowrap>${cardBin}</td>
			  <td align="center" nowrap>${transTypeName}</td>
			  <td align="center" nowrap>${feeTypeName}</td>
			  <td align="center" nowrap>${feeCycleTypeName}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <!--  td align="right" nowrap>${fn:amount(ulMoney)}</td>-->
			  <td align="right" nowrap>
			  	<s:if test="feeType == 1 || feeType == 2 || feeType == 3">${feeRate}</s:if><s:else>${fn:percentPre(feeRate, 4)}</s:else>
			  </td>
			  <td align="center" nowrap><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/fee/releaseCardFee/detail.do?key.branchCode=${branchCode}&key.cardBin=${cardBin}&key.transType=${transType}&key.merchId=${merchId}&key.ulMoney=${ulMoney}">明细</f:link>
			  		<f:pspan pid="releasecardfee_modify"><f:link href="/fee/releaseCardFee/showModify.do?key.branchCode=${branchCode}&key.cardBin=${cardBin}&key.transType=${transType}&key.merchId=${merchId}&key.ulMoney=${ulMoney}">修改</f:link></f:pspan>
<%--			  		<f:pspan pid="releasecardfee_delete">--%>
<%--			  			<a href="javascript:submitUrl('searchForm','/fee/releaseCardFee/delete.do?branchCode=${branchCode}&cardBin=${cardBin}&transType=${transType}&merchId=${merchId}&ulMoney=${ulMoney}','确定要删除吗?');">删除</a>--%>
<%--			  		</f:pspan>--%>
			  		<s:if test="status==00"><!-- 生效, 可停用变为待生效 -->
				  		<f:pspan pid="releasecardfee_stop">
				  			<a href="javascript:submitUrl('searchForm','/fee/releaseCardFee/stop.do?branchCode=${branchCode}&cardBin=${cardBin}&transType=${transType}&merchId=${merchId}&ulMoney=${ulMoney}','确定要停用吗?');">停用</a>
				  		</f:pspan>
				  	</s:if>
				  	<s:elseif test="status==01"><!-- 待生效， 可启用变为生效 -->
				  		<f:pspan pid="releasecardfee_start">
				  			<a href="javascript:submitUrl('searchForm','/fee/releaseCardFee/start.do?branchCode=${branchCode}&cardBin=${cardBin}&transType=${transType}&merchId=${merchId}&ulMoney=${ulMoney}','确定要启用吗?');">启用</a>
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