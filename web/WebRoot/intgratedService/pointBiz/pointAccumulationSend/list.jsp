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
		<script language="javascript">
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
							<td align="right">申请ID</td>
							<td><s:textfield name="pointSendApplyReg.applyId" cssClass="{digit:true}"/></td>
							
							<td align="right">发卡机构</td>
							<td>
								<s:if test="cardRoleLogined">
									<s:select name="pointSendApplyReg.cardIssuer" list="cardBranchList" headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName"></s:select>
								</s:if>
								<s:else>
									<s:textfield name="pointSendApplyReg.cardIssuer"/>
								</s:else>
							</td>

							<td align="right">积分类型编号</td>
							<td><s:textfield name="pointSendApplyReg.ptClass"/></td>

							<td align="right">备注</td>
							<td><s:textfield name="pointSendApplyReg.remark"/></td>
						</tr>
	
						<tr>
							<td align="right">状态</td>
							<td>
								<s:select name="pointSendApplyReg.status" list="statusList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
							<td align="right"></td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="pointAccumulationSend_add">
									<input style="margin-left:30px;" type="button" value="新增" onclick="javascript:gotoUrl('/intgratedService/pointBiz/pointAccumulationSend/showAdd.do');" id="input_btn3"  name="escape" />
								</f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_EXTERNAL_CARD_IMPORT_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">申请ID</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">积分类型编号 - 名称</td>
			   <td align="center" nowrap class="titlebg">开始累积日期</td>
			   <td align="center" nowrap class="titlebg">结束累积日期</td>
			   <td align="center" nowrap class="titlebg">阀值</td>
			   <td align="center" nowrap class="titlebg">赠送积分</td>
			   <td align="center" nowrap class="titlebg">备注</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="center" nowrap>${applyId}</td>
			  <td nowrap>${cardIssuer}-${fn:branch(cardIssuer)}</td>
			  <td align="center" nowrap>${ptClass} - ${ptClassName}</td>
			  <td align="center" nowrap>${beginDate}</td>
			  <td align="center" nowrap>${endDate}</td>
			  <td align="center" nowrap>${thresholdValue}</td>
			  <td align="center" nowrap>${sendPoint}</td>
			  <td align="center" nowrap>${remark}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="left" nowrap>
			  	<span class="redlink">
				  	<f:link href="/intgratedService/pointBiz/pointAccumulationSend/detail.do?pointSendApplyReg.applyId=${applyId}">明细</f:link>
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